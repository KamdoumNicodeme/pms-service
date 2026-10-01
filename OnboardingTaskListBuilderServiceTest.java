<section class="documents">

  <!-- HEADER -->
  <header class="documents__header">

    <div class="documents__title">
      <span
        nz-icon
        nzType="file-text"
        nzTheme="outline"
        class="documents__title-icon"
      ></span>

      <div>
        <h2>Documents</h2>

        <span class="documents__subtitle">
          {{ documentCount() }}
          {{ documentCount() === 1 ? 'document' : 'documents' }}
        </span>
      </div>
    </div>

    <button
      nz-button
      type="button"
      class="documents__add-button"
      (click)="onAddDocument()"
    >
      <span nz-icon nzType="plus"></span>
      Add new document
    </button>

  </header>


  <!-- TABLE -->
  <div class="documents__table-wrapper">

    <table class="documents__table">

      <thead>
        <tr>
          <th class="documents__filename-column">
            Filename
          </th>

          <th class="documents__type-column">
            Document type
          </th>

          <th class="documents__metadata-column">
            Metadata
          </th>

          <th class="documents__source-column">
            Source
          </th>

          <th class="documents__status-column">
            Status
          </th>

          <th class="documents__actions-column">
            Actions
          </th>
        </tr>
      </thead>


      <tbody>

        @for (document of documents(); track document.documentId) {

          <tr>

            <!-- FILENAME -->
            <td>
              <div class="document-file">

                <div class="document-file__icon">
                  <span
                    nz-icon
                    nzType="file-text"
                    nzTheme="outline"
                  ></span>
                </div>

                <div class="document-file__info">

                  <button
                    type="button"
                    class="document-file__name"
                    (click)="onDownload(document)"
                    [nz-tooltip]="document.name"
                  >
                    {{ document.name }}
                  </button>

                  <span class="document-file__id">
                    ID: {{ document.documentId }}
                  </span>

                </div>

              </div>
            </td>


            <!-- DOCUMENT TYPE -->
            <td>
              <span class="document-type">
                {{ displayDocumentType(document.documentType) }}
              </span>
            </td>


            <!-- METADATA -->
            <td>

              @if (metadataEntries(document.metadata).length > 0) {

                <div class="document-metadata">

                  @for (
                    metadata of metadataEntries(document.metadata);
                    track metadata[0]
                  ) {

                    <div class="document-metadata__item">

                      <span class="document-metadata__key">
                        {{ metadata[0] }}:
                      </span>

                      <span class="document-metadata__value">
                        {{ metadata[1] }}
                      </span>

                    </div>

                  }

                </div>

              } @else {

                <span class="documents__empty-value">
                  —
                </span>

              }

            </td>


            <!-- SOURCE -->
            <td>
              <span class="document-source">
                {{ displaySource(document.source) }}
              </span>
            </td>


            <!-- STATUS -->
            <td>

              <span
                class="document-status"
                [class]="statusClass(document.status)"
              >
                <span class="document-status__dot"></span>

                {{ displayStatus(document.status) }}
              </span>

            </td>


            <!-- ACTIONS -->
            <td class="documents__actions-cell">

              <button
                nz-button
                nz-dropdown
                nzTrigger="click"
                nzPlacement="bottomRight"
                [nzDropdownMenu]="documentMenu"
                type="button"
                class="document-actions-button"
                nz-tooltip
                nzTooltipTitle="Actions"
              >
                <span
                  nz-icon
                  nzType="more"
                  nzTheme="outline"
                ></span>
              </button>


              <nz-dropdown-menu #documentMenu="nzDropdownMenu">

                <ul
                  nz-menu
                  class="document-actions-menu"
                >

                  <!-- DOWNLOAD -->
                  <li
                    nz-menu-item
                    (click)="onDownload(document)"
                  >
                    <span
                      nz-icon
                      nzType="download"
                    ></span>

                    <span>Download</span>
                  </li>


                  <!-- EDIT -->
                  <li
                    nz-menu-item
                    (click)="onEdit(document)"
                  >
                    <span
                      nz-icon
                      nzType="edit"
                    ></span>

                    <span>Edit</span>
                  </li>


                  <li nz-menu-divider></li>


                  <!-- VALIDATE -->
                  <li
                    nz-menu-item
                    (click)="onValidate(document)"
                  >
                    <span
                      nz-icon
                      nzType="check-circle"
                    ></span>

                    <span>Validate</span>
                  </li>


                  <!-- REJECT -->
                  <li
                    nz-menu-item
                    (click)="onReject(document)"
                  >
                    <span
                      nz-icon
                      nzType="close-circle"
                    ></span>

                    <span>Reject</span>
                  </li>


                  <li nz-menu-divider></li>


                  <!-- DELETE -->
                  <li
                    nz-menu-item
                    nzDanger
                    (click)="onDelete(document)"
                  >
                    <span
                      nz-icon
                      nzType="delete"
                    ></span>

                    <span>Delete</span>
                  </li>

                </ul>

              </nz-dropdown-menu>

            </td>

          </tr>

        } @empty {

          <tr>
            <td
              colspan="6"
              class="documents-empty"
            >

              <span
                nz-icon
                nzType="file"
                nzTheme="outline"
                class="documents-empty__icon"
              ></span>

              <strong>No documents</strong>

              <span>
                No document is currently associated with this case.
              </span>

            </td>
          </tr>

        }

      </tbody>

    </table>

  </div>

</section>
