readonly canSubmitStructured = computed((): boolean => {
  const definitions: readonly ComparisonEntryFieldDefinition[] =
    this.row().entryFields ?? [];

  if (definitions.length === 0) {
    return false;
  }

  // =========================================
  // NATIONALITY
  // =========================================

  if (this.row().key === 'nationalities') {

    const country =
      this.fieldValue('country').trim();

    const date =
      this.fieldValue('date').trim();

    return (
      country !== '' &&
      date !== '' &&
      !this.nationalityDuplicate()
    );
  }


  // =========================================
  // TAX INFORMATION
  // =========================================

  if (this.row().key === 'tax-information') {

    const taxCountry =
      this.fieldValue('tax-country').trim();

    const tin =
      this.fieldValue('tin').trim();

    const reason =
      this.fieldValue('tin-unavailable-reason').trim();

    if (!taxCountry) {
      return false;
    }

    // Il faut soit un TIN, soit une raison.
    return tin !== '' || reason !== '';
  }


  return false;
});
