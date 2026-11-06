private getNationalityReasonKey(parsedId: ChangeIdParser): string {
  const position = parsedId.parts[1];

  switch (position) {
    case 'first':
      return 'nationality_first';

    case 'second':
      return 'nationality_second';

    case 'third':
      return 'nationality_third';

    default:
      return 'nationality';
  }
}
