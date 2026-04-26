package com.lifeos.service;

import com.lifeos.dto.RoutineRequest;
import com.lifeos.model.Routine;
import com.lifeos.model.User;
import com.lifeos.repository.RoutineRepository;
import com.lifeos.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoutineService {

    private final RoutineRepository routineRepository;
    private final UserRepository userRepository;

    @Transactional
    public Routine createRoutine(RoutineRequest request) {
        User user = getMockUser();

        if (request.getEndTime().isBefore(request.getStartTime())) {
            throw new IllegalArgumentException("End time must be after start time");
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
        User user = getMockUser();
        return routineRepository.findByUserId(user.getId());
    }

    public List<Routine> getActiveRoutines(String dayOfWeek) {
        User user = getMockUser();
        return routineRepository.findActiveRoutines(user.getId(), dayOfWeek.toUpperCase());
    }

    @Transactional
    public void deleteRoutine(UUID id) {
        routineRepository.deleteById(id);
    }

    private User getMockUser() {
        if (userRepository.count() == 0) {
            User newUser = User.builder()
                    .email("test@lifeos.com")
                    .passwordHash("mocked-hash")
                    .build();
            return userRepository.save(newUser);
        }
        return userRepository.findAll().get(0);
    }
}
