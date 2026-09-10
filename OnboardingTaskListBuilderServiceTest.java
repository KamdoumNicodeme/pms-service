const definitions = (field.entryFields ?? []).filter(definition => {
  if (definition.key !== 'tin-unavailable-reason') {
    return true;
  }

  const tin =
    paired.fields.core
      ?.find(item => item.key === 'tin')
      ?.value
    ??
    paired.fields.kyc
      ?.find(item => item.key === 'tin')
      ?.value
    ??
    paired.fields.digital
      ?.find(item => item.key === 'tin')
      ?.value;

  return !tin?.trim();
});



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


    setFieldValue(
  key: string,
  value: string
): void {
  this.structureDraft.update(current => {
    const next = {
      ...current,
      [key]: value
    };

    if (
      key === 'tin'
      && value?.trim()
    ) {
      next['tin-unavailable-reason'] = '';
    }

    return next;
  });
}
