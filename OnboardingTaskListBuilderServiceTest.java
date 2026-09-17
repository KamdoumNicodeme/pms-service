protected onChangesChange(event: ComparisonChanges): void {
  console.log('1 - HOLDER RECEIVED CHANGES');
  console.log('changes =', event.changes);
  console.log('deletedEntries =', event.deletedEntries);

  this.changesChange.emit({
    thirdPartyId: this.holder().thirdPartyId,
    changes: event.changes,
    deletedEntries: event.deletedEntries
  });
}
