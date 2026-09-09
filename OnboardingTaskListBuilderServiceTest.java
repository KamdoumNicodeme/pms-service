@Service()
export class CoreComparisonUtils {

  private readonly coreSource: ComparisonSourceMeta = {
    id: 'core',
    label: 'Core system',
    hint: 'Current value stored in the core system',
    capturedAt: '',
  };

  private readonly identityOptions: readonly ComparisonOption[] = [
    {
      value: 'ID_CARD',
      label: 'ID card',
    },
    {
      value: 'PASSPORT',
      label: 'Signed Passport',
    },
  ];

  private readonly taxUnavailableOptions: readonly ComparisonOption[] = [
    {
      value: 'A',
      label: 'A',
    },
    {
      value: 'B',
      label: 'B',
    },
    {
      value: 'C',
      label: 'C',
    },
  ];

  private readonly countryOptions: readonly ComparisonOption[] =
    this.buildCountryOptions();


  // =========================================================
  // GENERAL INFORMATION - PHYSICAL
  // =========================================================

  public physicalPersonGeneralInformation(
    holder: IPhysicalPerson
  ): ComparisonSectionDto {

    return {
      id: 'general-information',
      title: 'General information',
      sources: [this.coreSource],

      fields: [
        this.textField(
          'thirdPartyId',
          'ThirdParty ID',
          holder.thirdPartyId
        ),

        this.textField(
          'lastname',
          'Surname',
          holder.lastname
        ),

        this.textField(
          'firstname',
          'First name',
          holder.firstname
        ),

        this.textField(
          'birth-date',
          'Date of birth',
          this.formatDate(holder.birthDate)
        ),

        this.textField(
          'birth-country',
          'Country of birth',
          this.normalizeCountry(holder.birthCountry)
        ),

        this.listField(
          'nationalities',
          'Nationalities',
          'nationality',
          this.getNationalities(holder.nationality)
        ),

        this.textField(
          'status',
          'Marital Status',
          holder.civilStatus?.status
        ),

        ...this.professionalFields(holder),
      ],
    };
  }


  // =========================================================
  // GENERAL INFORMATION - MORAL
  // =========================================================

  public moralPersonGeneralInformation(
    holder: IMoralPerson
  ): ComparisonSectionDto {

    return {
      id: 'general-information',
      title: 'General information',
      sources: [this.coreSource],

      fields: [
        this.textField(
          'thirdPartyId',
          'ThirdParty ID',
          holder.thirdPartyId
        ),

        this.textField(
          'name',
          'Company name',
          holder.name
        ),

        this.textField(
          'creation-date',
          'Creation date',
          this.formatDate(holder.creationDate)
        ),

        this.textField(
          'vat-number',
          'VAT number',
          holder.vatNumber
        ),
      ],
    };
  }


  // =========================================================
  // CONTACT DETAILS
  // =========================================================

  public contactDetails(
    holder: IThirdParty
  ): ComparisonSectionDto {

    return {
      id: 'contact-details',
      title: 'Contact details',
      sources: [this.coreSource],

      fields: [
        this.listField(
          'emails',
          'Emails',
          'email',
          holder.emails?.map(email => email.email) ?? []
        ),

        this.listField(
          'phone-numbers',
          'Phone numbers',
          'phone number',
          holder.phoneNumbers?.map(phone => phone.phoneNumber) ?? []
        ),

        this.addressGroup(
          'legal-address',
          'Legal address',
          holder.legalAddress
        ),
      ],
    };
  }


  // =========================================================
  // IDENTITY DOCUMENTS
  // =========================================================

  public idDocument(
    holder: IPhysicalPerson
  ): ComparisonSectionDto {

    return {
      id: 'identity-documents',
      title: 'Identity Documents',
      sources: [this.coreSource],

      fields: [
        this.selectField(
          'type',
          'Document Type',
          holder.idDocument?.type,
          this.identityOptions
        ),

        this.textField(
          'number',
          'Document Number',
          holder.idDocument?.number
        ),

        this.textField(
          'expirationDate',
          'Expiry Date',
          this.formatDate(holder.idDocument?.expirationDate)
        ),
      ],
    };
  }


