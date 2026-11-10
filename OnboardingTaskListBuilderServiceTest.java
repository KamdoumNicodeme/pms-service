private mapItalianBirthTownCodes(
  references: IReference[]
): ComparisonOption[] {
  return references.map(reference => ({
    value: reference.key,
    label: reference.value
  }));
}
