addManualEntry(fieldKey: string, value: string): string | null {
  const newValue = value.trim();

  if (!newValue) {
    return null;
  }

  // =====================================================
  // CHECK EXISTING VALUES
  // Core + Digital + KYC + Manual + Overrides
  // =====================================================

  const row = this.rows().find(
    current => current.key === fieldKey
  );

  if (row) {
    const duplicate = row.entries.some(entry => {

      const existingValues: (string | null)[] = [
        // Current retained / modified value
        entry.resolution.value,

        // Original source values
        entry.values.core,
        entry.values.kyc,
        entry.values.digital,
      ];

      return existingValues.some(existingValue =>
        this.isSameListValue(
          fieldKey,
          existingValue,
          newValue
        )
      );
    });

    if (duplicate) {
      console.warn(
        `Duplicate value "${newValue}" for ${fieldKey}`
      );

      return null;
    }
  }

  // =====================================================
  // ADD
  // =====================================================

  const entryKey = `manual-${++this.manualSequence}`;

  this.manualEntries.update(current => [
    ...current,
    {
      fieldKey,
      entryKey,
      value: newValue,
    },
  ]);

  const id = `${fieldKey}:${entryKey}`;

  this.overrides.update(
    (current: ReadonlyMap<string, Resolution>) => {

      const next = new Map(current);

      next.set(id, {
        source: 'manual',
        value: newValue,
        reason: '',
        comment: '',
        reviewed: true,
      });

      return next;
    }
  );

  this.focusedId.set(id);

  return id;
}
