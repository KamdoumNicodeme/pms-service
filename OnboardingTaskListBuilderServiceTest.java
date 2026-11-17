protected onFileSelected(event: Event): void {
  const element = event.target as HTMLInputElement;
  const file = element.files?.[0];

  if (!file) {
    return;
  }

  this.selectedFile.set(file);
  this.form.controls.filename.setValue(file.name);
}
