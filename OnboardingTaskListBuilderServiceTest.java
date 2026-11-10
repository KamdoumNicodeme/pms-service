readonly displayValue = computed<string | null>(() => {
  const value = this.resolution().value;

  if (!value) {
    return null;
  }

  const option = this.options().find(
    option => option.value === value
  );

  console.log('Resolution value:', value);
  console.log('Matching option:', option);

  return option?.label ?? value;
});
