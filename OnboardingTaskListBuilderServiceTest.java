import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ClientProfilingChangeService {

  // ============================================================
  // ENTRY POINT
  // ============================================================

  applyHolderChanges(
    changeClientInformation: IChangeClientInformation,
    thirdPartyId: string,
    changes: ReadonlyMap<string, Resolution>
  ): IChangeClientInformation {

    const result: IChangeClientInformation =
      structuredClone(changeClientInformation);

    const client: IThirdParty | undefined =
      result.policy.clients.find(
        (item: IThirdParty): boolean =>
          item.thirdPartyId === thirdPartyId
      );

    if (!client) {
      return result;
    }

    if (client.type === 'PHYSICAL_PERSON') {
      this.applyPhysicalPersonChanges(
        client as IPhysicalPerson,
        changes
      );

      return result;
    }

    if (client.type === 'MORAL_PERSON') {
      this.applyMoralPersonChanges(
        client as IMoralPerson,
        changes
      );
    }

    return result;
  }


  // ============================================================
  // PHYSICAL PERSON
  // ============================================================

  private applyPhysicalPersonChanges(
    client: IPhysicalPerson,
    changes: ReadonlyMap<string, Resolution>
  ): void {

    changes.forEach(
      (resolution: Resolution, id: string): void => {

        const value = resolution.value;

        // --------------------------------------------------------
        // LISTS / GROUPS
        // --------------------------------------------------------

        if (id.startsWith('nationalities:')) {
          this.applyNationalityChange(client, id, resolution);
          return;
        }

        if (id.startsWith('emails:')) {
          this.applyEmailChange(client, id, resolution);
          return;
        }

        if (id.startsWith('phone-numbers:')) {
          this.applyPhoneChange(client, id, resolution);
          return;
        }

        if (id.startsWith('tax-information:')) {
          this.applyTaxInformationChange(client, id, resolution);
          return;
        }

        // --------------------------------------------------------
        // SCALAR FIELDS
        // --------------------------------------------------------

        switch (id) {

          // =========================
          // GENERAL INFORMATION
          // =========================

          case 'thirdPartyId':
            // Not editable
            break;

          case 'salutation':
            client.salutation = this.stringValue(value);
            break;

          case 'lastname':
            client.lastname = this.stringValue(value);
            break;

          case 'firstname':
            client.firstName = this.stringValue(value);
            break;

          case 'birth-date':
            client.birthDate = this.stringValue(value);
            break;

          case 'birth-country':
            client.birthCountry = this.stringValue(value);
            break;

          case 'spouse-name':
            client.spouseName = this.stringValue(value);
            break;

          case 'status':
            client.civilStatus = {
              ...client.civilStatus,
              status: this.stringValue(value)
            };
            break;


          // =========================
          // PROFESSIONAL DETAILS
          // =========================

          case 'profession':
            this.ensureProfessionalDetails(client);
            client.professionalDetails.profession =
              this.stringValue(value);
            break;

          case 'profession-status':
            this.ensureProfessionalDetails(client);
            client.professionalDetails.status =
              this.stringValue(value);
            break;

          case 'employer-name':
            this.ensureProfessionalDetails(client);
            client.professionalDetails.companyName =
              this.stringValue(value);
            break;

          case 'industry-sector':
            this.ensureProfessionalDetails(client);
            client.professionalDetails.sector =
              this.stringValue(value);
            break;


          // =========================
          // CONTROLLING PERSON
          // =========================

          case 'trustee-if-moral-person':
            client.trusteeIfMoralPerson =
              this.stringValue(value);
            break;


          // =========================
          // IDENTITY DOCUMENT
          // =========================

          case 'type':
            this.ensureIdentityDocument(client);
            client.idDocument.type =
              this.stringValue(value);
            break;

          case 'number':
            this.ensureIdentityDocument(client);
            client.idDocument.number =
              this.stringValue(value);
            break;

          case 'expirationDate':
            this.ensureIdentityDocument(client);
            client.idDocument.expirationDate =
              this.stringValue(value);
            break;


          // =========================
          // LEGAL ADDRESS
          // =========================

          case 'legal-address:no':
            this.ensureLegalAddress(client);
            client.legalAddress.no =
              this.stringValue(value);
            break;

          case 'legal-address:address':
            this.ensureLegalAddress(client);
            client.legalAddress.address =
              this.stringValue(value);
            break;

          case 'legal-address:postcode':
            this.ensureLegalAddress(client);
            client.legalAddress.postCode =
              this.stringValue(value);
            break;

          case 'legal-address:town':
            this.ensureLegalAddress(client);
            client.legalAddress.town =
              this.stringValue(value);
            break;

          case 'legal-address:country':
            this.ensureLegalAddress(client);
            client.legalAddress.country =
              this.stringValue(value);
            break;


          // =========================
          // US PERSON
          // =========================

          case 'us-entity':
            client.usPerson =
              this.booleanValue(value);
            break;

          default:
            console.warn(
              '[ClientProfilingChangeService] Unhandled physical field:',
              id,
              resolution
            );
            break;
        }
      }
    );
  }


  // ============================================================
  // MORAL PERSON
  // ============================================================

  private applyMoralPersonChanges(
    client: IMoralPerson,
    changes: ReadonlyMap<string, Resolution>
  ): void {

    changes.forEach(
      (resolution: Resolution, id: string): void => {

        const value = resolution.value;

        // --------------------------------------------------------
        // LISTS / GROUPS
        // --------------------------------------------------------

        if (id.startsWith('emails:')) {
          this.applyEmailChange(client, id, resolution);
          return;
        }

        if (id.startsWith('phone-numbers:')) {
          this.applyPhoneChange(client, id, resolution);
          return;
        }

        if (id.startsWith('tax-information:')) {
          this.applyTaxInformationChange(client, id, resolution);
          return;
        }

        switch (id) {

          // =========================
          // GENERAL INFORMATION
          // =========================

          case 'thirdPartyId':
            // Not editable
            break;

          case 'name':
            client.name = this.stringValue(value);
            break;

          case 'industry-sector':
            client.economicSector =
              this.stringValue(value);
            break;

          case 'creation-date':
            client.creationDate =
              this.stringValue(value);
            break;

          case 'vat-number':
            client.vatNumber =
              this.stringValue(value);
            break;


          // =========================
          // LEGAL ADDRESS
          // =========================

          case 'legal-address:no':
            this.ensureLegalAddress(client);
            client.legalAddress.no =
              this.stringValue(value);
            break;

          case 'legal-address:address':
            this.ensureLegalAddress(client);
            client.legalAddress.address =
              this.stringValue(value);
            break;

          case 'legal-address:postcode':
            this.ensureLegalAddress(client);
            client.legalAddress.postCode =
              this.stringValue(value);
            break;

          case 'legal-address:town':
            this.ensureLegalAddress(client);
            client.legalAddress.town =
              this.stringValue(value);
            break;

          case 'legal-address:country':
            this.ensureLegalAddress(client);
            client.legalAddress.country =
              this.stringValue(value);
            break;


          // =========================
          // TAX / FATCA / CRS
          // =========================

          case 'fatcaStatus':
            client.fatcaStatus =
              this.stringValue(value);
            break;

          case 'crsStatus':
            client.crsStatus =
              this.stringValue(value);
            break;

          case 'us-entity':
            client.usEntity =
              this.booleanValue(value);
            break;

          default:
            console.warn(
              '[ClientProfilingChangeService] Unhandled moral field:',
              id,
              resolution
            );
            break;
        }
      }
    );
  }


  // ============================================================
  // NATIONALITIES
  // ============================================================

  private applyNationalityChange(
    client: IPhysicalPerson,
    id: string,
    resolution: Resolution
  ): void {

    const previousCountry: string =
      id.substring('nationalities:'.length);

    const nationalities = [
      client.nationality?.first,
      client.nationality?.second,
      client.nationality?.third
    ];

    const nationality = nationalities.find(
      item => item?.country === previousCountry
    );

    if (!nationality) {
      console.warn(
        '[ClientProfilingChangeService] Nationality not found:',
        previousCountry
      );
      return;
    }

    nationality.country =
      this.nullableStringValue(resolution.value);
  }


  // ============================================================
  // EMAILS
  // ============================================================

  private applyEmailChange(
    client: IThirdParty,
    id: string,
    resolution: Resolution
  ): void {

    const previousEmail: string =
      id.substring('emails:'.length);

    const email = client.emails?.find(
      item => item.email === previousEmail
    );

    if (!email) {
      console.warn(
        '[ClientProfilingChangeService] Email not found:',
        previousEmail
      );
      return;
    }

    email.email = this.stringValue(resolution.value);
  }


  // ============================================================
  // PHONE NUMBERS
  // ============================================================

  private applyPhoneChange(
    client: IThirdParty,
    id: string,
    resolution: Resolution
  ): void {

    const previousPhoneNumber: string =
      id.substring('phone-numbers:'.length);

    const phone = client.phoneNumbers?.find(
      item => item.phoneNumber === previousPhoneNumber
    );

    if (!phone) {
      console.warn(
        '[ClientProfilingChangeService] Phone number not found:',
        previousPhoneNumber
      );
      return;
    }

    phone.phoneNumber =
      this.stringValue(resolution.value);
  }


  // ============================================================
  // TAX INFORMATION
  // ============================================================

  private applyTaxInformationChange(
    client: IThirdParty,
    id: string,
    resolution: Resolution
  ): void {

    /*
     * Expected ids:
     *
     * tax-information:<country>:tax-country
     * tax-information:<country>:tin
     * tax-information:<country>:tin-unavailable-reason
     *
     * We will adjust these ids if ComparisonStore currently
     * generates a slightly different format.
     */

    const parts: string[] = id.split(':');

    if (parts.length < 3) {
      console.warn(
        '[ClientProfilingChangeService] Invalid tax information id:',
        id
      );
      return;
    }

    const taxKey: string = parts[1];
    const field: string = parts.slice(2).join(':');

    const taxInformation = client.taxInformations?.find(
      tax => tax.taxCountry === taxKey
    );

    if (!taxInformation) {
      console.warn(
        '[ClientProfilingChangeService] Tax information not found:',
        taxKey
      );
      return;
    }

    switch (field) {

      case 'tax-country':
        taxInformation.taxCountry =
          this.stringValue(resolution.value);
        break;

      case 'tin':
        taxInformation.taxNumber =
          this.stringValue(resolution.value);
        break;

      case 'tin-unavailable-reason':
        taxInformation.taxUnavailable =
          this.nullableStringValue(resolution.value);
        break;

      default:
        console.warn(
          '[ClientProfilingChangeService] Unhandled tax field:',
          field
        );
        break;
    }
  }


  // ============================================================
  // INITIALIZERS
  // ============================================================

  private ensureProfessionalDetails(
    client: IPhysicalPerson
  ): void {

    if (client.professionalDetails) {
      return;
    }

    client.professionalDetails = {
      profession: '',
      status: '',
      companyName: '',
      sector: ''
    };
  }


  private ensureIdentityDocument(
    client: IPhysicalPerson
  ): void {

    if (client.idDocument) {
      return;
    }

    client.idDocument = {
      type: '',
      number: '',
      expirationDate: ''
    };
  }


  private ensureLegalAddress(
    client: IThirdParty
  ): void {

    if (client.legalAddress) {
      return;
    }

    client.legalAddress = {
      no: '',
      address: '',
      postCode: '',
      town: '',
      country: ''
    };
  }


  // ============================================================
  // VALUE CONVERSION
  // ============================================================

  private stringValue(
    value: unknown
  ): string {

    if (value === null || value === undefined) {
      return '';
    }

    return String(value);
  }


  private nullableStringValue(
    value: unknown
  ): string | null {

    if (
      value === null ||
      value === undefined ||
      value === ''
    ) {
      return null;
    }

    return String(value);
  }


  private booleanValue(
    value: unknown
  ): boolean {

    if (typeof value === 'boolean') {
      return value;
    }

    const normalized: string =
      String(value ?? '')
        .trim()
        .toLowerCase();

    return (
      normalized === 'true' ||
      normalized === 'yes' ||
      normalized === 'y' ||
      normalized === '1' ||
      normalized === 'us person' ||
      normalized === 'us entity' ||
      normalized === 'us indicia detected'
    );
  }
}
