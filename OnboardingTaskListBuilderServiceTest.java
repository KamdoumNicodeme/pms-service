private applyManualNationalityChange(
  client: IPhysicalPerson,
  id: string,
  resolution: Resolution
): void {

  if (!id.startsWith('nationalities:manual-')) {
    return;
  }

  const country = this.nullableStringValue(resolution.value);

  if (!country) {
    return;
  }

  // Initialise nationality si nécessaire
  if (!client.nationality) {
    client.nationality = {
      first: undefined,
      second: undefined,
      third: undefined
    };
  }

  // ------------------------------------------
  // Ne pas ajouter un doublon
  // ------------------------------------------

  const alreadyExists =
    client.nationality.first?.country === country ||
    client.nationality.second?.country === country ||
    client.nationality.third?.country === country;

  if (alreadyExists) {
    return;
  }

  // ------------------------------------------
  // FIRST
  // ------------------------------------------

  if (!client.nationality.first?.country) {
    client.nationality.first = {
      ...(client.nationality.first ?? {}),
      country
    };

    return;
  }

  // ------------------------------------------
  // SECOND
  // ------------------------------------------

  if (!client.nationality.second?.country) {
    client.nationality.second = {
      ...(client.nationality.second ?? {}),
      country
    };

    return;
  }

  // ------------------------------------------
  // THIRD
  // ------------------------------------------

  if (!client.nationality.third?.country) {
    client.nationality.third = {
      ...(client.nationality.third ?? {}),
      country
    };

    return;
  }

  console.warn(
    '[ClientProfilingChangeService] Maximum of 3 nationalities reached',
    country
  );
}
