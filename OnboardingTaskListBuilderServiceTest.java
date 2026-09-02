export function defaultResolution(
  values: Record<ComparisonSourceId, string | null>,
  status: ComparisonStatus
): Resolution {

  const coreOnly =
    !isBlank(values.core) &&
    isBlank(values.digital) &&
    isBlank(values.kyc);

  if (coreOnly) {
    return {
      source: 'core',
      value: values.core,
      reason: '',
      comment: '',
      reviewed: true,
    };
  }

  return {
    source: 'kyc',
    value: values.kyc,
    reason: '',
    comment: '',
    reviewed: !needsAttention(status),
  };
}
