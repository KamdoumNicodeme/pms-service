findReferencesByDomains(
  domains: string[]
): Observable<Record<string, IReference[]>> {

  return this.#httpClient.get<
    Record<string, IReference[]>
  >(
    `${environment.apiUrls.get('cma')}/references`,
    {
      params: {
        domains: domains.join(','),
      },
    }
  );
}

private loadReferences(): void {
  this.#referenceService
    .findReferencesByDomains([
      'FATCA_STATUS',
      'AEOI_STATUS',
    ])
    .subscribe({
      next: (
        references: Record<string, IReference[]>
      ): void => {

        this.FATCA_STATUS_OPTIONS.set(
          references['FATCA_STATUS'] ?? []
        );

        this.AEOI_STATUS_OPTIONS.set(
          references['AEOI_STATUS'] ?? []
        );

        console.log(
          'FATCA REFERENCES',
          this.FATCA_STATUS_OPTIONS()
        );

        console.log(
          'AEOI REFERENCES',
          this.AEOI_STATUS_OPTIONS()
        );

        console.log(
          'FATCA OPTIONS',
          this.fatcaOptions()
        );

        console.log(
          'CRS OPTIONS',
          this.crsOptions()
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
