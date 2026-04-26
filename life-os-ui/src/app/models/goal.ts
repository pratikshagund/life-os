export interface Goal {
  id: string;
  title: string;
  description: string;
  targetDate: string;
  status: string;
  progressPercentage: number;
  totalTasks: number;
  completedTasks: number;
  createdAt: string;
}

export interface GoalRequest {
  title: string;
  description: string;
  targetDate: string;
}
