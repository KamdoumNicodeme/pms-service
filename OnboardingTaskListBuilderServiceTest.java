import { Component, signal, WritableSignal } from '@angular/core';
import { NzIconModule } from 'ng-zorro-antd/icon';

export type SaveNotificationType = 'success' | 'error';

export interface SaveNotification {
  type: SaveNotificationType;
  title: string;
  message: string;
}

@Component({
  selector: 'save-notification',
  standalone: true,
  imports: [
    NzIconModule
  ],
  templateUrl: './save-notification.component.html',
  styleUrl: './save-notification.component.scss'
})
export class SaveNotificationComponent {

  protected readonly notification: WritableSignal<SaveNotification | null> =
    signal<SaveNotification | null>(null);

  private notificationTimeout?: ReturnType<typeof setTimeout>;

  public show(
    type: SaveNotificationType,
    title: string,
    message: string
  ): void {

    if (this.notificationTimeout) {
      clearTimeout(this.notificationTimeout);
    }

    this.notification.set({
      type,
      title,
      message
    });

    this.notificationTimeout = setTimeout((): void => {
      this.notification.set(null);
    }, 3000);
  }
}
