readonly options =
  input<readonly ComparisonOption[]>([]);

readonly displayValue = computed(() => {
  const value = this.resolution().value;

  if (!value) {
    return null;
  }

  const option =
    this.options().find(
      option => option.value === value
    );

  return option?.label ?? value;
});
