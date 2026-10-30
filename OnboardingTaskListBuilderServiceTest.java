completeTask(
  caseBusinessIdentifier: string,
  taskId: number,
  taskType: TaskType,
  request?: SafeAny
): Observable<ITaskDetails> {
  console.log('request .....', request);

  if (isTaskUpdatable(taskType)) {
    return (
      Boolean(request)
        ? this.saveTask(
            caseBusinessIdentifier,
            taskId,
            taskType,
            request
          )
        : of({})
    ).pipe(
      switchMap(
        (): Observable<ITaskDetails> =>
          this.updateTaskAction(
            caseBusinessIdentifier,
            taskId,
            taskType,
            TaskAction.COMPLETE
          ).pipe(
            switchMap(
              (): Observable<ITaskDetails> =>
                this.#dataCasesService.findTaskById(taskId)
            )
          )
      )
    );
  }

  return this.updateTaskAction(
    caseBusinessIdentifier,
    taskId,
    taskType,
    TaskAction.COMPLETE,
    request?.manualChangeRequired
  ).pipe(
    switchMap(
      (): Observable<ITaskDetails> =>
        this.#dataCasesService.findTaskById(taskId)
    )
  );
}
