package com.gesmio.platform.leave.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "leave_approvals")
@Getter
@Setter
@NoArgsConstructor
public class LeaveApproval {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @NotNull
    @Column(name = "leave_request_id")
    private UUID leaveRequestId;

    @NotNull
    @Column(name = "approver_user_id")
    private UUID approverUserId;

    @NotNull
    @Column(name = "approval_level")
    private Integer approvalLevel = 1;

    @NotBlank
    @Column(name = "decision", length = 255)
    private String decision;

    @Column(name = "comments")
    private String comments;

    @Column(name = "acted_at")
    private Instant actedAt;
}
