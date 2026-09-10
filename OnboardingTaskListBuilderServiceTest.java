@for (field of row().entryFields ?? []; track field.key) {

  @if (
    field.key !== 'tin-unavailable-reason'
    || !fieldValue('tin').trim()
  ) {

    <div class="structured-form__field">

      <label class="structured-form__label">
        {{ field.label }}
      </label>

      @if (field.kind === 'select') {

        <nz-select
          class="structured-form__control"
          [ngModel]="fieldValue(field.key)"
          (ngModelChange)="setFieldValue(field.key, $event)"
          nzPlaceHolder="Select a value"
        >

          @for (option of field.options ?? []; track option.value) {
            <nz-option
              [nzValue]="option.value"
              [nzLabel]="option.label"
            />
          }

        </nz-select>

      } @else {

        <input
          nz-input
          class="structured-form__control"
          [ngModel]="fieldValue(field.key)"
          (ngModelChange)="setFieldValue(field.key, $event)"
        />

      }

    </div>

  }

}
