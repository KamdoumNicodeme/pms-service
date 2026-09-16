apply(id: string, patch: ResolutionPatch): void {

  // =====================================================
  // CHECK DUPLICATES FOR SIMPLE LISTS
  // Nationality / Email / Phone
  // =====================================================

  const listRow = this.rows().find(row =>
    row.kind === 'list' &&
    row.entries.some(entry => entry.id === id)
  );

  if (listRow && patch.value) {

    const duplicate = listRow.entries.some(entry => {

      // Do not compare the entry with itself
      if (entry.id === id) {
        return false;
      }

      const currentValue =
        entry.resolution.value ??
        entry.values.kyc ??
        entry.values.digital ??
        entry.values.core ??
        null;

      return this.isSameListValue(
        listRow.key,
        currentValue,
        patch.value
      );
    });

    if (duplicate) {
      return;
    }
  }

  // =====================================================
  // APPLY RESOLUTION
  // =====================================================

  this.overrides.update(
    (current: ReadonlyMap<string, Resolution>) => {

      const next = new Map(current);

      next.set(id, {
        ...patch,
        reviewed: true,
      });

      return next;
    }
  );

  this.expandedId.set(null);
}
