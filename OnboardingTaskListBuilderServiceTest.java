core: values.map((tax: ITaxInformation) => ({
  key: tax.taxCountry,

  fields: [
    {
      key: 'tax-country',
      label: 'Tax Country',
      value: this.normalize(tax.taxCountry),
      kind: 'select',
      editable: false,
      options: this.countryOptions,
    },

    {
      key: 'tin',
      label: 'TIN',
      value: tax.taxNumber ?? null,
      kind: 'text',
      editable: true,
    },

    ...(!tax.taxNumber?.trim()
      ? [
          {
            key: 'tin-unavailable-reason',
            label: 'Reason if TIN Unavailable',
            value: tax.tinUnavailableReason ?? null,
            kind: 'select' as const,
            editable: true,
            options: this.taxUnavailableOptions,
          },
        ]
      : []),
  ],
})),
