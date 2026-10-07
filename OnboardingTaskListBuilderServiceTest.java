readonly canSubmitStructured = computed((): boolean => {
  const definitions = this.row().entryFields ?? [];

  if (definitions.length === 0) {
    return false;
  }

  return definitions
    .filter(definition => definition.editable)
    .every(definition => {
      const value = this.fieldValue(definition.key);
      return value.trim() !== '';
    });
});
