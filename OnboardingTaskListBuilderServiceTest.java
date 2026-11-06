private applyClientChangeReasons(
  changeReasons: Record<string, IChangeReason>,
  sourceHolder: IThirdParty,
  changes: ReadonlyMap<string, Resolution>,
  client: IThirdParty
): void {
  changes.forEach((resolution: Resolution, id: string): void => {
    if (!resolution.reason) {
      return;
    }

    const key = this.getClientChangeReasonKey(
      sourceHolder.thirdPartyId,
      id,
      client
    );

    if (!key) {
      return;
    }

    changeReasons[key] = {
      reason: resolution.reason,
      comment: resolution.comment ?? ''
    };
  });
}

private getClientChangeReasonKey(
  thirdPartyId: string,
  id: string,
  client: IThirdParty
): string | null {
  const parsedId: ChangeIdParser = ChangeIdParser.parse(id);

  const fieldPath = parsedId.prefix === 'nationalities'
    ? this.getNationalityChangeReasonPath(parsedId, client)
    : this.getSimpleChangeReasonPath(parsedId);

  if (!fieldPath) {
    return null;
  }

  return `policy_clients_${thirdPartyId}_${fieldPath}`;
}
