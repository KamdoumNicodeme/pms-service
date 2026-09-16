addManualEntry(
  fieldKey: string,
  value: string
): string | null {

  const normalizedValue = value.trim();

  if (!normalizedValue) {
    return null;
  }

  const row = this.rows().find(
    current => current.key === fieldKey
  );

  if (!row) {
    return null;
  }

  const duplicate = row.entries.some(entry =>
    this.isSameListValue(
      fieldKey,
      this.currentEntryValue(entry),
      normalizedValue
    )
  );

  if (duplicate) {
    return null;
  }

  const entryKey =
    `manual-${++this.manualSequence}`;

  this.manualEntries.update(current => [
    ...current,
    {
      fieldKey,
      entryKey,
      value: normalizedValue,
    },
  ]);

  const id = `${fieldKey}:${entryKey}`;

  this.overrides.update(current => {
    const next = new Map(current);

    next.set(id, {
      source: 'manual',
      value: normalizedValue,
      reason: '',
      comment: '',
      reviewed: true,
    });

    return next;
  });

  this.focusedId.set(id);

  return id;
}
