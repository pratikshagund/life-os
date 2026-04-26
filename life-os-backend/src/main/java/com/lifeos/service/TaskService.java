package com.lifeos.service;

import com.lifeos.dto.TaskRequest;
import com.lifeos.dto.TaskResponse;
import com.lifeos.model.Task;
import com.lifeos.model.User;
import com.lifeos.repository.GoalRepository;
import com.lifeos.repository.TaskRepository;
import com.lifeos.repository.UserRepository;
import com.lifeos.model.Goal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final GoalRepository goalRepository;

    @Transactional
    public TaskResponse createTask(TaskRequest request) {
        User user;
        if (userRepository.count() == 0) {
            User newUser = User.builder()
                    .email("test@lifeos.com")
                    .passwordHash("mocked-hash")
                    .build();
            user = userRepository.save(newUser);
        } else {
            user = userRepository.findAll().get(0);
        }

        Task task = Task.builder()
                .user(user)
                .title(request.getTitle())
                .description(request.getDescription())
                .status(request.getStatus() != null ? request.getStatus() : Task.TaskStatus.PENDING)
                .priority(request.getPriority() != null ? request.getPriority() : Task.TaskPriority.MEDIUM)
                .isRecurringBlock(request.isRecurringBlock())
                .scheduledStart(request.getScheduledStart())
                .scheduledEnd(request.getScheduledEnd())
                .estimatedMinutes(request.getEstimatedMinutes())
                .tags(request.getTags())
                .build();

        if (request.getGoalId() != null) {
            Goal goal = goalRepository.findById(request.getGoalId())
                    .orElseThrow(() -> new RuntimeException("Goal not found"));
            task.setGoal(goal);
        }

        task = taskRepository.save(task);
        return mapToResponse(task);
    }

    public List<TaskResponse> getAllTasks() {
        User user = userRepository.findAll().get(0);
        return taskRepository.findByUserOrderByScheduledStartAsc(user).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public TaskResponse updateTaskStatus(UUID taskId, Task.TaskStatus status) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        task.setStatus(status);
        task = taskRepository.save(task);
        return mapToResponse(task);
    }

    private TaskResponse mapToResponse(Task task) {
        return TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .priority(task.getPriority())
                .isRecurringBlock(task.isRecurringBlock())
                .scheduledStart(task.getScheduledStart())
                .scheduledEnd(task.getScheduledEnd())
                .estimatedMinutes(task.getEstimatedMinutes())
                .tags(task.getTags())
                .goalId(task.getGoal() != null ? task.getGoal().getId() : null)
                .build();
    }
}
