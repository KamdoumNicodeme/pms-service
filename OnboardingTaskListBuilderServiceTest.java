submitStructured(): void {

  const definitions:
    readonly ComparisonEntryFieldDefinition[] =
    this.row().entryFields ?? [];


  const fields: {
    key: string;
    value: string | null;
  }[] = definitions.map(
    (definition: ComparisonEntryFieldDefinition) => {

      const value =
        this.fieldValue(definition.key).trim();

      return {
        key: definition.key,
        value: value === '' ? null : value,
      };
    }
  );


  // =========================================
  // NATIONALITY VALIDATION
  // =========================================

  if (this.row().key === 'nationalities') {

    const country =
      fields.find(
        field => field.key === 'country'
      )?.value ?? null;


    if (!country) {
      this.structuredError.set(
        'Nationality is required.'
      );

      return;
    }


    if (this.nationalityAlreadyExists(country)) {
      this.structuredError.set(
        'This nationality already exists.'
      );

      return;
    }
  }


  // =========================================
  // TAX INFORMATION VALIDATION
  // =========================================

  if (this.row().key === 'tax-information') {

    const taxCountry =
      fields.find(
        field => field.key === 'tax-country'
      )?.value ?? null;


    if (
      taxCountry &&
      this.taxCountryAlreadyExists(taxCountry)
    ) {

      this.structuredError.set(
        'Tax Country already exists.'
      );

      return;
    }
  }


  // =========================================
  // VALID
  // =========================================

  this.structuredError.set(null);

  this.addStructuredEntry.emit({
    fields,
  });

  this.closeStructuredModal();
}
