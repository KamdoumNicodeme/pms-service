removeManualEntry(
  fieldKey: string,
  entryKey: string
): void {

  const id = `${fieldKey}:${entryKey}`;

  // ============================================================
  // 1 - REMOVE SIMPLE MANUAL ENTRY
  // ============================================================

  this.manualEntries.update(current =>
    current.filter(entry =>
      !(
        entry.fieldKey === fieldKey &&
        entry.entryKey === entryKey
      )
    )
  );

  // ============================================================
  // 2 - REMOVE STRUCTURED MANUAL ENTRY
  // ============================================================

  this.manualStructuredEntries.update(current =>
    current.filter(entry =>
      !(
        entry.fieldKey === fieldKey &&
        entry.entryKey === entryKey
      )
    )
  );

  // ============================================================
  // 3 - REMOVE ITS OVERRIDES
  // ============================================================

  this.overrides.update(current => {

    const next = new Map(current);

    // Simple manual entry
    next.delete(id);

    // Structured manual entry children
    //
    // Example:
    // tax-information:manual-1:tin
    // tax-information:manual-1:tax-country
    for (const key of next.keys()) {
      if (key.startsWith(`${id}:`)) {
        next.delete(key);
      }
    }

    return next;
  });

  // ============================================================
  // IMPORTANT
  //
  // DO NOT add this entry to deletedEntries.
  //
  // A manual entry never existed in Core.
  // Removing it means cancelling the addition.
  // ============================================================

  if (this.focusedId() === id) {
    this.focusedId.set(null);
  }

  if (this.expandedId() === id) {
    this.expandedId.set(null);
  }
}
