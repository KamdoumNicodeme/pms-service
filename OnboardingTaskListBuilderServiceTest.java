private applyManualNationalityChange(
  client: IPhysicalPerson,
  id: string,
  resolution: Resolution
): void {

  const country: string = this.stringValue(resolution.value);

  if (!country) {
    return;
  }

  if (!client.nationality) {
    return;
  }

  const nationality: NationalityInfo = {
    country
  };

  // First reste la nationalité provenant du Core
  // On remplit d'abord second, puis third.

  if (!client.nationality.second?.country) {
    client.nationality.second = nationality;
    return;
  }

  if (!client.nationality.third?.country) {
    client.nationality.third = nationality;
  }
}
