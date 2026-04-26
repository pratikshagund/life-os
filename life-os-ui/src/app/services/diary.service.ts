import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { DiaryEntryRequest, DiaryEntryResponse } from '../models/diary-entry';
import { API_ENDPOINTS } from '../constants/api.constants';

@Injectable({
  providedIn: 'root'
})
export class DiaryService {
  private apiUrl = `${API_ENDPOINTS.BASE_URL}${API_ENDPOINTS.DIARY}`;

  constructor(private http: HttpClient) { }

  createEntry(entry: DiaryEntryRequest): Observable<DiaryEntryResponse> {
    return this.http.post<DiaryEntryResponse>(this.apiUrl, entry);
  }

  getEntries(): Observable<DiaryEntryResponse[]> {
    return this.http.get<DiaryEntryResponse[]>(this.apiUrl);
  }
}
