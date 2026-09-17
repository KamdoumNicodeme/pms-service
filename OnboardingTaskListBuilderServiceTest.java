private applyManualTaxInformations(
    client: IPhysicalPerson,
    changes: ReadonlyMap<string, Resolution>
): void {

    const manualIds = new Set<string>();

    // Find all manual Tax Information entries.
    changes.forEach((_resolution: Resolution, id: string): void => {

        if (!id.startsWith('tax-information:manual-')) {
            return;
        }

        const parts = id.split(':');

        // Expected format:
        // tax-information:manual-1:tax-country
        // tax-information:manual-1:tin
        // tax-information:manual-1:tin-unavailable-reason
        if (parts.length >= 3) {
            manualIds.add(parts[1]);
        }
    });

    if (manualIds.size === 0) {
        return;
    }

    client.taxInformations ??= [];

    manualIds.forEach((manualId: string): void => {

        const prefix = `tax-information:${manualId}:`;

        const taxCountry = this.nullableStringValue(
            changes.get(`${prefix}tax-country`)?.value ?? null
        );

        const tin = this.nullableStringValue(
            changes.get(`${prefix}tin`)?.value ?? null
        );

        const reason = this.nullableStringValue(
            changes.get(`${prefix}tin-unavailable-reason`)?.value ?? null
        );

        // Tax Country is required to create the Tax Information entry.
        if (!taxCountry) {
            return;
        }

        // Do not add the same Tax Country twice.
        const alreadyExists = client.taxInformations.some(
            tax => tax.taxCountry === taxCountry
        );

        if (alreadyExists) {
            return;
        }

        client.taxInformations.push({
            taxCountry,
            taxNumber: tin,

            // Reason is only applicable when TIN is empty.
            tinUnavailableReason: tin ? null : reason
        } as ITaxInformation);
    });
}
