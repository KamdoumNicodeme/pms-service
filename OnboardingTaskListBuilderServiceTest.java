private resetPendingChanges(): void {
  this.pendingChangeClientInformation.set(null);
  this.holderSectionChanges.set(new Map());
  this.holderSectionRemovedEntries.set(new Map());
  this.policySectionChanges.set(new Map());
}
