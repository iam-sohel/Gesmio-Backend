package com.gesmio.platform.support.entity;

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
@Table(name = "support_ticket_comments")
@Getter
@Setter
@NoArgsConstructor
public class SupportTicketComment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @NotNull
    @Column(name = "ticket_id")
    private UUID ticketId;

    @Column(name = "author_user_id")
    private UUID authorUserId;

    @NotBlank
    @Column(name = "comment")
    private String comment;

    @NotNull
    @Column(name = "is_internal")
    private Boolean isInternal = false;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;
}
