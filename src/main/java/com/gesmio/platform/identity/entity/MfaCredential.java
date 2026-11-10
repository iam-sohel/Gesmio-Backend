package com.gesmio.platform.identity.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "mfa_credentials")
@Getter
@Setter
@NoArgsConstructor
public class MfaCredential {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @NotNull
    @Column(name = "user_id")
    private UUID userId;

    @NotBlank
    @Column(name = "credential_type", length = 255)
    private String credentialType;
}
