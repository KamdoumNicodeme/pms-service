private buildListRow(field: ComparisonListFieldDto): ComparisonRow {
  const overrides = this.overrides();

  const entries: ComparisonEntryRow[] = pairEntries(field).map(
    (paired: PairedEntry): ComparisonEntryRow => {
      const id = `${field.key}:${paired.key}`;

      // =====================================================
      // STRUCTURED ENTRY
      // Tax Information
      // =====================================================

      const children = this.buildEntryChildren(field, paired);

      if (children.length > 0) {
        const status = worstStatus(children);

        const fallback: Resolution = {
          source: 'core',
          value: null,
          reason: '',
          comment: '',
          reviewed: true,
        };

        return {
          id,
          fieldKey: field.key,
          entryKey: paired.key,
          label: paired.label,
          values: paired.values,
          fields: paired.fields,
          status,
          resolution: fallback,
          fallback,
          needsAttention: children.some(child => child.needsAttention),
          isOverride: children.some(child => child.isOverride),
          isManual: false,
          children,
        };
      }

      // =====================================================
      // SIMPLE ENTRY
      // Email / Phone / Nationality
      // =====================================================

      const values = paired.values;

      const status = computeEntryStatus(values);

      const fallback = entryFallback(values, status);

      const resolution = overrides.get(id) ?? fallback;

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
        children: [],
      };
    }
  );

  // =========================================================
  // SIMPLE MANUAL ENTRIES
  // Email / Phone / Nationality
  // =========================================================

  for (
    const manual of this.manualEntries()
      .filter(entry => entry.fieldKey === field.key)
  ) {
    const id = `${field.key}:${manual.entryKey}`;

    const values: Record<ComparisonSourceId, string | null> = {
      digital: null,
      kyc: null,
      core: null,
    };

    const fallback: Resolution = {
      source: 'manual',
      value: manual.value,
      reason: '',
      comment: '',
      reviewed: true,
    };

    const resolution =
      overrides.get(id) ?? fallback;

    entries.push({
      id,
      fieldKey: field.key,
      entryKey: manual.entryKey,

      label: manual.value,

      values,

      fields: undefined,

      status: 'added',

      resolution,

      fallback,

      needsAttention: false,

      isOverride: true,

      isManual: true,

      children: [],
    });
  }

  // =========================================================
  // STRUCTURED MANUAL ENTRIES
  // Tax Information
  // =========================================================

  for (
    const manual of this.manualStructureEntries()
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
          overrides.get(childId) ?? fallback;

        return {
          id: childId,

          key: definition.key,

          label: definition.label,

          kind: definition.kind,

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

  // =========================================================
  // LIST STATUS
  // =========================================================

  const status = worstStatus(entries);

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
        entry => entry.needsAttention
      ),

    isOverride:
      entries.some(
        entry => entry.isOverride
      ),

    entryNoun:
      field.entryNoun,

    entries,

    children: [],

    composed:
      EMPTY_COMPOSED,

    composedResult: [],

    entryFields:
      field.entryFields ?? [],
  };
}
