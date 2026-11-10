package com.gesmio.platform.employees.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "reporting_relationships")
@Getter
@Setter
@NoArgsConstructor
public class ReportingRelationship {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @NotNull
    @Column(name = "employee_id")
    private UUID employeeId;

    @NotNull
    @Column(name = "manager_employee_id")
    private UUID managerEmployeeId;

    @NotNull
    @Column(name = "effective_from")
    private LocalDate effectiveFrom;

    @Column(name = "effective_to")
    private LocalDate effectiveTo;
}
