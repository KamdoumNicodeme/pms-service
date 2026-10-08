readonly caseDocuments: Signal<ICaseDocument[]> = toSignal(
  combineLatest([
    toObservable(this.caseIdentifier),
    toObservable(this.documentRefresh)
  ]).pipe(

    filter(([identifier]) => identifier !== null),

    switchMap(([identifier]) =>
      this.#caseService.findAllDocuments(identifier!)
    )
  ),
  {
    initialValue: [] as ICaseDocument[]
  }
);
