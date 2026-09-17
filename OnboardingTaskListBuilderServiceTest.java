private applyManualNationalityChange(
  client: IPhysicalPerson,
  id: string,
  resolution: Resolution
): void {

  if (!id.startsWith('nationalities:manual-')) {
    return;
  }

  const country =
    this.nullableStringValue(
      resolution.value
    );

  if (!country) {
    return;
  }

  if (!client.nationality) {
    return;
  }

  // ============================================================
  // DUPLICATE CHECK
  // ============================================================

  const exists = [
    client.nationality.first,
    client.nationality.second,
    client.nationality.third
  ].some(
    nationality =>
      nationality?.country === country
  );

  if (exists) {
    return;
  }

  // ============================================================
  // FIRST AVAILABLE SLOT
  // ============================================================

  if (!client.nationality.first?.country) {

    client.nationality.first = {
      ...(client.nationality.first ?? {}),
      country
    };

    return;
  }

  if (!client.nationality.second?.country) {

    client.nationality.second = {
      ...(client.nationality.second ?? {}),
      country
    };

    return;
  }

  if (!client.nationality.third?.country) {

    client.nationality.third = {
      ...(client.nationality.third ?? {}),
      country
    };

    return;
  }

  console.warn(
    '[ClientProfilingChangeService] Maximum 3 nationalities reached'
  );
}
