addManualEntry(fieldKey: string, value: string): string | null {
  const newValue = value.trim();

  if (!newValue) {
    return null;
  }

  const row = this.rows().find(
    current => current.key === fieldKey
  );

  if (!row) {
    return null;
  }

  // =====================================================
  // DUPLICATE CHECK
  // =====================================================

  const duplicate = row.entries.some(entry => {

    const valuesToCheck: (string | null | undefined)[] = [
      entry.entryKey,
      entry.label,

      entry.values.core,
      entry.values.kyc,
      entry.values.digital,

      entry.resolution.value,
      entry.fallback.value,
    ];

    return valuesToCheck.some(existingValue =>
      this.isSameListValue(
        fieldKey,
        existingValue,
        newValue,
        row.options
      )
    );
  });

  if (duplicate) {
    console.warn(
      `[ComparisonStore] "${newValue}" already exists in "${fieldKey}"`
    );

    return null;
  }

  // =====================================================
  // ADD MANUAL ENTRY
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

  this.overrides.update(current => {
    const next = new Map(current);

    next.set(id, {
      source: 'manual',
      value: newValue,
      reason: '',
      comment: '',
      reviewed: true,
    });

    return next;
  });

  this.focusedId.set(id);

  return id;
}


private isSameListValue(
  fieldKey: string,
  first: string | null | undefined,
  second: string | null | undefined,
  options: readonly ComparisonOption[] = []
): boolean {

  if (!first || !second) {
    return false;
  }

  let a = first.trim();
  let b = second.trim();

  // =====================================================
  // NATIONALITY
  // Compare code AND label
  //
  // DE      === Germany
  // Germany === DE
  // Germany === Germany
  // =====================================================

  if (
    fieldKey.toLowerCase() === 'nationality' ||
    fieldKey.toLowerCase() === 'nationalities'
  ) {

    const optionA = options.find(option =>
      option.value.toLowerCase() === a.toLowerCase() ||
      option.label.toLowerCase() === a.toLowerCase()
    );

    const optionB = options.find(option =>
      option.value.toLowerCase() === b.toLowerCase() ||
      option.label.toLowerCase() === b.toLowerCase()
    );

    if (optionA) {
      a = optionA.value;
    }

    if (optionB) {
      b = optionB.value;
    }

    return a.toUpperCase() === b.toUpperCase();
  }

  // =====================================================
  // EMAIL
  // =====================================================

  if (
    fieldKey.toLowerCase() === 'email' ||
    fieldKey.toLowerCase() === 'emails'
  ) {
    return a.toLowerCase() === b.toLowerCase();
  }

  // =====================================================
  // PHONE
  // =====================================================

  if (
    fieldKey.toLowerCase() === 'phone' ||
    fieldKey.toLowerCase() === 'phones' ||
    fieldKey.toLowerCase() === 'phone-number' ||
    fieldKey.toLowerCase() === 'phone-numbers'
  ) {
    return this.normalizePhone(a) === this.normalizePhone(b);
  }

  return a.toLowerCase() === b.toLowerCase();
}
