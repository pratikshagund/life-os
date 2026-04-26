import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { TaskRequest, TaskResponse, TaskStatus } from '../models/task';
import { API_ENDPOINTS } from '../constants/api.constants';

@Injectable({
  providedIn: 'root'
})
export class TaskService {
  private apiUrl = `${API_ENDPOINTS.BASE_URL}${API_ENDPOINTS.TASKS || '/api/tasks'}`;

  constructor(private http: HttpClient) { }

  createTask(task: TaskRequest): Observable<TaskResponse> {
    return this.http.post<TaskResponse>(this.apiUrl, task);
  }

  getTasks(): Observable<TaskResponse[]> {
    return this.http.get<TaskResponse[]>(this.apiUrl);
  }

  updateTaskStatus(taskId: string, status: TaskStatus): Observable<TaskResponse> {
    const params = new HttpParams().set('status', status);
    return this.http.put<TaskResponse>(`${this.apiUrl}/${taskId}/status`, null, { params });
  }
}
