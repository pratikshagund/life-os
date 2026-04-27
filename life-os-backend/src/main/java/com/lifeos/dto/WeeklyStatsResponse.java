package com.lifeos.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class WeeklyStatsResponse {
    private List<DailyStat> dailyStats;
    private double overallProductivityScore;
    private int totalTasksCompleted;
    private int totalTasksPlanned;
    private String weeklyInsight;

    @Data
    @Builder
    public static class DailyStat {
        private LocalDate date;
        private int productivityScore;
        private int tasksPlanned;
        private int tasksCompleted;
        private String mood;
    }
}
