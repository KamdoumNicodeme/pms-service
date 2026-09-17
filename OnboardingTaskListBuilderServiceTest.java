public applyHolderChanges(
  changeClientInformation: IChangeClientInformation,
  thirdPartyId: string,
  changes: ReadonlyMap<string, Resolution>,
  deletedEntries: readonly string[]
): IChangeClientInformation {

  const result: IChangeClientInformation =
    structuredClone(changeClientInformation);

  const client: IThirdParty | undefined =
    result.policy.clients.find(
      (item: IThirdParty): boolean =>
        item.thirdPartyId === thirdPartyId
    );

  if (!client) {
    return result;
  }

  // 1. Apply modifications first
  if (client.type === 'PHYSICAL_PERSON') {
    this.applyPhysicalPersonChanges(
      client as IPhysicalPerson,
      changes
    );
  }

  if (client.type === 'MORAL_PERSON') {
    this.applyMoralPersonChanges(
      client as IMoralPerson,
      changes
    );
  }

  // 2. Apply deletions LAST
  for (const id of deletedEntries) {

    if (
      client.type === 'PHYSICAL_PERSON' &&
      id.startsWith('nationalities:manual-')
    ) {
      this.removeNationalityFromClient(
        client as IPhysicalPerson,
        id
      );
    }

    // Emails / phones / tax etc. will be added here later.
  }

  return result;
}
