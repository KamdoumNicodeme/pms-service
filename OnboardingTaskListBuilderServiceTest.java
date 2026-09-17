protected onHolderChanges(event: HolderChanges): void {
  console.log('1 - onHolderChanges called', event);

  const data: IClientProfilingData | null =
    this.getClientProfilingData();

  console.log('2 - data', data);

  if (!data) {
    console.warn('STOP - data is null');
    return;
  }

  console.log(
    '3 - pendingChangeClientInformation',
    this.pendingChangeClientInformation()
  );

  console.log(
    '4 - data.changeClientInformation',
    data.changeClientInformation
  );

  const current: IChangeClientInformation | null =
    this.pendingChangeClientInformation() ??
    data.changeClientInformation ??
    null;

  console.log('5 - current', current);

  if (!current) {
    console.warn('STOP - current is null');
    return;
  }

  const updated: IChangeClientInformation =
    this.changeService.applyHolderChanges(
      current,
      event.thirdPartyId,
      event.changes
    );

  console.log('6 - updated', updated);

  this.pendingChangeClientInformation.set(updated);

  console.log(
    'CHANGE CLIENT INFORMATION AFTER APPLY',
    this.pendingChangeClientInformation()
  );
}
