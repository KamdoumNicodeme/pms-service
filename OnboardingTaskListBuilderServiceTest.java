private updatePolicySectionChanges(
  event: ComparisonChanges
): ReadonlyMap<string, Resolution> {

  this.policySectionChanges.set(
    event.sectionId,
    event.changes
  );

  const allChanges = new Map<string, Resolution>();

  for (const changes of this.policySectionChanges.values()) {
    changes.forEach((resolution, key) => {
      allChanges.set(key, resolution);
    });
  }

  return allChanges;
}
