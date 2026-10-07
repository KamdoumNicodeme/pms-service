private formatDate(date: Date | string | null | undefined): string | null {
  if (!date) {
    return null;
  }

  if (typeof date === 'string') {
    return date;
  }

  return date.toISOString().split('T')[0];
}
