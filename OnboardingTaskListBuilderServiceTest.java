
import { TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import {
  HttpTestingController,
  provideHttpClientTesting,
} from '@angular/common/http/testing';

import { DataApplicationTeamsService } from './data-application-teams.service';
import { environment } from 'PATH_TO_ENVIRONMENT';

describe('DataApplicationTeamsService', () => {
  let service: DataApplicationTeamsService;
  let httpMock: HttpTestingController;

  const baseUrl = environment.apiUrls.get('caseManagement');

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        DataApplicationTeamsService,
        provideHttpClient(),
        provideHttpClientTesting(),
      ],
    });

    service = TestBed.inject(DataApplicationTeamsService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should create the service', () => {
    expect(service).toBeTruthy();
  });

  it('should retrieve team members', () => {
    const team = 'TEAM_A';

    const members = [
      { username: 'john.doe' },
      { username: 'jane.doe' },
    ];

    let result: unknown;

    service.findTeamMembers(team).subscribe(value => {
      result = value;
    });

    const req = httpMock.expectOne(
      `${baseUrl}/teams/${team}/members`
    );

    expect(req.request.method).toBe('GET');

    req.flush(members);

    expect(result).toEqual(members);
  });

  it('should propagate HTTP errors', () => {
    let receivedError: unknown;

    service.findTeamMembers('TEAM_A').subscribe({
      error: error => {
        receivedError = error;
      },
    });

    const req = httpMock.expectOne(
      `${baseUrl}/teams/TEAM_A/members`
    );

    req.flush(
      { message: 'Server error' },
      {
        status: 500,
        statusText: 'Internal Server Error',
      }
    );

    expect(receivedError).toMatchObject({
      status: 500,
    });
  });
});
