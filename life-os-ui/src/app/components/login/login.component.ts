import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { CardComponent } from '../ui/card/card.component';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, CardComponent],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  isLogin = true;
  credentials = {
    email: '',
    password: ''
  };
  isLoading = false;
  error = '';

  constructor(private authService: AuthService, private router: Router) {}

  onSubmit() {
    this.isLoading = true;
    this.error = '';

    const action = this.isLogin 
      ? this.authService.login(this.credentials) 
      : this.authService.register(this.credentials);

    action.subscribe({
      next: () => {
        this.router.navigate(['/planner']);
      },
      error: (err) => {
        console.error('Auth failed', err);
        this.error = 'Invalid credentials or email already taken.';
        this.isLoading = false;
      }
    });
  }

  toggleMode() {
    this.isLogin = !this.isLogin;
    this.error = '';
  }
}
