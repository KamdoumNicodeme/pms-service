if (client.type === 'PHYSICAL_PERSON') {
  this.applyPhysicalPersonChanges(
    client as IPhysicalPerson,
    changes
  );

  this.applyPhysicalPersonDeletes(
    client as IPhysicalPerson,
    deletedEntries
  );
}

private applyPhysicalPersonDeletes(
  client: IPhysicalPerson,
  deletedEntries: readonly string[]
): void {
  deletedEntries.forEach(id => {
    if (id.startsWith('nationalities:manual-')) {
      this.removeNationality(client, id);
    }
  });
}
