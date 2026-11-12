
import { TestBed, ComponentFixture } from '@angular/core/testing';
import { signal } from '@angular/core';
import { of, throwError } from 'rxjs';

import { CciManualChangeComponent } from './cci-manual-change.component';
import { CCIManualChangeForm } from './forms/cci-manual-change.form';

import { CASE_SUMMARY_SERVICE, CaseSummaryService } from 'YOUR_IMPORT';
import { CasesService } from 'YOUR_IMPORT';
import { AssignmentService } from 'YOUR_IMPORT';
import { ApplicationUsersService } from 'YOUR_IMPORT';
import { UserService } from 'YOUR_IMPORT';
import {
  COMPARISON_DATA_SERVICE,
  ComparisonDataService
} from 'YOUR_IMPORT';

import {
  TaskType,
  ICaseDetails,
  ITaskDetails,
  IPolicy,
  ClientProfilingData,
  ClientProfilingLastBusiness,
  IManualChange
} from 'YOUR_IMPORT';

jest.mock('./forms/cci-manual-change.form', () => ({
  CCIManualChangeForm: jest.fn().mockImplementation(
    (_validationEmitter, initialData, needCommPreferences) => ({
      initialData,
      needCommPreferences,
      buildPayload: jest.fn().mockReturnValue({
        sendingAddressManualTasks: {},
        sendingRulesManualTasks: {},
        mailingRulesManualTasks: {}
      })
    })
  )
}));

