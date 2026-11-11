private applyItalianBirthTownCodeVisibility(
  rows: readonly ComparisonRow[]
): ComparisonRow[] {
  const nationalityRow = rows.find(row => row.key === 'nationalities');

  if (!nationalityRow) {
    return [...rows];
  }

  const nationalities = nationalityRow.entries ?? [];

  const hasItalianNationality = nationalities.some(
    entry =>
      entry.status !== 'removed' &&
      (entry.resolution?.value === 'IT' ||
        (entry.resolution?.value == null && entry.values.core === 'IT'))
  );

  return hasItalianNationality
    ? [...rows]
    : rows.filter(row => row.key !== 'italian-birth-town-code');
}
