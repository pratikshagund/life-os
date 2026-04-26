export interface DiaryEntryRequest {
  userId: string;
  content: string;
  mood: string;
  tags: string;
  entryDate: string;
}

export interface DiaryEntryResponse {
  id: string;
  content: string;
  mood: string;
  tags: string;
  aiProductivityScore: number;
  aiInsights: string;
  entryDate: string;
}
