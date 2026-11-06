private getNationalityReasonKey(
  client: IThirdParty,
  sourceHolder: IThirdParty,
  parsedId: ChangeIdParser
): string | null {
  if (
    !this.isPhysicalPerson(sourceHolder) ||
    !this.isPhysicalPerson(client)
  ) {
    return null;
  }

  const identifier: string = parsedId.parts[1];
  const field: string = parsedId.parts[2];

  if (!identifier || !field) {
    return null;
  }

  const position:
    | 'first'
    | 'second'
    | 'third'
    | null = parsedId.isManual()
      ? this.getManualNationalityPosition(
          client,
          sourceHolder,
          parsedId
        )
      : this.getNationalityPosition(
          sourceHolder,
          identifier
        ) ?? this.getNationalityPosition(
          client,
          identifier
        );

  if (!position) {
    return null;
  }

  const backendField: string =
    field === 'date'
      ? 'fromDate'
      : field;

  return `policy_clients_${sourceHolder.thirdPartyId}_nationality_${position}_${backendField}`;
}
