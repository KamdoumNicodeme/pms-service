private applyNationalityChange(
  client: IPhysicalPerson,
  id: string,
  resolution: Resolution
): void {

  const previousCountry: string =
    id.substring('nationalities:'.length);

  console.log(
    'NATIONALITY BEFORE:',
    structuredClone(client.nationality)
  );

  console.log('PREVIOUS COUNTRY:', previousCountry);
  console.log('NEW COUNTRY:', resolution.value);

  const nationalities: (NationalityInfo | null | undefined)[] = [
    client.nationality?.first,
    client.nationality?.second,
    client.nationality?.third
  ];

  const nationality: NationalityInfo | null | undefined =
    nationalities.find(
      (item: NationalityInfo | null | undefined) =>
        item?.country === previousCountry
    );

  if (!nationality) {
    console.warn(
      '[ClientProfilingChangeService] Nationality not found:',
      previousCountry
    );
    return;
  }

  const value: string | null =
    this.nullableStringValue(resolution.value);

  if (value !== null) {
    nationality.country = value;
  }

  console.log(
    'NATIONALITY AFTER:',
    structuredClone(client.nationality)
  );
}
