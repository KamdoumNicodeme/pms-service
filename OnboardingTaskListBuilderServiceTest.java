removeManualEntry(
  fieldKey: string,
  entryKey: string
): void {

  // Simple manual entries
  this.manualEntries.update(current =>
    current.filter(entry =>
      !(
        entry.fieldKey === fieldKey &&
        entry.entryKey === entryKey
      )
    )
  );

  // Structured manual entries
  this.manualStructuredEntries.update(current =>
    current.filter(entry =>
      !(
        entry.fieldKey === fieldKey &&
        entry.entryKey === entryKey
      )
    )
  );

  const id = `${fieldKey}:${entryKey}`;

  if (this.focusedId() === id) {
    this.focusedId.set(null);
  }

  if (this.expandedId() === id) {
    this.expandedId.set(null);
  }
}
