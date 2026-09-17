protected onChangesChange(
  changes: ReadonlyMap<string, Resolution>
): void {

  console.log('1 - HOLDER RECEIVED CHANGES');

  changes.forEach((resolution, id) => {
    console.log('   id =', id);
    console.log('   resolution =', resolution);
  });

  console.log(
    '2 - deletedEntries =',
    this.deletedEntries()
  );

  this.changesChange.emit({
    thirdPartyId: this.holder().thirdPartyId,
    changes,
    deletedEntries: this.deletedEntries()
  });
}
