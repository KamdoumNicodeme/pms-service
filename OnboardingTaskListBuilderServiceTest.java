private professionalFields(
  holder: IPhysicalPerson
): readonly ComparisonFieldDto[] {

  const details = holder.professionalDetails;

  if (!details) {
    return [];
  }

  const fields: ComparisonFieldDto[] = [
    this.scalarField(
      'profession',
      'Profession',
      details.profession
    )
  ];

  if (details.profession?.toLowerCase() === 'unemployed') {
    return fields;
  }

  fields.push(
    this.scalarField(
      'profession-status',
      'Profession status',
      details.status
    ),

    this.scalarField(
      'employer-name',
      'Employer Name',
      details.companyName
    ),

    this.scalarField(
      'employer-country',
      'Employer Country',
      this.normalizeCountry(details.companyCountry)
    ),

    this.scalarField(
      'industry-sector',
      'Industry Sector',
      details.sector
    )
  );

  return fields;
}
