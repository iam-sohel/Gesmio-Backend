package com.gesmio.platform.common.security;

import com.gesmio.platform.common.exception.BusinessException;
import java.util.UUID;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public final class SecurityUtils {

    private SecurityUtils() {
    }

    public static UUID currentUserIdOrNull() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof JwtAuthenticationToken jwt) {
            try {
                return UUID.fromString(jwt.getToken().getSubject());
            } catch (IllegalArgumentException ignored) {
                return null;
            }
        }
        return null;
    }

    public static UUID currentUserId() {
        UUID id = currentUserIdOrNull();
        if (id == null) {
            throw new BusinessException("No authenticated user");
        }
        return id;
    }

    public static boolean hasRole(String role) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_" + role));
    }

    public static boolean isPrivileged() {
        return hasRole("ADMIN") || hasRole("HR");
    }
}
