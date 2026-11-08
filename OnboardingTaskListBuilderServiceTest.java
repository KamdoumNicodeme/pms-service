public getReasonOptions(key: string): Signal<ComparisonOption[]> {
  return computed((): ComparisonOption[] => {
    const allReferences = this.references();
    const references = allReferences[key] ?? [];

    console.log('ALL REFERENCES', allReferences);
    console.log('REQUESTED KEY', key);
    console.log('REFERENCES FOR KEY', references);

    return references.map(
      (ref: IReference): ComparisonOption => ({
        value: ref.value,
        label: ref.key,
      })
    );
  });
}
