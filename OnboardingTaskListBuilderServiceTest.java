private getNationalities(details?: INationalityDetails): string[] {
  if (!details) {
    return [];
  }

  return [
    details.first?.country,
    details.second?.country,
    details.third?.country,
  ].filter((country): country is string => !!country);
}
