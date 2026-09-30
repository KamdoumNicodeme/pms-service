private updatePolicySectionChanges(
  event: ComparisonChanges
): ReadonlyMap<string, Resolution> {

  this.policySectionChanges.update(current => {
    const updated = new Map(current);

    updated.set(
      event.sectionId,
      new Map(event.changes)
    );

    return updated;
  });

  const allChanges = new Map<string, Resolution>();

  for (const sectionChanges of this.policySectionChanges().values()) {
    sectionChanges.forEach((resolution, key) => {
      allChanges.set(key, resolution);
    });
  }

  return allChanges;
}
