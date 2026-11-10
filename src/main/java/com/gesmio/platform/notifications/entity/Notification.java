package com.gesmio.platform.notifications.entity;

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
@Table(name = "notifications")
@Getter
@Setter
@NoArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @NotNull
    @Column(name = "recipient_user_id")
    private UUID recipientUserId;

    @Column(name = "notification_type", length = 255)
    private String notificationType;

    @NotBlank
    @Column(name = "title", length = 255)
    private String title;

    @Column(name = "message")
    private String message;

    @Column(name = "reference_type", length = 255)
    private String referenceType;

    @Column(name = "reference_id")
    private UUID referenceId;

    @Column(name = "read_at")
    private Instant readAt;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;
}
