addManualStructuredEntry(
    fieldKey: string,
    fields: readonly StructureEntryValue[]
): string {

    const entryKey = `manual-${++this.manualSequence}`;

    this.manualStructureEntries.update(
        (current: readonly ManualEntryStructure[]) => [
            ...current,
            {
                fieldKey,
                entryKey,
                fields,
            },
        ]
    );

    const id = `${fieldKey}:${entryKey}`;

    // Register all structured fields as applied changes immediately.
    this.overrides.update(current => {
        const next = new Map(current);

        fields.forEach((field: StructureEntryValue): void => {
            next.set(`${id}:${field.key}`, {
                source: 'manual',
                value: field.value,
                reason: 'Confirmed with client',
                comment: '',
                reviewed: true,
            });
        });

        return next;
    });

    this.focusedId.set(id);

    return id;
}
