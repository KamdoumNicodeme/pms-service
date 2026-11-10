export function computeStatusWithoutPortal(
  values: Record<ComparisonSourceId, string | null>
): ComparisonStatus {
  const core = values.core;
  const snapshot = values.snapshot;

  if (isBlank(core) && isBlank(snapshot)) {
    return 'aligned';
  }

  if (!isBlank(core) && isBlank(snapshot)) {
    return 'aligned';
  }

  if (sameValue(core, snapshot)) {
    return 'aligned';
  }

  return 'drift';
}
