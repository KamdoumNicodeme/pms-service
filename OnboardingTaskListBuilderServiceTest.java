private applyNationalityChange(
  client: IPhysicalPerson,
  sourceHolder: IPhysicalPerson,
  parsedId: ChangedIdParser,
  resolution: Resolution
): void {
  const position = parsedId.subType as 'first' | 'second' | 'third';
  const field = parsedId.field ?? 'country';

  if (!position || !['first', 'second', 'third'].includes(position)) {
    return;
  }

  client.nationality ??= {} as NonNullable<IPhysicalPerson['nationality']>;

  if (!client.nationality[position]) {
    const sourceNationality = sourceHolder.nationality?.[position];

    if (!sourceNationality) {
      return;
    }

    client.nationality[position] = structuredClone(sourceNationality);
  }

  const nationality = client.nationality[position];

  if (!nationality) {
    return;
  }

  const value = this.nullableStringValue(resolution.value);

  if (field === 'country' && value !== null) {
    nationality.country = value;
  }

  if (field === 'date') {
    const date = this.toIsoDate(value);

    if (date) {
      nationality.fromDate = date;
    }
  }
}
