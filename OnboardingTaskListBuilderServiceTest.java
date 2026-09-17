private applyPhysicalPersonChanges(
    client: IPhysicalPerson,
    changes: ReadonlyMap<string, Resolution>
): void {

    // Structured manual tax entries must be processed first because
    // several resolutions belong to the same Tax Information entry.
    this.applyManualTaxInformations(client, changes);

    changes.forEach((resolution: Resolution, id: string): void => {

        const value: string | null = resolution.value;

        // ============================================================
        // NATIONALITIES
        // ============================================================

        if (id.startsWith('nationalities:manual-')) {
            this.applyManualNationalityChange(
                client,
                id,
                resolution
            );
            return;
        }

        if (id.startsWith('nationalities:')) {
            this.applyNationalityChange(
                client,
                id,
                resolution
            );
            return;
        }

        // ============================================================
        // EMAILS
        // ============================================================

        if (id.startsWith('emails:manual-')) {
            this.applyManualEmailChange(
                client,
                id,
                resolution
            );
            return;
        }

        if (id.startsWith('emails:')) {
            this.applyEmailChange(
                client,
                id,
                resolution
            );
            return;
        }

        // ============================================================
        // PHONE NUMBERS
        // ============================================================

        if (id.startsWith('phone-numbers:manual-')) {
            this.applyManualPhoneChange(
                client,
                id,
                resolution
            );
            return;
        }

        if (id.startsWith('phone-numbers:')) {
            this.applyPhoneChange(
                client,
                id,
                resolution
            );
            return;
        }

        // ============================================================
        // TAX INFORMATION
        // ============================================================

        // Manual Tax Information entries have already been processed
        // by applyManualTaxInformations().
        if (id.startsWith('tax-information:manual-')) {
            return;
        }

        if (id.startsWith('tax-information:')) {
            this.applyTaxInformationChange(
                client,
                id,
                resolution
            );
            return;
        }

        // ============================================================
        // GENERAL INFORMATION
        // ============================================================

        switch (id) {

            case 'thirdPartyId':
                // Third-party ID is not editable.
                break;

            case 'lastname':
                client.lastname = this.stringValue(value);
                break;

            case 'firstname':
                client.firstname = this.stringValue(value);
                break;

            case 'birth-date':
                client.birthDate = this.stringValue(value);
                break;

            case 'birth-country':
                client.birthCountry = this.stringValue(value);
                break;

            case 'status':
                if (client.civilStatus) {
                    client.civilStatus.status = this.nullableStringValue(value);
                }
                break;

            // ========================================================
            // PROFESSIONAL DETAILS
            // ========================================================

            case 'profession':
                if (client.professionalDetails) {
                    client.professionalDetails.profession =
                        this.stringValue(value);
                }
                break;

            case 'profession-status':
                if (client.professionalDetails) {
                    client.professionalDetails.status =
                        this.nullableStringValue(value);
                }
                break;

            case 'employer-name':
                if (client.professionalDetails) {
                    client.professionalDetails.companyName =
                        this.nullableStringValue(value);
                }
                break;

            case 'industry-sector':
                if (client.professionalDetails) {
                    client.professionalDetails.sector =
                        this.nullableStringValue(value);
                }
                break;

            // ========================================================
            // IDENTITY DOCUMENT
            // ========================================================

            case 'type':
                if (client.idDocument) {
                    client.idDocument.type =
                        this.stringValue(value);
                }
                break;

            case 'number':
                if (client.idDocument) {
                    client.idDocument.number =
                        this.stringValue(value);
                }
                break;

            case 'expirationDate':
                if (client.idDocument) {
                    client.idDocument.expirationDate =
                        this.nullableStringValue(value);
                }
                break;

            // ========================================================
            // US PERSON
            // ========================================================

            case 'us-entity':
                client.usPerson = this.booleanValue(value);
                break;

            // ========================================================
            // LEGAL ADDRESS
            // ========================================================

            case 'legal-address:no':
                if (client.legalAddress) {
                    client.legalAddress.no =
                        this.nullableStringValue(value);
                }
                break;

            case 'legal-address:street':
                if (client.legalAddress) {
                    client.legalAddress.address =
                        this.nullableStringValue(value);
                }
                break;

            case 'legal-address:postCode':
                if (client.legalAddress) {
                    client.legalAddress.postCode =
                        this.nullableStringValue(value);
                }
                break;

            case 'legal-address:town':
                if (client.legalAddress) {
                    client.legalAddress.town =
                        this.nullableStringValue(value);
                }
                break;

            case 'legal-address:country':
                if (client.legalAddress) {
                    client.legalAddress.country =
                        this.nullableStringValue(value);
                }
                break;

            default:
                break;
        }
    });
}
