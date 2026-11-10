package com.gesmio.platform.recruitment.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "job_openings")
@Getter
@Setter
@NoArgsConstructor
public class JobOpening {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "department_id")
    private UUID departmentId;

    @Column(name = "created_by")
    private UUID createdBy;

    @Column(name = "hiring_manager_id")
    private UUID hiringManagerId;

    @NotBlank
    @Column(name = "title", length = 255)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "employment_type", length = 255)
    private String employmentType;

    @NotBlank
    @Column(name = "status", length = 255)
    private String status = "OPEN";

    @Column(name = "closing_date")
    private LocalDate closingDate;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;
}
