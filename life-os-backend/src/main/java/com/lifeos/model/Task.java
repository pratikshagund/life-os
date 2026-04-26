package com.lifeos.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tasks")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskPriority priority;

    @Column(nullable = false)
    private boolean isRecurringBlock;

    private LocalDateTime scheduledStart;
    private LocalDateTime scheduledEnd;

    private Integer estimatedMinutes;

    @Column(name = "tags")
    private String tags; // Stored as comma-separated values

    public enum TaskStatus { PENDING, IN_PROGRESS, COMPLETED, MISSED }
    public enum TaskPriority { LOW, MEDIUM, HIGH }
}
