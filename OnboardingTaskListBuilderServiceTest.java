private getReasonKey(
  sourceHolder: IThirdParty,
  id: string
): string | null {
  const parsedId: ChangeIdParser = ChangeIdParser.parse(id);

  if (parsedId.prefix === 'nationalities') {
    return this.getNationalityReasonKey(
      sourceHolder,
      parsedId
    );
  }

  const field = this.getSimpleFieldName(parsedId.prefix);

  return `policy_clients_${sourceHolder.thirdPartyId}_${field}`;
}
