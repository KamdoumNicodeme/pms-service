import {
  ChangeDetectionStrategy,
  Component,
  computed,
  effect,
  input,
  output,
  signal,
  untracked,
} from '@angular/core';
import { NgClass } from '@angular/common';
import { Observable } from 'rxjs';

import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzDropDownModule } from 'ng-zorro-antd/dropdown';
import { NzIconModule } from 'ng-zorro-antd/icon';
import { NzMenuModule } from 'ng-zorro-antd/menu';
import { NzTableModule } from 'ng-zorro-antd/table';
import { NzToolTipModule } from 'ng-zorro-antd/tooltip';

import { ICaseDocument } from '...';
import { DocumentTypeConfiguration } from '...';
import { MetadataConfiguration } from '...';
import { UploadDocumentRequest } from '...';
import { EditDocumentRequest } from '...';

import { UploadDocumentComponent } from '...';
import { EditDocumentComponent } from '...';

@Component({
  selector: 'documents',
  standalone: true,
  imports: [
    NgClass,
    NzButtonModule,
    NzDropDownModule,
    NzIconModule,
    NzMenuModule,
    NzTableModule,
    NzToolTipModule,
    UploadDocumentComponent,
    EditDocumentComponent,
  ],
  templateUrl: './documents.html',
  styleUrl: './documents.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class DocumentsComponent {

  readonly documents =
    input<readonly ICaseDocument[]>([]);

  readonly downloadDocument =
    output<ICaseDocument>();

  readonly rejectDocument =
    output<ICaseDocument>();

  readonly removeDocument =
    output<ICaseDocument>();

  readonly validateDocument =
    output<ICaseDocument>();

  readonly deleteDocument =
    output<ICaseDocument>();

  readonly getDocumentConfigType =
    input.required<
      () => Observable<DocumentTypeConfiguration[]>
    >();

  readonly getDocumentMetadata =
    input.required<
      () => Observable<Record<string, MetadataConfiguration[]>>
    >();

  readonly uploadDocument =
    output<UploadDocumentRequest>();

  protected readonly refreshDocuments =
    input<() => void>(() => {});

  readonly documentSaved =
    input<number>(0);

  readonly documentEdited =
    input<number>(0);

  readonly editDocument = output<{
    documentId: string;
    request: EditDocumentRequest;
  }>();

  protected readonly editingDocument =
    signal<ICaseDocument | null>(null);

  protected readonly uploadModalVisible =
    signal(false);

  protected readonly pageIndex =
    signal(1);

  protected readonly pageSize = 5;

  protected readonly documentCount =
    computed(() => this.documents().length);

  protected readonly sortByDocumentType = (
    a: ICaseDocument,
    b: ICaseDocument
  ): number => {
    return this.displayDocumentType(a.type)
      .localeCompare(
        this.displayDocumentType(b.type)
      );
  };

  protected readonly sortBySource = (
    a: ICaseDocument,
    b: ICaseDocument
  ): number => {
    return this.displaySource(a.source)
      .localeCompare(
        this.displaySource(b.source)
      );
  };

  protected readonly sortByStatus = (
    a: ICaseDocument,
    b: ICaseDocument
  ): number => {
    return this.displayStatus(a.status)
      .localeCompare(
        this.displayStatus(b.status)
      );
  };

  constructor() {
    effect((): void => {
      const saved = this.documentSaved();
      const documents = this.documents();

      if (saved === 0) {
        return;
      }

      const lastPage = Math.max(
        1,
        Math.ceil(
          documents.length / this.pageSize
        )
      );

      untracked((): void => {
        this.closeUploadModal();
        this.pageIndex.set(lastPage);
      });
    });

    effect((): void => {
      const edited = this.documentEdited();

      if (edited === 0) {
        return;
      }

      untracked((): void => {
        this.closeEditDocument();
      });
    });
  }

  protected metadataEntries(
    metadata?: Record<string, string> | null
  ): [string, string][] {
    if (!metadata) {
      return [];
    }

    const entries = Object.entries(metadata).filter(
      ([_, value]: [string, string]): boolean =>
        value !== null &&
        value !== undefined &&
        value !== ''
    );

    return entries.sort(
      (
        [keyA]: [string, string],
        [keyB]: [string, string]
      ): number =>
        this.metadataPriority(keyA) -
        this.metadataPriority(keyB)
    );
  }

  protected primaryMetadata(
    metadata?: Record<string, string> | null
  ): [string, string] | null {
    const entries = this.metadataEntries(metadata);

    return entries.length > 0
      ? entries[0]
      : null;
  }

  protected remainingMetadata(
    metadata?: Record<string, string> | null
  ): [string, string][] {
    return this.metadataEntries(metadata).slice(1);
  }

  protected displayDocumentType(
    type?: string | null
  ): string {
    if (!type) {
      return '-';
    }

    return type
      .replaceAll('_', ' ')
      .toLowerCase()
      .replace(
        /\b\w/g,
        (character: string): string =>
          character.toUpperCase()
      );
  }

  protected displaySource(
    source?: string | null
  ): string {
    if (!source) {
      return '-';
    }

    return source
      .replaceAll('_', ' ')
      .toLowerCase()
      .replace(
        /\b\w/g,
        (character: string): string =>
          character.toUpperCase()
      );
  }

  protected displayStatus(
    status?: string | null
  ): string {
    if (!status) {
      return 'Pending';
    }

    switch (status.toUpperCase()) {
      case 'VALIDATED':
        return 'Validated';

      case 'REJECTED':
        return 'Rejected';

      case 'REPLACED':
        return 'Replaced';

      case 'PENDING':
        return 'Pending';

      default:
        return status;
    }
  }

  protected statusClass(
    status?: string | null
  ): string {
    switch (status?.toUpperCase()) {
      case 'VALIDATED':
        return 'document-status--validated';

      case 'REJECTED':
        return 'document-status--rejected';

      case 'REPLACED':
        return 'document-status--replaced';

      default:
        return 'document-status--pending';
    }
  }

  protected onUploadDocument(
    request: UploadDocumentRequest
  ): void {
    this.uploadDocument.emit(request);
  }

  protected onDownload(
    document: ICaseDocument
  ): void {
    this.downloadDocument.emit(document);
  }

  protected onEditDocumentSubmitted(
    request: EditDocumentRequest
  ): void {
    const document =
      this.editingDocument();

    if (!document) {
      return;
    }

    this.editDocument.emit({
      documentId: document.documentId,
      request,
    });
  }

  protected openEditDocument(
    document: ICaseDocument
  ): void {
    this.editingDocument.set(document);
  }

  protected closeEditDocument(): void {
    this.editingDocument.set(null);
  }

  protected onReject(
    document: ICaseDocument
  ): void {
    this.rejectDocument.emit(document);
  }

  protected onRemove(
    document: ICaseDocument
  ): void {
    this.removeDocument.emit(document);
  }

  protected onValidate(
    document: ICaseDocument
  ): void {
    this.validateDocument.emit(document);
  }

  protected openUploadModal(): void {
    this.uploadModalVisible.set(true);
  }

  protected closeUploadModal(): void {
    this.uploadModalVisible.set(false);
  }

  private metadataPriority(
    key: string
  ): number {
    switch (key.toLowerCase()) {
      case 'thirdpartyid':
        return 0;

      case 'policynumber':
        return 1;

      default:
        return 2;
    }
  }
}
