
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

@Component
public class DataInitializer implements ApplicationRunner {

    private static final Logger log =
            LoggerFactory.getLogger(DataInitializer.class);

    private static final List<String> MODULES = List.of(
            "administration",
            "announcements",
            "attendance",
            "audit",
            "authorization",
            "documents",
            "employees",
            "identity",
            "leave",
            "notifications",
            "organization",
            "products",
            "projects",
            "recruitment",
            "support",
            "tasks"
    );

    private static final List<String> ACTIONS = List.of(
            "read", "create", "update", "delete"
    );

    private static final String[][] ROLES = {
            {"ADMIN", "Administrator"},
            {"HR", "Human Resources"},
            {"MANAGER", "Manager"},
            {"EMPLOYEE", "Employee"}
    };

    private final RoleRepository roles;
    private final PermissionRepository permissions;
    private final RolePermissionRepository rolePermissions;
    private final UserRepository users;
    private final UserRoleRepository userRoles;
    private final PasswordEncoder encoder;
    private final String adminEmail;
    private final String adminPassword;

    public DataInitializer(
            RoleRepository roles,
            PermissionRepository permissions,
            RolePermissionRepository rolePermissions,
            UserRepository users,
            UserRoleRepository userRoles,
            PasswordEncoder encoder,
            @Value("${app.bootstrap.admin-email:}") String adminEmail,
            @Value("${app.bootstrap.admin-password:}") String adminPassword
    ) {
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

        // Create system roles if they do not exist.
        for (String[] roleData : ROLES) {

            if (roles.findByCode(roleData[0]).isEmpty()) {

                Role role = new Role();
                role.setCode(roleData[0]);
                role.setName(roleData[1]);
                role.setSystem(true);

                roles.save(role);
            }
        }

        Role admin = roles.findByCode("ADMIN")
                .orElseThrow();

        // Create module permissions and assign them to ADMIN.
        for (String module : MODULES) {

            for (String action : ACTIONS) {

                String code = module + "." + action;

                Permission permission = permissions.findByCode(code)
                        .orElseGet(() -> {

                            Permission newPermission = new Permission();
                            newPermission.setCode(code);
                            newPermission.setModule(module);
                            newPermission.setAction(action);

                            return permissions.save(newPermission);
                        });

                if (!rolePermissions.existsByRoleIdAndPermissionId(
                        admin.getId(),
                        permission.getId()
                )) {

                    RolePermission rolePermission = new RolePermission();
                    rolePermission.setRoleId(admin.getId());
                    rolePermission.setPermissionId(permission.getId());

                    rolePermissions.save(rolePermission);
                }
            }
        }

        // Create initial admin account if credentials are configured.
        if (!adminEmail.isBlank()
                && !adminPassword.isBlank()
                && !users.existsByEmailIgnoreCase(adminEmail)) {

            User user = new User();
            user.setEmail(adminEmail.trim());
            user.setPasswordHash(encoder.encode(adminPassword));
            user.setStatus("ACTIVE");

            user = users.save(user);

            UserRole userRole = new UserRole();
            userRole.setUserId(user.getId());
            userRole.setRoleId(admin.getId());
            userRole.setScopeType("GLOBAL");

            userRoles.save(userRole);

            log.info("Bootstrap admin user created: {}", adminEmail);
        }
    }
}
