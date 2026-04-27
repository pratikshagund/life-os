package com.lifeos.service;

import com.lifeos.model.Routine;
import com.lifeos.model.Task;
import com.lifeos.model.User;
import com.lifeos.repository.RoutineRepository;
import com.lifeos.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SchedulingService {

    private final TaskRepository taskRepository;
    private final RoutineRepository routineRepository;

    @Transactional
    public List<Task> autoSchedule(LocalDate date) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        // 1. Get blocking routines for the day
        String dayOfWeek = date.getDayOfWeek().name();
        List<Routine> blockingRoutines = routineRepository.findActiveRoutines(user.getId(), dayOfWeek)
                .stream().filter(Routine::isBlocking).toList();
        
        // 2. Get already scheduled tasks for the day
        List<Task> scheduledTasks = taskRepository.findByUserOrderByScheduledStartAsc(user).stream()
                .filter(t -> t.getScheduledStart() != null && t.getScheduledStart().toLocalDate().equals(date))
                .toList();

        // 3. Get pending tasks to schedule (ordered by priority)
        List<Task> pendingTasks = taskRepository.findByUserAndStatusOrderByPriorityDesc(user, Task.TaskStatus.PENDING)
                .stream().filter(t -> t.getScheduledStart() == null).toList();

        LocalDateTime cursor = date.atTime(7, 0); // Day starts at 7 AM
        LocalDateTime endOfDay = date.atTime(23, 0); // Day ends at 11 PM
        
        List<Task> newlyScheduled = new ArrayList<>();

        for (Task task : pendingTasks) {
            int duration = task.getEstimatedMinutes() != null ? task.getEstimatedMinutes() : 30;
            boolean placed = false;

            while (cursor.plusMinutes(duration).isBefore(endOfDay)) {
                LocalDateTime potentialStart = cursor;
                LocalDateTime potentialEnd = cursor.plusMinutes(duration);
                
                if (!hasConflict(potentialStart, potentialEnd, blockingRoutines, scheduledTasks, newlyScheduled)) {
                    task.setScheduledStart(potentialStart);
                    task.setScheduledEnd(potentialEnd);
                    newlyScheduled.add(taskRepository.save(task));
                    cursor = potentialEnd; // Move cursor to the end of this task
                    placed = true;
                    break;
                }
                
                cursor = cursor.plusMinutes(15); // Skip 15 mins and try again
            }
            
            if (!placed) {
                // If we couldn't place it today, reset cursor for next task just in case 
                // but usually it means the day is full.
                cursor = date.atTime(7, 0); 
            }
        }
        
        return newlyScheduled;
    }

    private boolean hasConflict(LocalDateTime start, LocalDateTime end, 
                                List<Routine> routines, List<Task> tasks, List<Task> newlyScheduled) {
        
        // Check routines
        for (Routine r : routines) {
            LocalDateTime rStart = start.toLocalDate().atTime(r.getStartTime());
            LocalDateTime rEnd = start.toLocalDate().atTime(r.getEndTime());
            if (start.isBefore(rEnd) && end.isAfter(rStart)) return true;
        }

        // Check existing tasks
        for (Task t : tasks) {
            if (start.isBefore(t.getScheduledEnd()) && end.isAfter(t.getScheduledStart())) return true;
        }

        // Check newly scheduled tasks in this batch
        for (Task t : newlyScheduled) {
            if (start.isBefore(t.getScheduledEnd()) && end.isAfter(t.getScheduledStart())) return true;
        }

        return false;
    }
}
