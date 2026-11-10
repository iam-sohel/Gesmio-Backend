package com.gesmio.platform.recruitment.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "interview_feedback")
@Getter
@Setter
@NoArgsConstructor
public class InterviewFeedback {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @NotNull
    @Column(name = "interview_id")
    private UUID interviewId;

    @NotNull
    @Column(name = "interviewer_user_id")
    private UUID interviewerUserId;

    @Column(name = "feedback")
    private String feedback;

    @Column(name = "recommendation", length = 255)
    private String recommendation;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;
}
