private applyManualNationalityChange(
  client: IPhysicalPerson,
  id: string,
  resolution: Resolution
): void {
  const value = this.stringValue(resolution.value);

  if (!value) {
    return;
  }

  const manualIndex = Number(id.replace('nationalities:manual-', ''));

  if (!client.nationality) {
    client.nationality = {
      first: { country: null, fromDate: null },
      second: { country: null, fromDate: null },
      third: { country: null, fromDate: null }
    };
  }

  if (manualIndex === 1) {
    client.nationality.second = {
      ...client.nationality.second,
      country: value
    };
  }

  if (manualIndex === 2) {
    client.nationality.third = {
      ...client.nationality.third,
      country: value
    };
  }
}
