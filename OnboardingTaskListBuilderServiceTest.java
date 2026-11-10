export function buildAnnotations(
  values: Readonly<Record<ComparisonSourceId, string | null>>,
  retained: string | null,
  options: readonly ComparisonOption[] = []
): readonly SourceAnnotation[] {
  return COMPARISON_SOURCES
    .filter(source => shouldDisplaySourceValue(source.id, values, retained))
    .map(source => {
      const value = values[source.id];

      return {
        source: source.id,
        label: source.label,
        value: options.find(option => option.value === value)?.label ?? value,
        tone: computeTone(source.id, values),
      };
    });
}
