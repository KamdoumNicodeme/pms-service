export class PolicySection {

  readonly corePolicy: InputSignal<IPolicy> =
    input.required<IPolicy>();

  readonly digitalPolicy: InputSignal<IPolicy | null> =
    input<IPolicy | null>(null);

  readonly kycPolicy: InputSignal<IPolicy | null> =
    input<IPolicy | null>(null);

  readonly sectionId: InputSignal<string> =
    input.required<string>();

  readonly filter: InputSignal<ComparisonFilter> =
    input<ComparisonFilter>('all');

  readonly countersChange: OutputEmitterRef<ComparisonCounters> =
    output<ComparisonCounters>();

  readonly changesChange: OutputEmitterRef<ComparisonChanges> =
    output<ComparisonChanges>();

  private readonly data: ComparisonDataService =
    inject(COMPARISON_DATA_SERVICE);

  protected readonly section:
    ResourceRef<ComparisonSectionDto | undefined> = rxResource({

    params: () => ({
      corePolicy: this.corePolicy(),
      digitalPolicy: this.digitalPolicy(),
      kycPolicy: this.kycPolicy(),
      sectionId: this.sectionId(),
    }),

    stream: ({ params }) =>
      this.data.getPolicySection(
        params.corePolicy,
        params.digitalPolicy,
        params.kycPolicy,
        params.sectionId
      ),
  });
}
