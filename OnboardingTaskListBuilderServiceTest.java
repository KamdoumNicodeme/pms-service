private buildEntryChildren(
  field: ComparisonListFieldDto,
  paired: PairedEntry
): ComparisonRow[] {

  if (!field.entryFields?.length) {
    return [];
  }

  return field.entryFields.map(definition => {

    const id =
      `${field.key}:${paired.key}:${definition.key}`;

    const values: Record<
      ComparisonSourceId,
      string | null
    > = {
      digital: this.entryFieldValue(
        paired,
        'digital',
        definition.key
      ),

      kyc: this.entryFieldValue(
        paired,
        'kyc',
        definition.key
      ),

      core: this.entryFieldValue(
        paired,
        'core',
        definition.key
      ),
    };

    const status: ComparisonStatus =
      computeStatus(values);

    const fallback: Resolution =
      defaultResolution(values, status);

    const resolution: Resolution =
      this.overrides().get(id) ?? fallback;

    return {
      id,
      key: definition.key,
      label: definition.label,

      kind: definition.kind,
      options: definition.options ?? [],

      values,

      status,
      resolution,
      fallback,

      needsAttention:
        needsAttention(status),

      isOverride:
        isOverride(resolution, fallback),

      entryNoun: '',
      entries: [],
      children: [],

      composed: EMPTY_COMPOSED,
      composedResult: [],
    };
  });
}
