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
@Table(name = "support_tickets")
@Getter
@Setter
@NoArgsConstructor
public class SupportTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @NotBlank
    @Column(name = "ticket_number", length = 255, unique = true)
    private String ticketNumber;

    @NotNull
    @Column(name = "requester_employee_id")
    private UUID requesterEmployeeId;

    @Column(name = "category_id")
    private UUID categoryId;

    @Column(name = "assigned_to_user_id")
    private UUID assignedToUserId;

    @NotBlank
    @Column(name = "subject", length = 255)
    private String subject;

    @Column(name = "description")
    private String description;

    @NotBlank
    @Column(name = "priority", length = 255)
    private String priority = "MEDIUM";

    @NotBlank
    @Column(name = "status", length = 255)
    private String status = "OPEN";

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @Column(name = "resolved_at")
    private Instant resolvedAt;
}
