private applyManualNationalityChange(
  client: IPhysicalPerson,
  id: string,
  resolution: Resolution
): void {

  const country = this.nullableStringValue(resolution.value);

  if (!country) {
    return;
  }

  if (!client.nationality) {
    client.nationality = {};
  }

  // Eviter les doublons
  const alreadyExists = [
    client.nationality.first,
    client.nationality.second,
    client.nationality.third
  ].some(
    nationality => nationality?.country === country
  );

  if (alreadyExists) {
    return;
  }

  if (!client.nationality.first) {
    client.nationality.first = { country };
    return;
  }

  if (!client.nationality.second) {
    client.nationality.second = { country };
    return;
  }

  if (!client.nationality.third) {
    client.nationality.third = { country };
  }
}
