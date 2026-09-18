import { IMoralPerson } from '@lia/commons';

export const MOCK_DIGITAL_MORAL_PERSON: IMoralPerson = {
  type: 'MORAL_PERSON',

  thirdPartyId: '0003310689',

  name: 'Cox Tilly Digital S.A.',

  creationDate: '2025-01-27T00:00:00Z',

  countryOfResidence: 'FR',

  economicSector: 'SCI',

  vatNumber: 'FR123456789',

  legalAddress: {
    address: '10 Avenue des Champs',
    no: '10',
    postCode: '75008',
    town: 'Paris',
    country: 'FR',
  },

  emails: [
    {
      email: 'contact@coxtilly-digital.fr',
    },
  ],

  phoneNumbers: [
    {
      phoneNumber: '+33123456789',
    },
  ],

  taxInformations: [
    {
      taxCountry: 'FR',
      taxNumber: '294435507',
      tinUnavailableReason: null,
    },
  ],

  fatcaStatus: 'PNFFE',

  aeoiStatus: 'PNFE',

  companyLegalForm: 'SCI',

  leiCode: null,

  giin: null,

  taxCountry: 'FR',

  roleTypes: ['Holder'],
} as IMoralPerson;
