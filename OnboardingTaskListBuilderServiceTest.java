save(
  caseId: string,
  taskId: number,
  onSuccess: () => void
): void {

  const model = this.changeClientInformationForm.getRawValue();

  if (!model.policy || this.saving()) {
    return;
  }

  this.saving.set(true);

  this.#caseService
    .saveTask(
      caseId,
      taskId,
      TaskType.CHANGE_CLIENT_INFORMATION,
      model
    )
    .pipe(
      finalize(() => this.saving.set(false))
    )
    .subscribe({
      next: () => {
        this.changeClientInformationForm.markAsPristine();
        onSuccess();
      },
      error: error => {
        console.error(
          'Error saving Change Client Information',
          error
        );
      }
    });
}
