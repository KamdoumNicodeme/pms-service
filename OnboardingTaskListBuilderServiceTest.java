uploadDocument(
  caseId: string,
  request: UploadDocumentRequest,
  userTaskIdentifier?: number
): Observable<IDocumentAttachInformation> {

  const formData = new FormData();

  formData.append('file', request.file);
  formData.append('type', request.documentType);

  const allowedTypes = ['STRING', 'DATE', 'DATETIME', 'NUMBER'];

  const metadata = request.metadata.map(item => {
    if (!allowedTypes.includes(item.type)) {
      throw new Error(`Invalid metadata type for ${item.key}: ${item.type}`);
    }

    return {
      key: item.key,
      type: item.type,
      value: item.value
    };
  });

  if (metadata.length > 0) {
    formData.append('metadata', JSON.stringify(metadata));
  }

  if (userTaskIdentifier != null) {
    formData.append('userTaskIdentifier', userTaskIdentifier.toString());
  }

  return this.#httpClient.post<IDocumentAttachInformation>(
    `${environment.apiUrls.get('caseManagement')}/cases/${caseId}/documents`,
    formData
  );
}
