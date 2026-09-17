protected onHolderChanges(event: HolderChanges): void {
  const data: IClientProfilingData | null =
    this.getClientProfilingData();

  if (!data) {
    return;
  }

  let current: IChangeClientInformation =
    this.pendingChangeClientInformation() ??
    data.changeClientInformation ??
    this.changeService.buildBase(data);

  // 1. Apply additions / modifications
  current = this.changeService.applyHolderChanges(
    current,
    event.thirdPartyId,
    event.changes
  );

  // 2. Apply deletions
  event.deletedEntries.forEach((id: string) => {
    if (id.startsWith('nationalities:manual-')) {
      current = this.changeService.removeNationality(
        current,
        event.thirdPartyId,
        id
      );
    }
  });

  // 3. Keep the updated working copy
  this.pendingChangeClientInformation.set(current);

  console.log(
    'CHANGE CLIENT INFORMATION AFTER APPLY',
    current
  );
}
