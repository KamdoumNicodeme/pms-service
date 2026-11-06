private getNationalityReasonKey(
  sourceHolder: IThirdParty,
  parsedId: ChangeIdParser
): string | null {
  if (!this.isPhysicalPerson(sourceHolder)) {
    return null;
  }

  const country = parsedId.parts[1];
  const field = parsedId.parts[2];

  if (!country || !field) {
    return null;
  }

  const position = this.getNationalityPosition(
    sourceHolder,
    country
  );

  if (!position) {
    return null;
  }

  const backendField =
    field === 'date'
      ? 'fromDate'
      : field;

  return `policy_clients_${sourceHolder.thirdPartyId}_nationality_${position}_${backendField}`;
}
