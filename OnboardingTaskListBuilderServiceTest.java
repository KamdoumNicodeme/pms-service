searchReferences(
  domain: string,
  search: string,
  page = 0,
  size = 20
): Observable<IPageable<IReference>> {
  return this.#httpClient.get<IPageable<IReference>>(
    `${environment.apiUrls.get('caseManagement')}/references/${encodeURIComponent(domain)}/search`,
    {
      params: {
        search,
        page,
        size
      }
    }
  );
}
