package com.gesmio.platform.tasks.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "task_assignments")
@Getter
@Setter
@NoArgsConstructor
public class TaskAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @NotNull
    @Column(name = "task_id")
    private UUID taskId;

    @NotNull
    @Column(name = "employee_id")
    private UUID employeeId;

    @NotNull
    @Column(name = "is_primary")
    private Boolean isPrimary = false;

    @Column(name = "assigned_by")
    private UUID assignedBy;

    @CreationTimestamp
    @Column(name = "assigned_at", updatable = false)
    private Instant assignedAt;

    @Column(name = "revoked_at")
    private Instant revokedAt;
}
