private applyManualTaxInformations(
    client: IThirdParty,
    changes: ReadonlyMap<string, Resolution>
): void {

    const manualTaxIds = new Set<string>();

    changes.forEach((_resolution, id) => {

        if (!id.startsWith('tax-information:manual-')) {
            return;
        }

        const parts = id.split(':');

        if (parts.length >= 3) {
            manualTaxIds.add(parts[1]);
        }
    });

    manualTaxIds.forEach(manualId => {

        const prefix = `tax-information:${manualId}:`;

        const country =
            this.nullableStringValue(
                changes.get(`${prefix}tax-country`)?.value ?? null
            );

        const tin =
            this.nullableStringValue(
                changes.get(`${prefix}tin`)?.value ?? null
            );

        const reason =
            this.nullableStringValue(
                changes.get(`${prefix}tin-unavailable-reason`)?.value ?? null
            );

        if (!country) {
            return;
        }

        client.taxInformations ??= [];

        /*
         * Évite également les doublons de Tax Country.
         */
        const alreadyExists = client.taxInformations.some(
            tax => tax.taxCountry === country
        );

        if (alreadyExists) {
            return;
        }

        client.taxInformations.push({
            taxCountry: country,
            taxNumber: tin,

            // IMPORTANT :
            // si TIN existe => Reason obligatoirement null
            tinUnavailableReason: tin ? null : reason

        } as ITaxInformation);
    });
}

private applyPhysicalPersonChanges(

    client: IPhysicalPerson,

    changes: ReadonlyMap<string, Resolution>

): void {

    // Reconstruit toutes les Tax Information manuelles

    this.applyManualTaxInformations(client, changes);

    changes.forEach((resolution: Resolution, id: string): void => {

        // Les taxes manuelles ont déjà été traitées au-dessus.

        if (id.startsWith('tax-information:manual-')) {

            return;

        }

        // Existing Tax Information

        if (id.startsWith('tax-information:')) {

            this.applyTaxInformationChange(

                client,

                id,

                resolution

            );

           
