package com.gesmio.platform.administration.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "platform_settings")
@Getter
@Setter
@NoArgsConstructor
public class PlatformSetting {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @NotBlank
    @Column(name = "setting_key", length = 255, unique = true)
    private String settingKey;

    @Column(name = "category", length = 255)
    private String category;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "setting_value", columnDefinition = "jsonb")
    private String settingValue;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "is_active")
    private Boolean isActive = true;

    @NotNull
    @Column(name = "version")
    private Integer version = 1;

    @Column(name = "updated_by")
    private UUID updatedBy;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;
}
