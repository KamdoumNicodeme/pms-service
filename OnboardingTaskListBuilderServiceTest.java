import { ClientProfilingChangeService, ChangeIdParser } from './client-profiling-change.service';

/**
 * Unit tests reconstructed from the screenshots of ClientProfilingChangeService.
 * The parameter-derived types deliberately avoid guessing paths to the project's DTOs.
 * Place this file next to client-profiling-change.service.ts.
 */
type Cci = Parameters<ClientProfilingChangeService['applyHolderChanges']>[0];
type Holder = Parameters<ClientProfilingChangeService['applyHolderChanges']>[1];
type Changes = Parameters<ClientProfilingChangeService['applyHolderChanges']>[2];
type RemovedEntry = NonNullable<Parameters<ClientProfilingChangeService['applyHolderChanges']>[3]>[number];
type ProfilingData = Parameters<ClientProfilingChangeService['createEmpty']>[0];

type ChangeSpec = [id: string, value: string | null, reason?: string, comment?: string];

const makeChanges = (...specs: ChangeSpec[]): Changes =>
  new Map(
    specs.map(([id, value, reason, comment]) => [id, { value, reason, comment }])
  ) as unknown as Changes;

const makeHolder = (
  type: 'PHYSICAL_PERSON' | 'MORAL_PERSON' = 'PHYSICAL_PERSON',
  extras: Record<string, unknown> = {}
): Holder => ({ thirdPartyId: 'TP-1', type, ...extras }) as unknown as Holder;

const makeCci = (
  clients: Record<string, unknown>[] = [],
  policyExtras: Record<string, unknown> = {},
  rootExtras: Record<string, unknown> = {}
): Cci => ({
  policy: { policyNumber: 'POL-123', clients, ...policyExtras },
  ...rootExtras
}) as unknown as Cci;

const makeRemovals = (...entries: { id: string; value: string }[]): readonly RemovedEntry[] =>
  entries as unknown as readonly RemovedEntry[];

describe('ChangeIdParser', () => {
  it('parses a simple identifier without subtype', () => {
    const result = ChangeIdParser.parse('emails:old@example.com');

    expect(result.rawId).toBe('emails:old@example.com');
    expect(result.prefix).toBe('emails');
    expect(result.parts).toEqual(['emails', 'old@example.com']);
    expect(result.subType).toBeNull();
    expect(result.field).toBe('old@example.com');
    expect(result.isManual()).toBe(false);
  });

  it('parses a typed identifier', () => {
    const result = ChangeIdParser.parse('nationalities:first:country');

    expect(result.prefix).toBe('nationalities');
    expect(result.subType).toBe('first');
    expect(result.field).toBe('country');
    expect(result.isManual()).toBe(false);
  });

  it('detects manually added identifiers', () => {
    const result = ChangeIdParser.parse('tax-information:manual-123:tin');

    expect(result.subType).toBe('manual-123');
    expect(result.field).toBe('tin');
    expect(result.isManual()).toBe(true);
  });

  it('does not consider a two-part manual-looking ID to be a manual subtype', () => {
    const result = ChangeIdParser.parse('emails:manual-123');

    expect(result.subType).toBeNull();
    expect(result.isManual()).toBe(false);
  });
});

