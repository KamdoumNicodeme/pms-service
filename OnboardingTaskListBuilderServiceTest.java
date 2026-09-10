private buildEntryChildren(
  field: ComparisonListFieldDto,
  paired: PairedEntry
): ComparisonRow[] {

  if (!field.entryFields?.length) {
    return [];
  }

  const overrides = this.overrides();

  const tinChildId = `${field.key}:${paired.key}:tin`;

  const tinOverride = overrides.get(tinChildId);

  const tin =
    tinOverride?.value ??
    paired.fields.core?.find(
      (item: ComparisonEntryFieldDto) => item.key === 'tin'
    )?.value ??
    paired.fields.kyc?.find(
      (item: ComparisonEntryFieldDto) => item.key === 'tin'
    )?.value ??
    paired.fields.digital?.find(
      (item: ComparisonEntryFieldDto) => item.key === 'tin'
    )?.value;

  const definitions: ComparisonEntryFieldDefinition[] =
    (field.entryFields ?? []).filter(
      (definition: ComparisonEntryFieldDefinition) => {

        if (definition.key !== 'tin-unavailable-reason') {
          return true;
        }

        return !tin?.trim();
      }
    );

  return definitions.map(
    (definition: ComparisonEntryFieldDefinition): ComparisonRow => {

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

      const status =
        computeStatus(values);

      const fallback =
        defaultResolution(
          values,
          status
        );

      const resolution =
        overrides.get(id) ?? fallback;

      return {
        id,

        key: definition.key,

        label: definition.label,

        kind: definition.kind,

        editable: definition.editable,

        options:
          definition.options ?? [],

        values,

        status,

        resolution,

        fallback,

        needsAttention:
          needsAttention(status),

        isOverride:
          isOverride(
            resolution,
            fallback
          ),

        entryNoun: '',

        entries: [],

        children: [],

        composed:
          EMPTY_COMPOSED,

        composedResult: [],
      };
    }
  );
}
