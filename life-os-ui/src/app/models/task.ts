export enum TaskStatus {
  PENDING = 'PENDING',
  IN_PROGRESS = 'IN_PROGRESS',
  COMPLETED = 'COMPLETED',
  MISSED = 'MISSED'
}

export enum TaskPriority {
  LOW = 'LOW',
  MEDIUM = 'MEDIUM',
  HIGH = 'HIGH'
}

export interface TaskRequest {
  title: string;
  description?: string;
  status?: TaskStatus;
  priority?: TaskPriority;
  isRecurringBlock: boolean;
  scheduledStart?: string;
  scheduledEnd?: string;
  estimatedMinutes?: number;
  tags?: string;
}

export interface TaskResponse {
  id: string;
  title: string;
  description: string;
  status: TaskStatus;
  priority: TaskPriority;
  isRecurringBlock: boolean;
  scheduledStart: string;
  scheduledEnd: string;
  estimatedMinutes: number;
  tags: string;
}
