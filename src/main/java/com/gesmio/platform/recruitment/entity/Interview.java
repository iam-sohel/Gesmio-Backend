package com.gesmio.platform.recruitment.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "interviews")
@Getter
@Setter
@NoArgsConstructor
public class Interview {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @NotNull
    @Column(name = "job_application_id")
    private UUID jobApplicationId;

    @Column(name = "interview_type", length = 255)
    private String interviewType;

    @Column(name = "scheduled_at")
    private Instant scheduledAt;

    @NotBlank
    @Column(name = "status", length = 255)
    private String status = "SCHEDULED";
}
