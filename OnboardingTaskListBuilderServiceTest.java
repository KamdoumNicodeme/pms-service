private applyChangeReasons(
  result: IChangeClientInformation,
  sourceHolder: IThirdParty,
  changes: ReadonlyMap<string, Resolution>
): void {
  result.changeReasons ??= {};

  const clientReasons: Record<string, IChangeReason> =
    result.changeReasons[sourceHolder.thirdPartyId] ?? {};

  changes.forEach(
    (resolution: Resolution, id: string): void => {
      if (!resolution.reason && !resolution.comment) {
        return;
      }

      const key = this.getReasonKey(
        id,
        sourceHolder,
        changes
      );

      if (!key) {
        return;
      }

      clientReasons[key] = {
        reason: resolution.reason,
        comment: resolution.comment
      };
    }
  );

  if (Object.keys(clientReasons).length > 0) {
    result.changeReasons[sourceHolder.thirdPartyId] =
      clientReasons;
  }
}


private getReasonKey(
  id: string,
  sourceHolder: IThirdParty,
  changes: ReadonlyMap<string, Resolution>
): string {
  const parsedId: ChangeIdParser =
    ChangeIdParser.parse(id);

  if (parsedId.prefix === 'nationalities') {
    return this.getNationalityReasonKey(
      parsedId,
      sourceHolder,
      changes
    );
  }

  return parsedId.prefix;
}

private getNationalityReasonKey(
  parsedId: ChangeIdParser,
  sourceHolder: IThirdParty,
  changes: ReadonlyMap<string, Resolution>
): string {
  if (!this.isPhysicalPerson(sourceHolder)) {
    return 'nationality';
  }

  const entryId = parsedId.parts[1];

  if (!entryId) {
    return 'nationality';
  }

  const nationalities = this.getNationalities(sourceHolder);

  if (entryId.startsWith('manual-')) {
    const country =
      changes.get(
        `nationalities:${entryId}:country`
      )?.value;

    if (!country) {
      return 'nationality';
    }

    const existingCount = nationalities.filter(
      nationality => !!nationality?.country
    ).length;

    const manualIndex = Number(
      entryId.replace('manual-', '')
    );

    const index =
      existingCount + manualIndex - 1;

    return this.getNationalityReasonKeyByIndex(index);
  }

  const index = nationalities.findIndex(
    nationality =>
      nationality?.country === entryId
  );

  return this.getNationalityReasonKeyByIndex(index);
}

private getNationalityReasonKeyByIndex(
  index: number
): string {
  switch (index) {
    case 0:
      return 'nationality-first';

    case 1:
      return 'nationality-second';

    case 2:
      return 'nationality-third';

    default:
      return 'nationality';
  }
}

private getNationalities(
  client: IPhysicalPerson
): Array<INationalityDetails | null | undefined> {
  return [
    client.nationality?.first,
    client.nationality?.second,
    client.nationality?.third
  ];
}
