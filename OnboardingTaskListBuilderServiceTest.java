private generalInformation(
  snapshotHolder: IThirdParty,
  portalHolder: IThirdParty | null,
  coreHolder: IThirdParty | null,
  clipHolder: IThirdParty | null
): ComparisonSectionDto {

  if (this.isPhysicalPerson(snapshotHolder)) {
    if (clipHolder !== null && !this.isPhysicalPerson(clipHolder)) {
      throw new Error('clipHolder must be a physical person');
    }

    const portalPhysical: IPhysicalPerson | null =
      portalHolder && this.isPhysicalPerson(portalHolder)
        ? portalHolder
        : null;

    const corePhysical: IPhysicalPerson | null =
      coreHolder && this.isPhysicalPerson(coreHolder)
        ? coreHolder
        : null;

    const clipPhysical: IPhysicalPerson | null =
      clipHolder && this.isPhysicalPerson(clipHolder)
        ? clipHolder
        : null;

    return this.coreUtils.physicalPersonGeneralInformation(
      snapshotHolder,
      portalPhysical,
      corePhysical,
      clipPhysical,
      this.countryOptions(),
      this.professionOptions(),
      this.industrySectorOptions(),
      this.statusMaritalOptions(),
      this.professionalStatusOptions(),
      this.isControllingPerson(snapshotHolder)
    );
  }

  // moral person...
}
