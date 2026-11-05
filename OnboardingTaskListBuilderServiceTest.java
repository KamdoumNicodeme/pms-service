protected readonly progress = computed(() => {
  const values = this.formValues();

  const activeChecks = this.checkRegistry.filter(check =>
    check.isVisible(values)
  );

  const total = activeChecks.length;

  const completed = activeChecks.filter(check => {
    const value = values[check.name as keyof typeof values];

    return typeof value === 'boolean'
      ? value
      : !UNANSWERED.includes(
          (value ?? '').toString().trim().toLowerCase()
        );
  }).length;

  return { total, completed };
});
