protected onPolicyChanges(event: ComparisonChanges): void {
  const data = this.getClientProfilingData();

  if (!data) {
    return;
  }

  const allPolicyChanges =
    this.updatePolicySectionChanges(event);

  const policy =
    this.changeClientInformationForm.controls.policy.value;

  const current: IChangeClientInformation = policy
    ? { policy: structuredClone(policy) }
    : data.changeClientInformation
      ? structuredClone(data.changeClientInformation)
      : this.changeService.createEmpty(data);

  const updated = this.changeService.applyPolicyChanges(
    current,
    allPolicyChanges
  );

  this.changeClientInformationForm.patchValueCustom(updated);
  this.changeClientInformationForm.markAsDirty();
}
