constructor() {
  console.log('CoreComparisonDataService CREATED');

  try {
    console.log('BEFORE loadReferences');

    this.loadReferences();

    console.log('AFTER loadReferences');
  } catch (error) {
    console.error('ERROR CALLING loadReferences', error);
  }
}
