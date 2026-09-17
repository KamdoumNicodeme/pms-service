constructor() {
  effect((): void => this.store.initialize(this.section()));

  effect((): void => this.store.setFilter(this.filter()));

  effect((): void => {
    this.countersChange.emit(this.store.counters());
  });

  effect((): void => {
    this.changesChange.emit(this.store.appliedChanges());
  });
}
