protected onHolderChanges(event: ComparisonChanges): void {
  const data = this.getClientProfilingData();

  if (!data) {
    return;
  }

  const allHolderChanges =
    this.updateHolderSectionChanges(event);

  const currentPolicy =
    this.changeClientInformationForm.controls.policy.value;

  const current: IChangeClientInformation = currentPolicy
    ? { policy: structuredClone(currentPolicy) }
    : data.changeClientInformation
      ? structuredClone(data.changeClientInformation)
      : this.changeService.createEmpty(data);

  const updated = this.changeService.applyHolderChanges(
    structuredClone(current),
    allHolderChanges
  );

  this.changeClientInformationForm.patchValueCustom(updated);
  this.changeClientInformationForm.markAsDirty();

  console.log(
    'HOLDER UPDATED FORM',
    this.changeClientInformationForm.getRawValue()
  );
}
