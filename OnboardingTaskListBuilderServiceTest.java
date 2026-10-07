const toEntries = (
  nationalities: readonly NationalityInfo[]
): ComparisonEntryDto[] =>
  nationalities
    .filter(nationality => !!nationality.country?.trim())
    .map((nationality): ComparisonEntryDto => ({
      key: nationality.country!,
      label:
        countryOptions.find(o => o.value === nationality.country)?.label
        ?? nationality.country!,

      fields: [
        {
          key: 'country',
          label: 'Nationality',
          value: nationality.country!,
          kind: 'select',
          options: countryOptions,
          editable: true,
        },
        {
          key: 'date',
          label: 'Date',
          value: this.formatDate(nationality.date),
          kind: 'text',
          editable: true,
        },
      ],
    }));
