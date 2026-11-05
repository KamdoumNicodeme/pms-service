import { TestBed } from '@angular/core/testing';
import { NzModalRef, NzModalService } from 'ng-zorro-antd/modal';

import { CompletionConfirmationModalService } from './completion-confirmation-modal.service';
import { CompletionConfirmationComponent } from '../../../features/client-profiling/shared/confirmation/completion-confirmation.component';

describe('CompletionConfirmationModalService', () => {
  let service: CompletionConfirmationModalService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        CompletionConfirmationModalService,
      ],
    });

    service = TestBed.inject(CompletionConfirmationModalService);
  });

  afterEach(() => {
    jest.restoreAllMocks();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should open the completion confirmation modal', () => {
    const modalRef = {
      afterClose: {
        subscribe: jest.fn(),
      },
    } as unknown as NzModalRef<CompletionConfirmationComponent, any>;

    const createSpy = jest
      .spyOn(service, 'create')
      .mockReturnValue(modalRef);

    const result = service.openConfirmation();

    expect(createSpy).toHaveBeenCalledTimes(1);

    expect(createSpy).toHaveBeenCalledWith({
      nzTitle: 'Complete task',
      nzCentered: true,
      nzWidth: '30%',
      nzFooter: null,
      nzContent: CompletionConfirmationComponent,
    });

    expect(result).toBe(modalRef);
  });
});
