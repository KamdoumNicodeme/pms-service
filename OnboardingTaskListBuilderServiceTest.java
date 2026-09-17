private applyManualNationalityChange(
  client: IPhysicalPerson,
  id: string,
  resolution: Resolution
): void {
  const country = this.stringValue(resolution.value);

  if (!country) {
    return;
  }

  const manualIndex = Number(
    id.replace('nationalities:manual-', '')
  );

  if (!client.nationality) {
    return;
  }

  const nationality: NationalityInfo = {
    country
  };

  if (manualIndex === 1) {
    client.nationality.second = nationality;
    return;
  }

  if (manualIndex === 2) {
    client.nationality.third = nationality;
  }
}
