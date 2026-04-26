import { Routes } from '@angular/router';
import { DiaryComponent } from './components/diary/diary.component';
import { PlannerComponent } from './components/planner/planner.component';

export const routes: Routes = [
  { path: 'planner', component: PlannerComponent },
  { path: 'diary', component: DiaryComponent },
  { path: '', redirectTo: '/planner', pathMatch: 'full' }
];