  // =========================================================
  // TAX - PHYSICAL
  // =========================================================

  public taxInformation(
    holder: IPhysicalPerson
  ): ComparisonSectionDto {

    return {
      id: 'tax-information',
      title: 'Tax Information',
      sources: [this.coreSource],

      fields: [
        this.taxInformationList(
          holder.taxInformations ?? []
        ),

        this.checkBoxField(
          'us-entity',
          'US Person',
          holder.usPerson
        ),
      ],
    };
  }


  // =========================================================
  // TAX - MORAL
  // =========================================================

  public moralTaxInformation(
    holder: IMoralPerson
  ): ComparisonSectionDto {

    return {
      id: 'tax-information',
      title: 'Tax Information',
      sources: [this.coreSource],

      fields: [
        this.taxInformationList(
          holder.taxInformations ?? []
        ),

        this.textField(
          'fatcaStatus',
          'FATCA status of the company',
          holder.fatcaStatus
        ),

        this.textField(
          'crsStatus',
          'CRS Status of the company',
          holder.crsStatus
        ),

        this.checkBoxField(
          'us-entity',
          'US Entity',
          holder.usEntity
        ),
      ],
    };
  }


  // =========================================================
  // PROFESSIONAL FIELDS
  // =========================================================

  private professionalFields(
    holder: IPhysicalPerson
  ): readonly ComparisonFieldDto[] {

    const details = holder.professionalDetails;

    if (!details) {
      return [];
    }

    const fields: ComparisonFieldDto[] = [
      this.textField(
        'profession',
        'Profession',
        details.profession
      ),
    ];

    if (details.profession?.toLowerCase() === 'unemployed') {
      return fields;
    }

    fields.push(
      this.textField(
        'profession-status',
        'Profession status',
        details.status
      ),

      this.textField(
        'employer-name',
        'Employer Name',
        details.companyName
      ),

      this.countryField(
        'employer-country',
        'Employer Country',
        details.companyCountry
      ),

      this.textField(
        'industry-sector',
        'Industry Sector',
        details.sector
      ),
    );

    return fields;
  }


  // =========================================================
  // ADDRESS
  // =========================================================

  public addressGroup(
    key: string,
    label: string,
    address: IAddress | null | undefined
  ): ComparisonGroupFieldDto {

    return {
      key,
      label,
      kind: 'group',

      lines: [
        ['no', 'address'],
        ['postcode', 'town'],
        ['country'],
      ],

      fields: [
        this.textField(
          'no',
          'Number',
          address?.no
        ),

        this.textField(
          'address',
          'Street',
          address?.address
        ),

        this.textField(
          'postcode',
          'Post code',
          address?.postCode
        ),

        this.textField(
          'town',
          'Town',
          address?.town
        ),

        this.countryField(
          'country',
          'Country',
          address?.country
        ),
      ],
    };
  }


  // =========================================================
  // TAX INFORMATION LIST
  // =========================================================

  private taxInformationList(
    values: readonly ITaxInformation[]
  ): ComparisonListFieldDto {

    return {
      key: 'tax-information',
      label: 'Tax Information',
      kind: 'list',
      entryNoun: 'tax information',

      entryFields: [
        {
          key: 'tax-country',
          label: 'Tax Country',
          kind: 'select',
          editable: false,
          options: this.countryOptions,
        },

        {
          key: 'tin',
          label: 'TIN',
          kind: 'text',
          editable: true,
        },

        {
          key: 'tin-unavailable-reason',
          label: 'Reason if TIN Unavailable',
          kind: 'select',
          editable: true,
          options: this.taxUnavailableOptions,
        },
      ],

      values: {
        digital: [],
        kyc: [],

        core: values.map((tax: ITaxInformation) => ({
          key: tax.taxCountry,

          fields: [
            {
              key: 'tax-country',
              label: 'Tax Country',
              value: tax.taxCountry,
              kind: 'select',
              editable: false,
              options: this.countryOptions,
            },

            {
              key: 'tin',
              label: 'TIN',
              value: tax.taxNumber ?? null,
              kind: 'text',
              editable: true,
            },

            {
              key: 'tin-unavailable-reason',
              label: 'Reason if TIN Unavailable',
              value: tax.tinUnavailableReason ?? null,
              kind: 'select',
              editable: true,
              options: this.taxUnavailableOptions,
            },
          ],
        })),
      },
    };
  }


