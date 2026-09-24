protected readonly documents = computed((): readonly ICaseDocument[] => {
  const sectionId = this.sectionId();

  return this.caseDocuments().filter(document =>
    this.belongsToSection(document, sectionId)
  );
});

private belongsToSection(
  document: ICaseDocument,
  sectionId: string
): boolean {

  switch (sectionId) {
    case 'contact-details':
      return document.type === 'PROOF_OF_RESIDENCE';

    case 'identity-documents':
      return document.type === 'ID_DOCUMENT';

    case 'tax-information':
      return document.type === 'AEOI_SELF_CERTIFICATION'
          || document.type === 'W9';

    default:
      return false;
  }
}
