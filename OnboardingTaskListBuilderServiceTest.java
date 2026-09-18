private hasDuplicateTaxCountry(taxCountry: string): boolean {
    return this.row().entries.some(entry => {
        const taxCountryRow = entry.children.find(
            child => child.key === 'tax-country'
        );

        return taxCountryRow?.resolution.value?.trim() === taxCountry;
    });
}
