const nationalityRemovals = removedEntries.filter(
  (entry: RemovedEntry) => entry.id.startsWith('nationalities:')
);

const otherRemovals = removedEntries.filter(
  (entry: RemovedEntry) => !entry.id.startsWith('nationalities:')
);
