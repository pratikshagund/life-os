package com.lifeos.service;

import com.lifeos.dto.RoutineRequest;
import com.lifeos.model.Routine;
import com.lifeos.model.User;
import com.lifeos.repository.RoutineRepository;
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
public class RoutineService {

    private final RoutineRepository routineRepository;

    @Transactional
    public Routine createRoutine(RoutineRequest request) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (request.getEndTime().isBefore(request.getStartTime())) {
            throw new IllegalArgumentException("End time must be after start time");
        }

        // Overlap Validation
        List<Routine> existingRoutines = routineRepository.findByUserId(user.getId());
        for (String day : request.getDaysOfWeek()) {
            String dayUpper = day.toUpperCase();
            boolean hasOverlap = existingRoutines.stream()
                    .filter(r -> r.getDaysOfWeek().contains(dayUpper))
                    .anyMatch(r -> (request.getStartTime().isBefore(r.getEndTime()) && 
                                    request.getEndTime().isAfter(r.getStartTime())));
            
            if (hasOverlap) {
                throw new IllegalArgumentException("Conflict: This routine overlaps with an existing one on " + day);
            }
        }

        Routine routine = Routine.builder()
                .user(user)
                .name(request.getName())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .daysOfWeek(String.join(",", request.getDaysOfWeek()))
                .isBlocking(request.isBlocking())
                .build();

        return routineRepository.save(routine);
    }

    public List<Routine> getAllRoutines() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return routineRepository.findByUserId(user.getId());
    }

    public List<Routine> getActiveRoutines(String dayOfWeek) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return routineRepository.findActiveRoutines(user.getId(), dayOfWeek.toUpperCase());
    }

    @Transactional
    public void deleteRoutine(UUID id) {
        routineRepository.deleteById(id);
    }

}
