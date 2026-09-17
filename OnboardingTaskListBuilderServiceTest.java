private toBoolean(value: string | null | undefined): boolean {
  if (!value) {
    return false;
  }

  const normalized = value.trim().toLowerCase();

  return normalized === 'true'
    || normalized === 'yes'
    || normalized === 'us indicia detected';
}
