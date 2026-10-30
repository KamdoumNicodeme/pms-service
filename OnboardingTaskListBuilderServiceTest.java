@if (rejectionReason) {
  <section class="rejection-alert">
    <nz-alert
      nzShowIcon
      nzType="error"
      [nzMessage]="alert"
    />
  </section>
}

<ng-template #alert>
  <div class="rejection-alert__content">
    <span class="rejection-alert__title">
      Rejected by PSA during Sign off
    </span>

    <span class="rejection-alert__separator">—</span>

    <span class="rejection-alert__reason">
      {{ rejectionReason }}
    </span>
  </div>
</ng-template>
