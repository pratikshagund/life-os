package com.lifeos.controller;

import com.lifeos.dto.TaskRequest;
import com.lifeos.dto.TaskResponse;
import com.lifeos.model.Task;
import com.lifeos.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@RequestBody TaskRequest request) {
        return new ResponseEntity<>(taskService.createTask(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TaskResponse>> getAllTasks() {
        return new ResponseEntity<>(taskService.getAllTasks(), HttpStatus.OK);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<TaskResponse> updateTaskStatus(
            @PathVariable UUID id,
            @RequestParam Task.TaskStatus status) {
        return new ResponseEntity<>(taskService.updateTaskStatus(id, status), HttpStatus.OK);
    }
}
