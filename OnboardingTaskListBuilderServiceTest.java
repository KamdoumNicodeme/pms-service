protected readonly section = rxResource({
  params: () => ({
    policyNumber: this.policyNumber(),
    coreHolder: this.coreHolder(),
    digitalHolder: this.digitalHolder(),
    kycHolder: this.kycHolder(),
    sectionId: this.sectionId(),
  }),

  stream: ({ params }) => {
    console.log('PROFILING SECTION RELOAD', {
      sectionId: params.sectionId,
      digitalHolder: params.digitalHolder,
      kycHolder: params.kycHolder,
      coreHolder: params.coreHolder,
    });

    return this.data.getSection(
      {
        policyNumber: params.policyNumber,
        coreHolder: params.coreHolder,
        digitalHolder: params.digitalHolder,
        kycHolder: params.kycHolder,
      },
      params.sectionId
    );
  },
});
