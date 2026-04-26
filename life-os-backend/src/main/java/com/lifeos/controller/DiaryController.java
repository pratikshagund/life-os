package com.lifeos.controller;

import com.lifeos.dto.DiaryEntryRequest;
import com.lifeos.model.DiaryEntry;
import com.lifeos.service.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/diary")
@CrossOrigin(origins = "http://localhost:4200") // Allow Angular frontend to connect
@RequiredArgsConstructor
public class DiaryController {

    private final DiaryService diaryService;

    @PostMapping
    public ResponseEntity<DiaryEntry> createEntry(@RequestBody DiaryEntryRequest request) {
        DiaryEntry createdEntry = diaryService.createDiaryEntry(request);
        return new ResponseEntity<>(createdEntry, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<DiaryEntry>> getAllEntries() {
        List<DiaryEntry> entries = diaryService.getAllDiaryEntries();
        return new ResponseEntity<>(entries, HttpStatus.OK);
    }
}
