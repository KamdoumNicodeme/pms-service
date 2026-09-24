protected documentsForHolder(
  thirdPartyId: string
): readonly ICaseDocument[] {

  const allDocuments = this.caseDocuments();

  console.log('[CCI] ALL DOCUMENTS', allDocuments);
  console.log('[CCI] HOLDER ID', thirdPartyId);

  const documents = allDocuments.filter(
    document =>
      document.metadata?.['thirdPartyId'] === thirdPartyId
  );

  console.log('[CCI] HOLDER DOCUMENTS', documents);

  return documents;
}
