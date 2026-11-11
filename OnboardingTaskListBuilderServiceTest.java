private applyItalianBirthTownCodeVisibility(
  rows: readonly ComparisonRow[]
): ComparisonRow[] {
  const nationalityRow = rows.find(row => row.key === 'nationalities');

  if (!nationalityRow) {
    return [...rows];
  }

  const hasItalianNationality = (nationalityRow.entries ?? []).some(entry =>
    entry.status !== 'removed' &&
    entry.children.some(child =>
      child.key === 'nationality' &&
      child.resolution.value === 'IT'
    )
  );

  return hasItalianNationality
    ? [...rows]
    : rows.filter(row => row.key !== 'italian-birth-town-code');
}
