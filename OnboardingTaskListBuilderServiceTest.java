private updateTaskAction(
  caseBusinessIdentifier: string,
  taskId: number,
  taskType: TaskType,
  taskAction: TaskAction,
  manualChangeRequired?: boolean
): Observable<any> {
  let addParams: Map<string, boolean> | undefined;

  const validationParam = getValidationParam(taskType);

  if (validationParam) {
    addParams = new Map<string, boolean>([
      [validationParam, taskAction === TaskAction.COMPLETE]
    ]);
  }

  if (
    taskType === TaskType.CHANGE_CLIENT_INFORMATION_SIGNOFF &&
    taskAction === TaskAction.COMPLETE
  ) {
    addParams ??= new Map<string, boolean>();

    addParams.set(
      'cciManualChangeRequired',
      manualChangeRequired ?? false
    );
  }

  return this.#dataCasesService
    .updateTaskAction(taskId, taskAction, addParams)
    .pipe(
      switchMap((result: any) =>
        forkJoin([
          this.#dataTasksService.updateTimestamp(taskId),
          this.#dataCasesService.updateTimestamp(caseBusinessIdentifier),
        ]).pipe(map(() => result))
      )
    );
}
