package com.lifeos.dto;

import lombok.Data;
import java.time.LocalTime;
import java.util.List;

@Data
public class RoutineRequest {
    private String name;
    private LocalTime startTime;
    private LocalTime endTime;
    private List<String> daysOfWeek;
    private boolean isBlocking;
}
