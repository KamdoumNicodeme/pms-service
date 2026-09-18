submitStructured(): void {
    const definitions: readonly ComparisonEntryFieldDefinition[] =
        this.row().entryFields ?? [];

    const fields: { key: string; value: string | null }[] =
        definitions.map((definition: ComparisonEntryFieldDefinition) => {
            const value: string = this.fieldValue(definition.key).trim();

            return {
                key: definition.key,
                value: value === '' ? null : value,
            };
        });

    // Prevent duplicate Tax Country for Tax Information.
    if (this.row().key === 'tax-information') {
        const taxCountry: string | null =
            fields.find(field => field.key === 'tax-country')?.value ?? null;

        if (taxCountry && this.hasDuplicateTaxCountry(taxCountry)) {
            this.addError.set(
                'Tax information already exists for this country.'
            );
            return;
        }
    }

    this.addError.set(null);

    this.addStructuredEntry.emit({
        fields,
    });

    this.closeStructuredModal();
}
