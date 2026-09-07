private taxInformationList(
  values: readonly ITaxInformation[]
): ComparisonListFieldDto {
  return {
    key: 'tax-information',
    label: 'Tax Information',
    kind: 'list',
    entryNoun: 'tax information',

    entryFields: [
      {
        key: 'tax-country',
        label: 'Tax Country',
        kind: 'text',
        editable: false,
      },
      {
        key: 'tin',
        label: 'TIN',
        kind: 'text',
        editable: true,
      },
      {
        key: 'tin-unavailable-reason',
        label: 'Reason if TIN Unavailable',
        kind: 'select',
        editable: true,
        options: this.tinUnavailableReasonOptions,
      },
    ],

    values: {
      digital: [],
      kyc: [],

      core: values.map((tax: ITaxInformation) => ({
        key: tax.taxCountry,

        fields: [
          {
            key: 'tax-country',
            label: 'Tax Country',
            value:
              this.normalizeCountry(tax.taxCountry)
              ?? tax.taxCountry,
          },
          {
            key: 'tin',
            label: 'TIN',
            value: tax.taxNumber ?? null,
          },
          {
            key: 'tin-unavailable-reason',
            label: 'Reason if TIN Unavailable',
            value: tax.tinUnavailableReason ?? null,
          },
        ],
      })),
    },
  };
}
