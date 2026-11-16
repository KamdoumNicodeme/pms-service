      expect(onSucceed).not.toHaveBeenCalled();
    });
  });

  describe('complete', () => {
    it('should complete the task and invoke onSucceed when confirmed', async () => {
      await createComponent();
      confirmationModalMock.openConfirmation.mockReturnValue({
        afterClose: of(true)
      });
      const onSucceed = jest.fn();

      component.complete(CASE_ID, TASK_ID, onSucceed);

      expect(confirmationModalMock.openConfirmation).toHaveBeenCalledTimes(1);
      expect(casesServiceMock.completeTask).toHaveBeenCalledWith(
        CASE_ID, TASK_ID, TaskType.CCI_ERROR_MANAGEMENT_SIGNOFF
      );
      expect(onSucceed).toHaveBeenCalledTimes(1);
    });

    it.each([false, null, undefined])(
      'should not complete when the confirmation result is %s',
      async confirmation => {
        await createComponent();
        confirmationModalMock.openConfirmation.mockReturnValue({
          afterClose: of(confirmation)
        });
        const onSucceed = jest.fn();

        component.complete(CASE_ID, TASK_ID, onSucceed);

        expect(casesServiceMock.completeTask).not.toHaveBeenCalled();
        expect(onSucceed).not.toHaveBeenCalled();
      }
    );

    it('should log an error and not invoke onSucceed if completion fails', async () => {
      await createComponent();
      const error = new Error('Complete failed');
      const consoleSpy = jest.spyOn(console, 'error').mockImplementation();
      confirmationModalMock.openConfirmation.mockReturnValue({ afterClose: of(true) });
      casesServiceMock.completeTask.mockReturnValue(throwError(() => error));
      const onSucceed = jest.fn();

      component.complete(CASE_ID, TASK_ID, onSucceed);

      expect(consoleSpy).toHaveBeenCalledWith(
        'Error completing error management Sign-off', error
      );
      expect(onSucceed).not.toHaveBeenCalled();
    });
  });

  describe('reject', () => {
    it('should send the rejection request and invoke onSucceed', async () => {
      await createComponent();
      rejectionModalMock.openRejectionModal.mockReturnValue({
        afterClose: of('Missing documents')
      });
      const onSucceed = jest.fn();

      component.reject(CASE_ID, TASK_ID, onSucceed);

      expect(rejectionModalMock.openRejectionModal).toHaveBeenCalledTimes(1);
      expect(casesServiceMock.rejectTask).toHaveBeenCalledWith(
        CASE_ID,
        TASK_ID,
        TaskType.CCI_ERROR_MANAGEMENT_SIGNOFF,
        'Missing documents',
        { rejectionReason: 'Missing documents' }
      );
      expect(onSucceed).toHaveBeenCalledTimes(1);
    });

    it.each([null, '', '   ', undefined])(
      'should not reject when the modal returns %s',
      async rejectionReason => {
        await createComponent();
        rejectionModalMock.openRejectionModal.mockReturnValue({
          afterClose: of(rejectionReason)
        });
        const onSucceed = jest.fn();

        component.reject(CASE_ID, TASK_ID, onSucceed);

        expect(casesServiceMock.rejectTask).not.toHaveBeenCalled();
        expect(onSucceed).not.toHaveBeenCalled();
      }
    );

    it('should log an error and not invoke onSucceed if rejection fails', async () => {
      await createComponent();
      const error = new Error('Reject failed');
      const consoleSpy = jest.spyOn(console, 'error').mockImplementation();
      rejectionModalMock.openRejectionModal.mockReturnValue({
        afterClose: of('Incorrect data')
      });
      casesServiceMock.rejectTask.mockReturnValue(throwError(() => error));
      const onSucceed = jest.fn();

      component.reject(CASE_ID, TASK_ID, onSucceed);

      expect(consoleSpy).toHaveBeenCalledWith(
        'Error rejecting error management Sign-off', error
      );
      expect(onSucceed).not.toHaveBeenCalled();
    });
  });
});
