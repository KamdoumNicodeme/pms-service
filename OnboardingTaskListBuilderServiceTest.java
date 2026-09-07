@if (expanded()) {

  @if (entry().fields?.core?.length) {

    <section class="structured-resolution">

      @for (
        field of entry().fields?.core ?? [];
        track field.key
      ) {

        @let definition = definitionFor(field.key);

        <div class="structured-resolution__field">

          <label class="structured-resolution__label">
            {{ field.label ?? field.key }}
          </label>

          @if (
            definition?.kind === 'select'
            && (definition?.options?.length ?? 0) > 0
          ) {

            <nz-select
              class="structured-resolution__control"
              [ngModel]="draftValue(field.key)"
              (ngModelChange)="updateDraftValue(field.key, $event)"
            >
              @for (
                option of definition?.options ?? [];
                track option.value
              ) {
                <nz-option
                  [nzValue]="option.value"
                  [nzLabel]="option.label"
                />
              }
            </nz-select>

          } @else {

            <input
              nz-input
              class="structured-resolution__control"
              [disabled]="definition?.editable === false"
              [ngModel]="draftValue(field.key)"
              (ngModelChange)="updateDraftValue(field.key, $event)"
            />

          }

        </div>

      }

      <div class="structured-resolution__actions">

        <button
          type="button"
          class="resolution__button"
          (click)="toggle.emit()"
        >
          Cancel
        </button>

        <button
          type="button"
          class="resolution__button resolution__button--primary"
          (click)="applyStructured()"
        >
          Apply
        </button>

      </div>

    </section>

  } @else {

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

}
