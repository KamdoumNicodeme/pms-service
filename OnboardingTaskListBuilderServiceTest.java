protected onHolderChanges(event: HolderChanges): void {
  const data: IClientProfilingData | null = this.getClientProfilingData();

  if (!data) {
    return;
  }

  const allHolderChanges: ReadonlyMap<string, Resolution> =
    this.updateHolderSectionChanges(event);

  const current: IChangeClientInformation =
    this.pendingChangeClientInformation()
      ? structuredClone(this.pendingChangeClientInformation()!)
      : this.changeService.buildBase(data);

  const updated: IChangeClientInformation =
    this.changeService.applyHolderChanges(
      current,
      event.thirdPartyId,
      allHolderChanges
    );

  this.pendingChangeClientInformation.set(updated);

  console.log('HOLDER:', event.thirdPartyId);
  console.log('SECTION:', event.sectionId);
  console.log('ALL HOLDER CHANGES:', allHolderChanges);
  console.log(
    'CHANGE CLIENT INFORMATION AFTER HOLDER CHANGE:',
    structuredClone(updated)
  );
}
