@Component({
  selector: 'completion-confirmation',
  standalone: true,
  imports: [
    NzButtonModule,
    NzIconModule,
  ],
  template: `
    <div class="completion-confirmation">
      <p>
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
  styleUrl: './completion-confirmation.component.scss',
})
export class CompletionConfirmationComponent {

  private readonly modalRef =
    inject(NzModalRef<CompletionConfirmationComponent>);

  protected cancel(): void {
    this.modalRef.close(false);
  }

  protected confirm(): void {
    this.modalRef.close(true);
  }
}
