private applyNationalityChange(
  client: IPhysicalPerson,
  id: string,
  resolution: Resolution
): void {

  const previousCountry =
    id.substring('nationalities:'.length);

  const nationalities = [
    client.nationality?.first,
    client.nationality?.second,
    client.nationality?.third
  ];

  const nationality =
    nationalities.find(
      item => item?.country === previousCountry
    );

  if (!nationality) {

    console.warn(
      '[ClientProfilingChangeService] Nationality not found:',
      previousCountry
    );

    return;
  }

  const value =
    this.nullableStringValue(
      resolution.value
    );

  if (value !== null) {
    nationality.country = value;
  }
}
