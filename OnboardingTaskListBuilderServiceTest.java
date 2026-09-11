protected readonly fatcaOptions = computed<ComparisonOption[]>(() =>
  this.FATCA_STATUS_OPTIONS().map((ref: IReference) => ({
    value: ref.code,
    label: ref.label,
  }))
);

protected readonly crsOptions = computed<ComparisonOption[]>(() =>
  this.AEOI_STATUS_OPTIONS().map((ref: IReference) => ({
    value: ref.code,
    label: ref.label,
  }))
);
