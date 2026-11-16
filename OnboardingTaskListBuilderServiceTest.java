import { Location } from '@angular/common';
import { signal, type Signal } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';
import { TaskStatus, TaskType, UserService } from '@lia/commons';
import { NzMessageService } from 'ng-zorro-antd/message';
import { of, throwError } from 'rxjs';

import { ClientProfilingComponent } from './client-profiling.component';

// IMPORTANT: use the exact paths from client-profiling.component.ts.
// The screenshot does not show its imports. These paths are indicative.
import { CASE_SUMMARY_SERVICE } from '../../core/providers/services/case-summary.service.service';
import { ApplicationUsersService } from '../../core/providers/services/application-users.service';
import { TasksService } from '../../core/providers/services/tasks.service';
// TODO: replace this line with the actual HOLDING_REASONS_TOKEN import used by the component.
import { HOLDING_REASONS_TOKEN } from './holding-reasons.token';

type TaskData = {
  userTaskIdentifier: number;
  userTaskBpmnIdentifier: string;
  candidateGroups: string[];
  assigneeDetails: { username: string } | null;
  status: string;
  pendingReason?: string | null;
};

type StateUnderTest = {
  readonly actualTask: Signal<TaskData>;
  readonly readOnly: Signal<boolean>;
  readonly isUpdatable: Signal<boolean>;
  readonly isRejectable: Signal<boolean>;
  readonly hiddenBtnSave: Signal<boolean>;
  readonly displayBtnReject: Signal<boolean>;
  readonly hasRejectedDocument: Signal<boolean>;
  readonly isUserPartOfTeam: Signal<boolean | undefined>;
  readonly canComplete: Signal<boolean>;
  readonly allChecksCompleted: Signal<boolean>;
  readonly holdingReasonLabel: Signal<string>;
  readonly updateTask: Signal<boolean>;
  readonly onChecksCompleted: (completed: boolean) => void;
  readonly summary: { value: () => unknown };
};

// Access the protected signals only in tests; do not change their visibility in production.
const state = (component: ClientProfilingComponent): StateUnderTest =>
  component as unknown as StateUnderTest;

const BASE_CASE = { caseBusinessIdentifier: 'CASE-123' };
const BASE_TASK: TaskData = {
  userTaskIdentifier: 555,
  userTaskBpmnIdentifier: 'cci-manual-change-signoff',
  candidateGroups: ['TEAM_A'],
  assigneeDetails: { username: 'john.doe' },
  status: 'OPEN',
  pendingReason: null
};

const taskWith = (patch: Partial<TaskData> = {}): TaskData => ({
  ...BASE_TASK,
  ...patch
});