describe('ClientProfilingChangeService', () => {
  let service: ClientProfilingChangeService;

  beforeEach(() => {
    service = new ClientProfilingChangeService();
  });

  describe('createEmpty', () => {
    it('creates a CCI payload with the initial policy number and no clients', () => {
      const data = {
        initialBusinessData: { policy: { policyNumber: 'POL-123' } }
      } as unknown as ProfilingData;

      expect(service.createEmpty(data)).toEqual({
        policy: { policyNumber: 'POL-123', clients: [] }
      });
    });
  });

  describe('cleanForSave', () => {
    it('recursively removes null, undefined and empty objects but keeps false, zero, strings and arrays', () => {
      const original = {
        name: 'Client',
        empty: null,
        missing: undefined,
        nested: { removed: null, active: false, count: 0, blank: '' },
        emptyObject: { child: undefined },
        items: [null, undefined, {}, { value: 'A', unused: null }, false, 0]
      };

      expect(service.cleanForSave(original)).toEqual({
        name: 'Client',
        nested: { active: false, count: 0, blank: '' },
        items: [{ value: 'A' }, false, 0]
      });
    });

    it('does not mutate the source object', () => {
      const original = { nested: { value: 'ok', missing: null } };

      const result = service.cleanForSave(original);

      expect(original.nested).toHaveProperty('missing', null);
      expect(result).toEqual({ nested: { value: 'ok' } });
      expect(result).not.toBe(original);
    });
  });

  describe('applyPolicyChanges', () => {
    it('maps sending address fields and creates missing holder/address structures', () => {
      const input = makeCci();
      const result = service.applyPolicyChanges(
        input,
        'POL-123',
        makeChanges(
          ['street', 'Main Street'],
          ['number', '15'],
          ['house-name', 'Oak House'],
          ['apartment-number', '4B'],
          ['postal-box', 'PO 19'],
          ['city', 'Luxembourg'],
          ['postcode', 'L-1234'],
          ['county', 'Luxembourg'],
          ['area', 'Centre']
        )
      );

      expect(result).toMatchObject({
        policy: {
          holder: {
            sendingAddress: {
              address: {
                streetName: 'Main Street',
                streetNumber: '15',
                residenceName: 'Oak House',
                apartmentNumber: '4B',
                postalBox: 'PO 19',
                townName: 'Luxembourg',
                postCode: 'L-1234',
                county: 'Luxembourg',
                area: 'Centre'
              }
            }
          }
        }
      });
      expect(input.policy.holder).toBeUndefined();
    });

    it('maps country and language to both nested properties and holder language', () => {
      const result = service.applyPolicyChanges(
        makeCci(),
        'POL-123',
        makeChanges(['country', 'LU'], ['language', 'FR'])
      );

      expect(result).toMatchObject({
        policy: {
          holder: {
            language: 'FR',
            sendingAddress: {
              address: { country: { code: 'LU', description: '' } },
              language: { code: 'FR', description: '' }
            }
          }
        }
      });
    });

    it('maps digital consents and tax deduction mode', () => {
      const result = service.applyPolicyChanges(
        makeCci(),
        'POL-123',
        makeChanges(
          ['receive-electronic-communication', 'ACCEPTED'],
          ['consent-to-use-digital-platform', 'PENDING'],
          ['consent-to-use-electronic-signature', 'REJECTED'],
          ['policy-type', 'SOME_TYPE']
        )
      );

      expect(result).toMatchObject({
        policy: {
          digitalConsent: {
            receiveElectronicCommunicationsConsentStatus: 'ACCEPTED',
            useDigitalPlatformConsentStatus: 'PENDING',
            useElectronicSignatureConsentStatus: 'REJECTED'
          },
          taxDeductionMode: 'SOME_TYPE'
        }
      });
    });

    it('stores change reasons using the policy number', () => {
      const result = service.applyPolicyChanges(
        makeCci(),
        'POL-123',
        makeChanges(['street', 'New Street', 'CORRECTION', 'Customer request'])
      );

      expect(result.changeReasons).toMatchObject({
        'POL-123:street': {
          reason: 'CORRECTION',
          comment: 'Customer request'
        }
      });
    });

    it('does not change the input object', () => {
      const original = makeCci();
      const result = service.applyPolicyChanges(original, 'POL-123', makeChanges(['street', 'New']));

      expect(result).not.toBe(original);
      expect(original.policy.holder).toBeUndefined();
    });
  });

  describe('applyHolderChanges / client creation', () => {
    it('creates a minimal physical-person client when it is absent', () => {
      const source = makeHolder('PHYSICAL_PERSON');
      const result = service.applyHolderChanges(
        makeCci(),
        source,
        makeChanges(['firstname', 'Alice'])
      );

      expect(result.policy.clients).toHaveLength(1);
      expect(result.policy.clients[0]).toMatchObject({
        thirdPartyId: 'TP-1', type: 'PHYSICAL_PERSON', firstName: 'Alice'
      });
    });

    it('updates an existing matching client rather than appending a duplicate', () => {
      const existing = { thirdPartyId: 'TP-1', type: 'PHYSICAL_PERSON', firstName: 'Old' };
      const original = makeCci([existing]);

      const result = service.applyHolderChanges(
        original,
        makeHolder(),
        makeChanges(['firstname', 'New'])
      );

      expect(result.policy.clients).toHaveLength(1);
      expect(result.policy.clients[0]).toMatchObject({ firstName: 'New' });
      expect(original.policy.clients[0]).toMatchObject({ firstName: 'Old' });
    });

    it('does not create a new client for an unsupported person type', () => {
      const unsupported = { thirdPartyId: 'TP-1', type: 'UNKNOWN_TYPE' } as unknown as Holder;
      const warning = jest.spyOn(console, 'warn').mockImplementation(() => {});

      try {
        const result = service.applyHolderChanges(makeCci(), unsupported, makeChanges(['name', 'Test']));
        expect(result.policy.clients).toEqual([]);
        expect(warning).toHaveBeenCalled();
      } finally {
        warning.mockRestore();
      }
    });

    it('stores the reason and comment for ordinary holder changes', () => {
      const result = service.applyHolderChanges(
        makeCci(),
        makeHolder(),
        makeChanges(['firstname', 'Alice', 'UPDATE', 'Verified by adviser'])
      );

      expect(result.changeReasons).toMatchObject({
        'TP-1:firstname': { reason: 'UPDATE', comment: 'Verified by adviser' }
      });
    });

    it('removes an existing reason when the new resolution has no reason', () => {
      const result = service.applyHolderChanges(
        makeCci([], {}, {
          changeReasons: { 'TP-1:firstname': { reason: 'OLD', comment: 'Old comment' } }
        }),
        makeHolder(),
        makeChanges(['firstname', 'Alice'])
      );

      expect(result.changeReasons?.['TP-1:firstname']).toBeUndefined();
    });

    it('does not record change reasons for manual entries', () => {
      const result = service.applyHolderChanges(
        makeCci(),
        makeHolder(),
        makeChanges(['emails:manual-1:email', 'new@example.com', 'NEW', 'Manual entry'])
      );

      expect(result.changeReasons).not.toHaveProperty('TP-1:emails:manual-1:email');
    });
  });

  describe('physical person scalar fields', () => {
    it('maps identity, birth, civil status, spouse and FATCA fields', () => {
      const result = service.applyHolderChanges(
        makeCci(),
        makeHolder(),
        makeChanges(
          ['salutation', 'MR'],
          ['firstname', 'John'],
          ['lastname', 'Doe'],
          ['birth-country', 'LU'],
          ['spouse-name', 'Jane Doe'],
          ['status', 'MARRIED'],
          ['fatcaStatus', 'COMPLIANT']
        )
      );

      expect(result.policy.clients[0]).toMatchObject({
        salutation: 'MR',
        firstName: 'John',
        lastname: 'Doe',
        birthCountry: 'LU',
        spouseName: 'Jane Doe',
        civilStatus: { status: 'MARRIED' },
        fatcaStatus: 'COMPLIANT'
      });
    });

    it('converts a DD/MM/YYYY birth date to an ISO midnight date', () => {
      const result = service.applyHolderChanges(
        makeCci(), makeHolder(), makeChanges(['birth-date', '12/11/1990'])
      );
      expect(result.policy.clients[0]).toMatchObject({ birthDate: '1990-11-12T00:00:00Z' });
    });

    it('initializes and updates identity document details', () => {
      const result = service.applyHolderChanges(
        makeCci(),
        makeHolder(),
        makeChanges(
          ['type', 'PASSPORT'],
          ['number', 'ABC123'],
          ['expirationDate', '01/02/2030']
        )
      );

      expect(result.policy.clients[0]).toMatchObject({
        idDocument: {
          type: 'PASSPORT',
          number: 'ABC123',
          expirationDate: '2030-02-01T00:00:00Z'
        }
      });
    });

    it('resets professional details when the profession changes to unemployed', () => {
      const initial = {
        thirdPartyId: 'TP-1', type: 'PHYSICAL_PERSON',
        professionalDetails: {
          profession: 'developer', status: 'EMPLOYEE', companyName: 'ACME', sector: 'IT'
        }
      };
      const result = service.applyHolderChanges(
        makeCci([initial]),
        makeHolder(),
        makeChanges(
          ['profession', 'unemployed'],
          ['profession-status', 'ACTIVE'],
          ['employer-name', 'Should be ignored'],
          ['industry-sector', 'Should be ignored']
        )
      );

      expect(result.policy.clients[0]).toMatchObject({
        professionalDetails: {
          profession: 'unemployed', status: null, companyName: null, sector: null
        }
      });
    });

    it('creates professional details when they do not exist', () => {
      const result = service.applyHolderChanges(
        makeCci(),
        makeHolder(),
        makeChanges(
          ['profession', 'engineer'],
          ['profession-status', 'EMPLOYEE'],
          ['employer-name', 'ACME'],
          ['industry-sector', 'IT']
        )
      );

      expect(result.policy.clients[0]).toMatchObject({
        professionalDetails: {
          profession: 'engineer', status: 'EMPLOYEE', companyName: 'ACME', sector: 'IT'
        }
      });
    });
  });

  describe('moral person scalar fields', () => {
    it('maps company identification and business fields', () => {
      const result = service.applyHolderChanges(
        makeCci(),
        makeHolder('MORAL_PERSON'),
        makeChanges(
          ['name', 'ACME SA'],
          ['industry-sector', 'FINANCE'],
          ['lei-code', 'LEI-123'],
          ['legal-form', 'SA'],
          ['incorporation-country', 'LU'],
          ['giin', 'GIIN-456'],
          ['fatcaStatus', 'ACTIVE'],
          ['aeoiStatus', 'REPORTABLE']
        )
      );

      expect(result.policy.clients[0]).toMatchObject({
        name: 'ACME SA', economicSector: 'FINANCE', leiCode: 'LEI-123',
        companyLegalForm: 'SA', countryOfIncorporation: 'LU',
        giin: 'GIIN-456', fatcaStatus: 'ACTIVE', aeoiStatus: 'REPORTABLE'
      });
    });

    it('normalizes truthy values for the US entity flag', () => {
      const positive = service.applyHolderChanges(
        makeCci(), makeHolder('MORAL_PERSON'), makeChanges(['us-entity', ' YES '])
      );
      const negative = service.applyHolderChanges(
        makeCci(), makeHolder('MORAL_PERSON'), makeChanges(['us-entity', 'no'])
      );

      expect(positive.policy.clients[0]).toMatchObject({ usEntity: true });
      expect(negative.policy.clients[0]).toMatchObject({ usEntity: false });
    });

    it('converts the creation date', () => {
      const result = service.applyHolderChanges(
        makeCci(), makeHolder('MORAL_PERSON'), makeChanges(['creation-date', '31/12/2001'])
      );
      expect(result.policy.clients[0]).toMatchObject({ creationDate: '2001-12-31T00:00:00Z' });
    });

    it('initializes the legal address and applies changes', () => {
      const result = service.applyHolderChanges(
        makeCci(),
        makeHolder('MORAL_PERSON'),
        makeChanges(
          ['legal-address.no', '42'],
          ['legal-address.address', 'Rue de la Gare'],
          ['legal-address.postcode', 'L-1000'],
          ['legal-address.city', 'Luxembourg'],
          ['legal-address.country', 'LU'],
          ['legal-address.appartNo', '2B'],
          ['legal-address.poBox', 'PO-100']
        )
      );

      expect(result.policy.clients[0]).toMatchObject({
        legalAddress: {
          no: '42', address: 'Rue de la Gare', postCode: 'L-1000',
          town: 'Luxembourg', country: 'LU', appartNo: '2B', poBox: 'PO-100'
        }
      });
    });
  });

  describe('email and phone updates', () => {
    it('updates an existing email by its old value', () => {
      const original = makeCci([{
        thirdPartyId: 'TP-1', type: 'PHYSICAL_PERSON',
        emails: [{ email: 'old@example.com' }]
      }]);

      const result = service.applyHolderChanges(
        original, makeHolder(), makeChanges(['emails:old@example.com', 'new@example.com'])
      );

      expect(result.policy.clients[0]).toMatchObject({ emails: [{ email: 'new@example.com' }] });
      expect(original.policy.clients[0]).toMatchObject({ emails: [{ email: 'old@example.com' }] });
    });

    it('adds a manual email but skips a case-insensitive duplicate', () => {
      const existing = makeCci([{
        thirdPartyId: 'TP-1', type: 'PHYSICAL_PERSON',
        emails: [{ email: 'foo@example.com' }]
      }]);
      const result = service.applyHolderChanges(
        existing,
        makeHolder(),
        makeChanges(
          ['emails:manual-1:email', ' FOO@example.com '],
          ['emails:manual-2:email', 'bar@example.com']
        )
      );

      expect(result.policy.clients[0]).toMatchObject({
        emails: [{ email: 'foo@example.com' }, { email: 'bar@example.com' }]
      });
    });

    it('updates an existing phone number by its old value', () => {
      const result = service.applyHolderChanges(
        makeCci([{
          thirdPartyId: 'TP-1', type: 'MORAL_PERSON',
          phoneNumbers: [{ phoneNumber: '+352123' }]
        }]),
        makeHolder('MORAL_PERSON'),
        makeChanges(['phone-numbers:+352123', '+352999'])
      );

      expect(result.policy.clients[0]).toMatchObject({
        phoneNumbers: [{ phoneNumber: '+352999' }]
      });
    });

    it('adds a manual phone number only once after trim-based duplicate comparison', () => {
      const result = service.applyHolderChanges(
        makeCci([{
          thirdPartyId: 'TP-1', type: 'PHYSICAL_PERSON',
          phoneNumbers: [{ phoneNumber: '+352123' }]
        }]),
        makeHolder(),
        makeChanges(
          ['phone-numbers:manual-1:number', ' +352123 '],
          ['phone-numbers:manual-2:number', '+352999']
        )
      );

      expect(result.policy.clients[0]).toMatchObject({
        phoneNumbers: [{ phoneNumber: '+352123' }, { phoneNumber: '+352999' }]
      });
    });

    it('removes email and phone entries by value', () => {
      const result = service.applyHolderChanges(
        makeCci([{
          thirdPartyId: 'TP-1', type: 'PHYSICAL_PERSON',
          emails: [{ email: 'keep@example.com' }, { email: 'remove@example.com' }],
          phoneNumbers: [{ phoneNumber: '+352111' }, { phoneNumber: '+352222' }]
        }]),
        makeHolder(),
        makeChanges(),
        makeRemovals(
          { id: 'emails:remove@example.com', value: 'remove@example.com' },
          { id: 'phone-numbers:+352222', value: '+352222' }
        )
      );

      expect(result.policy.clients[0]).toMatchObject({
        emails: [{ email: 'keep@example.com' }],
        phoneNumbers: [{ phoneNumber: '+352111' }]
      });
    });
  });

  describe('tax information', () => {
    it('updates existing tax information by tax country and maps each field', () => {
      const result = service.applyHolderChanges(
        makeCci([{
          thirdPartyId: 'TP-1', type: 'PHYSICAL_PERSON',
          taxInformations: [{ taxCountry: 'LU', taxNumber: 'OLD', missingTaxNumberReason: '', preferred: false }]
        }]),
        makeHolder(),
        makeChanges(
          ['tax-information:LU:tin', 'NEW-TIN'],
          ['tax-information:LU:tin-unavailable-reason', 'A'],
          ['tax-information:LU:is-default', 'true']
        )
      );

      expect(result.policy.clients[0]).toMatchObject({
        taxInformations: [{
          taxCountry: 'LU', taxNumber: 'NEW-TIN', missingTaxNumberReason: 'A', preferred: true
        }]
      });
    });

    it('adds a manual tax entry and ignores a missing-TIN reason if a TIN is provided', () => {
      const result = service.applyHolderChanges(
        makeCci(),
        makeHolder(),
        makeChanges(
          ['tax-information:manual-1:tax-country', 'LU'],
          ['tax-information:manual-1:tin', '12345'],
          ['tax-information:manual-1:tin-unavailable-reason', 'A'],
          ['tax-information:manual-1:is-default', 'true']
        )
      );

      expect(result.policy.clients[0]).toMatchObject({
        taxInformations: [{
          taxCountry: 'LU', taxNumber: '12345', missingTaxNumberReason: null, preferred: true
        }]
      });
    });

    it('keeps the missing-TIN reason when the manual tax number is absent', () => {
      const result = service.applyHolderChanges(
        makeCci(),
        makeHolder('MORAL_PERSON'),
        makeChanges(
          ['tax-information:manual-2:tax-country', 'BE'],
          ['tax-information:manual-2:tin-unavailable-reason', 'B']
        )
      );

      expect(result.policy.clients[0]).toMatchObject({
        taxInformations: [{
          taxCountry: 'BE', taxNumber: null, missingTaxNumberReason: 'B', preferred: false
        }]
      });
    });

    it('does not add a second manual tax entry with the same tax country', () => {
      const result = service.applyHolderChanges(
        makeCci([{
          thirdPartyId: 'TP-1', type: 'MORAL_PERSON',
          taxInformations: [{ taxCountry: 'LU', taxNumber: '123' }]
        }]),
        makeHolder('MORAL_PERSON'),
        makeChanges(
          ['tax-information:manual-3:tax-country', 'LU'],
          ['tax-information:manual-3:tin', '999']
        )
      );

      expect(result.policy.clients[0]).toMatchObject({
        taxInformations: [{ taxCountry: 'LU', taxNumber: '123' }]
      });
    });

    it('removes a tax entry by country', () => {
      const result = service.applyHolderChanges(
        makeCci([{
          thirdPartyId: 'TP-1', type: 'MORAL_PERSON',
          taxInformations: [{ taxCountry: 'LU' }, { taxCountry: 'BE' }]
        }]),
        makeHolder('MORAL_PERSON'),
        makeChanges(),
        makeRemovals({ id: 'tax-information:LU', value: 'LU' })
      );

      expect(result.policy.clients[0]).toMatchObject({
        taxInformations: [{ taxCountry: 'BE' }]
      });
    });
  });

  describe('nationalities', () => {
    it('updates the country and the date of an existing nationality', () => {
      const result = service.applyHolderChanges(
        makeCci([{
          thirdPartyId: 'TP-1', type: 'PHYSICAL_PERSON',
          nationality: { first: { country: 'FR', fromDate: '2000-01-01T00:00:00Z' } }
        }]),
        makeHolder(),
        makeChanges(
          ['nationalities:first:country', 'LU'],
          ['nationalities:first:date', '05/06/2010']
        )
      );

      expect(result.policy.clients[0]).toMatchObject({
        nationality: { first: { country: 'LU', fromDate: '2010-06-05T00:00:00Z' } }
      });
    });

    it('copies a nationality from the source holder if the target slot does not exist', () => {
      const result = service.applyHolderChanges(
        makeCci(),
        makeHolder('PHYSICAL_PERSON', {
          nationality: { first: { country: 'FR', fromDate: '2000-01-01T00:00:00Z' } }
        }),
        makeChanges(['nationalities:first:country', 'BE'])
      );

      expect(result.policy.clients[0]).toMatchObject({
        nationality: { first: { country: 'BE', fromDate: '2000-01-01T00:00:00Z' } }
      });
    });

    it('creates a complete manual nationality in the first available slot', () => {
      const result = service.applyHolderChanges(
        makeCci(),
        makeHolder(),
        makeChanges(
          ['nationalities:manual-1:country', 'IT'],
          ['nationalities:manual-1:date', '2020-01-01T00:00:00Z']
        )
      );

      expect(result.policy.clients[0]).toMatchObject({
        nationality: { first: { country: 'IT', fromDate: '2020-01-01T00:00:00Z' } }
      });
    });

    it('ignores incomplete manual nationality entries', () => {
      const result = service.applyHolderChanges(
        makeCci(),
        makeHolder(),
        makeChanges(['nationalities:manual-1:country', 'IT'])
      );

      expect(result.policy.clients[0]).not.toHaveProperty('nationality.first.country');
    });

    it('does not duplicate an existing country from the source holder', () => {
      const result = service.applyHolderChanges(
        makeCci(),
        makeHolder('PHYSICAL_PERSON', {
          nationality: { first: { country: 'FR', fromDate: '2000-01-01T00:00:00Z' } }
        }),
        makeChanges(
          ['nationalities:manual-1:country', 'FR'],
          ['nationalities:manual-1:date', '2020-01-01T00:00:00Z']
        )
      );

      expect(result.policy.clients[0]).not.toHaveProperty('nationality.first.country');
    });

    it('does not add a fourth nationality', () => {
      const warning = jest.spyOn(console, 'warn').mockImplementation(() => {});
      try {
        const result = service.applyHolderChanges(
          makeCci([{
            thirdPartyId: 'TP-1', type: 'PHYSICAL_PERSON',
            nationality: {
              first: { country: 'LU' },
              second: { country: 'FR' },
              third: { country: 'BE' }
            }
          }]),
          makeHolder(),
          makeChanges(
            ['nationalities:manual-1:country', 'IT'],
            ['nationalities:manual-1:date', '2020-01-01T00:00:00Z']
          )
        );

        expect(result.policy.clients[0]).toMatchObject({
          nationality: {
            first: { country: 'LU' },
            second: { country: 'FR' },
            third: { country: 'BE' }
          }
        });
        expect(warning).toHaveBeenCalledWith(
          '[ClientProfilingChangeService] Maximum 3 nationalities reached'
        );
      } finally {
        warning.mockRestore();
      }
    });

    it('removes a nationality by its slot without shifting others', () => {
      const result = service.applyHolderChanges(
        makeCci([{
          thirdPartyId: 'TP-1', type: 'PHYSICAL_PERSON',
          nationality: { first: { country: 'LU' }, second: { country: 'FR' } }
        }]),
        makeHolder(),
        makeChanges(),
        makeRemovals({ id: 'nationalities:first', value: 'LU' })
      );

      expect(result.policy.clients[0]).toMatchObject({
        nationality: { first: null, second: { country: 'FR' } }
      });
    });
  });
});
