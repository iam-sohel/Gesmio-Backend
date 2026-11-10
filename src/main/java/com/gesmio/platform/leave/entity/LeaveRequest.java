package com.gesmio.platform.leave.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "leave_requests")
@Getter
@Setter
@NoArgsConstructor
public class LeaveRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @NotNull
    @Column(name = "employee_id")
    private UUID employeeId;

    @NotNull
    @Column(name = "leave_policy_id")
    private UUID leavePolicyId;

    @NotNull
    @Column(name = "start_date")
    private LocalDate startDate;

    @NotNull
    @Column(name = "end_date")
    private LocalDate endDate;

    @NotNull
    @Column(name = "requested_days")
    private BigDecimal requestedDays;

    @Column(name = "reason", length = 255)
    private String reason;

    @NotBlank
    @Column(name = "status", length = 255)
    private String status = "PENDING";

    @CreationTimestamp
    @Column(name = "submitted_at", updatable = false)
    private Instant submittedAt;
}
