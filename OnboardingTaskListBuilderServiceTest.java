import {
  ChangeDetectionStrategy,
  Component,
  computed,
  input,
  output,
  signal
} from '@angular/core';
import {
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators
} from '@angular/forms';
import {
  toObservable,
  toSignal
} from '@angular/core/rxjs-interop';
import {
  Observable,
  switchMap
} from 'rxjs';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzIconModule } from 'ng-zorro-antd/icon';
import { NzInputModule } from 'ng-zorro-antd/input';
import { NzSelectModule } from 'ng-zorro-antd/select';

import {
  DocumentTypeConfiguration,
  MetadataConfiguration
} from 'YOUR_MODEL_PATH';

export interface UploadDocumentRequest {
  file: File;
  documentType: string;
  filename: string;
  metadata: Record<string, string>;
}

@Component({
  selector: 'upload-document',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    NzButtonModule,
    NzIconModule,
    NzInputModule,
    NzSelectModule
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

  protected readonly selectedFile = signal<File | null>(null);

  protected readonly metadataValues =
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

  protected readonly documentTypes = toSignal(
    toObservable(this.getDocumentConfigType).pipe(
      switchMap(getDocumentConfigType =>
        getDocumentConfigType()
      )
    ),
    {
      initialValue: [] as DocumentTypeConfiguration[]
    }
  );

  protected readonly metadataConfiguration = toSignal(
    toObservable(this.getDocumentMetadata).pipe(
      switchMap(getDocumentMetadata =>
        getDocumentMetadata()
      )
    ),
    {
      initialValue:
        {} as Record<string, MetadataConfiguration[]>
    }
  );

  protected readonly canSubmit = computed((): boolean => {
    return (
      this.selectedFile() !== null &&
      this.form.controls.documentType.value.trim().length > 0 &&
      this.form.controls.filename.value.trim().length > 0
    );
  });

  protected onFileSelected(event: Event): void {
    const element = event.target as HTMLInputElement;
    const file = element.files?.[0] ?? null;

    this.selectedFile.set(file);

    if (
      file &&
      !this.form.controls.filename.value.trim()
    ) {
      this.form.controls.filename.setValue(file.name);
    }
  }

  protected onDocumentTypeChanged(
    documentType: string
  ): void {
    this.form.controls.documentType.setValue(documentType);
    this.metadataValues.set({});
  }

  protected updateMetadata(
    key: string,
    value: string
  ): void {
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

    this.submitted.emit({
      file,
      documentType: this.form.controls.documentType.value,
      filename: this.form.controls.filename.value.trim(),
      metadata: {
        ...this.metadataValues()
      }
    });
  }
}
