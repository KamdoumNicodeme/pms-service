private applyChangeReasons(
  result: IChangeClientInformation,
  sourceHolder: IThirdParty,
  changes: ReadonlyMap<string, Resolution>
): void {
  result.changeReasons ??= {};

  changes.forEach((resolution: Resolution, id: string): void => {
    if (!resolution.reason) {
      return;
    }

    const reasonKey = this.getReasonKey(
      sourceHolder,
      id
    );

    if (!reasonKey) {
      return;
    }

    result.changeReasons[reasonKey] = {
      reason: resolution.reason,
      comment: resolution.comment ?? ''
    };
  });
}

private getReasonKey(
  sourceHolder: IThirdParty,
  id: string
): string | null {
  const parsedId: ChangeIdParser = ChangeIdParser.parse(id);

  if (parsedId.prefix === 'nationalities') {
    return this.getNationalityReasonKey(
      sourceHolder,
      parsedId
    );
  }

  return `policy_clients_${sourceHolder.thirdPartyId}_${parsedId.prefix}`;
}
