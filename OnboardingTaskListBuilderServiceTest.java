private loadReferences(): void {
  console.error('### NEW LOAD REFERENCES VERSION ###');

  this.#referenceService
    .findReferencesByDomains([
      'FATCA_STATUS',
      'AEOI_STATUS',
    ])
    .subscribe({
      next: references => {
        console.error(
          '### REFERENCES RECEIVED ###',
          references
        );

        this.FATCA_STATUS_OPTIONS.set(
          references['FATCA_STATUS'] ?? []
        );

        this.AEOI_STATUS_OPTIONS.set(
          references['AEOI_STATUS'] ?? []
        );

        console.log(
          'FATCA',
          this.FATCA_STATUS_OPTIONS()
        );

        console.log(
          'AEOI',
          this.AEOI_STATUS_OPTIONS()
        );
      },

      error: error => {
        console.error(
          '### REFERENCES ERROR ###',
          error
        );
      },

      complete: () => {
        console.log(
          '### REFERENCES COMPLETE ###'
        );
      },
    });
}
