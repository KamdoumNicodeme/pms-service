protected onPolicyChanges(event: ComparisonChanges): void {
  const data: IClientProfilingData | null =
    this.getClientProfilingData();

  if (!data) {
    return;
  }

  const current: IChangeClientInformation =
    this.pendingChangeClientInformation()
      ? structuredClone(this.pendingChangeClientInformation()!)
      : this.changeService.buildBase(data);

  const updated: IChangeClientInformation =
    this.changeService.applyPolicyChanges(
      current,
      event.changes
    );

  this.pendingChangeClientInformation.set(updated);

  console.log(
    'CHANGE CLIENT INFORMATION AFTER POLICY CHANGE',
    structuredClone(updated)
  );
}
