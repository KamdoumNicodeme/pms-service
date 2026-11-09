private applyTaxInformationChange(
  taxInformationByKey: ReadonlyMap<string, ITaxInformation>,
  parsedId: ChangeIdParsed,
  resolution: Resolution
): void {
  if (!parsedId.subType) {
    return;
  }

  const taxInformation: ITaxInformation | undefined =
    taxInformationByKey.get(parsedId.subType);

  if (!taxInformation) {
    return;
  }

  const value: string = this.stringValue(resolution.value);

  if (parsedId.field === 'tax-country') {
    taxInformation.taxCountry = value;
  }

  if (parsedId.field === 'tin') {
    taxInformation.taxNumber = value;
  }

  if (parsedId.field === 'tin-unavailable-reason') {
    taxInformation.missingTaxNumberReason = value;
  }

  if (parsedId.field === 'is-default') {
    taxInformation.preferred = value === 'true';
  }
}
