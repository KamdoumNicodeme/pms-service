private applyItalianBirthTownCodeVisibility(
  rows: readonly ComparisonRow[]
): ComparisonRow[] {
  const nationalityRow = rows.find(row => row.key === 'nationalities');

  console.log('NATIONALITY ROW:', nationalityRow);
  console.log('NATIONALITY ENTRIES:', nationalityRow?.entries);

  return [...rows];
}
