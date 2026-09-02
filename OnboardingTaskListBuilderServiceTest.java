private composeEntryFields(
  fields: readonly ComparisonEntryFieldDto[] | null
): string | null {

  if (!fields?.length) {
    return null;
  }

  const values = fields
    .filter(field => field.value != null)
    .map(field => `${field.label ?? field.key}: ${field.value}`);

  return values.length > 0
    ? values.join(' | ')
    : null;
}
