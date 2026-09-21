private generalInformation(
  coreHolder: IThirdParty,
  digitalHolder: IThirdParty | null,
  kycHolder: IThirdParty | null
): ComparisonSectionDto {

  if (this.isPhysicalPerson(coreHolder)) {

    const digitalPhysical =
      digitalHolder && this.isPhysicalPerson(digitalHolder)
        ? digitalHolder
        : null;

    const kycPhysical =
      kycHolder && this.isPhysicalPerson(kycHolder)
        ? kycHolder
        : null;

    return this.coreUtils.physicalPersonGeneralInformation(
      coreHolder,
      digitalPhysical,
      kycPhysical,
      this.isControllingPerson(coreHolder),
      this.countryOptions(),
      this.professionOptions(),
      this.industrySectorOptions(),
      this.statusMaritalOptions(),
      this.professionalStatusOptions()
    );
  }

  if (this.isMoralPerson(coreHolder)) {

    const digitalMoral =
      digitalHolder && this.isMoralPerson(digitalHolder)
        ? digitalHolder
        : null;

    const kycMoral =
      kycHolder && this.isMoralPerson(kycHolder)
        ? kycHolder
        : null;

    return this.coreUtils.moralPersonGeneralInformation(
      coreHolder,
      digitalMoral,
      kycMoral,
      this.industrySectorOptions()
    );
  }

  return this.coreUtils.emptySection(
    'general-information',
    'General information'
  );
}
