function mentions(
  source: ComparisonSourceId,
  values: Readonly<Record<ComparisonSourceId, string | null>>,
  retained: string | null
): boolean {

  const sourceValue = values[source];

  if (isBlank(sourceValue)) {
    return false;
  }

  // Si la valeur affichée correspond à UNE des vraies sources,
  // on ne répète pas cette source en annotation.
  //
  // MAIS si la valeur retenue est manuelle (elle ne correspond
  // à aucune source), on conserve toutes les sources disponibles.
  const retainedComesFromSource =
    sameValue(retained, values.digital) ||
    sameValue(retained, values.kyc) ||
    sameValue(retained, values.core);

  if (retainedComesFromSource && sameValue(sourceValue, retained)) {
    return false;
  }

  return true;
}
