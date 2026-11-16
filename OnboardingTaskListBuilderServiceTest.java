import { ComponentFixture, TestBed } from '@angular/core/testing';
import { signal } from '@angular/core';
import { of, throwError } from 'rxjs';
import { TaskType, UserService } from '@lia/commons';

import { CciErrorManagementSignOffComponent } from './cci-error-management-sign-off.component';

// IMPORTANT: reuse the exact import paths from the component under test.
// These paths are indicative of the client-profiling project structure.
import { CASE_SUMMARY_SERVICE } from '../../../../core/providers/services/case-summary.service.service';
import { ApplicationUsersService } from '../../../../core/providers/services/application-users.service';
import { CasesService } from '../../../../core/providers/services/cases.service';
// Replace the following two imports with the SAME imports used by your component.
import { RejectionModalService } from '../shared/rejection/rejection-modal.service';
import { CompletionConfirmationModalService } from '../shared/confirmation/completion-confirmation-modal.service';

describe('CciErrorManagementSignOffComponent', () => {
  let fixture: ComponentFixture<CciErrorManagementSignOffComponent>;
  let component: CciErrorManagementSignOffComponent;

  let caseSummaryMock: { getSummary: jest.Mock };
  let casesServiceMock: {
    getCaseDataAfterClientProfiling: jest.Mock;
    completeTask: jest.Mock;
    rejectTask: jest.Mock;
  };
  let applicationUsersMock: {
    findALL: jest.Mock;
    findAllUserTeams: jest.Mock;
  };
  let rejectionModalMock: { openRejectionModal: jest.Mock };
  let confirmationModalMock: { openConfirmation: jest.Mock };
  let userSignal: ReturnType<typeof signal<{ preferred_username: string } | null>>;

  const CASE_ID = 'CASE-123';
  const TASK_ID = 555;

  async function createComponent(): Promise<void> {
    fixture = TestBed.createComponent(CciErrorManagementSignOffComponent);
    component = fixture.componentInstance;

    // Required signal inputs. No template dependencies are needed for class tests.
    fixture.componentRef.setInput('currentCase', {
      caseBusinessIdentifier: CASE_ID
    });
    fixture.componentRef.setInput('currentTask', { taskId: TASK_ID });

    fixture.detectChanges();
    await fixture.whenStable();
  }

  beforeEach(async () => {
    caseSummaryMock = {
      getSummary: jest.fn().mockReturnValue(of({ summaryText: 'Mock summary' }))
    };
    casesServiceMock = {
      getCaseDataAfterClientProfiling: jest.fn().mockReturnValue(of({
        errorManagement: {
          check: {
            errorResolutionDetails: 'All issues resolved',
            allReturnedMessagesManaged: true
          }
        }
      })),
      completeTask: jest.fn().mockReturnValue(of({})),
      rejectTask: jest.fn().mockReturnValue(of({}))
    };
    applicationUsersMock = {
      findALL: jest.fn().mockReturnValue(of([])),
      findAllUserTeams: jest.fn().mockReturnValue(of(['Team A', 'Team B']))
    };
    rejectionModalMock = {
      openRejectionModal: jest.fn().mockReturnValue({ afterClose: of(null) })
    };
    confirmationModalMock = {
      openConfirmation: jest.fn().mockReturnValue({ afterClose: of(false) })
    };
    userSignal = signal<{ preferred_username: string } | null>({
      preferred_username: 'john.doe'
    });

    const providers = [
      { provide: CASE_SUMMARY_SERVICE, useValue: caseSummaryMock },
      { provide: CasesService, useValue: casesServiceMock },
      { provide: ApplicationUsersService, useValue: applicationUsersMock },
      { provide: UserService, useValue: { user: userSignal } },
      { provide: RejectionModalService, useValue: rejectionModalMock },
      { provide: CompletionConfirmationModalService, useValue: confirmationModalMock }
    ];

    await TestBed.configureTestingModule({
      imports: [CciErrorManagementSignOffComponent],
      providers
    })
      .overrideComponent(CciErrorManagementSignOffComponent, {
        set: {
          template: '',
          imports: [],
          // Important if the component declares its own providers.
          providers
        }
      })
      .compileComponents();
  });

  afterEach(() => jest.restoreAllMocks());

  it('should create the component', async () => {
    await createComponent();
    expect(component).toBeTruthy();
  });

  it('should load client-profiling case data for the current case', async () => {
    await createComponent();
    expect(casesServiceMock.getCaseDataAfterClientProfiling)
      .toHaveBeenCalledWith(CASE_ID);
  });

  it('should load users', async () => {
    await createComponent();
    expect(applicationUsersMock.findALL).toHaveBeenCalledTimes(1);
    expect(component.users()).toEqual([]);
  });

  it('should load teams of the current user and map their labels', async () => {
    await createComponent();
    expect(applicationUsersMock.findAllUserTeams)
      .toHaveBeenCalledWith('john.doe');
    expect(component.teams()).toEqual([
      { displayName: 'Team A', value: 'Team A' },
      { displayName: 'Team B', value: 'Team B' }
    ]);
  });

  it('should initialize its form from previously saved error management data', async () => {
    await createComponent();
    const form = component.form();
    expect(form.controls.check.controls.errorResolutionDetails.value)
      .toBe('All issues resolved');
    expect(form.controls.check.controls.allReturnedMessagesManaged.value)
      .toBe(true);
  });

  it('should emit checklist validity through checksCompleted', async () => {
    await createComponent();
    const emitted: boolean[] = [];
    const sub = component.checksCompleted.subscribe(value => emitted.push(value));
    // The form is a lazy computed signal; constructing it emits the initial validity.
    const form = component.form();
    expect(emitted).toEqual([true]);
    form.controls.check.controls.errorResolutionDetails.setValue('');
    expect(emitted).toEqual([true, false]);
    sub.unsubscribe();
  });

  describe('save', () => {
    it('should not call the success callback (currently not implemented)', async () => {
      await createComponent();
      const onSucceed = jest.fn();
      component.save(CASE_ID, TASK_ID, onSucceed);
      expect(onSucceed).not.toHaveBeenCalled();
    });
  });

  describe('complete', () => {
    it('should complete the task and invoke onSucceed when confirmed', async () => {
      await createComponent();
      confirmationModalMock.openConfirmation.mockReturnValue({
        afterClose: of(true)
      });
      const onSucceed = jest.fn();

      component.complete(CASE_ID, TASK_ID, onSucceed);

      expect(confirmationModalMock.openConfirmation).toHaveBeenCalledTimes(1);
      expect(casesServiceMock.completeTask).toHaveBeenCalledWith(
        CASE_ID, TASK_ID, TaskType.CCI_ERROR_MANAGEMENT_SIGNOFF
      );
      expect(onSucceed).toHaveBeenCalledTimes(1);
    });

    it.each([false, null, undefined])(
      'should not complete when the confirmation result is %s',
      async confirmation => {
        await createComponent();
        confirmationModalMock.openConfirmation.mockReturnValue({
          afterClose: of(confirmation)
        });
        const onSucceed = jest.fn();

        component.complete(CASE_ID, TASK_ID, onSucceed);

        expect(casesServiceMock.completeTask).not.toHaveBeenCalled();
        expect(onSucceed).not.toHaveBeenCalled();
      }
    );

    it('should log an error and not invoke onSucceed if completion fails', async () => {
      await createComponent();
      const error = new Error('Complete failed');
      const consoleSpy = jest.spyOn(console, 'error').mockImplementation();
      confirmationModalMock.openConfirmation.mockReturnValue({ afterClose: of(true) });
      casesServiceMock.completeTask.mockReturnValue(throwError(() => error));
      const onSucceed = jest.fn();

      component.complete(CASE_ID, TASK_ID, onSucceed);

      expect(consoleSpy).toHaveBeenCalledWith(
        'Error completing error management Sign-off', error
      );
      expect(onSucceed).not.toHaveBeenCalled();
    });
  });

  describe('reject', () => {
    it('should send the rejection request and invoke onSucceed', async () => {
      await createComponent();
      rejectionModalMock.openRejectionModal.mockReturnValue({
        afterClose: of('Missing documents')
      });
      const onSucceed = jest.fn();

      component.reject(CASE_ID, TASK_ID, onSucceed);

      expect(rejectionModalMock.openRejectionModal).toHaveBeenCalledTimes(1);
      expect(casesServiceMock.rejectTask).toHaveBeenCalledWith(
        CASE_ID,
        TASK_ID,
        TaskType.CCI_ERROR_MANAGEMENT_SIGNOFF,
        'Missing documents',
        { rejectionReason: 'Missing documents' }
      );
      expect(onSucceed).toHaveBeenCalledTimes(1);
    });

    it.each([null, '', '   ', undefined])(
      'should not reject when the modal returns %s',
      async rejectionReason => {
        await createComponent();
        rejectionModalMock.openRejectionModal.mockReturnValue({
          afterClose: of(rejectionReason)
        });
        const onSucceed = jest.fn();

        component.reject(CASE_ID, TASK_ID, onSucceed);

        expect(casesServiceMock.rejectTask).not.toHaveBeenCalled();
        expect(onSucceed).not.toHaveBeenCalled();
      }
    );

    it('should log an error and not invoke onSucceed if rejection fails', async () => {
      await createComponent();
      const error = new Error('Reject failed');
      const consoleSpy = jest.spyOn(console, 'error').mockImplementation();
      rejectionModalMock.openRejectionModal.mockReturnValue({
        afterClose: of('Incorrect data')
      });
      casesServiceMock.rejectTask.mockReturnValue(throwError(() => error));
      const onSucceed = jest.fn();

      component.reject(CASE_ID, TASK_ID, onSucceed);

      expect(consoleSpy).toHaveBeenCalledWith(
        'Error rejecting error management Sign-off', error
      );
      expect(onSucceed).not.toHaveBeenCalled();
    });
  });
});