  // =========================================================
  // FIELD BUILDERS
  // =========================================================

  private textField(
    key: string,
    label: string,
    value: string | null | undefined
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


  private selectField(
    key: string,
    label: string,
    value: string | null | undefined,
    options: readonly ComparisonOption[]
  ): ComparisonScalarFieldDto {

    return {
      key,
      label,
      kind: 'select',
      options,

      values: {
        digital: null,
        kyc: null,
        core: this.normalize(value),
      },
    };
  }


  private countryField(
    key: string,
    label: string,
    value: string | null | undefined
  ): ComparisonScalarFieldDto {

    return this.selectField(
      key,
      label,
      value,
      this.countryOptions
    );
  }


  private checkBoxField(
    key: string,
    label: string,
    value: boolean | null | undefined
  ): ComparisonScalarFieldDto {

    return {
      key,
      label,
      kind: 'checkbox',

      values: {
        digital: null,
        kyc: null,
        core:
          value == null
            ? null
            : String(value),
      },
    };
  }


  private listField(
    key: string,
    label: string,
    entryNoun: string,
    values: readonly string[]
  ): ComparisonListFieldDto {

    return {
      key,
      label,
      kind: 'list',
      entryNoun,

      values: {
        digital: [],
        kyc: [],

        core: values.map(value => ({
          key: value,
          label:
            this.normalizeCountry(value)
            ?? value,

          value:
            this.normalizeCountry(value)
            ?? value,
        })),
      },
    };
  }


  // =========================================================
  // COUNTRIES
  // =========================================================

  private buildCountryOptions():
    readonly ComparisonOption[] {

    const regionNames =
      new Intl.DisplayNames(
        ['en'],
        { type: 'region' }
      );

    const codes = [
      'AT', 'BE', 'BG', 'HR', 'CY', 'CZ',
      'DK', 'EE', 'FI', 'FR', 'DE', 'GR',
      'HU', 'IE', 'IT', 'LV', 'LT', 'LU',
      'MT', 'NL', 'PL', 'PT', 'RO', 'SK',
      'SI', 'ES', 'SE', 'CH', 'GB', 'US',
      'CA',
    ];

    return codes.map(code => ({
      value: code,
      label: regionNames.of(code) ?? code,
    }));
  }


  public normalizeCountry(
    code: string | null | undefined
  ): string | null {

    if (!code) {
      return null;
    }

    try {
      const regionNames =
        new Intl.DisplayNames(
          ['en'],
          { type: 'region' }
        );

      return (
        regionNames.of(
          code.toUpperCase()
        ) ?? code
      );

    } catch {
      return code;
    }
  }


  // =========================================================
  // OTHER HELPERS
  // =========================================================

  private getNationalities(
    details?: INationalityDetails
  ): string[] {

    if (!details) {
      return [];
    }

    return [
      details.first?.country,
      details.second?.country,
      details.third?.country,
    ].filter(
      (country): country is string =>
        !!country
    );
  }


  public formatDate(
    value: Date | string | null | undefined
  ): string | null {

    if (!value) {
      return null;
    }

    const date = new Date(value);

    if (Number.isNaN(date.getTime())) {
      return null;
    }

    return new Intl.DateTimeFormat(
      'en-GB'
    ).format(date);
  }


  public normalize(
    value: string | null | undefined
  ): string | null {

    if (!value) {
      return null;
    }

    const normalized =
      value.trim();

    return normalized.length > 0
      ? normalized
      : null;
  }


  public emptySection(
    id: string,
    title: string
  ): ComparisonSectionDto {

    return {
      id,
      title,
      sources: [this.coreSource],
      fields: [],
    };
  }
}
