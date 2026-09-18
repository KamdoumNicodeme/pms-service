changes.forEach((resolution: Resolution, id: string): void => {

    console.log('[MORAL 1] ENTER id =', id);

    const value: string | null = resolution.value;

    if (id.startsWith('tax-information:manual-')) {

        console.log('[MORAL RETURN] manual tax');

        return;

    }

    if (id.startsWith('emails:manual-')) {

        console.log('[MORAL RETURN] manual email');

        this.applyManualEmailChange(client, resolution);

        return;

    }

    if (id.startsWith('emails:')) {

        console.log('[MORAL RETURN] email');

        this.applyEmailChange(client, id, resolution);

        return;

    }

    if (id.startsWith('phone-numbers:manual-')) {

        console.log('[MORAL RETURN] manual phone');

        this.applyManualPhoneChange(client, resolution);

        return;

    }

    if (id.startsWith('phone-numbers:')) {

        console.log('[MORAL RETURN] phone');

        this.applyPhoneChange(client, id, resolution);

        return;

    }

    if (id.startsWith('tax-information:')) {

        console.log('[MORAL RETURN] tax');

        this.applyTaxInformationChange(client, id, resolution);

        return;

    }

    console.log('[MORAL 2] BEFORE SWITCH id =', id);

    switch (id) {

        case 'thirdPartyId':

            break;

        case 'name':

            console.log('[MORAL 3] NAME CASE value =', value);

            client.name = value ?? '';

            console.log('[MORAL 4] NEW NAME =', client.name);

            break;

        case 'industry-sector':

            client.economicSector = this.stringValue(value);

            break;

        case 'creation-date':

            client.creationDate = this.stringValue(value);

            break;

        case 'vat-number':

            client.vatNumber = this.stringValue(value);

            break;
