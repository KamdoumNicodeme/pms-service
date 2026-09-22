private toIsoDate(value: string | null): string | null {
  if (!value?.trim()) {
    return null;
  }

  // Valeur venant du date picker : dd/MM/yyyy
  const match = value.match(/^(\d{2})\/(\d{2})\/(\d{4})$/);

  if (match) {
    const [, day, month, year] = match;

    return `${year}-${month}-${day}T00:00:00Z`;
  }

  // Valeur déjà sous forme yyyy-MM-dd
  const isoDate = value.match(/^(\d{4})-(\d{2})-(\d{2})$/);

  if (isoDate) {
    return `${value}T00:00:00Z`;
  }

  // Valeur déjà ISO complète
  if (/^\d{4}-\d{2}-\d{2}T/.test(value)) {
    return value;
  }

  return null;
}
