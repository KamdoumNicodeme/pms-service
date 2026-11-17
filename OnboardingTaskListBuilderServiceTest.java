
import { TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import {
  HttpTestingController,
  provideHttpClientTesting,
} from '@angular/common/http/testing';

import { DataApplicationUsersService } from './data-application-users.service';
import { environment } from 'PATH_TO_ENVIRONMENT';

describe('DataApplicationUsersService', () => {
  let service: DataApplicationUsersService;
  let httpMock: HttpTestingController;

  const baseUrl = environment.apiUrls.get('caseManagement');

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        DataApplicationUsersService,
        provideHttpClient(),
        provideHttpClientTesting(),
      ],
    });

    service = TestBed.inject(DataApplicationUsersService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should create the service', () => {
    expect(service).toBeTruthy();
  });

  describe('findAll', () => {
    it('should retrieve all users', () => {
      const users = [
        { preferred_username: 'john.doe' },
        { preferred_username: 'jane.doe' },
      ];

      let result: unknown;

      service.findAll().subscribe(value => {
        result = value;
      });

      const req = httpMock.expectOne(`${baseUrl}/users`);

      expect(req.request.method).toBe('GET');

      req.flush(users);

      expect(result).toEqual(users);
    });
  });

  describe('findAllUserTeams', () => {
    it('should retrieve teams for a given user', () => {
      const username = 'john.doe';
      const teams = ['TEAM_A', 'TEAM_B'];

      let result: string[] | undefined;

      service.findAllUserTeams(username).subscribe(value => {
        result = value;
      });

      const req = httpMock.expectOne(
        `${baseUrl}/users/${username}/teams`
      );

      expect(req.request.method).toBe('GET');

      req.flush(teams);

      expect(result).toEqual(teams);
    });

    it('should return an empty array when the user has no teams', () => {
      const username = 'john.doe';

      let result: string[] | undefined;

      service.findAllUserTeams(username).subscribe(value => {
        result = value;
      });

      const req = httpMock.expectOne(
        `${baseUrl}/users/${username}/teams`
      );

      req.flush([]);

      expect(result).toEqual([]);
    });
  });
});
