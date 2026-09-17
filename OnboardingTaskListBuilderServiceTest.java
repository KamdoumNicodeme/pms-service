private removeManualNationality(
  client: IPhysicalPerson,
  id: string
): void {
  if (!client.nationality) {
    return;
  }

  const manualIndex = Number(id.replace('nationalities:manual-', ''));

  if (manualIndex === 1) {
    client.nationality.second = client.nationality.third;
    client.nationality.third = {
      country: null,
      fromDate: null
    };
  }

  if (manualIndex === 2) {
    client.nationality.third = {
      country: null,
      fromDate: null
    };
  }
}
