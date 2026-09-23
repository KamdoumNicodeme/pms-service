case 'identity-documents':
  if (this.isPhysicalPerson(digitalHolder)) {
    return of(
      this.coreUtils.idDocument(
        digitalHolder,
        kycHolder && this.isPhysicalPerson(kycHolder)
          ? kycHolder
          : null,
        coreHolder && this.isPhysicalPerson(coreHolder)
          ? coreHolder
          : null,
        this.idTypeOptions()
      )
    );
  }

  return of(
    this.coreUtils.emptySection({
      id: 'identity-documents',
      title: 'Identity Documents'
    })
  );
