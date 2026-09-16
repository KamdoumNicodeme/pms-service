readonly maxEntriesReached = computed(() => {
  if (this.row().key === 'nationalities') {
    return this.row().entries.length >= 3;
  }

  return false;
});

@if (!maxEntriesReached()) {
  <button
    type="button"
    class="list__add__trigger"
    (click)="openSimpleAdd()"
  >
    <nz-icon nzType="plus" />
    Add {{ row().entryNoun }}
  </button>
}

submit(): void {
  const value = this.draft().trim();

  if (value === '') {
    return;
  }

  if (this.validationError() !== '') {
    return;
  }

  if (
    this.row().key === 'nationalities' &&
    this.row().entries.length >= 3
  ) {
    return;
  }

  this.addEntry.emit(value);

  this.cancelAdd();
}
