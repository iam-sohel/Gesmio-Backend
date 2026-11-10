package com.gesmio.platform.authorization.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "user_roles")
@Getter
@Setter
@NoArgsConstructor
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @NotNull
    @Column(name = "user_id")
    private UUID userId;

    @NotNull
    @Column(name = "role_id")
    private UUID roleId;

    @NotBlank
    @Column(name = "scope_type", length = 255)
    private String scopeType = "GLOBAL";

    @Column(name = "department_id")
    private UUID departmentId;

    @Column(name = "project_id")
    private UUID projectId;

    @Column(name = "assigned_by")
    private UUID assignedBy;

    @CreationTimestamp
    @Column(name = "assigned_at", updatable = false)
    private Instant assignedAt;

    @Column(name = "revoked_at")
    private Instant revokedAt;
}
