import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { TaskService } from '../../services/task.service';
import { TaskRequest, TaskResponse, TaskStatus, TaskPriority } from '../../models/task';
import { Routine } from '../../models/routine';
import { CardComponent } from '../ui/card/card.component';
import { RoutineService } from '../../services/routine.service';
import { TASK_PRIORITY_OPTIONS, TASK_STATUS_COLUMNS } from '../../constants/ui.constants';

@Component({
  selector: 'app-planner',
  standalone: true,
  imports: [CommonModule, FormsModule, CardComponent],
  templateUrl: './planner.component.html',
  styleUrls: ['./planner.component.css']
})
export class PlannerComponent implements OnInit {
  tasks: TaskResponse[] = [];
  routines: Routine[] = [];
  newTask: TaskRequest = {
    title: '',
    description: '',
    priority: TaskPriority.MEDIUM,
    status: TaskStatus.PENDING,
    isRecurringBlock: false,
    estimatedMinutes: 30
  };

  TaskStatus = TaskStatus;
  TaskPriority = TaskPriority;
  taskPriorityOptions = TASK_PRIORITY_OPTIONS;
  taskStatusColumns = TASK_STATUS_COLUMNS;

  isLoading = false;
  showAddModal = false;
  errorMessage = '';

  constructor(
    private taskService: TaskService,
    private routineService: RoutineService
  ) {}

  getPriorityOption(priority: TaskPriority) {
    return this.taskPriorityOptions.find(o => o.value === priority) || this.taskPriorityOptions[1];
  }

  getColumn(status: TaskStatus) {
    return this.taskStatusColumns.find(c => c.id === status);
  }

  canTransitionTo(task: TaskResponse, targetStatus: TaskStatus): boolean {
    const column = this.getColumn(task.status);
    return column?.allowedTransitions.includes(targetStatus) || false;
  }

  isOverlapping(start: string, end: string, excludeId?: string): boolean {
    const newStart = new Date(start);
    const newEnd = new Date(end);
    const newStartTime = newStart.getTime();
    const newEndTime = newEnd.getTime();

    // Check against existing tasks
    const taskOverlap = this.tasks.some(task => {
      if (task.id === excludeId || !task.scheduledStart || !task.scheduledEnd) return false;
      const existingStart = new Date(task.scheduledStart).getTime();
      const existingEnd = new Date(task.scheduledEnd).getTime();

      return (newStartTime < existingEnd && newEndTime > existingStart);
    });

    if (taskOverlap) return true;

    // Check against active routines (strict blocking)
    return this.routines.some(routine => {
      if (!routine.isBlocking) return false;
      
      const rStart = new Date(newStart);
      const [sh, sm] = routine.startTime.split(':');
      rStart.setHours(parseInt(sh), parseInt(sm), 0, 0);

      const rEnd = new Date(newStart);
      const [eh, em] = routine.endTime.split(':');
      rEnd.setHours(parseInt(eh), parseInt(em), 0, 0);

      return (newStartTime < rEnd.getTime() && newEndTime > rStart.getTime());
    });
  }

  ngOnInit() {
    this.fetchTasks();
    this.fetchActiveRoutines();
  }

  fetchActiveRoutines() {
    const today = new Date().toLocaleDateString('en-US', { weekday: 'long' }).toUpperCase();
    this.routineService.getActiveRoutines(today).subscribe({
      next: (data) => this.routines = data,
      error: (err) => console.error('Failed to fetch routines', err)
    });
  }

  fetchTasks() {
    this.isLoading = true;
    this.taskService.getTasks().subscribe({
      next: (tasks) => {
        this.tasks = tasks;
        this.isLoading = false;
      },
      error: (err) => {
        console.error('Failed to fetch tasks', err);
        this.isLoading = false;
      }
    });
  }

  createTask() {
    if (!this.newTask.title) return;

    if (this.newTask.scheduledStart && this.newTask.scheduledEnd) {
      if (new Date(this.newTask.scheduledStart) >= new Date(this.newTask.scheduledEnd)) {
        this.errorMessage = 'Start time must be before end time.';
        return;
      }

      if (this.isOverlapping(this.newTask.scheduledStart, this.newTask.scheduledEnd)) {
        this.errorMessage = 'This task overlaps with an existing scheduled task.';
        return;
      }
    }
    
    this.isLoading = true;
    this.errorMessage = '';
    this.taskService.createTask(this.newTask).subscribe({
      next: (task) => {
        this.tasks.push(task);
        this.resetNewTask();
        this.showAddModal = false;
        this.isLoading = false;
      },
      error: (err) => {
        console.error('Failed to create task', err);
        this.errorMessage = 'Failed to create task. Please check your connection.';
        this.isLoading = false;
      }
    });
  }

  updateStatus(task: TaskResponse, status: TaskStatus) {
    this.taskService.updateTaskStatus(task.id, status).subscribe({
      next: (updatedTask) => {
        const index = this.tasks.findIndex(t => t.id === updatedTask.id);
        if (index !== -1) {
          this.tasks[index] = updatedTask;
        }
      },
      error: (err) => console.error('Failed to update task status', err)
    });
  }

  getTasksByStatus(status: TaskStatus): TaskResponse[] {
    return this.tasks.filter(t => t.status === status);
  }

  resetNewTask() {
    this.newTask = {
      title: '',
      description: '',
      priority: TaskPriority.MEDIUM,
      status: TaskStatus.PENDING,
      isRecurringBlock: false,
      estimatedMinutes: 30
    };
  }
}
