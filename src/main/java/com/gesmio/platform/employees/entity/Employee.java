package com.gesmio.platform.employees.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "employees")
@Getter
@Setter
@NoArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "department_id")
    private UUID departmentId;

    @Column(name = "designation_id")
    private UUID designationId;

    @NotBlank
    @Column(name = "employee_code", length = 255, unique = true)
    private String employeeCode;

    @Column(name = "joining_date")
    private LocalDate joiningDate;

    @NotBlank
    @Column(name = "employment_status", length = 255)
    private String employmentStatus = "ACTIVE";
}
