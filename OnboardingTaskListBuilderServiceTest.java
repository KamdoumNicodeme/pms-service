
import { TestBed } from '@angular/core/testing';
import { of, throwError } from 'rxjs';

import { ReferenceService } from './reference.service';
import { DataReferenceService } from './data-reference.service';

describe('ReferenceService', () => {
  let service: ReferenceService;

  let dataReferenceServiceMock: {
    searchReferences: jest.Mock;
    getReferenceByValue: jest.Mock;
  };

  beforeEach(() => {
    dataReferenceServiceMock = {
      searchReferences: jest.fn(),
      getReferenceByValue: jest.fn()
    };

    TestBed.configureTestingModule({
      providers: [
        ReferenceService,
        {
          provide: DataReferenceService,
          useValue: dataReferenceServiceMock
        }
      ]
    });

    service = TestBed.inject(ReferenceService);
  });

  afterEach(() => {
    jest.restoreAllMocks();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  describe('searchReferences', () => {
    it('should search references with default pagination', () => {
      const response = {
        content: [{ domain: 'YESNO', value: 'YES' }],
        totalElements: 1
      };

      dataReferenceServiceMock.searchReferences
        .mockReturnValue(of(response));

      const nextSpy = jest.fn();

      service.searchReferences('YESNO', 'YES')
        .subscribe(nextSpy);

      expect(
        dataReferenceServiceMock.searchReferences
      ).toHaveBeenCalledWith(
        'YESNO',
        'YES',
        0,
        20
      );

      expect(nextSpy).toHaveBeenCalledWith(response);
    });

    it('should pass custom pagination parameters', () => {
      const response = { content: [] };

      dataReferenceServiceMock.searchReferences
        .mockReturnValue(of(response));

      service.searchReferences('COUNTRY', 'LU', 2, 10)
        .subscribe();

      expect(
        dataReferenceServiceMock.searchReferences
      ).toHaveBeenCalledWith(
        'COUNTRY',
        'LU',
        2,
        10
      );
    });

    it('should return an empty pageable when searching fails', () => {
      const error = new Error('Network error');

      const consoleSpy = jest.spyOn(console, 'error')
        .mockImplementation(() => {});

      dataReferenceServiceMock.searchReferences
        .mockReturnValue(throwError(() => error));

      const nextSpy = jest.fn();
      const completeSpy = jest.fn();
      const errorSpy = jest.fn();

      service.searchReferences('YESNO', 'YES')
        .subscribe({
          next: nextSpy,
          error: errorSpy,
          complete: completeSpy
        });

      expect(consoleSpy).toHaveBeenCalledWith(
        'Failed to search references',
        error
      );

      expect(nextSpy).toHaveBeenCalledTimes(1);
      expect(nextSpy.mock.calls[0][0]).toBeDefined();
      expect(errorSpy).not.toHaveBeenCalled();
      expect(completeSpy).toHaveBeenCalledTimes(1);
    });
  });

  describe('findReferencesByDomains', () => {
    it('should return a reference when found', () => {
      const reference = {
        domain: 'YESNO',
        value: 'YES'
      };

      dataReferenceServiceMock.getReferenceByValue
        .mockReturnValue(of(reference));

      const nextSpy = jest.fn();

      service.findReferencesByDomains('YESNO', 'YES')
        .subscribe(nextSpy);

      expect(
        dataReferenceServiceMock.getReferenceByValue
      ).toHaveBeenCalledWith(
        'YESNO',
        'YES'
      );

      expect(nextSpy).toHaveBeenCalledWith(reference);
    });

    it('should complete without emitting when an error occurs', () => {
      const error = new Error('Reference not found');

      dataReferenceServiceMock.getReferenceByValue
        .mockReturnValue(throwError(() => error));

      const nextSpy = jest.fn();
      const completeSpy = jest.fn();
      const errorSpy = jest.fn();

      service.findReferencesByDomains('YESNO', 'UNKNOWN')
        .subscribe({
          next: nextSpy,
          error: errorSpy,
          complete: completeSpy
        });

      expect(
        dataReferenceServiceMock.getReferenceByValue
      ).toHaveBeenCalledWith(
        'YESNO',
        'UNKNOWN'
      );

      expect(nextSpy).not.toHaveBeenCalled();
      expect(errorSpy).not.toHaveBeenCalled();
      expect(completeSpy).toHaveBeenCalledTimes(1);
    });
  });
});
