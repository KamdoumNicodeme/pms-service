import {
  ChangeDetectionStrategy,
  Component,
  input,
  output,
} from '@angular/core';

import { ICaseDocument } from '...';

export type DocumentAction =
  | 'download'
  | 'edit'
  | 'reject'
  | 'validate'
  | 'delete';

@Component({
  selector: 'documents',
  standalone: true,
  templateUrl: './documents.html',
  styleUrl: './documents.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class DocumentsComponent {

  readonly documents = input.required<readonly ICaseDocument[]>();

  readonly downloadDocument = output<ICaseDocument>();
  readonly editDocument = output<ICaseDocument>();
  readonly rejectDocument = output<ICaseDocument>();
  readonly validateDocument = output<ICaseDocument>();
  readonly deleteDocument = output<ICaseDocument>();

  protected onAction(
    action: DocumentAction,
    document: ICaseDocument
  ): void {

    switch (action) {
      case 'download':
        this.downloadDocument.emit(document);
        break;

      case 'edit':
        this.editDocument.emit(document);
        break;

      case 'reject':
        this.rejectDocument.emit(document);
        break;

      case 'validate':
        this.validateDocument.emit(document);
        break;

      case 'delete':
        this.deleteDocument.emit(document);
        break;
    }
  }
}




<section class="documents">

  <div class="documents__table">

    <div class="documents__row documents__row--header">
      <div>Filename</div>
      <div>Document type</div>
      <div>Metadata</div>
      <div>Source</div>
      <div>Status</div>
      <div>Actions</div>
    </div>

    @for (document of documents(); track document.documentId) {

      <div class="documents__row">

        <!-- FILENAME -->
        <div class="documents__filename">
          {{ document.name }}
        </div>

        <!-- DOCUMENT TYPE -->
        <div>
          {{ document.type ?? '-' }}
        </div>

        <!-- METADATA -->
        <div class="documents__metadata">
          <!-- À mapper avec les vrais champs de ICaseDocument -->
        </div>

        <!-- SOURCE -->
        <div>
          {{ document.source ?? '-' }}
        </div>

        <!-- STATUS -->
        <div
          class="documents__status"
          [class.documents__status--rejected]="document.status === 'REJECTED'"
          [class.documents__status--validated]="document.status === 'VALIDATED'"
        >
          {{ document.status ?? '-' }}
        </div>

        <!-- ACTIONS -->
        <div class="documents__actions">

          <button
            nz-button
            nz-dropdown
            nzTrigger="click"
            [nzDropdownMenu]="documentMenu"
            class="documents__menu-button"
          >
            ⋮
          </button>

          <nz-dropdown-menu #documentMenu="nzDropdownMenu">

            <ul nz-menu>

              <li
                nz-menu-item
                (click)="onAction('download', document)"
              >
                Download
              </li>

              <li
                nz-menu-item
                (click)="onAction('edit', document)"
              >
                Edit
              </li>

              <li
                nz-menu-item
                (click)="onAction('reject', document)"
              >
                Reject
              </li>

              <li
                nz-menu-item
                (click)="onAction('validate', document)"
              >
                Validate
              </li>

              <li
                nz-menu-item
                nzDanger
                (click)="onAction('delete', document)"
              >
                Delete
              </li>

            </ul>

          </nz-dropdown-menu>

        </div>

      </div>

    } @empty {

      <div class="documents__empty">
        No documents available
      </div>

    }

  </div>

  <div class="documents__footer">
    <button
      nz-button
      nzType="primary"
      type="button"
    >
      Add new documents
    </button>
  </div>

</section>




          .documents {
  width: 100%;
  padding: 1rem;

  &__table {
    width: 100%;
  }

  &__row {
    display: grid;

    grid-template-columns:
      minmax(20rem, 3fr)
      minmax(8rem, 1fr)
      minmax(15rem, 2fr)
      7rem
      7rem
      5rem;

    min-height: 2.75rem;

    background: #e7e3dd;
    border-bottom: 1px solid #fff;

    > div {
      padding: 0.6rem;
      border-right: 1px solid #fff;
      display: flex;
      align-items: center;
    }

    &--header {
      background: var(--ant-primary-5);
      color: #fff;
      font-weight: 600;
    }
  }

  &__filename {
    font-weight: 500;
  }

  &__metadata {
    white-space: normal;
  }

  &__status {
    &--rejected {
      color: #d32029;
      font-weight: 600;
    }

    &--validated {
      color: #389e0d;
      font-weight: 600;
    }
  }

  &__actions {
    justify-content: center;
  }

  &__menu-button {
    min-width: 2rem;
    width: 2rem;
    padding: 0;
  }

  &__footer {
    margin-top: 1rem;
  }

  &__empty {
    padding: 2rem;
    text-align: center;
    background: #fafafa;
  }
}
