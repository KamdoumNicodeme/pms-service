@if (kind() === 'date') {

  <nz-date-picker
    nzFormat="dd/MM/yyyy"
    [ngModel]="dateValue()"
    (ngModelChange)="onDateChange($event)"
  />

} @else {

  <input
    nz-input
    [ngModel]="manualValue()"
    (ngModelChange)="manualValue.set($event)"
  />

}

readonly dateValue = computed(() => {
  const value = this.manualValue();

  if (!value) {
    return null;
  }

  const date = new Date(value);

  return isNaN(date.getTime())
    ? null
    : date;
});

onDateChange(date: Date | null): void {
  if (!date) {
    this.manualValue.set('');
    return;
  }

  const year = date.getFullYear();

  const month = String(
    date.getMonth() + 1
  ).padStart(2, '0');

  const day = String(
    date.getDate()
  ).padStart(2, '0');

  this.manualValue.set(
    `${year}-${month}-${day}`
  );
}

private dateField(
  key: string,
  label: string,
  value: string | null | undefined,
  editable = true
): ComparisonScalarFieldDto {
  return {
    key,
    label,
    kind: 'date',
    editable,
    values: {
      digital: null,
      kyc: null,
      core: this.normalize(value),
    },
  };
}
