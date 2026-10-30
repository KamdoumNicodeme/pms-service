private completeSignOff(
  caseId: string,
  taskId: number,
  onSuccess: () => void
): void {
  const manualChangeRequired = this.hasAddressChanged();

  this.#completionConfirmationModalService
    .openConfirmation()
    .afterClose
    .subscribe((confirmed: boolean): void => {
      if (!confirmed) {
        return;
      }

      if (manualChangeRequired) {
        this.#assignmentService
          .openAssignmentModal(
            this.teams(),
            'Manual change assignment',
            {
              displayName: this.currentCase()?.ownerTeam,
              value: this.currentCase()?.ownerTeam,
            } as Assignee,
            {
              displayName: this.currentCase()?.ownerDetails?.displayName,
              value: this.currentCase()?.ownerDetails?.username,
            } as Assignee
          )
          .afterClose.pipe(
            filter(Boolean),
            switchMap((owner: AssigneeInput) =>
              this.#caseService.completeTask(
                caseId,
                taskId,
                TaskType.CHANGE_CLIENT_INFORMATION_SIGNOFF,
                {
                  signOffAssignment: {
                    user: owner.assignee,
                    team: owner.team,
                  },
                  manualChangeRequired,
                }
              )
            )
          )
          .subscribe(() => onSuccess());

        return;
      }

      this.#caseService
        .completeTask(
          caseId,
          taskId,
          TaskType.CHANGE_CLIENT_INFORMATION_SIGNOFF,
          {
            manualChangeRequired,
          }
        )
        .subscribe(() => onSuccess());
    });
}