describe('ClientProfilingComponent', () => {
  let fixture: ComponentFixture<ClientProfilingComponent>;
  let component: ClientProfilingComponent;

  let currentUserSignal: ReturnType<typeof signal<{ preferred_username: string } | null>>;
  let caseSummaryMock: { getSummary: jest.Mock };
  let applicationUsersMock: { isUserPartOfTeam: jest.Mock };
  let tasksServiceMock: {
    findById: jest.Mock;
    updatePendingReason: jest.Mock;
    updateAssignee: jest.Mock;
  };
  let locationMock: { back: jest.Mock };
  let messageMock: { error: jest.Mock };

  async function render(options: {
    task?: TaskData;
    documents?: Array<{ status?: string | null }>;
    caseData?: { caseBusinessIdentifier: string };
  } = {}): Promise<void> {
    fixture = TestBed.createComponent(ClientProfilingComponent);
    component = fixture.componentInstance;

    // Populate all required signal inputs BEFORE detectChanges() / running effects.
    fixture.componentRef.setInput('currentTask', options.task ?? taskWith());
    fixture.componentRef.setInput('currentCase', options.caseData ?? BASE_CASE);
    fixture.componentRef.setInput('caseDocuments', options.documents ?? []);
    fixture.componentRef.setInput('positions', []);
    fixture.componentRef.setInput('policy', null);
    fixture.componentRef.setInput('clientProfilingReferences', {});

    // Required document callbacks are unused by the class-only tests,
    // but supplied to prevent input-required errors during change detection.
    fixture.componentRef.setInput('getDocumentConfigType', () => of([]));
    fixture.componentRef.setInput('getDocumentMetadata', () => of({}));
    fixture.componentRef.setInput('validateDocument', () => of(undefined));
    fixture.componentRef.setInput('rejectDocument', () => of(undefined));
    fixture.componentRef.setInput('downloadDocument', () => of(new Blob()));
    fixture.componentRef.setInput('removeDocument', () => of(undefined));
    fixture.componentRef.setInput('uploadDocument', () => of({}));
    fixture.componentRef.setInput('replacementDocument', () => of({}));
    fixture.componentRef.setInput('refreshDocuments', () => undefined);

    fixture.detectChanges();
    await fixture.whenStable();
  }

  beforeEach(async () => {
    currentUserSignal = signal({ preferred_username: 'john.doe' });
    caseSummaryMock = {
      getSummary: jest.fn().mockReturnValue(of({ summaryText: 'Case summary' }))
    };
    applicationUsersMock = {
      isUserPartOfTeam: jest.fn().mockReturnValue(of(true))
    };
    tasksServiceMock = {
      findById: jest.fn().mockReturnValue(of(taskWith())),
      updatePendingReason: jest.fn().mockReturnValue(of(undefined)),
      updateAssignee: jest.fn().mockReturnValue(of(undefined))
    };
    locationMock = { back: jest.fn() };
    messageMock = { error: jest.fn() };

    const providers = [
      { provide: CASE_SUMMARY_SERVICE, useValue: caseSummaryMock },
      { provide: UserService, useValue: { user: currentUserSignal } },
      { provide: ApplicationUsersService, useValue: applicationUsersMock },
      { provide: TasksService, useValue: tasksServiceMock },
      { provide: Location, useValue: locationMock },
      { provide: Router, useValue: {} },
      { provide: NzMessageService, useValue: messageMock },
      { provide: HOLDING_REASONS_TOKEN, useValue: [] }
    ];

    await TestBed.configureTestingModule({
      imports: [ClientProfilingComponent],
      providers
    })
      .overrideComponent(ClientProfilingComponent, {
        set: {
          template: '',
          imports: [],
          // Also override component-scoped providers, if present.
          providers
        }
      })
      .compileComponents();
  });

  afterEach(() => {
    jest.restoreAllMocks();
  });

  describe('initialization and reactive state', () => {
    it('should create the component', async () => {
      await render();
      expect(component).toBeTruthy();
    });

    it('should request the case summary using the current case', async () => {
      await render();
      expect(caseSummaryMock.getSummary).toHaveBeenCalledWith('CASE-123', BASE_CASE);
    });

    it('should check whether the current user belongs to the task team', async () => {
      await render();
      expect(applicationUsersMock.isUserPartOfTeam)
        .toHaveBeenCalledWith('john.doe', 'TEAM_A');
      expect(state(component).isUserPartOfTeam()).toBe(true);
    });

    it('should initialize actualTask from currentTask', async () => {
      await render();
      expect(state(component).actualTask().userTaskIdentifier).toBe(555);
    });

    it('should update actualTask when the input task changes', async () => {
      await render();
      fixture.componentRef.setInput('currentTask', taskWith({ userTaskIdentifier: 888 }));
      fixture.detectChanges();
      await fixture.whenStable();
      expect(state(component).actualTask().userTaskIdentifier).toBe(888);
    });

    it('should start with completion flags disabled', async () => {
      await render();
      expect(state(component).canComplete()).toBe(false);
      expect(state(component).allChecksCompleted()).toBe(false);
    });

    it('should record checklist completion through onChecksCompleted', async () => {
      await render();
      state(component).onChecksCompleted(true);
      expect(state(component).allChecksCompleted()).toBe(true);
      state(component).onChecksCompleted(false);
      expect(state(component).allChecksCompleted()).toBe(false);
    });

    it('should update canComplete through setCompletableState', async () => {
      await render();
      component.setCompletableState(true);
      expect(state(component).canComplete()).toBe(true);
      component.setCompletableState(false);
      expect(state(component).canComplete()).toBe(false);
    });
  });

  describe('task and document rules', () => {
    it('should be editable when assigned to the current user and not completed', async () => {
      await render();
      expect(state(component).readOnly()).toBe(false);
    });

    it('should be read-only when assigned to somebody else', async () => {
      await render({ task: taskWith({ assigneeDetails: { username: 'someone.else' } }) });
      expect(state(component).readOnly()).toBe(true);
    });

    it.each([TaskStatus.COMPLETED, TaskStatus.REJECTED])(
      'should be read-only for a task with status %s',
      async status => {
        await render({ task: taskWith({ status: String(status) }) });
        expect(state(component).readOnly()).toBe(true);
      }
    );

    it('should disable updating for change-client-information task', async () => {
      await render({ task: taskWith({ userTaskBpmnIdentifier: 'change-client-information' }) });
      expect(state(component).isUpdatable()).toBe(false);
    });

    it('should disable rejection for change-of-client-information task', async () => {
      await render({ task: taskWith({ userTaskBpmnIdentifier: 'change-of-client-information' }) });
      expect(state(component).isRejectable()).toBe(false);
    });

    it('should detect a rejected document case-insensitively', async () => {
      await render({ documents: [{ status: 'rejected' }] });
      expect(state(component).hasRejectedDocument()).toBe(true);
    });

    it('should flag a document with a missing status', async () => {
      await render({ documents: [{ status: '' }] });
      expect(state(component).hasRejectedDocument()).toBe(true);
    });

    it('should not flag documents with valid statuses', async () => {
      await render({ documents: [{ status: 'SIGNED' }, { status: 'VALIDATED' }] });
      expect(state(component).hasRejectedDocument()).toBe(false);
    });

    it.each([
      TaskType.CHANGE_CLIENT_INFORMATION_SIGNOFF,
      TaskType.CCI_MANUAL_CHANGE_SIGNOFF,
      TaskType.CCI_ERROR_MANAGEMENT_SIGNOFF
    ])('should hide Save and display Reject for sign-off task %s', async taskType => {
      await render({ task: taskWith({ userTaskBpmnIdentifier: String(taskType) }) });
      expect(state(component).hiddenBtnSave()).toBe(true);
      expect(state(component).displayBtnReject()).toBe(true);
    });

    it('should show Save and hide Reject for a non-sign-off task', async () => {
      await render({ task: taskWith({ userTaskBpmnIdentifier: 'onboarding-task' }) });
      expect(state(component).hiddenBtnSave()).toBe(false);
      expect(state(component).displayBtnReject()).toBe(false);
    });

    it('should use the existing pending reason for the label', async () => {
      await render({ task: taskWith({ pendingReason: 'WAITING_FOR_CLIENT' }) });
      expect(state(component).holdingReasonLabel()).toBe('WAITING_FOR_CLIENT');
    });
  });

  describe('task actions', () => {
    let childTask: {
      save: jest.Mock;
      complete: jest.Mock;
      reject: jest.Mock;
    };

    beforeEach(async () => {
      await render();
      childTask = {
        save: jest.fn(),
        complete: jest.fn(),
        reject: jest.fn()
      };
      // The real template is replaced by '', so @ViewChild has no instance.
      Object.assign(component, { taskComponent: childTask });
    });

    it('should delegate Save to the child task with the same identifiers', () => {
      component.save('CASE-123', 555);
      expect(childTask.save).toHaveBeenCalledWith('CASE-123', 555, expect.any(Function));
    });

    it('should refresh ChangeClientInformation only after successful Save', () => {
      const refresh = jest.fn();
      // Override the signal query in this isolated class-only test.
      Object.defineProperty(component, 'changeClientInformationComponent', {
        configurable: true,
        value: () => ({ refresh })
      });

      component.save('CASE-123', 555);
      expect(refresh).not.toHaveBeenCalled();
      const onSucceed = childTask.save.mock.calls[0][2] as () => void;
      onSucceed();
      expect(refresh).toHaveBeenCalledTimes(1);
    });

    it('should delegate Complete and go back only when its callback succeeds', () => {
      component.complete('CASE-123', 555);
      expect(childTask.complete).toHaveBeenCalledWith('CASE-123', 555, expect.any(Function));
      expect(locationMock.back).not.toHaveBeenCalled();
      const onSucceed = childTask.complete.mock.calls[0][2] as () => void;
      onSucceed();
      expect(locationMock.back).toHaveBeenCalledTimes(1);
    });

    it('should delegate Reject and go back only when its callback succeeds', () => {
      component.reject('CASE-123', 555);
      expect(childTask.reject).toHaveBeenCalledWith('CASE-123', 555, expect.any(Function));
      expect(locationMock.back).not.toHaveBeenCalled();
      const onSucceed = childTask.reject.mock.calls[0][2] as () => void;
      onSucceed();
      expect(locationMock.back).toHaveBeenCalledTimes(1);
    });
  });

  describe('holding reason', () => {
    it('should update the task pending reason and label after success', async () => {
      await render();
      component.setHoldingReason(
        'CASE-123',
        555,
        { value: 'WAITING_FOR_CLIENT' } as Parameters<ClientProfilingComponent['setHoldingReason']>[2]
      );
      expect(tasksServiceMock.updatePendingReason)
        .toHaveBeenCalledWith('CASE-123', 555, 'WAITING_FOR_CLIENT');
      expect(state(component).holdingReasonLabel()).toBe('WAITING_FOR_CLIENT');
    });


  });

  describe('case assignment', () => {
    it('should not assign the task if the user is missing', async () => {
      currentUserSignal.set(null);
      await render();
      component.setCaseOwner();
      expect(tasksServiceMock.updateAssignee).not.toHaveBeenCalled();
    });

    it('should update the assignee with the user and candidate team', async () => {
      await render();
      component.setCaseOwner();
      expect(tasksServiceMock.updateAssignee).toHaveBeenCalledWith(
        555,
        { assignee: 'john.doe', team: 'TEAM_A' }
      );
      expect(state(component).updateTask()).toBe(true);
    });

    it('should reload the task after assignment succeeds', async () => {
      tasksServiceMock.findById.mockReturnValue(
        of(taskWith({ assigneeDetails: { username: 'john.doe' } }))
      );
      await render();
      component.setCaseOwner();
      fixture.detectChanges();
      await fixture.whenStable();
      expect(tasksServiceMock.findById).toHaveBeenCalledWith('555');
      expect(state(component).actualTask().userTaskIdentifier).toBe(555);
    });

    it('should display an error and not request refresh if assignment fails', async () => {
      tasksServiceMock.updateAssignee.mockReturnValue(
        throwError(() => new Error('Assignment failed'))
      );
      await render();
      component.setCaseOwner();
      expect(messageMock.error).toHaveBeenCalledWith('Error while assigning the task');
      expect(state(component).updateTask()).toBe(false);
      expect(tasksServiceMock.findById).not.toHaveBeenCalled();
    });
  });
});
