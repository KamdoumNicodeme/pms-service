const toEntries = (
  nationalities: readonly NationalityEntry[]
): ComparisonEntryDto[] =>
  nationalities
    .filter(entry => !!entry.value?.country)
    .map(entry => ({
      key: entry.key,
      label:
        countryOptions.find(
          (option: ComparisonOption) =>
            option.value === entry.value.country
        )?.label ?? entry.value.country,

      fields: [
        {
          key: 'country',
          label: 'Nationality',
          value: entry.value.country,
          kind: 'select',
          options: countryOptions,
          editable: true,
        },
        {
          key: 'date',
          label: 'Obtaining date',
          value: this.formatDate(entry.value.fromDate),
          kind: 'date',
          editable: true,
        },
      ],
    }));
