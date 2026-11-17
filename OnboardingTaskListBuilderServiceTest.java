import { TestBed } from '@angular/core/testing';
import { NzModalService } from 'ng-zorro-antd/modal';
import { AssignmentService } from './assignment.service';
// Adjust this import to the actual folder used by AssignmentService.
import { AssignmentComponent } from '../components/assignment/assignment.component';

describe('AssignmentService', () => {
  let service: AssignmentService;
  let modal: { create: jest.Mock };

  beforeEach(() => {
    modal = { create: jest.fn() };
    TestBed.configureTestingModule({
      providers: [
        AssignmentService,
        { provide: NzModalService, useValue: modal },
      ],
    });
    service = TestBed.inject(AssignmentService);
  });

  it('creates the assignment modal with the expected data and configuration', () => {
    const teams = [{ name: 'TEAM_A' }];
    const assignedTeam = { name: 'TEAM_A' };
    const assignee = { name: 'john.doe' };
    const ref = { afterClose: {} };
    modal.create.mockReturnValue(ref);

    const result = service.openAssignmentModal(
      teams as never,
      'Assign task',
      assignedTeam as never,
      assignee as never,
    );

    expect(modal.create).toHaveBeenCalledWith({
      nzTitle: 'Assign task',
      nzData: { teams, assignedTeam, assignee },
      nzCentered: true,
      nzWidth: '20%',
      nzFooter: null,
      nzContent: AssignmentComponent,
    });
    expect(result).toBe(ref);
  });

  it('allows the title and optional assignment values to be omitted', () => {
    modal.create.mockReturnValue({});

    service.openAssignmentModal([]);

    expect(modal.create).toHaveBeenCalledWith(
      expect.objectContaining({
        nzTitle: undefined,
        nzData: {
          teams: [],
          assignedTeam: undefined,
          assignee: undefined,
        },
      }),
    );
  });
});
