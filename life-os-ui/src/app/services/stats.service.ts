import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { WeeklyStats } from '../models/stats';
import { API_ENDPOINTS } from '../constants/api.constants';

@Injectable({
  providedIn: 'root'
})
export class StatsService {
  private apiUrl = `${API_ENDPOINTS.BASE_URL}/stats`;

  constructor(private http: HttpClient) {}

  getWeeklyStats(): Observable<WeeklyStats> {
    return this.http.get<WeeklyStats>(`${this.apiUrl}/weekly`);
  }
}
