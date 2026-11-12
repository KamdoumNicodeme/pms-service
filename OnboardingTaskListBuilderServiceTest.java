import { signal } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of, Subject, throwError } from 'rxjs';

import { CciManualChangeComponent } from './cci-manual-change.component';
import { CCIManualChangeForm } from './forms/cci-manual-change.form';
import { CASE_SUMMARY_SERVICE } from '../../../../core/providers/data-services/case-summary.service';
import { COMPARISON_DATA_SERVICE } from '../../../../core/providers/data-services/comparison-data.service';
import { ClientProfilingChangeService } from '../../../../core/providers/services/client-profiling-change.service';
import { AssignmentService } from '../../../../core/providers/services/assignment.service';
import { ApplicationUsersService } from '../../../../core/providers/services/application-users.service';
import { CasesService } from '../../../../core/providers/services/cases.service';
import { TaskType, UserService } from '@lia/commons';

jest.mock('./forms/cci-manual-change.form', () => ({
  CCIManualChangeForm: jest.fn().mockImplementation(() => ({
    buildPayload: jest.fn().mockReturnValue({ mocked: 'payload' })
  }))
}));

describe('CciManualChangeComponent', () => {
  let fixture: ComponentFixture<CciManualChangeComponent>;
  let component: CciManualChangeComponent;

  let caseSummaryServiceMock: { getSummary: jest.Mock };
  let comparisonDataServiceMock: { setReferences: jest.Mock };
  let assignmentServiceMock: { openAssignmentModal: jest.Mock };
  let applicationUsersServiceMock: {
    findAll: jest.Mock;
    findAllUserTeams: jest.Mock;
  };
  let casesServiceMock: {
    getCaseDataAfterClientProfiling: jest.Mock;
    getCaseBusinessDataUptoDate: jest.Mock;
    saveTask: jest.Mock;
    completeTask: jest.Mock;
  };

  const caseId = 'CASE-123';
  const taskId = 555;
  const currentCase = {
    caseBusinessIdentifier: caseId,
    ownerTeam: 'TeamA',
    ownerDetails: { displayName: 'John', username: 'john.doe' }
  };
  const currentTask = { id: 999 };
  const owner = { assignee: 'jane.doe', team: 'Operations' };
  const manualChange = { someField: 'prepopulatedData' };
  const consent = {
    receiveElectronicCommunicationsConsentStatus: 'ACCEPTED',
    useDigitalPlatformConsentStatus: 'PENDING',
    useElectronicSignatureConsentStatus: 'ACCEPTED'
  };
  const policy = {
    holder: { sendingAddress: { address: {} } },
    digitalConsent: consent
  };
  const references = { COUNTRY: [{ key: 'LU', value: 'Luxembourg' }] };

  function profilingData(clientConsent: typeof consent | null = consent) {
    return {
      manualChange,
      changeClientInformation: {
        policy: { digitalConsent: clientConsent }
      }
    };
  }

  function businessData(classConsent: typeof consent | null = consent) {
    return { policy: { digitalConsent: classConsent } };
  }

  async function createComponent(): Promise<void> {
    fixture = TestBed.createComponent(CciManualChangeComponent);
    component = fixture.componentInstance;

    fixture.componentRef.setInput('currentCase', currentCase);
    fixture.componentRef.setInput('currentTask', currentTask);
    fixture.componentRef.setInput('policy', policy);
    fixture.componentRef.setInput('clientProfilingReferences', references);
    fixture.detectChanges();
    await fixture.whenStable();
  }

  beforeEach(async () => {
    jest.clearAllMocks();

    caseSummaryServiceMock = {
      getSummary: jest.fn().mockReturnValue(of({ summaryText: 'Mock summary' }))
    };
    comparisonDataServiceMock = {
      setReferences: jest.fn()
    };
    assignmentServiceMock = {
      openAssignmentModal: jest.fn().mockReturnValue({ afterClose: of(owner) })
    };
    applicationUsersServiceMock = {
      findAll: jest.fn().mockReturnValue(of([])),
      findAllUserTeams: jest.fn().mockReturnValue(of(['TeamA', 'TeamB']))
    };
    casesServiceMock = {
      getCaseDataAfterClientProfiling: jest.fn().mockReturnValue(of(profilingData())),
      getCaseBusinessDataUptoDate: jest.fn().mockReturnValue(of(businessData())),
      saveTask: jest.fn().mockReturnValue(of({})),
      completeTask: jest.fn().mockReturnValue(of({}))
    };

    const providers = [
      { provide: CASE_SUMMARY_SERVICE, useValue: caseSummaryServiceMock },
      { provide: COMPARISON_DATA_SERVICE, useValue: comparisonDataServiceMock },
      { provide: ClientProfilingChangeService, useValue: {} },
      { provide: AssignmentService, useValue: assignmentServiceMock },
      { provide: ApplicationUsersService, useValue: applicationUsersServiceMock },
      { provide: CasesService, useValue: casesServiceMock },
      {
        provide: UserService,
        useValue: { user: signal({ preferred_username: 'john.doe' }) }
      }
    ];

    await TestBed.configureTestingModule({
      imports: [CciManualChangeComponent],
      providers
    })
      .overrideComponent(CciManualChangeComponent, {
        set: {
          template: '',
          imports: [],
          providers
        }
      })
      .compileComponents();
  });

  it('should create', async () => {
    await createComponent();
    expect(component).toBeTruthy();
  });

  it('should load client profiling and current business data', async () => {
    await createComponent();
    expect(casesServiceMock.getCaseDataAfterClientProfiling).toHaveBeenCalledWith(caseId);
    expect(casesServiceMock.getCaseBusinessDataUptoDate).toHaveBeenCalledWith(caseId);
  });

  it('should load the case summary', async () => {
    await createComponent();
    expect(caseSummaryServiceMock.getSummary).toHaveBeenCalledWith(
      caseId,
      expect.objectContaining({ caseBusinessIdentifier: caseId })
    );
  });

  it('should initialize comparison references', async () => {
    await createComponent();
    expect(comparisonDataServiceMock.setReferences).toHaveBeenCalledWith(references);
  });

  it('should update comparison references when the input changes', async () => {
    await createComponent();
    const nextReferences = { COUNTRY: [{ key: 'IT', value: 'Italy' }] };

    fixture.componentRef.setInput('clientProfilingReferences', nextReferences);
    fixture.detectChanges();
    await fixture.whenStable();

    expect(comparisonDataServiceMock.setReferences).toHaveBeenLastCalledWith(nextReferences);
  });

  it('should create the manual change form with the loaded data', async () => {
    await createComponent();
    component['form']();

    expect(CCIManualChangeForm).toHaveBeenCalledWith(
      component.checksCompleted,
      manualChange,
      false
    );
  });

  it('should load users and their teams', async () => {
    await createComponent();
    expect(applicationUsersServiceMock.findAll).toHaveBeenCalled();
    expect(applicationUsersServiceMock.findAllUserTeams).toHaveBeenCalledWith('john.doe');
    expect(component.teams()).toEqual([
      { displayName: 'TeamA', value: 'TeamA' },
      { displayName: 'TeamB', value: 'TeamB' }
    ]);
  });

  describe('Communication preferences', () => {
    it('should return false when both sources are identical', async () => {
      await createComponent();
      expect(component['isCommPreferencesChanged']()).toBe(false);
    });

    it.each([
      'receiveElectronicCommunicationsConsentStatus',
      'useDigitalPlatformConsentStatus',
      'useElectronicSignatureConsentStatus'
    ] as const)('should detect a change in %s', async field => {
      const changedConsent = { ...consent, [field]: 'DECLINED' };
      casesServiceMock.getCaseDataAfterClientProfiling.mockReturnValue(
        of(profilingData(changedConsent))
      );

      await createComponent();
      expect(component['isCommPreferencesChanged']()).toBe(true);
    });

    it('should return false when the client consent is absent', async () => {
      casesServiceMock.getCaseDataAfterClientProfiling.mockReturnValue(
        of(profilingData(null))
      );
      await createComponent();
      expect(component['isCommPreferencesChanged']()).toBe(false);
    });

    it('should return false when the CLASS consent is absent', async () => {
      casesServiceMock.getCaseBusinessDataUptoDate.mockReturnValue(
        of(businessData(null))
      );
      await createComponent();
      expect(component['isCommPreferencesChanged']()).toBe(false);
    });

    it('should return false before profiling data has loaded', async () => {
      casesServiceMock.getCaseDataAfterClientProfiling.mockReturnValue(of(null));
      await createComponent();
      expect(component['isCommPreferencesChanged']()).toBe(false);
    });
  });

  describe('Save', () => {
    it('should save the payload and notify success', async () => {
      await createComponent();
      const onSucceed = jest.fn();

      component.save(caseId, taskId, onSucceed);

      expect(casesServiceMock.saveTask).toHaveBeenCalledWith(
        caseId,
        taskId,
        TaskType.CCI_MANUAL_CHANGE,
        { mocked: 'payload' }
      );
      expect(onSucceed).toHaveBeenCalledTimes(1);
    });
  });

  describe('Complete', () => {
    it('should open the sign-off assignment modal with the available teams', async () => {
      await createComponent();
      component.complete(caseId, taskId, jest.fn());

      expect(assignmentServiceMock.openAssignmentModal).toHaveBeenCalledWith(
        [
          { displayName: 'TeamA', value: 'TeamA' },
          { displayName: 'TeamB', value: 'TeamB' }
        ],
        'Sign Off assignment',
        { displayName: 'TeamA', value: 'TeamA' },
        { displayName: 'John', value: 'john.doe' }
      );
    });

    it('should complete with the selected sign-off assignee', async () => {
      await createComponent();
      const onSucceed = jest.fn();

      component.complete(caseId, taskId, onSucceed);

      expect(casesServiceMock.completeTask).toHaveBeenCalledWith(
        caseId,
        taskId,
        TaskType.CCI_MANUAL_CHANGE,
        {
          mocked: 'payload',
          signOffAssignment: { user: 'jane.doe', team: 'Operations' }
        }
      );
      expect(onSucceed).toHaveBeenCalledTimes(1);
    });

    it('should not complete if the assignment dialog is cancelled', async () => {
      assignmentServiceMock.openAssignmentModal.mockReturnValue({ afterClose: of(null) });
      await createComponent();
      const onSucceed = jest.fn();

      component.complete(caseId, taskId, onSucceed);

      expect(casesServiceMock.completeTask).not.toHaveBeenCalled();
      expect(onSucceed).not.toHaveBeenCalled();
    });

    it('should wait for the assignment dialog to return a value', async () => {
      const assignmentResult$ = new Subject<typeof owner | null>();
      assignmentServiceMock.openAssignmentModal.mockReturnValue({
        afterClose: assignmentResult$.asObservable()
      });
      await createComponent();
      const onSucceed = jest.fn();

      component.complete(caseId, taskId, onSucceed);
      expect(casesServiceMock.completeTask).not.toHaveBeenCalled();

      assignmentResult$.next(owner);
      expect(casesServiceMock.completeTask).toHaveBeenCalledTimes(1);
      expect(onSucceed).toHaveBeenCalledTimes(1);
      assignmentResult$.complete();
    });

    it('should not notify success if completion fails', async () => {
      casesServiceMock.completeTask.mockReturnValue(
        throwError(() => new Error('Completion failed'))
      );
      await createComponent();
      const onSucceed = jest.fn();
      const errorSpy = jest.spyOn(console, 'error').mockImplementation(() => {});

      try {
        component.complete(caseId, taskId, onSucceed);
        expect(onSucceed).not.toHaveBeenCalled();
        expect(errorSpy).toHaveBeenCalled();
      } finally {
        errorSpy.mockRestore();
      }
    });
  });
});
