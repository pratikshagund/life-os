package com.lifeos.controller;

import com.lifeos.dto.RoutineRequest;
import com.lifeos.model.Routine;
import com.lifeos.service.RoutineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/routines")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class RoutineController {

    private final RoutineService routineService;

    @PostMapping
    public ResponseEntity<Routine> createRoutine(@RequestBody RoutineRequest request) {
        return ResponseEntity.ok(routineService.createRoutine(request));
    }

    @GetMapping
    public ResponseEntity<List<Routine>> getAllRoutines() {
        return ResponseEntity.ok(routineService.getAllRoutines());
    }

    @GetMapping("/active")
    public ResponseEntity<List<Routine>> getActiveRoutines(@RequestParam String dayOfWeek) {
        return ResponseEntity.ok(routineService.getActiveRoutines(dayOfWeek));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoutine(@PathVariable UUID id) {
        routineService.deleteRoutine(id);
        return ResponseEntity.noContent().build();
    }
}
