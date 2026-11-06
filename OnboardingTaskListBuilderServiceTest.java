private getManualNationalityPosition(
  client: IPhysicalPerson,
  sourceHolder: IPhysicalPerson,
  parsedId: ChangeIdParser
): 'first' | 'second' | 'third' | null {
  const manualIndex: number =
    this.getManualNationalityIndex(parsedId);

  const availablePositions:
    ('first' | 'second' | 'third')[] =
      this.getAvailableNationalityPositions(sourceHolder);

  return availablePositions[manualIndex] ?? null;
}
