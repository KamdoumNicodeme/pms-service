@for (
  metadata of metadataConfiguration()[form.controls.documentType.value] ?? [];
  track metadata.name
) {
  <div class="document-form__row">
    <label>
      @if (metadata.mandatory) {
        <span class="required">*</span>
      }

      {{ metadata.name }}:
    </label>

    <input
      nz-input
      [value]="metadataValue(metadata.name)"
      [required]="metadata.mandatory"
      [readonly]="metadata.immutable === true"
      [pattern]="metadata.pattern ?? ''"
      (input)="updateMetadata(
        metadata.name,
        $any($event.target).value
      )"
    />
  </div>
}


      protected readonly selectedMetadata = computed(
  (): MetadataConfiguration[] => {

    const documentType =
      this.form.controls.documentType.value;

    if (!documentType) {
      return [];
    }

    return this.metadataConfiguration()[documentType] ?? [];
  }
);

      protected readonly canSubmit = computed((): boolean => {
  const file = this.selectedFile();

  const documentType =
    this.form.controls.documentType.value.trim();

  const filename =
    this.form.controls.filename.value.trim();

  const metadataValid =
    this.selectedMetadata()
      .filter(metadata => metadata.mandatory)
      .every(metadata =>
        !!this.metadataValue(metadata.name).trim()
      );

  return (
    file !== null &&
    documentType.length > 0 &&
    filename.length > 0 &&
    metadataValid
  );
});
