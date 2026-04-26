package com.lifeos.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class GoalResponse {
    private UUID id;
    private String title;
    private String description;
    private LocalDate targetDate;
    private String status;
    private double progressPercentage;
    private int totalTasks;
    private int completedTasks;
    private LocalDateTime createdAt;
}
