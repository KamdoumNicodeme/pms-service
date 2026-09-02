function entryFallback(
  values: Record<ComparisonSourceId, string | null>,
  status: ComparisonStatus
): Resolution {

  const droppedByClient = status === 'removed';

  const coreOnly =
    !isBlank(values.core) &&
    isBlank(values.kyc) &&
    isBlank(values.digital);

  if (coreOnly) {
    return {
      source: 'core',
      value: values.core,
      reason: '',
      comment: '',
      reviewed: true,
    };
  }

  if (droppedByClient) {
    return {
      source: 'kyc',
      value: null,
      reason: '',
      comment: '',
      reviewed: false,
    };
  }

  return defaultResolution(values, status);
}
