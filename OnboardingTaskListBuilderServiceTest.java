onDateChange(value: string | null): void {
  if (!value) {
    this.manualValue.set('');
    return;
  }

  const [year, month, day] =
    value.split('-');

  this.manualValue.set(
    `${day}/${month}/${year}`
  );
}
