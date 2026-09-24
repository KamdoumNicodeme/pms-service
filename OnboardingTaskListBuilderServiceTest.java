protected onHolderChanges(event: HolderChanges): void {
  const data: IClientProfilingData | null =
    this.getClientProfilingData();

  if (!data) {
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
      event.thirdPartyId,
      allHolderChanges
    );

  this.pendingChangeClientInformation.set(updated);

  console.log(
    'CHANGE CLIENT INFORMATION:',
    structuredClone(updated)
  );
}
