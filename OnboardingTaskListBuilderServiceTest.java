private generalInformation(
  snapshotHolder: IThirdParty,
  portalHolder: IThirdParty | null,
  coreHolder: IThirdParty | null,
  clipHolder: IThirdParty | null
): ComparisonSectionDto {
  if (this.isPhysicalPerson(snapshotHolder)) {
    if (
      clipHolder !== null &&
      !this.isPhysicalPerson(clipHolder)
    ) {
      throw new Error('clipHolder must be a physical person');
    }

    const portalPhysical =
      portalHolder && this.isPhysicalPerson(portalHolder)
        ? portalHolder
        : null;

    const corePhysical =
      coreHolder && this.isPhysicalPerson(coreHolder)
        ? coreHolder
        : null;

    return this.coreUtils.physicalPersonGeneralInformation(
      snapshotHolder,
      portalPhysical,
      corePhysical,
      clipHolder,
      this.countryOptions(),
      this.professionOptions(),
      this.industrySectorOptions(),
      this.statusMaritalOptions(),
      this.professionalStatusOptions(),
      this.isControllingPerson(snapshotHolder)
    );
  }

  if (this.isMoralPerson(snapshotHolder)) {
    if (
      clipHolder !== null &&
      !this.isMoralPerson(clipHolder)
    ) {
      throw new Error('clipHolder must be a moral person');
    }

    const portalMoral =
      portalHolder && this.isMoralPerson(portalHolder)
        ? portalHolder
        : null;

    const coreMoral =
      coreHolder && this.isMoralPerson(coreHolder)
        ? coreHolder
        : null;

    return this.coreUtils.moralPersonGeneralInformation(
      snapshotHolder,
      portalMoral,
      coreMoral,
      clipHolder,
      this.industrySectorOptions()
    );
  }

  return this.coreUtils.emptySection(
    'general-information',
    'General Information'
  );
}
