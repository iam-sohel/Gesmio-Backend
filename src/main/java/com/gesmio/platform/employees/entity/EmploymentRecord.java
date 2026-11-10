package com.gesmio.platform.employees.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "employment_records")
@Getter
@Setter
@NoArgsConstructor
public class EmploymentRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @NotNull
    @Column(name = "employee_id")
    private UUID employeeId;

    @Column(name = "department_id")
    private UUID departmentId;

    @Column(name = "designation_id")
    private UUID designationId;

    @NotNull
    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;
}
