submitStructured(): void {
  const definitions =
    this.row().entryFields ?? [];

  const fields = definitions.map(definition => ({
    key: definition.key,
    value:
      this.fieldValue(definition.key).trim() || null,
  }));

  this.addStructuredEntry.emit({
    fields,
  });

  this.cancelAdd();
}
