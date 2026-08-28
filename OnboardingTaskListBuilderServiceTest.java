@Injectable()
export class ApiComparisonDataService
  implements ComparisonDataService {

  private readonly thirdPartyService = inject(ThirdPartyService);

  getHolders(
    policyNumber: string
  ): Observable<readonly ClientHolder[]> {

    return this.thirdPartyService
      .findAll(policyNumber, {})
      .pipe(
        map(thirdParties =>
          thirdParties.map(thirdParty =>
            this.toClientHolder(thirdParty)
          )
        )
      );
  }

  getSection(
    policyNumber: string,
    holderId: string,
    sectionId: string
  ): Observable<ComparisonSectionDto> {

    return this.thirdPartyService
      .findAll(policyNumber, {})
      .pipe(
        map(thirdParties => {
          const holder = thirdParties.find(
            thirdParty =>
              this.getHolderId(thirdParty) === holderId
          );

          if (!holder) {
            throw new Error(
              `Holder ${holderId} not found`
            );
          }

          return this.toComparisonSection(
            holder,
            sectionId
          );
        })
      );
  }

  private toClientHolder(
    thirdParty: IThirdParty
  ): ClientHolder {

    return {
      id: this.getHolderId(thirdParty),
      name: this.getHolderName(thirdParty)
    };
  }

  private getHolderId(
    thirdParty: IThirdParty
  ): string {
    // À adapter avec ton vrai modèle IThirdParty
    return thirdParty.id;
  }

  private getHolderName(
    thirdParty: IThirdParty
  ): string {
    // À adapter au vrai DTO
    return `${thirdParty.firstName ?? ''} ${thirdParty.lastName ?? ''}`.trim();
  }

  private toComparisonSection(
    thirdParty: IThirdParty,
    sectionId: string
  ): ComparisonSectionDto {

    switch (sectionId) {

      case 'general-information':
        return this.mapGeneralInformation(thirdParty);

      case 'contact-details':
        return this.mapContactDetails(thirdParty);

      case 'identity-documents':
        return this.mapIdentityDocuments(thirdParty);

      case 'tax-information':
        return this.mapTaxInformation(thirdParty);

      default:
        throw new Error(
          `Unsupported section: ${sectionId}`
        );
    }
  }
}
