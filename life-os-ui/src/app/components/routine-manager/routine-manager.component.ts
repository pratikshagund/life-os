import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RoutineService } from '../../services/routine.service';
import { Routine, RoutineRequest } from '../../models/routine';
import { CardComponent } from '../ui/card/card.component';

@Component({
  selector: 'app-routine-manager',
  standalone: true,
  imports: [CommonModule, FormsModule, CardComponent],
  templateUrl: './routine-manager.component.html',
  styleUrls: ['./routine-manager.component.css']
})
export class RoutineManagerComponent implements OnInit {
  routines: Routine[] = [];
  newRoutine: RoutineRequest = {
    name: '',
    startTime: '',
    endTime: '',
    daysOfWeek: [],
    isBlocking: true
  };

  days = [
    { label: 'Mon', value: 'MONDAY' },
    { label: 'Tue', value: 'TUESDAY' },
    { label: 'Wed', value: 'WEDNESDAY' },
    { label: 'Thu', value: 'THURSDAY' },
    { label: 'Fri', value: 'FRIDAY' },
    { label: 'Sat', value: 'SATURDAY' },
    { label: 'Sun', value: 'SUNDAY' }
  ];

  isLoading = false;
  showAddModal = false;
  errorMessage = '';

  constructor(private routineService: RoutineService) {}

  ngOnInit() {
    this.fetchRoutines();
  }

  fetchRoutines() {
    this.isLoading = true;
    this.routineService.getRoutines().subscribe({
      next: (data) => {
        this.routines = data;
        this.isLoading = false;
      },
      error: (err) => {
        console.error('Failed to fetch routines', err);
        this.isLoading = false;
      }
    });
  }

  toggleDay(day: string) {
    const index = this.newRoutine.daysOfWeek.indexOf(day);
    if (index === -1) {
      this.newRoutine.daysOfWeek.push(day);
    } else {
      this.newRoutine.daysOfWeek.splice(index, 1);
    }
  }

  isDaySelected(day: string): boolean {
    return this.newRoutine.daysOfWeek.includes(day);
  }

  createRoutine() {
    if (!this.newRoutine.name || !this.newRoutine.startTime || !this.newRoutine.endTime || this.newRoutine.daysOfWeek.length === 0) {
      return;
    }

    this.isLoading = true;
    this.routineService.createRoutine(this.newRoutine).subscribe({
      next: (routine) => {
        this.routines.push(routine);
        this.resetNewRoutine();
        this.showAddModal = false;
        this.isLoading = false;
      },
      error: (err) => {
        console.error('Failed to create routine', err);
        this.errorMessage = err.error?.error || 'An unexpected error occurred. Please try again.';
        this.isLoading = false;
      }
    });
  }

  deleteRoutine(id: string) {
    if (confirm('Are you sure you want to delete this routine?')) {
      this.routineService.deleteRoutine(id).subscribe({
        next: () => {
          this.routines = this.routines.filter(r => r.id !== id);
        },
        error: (err) => console.error('Failed to delete routine', err)
      });
    }
  }

  resetNewRoutine() {
    this.newRoutine = {
      name: '',
      startTime: '',
      endTime: '',
      daysOfWeek: [],
      isBlocking: true
    };
    this.errorMessage = '';
  }
}
