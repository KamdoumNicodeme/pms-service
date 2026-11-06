private applyChangeReasons(
  result: IChangeClientInformation,
  thirdPartyId: string,
  changes: ReadonlyMap<string, Resolution>
): void {
  result.changeReasons ??= {};

  const clientReasons: Record<string, IChangeReason> = {};

  changes.forEach((resolution: Resolution, id: string): void => {
    if (!resolution.reason && !resolution.comment) {
      return;
    }

    clientReasons[this.getReasonKey(id)] = {
      reason: resolution.reason,
      comment: resolution.comment
    };
  });

  if (Object.keys(clientReasons).length > 0) {
    result.changeReasons[thirdPartyId] = clientReasons;
  }
}

private getReasonKey(id: string): string {
  const parsedId = ChangeIdParser.parse(id);

  if (parsedId.prefix === 'nationalities') {
    return this.getNationalityReasonKey(parsedId);
  }

  return parsedId.prefix;
}
