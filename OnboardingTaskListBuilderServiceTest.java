
import {
  ChangeDetectionStrategy,
  Component,
  Signal,
  WritableSignal,
  computed,
  input,
  output,
  signal
} from '@angular/core';
import {
  FormControl,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators
} from '@angular/forms';
import { toObservable, toSignal, takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { Observable, switchMap } from 'rxjs';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzIconModule } from 'ng-zorro-antd/icon';
import { NzInputModule } from 'ng-zorro-antd/input';
import { NzSelectModule } from 'ng-zorro-antd/select';

@Component({
  selector: 'upload-document',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    NzButtonModule,
    NzIconModule,
    NzInputModule,
    NzSelectModule,
    FormsModule
  ],
  templateUrl: './upload-document.component.html',
  styleUrl: './upload-document.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class UploadDocumentComponent {
  readonly getDocumentConfigType = input.required<
    () => Observable<DocumentTypeConfiguration[]>
  >();

  readonly getDocumentMetadata = input.required<
    () => Observable<Record<string, MetadataConfiguration[]>>
  >();

  readonly cancelled = output<void>();
  readonly submitted = output<UploadDocumentRequest>();

  protected readonly selectedFile: WritableSignal<File | null> =
    signal<File | null>(null);

  protected readonly metadataValues: WritableSignal<Record<string, string>> =
    signal<Record<string, string>>({});

  protected readonly form = new FormGroup({
    documentType: new FormControl<string>('', {
      nonNullable: true,
      validators: [Validators.required]
    }),
    filename: new FormControl<string>('', {
      nonNullable: true,
      validators: [Validators.required]
    })
  });

  constructor() {
    this.form.controls.documentType.valueChanges
      .pipe(takeUntilDestroyed())
      .subscribe((documentType: string) => {
        if (documentType) {
          this.onDocumentTypeChanged(documentType);
        }
      });
  }

  protected readonly documentTypes: Signal<DocumentTypeConfiguration[]> =
    toSignal(
      toObservable(this.getDocumentConfigType).pipe(
        switchMap(getDocumentConfigType => getDocumentConfigType())
      ),
      {
        initialValue: [] as DocumentTypeConfiguration[]
      }
    );

  protected readonly metadataConfiguration: Signal<
    Record<string, MetadataConfiguration[]>
  > = toSignal(
    toObservable(this.getDocumentMetadata).pipe(
      switchMap(getDocumentMetadata => getDocumentMetadata())
    ),
    {
      initialValue: {} as Record<string, MetadataConfiguration[]>
    }
  );

  protected readonly selectedMetadata: Signal<MetadataConfiguration[]> =
    computed((): MetadataConfiguration[] => {
      const documentType: string =
        this.form.controls.documentType.value;

      if (!documentType) {
        return [];
      }

      return this.metadataConfiguration()[documentType] ?? [];
    });

  protected readonly canSubmit: Signal<boolean> =
    computed((): boolean => {
      const file: File | null = this.selectedFile();

      const documentType: string =
        this.form.controls.documentType.value.trim();

      const filename: string =
        this.form.controls.filename.value.trim();

      const metadataValid: boolean = this.selectedMetadata()
        .filter((metadata: MetadataConfiguration) => metadata.mandatory)
        .every(
          (metadata: MetadataConfiguration) =>
            !!this.metadataValue(metadata.name).trim()
        );

      return file !== null &&
        documentType.length > 0 &&
        filename.length > 0 &&
        metadataValid;
    });

  protected onFileSelected(event: Event): void {
    const element = event.target as HTMLInputElement;
    const file = element.files?.[0] ?? null;

    this.selectedFile.set(file);

    if (file && !this.form.controls.filename.value.trim()) {
      this.form.controls.filename.setValue(file.name);
    }
  }

  protected onDocumentTypeChanged(documentType: string): void {
    this.form.controls.documentType.setValue(
      documentType,
      { emitEvent: false }
    );

    this.metadataValues.set({});
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

  protected cancel(): void {
    this.cancelled.emit();
  }

  protected submit(): void {
    if (!this.canSubmit()) {
      this.form.markAllAsTouched();
      return;
    }

    const file = this.selectedFile();

    if (!file) {
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
      file,
      documentType: this.form.controls.documentType.value,
      filename: this.form.controls.filename.value.trim(),
      metadata
    });
  }
}
