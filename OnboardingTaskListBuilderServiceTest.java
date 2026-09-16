displayValue(value: string | null): string {
  if (!value) {
    return '';
  }

  return (
    this.options().find(option => option.value === value)?.label
    ?? value
  );
}
