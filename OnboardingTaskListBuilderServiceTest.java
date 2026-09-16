displayValue(value: string | null): string {
  if (!value) {
    return '';
  }

  return (
    this.options().find(option => option.value === value)?.label
    ?? value
  );
}

@if (entry().isManual) {
  <span class="row__name">
    {{ displayValue(entry().resolution.value) }}
  </span>
} @else if (entry().label) {
  <span class="row__name">
    {{ entry().label }}
  </span>
}
