import { Component, inject, Injectable } from '@angular/core';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzModalRef, NzModalService } from 'ng-zorro-antd/modal';

@Component({
  selector: 'completion-confirmation',
  standalone: true,
  imports: [NzButtonModule],
  template: `
    <div class="completion-confirmation">
      <p class="completion-confirmation__message">
        Are you sure you want to complete this task?
      </p>

      <div class="completion-confirmation__actions">
        <button
          nz-button
          type="button"
          (click)="cancel()"
        >
          Cancel
        </button>

        <button
          nz-button
          nzType="primary"
          type="button"
          (click)="confirm()"
        >
          Confirm
        </button>
      </div>
    </div>
  `,
  styles: [`
    .completion-confirmation {
      display: flex;
      flex-direction: column;
      gap: 24px;
    }

    .completion-confirmation__message {
      margin: 0;
      font-size: 14px;
    }

    .completion-confirmation__actions {
      display: flex;
      justify-content: flex-end;
      gap: 8px;
    }
  `],
})
class CompletionConfirmationComponent {

  private readonly modalRef = inject(NzModalRef);

  protected cancel(): void {
    this.modalRef.close(false);
  }

  protected confirm(): void {
    this.modalRef.close(true);
  }
}
