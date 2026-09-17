public applyHolderChanges(
  changeClientInformation: IChangeClientInformation,
  thirdPartyId: string,
  changes: ReadonlyMap<string, Resolution>
): IChangeClientInformation {

  const result: IChangeClientInformation =
    structuredClone(changeClientInformation);

  const client: IThirdParty | undefined =
    result.policy.clients.find(
      (item: IThirdParty) =>
        item.thirdPartyId === thirdPartyId
    );

  if (!client) {
    console.warn(
      '[ClientProfilingChangeService] Client not found:',
      thirdPartyId
    );

    return result;
  }

  // ============================================================
  // PHYSICAL PERSON
  // ============================================================

  if (client.type === 'PHYSICAL_PERSON') {

    this.applyPhysicalPersonChanges(
      client as IPhysicalPerson,
      changes
    );

    return result;
  }

  // ============================================================
  // MORAL PERSON
  // ============================================================

  if (client.type === 'MORAL_PERSON') {

    this.applyMoralPersonChanges(
      client as IMoralPerson,
      changes
    );

    return result;
  }

  return result;
}