describe('CciManualChangeComponent', () => {
  let fixture: ComponentFixture<CciManualChangeComponent>;
  let component: CciManualChangeComponent;

  let caseSummaryService: {
    getSummary: jest.Mock;
  };

  let casesService: {
    getCaseDataAfterClientProfiling: jest.Mock;
    getCaseBusinessDataUptoDate: jest.Mock;
    completeTask: jest.Mock;
  };

  let assignmentService: {
    openAssignmentModal: jest.Mock;
  };

  let applicationUsersService: {
    findAllUserTeams: jest.Mock;
  };

  let comparisonService: {
    setReferences: jest.Mock;
  };

  let userService: {
    user: ReturnType<typeof signal>;
    findAll: jest.Mock;
  };

  const caseId = 'CASE-123';
  const taskId = 999;

  const manualChange = {
    sendingAddressManualTasks: {},
    sendingRulesManualTasks: {},
    mailingRulesManualTasks: {}
  } as IManualChange;

  const policy = {
    holder: {
      sendingAddress: {
        salutation: 'Mr',
        address: {
          area: '',
          streetName: 'Main Street',
          postCode: '1234',
          townName: 'Luxembourg',
          residenceName: '',
          postalBox: '',
          apartmentNumber: '',
          county: '',
          country: 'LU',
          streetNumber: '10'
        },
        language: 'EN'
      }
    },
    digitalConsent: {
      receiveElectronicCommunicationsConsentStatus: 'ACCEPTED',
      useDigitalPlatformConsentStatus: 'ACCEPTED',
      useElectronicSignatureConsentStatus: 'ACCEPTED'
    }
  } as unknown as IPolicy;

  const caseDetails = {
    caseBusinessIdentifier: caseId,
    ownerTeam: 'TeamA',
    ownerDetails: {
      displayName: 'John Doe',
      username: 'john.doe'
    }
  } as ICaseDetails;

  const taskDetails = {
    id: taskId
  } as ITaskDetails;

  const references = {
    COUNTRY: [
      {
        key: 'LU',
        value: 'Luxembourg'
      }
    ]
  };

  const createClientProfilingData = (
    digitalConsent = policy.digitalConsent
  ): ClientProfilingData =>
    ({
      manualChange,
      changeClientInformation: {
        policy: {
          digitalConsent
        }
      }
    }) as unknown as ClientProfilingData;

  const createBusinessData = (
    digitalConsent = policy.digitalConsent
  ): ClientProfilingLastBusiness =>
    ({
      policy: {
        digitalConsent
      }
    }) as unknown as ClientProfilingLastBusiness;

  const initializeComponent = async (
    clientData = createClientProfilingData(),
    businessData = createBusinessData()
  ): Promise<void> => {
    casesService.getCaseDataAfterClientProfiling.mockReturnValue(
      of(clientData)
    );

    casesService.getCaseBusinessDataUptoDate.mockReturnValue(
      of(businessData)
    );

    fixture = TestBed.createComponent(CciManualChangeComponent);
    component = fixture.componentInstance;

    fixture.componentRef.setInput('currentCase', caseDetails);
    fixture.componentRef.setInput('currentTask', taskDetails);
    fixture.componentRef.setInput('policy', policy);
    fixture.componentRef.setInput(
      'clientProfilingReferences',
      references
    );

    fixture.detectChanges();
    await fixture.whenStable();
  };

  beforeEach(async () => {
    jest.clearAllMocks();

    caseSummaryService = {
      getSummary: jest.fn().mockReturnValue(of({}))
    };

    casesService = {
      getCaseDataAfterClientProfiling: jest.fn(),
      getCaseBusinessDataUptoDate: jest.fn(),
      completeTask: jest.fn().mockReturnValue(of({}))
    };

    assignmentService = {
      openAssignmentModal: jest.fn()
    };

    applicationUsersService = {
      findAllUserTeams: jest.fn().mockReturnValue(
        of(['TeamA', 'TeamB'])
      )
    };

    comparisonService = {
      setReferences: jest.fn()
    };

    userService = {
      user: signal({
        preferred_username: 'john.doe'
      }),
      findAll: jest.fn().mockReturnValue(of([]))
    };

    await TestBed.configureTestingModule({
      imports: [CciManualChangeComponent],
      providers: [
        {
          provide: CASE_SUMMARY_SERVICE,
          useValue: caseSummaryService
        },
        {
          provide: CasesService,
          useValue: casesService
        },
        {
          provide: AssignmentService,
          useValue: assignmentService
        },
        {
          provide: ApplicationUsersService,
          useValue: applicationUsersService
        },
        {
          provide: COMPARISON_DATA_SERVICE,
          useValue: comparisonService
        },
        {
          provide: UserService,
          useValue: userService
        }
      ]
    })
      .overrideComponent(CciManualChangeComponent, {
        set: {
          template: ''
        }
      })
      .compileComponents();

    await initializeComponent();
  });

  afterEach(() => {
    fixture.destroy();
  });

  describe('Component initialization', () => {
    it('should create', () => {
      expect(component).toBeTruthy();
    });

    it('should load client profiling data', () => {
      expect(
        casesService.getCaseDataAfterClientProfiling
      ).toHaveBeenCalledWith(caseId);

      expect(component['clientProfilingCasesData']()).toEqual(
        createClientProfilingData()
      );
    });

    it('should load current business data', () => {
      expect(
        casesService.getCaseBusinessDataUptoDate
      ).toHaveBeenCalledWith(caseId);

      expect(component['getCurrentBusinessData']()).toEqual(
        createBusinessData()
      );
    });

    it('should initialize comparison references', async () => {
      comparisonService.setReferences.mockClear();

      fixture.componentRef.setInput(
        'clientProfilingReferences',
        {
          COUNTRY: [
            {
              key: 'BE',
              value: 'Belgium'
            }
          ]
        }
      );

      fixture.detectChanges();
      await fixture.whenStable();

      expect(
        comparisonService.setReferences
      ).toHaveBeenCalledWith({
        COUNTRY: [
          {
            key: 'BE',
            value: 'Belgium'
          }
        ]
      });
    });

    it('should initialize the manual change form', () => {
      expect(component['form']()).toBeDefined();

      expect(CCIManualChangeForm).toHaveBeenCalledWith(
        component.checksCompleted,
        manualChange,
        false
      );
    });

    it('should initialize users', () => {
      expect(userService.findAll).toHaveBeenCalled();
    });

    it('should load user teams', () => {
      expect(
        applicationUsersService.findAllUserTeams
      ).toHaveBeenCalledWith('john.doe');

      expect(component.teams()).toEqual([
        {
          displayName: 'TeamA',
          value: 'TeamA'
        },
        {
          displayName: 'TeamB',
          value: 'TeamB'
        }
      ]);
    });
  });

  describe('Communication preferences', () => {
    it('should not detect changes when values are identical', () => {
      expect(
        component['isCommPreferencesChanged']()
      ).toBe(false);
    });

    it('should detect electronic communications consent changes', async () => {
      const changedConsent = {
        ...policy.digitalConsent,
        receiveElectronicCommunicationsConsentStatus: 'DECLINED'
      };

      await initializeComponent(
        createClientProfilingData(changedConsent),
        createBusinessData()
      );

      expect(
        component['isCommPreferencesChanged']()
      ).toBe(true);
    });

    it('should detect digital platform consent changes', async () => {
      const changedConsent = {
        ...policy.digitalConsent,
        useDigitalPlatformConsentStatus: 'DECLINED'
      };

      await initializeComponent(
        createClientProfilingData(changedConsent),
        createBusinessData()
      );

      expect(
        component['isCommPreferencesChanged']()
      ).toBe(true);
    });

    it('should detect electronic signature consent changes', async () => {
      const changedConsent = {
        ...policy.digitalConsent,
        useElectronicSignatureConsentStatus: 'DECLINED'
      };

      await initializeComponent(
        createClientProfilingData(changedConsent),
        createBusinessData()
      );

      expect(
        component['isCommPreferencesChanged']()
      ).toBe(true);
    });

    it('should not detect changes when client consent is missing', async () => {
      await initializeComponent(
        createClientProfilingData(undefined),
        createBusinessData()
      );

      expect(
        component['isCommPreferencesChanged']()
      ).toBe(false);
    });

    it('should not detect changes when CLASS consent is missing', async () => {
      await initializeComponent(
        createClientProfilingData(),
        createBusinessData(undefined)
      );

      expect(
        component['isCommPreferencesChanged']()
      ).toBe(false);
    });
  });

  describe('Task completion', () => {
    const owner = {
      assignee: 'john.doe',
      team: 'TeamA'
    };

    it('should open the assignment modal', () => {
      assignmentService.openAssignmentModal.mockReturnValue({
        afterClose: of(null)
      });

      component.complete(caseId, taskId, jest.fn());

      expect(
        assignmentService.openAssignmentModal
      ).toHaveBeenCalledTimes(1);
    });

    it('should complete the task after assignment', () => {
      const onSucceed = jest.fn();

      assignmentService.openAssignmentModal.mockReturnValue({
        afterClose: of(owner)
      });

      component.complete(caseId, taskId, onSucceed);

      expect(casesService.completeTask).toHaveBeenCalledWith(
        caseId,
        taskId,
        TaskType.CCI_MANUAL_CHANGE,
        expect.objectContaining({
          signOffAssignment: {
            user: 'john.doe',
            team: 'TeamA'
          }
        })
      );

      expect(onSucceed).toHaveBeenCalledTimes(1);
    });

    it('should not complete when assignment is cancelled', () => {
      const onSucceed = jest.fn();

      assignmentService.openAssignmentModal.mockReturnValue({
        afterClose: of(null)
      });

      component.complete(caseId, taskId, onSucceed);

      expect(
        casesService.completeTask
      ).not.toHaveBeenCalled();

      expect(onSucceed).not.toHaveBeenCalled();
    });

    it('should complete the correct case and task', () => {
      assignmentService.openAssignmentModal.mockReturnValue({
        afterClose: of(owner)
      });

      component.complete('CASE-456', 123, jest.fn());

      expect(casesService.completeTask).toHaveBeenCalledWith(
        'CASE-456',
        123,
        TaskType.CCI_MANUAL_CHANGE,
        expect.any(Object)
      );
    });

    it('should call onSucceed only after successful completion', () => {
      const onSucceed = jest.fn();

      assignmentService.openAssignmentModal.mockReturnValue({
        afterClose: of(owner)
      });

      casesService.completeTask.mockReturnValue(of({}));

      component.complete(caseId, taskId, onSucceed);

      expect(onSucceed).toHaveBeenCalledTimes(1);
    });

    it('should not call onSucceed when completion fails', () => {
      const onSucceed = jest.fn();

      assignmentService.openAssignmentModal.mockReturnValue({
        afterClose: of(owner)
      });

      casesService.completeTask.mockReturnValue(
        throwError(() => new Error('Completion failed'))
      );

      const consoleSpy = jest
        .spyOn(console, 'error')
        .mockImplementation(() => {});

      component.complete(caseId, taskId, onSucceed);

      expect(onSucceed).not.toHaveBeenCalled();
      expect(consoleSpy).toHaveBeenCalled();

      consoleSpy.mockRestore();
    });

    it('should not complete when assignment returns undefined', () => {
      const onSucceed = jest.fn();

      assignmentService.openAssignmentModal.mockReturnValue({
        afterClose: of(undefined)
      });

      component.complete(caseId, taskId, onSucceed);

      expect(
        casesService.completeTask
      ).not.toHaveBeenCalled();

      expect(onSucceed).not.toHaveBeenCalled();
    });
  });
});
