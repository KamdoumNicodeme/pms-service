private nationalityListField(
  key: string,
  label: string,
  entryNoun: string,
  digital: readonly NationalityEntry[],
  kyc: readonly NationalityEntry[],
  core: readonly NationalityEntry[],
  countryOptions: readonly ComparisonOption[]
): ComparisonListFieldDto {

  const toEntries = (
    nationalities: readonly NationalityEntry[]
  ): ComparisonEntryDto[] =>
    nationalities.map(nationality => ({
      key: nationality.country,

      label:
        countryOptions.find(
          option => option.value === nationality.country
        )?.label ?? nationality.country,

      fields: [
        {
          key: 'country',
          label: 'Nationality',
          value: nationality.country,
          kind: 'select',
          options: countryOptions,
          editable: true,
        },
        {
          key: 'date',
          label: 'Date',
          value: nationality.date ?? null,
          kind: 'text',
          editable: true,
        }
      ]
    }));

  return {
    key,
    label,
    kind: 'list',
    entryNoun,

    entryFields: [
      {
        key: 'country',
        label: 'Nationality',
        kind: 'select',
        editable: true,
        options: countryOptions,
      },
      {
        key: 'date',
        label: 'Date',
        kind: 'text',
        editable: true,
      }
    ],

    values: {
      digital: toEntries(digital),
      kyc: toEntries(kyc),
      core: toEntries(core),
    }
  };
}
