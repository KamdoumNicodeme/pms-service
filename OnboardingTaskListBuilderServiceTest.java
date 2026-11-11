private applyItalianBirthTownCodeVisibility(
  rows: readonly ComparisonRow[]
): ComparisonRow[] {
  const nationalityRow = rows.find(
    row => row.key === 'nationalities'
  );

  if (!nationalityRow) {
    return rows.filter(
      row => row.key !== 'italian-birth-town-code'
    );
  }

  const removedIds = new Set(
    this.removedEntries().map(entry => entry.id)
  );

  const getValue = (row: ComparisonRow): string | null =>
    row.resolution.value ??
    row.values.core ??
    Object.values(row.values).find(value => value != null) ??
    null;

  const getChild = (
    entry: ComparisonEntryRow,
    keys: readonly string[],
    label: string
  ): ComparisonRow | undefined =>
    entry.children.find(child => {
      const fieldKey = child.key.split(':').at(-1);

      return keys.includes(fieldKey ?? '') ||
        child.label.toLowerCase() === label;
    });

  const getDate = (value: string | null): number => {
    if (!value) return 0;

    const match = /^(\d{2})\/(\d{2})\/(\d{4})$/.exec(value);

    if (match) {
      return Date.UTC(
        Number(match[3]),
        Number(match[2]) - 1,
        Number(match[1])
      );
    }

    return Date.parse(value) || 0;
  };

  const nationalities = (nationalityRow.entries ?? [])
    .filter(entry =>
      entry.status !== 'removed' &&
      !removedIds.has(`nationalities:${entry.entryKey}`)
    )
    .map((entry, index) => {
      const countryField = getChild(
        entry,
        ['nationality', 'country'],
        'nationality'
      );

      const dateField = getChild(
        entry,
        ['date', 'obtaining-date', 'fromDate'],
        'obtaining date'
      );

      return {
        country: countryField ? getValue(countryField) : null,
        date: dateField ? getDate(getValue(dateField)) : 0,
        index
      };
    })
    .filter(nationality => nationality.country != null)
    .sort((first, second) =>
      first.date - second.date || first.index - second.index
    );

  const latestNationality = nationalities.at(-1);

  return latestNationality?.country === 'IT'
    ? [...rows]
    : rows.filter(
        row => row.key !== 'italian-birth-town-code'
      );
}
