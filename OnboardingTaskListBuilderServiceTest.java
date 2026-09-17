protected onHolderChanges(event: HolderChanges): void {
  const data: IClientProfilingData | null =
    this.getClientProfilingData();

  if (!data) {
    return;
  }

  const current: IChangeClientInformation | null =
    this.pendingChangeClientInformation() ??
    data.changeClientInformation ??
    null;

  if (!current) {
    return;
  }

  const updated: IChangeClientInformation =
    this.changeService.applyHolderChanges(
      current,
      event.thirdPartyId,
      event.changes
    );

  this.pendingChangeClientInformation.set(updated);

  console.log(
    'CHANGE CLIENT INFORMATION AFTER APPLY',
    updated
  );
}
