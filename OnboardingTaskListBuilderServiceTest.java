for (const manual of this.manualStructureEntries().filter(entry => entry.fieldKey === field.key)) {
  const id = `${field.key}:${manual.entryKey}`;

  /*
   * Valeur actuelle du TIN.
   *
   * IMPORTANT :
   * on regarde d'abord l'override, car le TIN
   * peut avoir été modifié après l'ajout manuel.
   */
  const tinChildId = `${field.key}:${manual.entryKey}:tin`;

  const tinOverride = overrides.get(tinChildId);

  const originalTin =
    manual.fields.find(item => item.key === 'tin')?.value ?? null;

  const currentTin =
    tinOverride?.value ?? originalTin;

  /*
   * Reason if TIN Unavailable n'existe que
   * lorsque le TIN courant est vide.
   */
  const definitions = (field.entryFields ?? []).filter(definition => {
    if (definition.key !== 'tin-unavailable-reason') {
      return true;
    }

    return !currentTin?.trim();
  });

  const children: ComparisonRow[] = definitions.map(definition => {
    const childId =
      `${field.key}:${manual.entryKey}:${definition.key}`;

    const manualField =
      manual.fields.find(
        item => item.key === definition.key
      );

    const value = manualField?.value ?? null;

    const values: Record<ComparisonSourceId, string | null> = {
      digital: null,
      kyc: value,
      core: null,
    };

    const status: ComparisonStatus = 'added';

    const fallback: Resolution = {
      source: 'manual',
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
      options: definition.options ?? [],

      values,

      status,
      resolution,
      fallback,

      needsAttention: false,

      isOverride: isOverride(resolution, fallback),

      entryNoun: '',
      entries: [],
      children: [],

      composed: EMPTY_COMPOSED,
      composedResult: [],
    };
  });

  const entryResolution: Resolution = {
    source: 'manual',
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
