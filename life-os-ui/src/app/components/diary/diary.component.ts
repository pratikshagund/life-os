import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { DiaryService } from '../../services/diary.service';
import { DiaryEntryRequest, DiaryEntryResponse } from '../../models/diary-entry';
import { MOOD_OPTIONS } from '../../constants/ui.constants';

@Component({
  selector: 'app-diary',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './diary.component.html',
  styleUrls: ['./diary.component.css']
})
export class DiaryComponent implements OnInit {
  moodOptions = MOOD_OPTIONS;
  // Using a mock static UUID until Auth is implemented
  mockUserId = '123e4567-e89b-12d3-a456-426614174000'; 
  
  entry: DiaryEntryRequest = {
    userId: this.mockUserId,
    content: '',
    mood: 'Neutral',
    tags: '',
    entryDate: new Date().toISOString().split('T')[0]
  };

  savedEntry: DiaryEntryResponse | null = null;
  isLoading = false;
  errorMessage = '';
  history: DiaryEntryResponse[] = [];

  constructor(private diaryService: DiaryService) {}

  ngOnInit() {
    this.fetchHistory();
  }

  fetchHistory() {
    this.diaryService.getEntries().subscribe({
      next: (entries) => {
        this.history = entries;
      },
      error: (err) => {
        console.error('Failed to load history', err);
      }
    });
  }

  submitDiary() {
    this.isLoading = true;
    this.errorMessage = '';
    
    this.diaryService.createEntry(this.entry).subscribe({
      next: (response) => {
        this.savedEntry = response;
        this.history.unshift(response);
        this.isLoading = false;
        // Reset form
        this.entry.content = '';
        this.entry.tags = '';
      },
      error: (err) => {
        this.errorMessage = 'Failed to save diary entry. Is the Spring Boot backend running?';
        console.error(err);
        this.isLoading = false;
      }
    });
  }
}
