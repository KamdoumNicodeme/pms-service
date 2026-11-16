<nz-card [nzTitle]="'WARNING'">
  @if (warningMessages?.length) {
    <ul class="warning-list">
      @for (warning of warningMessages; track $index) {
        <li class="warning-item">
          <div class="warning-context">
            {{ warning.fieldContext }}
          </div>

          <div class="warning-message">
            {{ warning.message }}
          </div>
        </li>
      }
    </ul>
  } @else {
    <nz-empty [nzNotFoundContent]="'No Warnings'" />
  }
</nz-card>
