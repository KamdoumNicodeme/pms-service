casesServiceMock = {
  // Conserve tes autres méthodes
  getCaseDataAfterClientProfiling: jest.fn().mockReturnValue(of(profilingData())),
  getCaseBusinessDataUptoDate: jest.fn().mockReturnValue(of(businessData())),
  saveTask: jest.fn().mockReturnValue(of({})),
  completeTask: jest.fn().mockReturnValue(of({})),
  rejectTask: jest.fn().mockReturnValue(of({}))
};




it('should reject the task and call onSucceed when a rejection reason is provided', () => {
  const caseId = 'CASE-123';
  const taskId = 555;
  const rejectionReason = 'Missing documents';
  const onSucceedSpy = jest.fn();

  rejectionModalServiceMock.openRejectionModal.mockReturnValue({
    afterClose: of(rejectionReason)
  });

  casesServiceMock.rejectTask.mockReturnValue(of({}));

  component.reject(caseId, taskId, onSucceedSpy);

  expect(rejectionModalServiceMock.openRejectionModal)
    .toHaveBeenCalledTimes(1);

  expect(casesServiceMock.rejectTask).toHaveBeenCalledWith(
    caseId,
    taskId,
    TaskType.CCI_MANUAL_CHANGE_SIGNOFF,
    rejectionReason,
    { rejectionReason }
  );

  expect(onSucceedSpy).toHaveBeenCalledTimes(1);
});

rejectionModalServiceMock = {
  openRejectionModal: jest.fn().mockReturnValue({
    afterClose: of(null)
  })
};


it('should not reject the task when no rejection reason is provided', () => {
  const onSucceedSpy = jest.fn();

  rejectionModalServiceMock.openRejectionModal.mockReturnValue({
    afterClose: of(null)
  });

  component.reject('CASE-123', 555, onSucceedSpy);

  expect(casesServiceMock.rejectTask).not.toHaveBeenCalled();
  expect(onSucceedSpy).not.toHaveBeenCalled();
});

