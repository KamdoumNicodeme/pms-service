const definitions = (field.entryFields ?? []).filter(definition => {
  if (definition.key !== 'tin-unavailable-reason') {
    return true;
  }

  const tin =
    paired.fields.core
      ?.find(item => item.key === 'tin')
      ?.value
    ??
    paired.fields.kyc
      ?.find(item => item.key === 'tin')
      ?.value
    ??
    paired.fields.digital
      ?.find(item => item.key === 'tin')
      ?.value;

  return !tin?.trim();
});
