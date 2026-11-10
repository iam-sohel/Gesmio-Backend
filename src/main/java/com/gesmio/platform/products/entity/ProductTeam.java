package com.gesmio.platform.products.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product_teams")
@Getter
@Setter
@NoArgsConstructor
public class ProductTeam {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @NotNull
    @Column(name = "product_id")
    private UUID productId;

    @NotNull
    @Column(name = "employee_id")
    private UUID employeeId;

    @Column(name = "team_role", length = 255)
    private String teamRole;

    @Column(name = "joined_at")
    private LocalDate joinedAt;

    @Column(name = "left_at")
    private LocalDate leftAt;
}
