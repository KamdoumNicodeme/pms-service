protected saveChangeClientInformation(): void {
  const changeClientInformation =
    this.pendingChangeClientInformation();

  if (!changeClientInformation) {
    return;
  }

  const currentCase = this.currentCase();
  const taskId =
    this.currentTask().userTaskIdentifier;

  const payload =
    this.changeService.cleanForSave(
      changeClientInformation
    );

  this.saving.set(true);

  this.#caseService
    .updateChangeClientInformation(
      currentCase.caseBusinessIdentifier,
      payload,
      taskId
    )
    .pipe(
      finalize(() => this.saving.set(false))
    )
    .subscribe({
      next: () => {
        console.log(
          'ChangeClientInformation saved successfully'
        );
      },

      error: error => {
        console.error(
          'Error while saving ChangeClientInformation',
          error
        );
      }
    });
}
