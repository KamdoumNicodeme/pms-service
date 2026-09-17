applyHolderChanges(
  changeClientInformation: IChangeClientInformation,
  thirdPartyId: string,
  changes: ReadonlyMap<string, Resolution>
): IChangeClientInformation {

  const result: IChangeClientInformation =
    structuredClone(changeClientInformation);

  const client: IThirdParty | undefined = result.policy.clients.find(
    (item: IThirdParty): boolean =>
      item.thirdPartyId === thirdPartyId
  );

  if (!client) {
    return result;
  }

  if (client.type === 'PHYSICAL_PERSON') {
    this.applyPhysicalPersonChanges(
      client as IPhysicalPerson,
      changes
    );
  }

  return result;
}
