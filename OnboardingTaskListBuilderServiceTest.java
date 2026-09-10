private buildListRow(
  field: ComparisonListFieldDto
): ComparisonRow {

  const overrides = this.overrides();

  const entries: ComparisonEntryRow[] =
    pairEntries(field).map((paired: PairedEntry) => {

      const id =
        `${field.key}:${paired.key}`;

      const children =
        this.buildEntryChildren(field, paired);

      /*
       * Liste structurée :
       * Tax Information par exemple.
       */
      if (children.length > 0) {

        const status =
          worstStatus(children);

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

          needsAttention:
            children.some(child => child.needsAttention),

          isOverride:
            children.some(child => child.isOverride),

          isManual: false,

          children,
        };
      }

      /*
       * Listes simples :
       * email, phone, nationality...
       */
      const values = paired.values;

      const status =
        computeEntryStatus(values);

      const fallback =
        entryFallback(values, status);

      const resolution =
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

        needsAttention:
          needsAttention(status),

        isOverride:
          isOverride(resolution, fallback),

        isManual: false,

        children: [],
      };
    });
