protected saveChangeClientInformation(): void {
  const changeClientInformation: IChangeClientInformation | null =
    this.pendingChangeClientInformation();

  if (!changeClientInformation) {
    return;
  }

  const currentCase: ICaseDetails = this.currentCase();
  const taskId: number =
    this.currentTask().userTaskIdentifier;

  const payload: IChangeClientInformation =
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

        // Le pending n'est plus nécessaire :
        // les modifications sont maintenant sauvegardées.
        this.pendingChangeClientInformation.set(null);

        // Force le rechargement des données du case.
        this.refreshClientProfiling.update(
          value => value + 1
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
