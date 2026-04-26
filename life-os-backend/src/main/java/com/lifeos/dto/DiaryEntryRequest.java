package com.lifeos.dto;

import lombok.Data;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class DiaryEntryRequest {
    private UUID userId; // Will be extracted from JWT later, mocked for now
    private String content;
    private String mood;
    private String tags;
    private LocalDate entryDate;
}
