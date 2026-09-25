protected readonly fatcaOptions = computed<ComparisonOption[]>(() =>
  (this.references()['FATCA_STATUS'] ?? []).map(ref => ({
    value: ref.value,
    label: ref.key,
  }))
);

protected readonly crsOptions = computed<ComparisonOption[]>(() =>
  (this.references()['AEOI_STATUS'] ?? []).map(ref => ({
    value: ref.value,
    label: ref.key,
  }))
);

protected readonly countryOptions = computed<ComparisonOption[]>(() =>
  (this.references()['COUNTRY'] ?? []).map(ref => ({
    value: ref.value,
    label: ref.key,
  }))
);

protected readonly professionOptions = computed<ComparisonOption[]>(() =>
  (this.references()['PROFESSION'] ?? []).map(ref => ({
    value: ref.value,
    label: ref.key,
  }))
);

protected readonly industrySectorOptions = computed<ComparisonOption[]>(() =>
  (this.references()['INDUSTRY_SECTOR'] ?? []).map(ref => ({
    value: ref.value,
    label: ref.key,
  }))
);

protected readonly statusMaritalOptions = computed<ComparisonOption[]>(() =>
  (this.references()['STATUS_MARITAL'] ?? []).map(ref => ({
    value: ref.value,
    label: ref.key,
  }))
);

protected readonly professionalStatusOptions = computed<ComparisonOption[]>(() =>
  (this.references()['PROFESSIONAL_STATUS'] ?? []).map(ref => ({
    value: ref.value,
    label: ref.key,
  }))
);

protected readonly tinReasonOptions = computed<ComparisonOption[]>(() =>
  (this.references()['TIN_REASON'] ?? []).map(ref => ({
    value: ref.value,
    label: ref.key,
  }))
);

protected readonly idTypeOptions = computed<ComparisonOption[]>(() =>
  (this.references()['ID_TYPE'] ?? []).map(ref => ({
    value: ref.value,
    label: ref.key,
  }))
);
