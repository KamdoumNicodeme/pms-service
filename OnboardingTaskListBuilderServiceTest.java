private hasDuplicateTaxCountry(taxCountry: string): boolean {
    const expected: string = taxCountry.trim().toUpperCase();

    return this.row().entries.some(entry => {
        const taxCountryChild = entry.children.find(
            child => child.key === 'tax-country'
        );

        if (!taxCountryChild) {
            return false;
        }

        // Check the currently resolved value first.
        const resolvedValue: string | null =
            taxCountryChild.resolution.value;

        if (
            resolvedValue?.trim().toUpperCase() === expected
        ) {
            return true;
        }

        // Check the original values from every source.
        const sourceValues: Array<string | null> = [
            taxCountryChild.values.core,
            taxCountryChild.values.digital,
            taxCountryChild.values.kyc,
        ];

        return sourceValues.some(
            value => value?.trim().toUpperCase() === expected
        );
    });
}
