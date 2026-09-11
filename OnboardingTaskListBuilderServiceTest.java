private loadReferences(): void {
  this.#referenceService
    .findReferencesByDomains([
      'POLICY_TYPE',
      'THIRD_PARTY_TYPE',
      'FATCA_STATUS',
      'AEOI_STATUS',
    ])
    .subscribe({
      next: (references: Map<string, IReference[]>) => {

        this.POLICY_TYPES_OPTIONS.set(
          references.get('POLICY_TYPE') ?? []
        );

        this.THIRD_PARTY_TYPES_OPTIONS.set(
          references.get('THIRD_PARTY_TYPE') ?? []
        );

        this.FATCA_STATUS_OPTIONS.set(
          references.get('FATCA_STATUS') ?? []
        );

        this.AEOI_STATUS_OPTIONS.set(
          references.get('AEOI_STATUS') ?? []
        );
      },

      error: error => {
        console.error(
          'Unable to load references',
          error
        );
      },
    });
}
