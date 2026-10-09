const documentType = this.form.controls.documentType.value;

const configurations =
  this.metadataConfiguration()[documentType] ?? [];

const metadataTypes = Object.fromEntries(
  configurations.map(configuration => [
    configuration.name,
    configuration.type
  ])
);

const request: UploadDocumentRequest = {
  file: this.selectedFile()!,
  documentType,
  filename: this.form.controls.filename.value,
  metadata: this.formMetadata(),
  metadataTypes
};

this.submitted.emit(request);
