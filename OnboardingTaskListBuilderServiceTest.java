private applyChangeReason(
  result: IChangeClientInformation,
  id: string,
  resolution: Resolution
): void {
  if (!resolution.reason && !resolution.comment) {
    return;
  }

  result.changeReasons ??= {};

  result.changeReasons[id] = {
    reason: resolution.reason,
    comment: resolution.comment,
  };
}
