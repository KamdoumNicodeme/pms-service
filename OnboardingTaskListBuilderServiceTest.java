readonly maxEntriesReached = computed((): boolean => {
  if (this.row().key === 'nationalities') {
    return this.row().entries.length >= 3;
  }

  return false;
});


readonly nationalityDuplicate = computed((): boolean => {
  if (this.row().key !== 'nationalities') {
    return false;
  }

  const country = this.fieldValue('country').trim();

  if (!country) {
    return false;
  }

  return this.nationalityAlreadyExists(country);
});


private nationalityCountry(
  entry: ComparisonEntryRow
): string | null {

  // Nouvelle structure :
  // nationality
  //   ├── country
  //   └── date
  const countryChild = entry.children.find(
    (child: ComparisonRow) => child.key === 'country'
  );

  if (countryChild?.resolution.value) {
    return countryChild.resolution.value;
  }

  // Ancienne / existing nationality.
  // Exemple : entryKey = "GB"
  if (
    entry.entryKey &&
    !entry.entryKey.startsWith('manual-')
  ) {
    return entry.entryKey;
  }

  // Dernier fallback
  return entry.resolution.value ?? null;
}


private nationalityAlreadyExists(country: string): boolean {
  return this.row().entries.some(
    (entry: ComparisonEntryRow): boolean => {

      const existingCountry =
        this.nationalityCountry(entry);

      return this.isSameValue(
        existingCountry,
        country
      );
    }
  );
}
