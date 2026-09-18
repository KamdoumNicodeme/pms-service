private hasDuplicateTaxCountry(taxCountry: string): boolean {
    const normalizedTaxCountry: string =
        taxCountry.trim().toUpperCase();

    return this.row().entries.some(entry => {

        // First check the current resolved child value.
        const resolvedTaxCountry = entry.children.find(
            child => child.key === 'tax-country'
        )?.resolution.value;

        if (
            resolvedTaxCountry?.trim().toUpperCase() ===
            normalizedTaxCountry
        ) {
            return true;
        }

        // Then check values coming from Core, Digital or KYC.
        return Object.values(entry.fields ?? {}).some(fields =>
            fields?.some(field =>
                field.key === 'tax-country' &&
                field.value?.trim().toUpperCase() ===
                    normalizedTaxCountry
            ) ?? false
        );
    });
}
