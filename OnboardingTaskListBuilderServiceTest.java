effect(() => {
  const row = this.row();

  if (row.id !== 'italian-birth-town-code') {
    return;
  }

  const codes = [
    row.values.core,
    row.values.manual,
    row.resolution.value
  ].filter((code): code is string => !!code);

  for (const code of new Set(codes)) {
    const alreadyLoaded = untracked(() =>
      this.data.getItalianBirthTownLabels().some(
        option => option.value === code
      )
    );

    if (alreadyLoaded) {
      continue;
    }

    untracked(() => {
      this.#referenceService
        .getReferenceByValue('IT_BIRTH_CODE', code)
        .subscribe({
          next: reference => {
            this.data.addItalianBirthTownOptions([reference]);
          }
        });
    });
  }
});
