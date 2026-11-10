package com.gesmio.platform.organization.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "designations")
@Getter
@Setter
@NoArgsConstructor
public class Designation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @NotBlank
    @Column(name = "title", length = 255)
    private String title;

    @NotBlank
    @Column(name = "code", length = 255, unique = true)
    private String code;
}
