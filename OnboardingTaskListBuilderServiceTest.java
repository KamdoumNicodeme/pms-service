private applyNationalityChange(
  client: IPhysicalPerson,
  id: string,
  resolution: Resolution
): void {

  const parts = id.split(':');

  // nationalities:AF
  // nationalities:AF:country
  // nationalities:AF:date

  const previousCountry = parts[1];
  const field = parts[2] ?? 'country';

  if (!previousCountry) {
    return;
  }

  const nationalities = [
    client.nationality?.first,
    client.nationality?.second,
    client.nationality?.third,
  ];

  const nationality = nationalities.find(
    item => item?.country === previousCountry
  );

  if (!nationality) {
    console.warn(
      '[ClientProfilingChangeService] Nationality not found:',
      previousCountry
    );
    return;
  }

  const value = this.nullableStringValue(resolution.value);

  switch (field) {

    case 'country':
      if (value !== null) {
        nationality.country = value;
      }
      break;

    case 'date':
      nationality.date = value;
      break;
  }
}
