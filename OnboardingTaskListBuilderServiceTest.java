replacementDocument(
  caseId: string,
  documentId: string,
  request: EditDocumentRequest,
  userTaskIdentifier?: number
): Observable<IDocumentAttachInformation> {
  const additionalProps = new Map<string, SafeAny>();

  additionalProps.set('name', request.name);
  additionalProps.set('type', request.type);

  request.metadata.forEach((metadata, index) => {
    additionalProps.set(
      `metadata[${index}].key`,
      metadata.key
    );

    additionalProps.set(
      `metadata[${index}].type`,
      metadata.type
    );

    additionalProps.set(
      `metadata[${index}].value`,
      metadata.value
    );
  });

  if (request.status) {
    additionalProps.set(
      'status',
      request.status
    );
  }

  if (userTaskIdentifier != null) {
    additionalProps.set(
      'userTaskIdentifier',
      userTaskIdentifier.toString()
    );
  }

  const formData = toMultipartFormData(
    request.file,
    additionalProps
  );

  return this.#httpClient.post<IDocumentAttachInformation>(
    `${environment.apiUrls.get('caseManagement')}/cases/${caseId}/documents/${documentId}/replace`,
    formData
  );
}
