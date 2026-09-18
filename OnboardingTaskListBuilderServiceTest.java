private textField(
  key: string,
  label: string,
  value: string | null | undefined,
  editable = true,
  digitalValue: string | null | undefined = null
): ComparisonScalarFieldDto {
  return {
    key,
    label,
    kind: 'text',
    editable,
    values: {
      digital: this.normalize(digitalValue),
      kyc: null,
      core: this.normalize(value),
    },
  };
}
