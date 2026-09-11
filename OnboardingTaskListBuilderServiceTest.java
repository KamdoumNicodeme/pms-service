private listField(
  key: string,
  label: string,
  entryNoun: string,
  values: readonly string[],
  options: readonly ComparisonOption[] = []
): ComparisonListFieldDto {

  return {
    key,
    label,
    kind: 'list',
    entryNoun,
    options,

    values: {
      digital: [],
      kyc: [],

      core: values.map(value => ({
        key: value,
        label:
          options.find(option => option.value === value)?.label
          ?? value,
        value,
      })),
    },
  };
}
