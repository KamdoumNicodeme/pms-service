applyHolderChanges(
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

  if (client.type === 'PHYSICAL_PERSON') {
    const physicalPerson = client as IPhysicalPerson;

    // Modifications + ajouts
    this.applyPhysicalPersonChanges(
      physicalPerson,
      changes
    );

    // Suppressions
    deletedEntries.forEach((id: string) => {
      if (id.startsWith('nationalities:manual-')) {
        this.removeNationalityFromClient(
          physicalPerson,
          id
        );
      }
    });

    return result;
  }

  // MORAL PERSON
  if (client.type === 'MORAL_PERSON') {
    this.applyMoralPersonChanges(
      client as IMoralPerson,
      changes
    );

    return result;
  }

  return result;
}
