import { TestBed } from '@angular/core/testing';
import { firstValueFrom, of, throwError } from 'rxjs';
import { ApplicationUsersService } from './application-users.service';
import { DataApplicationUsersService } from '../data-services/data-application-users.service';
// Adjust this import to the actual location in your application.
import { ErrorsService } from '@lia/commons';

describe('ApplicationUsersService', () => {
  let service: ApplicationUsersService;
  let data: { findAll: jest.Mock; findAllUserTeams: jest.Mock };
  let errors: { handle: jest.Mock };

  beforeEach(() => {
    data = { findAll: jest.fn(), findAllUserTeams: jest.fn() };
    errors = { handle: jest.fn() };
    TestBed.configureTestingModule({
      providers: [
        ApplicationUsersService,
        { provide: DataApplicationUsersService, useValue: data },
        { provide: ErrorsService, useValue: errors },
      ],
    });
    service = TestBed.inject(ApplicationUsersService);
  });

  it('returns all users', async () => {
    const users = [{ username: 'john.doe' }];
    data.findAll.mockReturnValue(of(users));

    await expect(firstValueFrom(service.findAll())).resolves.toEqual(users);
    expect(data.findAll).toHaveBeenCalledTimes(1);
  });

  it('handles an error while listing users and returns an empty array', async () => {
    const error = new Error('Users API unavailable');
    data.findAll.mockReturnValue(throwError(() => error));

    await expect(firstValueFrom(service.findAll())).resolves.toEqual([]);
    expect(errors.handle).toHaveBeenCalledWith(error);
  });

  it('returns the teams of a user', async () => {
    data.findAllUserTeams.mockReturnValue(of(['TEAM_A', 'TEAM_B']));

    await expect(firstValueFrom(service.findAllUserTeams('john.doe')))
      .resolves.toEqual(['TEAM_A', 'TEAM_B']);
    expect(data.findAllUserTeams).toHaveBeenCalledWith('john.doe');
  });

  it('handles an error while listing teams and returns an empty array', async () => {
    const error = new Error('Teams API unavailable');
    data.findAllUserTeams.mockReturnValue(throwError(() => error));

    await expect(firstValueFrom(service.findAllUserTeams('john.doe')))
      .resolves.toEqual([]);
    expect(errors.handle).toHaveBeenCalledWith(error);
  });

  it('returns false for an undefined username without calling the API', async () => {
    await expect(firstValueFrom(service.isUserPartOfTeam(undefined, 'TEAM_A')))
      .resolves.toBe(false);
    expect(data.findAllUserTeams).not.toHaveBeenCalled();
  });

  it('returns true when a user belongs to the team', async () => {
    data.findAllUserTeams.mockReturnValue(of(['TEAM_A', 'TEAM_B']));

    await expect(firstValueFrom(service.isUserPartOfTeam('john.doe', 'TEAM_B')))
      .resolves.toBe(true);
    expect(data.findAllUserTeams).toHaveBeenCalledWith('john.doe');
  });

  it('returns false when a user does not belong to the team', async () => {
    data.findAllUserTeams.mockReturnValue(of(['TEAM_A']));

    await expect(firstValueFrom(service.isUserPartOfTeam('john.doe', 'TEAM_B')))
      .resolves.toBe(false);
  });
});
