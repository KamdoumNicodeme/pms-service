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

console.log(
  'NATIONALITY CHANGES:',
  [...changes.entries()].filter(([key]) =>
    key.startsWith('nationalities')
  )
);

console.log(
  'NATIONALITY REMOVED:',
  removedEntries.filter(entry =>
    entry.id.startsWith('nationalities:')
  )
);



///





if (manualEntry || manualStructureEntry) {
  this.removedEntries.update(current =>
    current.filter(entry => entry.id !== id)
  );
} else if (removedValue) {
  this.removedEntries.update(current => [
    ...current.filter(entry => entry.id !== id),
    {
      id,
      value: removedValue
    }
  ]);
}
