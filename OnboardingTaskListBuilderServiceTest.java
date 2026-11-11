save(caseId: string, taskId: number, onSucceed: () => void): void {
  const pendingChanges = this.pendingChangeClientInformation();

  if (!pendingChanges) {
    return;
  }

  const payload = this.changeService.cleanForSave(pendingChanges);

  this.saving.set(true);

  this.#caseService
    .saveTask(
      caseId,
      taskId,
      TaskType.CHANGE_CLIENT_INFORMATION,
      payload
    )
    .pipe(finalize(() => this.saving.set(false)))
    .subscribe({
      next: () => {
        this.resetPendingChanges();
        this.refresh();
        onSucceed();
      },
      error: (error: unknown) => {
        console.error('Error saving client information', error);
      }
    });
}
