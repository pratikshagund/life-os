package com.lifeos.service;

import com.lifeos.dto.DiaryEntryRequest;
import com.lifeos.model.DiaryEntry;
import com.lifeos.model.User;
import com.lifeos.repository.DiaryEntryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryEntryRepository diaryEntryRepository;

    @Transactional
    public DiaryEntry createDiaryEntry(DiaryEntryRequest request) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        DiaryEntry entry = DiaryEntry.builder()
                .user(user)
                .content(request.getContent())
                .mood(request.getMood())
                .tags(request.getTags())
                .entryDate(request.getEntryDate())
                // Mock AI insights until we connect OpenAI
                .aiProductivityScore(85)
                .aiInsights("Great focus today, keep it up!")
                .build();

        return diaryEntryRepository.save(entry);
    }

    public java.util.List<DiaryEntry> getAllDiaryEntries() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return diaryEntryRepository.findByUserIdOrderByEntryDateDesc(user.getId());
    }
}
