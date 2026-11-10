package com.gesmio.platform.recruitment.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "job_offers")
@Getter
@Setter
@NoArgsConstructor
public class JobOffer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @NotNull
    @Column(name = "job_application_id")
    private UUID jobApplicationId;

    @NotBlank
    @Column(name = "offer_status", length = 255)
    private String offerStatus = "DRAFT";

    @Column(name = "offered_at")
    private LocalDate offeredAt;

    @Column(name = "response_deadline")
    private LocalDate responseDeadline;
}
