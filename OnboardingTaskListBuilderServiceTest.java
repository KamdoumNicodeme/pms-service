private hasDuplicateTaxCountry(): boolean {
    if (this.row().key !== 'tax-information') {
        return false;
    }

    const newTaxCountry: string =
        (this.fieldValue('tax-country') ?? '').trim();

    if (!newTaxCountry) {
        return false;
    }

    return this.row().entries.some(entry => {

        const taxCountry = entry.children.find(
            child => child.key === 'tax-country'
        );

        return taxCountry?.resolution.value?.trim() === newTaxCountry;
    });
}

protected submitStructured(): void {
    if (!this.canSubmitStructured()) {
        return;
    }

    if (this.hasDuplicateTaxCountry()) {
        this.addError.set(
            'Tax information already exists for this country.'
        );

        return;
    }

    this.addError.set(null);

    const fields: Record<string, string> = {};

    for (const field of this.row().entryFields ?? []) {
        fields[field.key] =
            this.fieldValue(field.key)?.trim() ?? '';
    }

    this.addStructuredEntry.emit({
        fields
    });

    this.closeStructuredModal();
}
