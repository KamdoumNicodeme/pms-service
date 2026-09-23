const digitalPolicyMock: IPolicy = {
  policyNumber: '2412-157945',
  type: 'CAPITALISED_POLICY',
  currency: 'EUR',

  broker: 'B/2757',
  brokerName: 'Vevanta Family Office',
  brokerTargetMarket: 'FR',

  countryOfLaw: 'FR',
  countryOfBusinessOrigin: 'LU',
  taxCountry: 'FR',

  digitalConsent: {
    receiveElectronicCommunicationsConsentStatus: 'ACCEPTED',
    receiveElectronicCommunicationConsentFromDate: '2025-06-26T00:00:00Z'
  },

  clients: [
    // ============================================================
    // PHYSICAL PERSON
    // ============================================================
    {
      type: 'PHYSICAL_PERSON',
      thirdPartyId: '10025378793',

      roleTypes: [
        'Holder',
        'Life_Assured',
        'Economic_Beneficiary_Owner'
      ],

      // =========================
      // GENERAL INFORMATION
      // =========================

      salutation: 'MR',
      lastname: 'Jaeger',
      firstname: 'Nico',

      birthDate: '1987-10-11T00:00:00Z',
      birthCountry: 'DE',

      countryOfResidence: 'LU',

      civilStatus: {
        status: 'MARRIED'
      },

      spouseName: 'Anna Jaeger',

      // =========================
      // NATIONALITIES
      // =========================

      nationality: {
        first: {
          country: 'DE',
          fromDate: '1987-10-11'
        },
        second: {
          country: 'FR',
          fromDate: '2015-04-20'
        },
        third: {
          country: 'LU',
          fromDate: '2022-09-01'
        }
      },

      // =========================
      // PROFESSIONAL DETAILS
      // =========================

      professionalDetails: {
        profession: 'mngt_dire',
        status: 'working',
        sector: 'real_est',
        companyName: 'Digital Holding S.A.'
      },

      // =========================
      // CONTACT DETAILS
      // =========================

      emails: [
        {
          email: 'nico.jaeger@gmail.com'
        },
        {
          email: 'nico.jaeger@digitalholding.lu'
        }
      ],

      phoneNumbers: [
        {
          phoneNumber: '+352621123456'
        },
        {
          phoneNumber: '+491701234567'
        }
      ],

      legalAddress: {
        address: 'Boulevard Royal',
        no: '25',
        postCode: 'L-2449',
        town: 'Luxembourg',
        country: 'LU'
      },

      // =========================
      // IDENTITY DOCUMENT
      // =========================

      idDocument: {
        type: 'PASSPORT',
        number: 'C01X12345',
        expirationDate: '2031-06-22'
      },

      idDocumentExpirationDate: '2031-06-22',

      // =========================
      // FATCA / AEOI
      // =========================

      aeoiStatus: 'DOC_SELF-C',
      fatcaStatus: 'NO_US_IN',

      // =========================
      // TAX
      // =========================

      taxCountry: 'LU',
      taxNumber: '19871011001',

      taxInformations: [
        {
          taxCountry: 'LU',
          taxNumber: '19871011001'
        },
        {
          taxCountry: 'DE',
          taxNumber: 'DE123456789'
        },
        {
          taxCountry: 'FR',
          taxNumber: 'FR987654321'
        }
      ]
    } as IPhysicalPerson,

    // ============================================================
    // MORAL PERSON
    // ============================================================
    {
      type: 'MORAL_PERSON',
      thirdPartyId: '0003310689',

      roleTypes: [
        'Holder',
        'Economic_Beneficiary_Owner'
      ],

      // =========================
      // GENERAL INFORMATION
      // =========================

      name: 'Cox Tilly S.A.',

      companyLegalForm: 'S.A.',

      creationDate: '2025-01-27T00:00:00Z',

      vatNumber: 'LU12345678',

      country: 'LU',

      countryOfResidence: 'LU',

      // =========================
      // COMPANY INFORMATION
      // =========================

      economicSector: 'agri',

      giin: 'ABCDEF.12345.ME.442',

      leiCode: '5493001KJTIIGC8Y1R12',

      // =========================
      // CONTACT DETAILS
      // =========================

      emails: [
        {
          email: 'contact@coxtilly.lu'
        },
        {
          email: 'compliance@coxtilly.lu'
        },
        {
          email: 'finance@coxtilly.lu'
        }
      ],

      phoneNumbers: [
        {
          phoneNumber: '+35226123456'
        },
        {
          phoneNumber: '+352621987654'
        }
      ],

      legalAddress: {
        address: 'Avenue John F. Kennedy',
        no: '45',
        postCode: 'L-1855',
        town: 'Luxembourg',
        country: 'LU'
      },

      // =========================
      // FATCA / AEOI
      // =========================

      aeoiStatus: 'PNFEE',

      fatcaStatus: 'PNFEE',

      // =========================
      // TAX
      // =========================

      taxCountry: 'LU',

      taxNumber: 'LU12345678',

      taxInformations: [
        {
          taxCountry: 'LU',
          taxNumber: 'LU12345678'
        },
        {
          taxCountry: 'FR',
          taxNumber: 'FR294435507'
        },
        {
          taxCountry: 'DE',
          taxNumber: 'DE987654321'
        }
      ]
    } as IMoralPerson,

    // ============================================================
    // CONTROLLING PERSON
    // ============================================================
    {
      type: 'PHYSICAL_PERSON',
      thirdPartyId: '10098765432',

      roleTypes: [
        'Economic_Beneficiary_Owner',
        'Trustee'
      ],

      salutation: 'MRS',

      lastname: 'Muller',
      firstname: 'Sophie',

      birthDate: '1985-05-15T00:00:00Z',
      birthCountry: 'LU',

      countryOfResidence: 'LU',

      civilStatus: {
        status: 'SINGLE'
      },

      spouseName: null,

      nationality: {
        first: {
          country: 'LU',
          fromDate: '1985-05-15'
        },
        second: {
          country: 'BE',
          fromDate: '2018-03-12'
        },
        third: {
          country: null,
          fromDate: null
        }
      },

      professionalDetails: {
        profession: 'mngt_dire',
        status: 'working',
        sector: 'fin_serv',
        companyName: 'Lux Finance S.A.'
      },

      emails: [
        {
          email: 'sophie.muller@gmail.com'
        },
        {
          email: 'sophie.muller@luxfinance.lu'
        }
      ],

      phoneNumbers: [
        {
          phoneNumber: '+352621555555'
        }
      ],

      legalAddress: {
        address: 'Rue des Jardins',
        no: '18',
        postCode: 'L-1839',
        town: 'Luxembourg',
        country: 'LU'
      },

      idDocument: {
        type: 'ID_CARD',
        number: 'LU-ID-987654',
        expirationDate: '2032-12-31'
      },

      idDocumentExpirationDate: '2032-12-31',

      aeoiStatus: 'DOC_SELF-C',

      fatcaStatus: 'NO_US_IN',

      taxCountry: 'LU',

      taxNumber: '19850515001',

      taxInformations: [
        {
          taxCountry: 'LU',
          taxNumber: '19850515001'
        },
        {
          taxCountry: 'BE',
          taxNumber: 'BE850515001'
        }
      ]
    } as IPhysicalPerson
  ]
};
