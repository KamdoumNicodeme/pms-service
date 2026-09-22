protected readonly section = rxResource({
  params: () => ({
    policyNumber: this.policyNumber(),
    coreHolder: this.coreHolder(),
    digitalHolder: this.digitalHolder(),
    kycHolder: this.kycHolder(),
    sectionId: this.sectionId(),
  }),

  stream: ({ params }) =>
    this.data.getSection(
      {
        policyNumber: params.policyNumber,
        coreHolder: params.coreHolder,
        digitalHolder: params.digitalHolder,
        kycHolder: params.kycHolder,
      },
      params.sectionId
    ),
});
