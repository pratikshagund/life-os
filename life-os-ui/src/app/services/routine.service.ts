import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Routine, RoutineRequest } from '../models/routine';
import { API_ENDPOINTS } from '../constants/api.constants';

@Injectable({
  providedIn: 'root'
})
export class RoutineService {
  private apiUrl = `${API_ENDPOINTS.BASE_URL}${API_ENDPOINTS.ROUTINES}`;

  constructor(private http: HttpClient) { }

  createRoutine(routine: RoutineRequest): Observable<Routine> {
    return this.http.post<Routine>(this.apiUrl, routine);
  }

  getRoutines(): Observable<Routine[]> {
    return this.http.get<Routine[]>(this.apiUrl);
  }

  getActiveRoutines(dayOfWeek: string): Observable<Routine[]> {
    return this.http.get<Routine[]>(`${this.apiUrl}/active`, { params: { dayOfWeek } });
  }

  deleteRoutine(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
