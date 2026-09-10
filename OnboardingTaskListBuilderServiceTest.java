addManualStructuredEntry(
  fieldKey: string,
  fields: readonly StructuredEntryValue[]
): string {

  const entryKey = `manual-${++this.manualSequence}`;

  this.manualStructuredEntries.update(current => [
    ...current,
    {
      fieldKey,
      entryKey,
      fields,
    },
  ]);

  const id = `${fieldKey}:${entryKey}`;

  this.focusedId.set(id);

  return id;
}
