private selectField(
  key: string,
  label: string,
  value: string | null | undefined,
  options: readonly ComparisonOption[],
  editable = true,
  digitalValue: string | null | undefined = null
): ComparisonScalarFieldDto {
  return {
    key,
    label,
    kind: 'select',
    editable,
    options,
    values: {
      digital: this.normalize(digitalValue),
      kyc: null,
      core: this.normalize(value),
    },
  };
}
