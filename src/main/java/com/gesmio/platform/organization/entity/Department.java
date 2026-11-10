package com.gesmio.platform.organization.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "departments")
@Getter
@Setter
@NoArgsConstructor
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @NotBlank
    @Column(name = "name", length = 255)
    private String name;

    @NotBlank
    @Column(name = "code", length = 255, unique = true)
    private String code;

    @Column(name = "parent_department_id")
    private UUID parentDepartmentId;
}
