export function defaultResolution(
  values: Record<ComparisonSourceId, string | null>,
  status: ComparisonStatus
): Resolution {

  // KYC = ChangeClientInformation.
  // Si une valeur KYC existe, c'est la valeur de travail déjà sauvegardée.
  if (!isBlank(values.kyc)) {
    return {
      source: 'kyc',
      value: values.kyc,
      reason: '',
      comment: '',
      reviewed: !needsAttention(status),
    };
  }

  // Si aucune valeur KYC mais une valeur Digital existe.
  if (!isBlank(values.digital)) {
    return {
      source: 'digital',
      value: values.digital,
      reason: '',
      comment: '',
      reviewed: !needsAttention(status),
    };
  }

  // Sinon on conserve la valeur Core.
  return {
    source: 'core',
    value: values.core,
    reason: '',
    comment: '',
    reviewed: true,
  };
}
