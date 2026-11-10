package com.gesmio.platform.leave.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "leave_policies")
@Getter
@Setter
@NoArgsConstructor
public class LeavePolicy {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @NotBlank
    @Column(name = "code", length = 255, unique = true)
    private String code;

    @NotBlank
    @Column(name = "name", length = 255)
    private String name;

    @NotNull
    @Column(name = "annual_entitlement")
    private BigDecimal annualEntitlement = BigDecimal.ZERO;

    @NotNull
    @Column(name = "is_paid")
    private Boolean isPaid = true;

    @NotNull
    @Column(name = "is_active")
    private Boolean isActive = true;
}
