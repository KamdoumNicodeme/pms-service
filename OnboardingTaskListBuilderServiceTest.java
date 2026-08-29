@Injectable()
export class CoreComparisonDataService implements ComparisonDataService {

  private readonly coreSource: ComparisonSourceMeta = {
    id: 'core',
    label: 'Core system',
    hint: 'What the core system holds right now',
    capturedAt: ''
  };

  getSection(
    context: ComparisonContext,
    sectionId: string
  ): Observable<ComparisonSectionDto> {

    const holder = context.holder;

    switch (sectionId) {
      case 'general-information':
        return of(this.generalInformation(holder));

      case 'contact-details':
        return of(this.emptySection(
          'contact-details',
          'Contact details'
        ));

      case 'identity-documents':
        return of(this.emptySection(
          'identity-documents',
          'Identity documents'
        ));

      case 'tax-information':
        return of(this.emptySection(
          'tax-information',
          'Tax information'
        ));

      default:
        return throwError(
          () => new Error(`Unknown section "${sectionId}"`)
        );
    }
  }

  private generalInformation(
    holder: IThirdParty
  ): ComparisonSectionDto {

    if (this.isPhysicalPerson(holder)) {
      return this.physicalPersonGeneralInformation(holder);
    }

    if (this.isMoralPerson(holder)) {
      return this.moralPersonGeneralInformation(holder);
    }

    return this.emptySection(
      'general-information',
      'General information'
    );
  }

  private physicalPersonGeneralInformation(
    holder: IPhysicalPerson
  ): ComparisonSectionDto {

    return {
      id: 'general-information',
      title: 'General information',
      sources: [this.coreSource],

      fields: [
        // on va remplir ici dès qu'on a ComparisonFieldDto
      ]
    };
  }

  private moralPersonGeneralInformation(
    holder: IMoralPerson
  ): ComparisonSectionDto {

    return {
      id: 'general-information',
      title: 'General information',
      sources: [this.coreSource],

      fields: [
        // idem
      ]
    };
  }

  private emptySection(
    id: string,
    title: string
  ): ComparisonSectionDto {

    return {
      id,
      title,
      sources: [this.coreSource],
      fields: []
    };
  }

  private isPhysicalPerson(
    holder: IThirdParty
  ): holder is IPhysicalPerson {
    return 'birthDate' in holder;
  }

  private isMoralPerson(
    holder: IThirdParty
  ): holder is IMoralPerson {
    return 'creationDate' in holder;
  }
}
