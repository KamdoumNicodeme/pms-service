private applyPhysicalPersonChanges(
    client: IPhysicalPerson,
    changes: ReadonlyMap<string, Resolution>
): void {

    // ============================================================
    // MANUAL ENTRIES
    // ============================================================

    // Manual entries are additions and must be processed separately
    // from existing entries.
    this.applyManualNationalities(client, changes);
    this.applyManualEmails(client, changes);
    this.applyManualPhoneNumbers(client, changes);
    this.applyManualTaxInformations(client, changes);

    // ============================================================
    // EXISTING ENTRIES AND SCALAR FIELDS
    // ============================================================

    changes.forEach((resolution: Resolution, id: string): void => {

        const value: string | null = resolution.value;

        // Manual entries have already been processed above.
        if (
            id.startsWith('nationalities:manual-') ||
            id.startsWith('emails:manual-') ||
            id.startsWith('phone-numbers:manual-') ||
            id.startsWith('tax-information:manual-')
        ) {
            return;
        }

        // Existing nationality
        if (id.startsWith('nationalities:')) {
            this.applyNationalityChange(client, id, resolution);
            return;
        }

        // Existing email
        if (id.startsWith('emails:')) {
            this.applyEmailChange(client, id, resolution);
            return;
        }

        // Existing phone number
        if (id.startsWith('phone-numbers:')) {
            this.applyPhoneChange(client, id, resolution);
            return;
        }

        // Existing Tax Information
        if (id.startsWith('tax-information:')) {
            this.applyTaxInformationChange(client, id, resolution);
            return;
        }

        // ========================================================
        // GENERAL INFORMATION
        // ========================================================

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
                    client.civilStatus.status =
                        this.nullableStringValue(value);
                }
                break;

            // ====================================================
            // PROFESSIONAL DETAILS
            // ====================================================

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

            // ====================================================
            // IDENTITY DOCUMENT
            // ====================================================

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

            // ====================================================
            // US PERSON
            // ====================================================

            case 'us-entity':
                client.usPerson = this.booleanValue(value);
                break;

            // ====================================================
            // LEGAL ADDRESS
            // ====================================================

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
