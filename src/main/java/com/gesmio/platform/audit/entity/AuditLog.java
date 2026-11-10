package com.gesmio.platform.audit.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.net.InetAddress;
import java.time.Instant;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "audit_logs")
@Getter
@Setter
@NoArgsConstructor
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "actor_user_id")
    private UUID actorUserId;

    @NotBlank
    @Column(name = "action", length = 255)
    private String action;

    @Column(name = "entity_type", length = 255)
    private String entityType;

    @Column(name = "entity_id")
    private UUID entityId;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "old_values", columnDefinition = "jsonb")
    private String oldValues;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "new_values", columnDefinition = "jsonb")
    private String newValues;

    @Column(name = "outcome", length = 255)
    private String outcome;

    @Column(name = "request_id", length = 255)
    private String requestId;

    @JdbcTypeCode(SqlTypes.INET)
    @Column(name = "ip_address")
    private InetAddress ipAddress;

    @Column(name = "user_agent", length = 255)
    private String userAgent;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;
}
