protected readonly saving: WritableSignal<boolean> = signal(false);

protected saveChangeClientInformation(): void {
  const currentCase: ICaseDetails = this.currentCase();

  const changeClientInformation: IChangeClientInformation | null =
    this.pendingChangeClientInformation();

  if (!changeClientInformation) {
    console.warn(
      '[ClientProfilingComponent] No change client information to save'
    );
    return;
  }

  this.saving.set(true);

  this.#caseService
    .updateChangeClientInformation(
      currentCase.caseBusinessIdentifier,
      changeClientInformation
    )
    .pipe(
      finalize((): void => this.saving.set(false))
    )
    .subscribe({
      next: (): void => {
        console.log(
          '[ClientProfilingComponent] Change client information saved successfully'
        );
      },
      error: (error: unknown): void => {
        console.error(
          '[ClientProfilingComponent] Failed to save change client information',
          error
        );
      }
    });
}
