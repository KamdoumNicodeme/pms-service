protected onPolicyChanges(event: ComparisonChanges): void {
  const data: IClientProfilingData | null =
    this.getClientProfilingData();

  if (!data) {
    return;
  }

  const allPolicyChanges =
    this.updatePolicySectionChanges(event);

  const current: IChangeClientInformation =
    this.pendingChangeClientInformation()
      ?? (
        data.changeClientInformation
          ? structuredClone(data.changeClientInformation)
          : this.changeService.createEmpty(data)
      );

  const updated: IChangeClientInformation =
    this.changeService.applyPolicyChanges(
      structuredClone(current),
      allPolicyChanges
    );

  this.pendingChangeClientInformation.set(updated);

  console.log('POLICY SECTION:', event.sectionId);
  console.log('ALL POLICY CHANGES:', allPolicyChanges);
  console.log(
    'CHANGE CLIENT INFORMATION AFTER POLICY CHANGE:',
    structuredClone(updated)
  );
}
