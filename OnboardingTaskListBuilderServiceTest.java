type SaveNotification = {
  type: 'success' | 'error';
  title: string;
  message: string;
};

export class ClientProfilingComponent {

  protected readonly saveNotification =
    signal<SaveNotification | null>(null);

  private notificationTimeout?: ReturnType<typeof setTimeout>;

  private showSaveNotification(
    type: 'success' | 'error',
    title: string,
    message: string
  ): void {

    if (this.notificationTimeout) {
      clearTimeout(this.notificationTimeout);
    }

    this.saveNotification.set({
      type,
      title,
      message
    });

    this.notificationTimeout = setTimeout(() => {
      this.saveNotification.set(null);
    }, 3000);
  }

  // ...
}



protected saveChangeClientInformation(): void {
  const changeClientInformation =
    this.pendingChangeClientInformation();

  if (!changeClientInformation) {
    return;
  }

  const currentCase = this.currentCase();
  const taskId = this.currentTask().userTaskIdentifier;

  const payload =
    this.changeService.cleanForSave(changeClientInformation);

  this.saving.set(true);

  this.#caseService
    .updateChangeClientInformation(
      currentCase.caseBusinessIdentifier,
      payload,
      taskId
    )
    .pipe(
      finalize(() => this.saving.set(false))
    )
    .subscribe({
      next: () => {
        console.log('ChangeClientInformation saved successfully');

        this.showSaveNotification(
          'success',
          'Changes saved',
          'Client information has been saved successfully.'
        );

        // Ton mécanisme de refresh que nous venons de corriger
        this.refreshRequested.update(value => value + 1);
      },

      error: (error: any) => {
        console.error(
          'Error while saving ChangeClientInformation',
          error
        );

        this.showSaveNotification(
          'error',
          'Save failed',
          'Unable to save the changes. Please try again.'
        );
      }
    });
}




@if (saveNotification(); as notification) {

  <div
    class="save-notification"
    [class.save-notification--success]="notification.type === 'success'"
    [class.save-notification--error]="notification.type === 'error'"
  >
    <div class="save-notification__icon">
      @if (notification.type === 'success') {
        <span nz-icon nzType="check-circle" nzTheme="fill"></span>
      } @else {
        <span nz-icon nzType="close-circle" nzTheme="fill"></span>
      }
    </div>

    <div class="save-notification__content">
      <div class="save-notification__title">
        {{ notification.title }}
      </div>

      <div class="save-notification__message">
        {{ notification.message }}
      </div>
    </div>

  </div>
}

<section class="client-profiling">

  <!-- ton contenu actuel -->

</section>



        .save-notification {
  position: fixed;
  top: 24px;
  right: 24px;
  z-index: 9999;

  display: flex;
  align-items: center;
  gap: 14px;

  width: min(420px, calc(100vw - 48px));
  padding: 16px 20px;

  background: #fffdf9;
  border: 1px solid #d8c7aa;
  border-left: 5px solid;
  border-radius: 6px;

  box-shadow:
    0 8px 24px rgba(62, 43, 22, 0.16),
    0 2px 6px rgba(62, 43, 22, 0.08);

  animation: save-notification-in 220ms ease-out;

  &__icon {
    flex: 0 0 auto;

    display: flex;
    align-items: center;
    justify-content: center;

    width: 36px;
    height: 36px;

    border-radius: 50%;

    font-size: 20px;
  }

  &__content {
    min-width: 0;
  }

  &__title {
    margin-bottom: 3px;

    color: #5c4426;
    font-size: 14px;
    font-weight: 700;
  }

  &__message {
    color: #6f6253;
    font-size: 13px;
    line-height: 1.4;
  }

  // SUCCESS
  &--success {
    border-left-color: #3c9a5f;

    .save-notification__icon {
      color: #2f8a50;
      background: #eaf6ee;
    }
  }

  // ERROR
  &--error {
    border-left-color: #c84a4a;

    .save-notification__icon {
      color: #b83d3d;
      background: #fbecec;
    }
  }
}

@keyframes save-notification-in {
  from {
    opacity: 0;
    transform: translateY(-10px);
  }

  to {
    opacity: 1;
    transform: translateY(0);
  }
}
