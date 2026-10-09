protected readonly documentTypeValue = toSignal(
  this.form.controls.documentType.valueChanges.pipe(
    startWith(this.form.controls.documentType.value)
  ),
  { initialValue: this.form.controls.documentType.value }
);
