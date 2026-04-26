import { TaskPriority, TaskStatus } from "../models/task";

export const TASK_PRIORITY_OPTIONS = [
  { value: TaskPriority.LOW, label: 'Low', class: 'bg-blue-500/10 text-blue-400' },
  { value: TaskPriority.MEDIUM, label: 'Medium', class: 'bg-yellow-500/10 text-yellow-400' },
  { value: TaskPriority.HIGH, label: 'High', class: 'bg-red-500/10 text-red-400' }
];

export const TASK_STATUS_COLUMNS = [
  { 
    id: TaskStatus.PENDING, 
    label: 'To Do', 
    colorClass: 'bg-primary/60', 
    allowedTransitions: [TaskStatus.IN_PROGRESS]
  },
  { 
    id: TaskStatus.IN_PROGRESS, 
    label: 'In Progress', 
    colorClass: 'bg-yellow-500/60', 
    allowedTransitions: [TaskStatus.PENDING, TaskStatus.COMPLETED]
  },
  { 
    id: TaskStatus.COMPLETED, 
    label: 'Completed', 
    colorClass: 'bg-green-500/60', 
    allowedTransitions: [TaskStatus.PENDING, TaskStatus.IN_PROGRESS]
  }
];

export const MOOD_OPTIONS = [
  { value: 'Happy', label: 'Happy' },
  { value: 'Productive', label: 'Productive' },
  { value: 'Neutral', label: 'Neutral' },
  { value: 'Stressed', label: 'Stressed' },
  { value: 'Tired', label: 'Tired' }
];
