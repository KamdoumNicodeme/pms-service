private readonly italianBirthTownLabels =
  signal<ComparisonOption[]>([]);

readonly getItalianBirthTownLabels =
  this.italianBirthTownLabels.asReadonly();

addItalianBirthTownOptions(references: IReference[]): void {
  const options: ComparisonOption[] = references.map(reference => ({
    value: reference.value,
    label: reference.key
  }));

  this.italianBirthTownLabels.update(current => {
    const merged = new Map(
      current.map(option => [option.value, option])
    );

    options.forEach(option => merged.set(option.value, option));

    return [...merged.values()];
  });
}
