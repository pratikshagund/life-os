package com.lifeos.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "goals")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Goal {
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

    private LocalDate targetDate;

    @Column(nullable = false)
    private String status;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
}
