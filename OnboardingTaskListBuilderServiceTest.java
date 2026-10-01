import {
  ChangeDetectionStrategy,
  Component,
  computed,
  input,
  output
} from '@angular/core';

import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzDropDownModule } from 'ng-zorro-antd/dropdown';
import { NzIconModule } from 'ng-zorro-antd/icon';
import { NzMenuModule } from 'ng-zorro-antd/menu';
import { NzTagModule } from 'ng-zorro-antd/tag';
import { NzTooltipModule } from 'ng-zorro-antd/tooltip';

export interface ICaseDocument {
  documentId: string;
  name: string;
  documentType?: string | null;
  metadata?: Record<string, string> | null;
  source?: string | null;
  status?: string | null;
}

@Component({
  selector: 'documents',
  standalone: true,
  imports: [
    NzButtonModule,
    NzDropDownModule,
    NzIconModule,
    NzMenuModule,
    NzTagModule,
    NzTooltipModule
  ],
  templateUrl: './documents.html',
  styleUrl: './documents.scss',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class DocumentsComponent {

  readonly documents = input<readonly ICaseDocument[]>([]);

  readonly downloadDocument = output<ICaseDocument>();
  readonly editDocument = output<ICaseDocument>();
  readonly rejectDocument = output<ICaseDocument>();
  readonly validateDocument = output<ICaseDocument>();
  readonly deleteDocument = output<ICaseDocument>();
  readonly addDocument = output<void>();

  readonly documentCount = computed(() => this.documents().length);

  protected metadataEntries(
    metadata?: Record<string, string> | null
  ): [string, string][] {
    if (!metadata) {
      return [];
    }

    return Object.entries(metadata)
      .filter(([_, value]) => value !== null && value !== undefined && value !== '');
  }

  protected displayDocumentType(type?: string | null): string {
    if (!type) {
      return '—';
    }

    return type
      .replaceAll('_', ' ')
      .toLowerCase()
      .replace(/\b\w/g, value => value.toUpperCase());
  }

  protected displaySource(source?: string | null): string {
    return source || '—';
  }

  protected displayStatus(status?: string | null): string {
    if (!status) {
      return 'Pending';
    }

    switch (status.toUpperCase()) {
      case 'VALIDATED':
        return 'Validated';

      case 'REJECTED':
        return 'Rejected';

      case 'PENDING':
        return 'Pending';

      default:
        return status;
    }
  }

  protected statusClass(status?: string | null): string {
    switch (status?.toUpperCase()) {
      case 'VALIDATED':
        return 'document-status--validated';

      case 'REJECTED':
        return 'document-status--rejected';

      default:
        return 'document-status--pending';
    }
  }

  protected onAddDocument(): void {
    this.addDocument.emit();
  }

  protected onDownload(document: ICaseDocument): void {
    this.downloadDocument.emit(document);
  }

  protected onEdit(document: ICaseDocument): void {
    this.editDocument.emit(document);
  }

  protected onReject(document: ICaseDocument): void {
    this.rejectDocument.emit(document);
  }

  protected onValidate(document: ICaseDocument): void {
    this.validateDocument.emit(document);
  }

  protected onDelete(document: ICaseDocument): void {
    this.deleteDocument.emit(document);
  }
}
