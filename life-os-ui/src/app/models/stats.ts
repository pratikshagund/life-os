export interface DailyStat {
  date: string;
  productivityScore: number;
  tasksPlanned: number;
  tasksCompleted: number;
  mood: string;
}

export interface WeeklyStats {
  dailyStats: DailyStat[];
  overallProductivityScore: number;
  totalTasksCompleted: number;
  totalTasksPlanned: number;
  weeklyInsight: string;
}
