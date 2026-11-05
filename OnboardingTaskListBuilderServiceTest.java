import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideNoopAnimations } from '@angular/platform-browser/animations';
import { provideNzIcons } from 'ng-zorro-antd/icon';

import { ChangeClientInformationSignOffComponent } from './change-client-information-sign-off.component';

import { CasesService } from '../../core/providers/services/cases.service';
import { UserService } from '../../core/providers/services/user.service';
import { AssignmentService } from '../../core/providers/services/assignment.service';
import { NzModalService } from 'ng-zorro-antd/modal';

import { IThirdParty } from '../../core/models/third-party.model';
import { IPolicy } from '../../core/models/policy.model';

describe('ChangeClientInformationSignOffComponent', () => {
  let component: ChangeClientInformationSignOffComponent;
  let fixture: ComponentFixture<ChangeClientInformationSignOffComponent>;

  const MOCK_CASES_SERVICE = {
    getCaseDataAfterClientProfiling: jest.fn(),
    getCaseBusinessDataUptoDate: jest.fn(),
    validateDocument: jest.fn(),
    rejectDocument: jest.fn(),
    uploadDocument: jest.fn(),
    removeDocument: jest.fn(),
    replacementDocument: jest.fn(),
    downloadDocument: jest.fn(),
    completeTask: jest.fn(),
    rejectTask: jest.fn(),
  };

  const MOCK_USER_SERVICE = {
    user: jest.fn(),
    findAll: jest.fn(),
    findAllUserTeams: jest.fn(),
  };

  const MOCK_ASSIGNMENT_SERVICE = {
    openAssignmentModal: jest.fn(),
  };

  const MOCK_MODAL_SERVICE = {
    confirm: jest.fn(),
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ChangeClientInformationSignOffComponent],
      providers: [
        provideNoopAnimations(),
        provideNzIcons([]),
        {
          provide: CasesService,
          useValue: MOCK_CASES_SERVICE,
        },
        {
          provide: UserService,
          useValue: MOCK_USER_SERVICE,
        },
        {
          provide: AssignmentService,
          useValue: MOCK_ASSIGNMENT_SERVICE,
        },
        {
          provide: NzModalService,
          useValue: MOCK_MODAL_SERVICE,
        },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(
      ChangeClientInformationSignOffComponent,
    );

    component = fixture.componentInstance;
  });

  afterEach(() => {
    jest.clearAllMocks();
  });

  describe('isControllingPerson', () => {
    test('should return true when client is Economic Beneficiary Owner', () => {
      const client = {
        thirdPartyId: 'client-1',
        roleTypes: ['Economic_Beneficiary_Owner'],
      } as IThirdParty;

      const result = (component as any).isControllingPerson(client);

      expect(result).toBe(true);
    });

    test('should return true when client is Trustee', () => {
      const client = {
        thirdPartyId: 'client-1',
        roleTypes: ['Trustee'],
      } as IThirdParty;

      const result = (component as any).isControllingPerson(client);

      expect(result).toBe(true);
    });

    test('should return true when client is Holder and Economic Beneficiary Owner', () => {
      const client = {
        thirdPartyId: 'client-1',
        roleTypes: [
          'Holder',
          'Economic_Beneficiary_Owner',
        ],
      } as IThirdParty;

      const result = (component as any).isControllingPerson(client);

      expect(result).toBe(true);
    });

    test('should return true when client is Holder and Trustee', () => {
      const client = {
        thirdPartyId: 'client-1',
        roleTypes: [
          'Holder',
          'Trustee',
        ],
      } as IThirdParty;

      const result = (component as any).isControllingPerson(client);

      expect(result).toBe(true);
    });

    test('should return true when client is Holder, Trustee and Economic Beneficiary Owner', () => {
      const client = {
        thirdPartyId: 'client-1',
        roleTypes: [
          'Holder',
          'Trustee',
          'Economic_Beneficiary_Owner',
        ],
      } as IThirdParty;

      const result = (component as any).isControllingPerson(client);

      expect(result).toBe(true);
    });

    test('should return false when client is only Holder', () => {
      const client = {
        thirdPartyId: 'client-1',
        roleTypes: ['Holder'],
      } as IThirdParty;

      const result = (component as any).isControllingPerson(client);

      expect(result).toBe(false);
    });

    test('should return false when client has no controlling person role', () => {
      const client = {
        thirdPartyId: 'client-1',
        roleTypes: ['Client_Advisor'],
      } as IThirdParty;

      const result = (component as any).isControllingPerson(client);

      expect(result).toBe(false);
    });

    test('should return false when roleTypes is empty', () => {
      const client = {
        thirdPartyId: 'client-1',
        roleTypes: [],
      } as IThirdParty;

      const result = (component as any).isControllingPerson(client);

      expect(result).toBe(false);
    });

    test('should return false when roleTypes is undefined', () => {
      const client = {
        thirdPartyId: 'client-1',
        roleTypes: undefined,
      } as unknown as IThirdParty;

      const result = (component as any).isControllingPerson(client);

      expect(result).toBe(false);
    });
  });

  describe('getPolicyHolders', () => {
    test('should return client when client is only Holder', () => {
      const holder = {
        thirdPartyId: 'holder-1',
        roleTypes: ['Holder'],
      } as IThirdParty;

      const policy = {
        clients: [holder],
      } as IPolicy;

      const result = (component as any).getPolicyHolders(policy);

      expect(result).toEqual([holder]);
    });

    test('should exclude client when client is Holder and Economic Beneficiary Owner', () => {
      const client = {
        thirdPartyId: 'holder-ebo-1',
        roleTypes: [
          'Holder',
          'Economic_Beneficiary_Owner',
        ],
      } as IThirdParty;

      const policy = {
        clients: [client],
      } as IPolicy;

      const result = (component as any).getPolicyHolders(policy);

      expect(result).toEqual([]);
    });

    test('should exclude client when client is Holder and Trustee', () => {
      const client = {
        thirdPartyId: 'holder-trustee-1',
        roleTypes: [
          'Holder',
          'Trustee',
        ],
      } as IThirdParty;

      const policy = {
        clients: [client],
      } as IPolicy;

      const result = (component as any).getPolicyHolders(policy);

      expect(result).toEqual([]);
    });

    test('should exclude client when client is Holder, Trustee and Economic Beneficiary Owner', () => {
      const client = {
        thirdPartyId: 'holder-controlling-1',
        roleTypes: [
          'Holder',
          'Trustee',
          'Economic_Beneficiary_Owner',
        ],
      } as IThirdParty;

      const policy = {
        clients: [client],
      } as IPolicy;

      const result = (component as any).getPolicyHolders(policy);

      expect(result).toEqual([]);
    });

    test('should not return Economic Beneficiary Owner without Holder role', () => {
      const client = {
        thirdPartyId: 'ebo-1',
        roleTypes: ['Economic_Beneficiary_Owner'],
      } as IThirdParty;

      const policy = {
        clients: [client],
      } as IPolicy;

      const result = (component as any).getPolicyHolders(policy);

      expect(result).toEqual([]);
    });

    test('should not return Trustee without Holder role', () => {
      const client = {
        thirdPartyId: 'trustee-1',
        roleTypes: ['Trustee'],
      } as IThirdParty;

      const policy = {
        clients: [client],
      } as IPolicy;

      const result = (component as any).getPolicyHolders(policy);

      expect(result).toEqual([]);
    });

    test('should return only Holder who is not a controlling person', () => {
      const holder = {
        thirdPartyId: 'holder-1',
        roleTypes: ['Holder'],
      } as IThirdParty;

      const holderAndEbo = {
        thirdPartyId: 'holder-ebo-1',
        roleTypes: [
          'Holder',
          'Economic_Beneficiary_Owner',
        ],
      } as IThirdParty;

      const holderAndTrustee = {
        thirdPartyId: 'holder-trustee-1',
        roleTypes: [
          'Holder',
          'Trustee',
        ],
      } as IThirdParty;

      const ebo = {
        thirdPartyId: 'ebo-1',
        roleTypes: ['Economic_Beneficiary_Owner'],
      } as IThirdParty;

      const trustee = {
        thirdPartyId: 'trustee-1',
        roleTypes: ['Trustee'],
      } as IThirdParty;

      const policy = {
        clients: [
          holder,
          holderAndEbo,
          holderAndTrustee,
          ebo,
          trustee,
        ],
      } as IPolicy;

      const result = (component as any).getPolicyHolders(policy);

      expect(result).toHaveLength(1);
      expect(result).toEqual([holder]);
    });

    test('should return empty array when clients is empty', () => {
      const policy = {
        clients: [],
      } as IPolicy;

      const result = (component as any).getPolicyHolders(policy);

      expect(result).toEqual([]);
    });

    test('should return empty array when clients is undefined', () => {
      const policy = {} as IPolicy;

      const result = (component as any).getPolicyHolders(policy);

      expect(result).toEqual([]);
    });

    test('should return empty array when policy is undefined', () => {
      const result = (component as any).getPolicyHolders(undefined);

      expect(result).toEqual([]);
    });
  });

  describe('Holder and controlling person classification', () => {
    test('should classify Holder only as policy holder', () => {
      const client = {
        thirdPartyId: 'client-1',
        roleTypes: ['Holder'],
      } as IThirdParty;

      const policy = {
        clients: [client],
      } as IPolicy;

      const holders =
        (component as any).getPolicyHolders(policy);

      const isControllingPerson =
        (component as any).isControllingPerson(client);

      expect(holders).toContain(client);
      expect(isControllingPerson).toBe(false);
    });

    test('should classify Holder and Economic Beneficiary Owner only as controlling person', () => {
      const client = {
        thirdPartyId: 'client-1',
        roleTypes: [
          'Holder',
          'Economic_Beneficiary_Owner',
        ],
      } as IThirdParty;

      const policy = {
        clients: [client],
      } as IPolicy;

      const holders =
        (component as any).getPolicyHolders(policy);

      const isControllingPerson =
        (component as any).isControllingPerson(client);

      expect(holders).not.toContain(client);
      expect(isControllingPerson).toBe(true);
    });

    test('should classify Holder and Trustee only as controlling person', () => {
      const client = {
        thirdPartyId: 'client-1',
        roleTypes: [
          'Holder',
          'Trustee',
        ],
      } as IThirdParty;

      const policy = {
        clients: [client],
      } as IPolicy;

      const holders =
        (component as any).getPolicyHolders(policy);

      const isControllingPerson =
        (component as any).isControllingPerson(client);

      expect(holders).not.toContain(client);
      expect(isControllingPerson).toBe(true);
    });
  });
});
