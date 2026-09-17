apply(id: string, patch: ResolutionPatch): void {
  // ============================================
  // CHECK DUPLICATES FOR SIMPLE LISTS
  // Nationality / Email / Phone
  // ============================================

  const listRow: ComparisonRow | undefined = this.rows().find(
    (row: ComparisonRow) =>
      row.kind === 'list' &&
      row.entries.some(
        (entry: ComparisonEntryRow) => entry.id === id
      )
  );

  if (listRow && patch.value) {
    const duplicate: boolean = listRow.entries.some(
      (entry: ComparisonEntryRow) => {
        // Do not compare the entry with itself
        if (entry.id === id) {
          return false;
        }

        const currentValue: string | null =
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
      }
    );

    if (duplicate) {
      return;
    }
  }

  // ============================================
  // BUILD RESOLUTION
  // ============================================

  const resolution: Resolution = {
    ...patch,
    reviewed: true,
  };

  // ============================================
  // APPLY RESOLUTION LOCALLY
  // ============================================

  this.overrides.update(
    (
      current: ReadonlyMap<string, Resolution>
    ): Map<string, Resolution> => {
      const next = new Map(current);

      next.set(id, resolution);

      return next;
    }
  );

  // ============================================
  // NOTIFY PARENT
  // ============================================

  this.resolutionApplied.emit({
    id,
    resolution,
  });

  // ============================================
  // CLOSE EDIT MODE
  // ============================================

  this.expandedId.set(null);
}
