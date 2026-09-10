// =========================================================
// STRUCTURED MANUAL ENTRIES
// Tax Information
// =========================================================

for (
  const manual of this.manualStructureEntries()
    .filter(entry => entry.fieldKey === field.key)
) {
  const id = `${field.key}:${manual.entryKey}`;

  const tinValue =
    manual.fields.find(item => item.key === 'tin')?.value ?? null;

  const definitions =
    (field.entryFields ?? []).filter(definition => {

      if (definition.key !== 'tin-unavailable-reason') {
        return true;
      }

      return !tinValue?.trim();
    });

  const children: ComparisonRow[] =
    definitions.map(definition => {

      const childId =
        `${field.key}:${manual.entryKey}:${definition.key}`;

      const manualField =
        manual.fields.find(
          item => item.key === definition.key
        );

      const value =
        manualField?.value ?? null;

      const values: Record<
        ComparisonSourceId,
        string | null
      > = {
        digital: null,
        kyc: value,
        core: null,
      };

      const status: ComparisonStatus =
        'added';

      const fallback: Resolution = {
        source: 'kyc',
        value,
        reason: '',
        comment: '',
        reviewed: true,
      };

      const resolution =
        this.overrides().get(childId) ?? fallback;

      return {
        id: childId,

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

        needsAttention: false,

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
    });

  const entryResolution: Resolution = {
    source: 'kyc',
    value: null,
    reason: '',
    comment: '',
    reviewed: true,
  };

  entries.push({
    id,

    fieldKey: field.key,

    entryKey: manual.entryKey,

    label: '',

    values: EMPTY_VALUES,

    fields: undefined,

    status: 'added',

    resolution: entryResolution,

    fallback: entryResolution,

    needsAttention: false,

    isOverride: true,

    isManual: true,

    children,
  });
}
