searchReferences(
  domain: string,
  search: string,
  page = 0,
  size = 20
): Observable<IPageable<IReference>> {
  return this.#service.searchReferences(domain, search, page, size).pipe(
    catchError((error: unknown) => {
      console.error('Failed to search references', error);
      return of(EMPTY_PAGEABLE as IPageable<IReference>);
    })
  );
}
