package com.gesmio.platform.common.bootstrap;

import com.gesmio.platform.authorization.entity.Permission;
import com.gesmio.platform.authorization.entity.Role;
import com.gesmio.platform.authorization.entity.RolePermission;
import com.gesmio.platform.authorization.entity.UserRole;
import com.gesmio.platform.authorization.repository.PermissionRepository;
import com.gesmio.platform.authorization.repository.RolePermissionRepository;
import com.gesmio.platform.authorization.repository.RoleRepository;
import com.gesmio.platform.authorization.repository.UserRoleRepository;
import com.gesmio.platform.identity.entity.User;
import com.gesmio.platform.identity.repository.UserRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Idempotent startup seeding:
 *  - system roles ADMIN, HR, MANAGER, EMPLOYEE
 *  - permissions "<module>.<action>" for every module, all granted to ADMIN
 *  - first admin account when ADMIN_EMAIL / ADMIN_PASSWORD are set and the email does not exist yet
 */
@Component
public class DataInitializer implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);
    private static final List<String> MODULES = List.of("administration","announcements","attendance","audit","authorization","documents","employees","identity","leave","notifications","organization","products","projects","recruitment","support","tasks");
    private static final List<String> ACTIONS = List.of("read", "create", "update", "delete");
    private static final String[][] ROLES = {
            {"ADMIN", "Administrator"}, {"HR", "Human Resources"}, {"MANAGER", "Manager"}, {"EMPLOYEE", "Employee"}};

    private final RoleRepository roles;
    private final PermissionRepository permissions;
    private final RolePermissionRepository rolePermissions;
    private final UserRepository users;
    private final UserRoleRepository userRoles;
    private final PasswordEncoder encoder;
    private final String adminEmail;
    private final String adminPassword;

    public DataInitializer(RoleRepository roles, PermissionRepository permissions,
                           RolePermissionRepository rolePermissions, UserRepository users,
                           UserRoleRepository userRoles, PasswordEncoder encoder,
                           @Value("${app.bootstrap.admin-email:}") String adminEmail,
                           @Value("${app.bootstrap.admin-password:}") String adminPassword) {
        this.roles = roles;
        this.permissions = permissions;
        this.rolePermissions = rolePermissions;
        this.users = users;
        this.userRoles = userRoles;
        this.encoder = encoder;
        this.adminEmail = adminEmail;
        this.adminPassword = adminPassword;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        for (String[] r : ROLES) {
            if (roles.findByCode(r[0]).isEmpty()) {
                Role role = new Role();
                role.setCode(r[0]);
                role.setName(r[1]);
                role.setIsSystem(true);
                roles.save(role);
            }
        }
        Role admin = roles.findByCode("ADMIN").orElseThrow();
        for (String module : MODULES) {
            for (String action : ACTIONS) {
                String code = module + "." + action;
                Permission p = permissions.findByCode(code).orElseGet(() -> {
                    Permission np = new Permission();
                    np.setCode(code);
                    np.setModule(module);
                    np.setAction(action);
                    return permissions.save(np);
                });
                if (!rolePermissions.existsByRoleIdAndPermissionId(admin.getId(), p.getId())) {
                    RolePermission rp = new RolePermission();
                    rp.setRoleId(admin.getId());
                    rp.setPermissionId(p.getId());
                    rolePermissions.save(rp);
                }
            }
        }
        if (!adminEmail.isBlank() && !adminPassword.isBlank() && !users.existsByEmailIgnoreCase(adminEmail)) {
            User user = new User();
            user.setEmail(adminEmail.trim());
            user.setPasswordHash(encoder.encode(adminPassword));
            user.setStatus("ACTIVE");
            user = users.save(user);
            UserRole ur = new UserRole();
            ur.setUserId(user.getId());
            ur.setRoleId(admin.getId());
            ur.setScopeType("GLOBAL");
            userRoles.save(ur);
            log.info("Bootstrap admin user created: {}", adminEmail);
        }
    }
}
