import { TestBed } from '@angular/core/testing';
import { firstValueFrom, of, throwError } from 'rxjs';
import { ApplicationTeamsService } from './application-teams.service';
import { DataApplicationTeamsService } from '../data-services/data-application-teams.service';
// Adjust this import to the actual location in your application.
import { ErrorsService } from '@lia/commons';

describe('ApplicationTeamsService', () => {
  let service: ApplicationTeamsService;
  let data: { findTeamMembers: jest.Mock };
  let errors: { handle: jest.Mock };

  beforeEach(() => {
    data = { findTeamMembers: jest.fn() };
    errors = { handle: jest.fn() };
    TestBed.configureTestingModule({
      providers: [
        ApplicationTeamsService,
        { provide: DataApplicationTeamsService, useValue: data },
        { provide: ErrorsService, useValue: errors },
      ],
    });
    service = TestBed.inject(ApplicationTeamsService);
  });

  it('returns the members of the requested team', async () => {
    const members = [{ username: 'john.doe' }];
    data.findTeamMembers.mockReturnValue(of(members));

    await expect(firstValueFrom(service.findTeamMembers('TEAM_A')))
      .resolves.toEqual(members);
    expect(data.findTeamMembers).toHaveBeenCalledWith('TEAM_A');
    expect(errors.handle).not.toHaveBeenCalled();
  });

  it('handles API errors and returns an empty array', async () => {
    const error = new Error('Backend unavailable');
    data.findTeamMembers.mockReturnValue(throwError(() => error));

    await expect(firstValueFrom(service.findTeamMembers('TEAM_A')))
      .resolves.toEqual([]);
    expect(errors.handle).toHaveBeenCalledWith(error);
  });
});
