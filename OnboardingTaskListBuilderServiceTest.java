@Injectable()
export class CoreComparisonDataService
  implements ComparisonDataService {

  getSection(
    context: ComparisonContext,
    sectionId: string
  ): Observable<ComparisonSectionDto> {

    const holder = context.holder;

    switch (sectionId) {

      case 'general-information':
        return of(
          this.generalInformation(holder)
        );

      case 'contact-details':
        return of(
          this.emptySection(
            'contact-details',
            'Contact details'
          )
        );

      case 'identity-documents':
        return of(
          this.emptySection(
            'identity-documents',
            'Identity documents'
          )
        );

      case 'tax-information':
        return of(
          this.emptySection(
            'tax-information',
            'Tax information'
          )
        );

      default:
        return throwError(
          () => new Error(
            `Unknown section "${sectionId}"`
          )
        );
    }
  }

  private generalInformation(
    holder: IThirdParty
  ): ComparisonSectionDto {

    // Ici on va mapper ton IThirdParty
    // vers TON ComparisonSectionDto existant.

    // À compléter avec la vraie structure du DTO.
    throw new Error('Mapping to implement');
  }

  private emptySection(
    id: string,
    title: string
  ): ComparisonSectionDto {

    // À adapter également au ComparisonSectionDto existant.
    throw new Error('Mapping to implement');
  }
}
