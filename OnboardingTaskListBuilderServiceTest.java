private readonly policySectionChanges:
  WritableSignal<
    ReadonlyMap<string, ReadonlyMap<string, Resolution>>
  > = signal<
    ReadonlyMap<string, ReadonlyMap<string, Resolution>>
  >(new Map());
