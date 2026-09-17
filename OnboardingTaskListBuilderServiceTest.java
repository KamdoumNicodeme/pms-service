case 'nationalities':
  this.applyNationalitiesChange(client, resolution);
  break;

private applyNationalitiesChange(
  client: IPhysicalPerson,
  resolution: Resolution
): void {

  const values: string[] = Array.isArray(resolution.value)
    ? resolution.value
    : resolution.value
      ? [resolution.value]
      : [];

  client.nationality = {
    ...client.nationality,

    first: {
      ...client.nationality?.first,
      country: values[0] ?? null,
    },

    second: {
      ...client.nationality?.second,
      country: values[1] ?? null,
    },

    third: {
      ...client.nationality?.third,
      country: values[2] ?? null,
    },
  };
}
