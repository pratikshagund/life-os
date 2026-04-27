import { Component, OnInit, AfterViewInit, ViewChild, ElementRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { StatsService } from '../../services/stats.service';
import { WeeklyStats } from '../../models/stats';
import { CardComponent } from '../ui/card/card.component';
import { Chart, registerables } from 'chart.js';

Chart.register(...registerables);

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, CardComponent],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  stats: WeeklyStats | null = null;
  isLoading = true;

  @ViewChild('productivityChart') productivityChart!: ElementRef;

  constructor(private statsService: StatsService) {}

  ngOnInit() {
    this.fetchStats();
  }

  fetchStats() {
    this.statsService.getWeeklyStats().subscribe({
      next: (data) => {
        this.stats = data;
        this.isLoading = false;
        setTimeout(() => this.initChart(), 0);
      },
      error: (err) => {
        console.error('Failed to fetch stats', err);
        this.isLoading = false;
      }
    });
  }

  initChart() {
    if (!this.stats || !this.productivityChart) return;

    const ctx = this.productivityChart.nativeElement.getContext('2d');
    new Chart(ctx, {
      type: 'line',
      data: {
        labels: this.stats.dailyStats.map(s => new Date(s.date).toLocaleDateString('en-US', { weekday: 'short' })),
        datasets: [{
          label: 'Productivity Score',
          data: this.stats.dailyStats.map(s => s.productivityScore),
          borderColor: '#6366f1',
          backgroundColor: 'rgba(99, 102, 241, 0.1)',
          fill: true,
          tension: 0.4,
          pointRadius: 4,
          pointBackgroundColor: '#6366f1'
        }]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
          legend: { display: false }
        },
        scales: {
          y: {
            beginAtZero: true,
            max: 100,
            grid: { color: 'rgba(255, 255, 255, 0.05)' },
            ticks: { color: 'rgba(255, 255, 255, 0.5)' }
          },
          x: {
            grid: { display: false },
            ticks: { color: 'rgba(255, 255, 255, 0.5)' }
          }
        }
      }
    });
  }
}
