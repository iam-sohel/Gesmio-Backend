package com.gesmio.platform.attendance.entity;

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
@Table(name = "attendance_adjustments")
@Getter
@Setter
@NoArgsConstructor
public class AttendanceAdjustment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @NotNull
    @Column(name = "attendance_record_id")
    private UUID attendanceRecordId;

    @Column(name = "requested_by")
    private UUID requestedBy;

    @Column(name = "reviewed_by")
    private UUID reviewedBy;

    @Column(name = "reason")
    private String reason;

    @Column(name = "old_check_in_at")
    private Instant oldCheckInAt;

    @Column(name = "new_check_in_at")
    private Instant newCheckInAt;

    @Column(name = "old_check_out_at")
    private Instant oldCheckOutAt;

    @Column(name = "new_check_out_at")
    private Instant newCheckOutAt;

    @NotBlank
    @Column(name = "status", length = 255)
    private String status = "PENDING";

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;
}
