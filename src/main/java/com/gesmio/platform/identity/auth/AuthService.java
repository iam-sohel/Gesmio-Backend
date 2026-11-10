package com.gesmio.platform.identity.auth;

import com.gesmio.platform.audit.service.AuditService;
import com.gesmio.platform.authorization.entity.Permission;
import com.gesmio.platform.authorization.entity.Role;
import com.gesmio.platform.authorization.entity.RolePermission;
import com.gesmio.platform.authorization.entity.UserRole;
import com.gesmio.platform.authorization.repository.PermissionRepository;
import com.gesmio.platform.authorization.repository.RolePermissionRepository;
import com.gesmio.platform.authorization.repository.RoleRepository;
import com.gesmio.platform.authorization.repository.UserRoleRepository;
import com.gesmio.platform.common.exception.BusinessException;
import com.gesmio.platform.common.exception.ResourceNotFoundException;
import com.gesmio.platform.employees.entity.Employee;
import com.gesmio.platform.employees.repository.EmployeeRepository;
import com.gesmio.platform.identity.entity.Session;
import com.gesmio.platform.identity.entity.User;
import com.gesmio.platform.identity.repository.SessionRepository;
import com.gesmio.platform.identity.repository.UserRepository;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.HexFormat;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    public record LoginResult(String accessToken, String tokenType, Instant expiresAt, Profile user) {
    }

    public record Profile(UUID userId, String email, Set<String> roles, Set<String> permissions,
                          UUID employeeId, String employeeCode, UUID departmentId) {
    }

    private final UserRepository users;
    private final UserRoleRepository userRoles;
    private final RoleRepository roles;
    private final RolePermissionRepository rolePermissions;
    private final PermissionRepository permissions;
    private final EmployeeRepository employees;
    private final SessionRepository sessions;
    private final PasswordEncoder encoder;
    private final JwtService jwt;
    private final AuditService audit;

    public AuthService(UserRepository users, UserRoleRepository userRoles, RoleRepository roles,
                       RolePermissionRepository rolePermissions, PermissionRepository permissions,
                       EmployeeRepository employees, SessionRepository sessions, PasswordEncoder encoder,
                       JwtService jwt, AuditService audit) {
        this.users = users;
        this.userRoles = userRoles;
        this.roles = roles;
        this.rolePermissions = rolePermissions;
        this.permissions = permissions;
        this.employees = employees;
        this.sessions = sessions;
        this.encoder = encoder;
        this.jwt = jwt;
        this.audit = audit;
    }

    @Transactional
    public LoginResult login(String email, String password) {
        Optional<User> found = users.findByEmailIgnoreCase(email.trim());
        if (found.isEmpty() || !encoder.matches(password, found.get().getPasswordHash())) {
            audit.recordIndependent("LOGIN", "users", found.map(User::getId).orElse(null), "FAILURE",
                    found.map(User::getId).orElse(null));
            throw new BadCredentialsException("Invalid email or password");
        }
        User user = found.get();
        if (!"ACTIVE".equalsIgnoreCase(user.getStatus())) {
            audit.recordIndependent("LOGIN", "users", user.getId(), "DENIED_" + user.getStatus(), user.getId());
            throw new BusinessException("Account is not active");
        }
        Profile profile = profile(user);
        Instant now = Instant.now();
        String token = jwt.issue(user.getId(), user.getEmail(), profile.roles(), profile.permissions(),
                profile.employeeId(), now);

        Session session = new Session();
        session.setUserId(user.getId());
        session.setTokenHash(sha256(token));
        session.setExpiresAt(jwt.expiryFrom(now));
        sessions.save(session);

        audit.recordIndependent("LOGIN", "users", user.getId(), "SUCCESS", user.getId());
        return new LoginResult(token, "Bearer", jwt.expiryFrom(now), profile);
    }

    @Transactional(readOnly = true)
    public Profile me(UUID userId) {
        User user = users.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return profile(user);
    }

    private Profile profile(User user) {
        List<UserRole> assignments = userRoles.findByUserIdAndRevokedAtIsNull(user.getId());
        List<UUID> roleIds = assignments.stream().map(UserRole::getRoleId).distinct().toList();
        Set<String> roleCodes = roles.findAllById(roleIds).stream().map(Role::getCode).collect(Collectors.toSet());
        Set<String> permissionCodes = Set.of();
        if (!roleIds.isEmpty()) {
            List<UUID> permissionIds = rolePermissions.findByRoleIdIn(roleIds).stream()
                    .map(RolePermission::getPermissionId).distinct().toList();
            permissionCodes = permissions.findAllById(permissionIds).stream()
                    .map(Permission::getCode).collect(Collectors.toSet());
        }
        Optional<Employee> emp = employees.findByUserId(user.getId());
        return new Profile(user.getId(), user.getEmail(), roleCodes, permissionCodes,
                emp.map(Employee::getId).orElse(null), emp.map(Employee::getEmployeeCode).orElse(null),
                emp.map(Employee::getDepartmentId).orElse(null));
    }

    private static String sha256(String value) {
        try {
            return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256")
                    .digest(value.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
