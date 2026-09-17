protected onHolderChanges(event: HolderChanges): void {
  const data: IClientProfilingData | null =
    this.getClientProfilingData();

  if (!data) {
    return;
  }

  let current: IChangeClientInformation | null =
    this.pendingChangeClientInformation();

  if (!current) {
    return;
  }

  // MODIFICATIONS / ADDITIONS
  current = this.changeService.applyHolderChanges(
    current,
    event.thirdPartyId,
    event.changes
  );

  // DELETIONS
  event.deletedEntries.forEach((id: string) => {
    if (id.startsWith('nationalities:manual-')) {
      current = this.changeService.removeNationality(
        current!,
        event.thirdPartyId,
        id
      );
    }
  });

  this.pendingChangeClientInformation.set(current);

  console.log(
    'CHANGE CLIENT INFORMATION AFTER APPLY',
    current
  );
}
