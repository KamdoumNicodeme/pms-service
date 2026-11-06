private getNationalityPosition(
  holder: IPhysicalPerson,
  country: string
): 'first' | 'second' | 'third' | null {
  if (holder.nationality?.first?.country === country) {
    return 'first';
  }

  if (holder.nationality?.second?.country === country) {
    return 'second';
  }

  if (holder.nationality?.third?.country === country) {
    return 'third';
  }

  return null;
}
