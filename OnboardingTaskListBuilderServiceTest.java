readonly fieldDefinitions =
  input<readonly ComparisonFieldEntryDefinition[]>([]);

protected readonly structuredDraft =
  linkedSignal<Record<string, string>>(() => {

    const fields =
      this.entry().fields?.core ?? [];

    return Object.fromEntries(
      fields.map(field => [
        field.key,
        field.value ?? ''
      ])
    );
  });

protected draftValue(
  key: string
): string {
  return this.structuredDraft()[key] ?? '';
}

protected updateDraftValue(
  key: string,
  value: string
): void {

  this.structuredDraft.update(current => ({
    ...current,
    [key]: value
  }));
}

protected definitionFor(
  key: string
): ComparisonFieldEntryDefinition | undefined {

  return this.fieldDefinitions()
    .find(definition => definition.key === key);
}

protected applyStructured(): void {
  console.log(
    'Structured tax information',
    this.structuredDraft()
  );
}
