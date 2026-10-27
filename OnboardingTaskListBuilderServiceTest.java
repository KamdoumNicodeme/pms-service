<section class="documents">

  <!-- =========================================================
       HEADER
       ========================================================= -->
  <header class="documents__header">

    <div class="documents__title">
      <!-- Garde ici exactement ton contenu actuel du title -->
      <span
        class="documents__title-icon"
        nz-icon
        nzTheme="outline"
        nzType="file"
      ></span>

      <div>
        <h2>Documents</h2>
        <span>
          {{ documents().length }}
          {{ documents().length === 1 ? 'document' : 'documents' }}
        </span>
      </div>
    </div>

    <!-- Garde ici ton handler actuel si son nom est différent -->
    <button
      nz-button
      type="button"
      class="documents__add-button"
    >
      <span
        nz-icon
        nzType="plus"
        nzTheme="outline"
      ></span>

      Add new document
    </button>

  </header>


  <!-- =========================================================
       TABLE
       ========================================================= -->
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

        @for (document of paginatedDocuments(); track document.documentId) {

          <tr>

            <!-- =================================================
                 FILENAME
                 ================================================= -->
            <td>

              <div class="document-file">

                <div class="document-file__icon">

                  <span
                    nz-icon
                    nzTheme="outline"
                    nzType="file-text"
                  ></span>

                </div>

                <div class="document-file__info">

                  <button
                    (click)="onDownload(document)"
                    [nz-tooltip]="document.name"
                    class="document-file__name"
                    type="button"
                  >
                    {{ document.name }}
                  </button>

                  <span class="document-file__id">
                    ID: {{ document.documentId }}
                  </span>

                </div>

              </div>

            </td>


            <!-- =================================================
                 DOCUMENT TYPE
                 ================================================= -->
            <td>

              <span class="document-type">
                {{ displayDocumentType(document.type) }}
              </span>

            </td>


            <!-- =================================================
                 METADATA
                 ================================================= -->
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
                  -
                </span>

              }

            </td>


            <!-- =================================================
                 SOURCE
                 ================================================= -->
            <td>

              <span class="document-source">
                {{ displaySource(document.source) }}
              </span>

            </td>


            <!-- =================================================
                 STATUS
                 ================================================= -->
            <td>

              <span
                [class]="statusClass(document.status)"
                class="document-status"
              >

                <span class="document-status__dot"></span>

                {{ displayStatus(document.status) }}

              </span>

            </td>


            <!-- =================================================
                 ACTIONS
                 ================================================= -->
            <td class="documents__actions-cell">

              <button
                [nzDropdownMenu]="documentMenu"
                class="document-actions-button"
                nz-button
                nz-dropdown
                nz-tooltip
                nzPlacement="bottomRight"
                nzTooltipTitle="Actions"
                nzTrigger="click"
                type="button"
              >

                <span
                  nz-icon
                  nzTheme="outline"
                  nzType="more"
                ></span>

              </button>


              <nz-dropdown-menu #documentMenu="nzDropdownMenu">

                <ul
                  class="document-actions-menu"
                  nz-menu
                >

                  <!-- DOWNLOAD -->
                  <li
                    (click)="onDownload(document)"
                    nz-menu-item
                  >

                    <span
                      nz-icon
                      nzType="download"
                    ></span>

                    <span>Download</span>

                  </li>


                  <!-- EDIT -->
                  <li
                    (click)="onEdit(document)"
                    nz-menu-item
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
                    (click)="onValidate(document)"
                    nz-menu-item
                  >

                    <span
                      nz-icon
                      nzType="check-circle"
                    ></span>

                    <span>Validate</span>

                  </li>


                  <!-- REJECT -->
                  <li
                    (click)="onReject(document)"
                    nz-menu-item
                  >

                    <span
                      nz-icon
                      nzType="close-circle"
                    ></span>

                    <span>Reject</span>

                  </li>


                  <li nz-menu-divider></li>

                </ul>

              </nz-dropdown-menu>

            </td>

          </tr>

        } @empty {

          <!-- =================================================
               EMPTY STATE
               ================================================= -->
          <tr>

            <td
              class="documents-empty"
              colspan="6"
            >

              <span
                class="documents-empty__icon"
                nz-icon
                nzTheme="outline"
                nzType="file"
              ></span>

              <strong>
                No documents
              </strong>

              <span>
                No document is currently associated with this case.
              </span>

            </td>

          </tr>

        }

      </tbody>

    </table>

  </div>


  <!-- =========================================================
       PAGINATION
       ========================================================= -->
  @if (documents().length > 0) {

    <div class="documents__pagination">

      <nz-pagination
        [nzPageIndex]="pageIndex()"
        [nzPageSize]="pageSize()"
        [nzTotal]="documents().length"
        [nzShowSizeChanger]="true"
        [nzPageSizeOptions]="[5, 10, 20, 50]"
        (nzPageIndexChange)="pageIndex.set($event)"
        (nzPageSizeChange)="onPageSizeChange($event)"
      ></nz-pagination>

    </div>

  }

</section>
