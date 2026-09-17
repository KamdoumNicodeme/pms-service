private applyPhysicalPersonChange(
  client: IPhysicalPerson,
  id: string,
  resolution: Resolution
): void {

  const value = resolution.value;

  switch (id) {

    // ==========================================
    // GENERAL INFORMATION
    // ==========================================

    case 'lastname':
      client.lastname = value ?? '';
      break;

    case 'firstname':
      client.firstName = value ?? '';
      break;

    case 'birth-date':
      client.birthDate = value ?? '';
      break;

    case 'birth-country':
      client.birthCountry = value ?? '';
      break;

    case 'status':
      client.civilStatus = {
        ...client.civilStatus,
        status: value ?? ''
      };
      break;

    case 'trustee-if-moral-person':
      client.trusteeIfMoralPerson = value ?? '';
      break;


    // ==========================================
    // PROFESSIONAL DETAILS
    // ==========================================

    case 'profession':
      client.professionalDetails = {
        ...client.professionalDetails,
        profession: value ?? ''
      };
      break;

    case 'profession-status':
      client.professionalDetails = {
        ...client.professionalDetails,
        status: value ?? ''
      };
      break;

    case 'employer-name':
      client.professionalDetails = {
        ...client.professionalDetails,
        companyName: value ?? ''
      };
      break;

    case 'industry-sector':
      client.professionalDetails = {
        ...client.professionalDetails,
        sector: value ?? ''
      };
      break;


    // ==========================================
    // IDENTITY DOCUMENT
    // ==========================================

    case 'type':
      client.idDocument = {
        ...client.idDocument,
        type: value ?? ''
      };
      break;

    case 'number':
      client.idDocument = {
        ...client.idDocument,
        number: value ?? ''
      };
      break;

    case 'expirationDate':
      client.idDocument = {
        ...client.idDocument,
        expirationDate: value ?? ''
      };
      break;


    // ==========================================
    // TAX / US PERSON
    // ==========================================

    case 'us-entity':
      client.usPerson = this.toBoolean(value);
      break;
  }
}
