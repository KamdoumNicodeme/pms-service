private completeSignOff(
  caseId: string,
  taskId: number,
  onSuccess: () => void
): void {
  this.#completionConfirmationModalService
    .openConfirmationModal()
    .afterClose
    .subscribe((confirmed: boolean): void => {
      if (!confirmed) {
        return;
      }

      this.#caseService
        .completeTask(
          caseId,
          taskId,
          TaskType.CHANGE_CLIENT_INFORMATION_SIGNOFF,
          {}
        )
        .subscribe((): void => onSuccess());
    });
}
