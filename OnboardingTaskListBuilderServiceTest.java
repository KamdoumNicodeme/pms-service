removeManualEntry(fieldKey: string, entryKey: string): void {

  this.manualEntries.update(current =>
    current.filter(
      entry =>
        !(entry.fieldKey === fieldKey && entry.entryKey === entryKey)
    )
  );

  this.manualStructureEntries.update(current =>
    current.filter(
      entry =>
        !(entry.fieldKey === fieldKey && entry.entryKey === entryKey)
    )
  );

  const id = `${fieldKey}:${entryKey}`;

  this.resolutions.update(current => {
    const updated = new Map(current);
    updated.delete(id);
    return updated;
  });

  this.deletedEntries.update(current => [
    ...current.filter(existingId => existingId !== id),
    id
  ]);

  if (this.focusedId() === id) {
    this.focusedId.set(null);
  }

  if (this.expandedId() === id) {
    this.expandedId.set(null);
  }
}
