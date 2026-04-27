package com.lifeos.service;

import com.lifeos.dto.WeeklyStatsResponse;
import com.lifeos.model.DiaryEntry;
import com.lifeos.model.Task;
import com.lifeos.model.User;
import com.lifeos.repository.DiaryEntryRepository;
import com.lifeos.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReflectionService {

    private final TaskRepository taskRepository;
    private final DiaryEntryRepository diaryEntryRepository;

    public WeeklyStatsResponse getWeeklyStats() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        LocalDate today = LocalDate.now();
        List<WeeklyStatsResponse.DailyStat> dailyStats = new ArrayList<>();

        int totalCompleted = 0;
        int totalPlanned = 0;
        double sumProductivity = 0;

        for (int i = 6; i >= 0; i--) {
            LocalDate date = today.minusDays(i);
            
            // Get tasks for this date
            // Note: In a real app, we'd use a custom query for date range. 
            // For now, we'll filter the user's tasks.
            List<Task> allTasks = taskRepository.findByUserOrderByScheduledStartAsc(user);
            List<Task> dailyTasks = allTasks.stream()
                    .filter(t -> t.getScheduledStart() != null && t.getScheduledStart().toLocalDate().equals(date))
                    .toList();

            long completedCount = dailyTasks.stream()
                    .filter(t -> t.getStatus() == Task.TaskStatus.COMPLETED)
                    .count();
            
            int tasksPlanned = dailyTasks.size();
            int tasksCompleted = (int) completedCount;

            // Get diary entry for this date
            Optional<DiaryEntry> diaryOpt = diaryEntryRepository.findByUserIdOrderByEntryDateDesc(user.getId()).stream()
                    .filter(d -> d.getEntryDate().equals(date))
                    .findFirst();

            int score = calculateDailyScore(tasksPlanned, tasksCompleted, diaryOpt);
            String mood = diaryOpt.map(DiaryEntry::getMood).orElse("N/A");

            dailyStats.add(WeeklyStatsResponse.DailyStat.builder()
                    .date(date)
                    .productivityScore(score)
                    .tasksPlanned(tasksPlanned)
                    .tasksCompleted(tasksCompleted)
                    .mood(mood)
                    .build());

            totalCompleted += tasksCompleted;
            totalPlanned += tasksPlanned;
            sumProductivity += score;
        }

        return WeeklyStatsResponse.builder()
                .dailyStats(dailyStats)
                .overallProductivityScore(sumProductivity / 7.0)
                .totalTasksCompleted(totalCompleted)
                .totalTasksPlanned(totalPlanned)
                .weeklyInsight(generateWeeklyInsight(totalPlanned, totalCompleted))
                .build();
    }

    private int calculateDailyScore(int planned, int completed, Optional<DiaryEntry> diary) {
        if (planned == 0) return diary.isPresent() ? 50 : 0; // Baseline if no tasks but wrote diary
        
        double taskRatio = (double) completed / planned;
        int score = (int) (taskRatio * 100);
        
        // Bonus for writing diary
        if (diary.isPresent()) {
            score = Math.min(100, score + 10);
        }
        
        return score;
    }

    private String generateWeeklyInsight(int planned, int completed) {
        if (planned == 0) return "Start planning your week to see insights!";
        double ratio = (double) completed / planned;
        if (ratio > 0.8) return "Outstanding consistency! You are crushing your goals.";
        if (ratio > 0.5) return "Good progress. Try to focus on completing high-priority tasks first.";
        return "Don't get discouraged. Small steps lead to big changes. Try smaller daily targets.";
    }
}
