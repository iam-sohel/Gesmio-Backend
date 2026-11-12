
package com.gesmio.platform.identity.auth;

import com.gesmio.platform.audit.service.AuditService;
import com.gesmio.platform.common.exception.BadRequestException;
import com.gesmio.platform.common.security.SecurityUtils;
import com.gesmio.platform.identity.entity.User;
import com.gesmio.platform.identity.repository.UserRepository;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    public record LoginRequest(
            @NotBlank @Email String email,
            @NotBlank String password
    ) {}

    public record ChangePasswordRequest(
            @NotBlank String currentPassword,
            @NotBlank @Size(min = 8, max = 100) String newPassword
    ) {}

    private final AuthService authService;
    private final UserRepository users;
    private final PasswordEncoder encoder;
    private final AuditService audit;

    public AuthController(
            AuthService authService,
            UserRepository users,
            PasswordEncoder encoder,
            AuditService audit
    ) {
        this.authService = authService;
        this.users = users;
        this.encoder = encoder;
        this.audit = audit;
    }

    /**
     * Authenticate user and generate JWT.
     */
    @PostMapping("/login")
    public AuthService.LoginResult login(
            @Valid @RequestBody LoginRequest request
    ) {
        return authService.login(
                request.email(),
                request.password()
        );
    }

    /**
     * Get currently authenticated user's profile.
     */
    @GetMapping("/me")
    public AuthService.Profile me() {
        return authService.me(
                SecurityUtils.currentUserId()
        );
    }

    /**
     * Change password of currently authenticated user.
     */
    @PostMapping("/change-password")
    @Transactional
    public void changePassword(
            @Valid @RequestBody ChangePasswordRequest request
    ) {
        User user = users.findById(
                SecurityUtils.currentUserId()
        ).orElseThrow();

        if (!encoder.matches(
                request.currentPassword(),
                user.getPasswordHash()
        )) {
            throw new BadRequestException(
                    "Current password is incorrect"
            );
        }

        user.setPasswordHash(
                encoder.encode(request.newPassword())
        );

        audit.record(
                "CHANGE_PASSWORD",
                "users",
                user.getId(),
                "SUCCESS"
        );
    }

    /**
     * Handle invalid login credentials.
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ProblemDetail badCredentials(
            BadCredentialsException ex
    ) {
        return ProblemDetail.forStatusAndDetail(
                HttpStatus.UNAUTHORIZED,
                ex.getMessage()
        );
    }
}
