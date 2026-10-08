readonly nationalityDuplicate = computed((): boolean => {
  if (this.row().key !== 'nationalities') {
    return false;
  }

  const country: string = this.fieldValue('country').trim();

  if (!country) {
    return false;
  }

  return this.nationalityAlreadyExists(country);
});
