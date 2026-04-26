import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';

type ButtonVariant = 'primary' | 'secondary' | 'ghost';

@Component({
  selector: 'app-button',
  standalone: true,
  imports: [CommonModule],
  template: `
    <button
      [ngClass]="getClasses()"
      [disabled]="disabled"
      [type]="type"
    >
      <ng-content></ng-content>
    </button>
  `,
  styles: ``
})
export class ButtonComponent {
  @Input() variant: ButtonVariant = 'primary';
  @Input() disabled: boolean = false;
  @Input() type: 'button' | 'submit' | 'reset' = 'button';
  @Input() className: string = '';

  getClasses(): string {
    const baseClasses = 'inline-flex items-center justify-center rounded-lg text-sm font-medium transition-all focus:outline-none focus:ring-2 focus:ring-primary-500/20 disabled:opacity-50 disabled:pointer-events-none px-4 py-2';
    
    const variantClasses = {
      'primary': 'bg-primary text-white hover:bg-primary-dark shadow-[0_4px_14px_0_rgba(99,102,241,0.39)]',
      'secondary': 'bg-white dark:bg-surface-dark border border-slate-200 dark:border-slate-800 text-slate-900 dark:text-slate-50 hover:bg-slate-50 dark:hover:bg-slate-800/50 shadow-sm',
      'ghost': 'bg-transparent text-slate-700 dark:text-slate-300 hover:bg-slate-100 dark:hover:bg-slate-800'
    };

    return `${baseClasses} ${variantClasses[this.variant]} ${this.className}`;
  }
}
