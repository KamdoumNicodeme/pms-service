
import { TestBed } from '@angular/core/testing';
import {
  provideHttpClient
} from '@angular/common/http';
import {
  HttpTestingController,
  provideHttpClientTesting
} from '@angular/common/http/testing';

import { DataReferenceService } from './data-reference.service';

describe('DataReferenceService', () => {
  let service: DataReferenceService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        DataReferenceService,
        provideHttpClient(),
        provideHttpClientTesting()
      ]
    });

    service = TestBed.inject(DataReferenceService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
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

      const nextSpy = jest.fn();

      service.searchReferences('YESNO', 'YES')
        .subscribe(nextSpy);

      const req = httpMock.expectOne(request =>
        request.method === 'GET' &&
        request.url.endsWith('/references/YESNO/search')
      );

      expect(req.request.params.get('search')).toBe('YES');
      expect(req.request.params.get('page')).toBe('0');
      expect(req.request.params.get('size')).toBe('20');

      req.flush(response);

      expect(nextSpy).toHaveBeenCalledWith(response);
    });

    it('should use custom pagination parameters', () => {
      const nextSpy = jest.fn();

      service.searchReferences('COUNTRY', 'LU', 2, 10)
        .subscribe(nextSpy);

      const req = httpMock.expectOne(request =>
        request.method === 'GET' &&
        request.url.endsWith('/references/COUNTRY/search')
      );

      expect(req.request.params.get('search')).toBe('LU');
      expect(req.request.params.get('page')).toBe('2');
      expect(req.request.params.get('size')).toBe('10');

      req.flush({ content: [] });

      expect(nextSpy).toHaveBeenCalledTimes(1);
    });

    it('should encode the domain in the URL', () => {
      service.searchReferences('TAX COUNTRY', '')
        .subscribe();

      const req = httpMock.expectOne(request =>
        request.url.endsWith(
          '/references/TAX%20COUNTRY/search'
        )
      );

      expect(req.request.method).toBe('GET');

      req.flush({ content: [] });
    });
  });

  describe('getReferenceByValue', () => {
    it('should return a reference by domain and value', () => {
      const reference = {
        domain: 'YESNO',
        value: 'YES'
      };

      const nextSpy = jest.fn();

      service.getReferenceByValue('YESNO', 'YES')
        .subscribe(nextSpy);

      const req = httpMock.expectOne(request =>
        request.method === 'GET' &&
        request.url.endsWith('/references/by-value')
      );

      expect(req.request.params.get('domain')).toBe('YESNO');
      expect(req.request.params.get('value')).toBe('YES');

      req.flush(reference);

      expect(nextSpy).toHaveBeenCalledWith(reference);
    });

    it('should propagate an HTTP error', () => {
      const errorSpy = jest.fn();

      service.getReferenceByValue('YESNO', 'UNKNOWN')
        .subscribe({
          error: errorSpy
        });

      const req = httpMock.expectOne(request =>
        request.url.endsWith('/references/by-value')
      );

      req.flush('Not found', {
        status: 404,
        statusText: 'Not Found'
      });

      expect(errorSpy).toHaveBeenCalledTimes(1);
      expect(errorSpy.mock.calls[0][0].status).toBe(404);
    });
  });
});
