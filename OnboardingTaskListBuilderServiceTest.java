protected onHolderChanges(event: HolderChanges): void {
  const data: IClientProfilingData | null =
    this.getClientProfilingData();

  if (!data) {
    return;
  }

  const digitalHolder: IThirdParty | undefined =
    data.initialBusinessData.policy.clients?.find(
      (client: IThirdParty): boolean =>
        client.thirdPartyId === event.thirdPartyId
    );

  if (!digitalHolder) {
    console.warn(
      'Digital holder not found:',
      event.thirdPartyId
    );
    return;
  }

  const allHolderChanges: ReadonlyMap<string, Resolution> =
    this.updateHolderSectionChanges(event);

  const current: IChangeClientInformation =
    this.pendingChangeClientInformation()
    ?? (
      data.changeClientInformation
        ? structuredClone(data.changeClientInformation)
        : this.changeService.createEmpty(data)
    );

  const updated: IChangeClientInformation =
    this.changeService.applyHolderChanges(
      current,
      digitalHolder,
      allHolderChanges
    );

  this.pendingChangeClientInformation.set(updated);

  console.log(
    'CHANGE CLIENT INFORMATION:',
    structuredClone(updated)
  );
}
