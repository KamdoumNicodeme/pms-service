private currentEntryValue(
  entry: ComparisonEntryRow
): string | null {

  return (
    entry.resolution.value ??
    entry.values.kyc ??
    entry.values.digital ??
    entry.values.core ??
    null
  );
}

private isSameListValue(
  fieldKey: string,
  first: string | null,
  second: string | null
): boolean {

  if (!first || !second) {
    return false;
  }

  const a = first.trim();
  const b = second.trim();

  switch (fieldKey.toLowerCase()) {

    /*
     * Country codes:
     *
     * BE === be
     * DE === de
     */
    case 'nationalities':
    case 'nationality':
      return a.toUpperCase() === b.toUpperCase();

    /*
     * Email:
     *
     * Test@Test.com === test@test.com
     */
    case 'emails':
    case 'email':
      return a.toLowerCase() === b.toLowerCase();

    /*
     * Phone:
     *
     * +352 621 123 456
     * +352621123456
     *
     * sont considérés identiques.
     */
    case 'phone-numbers':
    case 'phone-number':
    case 'phones':
      return this.normalizePhone(a) ===
             this.normalizePhone(b);

    default:
      return a.toLowerCase() === b.toLowerCase();
  }
}

private normalizePhone(
  value: string
): string {

  return value.replace(
    /[\s().-]/g,
    ''
  );
}
