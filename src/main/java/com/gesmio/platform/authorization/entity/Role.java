
package com.gesmio.platform.authorization.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @NotBlank
    @Column(name = "code", nullable = false, unique = true, length = 255)
    private String code;

    @NotBlank
    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @NotNull
    @Column(name = "is_system", nullable = false)
    private Boolean system = false;
}
