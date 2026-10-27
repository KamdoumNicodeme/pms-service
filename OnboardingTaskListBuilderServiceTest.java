uploadDocument(
  caseId: string,
  request: UploadDocumentRequest,
  userTaskIdentifier?: number
): Observable<IDocumentAttachInformation> {
  const allowedTypes = new Set([
    'STRING',
    'DATE',
    'DATETIME',
    'NUMBER',
  ]);

  const metadata = request.metadata.map(item => {
    if (!allowedTypes.has(item.type)) {
      throw new Error(
        `Invalid metadata type for ${item.key}: ${item.type}`
      );
    }

    return {
      key: item.key,
      type: item.type,
      value: item.value,
    };
  });

  const additionalProps = new Map<string, SafeAny>();

  additionalProps.set(
    'type',
    request.documentType ?? ''
  );

  if (metadata.length > 0) {
    additionalProps.set(
      'metadata',
      JSON.stringify(metadata)
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
    `${environment.apiUrls.get('caseManagement')}/cases/${caseId}/documents`,
    formData
  );
}
