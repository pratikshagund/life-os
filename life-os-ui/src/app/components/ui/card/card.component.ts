import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-card',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div [ngClass]="getClasses()">
      <ng-content></ng-content>
    </div>
  `,
  styles: ``
})
export class CardComponent {
  @Input() className: string = '';
  @Input() padding: 'none' | 'small' | 'normal' | 'large' = 'normal';

  getClasses(): string {
    const baseClasses = 'bg-surface-light dark:bg-surface-dark rounded-xl border border-slate-200 dark:border-white/5 shadow-[0_10px_40px_-10px_rgba(0,0,0,0.05)]';
    
    const paddingClasses = {
      'none': '',
      'small': 'p-4',
      'normal': 'p-6',
      'large': 'p-8'
    };

    return `${baseClasses} ${paddingClasses[this.padding]} ${this.className}`;
  }
}
