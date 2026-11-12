
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { signal } from '@angular/core';
import { of } from 'rxjs';

import { CciManualChangeComponent } from './cci-manual-change.component';
import { CCIManualChangeForm } from './forms/cci-manual-change.form';

jest.mock('./forms/cci-manual-change.form', () => ({
  CCIManualChangeForm: jest.fn()
}));

describe('CciManualChangeComponent', () => {
  let component: CciManualChangeComponent;
  let fixture: ComponentFixture<CciManualChangeComponent>;

  let casesService: {
    getCaseDataAfterClientProfiling: jest.Mock;
    getCaseBusinessDataUptoDate: jest.Mock;
    completeTask: jest.Mock;
  };

  let caseSummaryService: {
    getSummary: jest.Mock;
  };

  let comparisonService: {
    setReferences: jest.Mock;
  };

  let assignmentService: {
    openAssignmentModal: jest.Mock;
  };

  let applicationUsersService: {
    findAll: jest.Mock;
    findAllUserTeams: jest.Mock;
  };

  const caseId = 'CASE-123';
  const taskId = 999;

  const currentCase = {
    caseBusinessIdentifier: caseId,
    ownerTeam: 'TeamA',
    ownerDetails: {
      displayName: 'John',
      username: 'john.doe'
    }
  };

  const currentTask = {
    id: taskId
  };

  const policy = {
    holder: {
      sendingAddress: {
        salutation: 'Mr',
        address: {
          area: 'Luxembourg',
          streetName: 'Main Street',
          postCode: '1000',
          townName: 'Luxembourg',
          residenceName: '',
          postalBox: '',
          apartmentNumber: '10',
          county: '',
          country: 'LU',
          streetNumber: '12'
        },
        language: 'EN'
      }
    },
    digitalConsent: {
      receiveElectronicCommunicationsConsentStatus: 'ACCEPTED',
      useDigitalPlatformConsentStatus: 'ACCEPTED',
      useElectronicSignatureConsentStatus: 'DECLINED'
    }
  };

  const manualChange = {
    sendingAddressManualTasks: {},
    sendingRulesManualTasks: {},
    mailingRulesManualTasks: {},
    digitalConsentManualTasks: {}
  };

  beforeEach(async () => {
    jest.clearAllMocks();

    casesService = {
      getCaseDataAfterClientProfiling: jest.fn().mockReturnValue(
        of({
          manualChange,
          changeClientInformation: {
            policy: {
              digitalConsent: policy.digitalConsent
            }
          }
        })
      ),
      getCaseBusinessDataUptoDate: jest.fn().mockReturnValue(
        of({
          policy: {
            digitalConsent: policy.digitalConsent
          }
        })
      ),
      completeTask: jest.fn().mockReturnValue(of({}))
    };

    caseSummaryService = {
      getSummary: jest.fn().mockReturnValue(of({}))
    };

    comparisonService = {
      setReferences: jest.fn()
    };

    assignmentService = {
      openAssignmentModal: jest.fn()
    };

    applicationUsersService = {
      findAll: jest.fn().mockReturnValue(of([])),
      findAllUserTeams: jest.fn().mockReturnValue(of(['TeamA']))
    };

    (CCIManualChangeForm as jest.Mock).mockImplementation(() => ({
      buildPayload: jest.fn().mockReturnValue({
        sendingAddressManualTasks: {},
        sendingRulesManualTasks: {},
        mailingRulesManualTasks: {}
      }),
      valid: true
    }));

    await TestBed.configureTestingModule({
      imports: [CciManualChangeComponent],
      providers: [
        {
          provide: CasesService,
          useValue: casesService
        },
        {
          provide: CASE_SUMMARY_SERVICE,
          useValue: caseSummaryService
        },
        {
          provide: COMPARISON_DATA_SERVICE,
          useValue: comparisonService
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
          provide: UserService,
          useValue: {
            user: signal({
              preferred_username: 'john.doe'
            })
          }
        }
      ]
    })
      .overrideComponent(CciManualChangeComponent, {
        set: {
          template: '',
          imports: []
        }
      })
      .compileComponents();

    fixture = TestBed.createComponent(CciManualChangeComponent);
    component = fixture.componentInstance;

    fixture.componentRef.setInput('currentCase', currentCase);
    fixture.componentRef.setInput('currentTask', currentTask);
    fixture.componentRef.setInput('policy', policy);
    fixture.componentRef.setInput('readOnly', false);

    fixture.detectChanges();
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should load client profiling data', () => {
    expect(
      casesService.getCaseDataAfterClientProfiling
    ).toHaveBeenCalledWith(caseId);

    expect(
      casesService.getCaseBusinessDataUptoDate
    ).toHaveBeenCalledWith(caseId);
  });

  it('should initialize comparison references', () => {
    const references = {
      COUNTRY: [
        {
          key: 'LU',
          value: 'Luxembourg'
        }
      ]
    };

    fixture.componentRef.setInput(
      'clientProfilingReferences',
      references
    );

    fixture.detectChanges();

    expect(comparisonService.setReferences).toHaveBeenCalledWith(
      references
    );
  });

  it('should create the manual change form', () => {
    const form = component.form();

    expect(form).toBeDefined();
    expect(CCIManualChangeForm).toHaveBeenCalled();
  });

  it('should detect no communication preferences changes when values are identical', () => {
    const result = component.isCommPreferencesChanged();

    expect(result).toBe(false);
  });

  it('should detect communication preferences changes', () => {
    casesService.getCaseDataAfterClientProfiling.mockReturnValue(
      of({
        manualChange,
        changeClientInformation: {
          policy: {
            digitalConsent: {
              receiveElectronicCommunicationsConsentStatus: 'DECLINED',
              useDigitalPlatformConsentStatus: 'ACCEPTED',
              useElectronicSignatureConsentStatus: 'DECLINED'
            }
          }
        }
      })
    );

    fixture.componentRef.setInput('currentCase', {
      ...currentCase,
      caseBusinessIdentifier: 'CASE-456'
    });

    fixture.detectChanges();

    expect(component.isCommPreferencesChanged()).toBe(true);
  });

  it('should not detect changes when communication preferences are missing', () => {
    casesService.getCaseDataAfterClientProfiling.mockReturnValue(
      of({
        manualChange,
        changeClientInformation: {
          policy: {}
        }
      })
    );

    fixture.componentRef.setInput('currentCase', {
      ...currentCase,
      caseBusinessIdentifier: 'CASE-789'
    });

    fixture.detectChanges();

    expect(component.isCommPreferencesChanged()).toBe(false);
  });

  it('should complete the task after assignment', () => {
    const onSucceed = jest.fn();

    assignmentService.openAssignmentModal.mockReturnValue({
      afterClose: of({
        assignee: 'john.doe',
        team: 'TeamA'
      })
    });

    component.complete(caseId, taskId, onSucceed);

    expect(
      assignmentService.openAssignmentModal
    ).toHaveBeenCalled();

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

    expect(casesService.completeTask).not.toHaveBeenCalled();
    expect(onSucceed).not.toHaveBeenCalled();
  });

  it('should throw when reject is called', () => {
    expect(() => {
      component.reject(caseId, taskId, jest.fn());
    }).toThrow('Method not implemented.');
  });
});
