private entryFieldValue(
  paired: PairedEntry,
  source: ComparisonSourceId,
  fieldKey: string
): string | null {
  return paired.fields[source]
    ?.find(field => field.key === fieldKey)
    ?.value ?? null;
}
