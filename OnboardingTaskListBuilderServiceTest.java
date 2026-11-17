import { CoreComparisonUtils } from './core-comparison.utils';

/**
 * Unit tests for CoreComparisonUtils (CCI / Client Profiling).
 *
 * The tests use the public methods of the service instead of calling its
 * private mapping helpers. This verifies the final ComparisonSectionDto
 * and avoids coupling the tests to internal implementation details.
 *
 * Jest is used, consistently with the other *.spec.ts files in this project.
 */
describe('CoreComparisonUtils', () => {
  let service: CoreComparisonUtils;

  type Section = ReturnType<CoreComparisonUtils['contactDetails']>;
  type Physical = NonNullable<Parameters<CoreComparisonUtils['physicalPersonGeneralInformation']>[0]>;
  type Moral = NonNullable<Parameters<CoreComparisonUtils['moralPersonGeneralInformation']>[0]>;
  type Holder = Parameters<CoreComparisonUtils['contactDetails']>[0];
  type Policy = Parameters<CoreComparisonUtils['policyType']>[0];
  type Address = Parameters<CoreComparisonUtils['addressGroup']>[2];
  type ComparisonOption = { readonly value: string; readonly label: string };
  type Sources = 'snapshot' | 'portal' | 'core' | 'clip';

  interface ScalarValues {
    snapshot: string | null;
    portal: string | null;
    core: string | null;
    clip: string | null;
  }

  interface EntryField {
    key: string;
    label: string;
    value: string | null;
    kind: string;
    editable: boolean;
    options?: readonly ComparisonOption[];
  }

  interface ListEntry {
    key: string;
    label: string;
    value?: string;
    fields?: readonly EntryField[];
  }

  interface ListField {
    kind: 'list';
    key: string;
    label: string;
    entryNoun: string;
    options?: readonly ComparisonOption[];
    entryFields?: readonly { key: string; label: string; kind: string; editable: boolean }[];
    values: Record<Sources, readonly ListEntry[]>;
  }

  interface ScalarField {
    key: string;
    label: string;
    kind: string;
    editable?: boolean;
    options?: readonly ComparisonOption[];
    availableSources?: readonly string[];
    values: ScalarValues;
  }

  const countries: readonly ComparisonOption[] = [
    { value: 'LU', label: 'Luxembourg' },
    { value: 'BE', label: 'Belgium' },
    { value: 'FR', label: 'France' },
    { value: 'IT', label: 'Italy' },
  ];
  const salutations: readonly ComparisonOption[] = [
    { value: 'MR', label: 'Mr' },
    { value: 'MRS', label: 'Mrs' },
  ];
  const maritalStatuses: readonly ComparisonOption[] = [
    { value: 'SINGLE', label: 'Single' },
  ];
  const professions: readonly ComparisonOption[] = [
    { value: 'DEVELOPER', label: 'Developer' },
    { value: 'UNEMPLOYED', label: 'Unemployed' },
  ];
  const sectors: readonly ComparisonOption[] = [
    { value: 'IT', label: 'Information Technology' },
  ];
  const professionalStatuses: readonly ComparisonOption[] = [
    { value: 'EMPLOYEE', label: 'Employee' },
  ];
  const birthTowns: readonly ComparisonOption[] = [
    { value: '001', label: 'Rome' },
    { value: '002', label: 'Milan' },
  ];
  const identityTypes: readonly ComparisonOption[] = [
    { value: 'ID_CARD', label: 'ID card' },
    { value: 'PASSPORT', label: 'Signed Passport' },
  ];
  const taxReasons: readonly ComparisonOption[] = [
    { value: 'A', label: 'Reason A' },
    { value: 'B', label: 'Reason B' },
  ];
  const usPersonOptions: readonly ComparisonOption[] = [
    { value: 'US', label: 'US person' },
    { value: 'NON_US', label: 'Non-US person' },
  ];
  const fatcaOptions: readonly ComparisonOption[] = [
    { value: 'ACTIVE', label: 'Active' },
  ];
  const crsOptions: readonly ComparisonOption[] = [
    { value: 'REPORTABLE', label: 'Reportable' },
  ];
  const consentOptions: readonly ComparisonOption[] = [
    { value: 'YES', label: 'Yes' },
    { value: 'NO', label: 'No' },
  ];
  const languages: readonly ComparisonOption[] = [
    { value: 'EN', label: 'English' },
    { value: 'FR', label: 'French' },
  ];
  const taxDeductionModes: readonly ComparisonOption[] = [
    { value: 'DEDUCTIBLE', label: 'Deductible' },
  ];

  const physical = (overrides: Record<string, unknown> = {}): Physical => ({
    thirdPartyId: 'TP-100',
    salutation: 'MR',
    lastname: 'Doe',
    firstname: 'Alex',
    birthDate: '1990-06-10T12:00:00Z',
    birthCountry: 'LU',
    italianBirthCode: '001',
    civilStatus: { status: 'SINGLE' },
    nationality: { first: { country: 'LU', fromDate: '2020-01-15T12:00:00Z' } },
    ...overrides,
  }) as unknown as Physical;

  const moral = (overrides: Record<string, unknown> = {}): Moral => ({
    thirdPartyId: 'TP-200',
    companyLegalForm: 'SA',
    name: 'Example SA',
    economicSector: 'IT',
    creationDate: '2020-03-18T12:00:00Z',
    leiCode: 'LEI-123',
    countryOfIncorporation: 'LU',
    ...overrides,
  }) as unknown as Moral;

  const holder = (overrides: Record<string, unknown> = {}): Holder => ({
    thirdPartyId: 'TP-100',
    emails: [{ email: 'alex@example.com' }],
    phoneNumbers: [{ phoneNumber: '+352123456' }],
    legalAddress: { address: 'Main Street', no: '12', town: 'Luxembourg', country: 'LU' },
    ...overrides,
  }) as unknown as Holder;

  const policy = (overrides: Record<string, unknown> = {}): Policy => ({
    taxDeductionMode: 'DEDUCTIBLE',
    holder: {
      sendingAddress: {
        address: {
          streetName: 'Main Street',
          streetNumber: '12',
          residenceName: 'Residence A',
          apartmentNumber: '4',
          postalBox: 'PO 10',
          townName: 'Luxembourg',
          postCode: 'L-1000',
          country: { code: 'LU' },
        },
        language: { code: 'EN' },
      },
    },
    digitalConsent: {
      useDigitalPlatformConsentStatus: 'YES',
      useElectronicSignatureConsentStatus: 'NO',
      receiveElectronicCommunicationsConsentStatus: 'YES',
    },
    ...overrides,
  }) as unknown as Policy;

  function field(section: Section, key: string): Section['fields'][number] {
    const result = section.fields.find(item => item.key === key);
    if (!result) {
      throw new Error(`Field "${key}" missing from "${section.id}"`);
    }
    return result;
  }

  function scalar(section: Section, key: string): ScalarField {
    return field(section, key) as unknown as ScalarField;
  }

  function list(section: Section, key: string): ListField {
    return field(section, key) as unknown as ListField;
  }

  function entryField(entry: ListEntry, key: string): EntryField {
    const result = entry.fields?.find(item => item.key === key);
    if (!result) {
      throw new Error(`Entry field "${key}" missing from entry "${entry.key}"`);
    }
    return result;
  }

  const generalPhysical = (
    snapshot: Physical | null,
    portal: Physical | null = null,
    core: Physical | null = null,
    clip: Physical | null = null,
    controllingPerson = false,
  ) => service.physicalPersonGeneralInformation(
    snapshot,
    portal,
    core,
    clip,
    salutations,
    countries,
    professions,
    sectors,
    maritalStatuses,
    professionalStatuses,
    birthTowns,
    controllingPerson,
  );

  const generalMoral = (
    snapshot: Moral | null,
    portal: Moral | null = null,
    core: Moral | null = null,
    clip: Moral | null = null,
  ) => service.moralPersonGeneralInformation(snapshot, portal, core, clip, countries, sectors);

  const physicalTax = (
    snapshot: Physical,
    portal: Physical | null = null,
    core: Physical | null = null,
    clip: Physical | null = null,
  ) => service.physicalTaxInformation(
    snapshot,
    portal,
    core,
    clip,
    countries,
    taxReasons,
    usPersonOptions,
  );

  const moralTax = (
    snapshot: Moral,
    portal: Moral | null = null,
    core: Moral | null = null,
    clip: Moral | null = null,
  ) => service.moralTaxInformation(
    snapshot,
    portal,
    core,
    clip,
    fatcaOptions,
    crsOptions,
    countries,
    taxReasons,
  );

  beforeEach(() => {
    service = new CoreComparisonUtils();
  });

  describe('general information - physical person', () => {
    it('creates the expected section and core fields', () => {
      const result = generalPhysical(physical());

      expect(result.id).toBe('general-information');
      expect(result.title).toBe('General information');
      expect(result.sources).toBeDefined();
      expect(result.fields.map(value => value.key)).toEqual(expect.arrayContaining([
        'thirdPartyId', 'salutation', 'lastname', 'firstname',
        'birth-date', 'birth-country', 'italian-birth-town-code',
        'nationalities', 'status',
      ]));
    });

    it('maps the four sources to their own values', () => {
      const result = generalPhysical(
        physical({ lastname: 'Snapshot' }),
        physical({ lastname: 'Portal' }),
        physical({ lastname: 'Core' }),
        physical({ lastname: 'Current' }),
      );

      expect(scalar(result, 'lastname').values).toEqual({
        snapshot: 'Snapshot', portal: 'Portal', core: 'Core', clip: 'Current',
      });
    });

    it('makes the third-party ID read-only', () => {
      const result = generalPhysical(physical());
      expect(scalar(result, 'thirdPartyId')).toMatchObject({
        kind: 'text', editable: false,
      });
    });

    it('maps the salutation and country fields to selects with options', () => {
      const result = generalPhysical(physical());
      expect(scalar(result, 'salutation')).toMatchObject({ kind: 'select', options: salutations });
      expect(scalar(result, 'birth-country')).toMatchObject({ kind: 'select', options: countries });
      expect(scalar(result, 'salutation').values.snapshot).toBe('MR');
      expect(scalar(result, 'birth-country').values.snapshot).toBe('LU');
    });

    it('formats the birth date and exposes it as a date field', () => {
      const result = generalPhysical(physical({ birthDate: '2000-05-20T12:00:00Z' }));
      expect(scalar(result, 'birth-date')).toMatchObject({ kind: 'date' });
      expect(scalar(result, 'birth-date').values.snapshot).toBe('20/05/2000');
    });

    it('allows the Italian birth town code to be resolved only from core or manually', () => {
      const result = generalPhysical(physical());
      expect(scalar(result, 'italian-birth-town-code')).toMatchObject({
        kind: 'select', options: birthTowns, availableSources: ['core', 'manual'],
      });
    });

    it('does not add the trustee field for an ordinary holder', () => {
      const result = generalPhysical(physical(), null, null, null, false);
      expect(result.fields.some(value => value.key === 'trustee-if-moral-person')).toBe(false);
    });

    it('adds the trustee field for a controlling person', () => {
      const result = generalPhysical(
        physical({ trusteeIfMoralPerson: 'John Doe' }), null, null, null, true,
      );
      expect(scalar(result, 'trustee-if-moral-person').values.snapshot).toBe('John Doe');
    });

    it('does not add professional fields without current professional details', () => {
      const result = generalPhysical(physical({ professionalDetails: undefined }));
      expect(result.fields.some(value => value.key === 'profession')).toBe(false);
    });

    it('adds professional fields when current professional details exist', () => {
      const result = generalPhysical(physical({
        professionalDetails: {
          profession: 'DEVELOPER', status: 'EMPLOYEE', companyName: 'ABC', sector: 'IT',
        },
      }));

      expect(result.fields.map(value => value.key)).toEqual(expect.arrayContaining([
        'profession', 'profession-status', 'employer-name', 'industry-sector',
      ]));
      expect(scalar(result, 'profession').values.clip).toBe('DEVELOPER');
      expect(scalar(result, 'profession').options).toEqual(professions);
    });

    it('does not add profession status, employer or sector when unemployed', () => {
      const result = generalPhysical(physical({
        professionalDetails: { profession: 'unemployed', status: 'EMPLOYEE', companyName: 'ABC' },
      }));

      expect(result.fields.some(value => value.key === 'profession')).toBe(true);
      expect(result.fields.some(value => value.key === 'employer-name')).toBe(false);
      expect(result.fields.some(value => value.key === 'profession-status')).toBe(false);
      expect(result.fields.some(value => value.key === 'industry-sector')).toBe(false);
    });
  });

  describe('general information - moral person', () => {
    it('creates the moral-person fields with the expected keys', () => {
      const result = generalMoral(moral());
      expect(result).toMatchObject({ id: 'general-information', title: 'General information' });
      expect(result.fields.map(value => value.key)).toEqual(expect.arrayContaining([
        'thirdPartyId', 'legal-form', 'name', 'industry-sector',
        'creation-date', 'lei-code', 'incorporation-country',
      ]));
    });

    it('keeps the third-party ID and legal form non-editable', () => {
      const result = generalMoral(moral());
      expect(scalar(result, 'thirdPartyId').editable).toBe(false);
      expect(scalar(result, 'legal-form').editable).toBe(false);
    });

    it('maps company names from snapshot, portal, core and current data', () => {
      const result = generalMoral(
        moral({ name: 'Snapshot SA' }),
        moral({ name: 'Portal SA' }),
        moral({ name: 'Core SA' }),
        moral({ name: 'Current SA' }),
      );

      expect(scalar(result, 'name').values).toEqual({
        snapshot: 'Snapshot SA', portal: 'Portal SA', core: 'Core SA', clip: 'Current SA',
      });
    });

    it('maps economic sector and country of incorporation using options', () => {
      const result = generalMoral(moral());
      expect(scalar(result, 'industry-sector')).toMatchObject({ kind: 'select', options: sectors });
      expect(scalar(result, 'industry-sector').values.snapshot).toBe('IT');
      expect(scalar(result, 'incorporation-country')).toMatchObject({ kind: 'select', options: countries });
      expect(scalar(result, 'incorporation-country').values.snapshot).toBe('LU');
    });

    it('formats creation date and keeps LEI code', () => {
      const result = generalMoral(moral());
      expect(scalar(result, 'creation-date').values.snapshot).toBe('18/03/2020');
      expect(scalar(result, 'lei-code').values.snapshot).toBe('LEI-123');
    });
  });

  describe('contact details', () => {
    it('creates email and phone lists and the legal address', () => {
      const result = service.contactDetails(holder(), null, null, null, countries);
      expect(result).toMatchObject({ id: 'contact-details', title: 'Contact details' });
      expect(result.fields.map(value => value.key)).toEqual(expect.arrayContaining([
        'emails', 'phone-numbers', 'legal-address',
      ]));
    });

    it('maps email and phone values from every source', () => {
      const result = service.contactDetails(
        holder({ emails: [{ email: 's@test.com' }], phoneNumbers: [{ phoneNumber: '+11111' }] }),
        holder({ emails: [{ email: 'p@test.com' }], phoneNumbers: [{ phoneNumber: '+22222' }] }),
        holder({ emails: [{ email: 'c@test.com' }], phoneNumbers: [{ phoneNumber: '+33333' }] }),
        holder({ emails: [{ email: 'x@test.com' }], phoneNumbers: [{ phoneNumber: '+44444' }] }),
        countries,
      );

      expect(list(result, 'emails').values).toMatchObject({
        snapshot: [{ key: 's@test.com', value: 's@test.com' }],
        portal: [{ key: 'p@test.com', value: 'p@test.com' }],
        core: [{ key: 'c@test.com', value: 'c@test.com' }],
        clip: [{ key: 'x@test.com', value: 'x@test.com' }],
      });
      expect(list(result, 'phone-numbers').values).toMatchObject({
        snapshot: [{ key: '+11111', value: '+11111' }],
        portal: [{ key: '+22222', value: '+22222' }],
        core: [{ key: '+33333', value: '+33333' }],
        clip: [{ key: '+44444', value: '+44444' }],
      });
    });

    it('uses option labels for known list values and the original string otherwise', () => {
      const result = service.contactDetails(holder(), null, null, null, countries);
      const items = list(result, 'emails').values.snapshot;
      expect(items[0]).toMatchObject({
        key: 'alex@example.com', label: 'alex@example.com', value: 'alex@example.com',
      });
    });

    it('maps missing lists to empty arrays', () => {
      const result = service.contactDetails(
        holder({ emails: null, phoneNumbers: undefined, legalAddress: null }),
        null, null, null, countries,
      );
      expect(list(result, 'emails').values).toEqual({ snapshot: [], portal: [], core: [], clip: [] });
      expect(list(result, 'phone-numbers').values).toEqual({ snapshot: [], portal: [], core: [], clip: [] });
    });
  });

  describe('address group', () => {
    const makeAddress = (overrides: Record<string, unknown> = {}): Address => ({
      address: 'Main Street',
      no: '12',
      residence: 'Residence A',
      appartNo: '3',
      poBox: 'PO 1',
      town: 'Luxembourg',
      postCode: 'L-1000',
      county: 'Luxembourg',
      area: 'Center',
      country: 'LU',
      ...overrides,
    }) as unknown as Address;

    it('creates a group with address and country lines', () => {
      const result = service.addressGroup(
        'legal-address', 'Legal address', makeAddress(), null, null, null, countries,
      );
      expect(result).toMatchObject({
        key: 'legal-address',
        label: 'Legal address',
        kind: 'group',
        lines: [['no', 'address'], ['postcode', 'city'], ['country']],
      });
    });

    it('maps the component address fields', () => {
      const result = service.addressGroup(
        'legal-address', 'Legal address', makeAddress(), null, null, null, countries,
      );
      const inner = result.fields;
      expect(inner.map(value => value.key)).toEqual(expect.arrayContaining([
        'address', 'no', 'residence', 'appartNo', 'poBox', 'city',
        'postcode', 'county', 'area', 'country',
      ]));
      const street = inner.find(value => value.key === 'address') as unknown as ScalarField;
      const city = inner.find(value => value.key === 'city') as unknown as ScalarField;
      expect(street.values.snapshot).toBe('Main Street');
      expect(city.values.snapshot).toBe('Luxembourg');
    });

    it('preserves differences between all four address sources', () => {
      const result = service.addressGroup(
        'address', 'Address',
        makeAddress({ town: 'Snapshot city' }),
        makeAddress({ town: 'Portal city' }),
        makeAddress({ town: 'Core city' }),
        makeAddress({ town: 'Clip city' }),
        countries,
      );
      const city = result.fields.find(value => value.key === 'city') as unknown as ScalarField;
      expect(city.values).toEqual({
        snapshot: 'Snapshot city', portal: 'Portal city',
        core: 'Core city', clip: 'Clip city',
      });
    });
  });

  describe('identity documents', () => {
    const personWithDocument = (data: Record<string, unknown>): Physical => physical({ idDocument: data });

    it('creates the identity document section', () => {
      const result = service.idDocument(personWithDocument({ type: 'PASSPORT', number: 'A123' }), null, null, null, identityTypes);
      expect(result).toMatchObject({ id: 'identity-documents', title: 'Identity Documents' });
      expect(result.fields.map(value => value.key)).toEqual(expect.arrayContaining(['type', 'number']));
    });

    it('maps document types to select options', () => {
      const result = service.idDocument(personWithDocument({ type: 'PASSPORT' }), null, null, null, identityTypes);
      expect(scalar(result, 'type')).toMatchObject({ kind: 'select', options: identityTypes });
      expect(scalar(result, 'type').values.snapshot).toBe('PASSPORT');
    });

    it('uses a different document number for each source', () => {
      const p = (number: string) => personWithDocument({ type: 'PASSPORT', number });
      const result = service.idDocument(p('S'), p('P'), p('C'), p('X'), identityTypes);
      expect(scalar(result, 'number').values).toEqual({ snapshot: 'S', portal: 'P', core: 'C', clip: 'X' });
    });

    it('formats the expiration date', () => {
      const result = service.idDocument(
        personWithDocument({ type: 'ID_CARD', expirationDate: '2030-12-05T12:00:00Z' }),
        null, null, null, identityTypes,
      );
      expect(scalar(result, 'expirationDate')).toMatchObject({ kind: 'date' });
      expect(scalar(result, 'expirationDate').values.snapshot).toBe('05/12/2030');
    });

    it('returns null values when no identity document exists', () => {
      const result = service.idDocument(physical({ idDocument: null }), null, null, null, identityTypes);
      expect(scalar(result, 'type').values.snapshot).toBeNull();
      expect(scalar(result, 'number').values.snapshot).toBeNull();
    });
  });

  describe('nationalities - structured list', () => {
    it('maps first, second and third nationalities to structured entries', () => {
      const result = generalPhysical(physical({
        nationality: {
          first: { country: 'LU', fromDate: '2020-01-15T12:00:00Z' },
          second: { country: 'BE', fromDate: '2022-02-16T12:00:00Z' },
          third: { country: 'FR', fromDate: '2023-03-17T12:00:00Z' },
        },
      }));
      const nationalities = list(result, 'nationalities');

      expect(nationalities).toMatchObject({ kind: 'list', entryNoun: 'nationality' });
      expect(nationalities.values.snapshot.map(item => item.key)).toEqual(['first', 'second', 'third']);
      expect(nationalities.values.snapshot.map(item => item.label)).toEqual([
        'Luxembourg', 'Belgium', 'France',
      ]);
    });

    it('creates editable country and obtaining date fields for each entry', () => {
      const result = generalPhysical(physical());
      const nationalities = list(result, 'nationalities');
      const first = nationalities.values.snapshot[0];

      expect(entryField(first, 'country')).toMatchObject({
        value: 'LU', label: 'Nationality', kind: 'select', editable: true, options: countries,
      });
      expect(entryField(first, 'date')).toMatchObject({
        label: 'Obtaining date', kind: 'date', editable: true, value: '15/01/2020',
      });
      expect(nationalities.entryFields).toEqual(expect.arrayContaining([
        expect.objectContaining({ key: 'country', kind: 'select', editable: true }),
        expect.objectContaining({ key: 'date', kind: 'date', editable: true }),
      ]));
    });

    it('keeps first/second/third source entries independent', () => {
      const result = generalPhysical(
        physical({ nationality: { first: { country: 'LU' } } }),
        physical({ nationality: { second: { country: 'BE' } } }),
        physical({ nationality: { third: { country: 'FR' } } }),
        physical({ nationality: { first: { country: 'IT' } } }),
      );
      const values = list(result, 'nationalities').values;
      expect(values.snapshot.map(entry => entry.key)).toEqual(['first']);
      expect(values.portal.map(entry => entry.key)).toEqual(['second']);
      expect(values.core.map(entry => entry.key)).toEqual(['third']);
      expect(values.clip.map(entry => entry.label)).toEqual(['Italy']);
    });

    it('drops null nationality items and items without a country', () => {
      const result = generalPhysical(physical({ nationality: {
        first: null, second: { country: '' }, third: { country: 'BE' },
      } }));
      expect(list(result, 'nationalities').values.snapshot.map(item => item.key)).toEqual(['third']);
    });

    it('uses the raw country code when the option list has no matching value', () => {
      const result = generalPhysical(physical({ nationality: { first: { country: 'XX' } } }));
      expect(list(result, 'nationalities').values.snapshot[0].label).toBe('XX');
    });

    it('returns empty arrays when nationality data is absent', () => {
      const result = generalPhysical(physical({ nationality: null }));
      expect(list(result, 'nationalities').values).toEqual({
        snapshot: [], portal: [], core: [], clip: [],
      });
    });
  });

  describe('tax information - structured list', () => {
    const item = (overrides: Record<string, unknown> = {}) => ({
      taxCountry: 'LU',
      taxNumber: 'TIN-123',
      missingTaxNumberReason: null,
      preferred: true,
      ...overrides,
    });

    it('creates a tax-information list for physical persons', () => {
      const result = physicalTax(physical({ taxInformations: [item()] }));
      const tax = list(result, 'tax-information');
      expect(result.id).toBe('tax-information');
      expect(tax).toMatchObject({ kind: 'list', key: 'tax-information', entryNoun: 'tax information' });
      expect(tax.entryFields).toEqual(expect.arrayContaining([
        expect.objectContaining({ key: 'tax-country', kind: 'select', editable: false }),
        expect.objectContaining({ key: 'tin', kind: 'text', editable: true }),
        expect.objectContaining({ key: 'tin-unavailable-reason', kind: 'select', editable: true }),
        expect.objectContaining({ key: 'is-default', kind: 'select', editable: true }),
      ]));
    });

    it('maps tax country, TIN and preferred flag', () => {
      const result = physicalTax(physical({ taxInformations: [item()] }));
      const taxEntry = list(result, 'tax-information').values.snapshot[0];

      expect(taxEntry.key).toBe('LU');
      expect(entryField(taxEntry, 'tax-country')).toMatchObject({
        kind: 'select', value: 'LU', editable: false, options: countries,
      });
      expect(entryField(taxEntry, 'tin')).toMatchObject({
        kind: 'text', value: 'TIN-123', editable: true,
      });
      expect(entryField(taxEntry, 'is-default')).toMatchObject({ value: 'true' });
    });

    it('shows the TIN unavailable reason only when the TIN is missing', () => {
      const result = physicalTax(physical({ taxInformations: [
        item({ taxCountry: 'LU', taxNumber: 'TIN-123' }),
        item({ taxCountry: 'BE', taxNumber: '', missingTaxNumberReason: 'A', preferred: false }),
      ] }));
      const entries = list(result, 'tax-information').values.snapshot;

      expect(entries[0].fields?.some(value => value.key === 'tin-unavailable-reason')).toBe(false);
      expect(entryField(entries[1], 'tin-unavailable-reason')).toMatchObject({
        kind: 'select', editable: true, value: 'A', options: taxReasons,
      });
      expect(entryField(entries[1], 'is-default').value).toBe('false');
    });

    it('maps an independent tax list for each source', () => {
      const result = physicalTax(
        physical({ taxInformations: [item({ taxCountry: 'LU' })] }),
        physical({ taxInformations: [item({ taxCountry: 'BE' })] }),
        physical({ taxInformations: [item({ taxCountry: 'FR' })] }),
        physical({ taxInformations: [item({ taxCountry: 'IT' })] }),
      );

      const values = list(result, 'tax-information').values;
      expect(values.snapshot.map(entry => entry.key)).toEqual(['LU']);
      expect(values.portal.map(entry => entry.key)).toEqual(['BE']);
      expect(values.core.map(entry => entry.key)).toEqual(['FR']);
      expect(values.clip.map(entry => entry.key)).toEqual(['IT']);
    });

    it('returns empty lists when the tax information is missing', () => {
      const result = physicalTax(physical({ taxInformations: undefined }));
      expect(list(result, 'tax-information').values).toEqual({
        snapshot: [], portal: [], core: [], clip: [],
      });
    });

    it('includes the US-person select for physical persons', () => {
      const result = physicalTax(physical({ fatcaStatus: 'US' }));
      expect(scalar(result, 'fatcaStatus')).toMatchObject({ kind: 'select', options: usPersonOptions });
      expect(scalar(result, 'fatcaStatus').values.snapshot).toBe('US');
    });

    it('includes FATCA, CRS, US-entity and GIIN fields for moral persons', () => {
      const result = moralTax(moral({
        fatcaStatus: 'ACTIVE', aeoiStatus: 'REPORTABLE', usEntity: false, giin: 'GIIN-1',
      }));
      expect(result.id).toBe('tax-information');
      expect(result.fields.map(value => value.key)).toEqual(expect.arrayContaining([
        'tax-information', 'fatcaStatus', 'aeoiStatus', 'us-entity', 'giin',
      ]));
      expect(scalar(result, 'fatcaStatus').options).toEqual(fatcaOptions);
      expect(scalar(result, 'aeoiStatus').options).toEqual(crsOptions);
      expect(scalar(result, 'giin').values.snapshot).toBe('GIIN-1');
    });

    it('keeps boolean false as the string "false" for US entity', () => {
      const result = moralTax(moral({ usEntity: false }));
      expect(scalar(result, 'us-entity').values.snapshot).toBe('false');
    });

    it('does not convert a missing US-entity value to "undefined"', () => {
      const result = moralTax(moral({ usEntity: undefined }));
      expect(scalar(result, 'us-entity').values.snapshot).toBeNull();
    });
  });

  describe('policy sections', () => {
    it('maps the sending address to its section', () => {
      const result = service.policySendingAddressAndCommunicationPreferences(
        policy(), null, null, null, countries, languages, consentOptions,
      );
      expect(result.id).toBe('sending-address');
      expect(result.fields.map(value => value.key)).toEqual(expect.arrayContaining([
        'street', 'number', 'house-name', 'apartment-number', 'postal-box',
        'city', 'postcode', 'country', 'language',
        'consent-to-use-digital-platform',
        'consent-to-use-electronic-signature',
        'receive-electronic-communication',
      ]));
    });

    it('reads the street and city from the nested policy sending address', () => {
      const result = service.policySendingAddressAndCommunicationPreferences(
        policy(), null, null, null, countries, languages, consentOptions,
      );
      expect(scalar(result, 'street').values.snapshot).toBe('Main Street');
      expect(scalar(result, 'number').values.snapshot).toBe('12');
      expect(scalar(result, 'city').values.snapshot).toBe('Luxembourg');
      expect(scalar(result, 'postcode').values.snapshot).toBe('L-1000');
    });

    it('maps country, language and consent as selects', () => {
      const result = service.policySendingAddressAndCommunicationPreferences(
        policy(), null, null, null, countries, languages, consentOptions,
      );
      expect(scalar(result, 'country')).toMatchObject({ kind: 'select', options: countries });
      expect(scalar(result, 'country').values.snapshot).toBe('LU');
      expect(scalar(result, 'language')).toMatchObject({ kind: 'select', options: languages });
      expect(scalar(result, 'language').values.snapshot).toBe('EN');
      expect(scalar(result, 'consent-to-use-digital-platform')).toMatchObject({
        kind: 'select', options: consentOptions,
      });
      expect(scalar(result, 'consent-to-use-digital-platform').values.snapshot).toBe('YES');
      expect(scalar(result, 'consent-to-use-electronic-signature').values.snapshot).toBe('NO');
      expect(scalar(result, 'receive-electronic-communication').values.snapshot).toBe('YES');
    });

    it('maps current/clip sending address separately from snapshot', () => {
      const currentPolicy = policy({
        holder: { sendingAddress: { address: { streetName: 'Current Street' } } },
      });
      const result = service.policySendingAddressAndCommunicationPreferences(
        policy(), null, null, currentPolicy, countries, languages, consentOptions,
      );
      expect(scalar(result, 'street').values).toMatchObject({
        snapshot: 'Main Street', clip: 'Current Street',
      });
    });

    it('creates the opt-in/out section with the policy type select', () => {
      const result = service.policyType(
        policy(), null, null, null, taxDeductionModes,
      );
      expect(result.id).toBe('opt-in-out');
      expect(scalar(result, 'policy-type')).toMatchObject({
        kind: 'select', options: taxDeductionModes,
      });
    });
  });

  describe('empty section and normalization behavior', () => {
    it('creates an empty section without any field', () => {
      const result = service.emptySection('unknown-section', 'Unknown Section');
      expect(result).toMatchObject({
        id: 'unknown-section', title: 'Unknown Section', fields: [],
      });
      expect(result.sources).toBeDefined();
    });

    it('formats valid Date objects to en-GB day/month/year', () => {
      expect(service.formatDate(new Date('2024-07-15T12:00:00Z'))).toBe('15/07/2024');
    });

    it('formats valid date strings', () => {
      expect(service.formatDate('2024-12-31T12:00:00Z')).toBe('31/12/2024');
    });

    it.each([null, undefined, ''])('returns null for missing date value %p', (value) => {
      expect(service.formatDate(value)).toBeNull();
    });

    it('returns null for invalid date strings', () => {
      expect(service.formatDate('not-a-date')).toBeNull();
    });

    it('trims text values and maps whitespace-only values to null', () => {
      const result = generalPhysical(physical({ lastname: '  Doe  ', firstname: '  ' }));
      expect(scalar(result, 'lastname').values.snapshot).toBe('Doe');
      expect(scalar(result, 'firstname').values.snapshot).toBeNull();
    });

    it('maps absent source values to null rather than undefined', () => {
      const result = generalPhysical(physical({ lastname: undefined }), null, null, null);
      expect(scalar(result, 'lastname').values).toEqual({
        snapshot: null, portal: null, core: null, clip: null,
      });
    });
  });
});
