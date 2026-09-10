setFieldValue(
  key: string,
  value: string
): void {

  this.structureDraft.update(current => {

    const next = {
      ...current,
      [key]: value
    };

    if (
      key === 'tin'
      && value?.trim()
    ) {
      next['tin-unavailable-reason'] = '';
    }

    return next;
  });
}
