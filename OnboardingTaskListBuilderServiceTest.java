
it('should open assignment and complete with selected owner when a manual change is required', async () => {
  await render({
    data: profiling({
      changeClientInformation: {
        policy: {
          clients: [],
          holder: {
            sendingAddress: {
              address: {
                streetName: 'Changed Street',
                postCode: '1000',
              },
            },
          },
          digitalConsent: {
            useElectronicSignatureConsentStatus: 'YES',
          },
        },
        changeReasons: {},
      },
    }),
  });

  expect(view(component).hasAddressChanged()).toBe(true);

  confirmationMock.openConfirmation.mockReturnValue({
    afterClose: of(true),
  });

  assignmentMock.openAssignmentModal.mockReturnValue({
    afterClose: of({
      assignee: 'agent.1',
      team: 'TEAM-MANUAL',
    }),
  });

  const success = jest.fn();

  component.complete(CASE_ID, TASK_ID, success);

  expect(confirmationMock.openConfirmation).toHaveBeenCalledTimes(1);

  expect(assignmentMock.openAssignmentModal).toHaveBeenCalledWith(
    expect.any(Array),
    'Manual change assignment',
    expect.objectContaining({ value: 'TEAM-A' }),
    expect.objectContaining({ value: 'jane.owner' }),
  );

  expect(casesMock.completeTask).toHaveBeenCalledWith(
    CASE_ID,
    TASK_ID,
    TaskType.CHANGE_CLIENT_INFORMATION_SIGNOFF,
    {
      signOffAssignment: {
        user: 'agent.1',
        team: 'TEAM-MANUAL',
      },
      manualChangeRequired: true,
    },
  );

  expect(success).toHaveBeenCalledTimes(1);
});
