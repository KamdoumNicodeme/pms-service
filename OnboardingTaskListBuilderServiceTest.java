@Service()
export class CaseSummaryServiceService implements CaseSummaryService {

  readonly #dataCasesService = inject(DataCasesService);

  getSummary(
    caseBusinessIdentifier: string,
    currentCase: ICaseDetails
  ): Observable<CaseSummary> {
    return this.#dataCasesService
      .getCaseData(caseBusinessIdentifier)
      .pipe(
        map((data: IClientProfilingData) =>
          this.toCaseSummary(data, currentCase)
        )
      );
  }

  private toCaseSummary(
    data: IClientProfilingData,
    currentCase: ICaseDetails
  ): CaseSummary {
    return {
      reference: data.caseBusinessIdentifier ?? '',
      title: 'Client profiling',
      facts: this.buildFacts(data, currentCase),
    };
  }

  private buildFacts(
    data: IClientProfilingData,
    currentCase: ICaseDetails
  ): { label: string; value: string }[] {
    return [
      {
        label: 'POLICY NUMBER',
        value: data.initialBusinessData?.policy?.policyNumber ?? '-',
      },
      {
        label: 'CASE OWNER',
        value: currentCase.ownerDetails?.displayName ?? '-',
      },
      {
        label: 'BROKER',
        value: data.initialBusinessData?.policy?.broker ?? '-',
      },
    ];
  }
}
