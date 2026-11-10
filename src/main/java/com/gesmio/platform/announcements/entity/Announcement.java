package com.gesmio.platform.announcements.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "announcements")
@Getter
@Setter
@NoArgsConstructor
public class Announcement {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "created_by")
    private UUID createdBy;

    @NotBlank
    @Column(name = "title", length = 255)
    private String title;

    @NotBlank
    @Column(name = "content")
    private String content;

    @NotBlank
    @Column(name = "audience_type", length = 255)
    private String audienceType = "ALL";

    @NotBlank
    @Column(name = "status", length = 255)
    private String status = "DRAFT";

    @Column(name = "publish_at")
    private Instant publishAt;

    @Column(name = "expires_at")
    private Instant expiresAt;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;
}
