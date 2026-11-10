package com.gesmio.platform.products.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
public class Product {

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

    @Column(name = "description")
    private String description;

    @NotBlank
    @Column(name = "status", length = 255)
    private String status = "ACTIVE";

    @Column(name = "owner_employee_id")
    private UUID ownerEmployeeId;

    @Column(name = "created_by")
    private UUID createdBy;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;
}
