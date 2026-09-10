for (
  const manual of this.manualStructuredEntries()
    .filter(entry => entry.fieldKey === field.key)
) {

  const id = `${field.key}:${manual.entryKey}`;

  const children: ComparisonRow[] =
    (field.entryFields ?? []).map(definition => {

      const childId =
        `${field.key}:${manual.entryKey}:${definition.key}`;

      const manualField =
        manual.fields.find(
          value => value.key === definition.key
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

      const status: ComparisonStatus = 'added';

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
        options: definition.options ?? [],

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

        composed: EMPTY_COMPOSED,
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
