package com.lifeos.service;

import com.lifeos.dto.GoalRequest;
import com.lifeos.dto.GoalResponse;
import com.lifeos.model.Goal;
import com.lifeos.model.Task;
import com.lifeos.model.User;
import com.lifeos.repository.GoalRepository;
import com.lifeos.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GoalService {

    private final GoalRepository goalRepository;

    @Transactional
    public GoalResponse createGoal(GoalRequest request) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Goal goal = Goal.builder()
                .user(user)
                .title(request.getTitle())
                .description(request.getDescription())
                .targetDate(request.getTargetDate())
                .build();
        
        Goal savedGoal = goalRepository.save(goal);
        return mapToResponse(savedGoal);
    }

    public List<GoalResponse> getAllGoals() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return goalRepository.findByUserId(user.getId()).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteGoal(UUID id) {
        goalRepository.deleteById(id);
    }

    private GoalResponse mapToResponse(Goal goal) {
        List<Task> tasks = goal.getTasks() != null ? goal.getTasks() : List.of();
        int totalTasks = tasks.size();
        long completedTasks = tasks.stream()
                .filter(t -> t.getStatus() == Task.TaskStatus.COMPLETED)
                .count();

        double progress = totalTasks > 0 ? (double) completedTasks / totalTasks * 100 : 0;

        return GoalResponse.builder()
                .id(goal.getId())
                .title(goal.getTitle())
                .description(goal.getDescription())
                .targetDate(goal.getTargetDate())
                .status(goal.getStatus())
                .progressPercentage(progress)
                .totalTasks(totalTasks)
                .completedTasks((int) completedTasks)
                .createdAt(goal.getCreatedAt())
                .build();
    }

}
