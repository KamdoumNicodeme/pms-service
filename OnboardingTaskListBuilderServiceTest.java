private getReasonKey(
  id: string,
  sourceHolder: IThirdParty,
  changes: ReadonlyMap<string, Resolution>
): string {
  const parsedId = ChangeIdParser.parse(id);

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
  const entryId = parsedId.parts[1];

  let country = entryId;

  if (entryId.startsWith('manual-')) {
    country =
      changes.get(`nationalities:${entryId}:country`)?.value ?? '';
  }

  const nationalities = this.getNationalities(sourceHolder);

  let index = nationalities.findIndex(
    nationality => nationality.country === country
  );

  if (index === -1 && entryId.startsWith('manual-')) {
    index =
      nationalities.length +
      Number(entryId.replace('manual-', '')) -
      1;
  }

  return this.nationalityReasonKey(index);
}
