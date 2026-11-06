private applyChangeReasons(
  changeClientInformation: IChangeClientInformation,
  changes: ReadonlyMap<string, Resolution>
): void {
  changeClientInformation.changeReasons ??= {};

  changes.forEach((resolution: Resolution, id: string): void => {
    if (!resolution.reason && !resolution.comment) {
      return;
    }

    const parsedId: ChangeIdParser = ChangeIdParser.parse(id);

    const reasonKey = this.getChangeReasonKey(parsedId, id);

    changeClientInformation.changeReasons[reasonKey] = {
      reason: resolution.reason,
      comment: resolution.comment,
    };
  });
}
