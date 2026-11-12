
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of, Subject } from 'rxjs';

import { CciManualChangeSignOffComponent } from './cci-manual-change-sign-off.component';

describe('CciManualChangeSignOffComponent', () => {
  let component: CciManualChangeSignOffComponent;
  let fixture: ComponentFixture<CciManualChangeSignOffComponent>;

  let caseService: {
    getCaseDataAfterClientProfiling: jest.Mock;
    getCaseBusinessDataUptoDate: jest.Mock;
    completeTask: jest.Mock;
    rejectTask: jest.Mock;
  };

  let comparisonService: {
    setReferences: jest.Mock;
  };

  let caseSummaryService: {
    getSummary: jest.Mock;
  };

  let rejectionModalService: {
    openRejectionModal: jest.Mock;
  };

  let completionModalService: {
    openConfirmation: jest.Mock;
  };

  const caseId = 'CLIP_TEST_001';
  const taskId = 123;
  const rejectionReason = 'Incorrect client information';

  beforeEach(async () => {
    caseService = {
      getCaseDataAfterClientProfiling: jest.fn().mockReturnValue(
        of({
          manualChange: null,
          changeClientInformation: null
        })
      ),
      getCaseBusinessDataUptoDate: jest.fn().mockReturnValue(
        of({ policy: { digitalConsent: null } })
      ),
      completeTask: jest.fn().mockReturnValue(of(undefined)),
      rejectTask: jest.fn().mockReturnValue(of(undefined))
    };

    comparisonService = {
      setReferences: jest.fn()
    };

    caseSummaryService = {
      getSummary: jest.fn().mockReturnValue(of({}))
    };

    rejectionModalService = {
      openRejectionModal: jest.fn()
    };

    completionModalService = {
      openConfirmation: jest.fn()
    };

    await TestBed.configureTestingModule({
      imports: [CciManualChangeSignOffComponent],
      providers: [
        { provide: CasesService, useValue: caseService },
        { provide: CASE_SUMMARY_SERVICE, useValue: caseSummaryService },
        { provide: COMPARISON_DATA_SERVICE, useValue: comparisonService },
        { provide: RejectionModalService, useValue: rejectionModalService },
        {
          provide: CompletionConfirmationModalService,
          useValue: completionModalService
        },
        {
          provide: ApplicationUsersService,
          useValue: {
            findAll: jest.fn().mockReturnValue(of([]))
          }
        }
      ]
    })
      .overrideComponent(CciManualChangeSignOffComponent, {
        set: {
          template: '',
          imports: []
        }
      })
      .compileComponents();

    fixture = TestBed.createComponent(CciManualChangeSignOffComponent);
    component = fixture.componentInstance;

    fixture.componentRef.setInput('currentCase', {
      caseBusinessIdentifier: caseId
    });

    fixture.componentRef.setInput('currentTask', {
      taskId
    });

    fixture.componentRef.setInput('policy', null);
    fixture.componentRef.setInput('readOnly', true);

    fixture.detectChanges();
    await fixture.whenStable();
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('should load client profiling data', () => {
    expect(
      caseService.getCaseDataAfterClientProfiling
    ).toHaveBeenCalledWith(caseId);

    expect(
      caseService.getCaseBusinessDataUptoDate
    ).toHaveBeenCalledWith(caseId);
  });

  it('should initialize comparison references', () => {
    const references = {
      COUNTRY: [
        {
          key: 'IT',
          value: 'Italy'
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

  describe('Complete sign-off', () => {
    it('should complete the task when confirmation is accepted', () => {
      const onSucceed = jest.fn();

      completionModalService.openConfirmation.mockReturnValue({
        afterClose: of(true)
      });

      component.complete(caseId, taskId, onSucceed);

      expect(
        completionModalService.openConfirmation
      ).toHaveBeenCalledTimes(1);

      expect(caseService.completeTask).toHaveBeenCalledWith(
        caseId,
        taskId,
        TaskType.CCI_MANUAL_CHANGE_SIGNOFF
      );

      expect(onSucceed).toHaveBeenCalledTimes(1);
    });

    it('should not complete the task when confirmation is cancelled', () => {
      const onSucceed = jest.fn();

      completionModalService.openConfirmation.mockReturnValue({
        afterClose: of(false)
      });

      component.complete(caseId, taskId, onSucceed);

      expect(caseService.completeTask).not.toHaveBeenCalled();
      expect(onSucceed).not.toHaveBeenCalled();
    });

    it('should wait for confirmation before completing the task', () => {
      const onSucceed = jest.fn();
      const confirmation$ = new Subject<boolean>();

      completionModalService.openConfirmation.mockReturnValue({
        afterClose: confirmation$.asObservable()
      });

      component.complete(caseId, taskId, onSucceed);

      expect(caseService.completeTask).not.toHaveBeenCalled();

      confirmation$.next(true);

      expect(caseService.completeTask).toHaveBeenCalledTimes(1);
      expect(onSucceed).toHaveBeenCalledTimes(1);

      confirmation$.complete();
    });
  });

  describe('Reject sign-off', () => {
    it('should reject the task with the provided reason', () => {
      const onSucceed = jest.fn();

      rejectionModalService.openRejectionModal.mockReturnValue({
        afterClose: of(rejectionReason)
      });

      component.reject(caseId, taskId, onSucceed);

      expect(
        rejectionModalService.openRejectionModal
      ).toHaveBeenCalledTimes(1);

      expect(caseService.rejectTask).toHaveBeenCalledWith(
        caseId,
        taskId,
        TaskType.CCI_MANUAL_CHANGE_SIGNOFF,
        rejectionReason,
        {
          rejectionReason
        }
      );

      expect(onSucceed).toHaveBeenCalledTimes(1);
    });

    it('should not reject the task when the modal is cancelled', () => {
      const onSucceed = jest.fn();

      rejectionModalService.openRejectionModal.mockReturnValue({
        afterClose: of(null)
      });

      component.reject(caseId, taskId, onSucceed);

      expect(caseService.rejectTask).not.toHaveBeenCalled();
      expect(onSucceed).not.toHaveBeenCalled();
    });

    it('should wait for a rejection reason before rejecting', () => {
      const onSucceed = jest.fn();
      const rejection$ = new Subject<string | null>();

      rejectionModalService.openRejectionModal.mockReturnValue({
        afterClose: rejection$.asObservable()
      });

      component.reject(caseId, taskId, onSucceed);

      expect(caseService.rejectTask).not.toHaveBeenCalled();

      rejection$.next(rejectionReason);

      expect(caseService.rejectTask).toHaveBeenCalledTimes(1);
      expect(onSucceed).toHaveBeenCalledTimes(1);

      rejection$.complete();
    });
  });
});
