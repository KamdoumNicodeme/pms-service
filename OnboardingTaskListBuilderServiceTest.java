template: `
  <div class="row__line">

    <div class="row__label">

      <span
        class="row__stripe"
        [nz-tooltip]="statusText()">
      </span>

      @if (chipLabel(); as chip) {
        <span
          class="row__chip"
          [attr.data-status]="entry().status">

          <nz-icon [nzType]="chipIcon()" />

          {{ chip }}

        </span>
      }

      @if (entry().label) {
        <span class="row__name">
          {{ entry().label }}
        </span>
      }

      @if (entry().isManual) {
        <button
          type="button"
          class="row__remove"
          nz-tooltip
          nzTooltipTitle="Remove this entry"
          (click)="remove.emit()">

          <nz-icon nzType="delete" />

        </button>
      }

    </div>


    <div class="row__body">

      @if (entry().fields?.core?.length) {

        <div class="row__fields">

          @for (
            field of entry().fields?.core ?? [];
            track field.key
          ) {

            <div class="row__field">

              <span class="row__field-label">
                {{ field.label ?? field.key }}
              </span>

              <span class="row__field-value">
                {{ field.value ?? '-' }}
              </span>

            </div>

          }

        </div>

      } @else {

        <comparison-result
          [resolution]="entry().resolution"
          [needsAttention]="entry().needsAttention"
          [isOverride]="entry().isOverride"
          [expanded]="expanded()"
          [emptyMeansDropped]="true"
          [kind]="kind()"
          (edit)="toggle.emit()"
        />

        <comparison-annotations
          [annotations]="annotations()"
        />

      }

    </div>

  </div>


  @if (expanded() && !entry().fields?.core?.length) {

    <comparison-resolution
      [fieldId]="entry().id"
      [kind]="kind()"
      [values]="entry().values"
      [status]="entry().status"
      [resolution]="entry().resolution"
      [fallback]="entry().fallback"
      [isOverride]="entry().isOverride"
      (apply)="apply.emit($event)"
      (cancel)="toggle.emit()"
      (reset)="reset.emit()"
    />

  }
`,
