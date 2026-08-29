@Injectable()

export class CoreComparisonDataService implements ComparisonDataService {

  private readonly coreSource: ComparisonSourceMeta = {

    id: 'core',

    label: 'Core system',

    hint: 'Current value stored in the core system',

    capturedAt: '',

  };

  getSection(

    context: ComparisonContext,

    sectionId: string,

  ): Observable<ComparisonSectionDto> {

    const holder = context.holder;

    switch (sectionId) {

      case 'general-information':

        return of(this.generalInformation(holder));

      case 'contact-details':

        return of(

          this.emptySection(

            'contact-details',

            'Contact details',

          ),

        );

      case 'identity-documents':

        return of(

          this.emptySection(

            'identity-documents',

            'Identity documents',

          ),

        );

      case 'tax-information':

        return of(

          this.emptySection(

            'tax-information',

            'Tax information',

          ),

        );

      default:

        return throwError(

          () =>

            new Error(

              `Unknown section "${sectionId}"`,

            ),

        );

    }

  }

  private generalInformation(

    holder: IThirdParty,

  ): ComparisonSectionDto {

    if (this.isPhysicalPerson(holder)) {

      return this.physicalPersonGeneralInformation(holder);

    }

    if (this.isMoralPerson(holder)) {

      return this.moralPersonGeneralInformation(holder);

    }

    return this.emptySection(

      'general-information',

      'General information',

    );

  }

  private physicalPersonGeneralInformation(

    holder: IPhysicalPerson,

  ): ComparisonSectionDto {

    return {

      id: 'general-information',

      title: 'General information',

      sources: [

        this.coreSource,

      ],

      fields: [

        this.scalarField(

          'lastname',

          'Surname',

          holder.lastname,

        ),

        this.scalarField(

          'firstname',

          'First name',

          holder.firstname,

        ),

        this.scalarField(

          'birth-date',

          'Date of birth',

          this.formatDate(holder.birthDate),

        ),

        this.scalarField(

          'birth-place',

          'Place of birth',

          holder.birthPlace,

        ),

        this.scalarField(

          'birth-country',

          'Country of birth',

          holder.birthCountry,

        ),

        this.scalarField(

          'nationality',

          'Nationality',

          holder.nationality,

        ),

      ],

    };

  }

  private moralPersonGeneralInformation(

    holder: IMoralPerson,

  ): ComparisonSectionDto {

    return {

      id: 'general-information',

      title: 'General information',

      sources: [

        this.coreSource,

      ],

      fields: [

        this.scalarField(

          'name',

          'Company name',

          holder.name,

        ),

        this.scalarField(

          'creation-date',

          'Creation date',

          this.formatDate(holder.creationDate),

        ),

        this.scalarField(

          'creation-place',

          'Creation place',

          holder.creationPlace,

        ),

        this.scalarField(

          'country',

          'Country',

          holder.country,

        ),

        this.scalarField(

          'vat-number',

          'VAT number',

          holder.vatNumber,

        ),

      ],

    };

  }

  private scalarField(

    key: string,

    label: string,

    value: string | null | undefined,

  ): ComparisonScalarFieldDto {

    return {

      key,

      label,

      kind: 'text',

      values: {

        digital: null,

        kyc: null,

        core: this.normalize(value),

      },

    };

  }

  private emptySection(

    id: string,

    title: string,

  ): ComparisonSectionDto {

    return {

      id,

      title,

      sources: [

        this.coreSource,

      ],

      fields: [],

    };

  }

  private normalize(

    value: string | null | undefined,

  ): string | null {

    if (!value) {

      return null;

    }

    const normalized = value.trim();

    return normalized.length > 0

      ? normalized

      : null;

  }

  private formatDate(

    value: Date | string | null | undefined,

  ): string | null {

    if (!value) {

      return null;

    }

    const date = new Date(value);

    if (Number.isNaN(date.getTime())) {

      return null;

    }

    return new Intl.DateTimeFormat(

      'en-GB',

    ).format(date);

  }

  private isPhysicalPerson(

    holder: IThirdParty,

  ): holder is IPhysicalPerson {

    return 'birthDate' in holder;

  }

  private isMoralPerson(

    holder: IThirdParty,

  ): holder is IMoralPerson {

    return 'creationDate' in holder;

  }

}
