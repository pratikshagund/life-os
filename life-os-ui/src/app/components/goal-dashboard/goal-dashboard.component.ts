import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { GoalService } from '../../services/goal.service';
import { Goal, GoalRequest } from '../../models/goal';
import { CardComponent } from '../ui/card/card.component';

@Component({
  selector: 'app-goal-dashboard',
  standalone: true,
  imports: [CommonModule, FormsModule, CardComponent],
  templateUrl: './goal-dashboard.component.html',
  styleUrls: ['./goal-dashboard.component.css']
})
export class GoalDashboardComponent implements OnInit {
  goals: Goal[] = [];
  newGoal: GoalRequest = {
    title: '',
    description: '',
    targetDate: ''
  };

  isLoading = false;
  showAddModal = false;

  constructor(private goalService: GoalService) {}

  ngOnInit() {
    this.fetchGoals();
  }

  fetchGoals() {
    this.isLoading = true;
    this.goalService.getGoals().subscribe({
      next: (data) => {
        this.goals = data;
        this.isLoading = false;
      },
      error: (err) => {
        console.error('Failed to fetch goals', err);
        this.isLoading = false;
      }
    });
  }

  createGoal() {
    if (!this.newGoal.title) return;

    this.isLoading = true;
    this.goalService.createGoal(this.newGoal).subscribe({
      next: (goal) => {
        this.goals.push(goal);
        this.resetNewGoal();
        this.showAddModal = false;
        this.isLoading = false;
      },
      error: (err) => {
        console.error('Failed to create goal', err);
        this.isLoading = false;
      }
    });
  }

  deleteGoal(id: string) {
    if (confirm('Are you sure you want to delete this goal? This will also delete associated tasks.')) {
      this.goalService.deleteGoal(id).subscribe({
        next: () => {
          this.goals = this.goals.filter(g => g.id !== id);
        },
        error: (err) => console.error('Failed to delete goal', err)
      });
    }
  }

  resetNewGoal() {
    this.newGoal = {
      title: '',
      description: '',
      targetDate: ''
    };
  }
}
