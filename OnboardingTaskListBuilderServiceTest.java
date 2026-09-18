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
        // SENDING ADDRESS
        // ============================================================

        case 'street':
          this.applyPolicyStreet(result, value);
          break;

        case 'house-name':
          this.applyPolicyHouseName(result, value);
          break;

        case 'city':
          this.applyPolicyCity(result, value);
          break;

        case 'county':
          this.applyPolicyCounty(result, value);
          break;

        case 'language':
          this.applyPolicyLanguage(result, value);
          break;

        case 'number':
          this.applyPolicyNumber(result, value);
          break;

        case 'apartment-number':
          this.applyPolicyApartmentNumber(result, value);
          break;

        case 'postcode':
          this.applyPolicyPostcode(result, value);
          break;

        case 'area':
          this.applyPolicyArea(result, value);
          break;

        // ============================================================
        // COMMUNICATION PREFERENCES
        // ============================================================

        case 'receive-electronic-communication':
          this.applyReceiveElectronicCommunication(result, value);
          break;

        case 'consent-to-use-digital-platform':
          this.applyConsentToUseDigitalPlatform(result, value);
          break;

        case 'consent-to-use-electronic-signature':
          this.applyConsentToUseElectronicSignature(result, value);
          break;

        // ============================================================
        // OPT IN / OUT
        // ============================================================

        case 'policy-type':
          this.applyPolicyType(result, value);
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
