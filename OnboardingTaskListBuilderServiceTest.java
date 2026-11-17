import { TestBed } from '@angular/core/testing';
import { firstValueFrom } from 'rxjs';

// Adapt these two relative paths to your project structure if necessary.
import { CoreComparisonDataService } from './core-comparison-data.service';
import { CoreComparisonUtils } from './core-comparison.utils';

describe('CoreComparisonDataService', () => {
  let service: CoreComparisonDataService;
  let utils: {
    policySendingAddressAndCommunicationPreferences: jest.Mock;
    policyType: jest.Mock;
    physicalPersonGeneralInformation: jest.Mock;
    moralPersonGeneralInformation: jest.Mock;
    contactDetails: jest.Mock;
    idDocument: jest.Mock;
    physicalTaxInformation: jest.Mock;
    moralTaxInformation: jest.Mock;
    emptySection: jest.Mock;
  };

  // Derive types from the public API instead of guessing domain model imports.
  type HolderContext = Parameters<CoreComparisonDataService['getSection']>[0];
  type Policy = Parameters<CoreComparisonDataService['getPolicySection']>[0];
  type References = Parameters<CoreComparisonDataService['setReferences']>[0];
  type TownReferences = Parameters<CoreComparisonDataService['setItalianBirthTownCode']>[0];

  const sectionResult = { id: 'test-section', title: 'Test section', rows: [] };

  const physical = (id = 'P1'): HolderContext['snapshotHolder'] =>
    ({ thirdPartyId: id, birthDate: '1990-01-01', roleTypes: [] }) as unknown as HolderContext['snapshotHolder'];

  const moral = (id = 'M1'): HolderContext['snapshotHolder'] =>
    ({ thirdPartyId: id, creationDate: '2020-01-01', roleTypes: [] }) as unknown as HolderContext['snapshotHolder'];

  const context = (overrides: Partial<HolderContext> = {}): HolderContext => ({
    snapshotHolder: physical(),
    portalHolder: null,
    coreHolder: null,
    clipHolder: null,
    ...overrides,
  });

  const references = (value: Record<string, { key: string; value: string }[]>): References =>
    value as References;

  beforeEach(() => {
    utils = {
      policySendingAddressAndCommunicationPreferences: jest.fn(() => sectionResult),
      policyType: jest.fn(() => sectionResult),
      physicalPersonGeneralInformation: jest.fn(() => sectionResult),
      moralPersonGeneralInformation: jest.fn(() => sectionResult),
      contactDetails: jest.fn(() => sectionResult),
      idDocument: jest.fn(() => sectionResult),
      physicalTaxInformation: jest.fn(() => sectionResult),
      moralTaxInformation: jest.fn(() => sectionResult),
      emptySection: jest.fn(() => sectionResult),
    };

    TestBed.configureTestingModule({
      providers: [
        CoreComparisonDataService,
        { provide: CoreComparisonUtils, useValue: utils },
      ],
    });

    service = TestBed.inject(CoreComparisonDataService);
  });

  describe('reference options', () => {
    it('should return empty options before references are loaded', () => {
      expect(service.getSignatureOptions()()).toEqual([]);
      expect(service.getYesNoNAOptions()()).toEqual([]);
      expect(service.getYesNoOptions()()).toEqual([]);
      expect(service.getReasonOptions()()).toEqual([]);
    });

    it('should map reference key to label and reference value to value', () => {
      service.setReferences(references({
        SIGNATURE_TYPE: [{ key: 'Wet signature', value: 'WET' }],
        YES_NO_NOTAPPLICABLE: [{ key: 'Not applicable', value: 'NA' }],
        YESNO: [{ key: 'Yes', value: 'YES' }],
        CHANGE_REASON: [{ key: 'Correction', value: 'CORRECTION' }],
      }));

      expect(service.getSignatureOptions()()).toEqual([{ value: 'WET', label: 'Wet signature' }]);
      expect(service.getYesNoNAOptions()()).toEqual([{ value: 'NA', label: 'Not applicable' }]);
      expect(service.getYesNoOptions()()).toEqual([{ value: 'YES', label: 'Yes' }]);
      expect(service.getReasonOptions()()).toEqual([{ value: 'CORRECTION', label: 'Correction' }]);
    });

    it('should recompute previously retrieved option signals when references change', () => {
      const signatureOptions = service.getSignatureOptions();
      expect(signatureOptions()).toEqual([]);

      service.setReferences(references({
        SIGNATURE_TYPE: [{ key: 'Digital signature', value: 'DIGITAL' }],
      }));
      expect(signatureOptions()).toEqual([{ value: 'DIGITAL', label: 'Digital signature' }]);

      service.setReferences(references({
        SIGNATURE_TYPE: [{ key: 'Wet signature', value: 'WET' }],
      }));
      expect(signatureOptions()).toEqual([{ value: 'WET', label: 'Wet signature' }]);
    });

    it('should expose Italian birth town labels and replace existing entries with the same code', () => {
      const townLabels = service.getItalianBirthTownLabels();
      const towns = (items: { key: string; value: string }[]): TownReferences =>
        items as TownReferences;

      expect(townLabels()).toEqual([]);

      service.addItalianBirthTownOptions(towns([
        { key: 'Rome', value: '001' },
        { key: 'Milan', value: '002' },
      ]));
      expect(townLabels()).toEqual([
        { value: '001', label: 'Rome' },
        { value: '002', label: 'Milan' },
      ]);

      service.addItalianBirthTownOptions(towns([
        { key: 'Roma', value: '001' },
        { key: 'Naples', value: '003' },
      ]));
      expect(townLabels()).toEqual([
        { value: '001', label: 'Roma' },
        { value: '002', label: 'Milan' },
        { value: '003', label: 'Naples' },
      ]);
    });
  });

  describe('getPolicySection', () => {
    const policy = {} as Policy;

    it('should route sending-address to the utility with country, language and consent options', async () => {
      service.setReferences(references({
        COUNTRY: [{ key: 'Luxembourg', value: 'LU' }],
        LANGUAGE: [{ key: 'English', value: 'EN' }],
        CONSENT_STATUS: [{ key: 'Accepted', value: 'YES' }],
      }));

      const result = await firstValueFrom(
        service.getPolicySection(policy, null, null, null, 'sending-address'),
      );

      expect(result).toBe(sectionResult);
      expect(utils.policySendingAddressAndCommunicationPreferences).toHaveBeenCalledWith(
        policy,
        null,
        null,
        null,
        [{ value: 'LU', label: 'Luxembourg' }],
        [{ value: 'EN', label: 'English' }],
        [{ value: 'YES', label: 'Accepted' }],
      );
      expect(utils.policyType).not.toHaveBeenCalled();
    });

    it('should route opt-in-out to policyType with tax deduction mode options', async () => {
      service.setReferences(references({
        TAX_DEDUCTION_MODE: [{ key: 'Deductible', value: 'D' }],
      }));

      const result = await firstValueFrom(
        service.getPolicySection(policy, null, null, null, 'opt-in-out'),
      );

      expect(result).toBe(sectionResult);
      expect(utils.policyType).toHaveBeenCalledWith(
        policy,
        null,
        null,
        null,
        [{ value: 'D', label: 'Deductible' }],
      );
    });

    it('should reject an unknown policy section', async () => {
      await expect(
        firstValueFrom(service.getPolicySection(policy, null, null, null, 'unknown')),
      ).rejects.toThrow('Unknown policy section "unknown"');
    });
  });

  describe('getSection', () => {
    it('should route contact-details with country options', async () => {
      const input = context();
      service.setReferences(references({
        COUNTRY: [{ key: 'Belgium', value: 'BE' }],
      }));

      const result = await firstValueFrom(service.getSection(input, 'contact-details'));

      expect(result).toBe(sectionResult);
      expect(utils.contactDetails).toHaveBeenCalledWith(
        input.snapshotHolder,
        null,
        null,
        null,
        [{ value: 'BE', label: 'Belgium' }],
      );
    });

    it('should route physical general-information and pass only compatible source holders', async () => {
      const snapshot = physical('P1');
      const portal = moral('M2');
      const core = physical('P3');
      const input = context({ snapshotHolder: snapshot, portalHolder: portal, coreHolder: core });

      const result = await firstValueFrom(service.getSection(input, 'general-information'));

      expect(result).toBe(sectionResult);
      expect(utils.physicalPersonGeneralInformation).toHaveBeenCalledWith(
        snapshot,
        null,
        core,
        null,
        expect.any(Array),  // salutation
        expect.any(Array),  // country
        expect.any(Array),  // profession
        expect.any(Array),  // industry sector
        expect.any(Array),  // marital status
        expect.any(Array),  // professional status
        expect.any(Array),  // Italian birth town codes
        false,              // controlling person, with no relevant role
      );
      expect(utils.moralPersonGeneralInformation).not.toHaveBeenCalled();
    });

    it('should route moral general-information to the moral person utility', async () => {
      const snapshot = moral();
      const portal = moral('M2');

      const result = await firstValueFrom(service.getSection(
        context({ snapshotHolder: snapshot, portalHolder: portal }),
        'general-information',
      ));

      expect(result).toBe(sectionResult);
      expect(utils.moralPersonGeneralInformation).toHaveBeenCalledWith(
        snapshot,
        portal,
        null,
        null,
        expect.any(Array), // country options
        expect.any(Array), // economic sector options
      );
      expect(utils.physicalPersonGeneralInformation).not.toHaveBeenCalled();
    });

    it('should throw if a physical snapshot has an incompatible moral CLIP holder', () => {
      expect(() => service.getSection(
        context({ snapshotHolder: physical(), clipHolder: moral() }),
        'general-information',
      )).toThrow('clipHolder must be a physical person');
    });

    it('should throw if a moral snapshot has an incompatible physical CLIP holder', () => {
      expect(() => service.getSection(
        context({ snapshotHolder: moral(), clipHolder: physical() }),
        'general-information',
      )).toThrow('clipHolder must be a moral person');
    });

    it('should route physical identity-documents to idDocument', async () => {
      service.setReferences(references({
        ID_TYPE: [{ key: 'Passport', value: 'PASSPORT' }],
      }));
      const input = context({
        snapshotHolder: physical('P1'),
        portalHolder: moral('M2'),
        coreHolder: physical('P3'),
      });

      const result = await firstValueFrom(service.getSection(input, 'identity-documents'));

      expect(result).toBe(sectionResult);
      expect(utils.idDocument).toHaveBeenCalledWith(
        input.snapshotHolder,
        null,
        input.coreHolder,
        null,
        [{ value: 'PASSPORT', label: 'Passport' }],
      );
    });

    it('should return an empty identity-documents section for moral persons', async () => {
      const result = await firstValueFrom(service.getSection(
        context({ snapshotHolder: moral() }),
        'identity-documents',
      ));

      expect(result).toBe(sectionResult);
      expect(utils.emptySection).toHaveBeenCalledWith('identity-documents', 'Identity Documents');
      expect(utils.idDocument).not.toHaveBeenCalled();
    });

    it('should use physicalTaxInformation for physical persons', async () => {
      const input = context({ snapshotHolder: physical() });
      const result = await firstValueFrom(service.getSection(input, 'tax-information'));

      expect(result).toBe(sectionResult);
      expect(utils.physicalTaxInformation).toHaveBeenCalledWith(
        input.snapshotHolder,
        null,
        null,
        null,
        expect.any(Array), // countries
        expect.any(Array), // TIN unavailable reasons
        expect.any(Array), // FATCA status for physical persons
      );
      expect(utils.moralTaxInformation).not.toHaveBeenCalled();
    });

    it('should use moralTaxInformation for moral persons', async () => {
      const input = context({ snapshotHolder: moral() });
      const result = await firstValueFrom(service.getSection(input, 'tax-information'));

      expect(result).toBe(sectionResult);
      expect(utils.moralTaxInformation).toHaveBeenCalledWith(
        input.snapshotHolder,
        null,
        null,
        null,
        expect.any(Array), // FATCA status
        expect.any(Array), // CRS / AEOI status
        expect.any(Array), // countries
        expect.any(Array), // TIN unavailable reasons
      );
      expect(utils.physicalTaxInformation).not.toHaveBeenCalled();
    });

    it('should return an empty tax-information section when the snapshot has no recognized person type', async () => {
      const unknownHolder = { thirdPartyId: 'U1' } as HolderContext['snapshotHolder'];

      const result = await firstValueFrom(service.getSection(
        context({ snapshotHolder: unknownHolder }),
        'tax-information',
      ));

      expect(result).toBe(sectionResult);
      expect(utils.emptySection).toHaveBeenCalledWith('tax-information', 'Tax Information');
    });

    it('should reject an unknown holder section', async () => {
      await expect(
        firstValueFrom(service.getSection(context(), 'unknown')),
      ).rejects.toThrow('Unknown section "unknown"');
    });
  });

  describe('Italian birth town codes in the general information comparison', () => {
    it('should pass mapped Italian birth town codes to the physical-person utility', async () => {
      service.setItalianBirthTownCode([
        { key: 'Rome', value: '001' },
      ] as TownReferences);

      await firstValueFrom(service.getSection(context(), 'general-information'));

      const args = utils.physicalPersonGeneralInformation.mock.calls[0];
      expect(args[10]).toEqual([{ value: '001', label: 'Rome' }]);
    });
  });
});
