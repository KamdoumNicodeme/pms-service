
import { TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import {
  HttpTestingController,
  provideHttpClientTesting,
} from '@angular/common/http/testing';

import { DataCasesService } from './data-cases.service';
import { environment } from 'PATH_TO_ENVIRONMENT';

describe('DataCasesService', () => {
  let service: DataCasesService;
  let httpMock: HttpTestingController;

  const caseId = 'CLIP_TEST_001';
  const taskId = 123;

  const clientProfilingUrl =
    environment.apiUrls.get('clientProfiling');

  const caseManagementUrl =
    environment.apiUrls.get('caseManagement');

  const taskType =
    'change-of-client-information-sign-off' as
      Parameters<DataCasesService['updateTask']>[2];

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        DataCasesService,
        provideHttpClient(),
        provideHttpClientTesting(),
      ],
    });

    service = TestBed.inject(DataCasesService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should create the service', () => {
    expect(service).toBeTruthy();
  });

  describe('getCaseData', () => {
    it('should retrieve client profiling case data', () => {
      const response = {
        caseBusinessIdentifier: caseId,
      };

      let result: unknown;

      service.getCaseData(caseId).subscribe(value => {
        result = value;
      });

      const req = httpMock.expectOne(
        `${clientProfilingUrl}/cases/client-profiling/${caseId}/data`
      );

      expect(req.request.method).toBe('GET');

      req.flush(response);

      expect(result).toEqual(response);
    });
  });

  describe('getCaseBusinessData', () => {
    it('should retrieve the latest business data', () => {
      const response = {
        policy: {
          policyNumber: '2412-157945',
        },
      };

      let result: unknown;

      service.getCaseBusinessData(caseId).subscribe(value => {
        result = value;
      });

      const req = httpMock.expectOne(
        `${clientProfilingUrl}/cases/client-profiling/business-data/${caseId}/last`
      );

      expect(req.request.method).toBe('GET');

      req.flush(response);

      expect(result).toEqual(response);
    });
  });

  describe('findTaskById', () => {
    it('should retrieve task details by identifier', () => {
      const response = {
        taskId,
      };

      let result: unknown;

      service.findTaskById(taskId).subscribe(value => {
        result = value;
      });

      const req = httpMock.expectOne(
        `${caseManagementUrl}/user-tasks/${taskId}`
      );

      expect(req.request.method).toBe('GET');

      req.flush(response);

      expect(result).toEqual(response);
    });
  });

  describe('updateTask', () => {
    it('should update a task with the correct payload', () => {
      const request = {
        reviewed: true,
      };

      const additionalParams = new Map([
        ['source', 'unit-test'],
      ]);

      service.updateTask(
        caseId,
        taskId,
        taskType,
        request,
        additionalParams
      ).subscribe();

      const req = httpMock.expectOne(
        request =>
          request.url ===
          `${clientProfilingUrl}/cases/client-profiling/${caseId}/tasks/${taskType}`
      );

      expect(req.request.method).toBe('PUT');

      expect(req.request.body).toEqual({
        reviewed: true,
        taskId,
      });

      expect(req.request.params.get('source'))
        .toBe('unit-test');

      req.flush({});
    });
  });

  describe('updateTaskAction', () => {
    it('should send the task action and additional parameters', () => {
      const action =
        'APPROVE' as
          Parameters<DataCasesService['updateTaskAction']>[1];

      const additionalParams = new Map([
        ['source', 'unit-test'],
      ]);

      service.updateTaskAction(
        taskId,
        action,
        additionalParams
      ).subscribe();

      const req = httpMock.expectOne(
        request =>
          request.url ===
          `${caseManagementUrl}/user-tasks/${taskId}/completed`
      );

      expect(req.request.method).toBe('PUT');
      expect(req.request.body).toEqual({});

      expect(req.request.params.get('action'))
        .toBe(action);

      expect(req.request.params.get('source'))
        .toBe('unit-test');

      req.flush({});
    });
  });

  describe('updateTimestamp', () => {
    it('should update the case timestamp', () => {
      service.updateTimestamp(caseId).subscribe();

      const req = httpMock.expectOne(
        `${caseManagementUrl}/cases/${caseId}/update-timestamp`
      );

      expect(req.request.method).toBe('PUT');
      expect(req.request.body).toEqual({});

      req.flush({});
    });
  });

  describe('updateRejectionReason', () => {
    it('should send the rejection reason for the selected task', () => {
      const rejectionReason = {
        rejectionReason: 'Incorrect client information',
      } as Parameters<DataCasesService['updateRejectionReason']>[2];

      service.updateRejectionReason(
        caseId,
        taskType,
        rejectionReason
      ).subscribe();

      const req = httpMock.expectOne(
        `${clientProfilingUrl}/cases/client-profiling/${caseId}/tasks/${taskType}/rejection-reason`
      );

      expect(req.request.method).toBe('PUT');

      expect(req.request.body).toEqual(rejectionReason);

      req.flush({});
    });
  });

  describe('HTTP errors', () => {
    it('should propagate an error when case data cannot be loaded', () => {
      let receivedError: unknown;

      service.getCaseData(caseId).subscribe({
        error: error => {
          receivedError = error;
        },
      });

      const req = httpMock.expectOne(
        `${clientProfilingUrl}/cases/client-profiling/${caseId}/data`
      );

      req.flush(
        { message: 'Not found' },
        {
          status: 404,
          statusText: 'Not Found',
        }
      );

      expect(receivedError).toMatchObject({
        status: 404,
      });
    });
  });
});
