getSection(
  context: ComparisonContext,
  sectionId: string
): Observable<ComparisonSectionDto> {

  const coreHolder: IThirdParty = context.holder;

  const digitalHolder: IThirdParty | null =
    context.digitalHolder ?? null;

  const kycHolder: IThirdParty | null =
    context.kycHolder ?? null;

  switch (sectionId) {

    case 'general-information':
      return of(
        this.generalInformation(
          coreHolder,
          digitalHolder,
          kycHolder
        )
      );

    case 'contact-details':
      return of(
        this.coreUtils.contactDetails(
          coreHolder,
          digitalHolder,
          kycHolder,
          this.countryOptions()
        )
      );

    case 'identity-documents':
      if (
        this.isPhysicalPerson(coreHolder) &&
        (!digitalHolder || this.isPhysicalPerson(digitalHolder)) &&
        (!kycHolder || this.isPhysicalPerson(kycHolder))
      ) {
        return of(
          this.coreUtils.idDocument(
            coreHolder,
            digitalHolder,
            kycHolder,
            this.idTypeOptions()
          )
        );
      }

      return of(
        this.coreUtils.emptySection(
          'identity-documents',
          'Identity Documents'
        )
      );

    case 'tax-information':
      if (this.isPhysicalPerson(coreHolder)) {
        return of(
          this.coreUtils.taxInformation(
            coreHolder,
            digitalHolder && this.isPhysicalPerson(digitalHolder)
              ? digitalHolder
              : null,
            kycHolder && this.isPhysicalPerson(kycHolder)
              ? kycHolder
              : null,
            this.countryOptions(),
            this.tinReasonOptions()
          )
        );
      }

      if (this.isMoralPerson(coreHolder)) {
        return of(
          this.coreUtils.moralTaxInformation(
            coreHolder,
            digitalHolder && this.isMoralPerson(digitalHolder)
              ? digitalHolder
              : null,
            kycHolder && this.isMoralPerson(kycHolder)
              ? kycHolder
              : null,
            this.fatcaOptions(),
            this.crsOptions(),
            this.countryOptions(),
            this.tinReasonOptions()
          )
        );
      }

      return of(
        this.coreUtils.emptySection(
          'tax-information',
          'Tax Information'
        )
      );

    default:
      return throwError(
        () => new Error(`Unknown section "${sectionId}"`)
      );
  }
}
