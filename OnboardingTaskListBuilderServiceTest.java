readonly caseDocuments: Signal<ICaseDocument[]> = toSignal(
  combineLatest([
    toObservable(this.caseIdentifier),
    toObservable(this.documentsRefresh)
  ]).pipe(
    filter(
      ([identifier]): identifier is [string, number] =>
        !!identifier
    ),
    switchMap(([identifier]) =>
      this.#caseService.findAllDocuments(identifier)
    )
  ),
  {
    initialValue: [] as ICaseDocument[]
  }
);
