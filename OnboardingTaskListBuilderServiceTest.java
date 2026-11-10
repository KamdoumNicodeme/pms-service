protected readonly displayedOptions = computed((): ComparisonOption[] => {
  return this.isItalianBirthTownCode()
    ? this.italianBirthTownCodeOptions()
    : [...this.options()];
});
