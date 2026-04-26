package com.lifeos.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class GoalRequest {
    private String title;
    private String description;
    private LocalDate targetDate;
}
