protected readonly refreshDocuments = (): void => {
  console.log('🔥 REFRESH DOCUMENTS REQUESTED');

  this.documentRefresh.update(value => {
    console.log('🔥 documentRefresh', value, '->', value + 1);
    return value + 1;
  });
};

readonly caseDocuments: Signal<ICaseDocument[]> = toSignal(
  combineLatest([
    toObservable(this.caseIdentifier),
    toObservable(this.documentRefresh)
  ]).pipe(

    filter(([identifier]) => identifier !== null),

    switchMap(([identifier, refresh]) => {
      console.log(
        '🔥 RELOAD DOCUMENTS',
        identifier,
        'refresh =',
        refresh
      );

      return this.#caseService.findAllDocuments(identifier!);
    })
  ),
  {
    initialValue: [] as ICaseDocument[]
  }
);
