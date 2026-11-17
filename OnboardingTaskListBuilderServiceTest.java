import { TestBed } from '@angular/core/testing';
import { NzMessageService } from 'ng-zorro-antd/message';
import { firstValueFrom, of, throwError } from 'rxjs';

import { CasesService } from './cases.service';
// Adapt these three imports to the actual structure of your project.
import { DataCasesService } from '../data-services/data-cases.service';
import { DataTasksService } from '../data-services/data-tasks.service';
import { TaskAction, TaskType } from '@lia/commons'; // Check the location of these enums.

describe('CasesService', () => {
  const CASE_ID = 'CLIP_TEST_001';
  const TASK_ID = 123;

  let service: CasesService;
  let casesData: {
    getCaseData: jest.Mock;
    getCaseBusinessData: jest.Mock;
    updateTask: jest.Mock;
    updateRejectionReason: jest.Mock;
    updateTaskAction: jest.Mock;
    findTaskById: jest.Mock;
    updateTimestamp: jest.Mock;
  };
  let tasksData: { updateTimestamp: jest.Mock };
  let messages: { success: jest.Mock; error: jest.Mock };

  beforeEach(() => {
    casesData = {
      getCaseData: jest.fn().mockReturnValue(of({ caseBusinessIdentifier: CASE_ID })),
      getCaseBusinessData: jest.fn().mockReturnValue(of({ policy: {} })),
      updateTask: jest.fn().mockReturnValue(of({ taskId: TASK_ID })),
      updateRejectionReason: jest.fn().mockReturnValue(of({})),
      updateTaskAction: jest.fn().mockReturnValue(of({ action: 'done' })),
      findTaskById: jest.fn().mockReturnValue(of({ userTaskIdentifier: TASK_ID })),
      updateTimestamp: jest.fn().mockReturnValue(of({})),
    };
    tasksData = { updateTimestamp: jest.fn().mockReturnValue(of({})) };
    messages = { success: jest.fn(), error: jest.fn() };

    TestBed.configureTestingModule({
      providers: [
        CasesService,
        { provide: DataCasesService, useValue: casesData },
        { provide: DataTasksService, useValue: tasksData },
        { provide: NzMessageService, useValue: messages },
      ],
    });
    service = TestBed.inject(CasesService);
  });

  it('delegates getCaseDataAfterClientProfiling to the data service', async () => {
    await expect(firstValueFrom(service.getCaseDataAfterClientProfiling(CASE_ID)))
      .resolves.toEqual({ caseBusinessIdentifier: CASE_ID });
    expect(casesData.getCaseData).toHaveBeenCalledWith(CASE_ID);
  });

  it('delegates getCaseBusinessDataUptoDate to the data service', async () => {
    await expect(firstValueFrom(service.getCaseBusinessDataUptoDate(CASE_ID)))
      .resolves.toEqual({ policy: {} });
    expect(casesData.getCaseBusinessData).toHaveBeenCalledWith(CASE_ID);
  });

  describe('saveTask', () => {
    it.each([
      TaskType.CHANGE_CLIENT_INFORMATION,
      TaskType.CCI_MANUAL_CHANGE,
      TaskType.CCI_ERROR_MANAGEMENT,
    ])('saves task of type %s, shows success and updates both timestamps', async taskType => {
      const request = { testValue: 'value' };
      const extra = new Map([['status', 'draft']]);

      await firstValueFrom(service.saveTask(CASE_ID, TASK_ID, taskType, request, extra));

      expect(casesData.updateTask).toHaveBeenCalledWith(
        CASE_ID, TASK_ID, taskType, request, extra,
      );
      expect(messages.success).toHaveBeenCalledWith(
        'Task successfully Saved', { nzDuration: 3000 },
      );
      expect(tasksData.updateTimestamp).toHaveBeenCalledWith(TASK_ID);
      expect(casesData.updateTimestamp).toHaveBeenCalledWith(CASE_ID);
    });

    it('throws immediately for an unsupported task type', () => {
      expect(() => service.saveTask(
        CASE_ID, TASK_ID, 'invalid-type' as TaskType, {},
      )).toThrow(`Unable to save the task ${TASK_ID}: Invalid task type invalid-type`);
      expect(casesData.updateTask).not.toHaveBeenCalled();
    });

    it('shows an error and propagates the save failure', async () => {
      casesData.updateTask.mockReturnValue(
        throwError(() => new Error('Backend unavailable')),
      );

      await expect(firstValueFrom(service.saveTask(
        CASE_ID, TASK_ID, TaskType.CHANGE_CLIENT_INFORMATION, {},
      ))).rejects.toThrow(`Unable to save the task ${TASK_ID}`);

      expect(messages.error).toHaveBeenCalledWith(
        'Task cannot be Saved. Please try again later',
        { nzDuration: 3000 },
      );
      expect(messages.success).not.toHaveBeenCalled();
      expect(tasksData.updateTimestamp).not.toHaveBeenCalled();
      expect(casesData.updateTimestamp).not.toHaveBeenCalled();
    });

    it('propagates a timestamp failure after the task has been saved', async () => {
      casesData.updateTimestamp.mockReturnValue(
        throwError(() => new Error('Timestamp unavailable')),
      );

      await expect(firstValueFrom(service.saveTask(
        CASE_ID, TASK_ID, TaskType.CHANGE_CLIENT_INFORMATION, {},
      ))).rejects.toThrow('Timestamp unavailable');

      // Current implementation emits success in tap() BEFORE timestamp updates.
      expect(messages.success).toHaveBeenCalledTimes(1);
    });
  });

  describe('completeTask', () => {
    it('saves an editable task before completing it when a request is provided', async () => {
      const request = { changed: true };
      const task = await firstValueFrom(service.completeTask(
        CASE_ID, TASK_ID, TaskType.CHANGE_CLIENT_INFORMATION, request,
      ));

      expect(casesData.updateTask).toHaveBeenCalledWith(
        CASE_ID, TASK_ID, TaskType.CHANGE_CLIENT_INFORMATION,
        request, new Map([['status', 'complete']]),
      );
      expect(casesData.updateTaskAction.mock.calls[0].slice(0, 2))
        .toEqual([TASK_ID, TaskAction.COMPLETE]);
      expect(casesData.findTaskById).toHaveBeenCalledWith(TASK_ID);
      expect(task).toEqual({ userTaskIdentifier: TASK_ID });
    });

    it('completes an editable task without saving when request is absent', async () => {
      await firstValueFrom(service.completeTask(
        CASE_ID, TASK_ID, TaskType.CHANGE_CLIENT_INFORMATION,
      ));

      expect(casesData.updateTask).not.toHaveBeenCalled();
      expect(casesData.updateTaskAction.mock.calls[0].slice(0, 2))
        .toEqual([TASK_ID, TaskAction.COMPLETE]);
      expect(casesData.findTaskById).toHaveBeenCalledWith(TASK_ID);
    });

    it('passes manualChangeRequired for a non-editable sign-off task', async () => {
      await firstValueFrom(service.completeTask(
        CASE_ID, TASK_ID, TaskType.CCI_MANUAL_CHANGE_SIGNOFF,
        { manualChangeRequired: true },
      ));

      expect(casesData.updateTask).not.toHaveBeenCalled();
      expect(casesData.updateTaskAction.mock.calls[0].slice(0, 2))
        .toEqual([TASK_ID, TaskAction.COMPLETE]);
      // The third argument is the validation-parameter Map, if the task type
      // is configured with one by getValidationParam().
      expect(casesData.findTaskById).toHaveBeenCalledWith(TASK_ID);
    });
  });

  describe('rejectTask', () => {
    const REASON = 'Invalid client data';
    const body = { taskId: TASK_ID, reason: REASON };

    it('saves an editable task, persists rejection reason and rejects the task', async () => {
      const request = { change: true };
      await firstValueFrom(service.rejectTask(
        CASE_ID, TASK_ID, TaskType.CHANGE_CLIENT_INFORMATION, REASON, request,
      ));

      expect(casesData.updateTask).toHaveBeenCalledWith(
        CASE_ID, TASK_ID, TaskType.CHANGE_CLIENT_INFORMATION, request, undefined,
      );
      expect(casesData.updateRejectionReason).toHaveBeenCalledWith(
        CASE_ID, TaskType.CHANGE_CLIENT_INFORMATION, body,
      );
      expect(casesData.updateTaskAction.mock.calls[0].slice(0, 2))
        .toEqual([TASK_ID, TaskAction.REJECT]);
    });

    it('does not save when rejecting an editable task without a request', async () => {
      await firstValueFrom(service.rejectTask(
        CASE_ID, TASK_ID, TaskType.CHANGE_CLIENT_INFORMATION, REASON, undefined,
      ));

      expect(casesData.updateTask).not.toHaveBeenCalled();
      expect(casesData.updateRejectionReason).toHaveBeenCalledWith(
        CASE_ID, TaskType.CHANGE_CLIENT_INFORMATION, body,
      );
    });

    it('persists rejection reason without saving a non-editable sign-off task', async () => {
      await firstValueFrom(service.rejectTask(
        CASE_ID, TASK_ID, TaskType.CCI_MANUAL_CHANGE_SIGNOFF, REASON, {},
      ));

      expect(casesData.updateTask).not.toHaveBeenCalled();
      expect(casesData.updateRejectionReason).toHaveBeenCalledWith(
        CASE_ID, TaskType.CCI_MANUAL_CHANGE_SIGNOFF, body,
      );
      expect(casesData.updateTaskAction.mock.calls[0].slice(0, 2))
        .toEqual([TASK_ID, TaskAction.REJECT]);
    });

    it('does not reject the task when persistence of the reason fails', async () => {
      casesData.updateRejectionReason.mockReturnValue(
        throwError(() => new Error('Rejection reason unavailable')),
      );

      await expect(firstValueFrom(service.rejectTask(
        CASE_ID, TASK_ID, TaskType.CCI_MANUAL_CHANGE_SIGNOFF, REASON, {},
      ))).rejects.toThrow('Rejection reason unavailable');
      expect(casesData.updateTaskAction).not.toHaveBeenCalled();
    });
  });
});
