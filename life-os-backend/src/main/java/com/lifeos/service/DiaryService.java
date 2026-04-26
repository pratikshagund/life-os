package com.lifeos.service;

import com.lifeos.dto.DiaryEntryRequest;
import com.lifeos.model.DiaryEntry;
import com.lifeos.model.User;
import com.lifeos.repository.DiaryEntryRepository;
import com.lifeos.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryEntryRepository diaryEntryRepository;
    private final UserRepository userRepository; 

    @Transactional
    public DiaryEntry createDiaryEntry(DiaryEntryRequest request) {
        // Mocking user retrieval for now since auth isn't built
        // If the database is completely fresh, create a mock user so we don't get a "User not found" error
        User user;
        if (userRepository.count() == 0) {
            User newUser = User.builder()
                    .email("test@lifeos.com")
                    .passwordHash("mocked-hash")
                    .build();
            user = userRepository.save(newUser);
        } else {
            user = userRepository.findAll().get(0); // Grab the first user
        }

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
        return diaryEntryRepository.findAllByOrderByEntryDateDesc();
    }
}
