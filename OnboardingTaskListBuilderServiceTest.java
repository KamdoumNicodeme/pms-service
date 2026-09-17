export class ProfilingSection {

  private readonly data: ComparisonDataService =
    inject(COMPARISON_DATA_SERVICE);

  readonly policyNumber: InputSignal<string> =
    input.required<string>();

  readonly holder: InputSignal<IThirdParty> =
    input.required<IThirdParty>();

  readonly sectionId: InputSignal<string> =
    input.required<string>();

  readonly filter: InputSignal<ComparisonFilter> =
    input<ComparisonFilter>('all');

  readonly countersChange: OutputEmitterRef<ComparisonCounters> =
    output<ComparisonCounters>();

  readonly changesChange: OutputEmitterRef<ComparisonChanges> =
    output<ComparisonChanges>();

  protected readonly section = rxResource({
    params: () => ({
      policyNumber: this.policyNumber(),
      holder: this.holder(),
      sectionId: this.sectionId()
    }),

    stream: ({ params }) =>
      this.data.getSection(
        {
          policyNumber: params.policyNumber,
          holder: params.holder
        },
        params.sectionId
      )
  });
}
