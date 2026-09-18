private updateHolderSectionChanges(
    event: HolderChanges
): ReadonlyMap<string, Resolution> {

    const holders = new Map(this.holderSectionChanges());

    const sections = new Map<
        string,
        ReadonlyMap<string, Resolution>
    >(
        holders.get(event.thirdPartyId) ?? []
    );

    /*
     * Replace the complete state of the current section.
     * This also removes entries that no longer exist in the store.
     */
    sections.set(
        event.sectionId,
        new Map(event.changes)
    );

    holders.set(
        event.thirdPartyId,
        sections
    );

    this.holderSectionChanges.set(holders);

    /*
     * Merge the current changes from every section
     * belonging to this holder.
     */
    const allChanges = new Map<string, Resolution>();

    sections.forEach(
        (sectionChanges: ReadonlyMap<string, Resolution>) => {

            sectionChanges.forEach(
                (resolution: Resolution, id: string) => {
                    allChanges.set(id, resolution);
                }
            );
        }
    );

    return allChanges;
}
