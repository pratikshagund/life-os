package com.lifeos.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "diary_entries")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiaryEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    private String mood;
    
    @Column(name = "tags")
    private String tags; // Comma-separated

    private Integer aiProductivityScore;

    @Column(columnDefinition = "TEXT")
    private String aiInsights;

    @Column(nullable = false)
    private LocalDate entryDate;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
}
