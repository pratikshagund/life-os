export interface Routine {
  id: string;
  name: string;
  startTime: string; // ISO time format "HH:mm:ss"
  endTime: string;
  daysOfWeek: string; // "MONDAY,TUESDAY..."
  isBlocking: boolean;
}

export interface RoutineRequest {
  name: string;
  startTime: string;
  endTime: string;
  daysOfWeek: string[];
  isBlocking: boolean;
}
