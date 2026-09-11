constructor() {

    this.loadReferences();

  }

  private loadReferences(): void {

    this.referenceService

      .findReferencesByDomains([

        'POLICY_TYPE',

        'THIRD_PARTY_TYPE',

        'FATCA_STATUS',

        'AEOI_STATUS',

      ])

      .subscribe({

        next: (value: IReference[]) => {

          const refs =

            new Map(

              value.map(

                (ref: IReference) => [

                  ref.domain,

                  ref

                ]

              )

            );

          this.POLICY_TYPES_OPTIONS.set(

            refs.get('POLICY_TYPE') ?? []

          );

          this.THIRD_PARTY_TYPES_OPTIONS.set(

            refs.get('THIRD_PARTY_TYPE') ?? []

          );

          this.FATCA_STATUS_OPTIONS.set(

            refs.get('FATCA_STATUS') ?? []

          );

          this.AEOI_STATUS_OPTIONS.set(

            refs.get('AEOI_STATUS') ?? []

          );

        },

      });
