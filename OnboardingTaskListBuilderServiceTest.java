readonly searchItalianBirthTownCodes = (
  search: string,
  page = 0,
  size = 20
): Observable<IPageable<IReference>> =>
  this.#referenceService.searchReferences(
    'IT_BIRTH_CODE',
    search,
    page,
    size
  );
