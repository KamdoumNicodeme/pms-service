private listField(
  key: string,
  label: string,
  entryNoun: string,
  values: readonly string[],
  options: readonly ComparisonOption[] = []
): ComparisonListFieldDto {

  const hasOptions = options.length > 0;

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

        label: hasOptions
          ? options.find(
              option => option.value === value
            )?.label ?? this.normalizeCountry(value) ?? value
          : value,

        value,
      })),
    },
  };
}
