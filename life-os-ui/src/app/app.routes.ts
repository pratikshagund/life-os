import { Routes } from '@angular/router';
import { DiaryComponent } from './components/diary/diary.component';
import { PlannerComponent } from './components/planner/planner.component';
import { RoutineManagerComponent } from './components/routine-manager/routine-manager.component';
import { GoalDashboardComponent } from './components/goal-dashboard/goal-dashboard.component';
import { LoginComponent } from './components/login/login.component';
import { authGuard } from './auth.guard';

import { DashboardComponent } from './components/dashboard/dashboard.component';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'dashboard', component: DashboardComponent, canActivate: [authGuard] },
  { path: 'planner', component: PlannerComponent, canActivate: [authGuard] },
  { path: 'diary', component: DiaryComponent, canActivate: [authGuard] },
  { path: 'routines', component: RoutineManagerComponent, canActivate: [authGuard] },
  { path: 'goals', component: GoalDashboardComponent, canActivate: [authGuard] },
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' }
];
