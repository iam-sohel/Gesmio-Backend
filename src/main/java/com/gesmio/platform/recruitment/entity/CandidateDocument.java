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
@Table(name = "candidate_documents")
@Getter
@Setter
@NoArgsConstructor
public class CandidateDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @NotNull
    @Column(name = "candidate_id")
    private UUID candidateId;

    @NotBlank
    @Column(name = "document_type", length = 255)
    private String documentType;

    @NotBlank
    @Column(name = "file_name", length = 255)
    private String fileName;

    @NotBlank
    @Column(name = "storage_key", length = 255)
    private String storageKey;

    @CreationTimestamp
    @Column(name = "uploaded_at", updatable = false)
    private Instant uploadedAt;
}
