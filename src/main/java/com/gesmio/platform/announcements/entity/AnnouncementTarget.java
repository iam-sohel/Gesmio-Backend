package com.gesmio.platform.announcements.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "announcement_targets")
@Getter
@Setter
@NoArgsConstructor
public class AnnouncementTarget {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @NotNull
    @Column(name = "announcement_id")
    private UUID announcementId;

    @NotNull
    @Column(name = "department_id")
    private UUID departmentId;
}
