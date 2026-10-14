@Component({
  selector: 'edit-document',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    NzButtonModule,
    NzIconModule,
    NzInputModule,
    NzSelectModule
  ],
  templateUrl: './edit-document.component.html',
  styleUrl: './edit-document.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class EditDocumentComponent {

  readonly document = input.required<ICaseDocument>();

  readonly getDocumentConfigType =
    input.required<() => Observable<DocumentTypeConfiguration[]>>();

  readonly getDocumentMetadata =
    input.required<() => Observable<Record<string, MetadataConfiguration[]>>>();

  readonly cancelled = output<void>();
  readonly submitted = output<EditDocumentRequest>();

  protected readonly selectedFile = signal<File | null>(null);

  protected readonly metadataValues =
    signal<Record<string, string>>({});

  protected readonly form = new FormGroup({
    documentType: new FormControl<string | null>(null, {
      validators: [Validators.required]
    }),
    filename: new FormControl<string>('', {
      nonNullable: true,
      validators: [Validators.required]
    }),
    status: new FormControl<string | null>(null)
  });

  protected readonly documentTypes = toSignal(
    toObservable(this.getDocumentConfigType).pipe(
      switchMap(getDocumentConfigType => getDocumentConfigType())
    ),
    {
      initialValue: [] as DocumentTypeConfiguration[]
    }
  );

  protected readonly metadataConfiguration = toSignal(
    toObservable(this.getDocumentMetadata).pipe(
      switchMap(getDocumentMetadata => getDocumentMetadata())
    ),
    {
      initialValue: {} as Record<string, MetadataConfiguration[]>
    }
  );

  protected readonly documentTypeValue = toSignal(
    this.form.controls.documentType.valueChanges.pipe(
      startWith(this.form.controls.documentType.value)
    ),
    {
      initialValue: this.form.controls.documentType.value
    }
  );

  protected readonly selectedMetadata = computed((): MetadataConfiguration[] => {
    const documentType = this.documentTypeValue();

    if (!documentType) {
      return [];
    }

    return this.metadataConfiguration()[documentType] ?? [];
  });

  protected readonly canSubmit = computed((): boolean => {
    const documentType = this.documentTypeValue();
    const filename = this.form.controls.filename.value.trim();

    const metadataValid = this.selectedMetadata()
      .filter(metadata => metadata.mandatory)
      .every(metadata => !!this.metadataValue(metadata.name).trim());

    return !!documentType && filename.length > 0 && metadataValid;
  });

  constructor() {
    effect(() => {
      const document = this.document();

      this.form.patchValue({
        documentType: document.documentType,
        filename: document.name,
        status: document.status ?? null
      }, { emitEvent: true });

      const values: Record<string, string> = {};

      for (const [key, value] of Object.entries(document.metadata ?? {})) {
        values[key] = String(value ?? '');
      }

      this.metadataValues.set(values);
      this.selectedFile.set(null);
    });

    this.form.controls.documentType.valueChanges
      .pipe(takeUntilDestroyed())
      .subscribe(documentType => {
        if (documentType) {
          this.onDocumentTypeChanged(documentType);
        }
      });
  }

  protected onFileSelected(event: Event): void {
    const element = event.target as HTMLInputElement;
    const file = element.files?.[0] ?? null;

    this.selectedFile.set(file);

    if (file) {
      this.form.controls.filename.setValue(file.name);
    }
  }

  protected onDocumentTypeChanged(documentType: string): void {
    this.form.controls.documentType.setValue(
      documentType,
      { emitEvent: false }
    );

    if (documentType !== this.document().documentType) {
      this.metadataValues.set({});
    }
  }

  protected updateMetadata(key: string, value: string): void {
    this.metadataValues.update(current => ({
      ...current,
      [key]: value
    }));
  }

  protected metadataValue(key: string): string {
    return this.metadataValues()[key] ?? '';
  }

  protected metadataInputType(type: string): string {
    switch (type) {
      case 'DATE':
        return 'date';
      case 'DATETIME':
        return 'datetime-local';
      case 'NUMBER':
        return 'number';
      default:
        return 'text';
    }
  }

  protected cancel(): void {
    this.cancelled.emit();
  }

  protected submit(): void {
    if (!this.canSubmit()) {
      this.form.markAllAsTouched();
      return;
    }

    const metadata = this.selectedMetadata()
      .filter(configuration =>
        this.metadataValue(configuration.name).trim().length > 0
      )
      .map(configuration => ({
        key: configuration.name,
        type: configuration.type,
        value: this.metadataValue(configuration.name).trim()
      }));

    this.submitted.emit({
      file: this.selectedFile(),
      name: this.form.controls.filename.value.trim(),
      type: this.form.controls.documentType.value ?? '',
      metadata,
      status: this.form.controls.status.value
    });
  }
}
