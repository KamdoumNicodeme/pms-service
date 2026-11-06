private getNationalityReasonKey(
  thirdPartyId: string,
  client: IPhysicalPerson,
  parsedId: ChangeIdParser
): string | null {
  const country = parsedId.parts[1];
  const field = parsedId.parts[2];

  const position = this.findNationalityPosition(client, country);

  if (!position) {
    return null;
  }

  const backendField = field === 'date' ? 'fromDate' : field;

  return `policy_clients_${thirdPartyId}_nationality_${position}_${backendField}`;
}

private findNationalityPosition(
  client: IPhysicalPerson,
  country: string
): 'first' | 'second' | 'third' | null {
  if (client.nationality?.first?.country === country) {
    return 'first';
  }

  if (client.nationality?.second?.country === country) {
    return 'second';
  }

  if (client.nationality?.third?.country === country) {
    return 'third';
  }

  return null;
}
