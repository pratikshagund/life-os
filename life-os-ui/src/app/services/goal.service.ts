import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Goal, GoalRequest } from '../models/goal';
import { API_ENDPOINTS } from '../constants/api.constants';

@Injectable({
  providedIn: 'root'
})
export class GoalService {
  private apiUrl = `${API_ENDPOINTS.BASE_URL}${API_ENDPOINTS.GOALS}`;

  constructor(private http: HttpClient) { }

  createGoal(goal: GoalRequest): Observable<Goal> {
    return this.http.post<Goal>(this.apiUrl, goal);
  }

  getGoals(): Observable<Goal[]> {
    return this.http.get<Goal[]>(this.apiUrl);
  }

  deleteGoal(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
