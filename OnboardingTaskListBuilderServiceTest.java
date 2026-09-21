private listField(
  key: string,
  label: string,
  entryNoun: string,
  coreValue: readonly string[],
  digitalValue: readonly string[] = [],
  kycValue: readonly string[] = [],
  options: readonly ComparisonOption[] = []
): ComparisonListFieldDto {
  const toEntries = (values: readonly string[]) =>
    values.map((value: string) => ({
      key: value,
      label: options.find(
        (option: ComparisonOption) => option.value === value
      )?.label ?? value,
      value,
    }));

  return {
    key,
    label,
    kind: 'list',
    entryNoun,
    options,

    values: {
      digital: toEntries(digitalValue),
      kyc: toEntries(kycValue),
      core: toEntries(coreValue),
    },
  };
}
