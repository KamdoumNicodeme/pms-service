
export class CciManualChangeSignOffComponent implements TaskManagement {
  private readonly caseSummary = inject(CASE_SUMMARY_SERVICE);
  private readonly comparisonService = inject(COMPARISON_DATA_SERVICE);
  private readonly rejectionModalService = inject(RejectionModalService);
  private readonly completionConfirmationModalService = inject(
    CompletionConfirmationModalService
  );

  readonly #caseService = inject(CasesService);

  readonly clientProfilingReferences =
    input<Record<string, IReference[]>>({});

  readonly currentTask = input.required<ITaskDetails>();
  readonly currentCase = input.required<ICaseDetails>();
  readonly policy = input.required<IPolicy | null | undefined>();
  readonly readOnly = input<boolean>(true);

  readonly checksCompleted = output<boolean>();

  protected readonly summary = rxResource({
    stream: () =>
      this.caseSummary.getSummary(
        this.currentCase()?.caseBusinessIdentifier,
        this.currentCase()
      )
  });

  protected readonly clientProfilingCasesData = toSignal(
    toObservable(this.currentCase).pipe(
      filter(Boolean),
      switchMap(currentCase =>
        this.#caseService.getCaseDataAfterClientProfiling(
          currentCase.caseBusinessIdentifier
        )
      )
    ),
    { initialValue: null }
  );

  protected readonly getCurrentBusinessData = toSignal(
    toObservable(this.currentCase).pipe(
      filter(Boolean),
      switchMap(currentCase =>
        this.#caseService.getCaseBusinessDataUptoDate(
          currentCase.caseBusinessIdentifier
        )
      )
    ),
    { initialValue: null }
  );

  protected readonly isCommPreferencesChanged = computed<boolean>(() => {
    const clientConsent =
      this.clientProfilingCasesData()
        ?.changeClientInformation
        ?.policy
        ?.digitalConsent;

    const coreConsent =
      this.getCurrentBusinessData()
        ?.policy
        ?.digitalConsent;

    if (!clientConsent || !coreConsent) {
      return false;
    }

    return (
      clientConsent.receiveElectronicCommunicationsConsentStatus !==
        coreConsent.receiveElectronicCommunicationsConsentStatus ||
      clientConsent.useDigitalPlatformConsentStatus !==
        coreConsent.useDigitalPlatformConsentStatus ||
      clientConsent.useElectronicSignatureConsentStatus !==
        coreConsent.useElectronicSignatureConsentStatus
    );
  });

  readonly form = computed<CCIManualChangeForm>(() =>
    new CCIManualChangeForm(
      this.checksCompleted,
      this.clientProfilingCasesData()?.manualChange,
      this.isCommPreferencesChanged()
    )
  );

  constructor() {
    effect(() => {
      this.comparisonService.setReferences(
        this.clientProfilingReferences()
      );
    });
  }

  save(
    _caseId: string,
    _taskId: number,
    _onSucceed: () => void
  ): void {}

  complete(
    caseId: string,
    taskId: number,
    onSucceed: () => void
  ): void {
    this.completionConfirmationModalService
      .openConfirmation()
      .afterClose
      .pipe(
        filter((confirmed): confirmed is true => confirmed === true),
        switchMap(() =>
          this.#caseService.completeTask(
            caseId,
            taskId,
            TaskType.CCI_MANUAL_CHANGE_SIGNOFF
          )
        )
      )
      .subscribe({
        next: () => onSucceed(),
        error: error =>
          console.error('Error completing Manual Change Sign-off', error)
      });
  }

  reject(
    caseId: string,
    taskId: number,
    onSucceed: () => void
  ): void {
    this.rejectionModalService
      .openRejectionModal()
      .afterClose
      .pipe(
        filter(
          (reason): reason is string =>
            typeof reason === 'string' && reason.trim().length > 0
        ),
        switchMap(rejectionReason => {
          const request: IManualChangeSignOff = {
            rejectionReason
          };

          return this.#caseService.rejectTask(
            caseId,
            taskId,
            TaskType.CCI_MANUAL_CHANGE_SIGNOFF,
            rejectionReason,
            request
          );
        })
      )
      .subscribe({
        next: () => onSucceed(),
        error: error =>
          console.error('Error rejecting Manual Change Sign-off', error)
      });
  }
}
