export function pairEntries(
  field: ComparisonListFieldDto
): readonly PairedEntry[] {

  const paired = new Map<
    string,
    {
      label: string;
      values: Record<ComparisonSourceId, string | null>;
      fields: Record<
        ComparisonSourceId,
        readonly ComparisonEntryFieldDto[] | null
      >;
    }
  >();

  const register = (
    source: ComparisonSourceId,
    entry: ComparisonEntryDto
  ): void => {

    const existing = paired.get(entry.key);

    if (existing) {
      existing.values[source] = entry.value ?? null;
      existing.fields[source] = entry.fields ?? null;
      existing.label ||= entry.label ?? '';
      return;
    }

    paired.set(entry.key, {
      label: entry.label ?? '',

      values: {
        digital: null,
        kyc: null,
        core: null,
        [source]: entry.value ?? null,
      } as Record<ComparisonSourceId, string | null>,

      fields: {
        digital: null,
        kyc: null,
        core: null,
        [source]: entry.fields ?? null,
      } as Record<
        ComparisonSourceId,
        readonly ComparisonEntryFieldDto[] | null
      >,
    });
  };

  for (const source of COMPARISON_SOURCE_IDS) {
    for (const entry of field.values[source]) {
      register(source, entry);
    }
  }

  return [...paired.entries()]
    .filter(([, entry]) =>
      !isBlank(entry.values.digital) ||
      !isBlank(entry.values.kyc) ||
      !isBlank(entry.values.core) ||
      entry.fields.digital !== null ||
      entry.fields.kyc !== null ||
      entry.fields.core !== null
    )
    .map(([key, entry]) => ({
      key,
      label: entry.label,
      values: entry.values,
      fields: entry.fields,
    }));
}
