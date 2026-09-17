protected onHolderChanges(event: HolderChanges): void {
  const data: IClientProfilingData | null =
    this.getClientProfilingData();

  if (!data) {
    return;
  }

  const current: IChangeClientInformation =
    this.pendingChangeClientInformation() ??
    this.changeService.buildBase(data);

  const updated: IChangeClientInformation =
    this.changeService.applyHolderChanges(
      current,
      event.thirdPartyId,
      event.changes
    );

  this.pendingChangeClientInformation.set(updated);

  const client: IThirdParty | undefined =
    updated.policy.clients.find(
      (item: IThirdParty) =>
        item.thirdPartyId === event.thirdPartyId
    );

  console.log(
    'CHANGE CLIENT INFORMATION AFTER APPLY',
    client
  );
}
