import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { API_ENDPOINTS } from '../constants/api.constants';

export interface AuthResponse {
  token: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = `${API_ENDPOINTS.BASE_URL}/auth`;

  constructor(private http: HttpClient) {}

  login(credentials: any): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.apiUrl}/login`, credentials).pipe(
      tap(response => {
        localStorage.setItem('life_os_token', response.token);
      })
    );
  }

  register(userData: any): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.apiUrl}/register`, userData).pipe(
      tap(response => {
        localStorage.setItem('life_os_token', response.token);
      })
    );
  }

  logout() {
    localStorage.removeItem('life_os_token');
    window.location.href = '/login';
  }

  getToken(): string | null {
    return localStorage.getItem('life_os_token');
  }

  isAuthenticated(): boolean {
    return !!this.getToken();
  }
}
