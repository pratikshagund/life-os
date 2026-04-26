package com.lifeos.dto;

import com.lifeos.model.Task.TaskPriority;
import com.lifeos.model.Task.TaskStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class TaskResponse {
    private UUID id;
    private String title;
    private String description;
    private TaskStatus status;
    private TaskPriority priority;
    private boolean isRecurringBlock;
    private LocalDateTime scheduledStart;
    private LocalDateTime scheduledEnd;
    private Integer estimatedMinutes;
    private String tags;
    private UUID goalId;
}
