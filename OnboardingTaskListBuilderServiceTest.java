private professionalFields(
  holder: IPhysicalPerson
): readonly ComparisonFieldDto[] {

  const professionalDetails = holder.professionalDetails;

  if (!professionalDetails) {
    return [];
  }

  const fields: ComparisonFieldDto[] = [
    this.selectField(
      'profession',
      'Profession',
      professionalDetails.profession,
      professionOptions
    ),
  ];

  const unemployed =
    professionalDetails.profession === 'UNEMPLOYED';

  if (!unemployed) {
    fields.push(
      this.selectField(
        'profession-status',
        'Profession status',
        professionalDetails.status,
        professionStatusOptions
      ),

      this.scalarField(
        'employer-name',
        'Employer Name',
        professionalDetails.companyName
      ),

      this.selectField(
        'employer-country',
        'Employer Country',
        professionalDetails.companyCountry,
        countryOptions
      ),

      this.selectField(
        'industry-sector',
        'Industry Sector',
        professionalDetails.sector,
        industrySectorOptions
      )
    );
  }

  return fields;
}
