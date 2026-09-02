private buildListRow(
  field: ComparisonListFieldDto
): ComparisonRow {

  const overrides: ReadonlyMap<string, Resolution> =
    this.overrides();

  const entries: ComparisonEntryRow[] =
    pairEntries(field).map((paired: PairedEntry) => {

      const id = `${field.key}:${paired.key}`;

      const hasStructuredFields =
        paired.fields.digital !== null ||
        paired.fields.kyc !== null ||
        paired.fields.core !== null;

      const values: Readonly<
        Record<ComparisonSourceId, string | null>
      > = hasStructuredFields
        ? {
            digital: this.composeEntryFields(
              paired.fields.digital
            ),
            kyc: this.composeEntryFields(
              paired.fields.kyc
            ),
            core: this.composeEntryFields(
              paired.fields.core
            ),
          }
        : paired.values;

      const status: ComparisonStatus =
        computeEntryStatus(values);

      const fallback: Resolution =
        entryFallback(values, status);

      const resolution: Resolution =
        overrides.get(id) ?? fallback;

      return {
        id,
        fieldKey: field.key,
        entryKey: paired.key,
        label: paired.label,
        values,
        fields: paired.fields,
        status,
        resolution,
        fallback,
        needsAttention: needsAttention(status),
        isOverride: isOverride(resolution, fallback),
        isManual: false,
      };
    });

  for (
    const manual of this.manualEntries()
      .filter(
        (entry: ManualEntry) =>
          entry.fieldKey === field.key
      )
  ) {

    const id =
      `${field.key}:${manual.entryKey}`;

    const fallback: Resolution = {
      source: 'manual',
      value: manual.value,
      reason: '',
      comment: '',
      reviewed: true,
    };

    const resolution: Resolution =
      overrides.get(id) ?? fallback;

    entries.push({
      id,
      fieldKey: field.key,
      entryKey: manual.entryKey,
      label: '',
      values: EMPTY_VALUES,

      fields: undefined,

      status: 'added',
      resolution,
      fallback,
      needsAttention: false,
      isOverride: true,
      isManual: true,
    });
  }

  const status: ComparisonStatus =
    worstStatus(entries);

  const groupResolution: Resolution = {
    source: 'kyc',
    value: null,
    reason: '',
    comment: '',
    reviewed: true,
  };

  return {
    id: field.key,
    key: field.key,
    label: field.label,
    kind: 'list',
    options: [],
    values: EMPTY_VALUES,
    status,
    resolution: groupResolution,
    fallback: groupResolution,
    needsAttention:
      entries.some(
        (entry: ComparisonEntryRow) =>
          entry.needsAttention
      ),
    isOverride:
      entries.some(
        (entry: ComparisonEntryRow) =>
          entry.isOverride
      ),
    entryNoun: field.entryNoun,
    entries,
    children: [],
    composed: EMPTY_COMPOSED,
    composedResult: [],
  };
}
