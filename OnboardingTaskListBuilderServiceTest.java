export function computeEntryStatus(
  values: Record<ComparisonSourceId, string | null>
): ComparisonStatus {

  const inDigital = !isBlank(values.digital);
  const inKyc = !isBlank(values.kyc);
  const inCore = !isBlank(values.core);

  // Core uniquement :
  // pour le moment on affiche simplement la donnée existante.
  if (inCore && !inDigital && !inKyc) {
    return 'aligned';
  }

  // Nouvelle valeur saisie côté KYC,
  // absente de Digital et Core.
  if (!inDigital && inKyc && !inCore) {
    return 'added';
  }

  // Valeur présente côté Digital mais non retenue côté KYC.
  if (inDigital && !inKyc) {
    return 'removed';
  }

  return computeStatus(values);
}
