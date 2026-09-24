public applyHolderChanges(
  changeClientInformation: IChangeClientInformation,
  sourceHolder: IThirdParty,
  changes: ReadonlyMap<string, Resolution>
): IChangeClientInformation {

  const result: IChangeClientInformation =
    structuredClone(changeClientInformation);

  result.policy.clients ??= [];

  let client: IThirdParty | undefined =
    result.policy.clients.find(
      (item: IThirdParty): boolean =>
        item.thirdPartyId === sourceHolder.thirdPartyId
    );

  /*
   * CCI/Connect does not contain the client yet.
   * Create ONLY the minimum structure required for the partial update.
   *
   * Do NOT copy the Snapshot holder.
   */
  if (!client) {
    if (sourceHolder.type === 'PHYSICAL_PERSON') {
      client = {
        thirdPartyId: sourceHolder.thirdPartyId,
        type: 'PHYSICAL_PERSON'
      } as IPhysicalPerson;
    } else if (sourceHolder.type === 'MORAL_PERSON') {
      client = {
        thirdPartyId: sourceHolder.thirdPartyId,
        type: 'MORAL_PERSON'
      } as IMoralPerson;
    } else {
      console.warn(
        '[ClientProfilingChangeService] Unsupported client type:',
        sourceHolder.type
      );

      return result;
    }

    result.policy.clients.push(client);
  }

  if (client.type === 'PHYSICAL_PERSON') {
    this.applyPhysicalPersonChanges(
      client as IPhysicalPerson,
      changes
    );

    return result;
  }

  if (client.type === 'MORAL_PERSON') {
    this.applyMoralPersonChanges(
      client as IMoralPerson,
      changes
    );

    return result;
  }

  return result;
}
