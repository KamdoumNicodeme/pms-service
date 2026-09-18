public applyPolicyChanges(
  changeClientInformation: IChangeClientInformation,
  changes: ReadonlyMap<string, Resolution>
): IChangeClientInformation {

  const result: IChangeClientInformation =
    structuredClone(changeClientInformation);

  changes.forEach(
    (resolution: Resolution, id: string): void => {

      const value: string | null = resolution.value;

      switch (id) {

        // ============================================================
        // SENDING ADDRESS AND COMMUNICATION PREFERENCES
        // ============================================================

        case 'street':
          result.policy.sendingAddress.address =
            this.stringValue(value);
          break;

        case 'number':
          result.policy.sendingAddress.no =
            this.stringValue(value);
          break;

        case 'house-name':
          result.policy.sendingAddress.houseName =
            this.stringValue(value);
          break;

        case 'apartment-number':
          result.policy.sendingAddress.apartmentNumber =
            this.stringValue(value);
          break;

        case 'city':
          result.policy.sendingAddress.town =
            this.stringValue(value);
          break;

        case 'postcode':
          result.policy.sendingAddress.postCode =
            this.stringValue(value);
          break;

        case 'county':
          result.policy.sendingAddress.county =
            this.stringValue(value);
          break;

        case 'area':
          result.policy.sendingAddress.area =
            this.stringValue(value);
          break;

        case 'country':
          result.policy.sendingAddress.country =
            this.stringValue(value);
          break;

        case 'language':
          result.policy.language =
            this.stringValue(value);
          break;

        // ============================================================
        // COMMUNICATION CONSENTS
        // ============================================================

        case 'receive-electronic-communication':
          result.policy.digitalConsent
            .receiveElectronicCommunicationsConsentStatus =
              this.stringValue(value);
          break;

        case 'consent-to-use-digital-platform':
          result.policy.digitalConsent
            .digitalPlatformConsentStatus =
              this.stringValue(value);
          break;

        case 'consent-to-use-electronic-signature':
          result.policy.digitalConsent
            .electronicSignatureConsentStatus =
              this.stringValue(value);
          break;

        // ============================================================
        // OPT IN / OUT
        // ============================================================

        case 'policy-type':
          result.policy.type =
            this.stringValue(value);
          break;

        default:
          console.warn(
            '[ClientProfilingChangeService] Unknown policy field:',
            id
          );
          break;
      }
    }
  );

  return result;
}
