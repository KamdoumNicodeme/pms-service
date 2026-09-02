private taxInformationList(
  values: readonly ITaxInformation[]
): ComparisonListFieldDto {
  return {
    key: 'tax-information',
    label: 'Tax Information',
    kind: 'list',
    entryNoun: 'tax information',

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
              ?? tax.taxCountry
          },
          {
            key: 'tin',
            label: 'TIN',
            value: tax.taxNumber
          }
        ]
      }))
    }
  };
}
