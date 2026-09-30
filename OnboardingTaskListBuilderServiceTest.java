private updatePolicySectionChanges(
  event: ComparisonChanges
): ReadonlyMap<string, Resolution> {

  const sections =
    new Map<string, ReadonlyMap<string, Resolution>>(
      this.policySectionChanges()
    );

  /*
   * Replace the complete state of the current section.
   * This also removes entries that no longer exist in the store.
   */
  sections.set(
    event.sectionId,
    new Map(event.changes)
  );

  this.policySectionChanges.set(sections);

  /* Merge the current changes from every section. */
  const allChanges = new Map<string, Resolution>();

  sections.forEach(
    (sectionChanges: ReadonlyMap<string, Resolution>): void => {

      sectionChanges.forEach(
        (resolution: Resolution, id: string): void => {
          allChanges.set(id, resolution);
        }
      );

    }
  );

  return allChanges;
}
