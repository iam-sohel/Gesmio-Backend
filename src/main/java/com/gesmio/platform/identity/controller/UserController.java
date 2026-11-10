package com.gesmio.platform.identity.controller;

import com.gesmio.platform.audit.service.AuditService;
import com.gesmio.platform.common.crud.PageResponse;
import com.gesmio.platform.common.exception.ConflictException;
import com.gesmio.platform.common.exception.ResourceNotFoundException;
import com.gesmio.platform.identity.entity.User;
import com.gesmio.platform.identity.repository.UserRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.Instant;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/** Admin user management. Passwords are hashed here and never returned. */
@RestController
@RequestMapping("/api/v1/identity/users")
@Transactional
public class UserController {

    public record UserResponse(UUID id, String email, String status, Instant createdAt) {
        static UserResponse from(User u) {
            return new UserResponse(u.getId(), u.getEmail(), u.getStatus(), u.getCreatedAt());
        }
    }

    public record CreateUserRequest(@NotBlank @Email String email,
                                    @NotBlank @Size(min = 8, max = 100) String password,
                                    String status) {
    }

    public record UpdateUserRequest(@Email String email, String status) {
    }

    public record ResetPasswordRequest(@NotBlank @Size(min = 8, max = 100) String newPassword) {
    }

    private final UserRepository users;
    private final PasswordEncoder encoder;
    private final AuditService audit;

    public UserController(UserRepository users, PasswordEncoder encoder, AuditService audit) {
        this.users = users;
        this.encoder = encoder;
        this.audit = audit;
    }

    @GetMapping
    @Transactional(readOnly = true)
    public PageResponse<UserResponse> list(@PageableDefault(size = 20) Pageable pageable) {
        return PageResponse.of(users.findAll(pageable).map(UserResponse::from));
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public UserResponse get(@PathVariable UUID id) {
        return UserResponse.from(find(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse create(@Valid @RequestBody CreateUserRequest request) {
        if (users.existsByEmailIgnoreCase(request.email())) {
            throw new ConflictException("Email already registered");
        }
        User user = new User();
        user.setEmail(request.email().trim());
        user.setPasswordHash(encoder.encode(request.password()));
        if (request.status() != null) {
            user.setStatus(request.status());
        }
        User saved = users.save(user);
        audit.record("CREATE", "users", saved.getId(), "SUCCESS");
        return UserResponse.from(saved);
    }

    @PutMapping("/{id}")
    public UserResponse update(@PathVariable UUID id, @Valid @RequestBody UpdateUserRequest request) {
        User user = find(id);
        if (request.email() != null && !request.email().equalsIgnoreCase(user.getEmail())) {
            if (users.existsByEmailIgnoreCase(request.email())) {
                throw new ConflictException("Email already registered");
            }
            user.setEmail(request.email().trim());
        }
        if (request.status() != null) {
            user.setStatus(request.status());
        }
        audit.record("UPDATE", "users", id, "SUCCESS");
        return UserResponse.from(user);
    }

    @PostMapping("/{id}/reset-password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void resetPassword(@PathVariable UUID id, @Valid @RequestBody ResetPasswordRequest request) {
        User user = find(id);
        user.setPasswordHash(encoder.encode(request.newPassword()));
        audit.record("RESET_PASSWORD", "users", id, "SUCCESS");
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        users.delete(find(id));
        users.flush();
        audit.record("DELETE", "users", id, "SUCCESS");
    }

    private User find(UUID id) {
        return users.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found: " + id));
    }
}
