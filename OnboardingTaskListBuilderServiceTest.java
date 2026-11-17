
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NzModalRef } from 'ng-zorro-antd/modal';

import { CompletionConfirmationComponent } from './completion-confirmation.component';

describe('CompletionConfirmationComponent', () => {
  let component: CompletionConfirmationComponent;
  let fixture: ComponentFixture<CompletionConfirmationComponent>;

  let modalRefMock: {
    close: jest.Mock;
  };

  beforeEach(async () => {
    modalRefMock = {
      close: jest.fn(),
    };

    await TestBed.configureTestingModule({
      imports: [CompletionConfirmationComponent],
      providers: [
        {
          provide: NzModalRef,
          useValue: modalRefMock,
        },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(
      CompletionConfirmationComponent
    );

    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('should display the confirmation message', () => {
    const message: HTMLElement =
      fixture.nativeElement.querySelector(
        '.completion-confirmation__message'
      );

    expect(message.textContent?.trim()).toBe(
      'Are you sure you want to complete this task?'
    );
  });

  it('should display Cancel and Confirm buttons', () => {
    const buttons: HTMLButtonElement[] = Array.from(
      fixture.nativeElement.querySelectorAll(
        '.completion-confirmation__actions button'
      )
    );

    expect(buttons).toHaveLength(2);
    expect(buttons[0].textContent?.trim()).toBe('Cancel');
    expect(buttons[1].textContent?.trim()).toBe('Confirm');
  });

  it('should close the modal with false when Cancel is clicked', () => {
    const cancelButton: HTMLButtonElement =
      fixture.nativeElement.querySelector(
        '.completion-confirmation__actions button:first-child'
      );

    cancelButton.click();

    expect(modalRefMock.close).toHaveBeenCalledTimes(1);
    expect(modalRefMock.close).toHaveBeenCalledWith(false);
  });

  it('should close the modal with true when Confirm is clicked', () => {
    const confirmButton: HTMLButtonElement =
      fixture.nativeElement.querySelector(
        '.completion-confirmation__actions button:last-child'
      );

    confirmButton.click();

    expect(modalRefMock.close).toHaveBeenCalledTimes(1);
    expect(modalRefMock.close).toHaveBeenCalledWith(true);
  });

  it('should not close the modal on initialization', () => {
    expect(modalRefMock.close).not.toHaveBeenCalled();
  });
});
