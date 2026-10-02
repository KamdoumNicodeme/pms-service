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
      : (
          data.changeClientInformation
            ? structuredClone(data.changeClientInformation)
            : this.changeService.createEmpty(data)
        );

  /*
   * Reset only the current holder to its reference state.
   * Policy changes and changes made to other holders are preserved.
   */
  const reset: IChangeClientInformation =
    this.changeService.resetHolder(
      current,
      data,
      event.thirdPartyId
    );

  /*
   * Reapply the complete current state of the holder changes.
   * Removed manual entries are no longer part of allHolderChanges,
   * so they will not be added back.
   */
  const updated: IChangeClientInformation =
    this.changeService.applyHolderChanges(
      reset,
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
