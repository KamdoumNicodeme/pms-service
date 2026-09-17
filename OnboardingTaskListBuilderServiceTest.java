private applyManualEmailChange(
    client: IThirdParty,
    resolution: Resolution
): void {

    const value: string | null =
        this.nullableStringValue(resolution.value);

    if (!value) {
        return;
    }

    if (!client.emails) {
        client.emails = [];
    }

    const alreadyExists: boolean =
        client.emails.some(
            email =>
                email.email?.trim().toLowerCase() ===
                value.trim().toLowerCase()
        );

    if (alreadyExists) {
        return;
    }

    client.emails.push({
        email: value
    } as IEmail);
}


private applyManualPhoneChange(
    client: IThirdParty,
    resolution: Resolution
): void {

    const value: string | null =
        this.nullableStringValue(resolution.value);

    if (!value) {
        return;
    }

    if (!client.phoneNumbers) {
        client.phoneNumbers = [];
    }

    const alreadyExists: boolean =
        client.phoneNumbers.some(
            phone =>
                phone.phoneNumber?.trim() === value.trim()
        );

    if (alreadyExists) {
        return;
    }

    client.phoneNumbers.push({
        phoneNumber: value
    } as IPhoneNumber);
}
