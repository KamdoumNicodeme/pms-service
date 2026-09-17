addManualStructuredEntry(
    fieldKey: string,
    fields: readonly StructuredEntryValue[]
): string {

    const entryKey = `manual-${++this.manualSequence}`;

    this.manualStructuredEntries.update(
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

    // Register every field of the manual structured entry as a change.
    this.overrides.update(current => {

        const next = new Map(current);

        fields.forEach((field: StructuredEntryValue): void => {

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
