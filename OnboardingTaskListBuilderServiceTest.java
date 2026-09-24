getDocuments(
  holderId: string,
  sectionId: string
): Observable<readonly ClientDocument[]> {

  console.log('[Mock documents]', {
    holderId,
    sectionId
  });

  const documents = DOCUMENTS.filter(
    document =>
      document.thirdPartyId === holderId &&
      document.sectionId === sectionId
  );

  return of(documents).pipe(delay(120));
}
