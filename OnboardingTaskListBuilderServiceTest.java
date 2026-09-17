private addNationality(
  holder: IPhysicalPerson,
  resolution: Resolution
): void {
  const country = this.nullable(resolution.value);

  if (!country) {
    return;
  }

  const nationalities = holder.nationality;

  const alreadyExists = [
    nationalities.first,
    nationalities.second,
    nationalities.third
  ].some(item => item?.country === country);

  if (alreadyExists) {
    return;
  }

  const newNationality: NationalityInfo = {
    country
  };

  if (!nationalities.first?.country) {
    nationalities.first = newNationality;
    return;
  }

  if (!nationalities.second?.country) {
    nationalities.second = newNationality;
    return;
  }

  if (!nationalities.third?.country) {
    nationalities.third = newNationality;
    return;
  }

  console.warn(
    '[ClientProfilingChangeService] Maximum of 3 nationalities reached'
  );
}
