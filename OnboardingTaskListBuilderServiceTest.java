private applyItalianBirthTownCodeVisibility(
  rows: readonly ComparisonRow[]
): ComparisonRow[] {
  const manualNationalities = new Map<string, string>();

  for (const entry of this.manualEntries()) {
    if (entry.fieldKey === 'nationalities' && entry.value) {
      manualNationalities.set(entry.entryKey, entry.value);
    }
  }

  for (const entry of this.manualStructureEntries()) {
    if (entry.fieldKey !== 'nationalities') {
      continue;
    }

    const country = entry.fields.find(
      field => field.key === 'country'
    )?.value;

    if (country) {
      manualNationalities.set(entry.entryKey, country);
    }
  }

  const manualNationality = [...manualNationalities.values()].at(-1);

  if (!manualNationality || manualNationality === 'IT') {
    return [...rows];
  }

  return rows.filter(
    row => row.key !== 'italian-birth-town-code'
  );
}
