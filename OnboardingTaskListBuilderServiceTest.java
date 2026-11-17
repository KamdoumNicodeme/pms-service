import { RejectionModalService } from './rejection-modal.service';
// Adjust this import to the actual folder used by RejectionModalService.
import { RejectionComponent } from '../components/rejection/rejection.component';

describe('RejectionModalService', () => {
  it('creates a centered rejection modal and returns its reference', () => {
    // The class extends NzModalService. The inherited create() method is
    // replaced here to avoid constructing the real Angular modal/overlay.
    const service = Object.create(RejectionModalService.prototype) as RejectionModalService;
    const modalRef = { afterClose: {} };
    const create = jest.fn().mockReturnValue(modalRef);
    Object.defineProperty(service, 'create', { value: create });

    const result = service.openRejectionModal();

    expect(create).toHaveBeenCalledWith({
      nzTitle: 'You are about to reject this task',
      nzCentered: true,
      nzWidth: '30%',
      nzFooter: null,
      nzContent: RejectionComponent,
    });
    expect(result).toBe(modalRef);
  });
});
