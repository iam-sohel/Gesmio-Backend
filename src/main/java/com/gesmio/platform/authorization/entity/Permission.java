package com.gesmio.platform.authorization.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "permissions")
@Getter
@Setter
@NoArgsConstructor
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @NotBlank
    @Column(name = "code", length = 255, unique = true)
    private String code;

    @NotBlank
    @Column(name = "module", length = 255)
    private String module;

    @NotBlank
    @Column(name = "action", length = 255)
    private String action;
}
