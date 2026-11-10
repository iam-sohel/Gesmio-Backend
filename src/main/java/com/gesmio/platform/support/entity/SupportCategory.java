package com.gesmio.platform.support.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "support_categories")
@Getter
@Setter
@NoArgsConstructor
public class SupportCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @NotBlank
    @Column(name = "code", length = 255, unique = true)
    private String code;

    @NotBlank
    @Column(name = "name", length = 255)
    private String name;

    @NotNull
    @Column(name = "is_active")
    private Boolean isActive = true;
}
