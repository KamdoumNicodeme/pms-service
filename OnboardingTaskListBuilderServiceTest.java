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
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzIconModule } from 'ng-zorro-antd/icon';
import { NzInputModule } from 'ng-zorro-antd/input';
import { NzSelectModule } from 'ng-zorro-antd/select';

export interface DocumentTypeOption {
  value: string;
  label: string;
}

export interface DocumentMetadataDefinition {
  key: string;
  label: string;
  required: boolean;
}

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

  readonly documentTypes =
    input<readonly DocumentTypeOption[]>([]);

  readonly metadataDefinitions =
    input<readonly DocumentMetadataDefinition[]>([]);

  readonly cancelled = output<void>();

  readonly submitted =
    output<UploadDocumentRequest>();

  protected readonly selectedFile =
    signal<File | null>(null);

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

  protected readonly canSubmit = computed(() => {
    return (
      this.selectedFile() !== null &&
      this.form.valid &&
      this.metadataDefinitions()
        .filter(definition => definition.required)
        .every(definition =>
          !!this.metadataValues()[definition.key]?.trim()
        )
    );
  });

  protected onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    const file = input.files?.[0] ?? null;

    this.selectedFile.set(file);

    if (file && !this.form.controls.filename.value.trim()) {
      this.form.controls.filename.setValue(file.name);
    }
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
      metadata: this.metadataValues()
    });
  }
}
