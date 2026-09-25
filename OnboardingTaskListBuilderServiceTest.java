readonly taskDetailsInputs = computed(() => {
  const currentCase = this.sourceCase();
  const task = this.task();

  if (!currentCase || !task) {
    return {};
  }

  const commonInputs = {
    currentTask: task,
    currentCase,
    policy: this.policies(),
    positions: this.positions().flat(),
  };

  switch (currentCase.caseType) {
    case CaseType.CLIENT_PROFILING:
      return {
        ...commonInputs,
        caseDocuments: this.caseDocuments(),
        clientProfilingReferences: this.clientProfilingReferences(),
      };

    case CaseType.CHANGE_OF_STRATEGY:
      return commonInputs;

    default:
      return {};
  }
});

<ng-container
  *ngComponentOutlet="
    component;
    inputs: taskDetailsInputs()
  "
/>
