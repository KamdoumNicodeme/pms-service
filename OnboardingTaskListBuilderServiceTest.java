public applyHolderChanges(
  changeClientInformation: IChangeClientInformation,
  thirdPartyId: string,
  changes: ReadonlyMap<string, Resolution>,
  deletedEntries: readonly string[]
): IChangeClientInformation {

  // 1. On part toujours de la base actuelle
  let result: IChangeClientInformation =
    this.buildBase(changeClientInformation);

  // 2. Appliquer les suppressions
  deletedEntries.forEach((id: string): void => {

    if (id.startsWith('nationalities:manual-')) {
      result = this.removeNationality(
        result,
        thirdPartyId,
        id
      );
    }

  });

  // 3. Récupérer le client APRÈS les suppressions
  const client: IThirdParty | undefined =
    result.policy.clients.find(
      (item: IThirdParty): boolean =>
        item.thirdPartyId === thirdPartyId
    );

  if (!client) {
    return result;
  }

  // 4. Ne surtout pas réappliquer une entrée supprimée
  const activeChanges: Map<string, Resolution> =
    new Map<string, Resolution>(
      [...changes].filter(
        ([id]: [string, Resolution]): boolean =>
          !deletedEntries.includes(id)
      )
    );

  // 5. Appliquer les modifications restantes
  if (client.type === 'PHYSICAL_PERSON') {

    this.applyPhysicalPersonChanges(
      client as IPhysicalPerson,
      activeChanges
    );

    return result;
  }

  if (client.type === 'MORAL_PERSON') {

    this.applyMoralPersonChanges(
      client as IMoralPerson,
      activeChanges
    );

    return result;
  }

  return result;
}
