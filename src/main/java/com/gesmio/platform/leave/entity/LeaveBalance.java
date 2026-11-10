package com.gesmio.platform.leave.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "leave_balances")
@Getter
@Setter
@NoArgsConstructor
public class LeaveBalance {

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
    @Column(name = "leave_year")
    private Integer leaveYear;

    @NotNull
    @Column(name = "opening_balance")
    private BigDecimal openingBalance = BigDecimal.ZERO;

    @NotNull
    @Column(name = "accrued_days")
    private BigDecimal accruedDays = BigDecimal.ZERO;

    @NotNull
    @Column(name = "used_days")
    private BigDecimal usedDays = BigDecimal.ZERO;

    @NotNull
    @Column(name = "available_days")
    private BigDecimal availableDays = BigDecimal.ZERO;
}
