
package com.gesmio.platform.authorization.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(
        name = "permissions",
        indexes = {
                @Index(name = "idx_permissions_module", columnList = "module"),
                @Index(name = "idx_permissions_action", columnList = "action")
        }
)
@Getter
@Setter
@NoArgsConstructor
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @NotBlank
    @Column(name = "code", nullable = false, length = 255, unique = true)
    private String code;

    @NotBlank
    @Column(name = "module", nullable = false, length = 255)
    private String module;

    @NotBlank
    @Column(name = "action", nullable = false, length = 255)
    private String action;
}
