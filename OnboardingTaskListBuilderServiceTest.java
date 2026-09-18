updateChangeClientInformation(
  caseBusinessIdentifier: string,
  request: IChangeClientInformation,
  status?: string
): Observable<void> {
  const params: { [key: string]: string } =
    status ? { status } : {};

  return this.httpClient.put<void>(
    `${environment.apiUrls.get('changeOfStrategy')}/cases/client-profiling/${caseBusinessIdentifier}/tasks/change-client-information`,
    request,
    { params }
  );
}
