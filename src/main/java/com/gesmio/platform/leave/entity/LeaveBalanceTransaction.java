package com.gesmio.platform.leave.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "leave_balance_transactions")
@Getter
@Setter
@NoArgsConstructor
public class LeaveBalanceTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @NotNull
    @Column(name = "leave_balance_id")
    private UUID leaveBalanceId;

    @Column(name = "leave_request_id")
    private UUID leaveRequestId;

    @NotBlank
    @Column(name = "transaction_type", length = 255)
    private String transactionType;

    @NotNull
    @Column(name = "days")
    private BigDecimal days;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;
}
