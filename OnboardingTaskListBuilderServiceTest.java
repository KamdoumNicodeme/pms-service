protected readonly documentTypes = toSignal(
  toObservable(this.getDocumentConfigType).pipe(
    switchMap(getDocumentConfigType =>
      getDocumentConfigType()
    )
  ),
  {
    initialValue: [] as DocumentTypeConfiguration[]
  }
);

protected readonly metadataConfiguration = toSignal(
  toObservable(this.getDocumentMetadata).pipe(
    switchMap(getDocumentMetadata =>
      getDocumentMetadata()
    )
  ),
  {
    initialValue: {} as Record<string, MetadataConfiguration[]>
  }
);
