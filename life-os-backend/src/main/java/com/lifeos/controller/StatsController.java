package com.lifeos.controller;

import com.lifeos.dto.WeeklyStatsResponse;
import com.lifeos.service.ReflectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stats")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class StatsController {

    private final ReflectionService reflectionService;

    @GetMapping("/weekly")
    public ResponseEntity<WeeklyStatsResponse> getWeeklyStats() {
        return ResponseEntity.ok(reflectionService.getWeeklyStats());
    }
}
