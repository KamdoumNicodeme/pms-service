
import { TestBed } from '@angular/core/testing';
import { firstValueFrom, of, throwError } from 'rxjs';

import { CaseSummaryServiceService } from './case-summary-service.service';
import { DataCasesService } from './data-cases.service';

describe('CaseSummaryServiceService', () => {
  let service: CaseSummaryServiceService;

  const dataCasesMock = {
    getCaseData: jest.fn(),
  };

  const caseId = 'CLIP_TEST_001';

  type CurrentCase =
    Parameters<CaseSummaryServiceService['getSummary']>[1];

  const currentCase = {
    ownerDetails: {
      displayName: 'John Doe',
    },
  } as CurrentCase;

  beforeEach(() => {
    jest.clearAllMocks();

    TestBed.configureTestingModule({
      providers: [
        CaseSummaryServiceService,
        {
          provide: DataCasesService,
          useValue: dataCasesMock,
        },
      ],
    });

    service = TestBed.inject(CaseSummaryServiceService);
  });

  it('should create the service', () => {
    expect(service).toBeTruthy();
  });

  it('should load case data using the case identifier', async () => {
    dataCasesMock.getCaseData.mockReturnValue(of({}));

    await firstValueFrom(
      service.getSummary(caseId, currentCase)
    );

    expect(dataCasesMock.getCaseData)
      .toHaveBeenCalledWith(caseId);
  });

  it('should build the case summary correctly', async () => {
    dataCasesMock.getCaseData.mockReturnValue(
      of({
        caseBusinessIdentifier: caseId,
        initialBusinessData: {
          policy: {
            policyNumber: '2412-157945',
            broker: 'B/2757',
          },
        },
      })
    );

    const result = await firstValueFrom(
      service.getSummary(caseId, currentCase)
    );

    expect(result).toEqual({
      reference: caseId,
      title: 'Client profiling',
      facts: [
        {
          label: 'POLICY NUMBER',
          value: '2412-157945',
        },
        {
          label: 'CASE OWNER',
          value: 'John Doe',
        },
        {
          label: 'BROKER',
          value: 'B/2757',
        },
      ],
    });
  });

  it('should use default values when information is missing', async () => {
    dataCasesMock.getCaseData.mockReturnValue(
      of({
        initialBusinessData: {
          policy: {},
        },
      })
    );

    const result = await firstValueFrom(
      service.getSummary(caseId, {} as CurrentCase)
    );

    expect(result.reference).toBe('');
    expect(result.title).toBe('Client profiling');

    expect(result.facts).toEqual([
      { label: 'POLICY NUMBER', value: '-' },
      { label: 'CASE OWNER', value: '-' },
      { label: 'BROKER', value: '-' },
    ]);
  });

  it('should propagate an error when loading case data fails', async () => {
    dataCasesMock.getCaseData.mockReturnValue(
      throwError(() => new Error('API error'))
    );

    await expect(
      firstValueFrom(service.getSummary(caseId, currentCase))
    ).rejects.toThrow('API error');
  });
});
