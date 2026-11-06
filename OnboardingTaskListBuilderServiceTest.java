private applyChangeReasons(
  changeClientInformation: IChangeClientInformation,
  changes: ReadonlyMap<string, Resolution>
): void {
  changeClientInformation.changeReasons ??= {};

  changes.forEach((resolution: Resolution, id: string): void => {
    if (!resolution.reason && !resolution.comment) {
      return;
    }

    changeClientInformation.changeReasons[id] = {
      reason: resolution.reason,
      comment: resolution.comment,
    };
  });
}
