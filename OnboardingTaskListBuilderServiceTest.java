private listField(
  key: string,
  label: string,
  entryNoun: string,
  values: readonly string[],
): ComparisonListFieldDto {
  return {
    key,
    label,
    kind: 'list',
    entryNoun,
    values: {
      digital: [],
      kyc: [],
      core: values.map((value: string) => ({
        key: value,
        label: value,
        value,
      })),
    },
  };
}
