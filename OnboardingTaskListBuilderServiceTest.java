private applyTaxInformationChange(
  client: IThirdParty,
  sourceHolder: IThirdParty,
  id: string,
  resolution: Resolution
): void {
  const parts = id.split(':');
  if (parts.length < 3) return;

  const taxKey = parts[1];
  const field = parts.slice(2).join(':');

  client.taxInformations ??= [];

  let taxInformation = client.taxInformations.find(
    tax => tax.taxCountry === taxKey
  );

  if (!taxInformation) {
    const sourceTaxInformation = sourceHolder.taxInformations?.find(
      tax => tax.taxCountry === taxKey
    );

    if (!sourceTaxInformation) return;

    taxInformation = structuredClone(sourceTaxInformation);
    client.taxInformations.push(taxInformation);
  }

  const val = this.stringValue(resolution.value);

  if (field === 'tax-country') {
    taxInformation.taxCountry = val;
  }

  if (field === 'tin') {
    taxInformation.taxNumber = val;
  }

  if (field === 'tin-unavailable-reason') {
    taxInformation.missingTaxNumberReason = val;
  }

  if (field === 'is-default') {
    taxInformation.preferred = val === 'true';
  }
}
