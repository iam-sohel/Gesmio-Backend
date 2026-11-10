package com.gesmio.platform.documents.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "documents")
@Getter
@Setter
@NoArgsConstructor
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "owner_employee_id")
    private UUID ownerEmployeeId;

    @Column(name = "uploaded_by")
    private UUID uploadedBy;

    @NotBlank
    @Column(name = "title", length = 255)
    private String title;

    @NotBlank
    @Column(name = "file_name", length = 255)
    private String fileName;

    @NotBlank
    @Column(name = "storage_key", length = 255, unique = true)
    private String storageKey;

    @Column(name = "mime_type", length = 255)
    private String mimeType;

    @Column(name = "file_size")
    private Long fileSize;

    @NotBlank
    @Column(name = "visibility", length = 255)
    private String visibility = "PRIVATE";

    @NotBlank
    @Column(name = "status", length = 255)
    private String status = "ACTIVE";

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;
}
