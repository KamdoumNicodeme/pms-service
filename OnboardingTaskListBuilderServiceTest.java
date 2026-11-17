protected readonly filenameValue = toSignal(
  this.form.controls.filename.valueChanges,
  {
    initialValue: this.form.controls.filename.value
  }
);

protected readonly canSubmit: Signal<boolean> = computed((): boolean => {
  const documentType: string | null = this.documentTypeValue();
  const filename: string = this.filenameValue().trim();

  const metadataValid: boolean = this.selectedMetadata()
    .filter((metadata: MetadataConfiguration) => metadata.mandatory)
    .every(
      (metadata: MetadataConfiguration): boolean =>
        this.metadataValue(metadata.name).trim().length > 0
    );

  return !!documentType && filename.length > 0 && metadataValid;
});

it('should recompute canSubmit when only the filename changes', async () => {
  await render();

  // The form is initially valid.
  expect(view.canSubmit()).toBe(true);

  // Clear only the filename.
  view.form.controls.filename.setValue('');

  // Submit must be disabled.
  expect(view.canSubmit()).toBe(false);

  // Restore the filename without changing other fields.
  view.form.controls.filename.setValue('passport.pdf');

  // Submit must be enabled again.
  expect(view.canSubmit()).toBe(true);
});
