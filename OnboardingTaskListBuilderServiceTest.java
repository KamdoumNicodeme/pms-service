private getSimpleChangeReasonPath(
  parsedId: ChangeIdParser
): string {
  const fields: Record<string, string> = {
    thirdPartyId: 'thirdPartyId',
    salutation: 'salutation',
    lastname: 'lastname',
    firstname: 'firstName',
    'birth-date': 'birthDate',
    'birth-country': 'birthCountry',
    'spouse-name': 'spouseName',
    status: 'civilStatus_status',
    profession: 'professionalDetails_profession',
    'profession-status': 'professionalDetails_status',
    'employer-name': 'professionalDetails_companyName',
    sector: 'professionalDetails_sector'
  };

  return fields[parsedId.prefix] ?? parsedId.prefix;
}

private getNationalityChangeReasonPath(
  parsedId: ChangeIdParser,
  client: IThirdParty
): string | null {
  const rawPosition = parsedId.parts[1];
  const rawField = parsedId.parts[2];

  const position = rawPosition.startsWith('manual-')
    ? this.getManualNationalityPosition(client, rawPosition)
    : this.getNationalityPosition(rawPosition);

  if (!position) {
    return null;
  }

  const field = this.getNationalityField(rawField);

  if (!field) {
    return null;
  }

  return `nationality_${position}_${field}`;
}
