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

      core: values.map(tax => ({
        key: `${tax.taxCountry}-${tax.tin}`,
        label: this.normalizeCountry(tax.taxCountry) ?? tax.taxCountry,
        value: tax.tin
      }))
    }
  };
}
