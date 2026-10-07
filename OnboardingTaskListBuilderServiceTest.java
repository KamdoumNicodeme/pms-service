readonly canSubmitStructured = computed((): boolean => {
  const definitions = this.row().entryFields ?? [];

  if (definitions.length === 0) {
    return false;
  }

  // =========================
  // TAX INFORMATION
  // =========================
  if (this.row().key === 'tax-information') {
    const taxCountry = this.fieldValue('tax-country').trim();
    const tin = this.fieldValue('tin').trim();
    const reason = this.fieldValue('tin-unavailable-reason').trim();

    if (!taxCountry) {
      return false;
    }

    // Either TIN or unavailable reason is required
    return !!tin || !!reason;
  }

  // =========================
  // NATIONALITIES
  // =========================
  if (this.row().key === 'nationalities') {
    const country = this.fieldValue('country').trim();
    const date = this.fieldValue('date').trim();

    return !!country && !!date;
  }

  // =========================
  // DEFAULT STRUCTURED ENTRY
  // =========================
  return definitions
    .filter(definition => definition.editable)
    .every(definition =>
      this.fieldValue(definition.key).trim() !== ''
    );
});
