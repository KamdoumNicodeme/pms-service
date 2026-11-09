readonly choices = computed<readonly SourceChoice[]>(() => {
  const values = this.values();
  const availableSources = this.availableSources();

  const choices: SourceChoice[] = [
    {
      source: 'portal',
      label: CLIENT_PORTAL_DATA_SOURCE_NAME,
      value: values.portal,
      shortcut: '1'
    },
    {
      source: 'core',
      label: CORE_SYSTEM_DATA_SOURCE_NAME,
      value: values.core,
      shortcut: '2'
    },
    {
      source: 'manual',
      label: 'Type another value',
      value: this.manualValue() || null,
      shortcut: '3'
    }
  ];

  const filteredChoices = availableSources?.length
    ? choices.filter(choice => availableSources.includes(choice.source))
    : choices;

  return filteredChoices.map((choice, index) => ({
    ...choice,
    shortcut: `${index + 1}`
  }));
});
