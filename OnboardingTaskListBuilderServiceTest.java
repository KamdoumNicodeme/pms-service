static hasItalianMostRecentNationality(
  nationality: INationalityDetails | null | undefined
): boolean {
  if (!nationality) {
    return false;
  }

  const nationalities: NationalityInfo[] = [
    nationality.first,
    nationality.second,
    nationality.third
  ].filter(
    (item): item is NationalityInfo =>
      !!item?.country && !!item?.fromDate
  );

  if (!nationalities.length) {
    return false;
  }

  const mostRecentDate = Math.max(
    ...nationalities.map(item => new Date(item.fromDate).getTime())
  );

  return nationalities.some(
    item =>
      item.country === 'IT' &&
      new Date(item.fromDate).getTime() === mostRecentDate
  );
}
