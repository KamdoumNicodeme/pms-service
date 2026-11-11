private applyItalianBirthTownCodeVisibility(
  rows: readonly ComparisonRow[]
): ComparisonRow[] {
  const nationalityRow = rows.find(row => row.key === 'nationalities');

  if (!nationalityRow) {
    return [...rows];
  }

  const hasItalianNationality = (nationalityRow.entries ?? []).some(
    entry =>
      entry.status !== 'removed' &&
      (entry.entryFields ?? []).some(
        field =>
          field.key === 'nationality' &&
          (field.resolution.value === 'IT' ||
            field.values.core === 'IT' ||
            field.values.portal === 'IT')
      )
  );

  return hasItalianNationality
    ? [...rows]
    : rows.filter(row => row.key !== 'italian-birth-town-code');
}
