private applyNationalityChange(
  client: IPhysicalPerson,
  sourceHolder: IPhysicalPerson,
  id: string,
  resolution: Resolution
): void {

  const parts = id.split(':');

  const previousCountry = parts[1];
  const field = parts[2] ?? 'country';

  if (!previousCountry) {
    return;
  }

  client.nationality ??=
    {} as NonNullable<IPhysicalPerson['nationality']>;

  // 1. On cherche d'abord dans le CCI
  let nationality =
    [
      client.nationality.first,
      client.nationality.second,
      client.nationality.third
    ].find(item => item?.country === previousCountry);

  // 2. Si elle n'est pas encore dans le CCI,
  // on la récupère depuis le holder source.
  if (!nationality) {

    const sourceNationality = sourceHolder.nationality;

    if (sourceNationality?.first?.country === previousCountry) {

      client.nationality.first =
        structuredClone(sourceNationality.first);

      nationality = client.nationality.first;

    } else if (sourceNationality?.second?.country === previousCountry) {

      client.nationality.second =
        structuredClone(sourceNationality.second);

      nationality = client.nationality.second;

    } else if (sourceNationality?.third?.country === previousCountry) {

      client.nationality.third =
        structuredClone(sourceNationality.third);

      nationality = client.nationality.third;
    }
  }

  if (!nationality) {
    console.warn(
      '[ClientProfilingChangeService] Nationality not found:',
      previousCountry
    );
    return;
  }

  const value = this.nullableStringValue(resolution.value);

  switch (field) {

    case 'country': {
      if (value !== null) {
        nationality.country = value;
      }
      break;
    }

    case 'date': {
      const date = this.toIsoDate(value);

      if (date) {
        nationality.fromDate = date;
      }
      break;
    }
  }
}
