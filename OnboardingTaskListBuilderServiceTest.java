private getManualNationalityIndex(
  parsedId: ChangeIdParser
): number {
  const identifier: string = parsedId.parts[1];

  const match: RegExpMatchArray | null =
    identifier.match(/^manual-(\d+)$/);

  if (!match) {
    return 0;
  }

  return Math.max(Number(match[1]) - 1, 0);
}

private getAvailableNationalityPositions(
  holder: IPhysicalPerson
): ('first' | 'second' | 'third')[] {
  const positions:
    ('first' | 'second' | 'third')[] = [];

  if (!holder.nationality?.first) {
    positions.push('first');
  }

  if (!holder.nationality?.second) {
    positions.push('second');
  }

  if (!holder.nationality?.third) {
    positions.push('third');
  }

  return positions;
}
