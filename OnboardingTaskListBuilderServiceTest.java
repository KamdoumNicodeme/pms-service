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

      core: values.map((tax: ITaxInformation, index: number) => ({
        key: `${tax.taxCountry}-${tax.taxNumber}-${index}`,

        label:
          `Tax Country: ${
            this.normalizeCountry(tax.taxCountry) ?? tax.taxCountry
          }`,

        value:
          `TIN: ${tax.taxNumber ?? ''}`
      }))
    }
  };
}
