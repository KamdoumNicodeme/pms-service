private getNationalityReasonKey(parsedId: ChangeIdParser): string {
  switch (parsedId.index) {
    case 0:
      return 'nationality-first';
    case 1:
      return 'nationality-second';
    case 2:
      return 'nationality-third';
    default:
      return `nationality-${parsedId.index + 1}`;
  }
}
