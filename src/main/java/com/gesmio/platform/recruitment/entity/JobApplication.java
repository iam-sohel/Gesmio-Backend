package com.gesmio.platform.recruitment.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "job_applications")
@Getter
@Setter
@NoArgsConstructor
public class JobApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @NotNull
    @Column(name = "candidate_id")
    private UUID candidateId;

    @NotNull
    @Column(name = "job_opening_id")
    private UUID jobOpeningId;

    @NotBlank
    @Column(name = "status", length = 255)
    private String status = "APPLIED";

    @CreationTimestamp
    @Column(name = "applied_at", updatable = false)
    private Instant appliedAt;
}
