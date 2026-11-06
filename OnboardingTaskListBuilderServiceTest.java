private getManualNationalityPosition(
  client: IThirdParty,
  manualId: string
): string | null {
  if (!this.isPhysicalPerson(client)) {
    return null;
  }

  const manualIndex = Number(manualId.replace('manual-', '')) - 1;

  if (Number.isNaN(manualIndex) || manualIndex < 0) {
    return null;
  }

  const nationality = client.nationality;

  if (!nationality) {
    return null;
  }

  const positions: Array<{
    name: string;
    value: NationalityInfo | null | undefined;
  }> = [
    {
      name: 'first',
      value: nationality.first
    },
    {
      name: 'second',
      value: nationality.second
    },
    {
      name: 'third',
      value: nationality.third
    }
  ];

  const populatedPositions = positions.filter(
    position => position.value != null
  );

  return populatedPositions[manualIndex]?.name ?? null;
}
