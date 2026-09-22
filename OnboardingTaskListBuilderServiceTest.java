private toIsoDate(value: string | null): string | null {
  if (!value) {
    return null;
  }

  // Valeur venant de l'UI : dd/MM/yyyy
  const match = value.match(/^(\d{2})\/(\d{2})\/(\d{4})$/);

  if (match) {
    const [, day, month, year] = match;
    return `${year}-${month}-${day}T00:00:00Z`;
  }

  // Déjà au format ISO
  if (/^\d{4}-\d{2}-\d{2}T/.test(value)) {
    return value;
  }

  return value;
}
