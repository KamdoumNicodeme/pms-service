private getChangeReasonKey(
  parsedId: ChangeIdParser,
  id: string
): string {
  if (parsedId.prefix === 'nationalities') {
    return this.getNationalityReasonKey(parsedId);
  }

  return id;
}
