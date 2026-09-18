private taxCountryAlreadyExists(taxCountry: string): boolean {
  const normalizedTaxCountry: string = taxCountry.trim().toUpperCase();

  return this.row().entries.some((entry: ComparisonEntryRow): boolean => {

    // Existing Core entry.
    // The entryKey contains the technical country code, e.g. "FR".
    if (!entry.isManual) {
      return entry.entryKey.trim().toUpperCase() === normalizedTaxCountry;
    }

    // Manual entry.
    // Read the current resolved value of the tax-country child.
    const taxCountryChild: ComparisonRow | undefined =
      entry.children.find(
        (child: ComparisonRow): boolean =>
          child.key === 'tax-country'
      );

    const manualValue: string | null =
      taxCountryChild?.resolution.value ?? null;

    return manualValue?.trim().toUpperCase() === normalizedTaxCountry;
  });
}
