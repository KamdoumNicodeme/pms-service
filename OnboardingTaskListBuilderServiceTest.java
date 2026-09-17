if (client.type === 'PHYSICAL_PERSON') {
  const physicalPerson = client as IPhysicalPerson;

  deletedEntries.forEach(id => {
    if (id.startsWith('nationalities:manual-')) {
      this.removeNationalityFromClient(physicalPerson, id);
    }
  });

  const activeChanges = new Map(
    [...changes].filter(([id]) => !deletedEntries.includes(id))
  );

  this.applyPhysicalPersonChanges(
    physicalPerson,
    activeChanges
  );

  return result;
}
