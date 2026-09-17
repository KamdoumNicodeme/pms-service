removeManualEntry(fieldKey: string, entryKey: string): void {

  const id = `${fieldKey}:${entryKey}`;

  // ==========================================
  // 1. REMOVE SIMPLE MANUAL ENTRY
  // ==========================================

  this.manualEntries.update(
    (current: readonly ManualEntry[]) =>
      current.filter(
        (entry: ManualEntry) =>
          !(
            entry.fieldKey === fieldKey &&
            entry.entryKey === entryKey
          )
      )
  );

  // ==========================================
  // 2. REMOVE STRUCTURED MANUAL ENTRY
  // ==========================================

  this.manualStructuredEntries.update(
    (current: readonly ManualEntryStructure[]) =>
      current.filter(
        (entry: ManualEntryStructure) =>
          !(
            entry.fieldKey === fieldKey &&
            entry.entryKey === entryKey
          )
      )
  );

  // ==========================================
  // 3. REMOVE ALL OVERRIDES OF THIS ENTRY
  // ==========================================

  this.overrides.update(
    (current: ReadonlyMap<string, Resolution>) => {

      const next = new Map(current);

      // Simple entry
      next.delete(id);

      // Structured entry children:
      // tax-information:manual-1:tin
      // tax-information:manual-1:tax-country
      // etc.
      for (const key of next.keys()) {
        if (key.startsWith(`${id}:`)) {
          next.delete(key);
        }
      }

      return next;
    }
  );

  // ==========================================
  // 4. MANUAL ENTRY != DELETED CORE ENTRY
  // ==========================================

  // Si elle avait été mise dans deletedEntries auparavant,
  // on la retire.
  this.deletedEntries.update(
    (current: readonly string[]) =>
      current.filter(
        (existingId: string) => existingId !== id
      )
  );

  // ==========================================
  // 5. CLEAN UI STATE
  // ==========================================

  if (this.focusedId() === id) {
    this.focusedId.set(null);
  }

  if (this.expandedId() === id) {
    this.expandedId.set(null);
  }
}
