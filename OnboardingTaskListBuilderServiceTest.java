public applyHolderChanges(
  changeClientInformation: IChangeClientInformation,
  thirdPartyId: string,
  changes: ReadonlyMap<string, Resolution>,
  deletedEntries: readonly string[]
): IChangeClientInformation {

  let result: IChangeClientInformation =
    structuredClone(changeClientInformation);

  const client: IThirdParty | undefined =
    result.policy.clients.find(
      (item: IThirdParty): boolean =>
        item.thirdPartyId === thirdPartyId
    );

  if (!client) {
    return result;
  }

  // 1 - APPLY CHANGES
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

  // 2 - APPLY DELETIONS AFTER CHANGES
  deletedEntries.forEach((id: string): void => {

    if (id.startsWith('nationalities:manual-')) {
      result = this.removeNationality(
        result,
        thirdPartyId,
        id
      );
    }

  });

  return result;
}
