import { Routes } from '@angular/router';
import { DiaryComponent } from './components/diary/diary.component';
import { PlannerComponent } from './components/planner/planner.component';
import { RoutineManagerComponent } from './components/routine-manager/routine-manager.component';

export const routes: Routes = [
  { path: 'planner', component: PlannerComponent },
  { path: 'diary', component: DiaryComponent },
  { path: 'routines', component: RoutineManagerComponent },
  { path: '', redirectTo: '/planner', pathMatch: 'full' }
];
