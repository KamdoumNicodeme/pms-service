onDraftChange(value: string): void {
  this.draft.set(value);
  this.addError.set(null);
}
