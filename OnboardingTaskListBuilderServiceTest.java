private getNationalityPosition(index: string): string | null {
  switch (index) {
    case '0':
      return 'first';

    case '1':
      return 'second';

    case '2':
      return 'third';

    default:
      return null;
  }
}
private getNationalityField(field: string): string | null {
  switch (field) {
    case 'country':
      return 'country';

    case 'date':
    case 'fromDate':
      return 'fromDate';

    default:
      return null;
  }
}
