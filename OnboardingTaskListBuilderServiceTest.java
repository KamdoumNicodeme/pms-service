<section class="documents">

  <header class="documents__header">
    <div class="documents__heading">
      <div class="documents__title-row">
        <span
          class="documents__title-icon"
          nz-icon
          nzType="file-text"
          nzTheme="outline">
        </span>

        <h2 class="documents__title">
          Documents
        </h2>
      </div>

      <span class="documents__count">
        {{ documentCount() }}
        {{ documentCount() === 1 ? 'document' : 'documents' }}
      </span>
    </div>

    <button
      nz-button
      nzType="primary"
      type="button"
      class="documents__add-button"
      (click)="openUploadModal()">

      <span
        nz-icon
        nzType="plus"
        nzTheme="outline">
      </span>

      Add document
    </button>
  </header>


  <div class="documents__filters">

    <div class="documents__filter">

      <label
        for="metadata-filter"
        class="documents__filter-label">
        Filter by metadata
      </label>

      <nz-input-group
        [nzPrefix]="searchIcon"
        [nzSuffix]="clearIcon">

        <input
          id="metadata-filter"
          nz-input
          type="text"
          placeholder="Search metadata key or value"
          [ngModel]="metadataFilter()"
          (ngModelChange)="onMetadataFilterChange($event)"
        />

      </nz-input-group>

      <ng-template #searchIcon>
        <span
          nz-icon
          nzType="search"
          nzTheme="outline">
        </span>
      </ng-template>

      <ng-template #clearIcon>
        @if (metadataFilter()) {
          <button
            type="button"
            class="documents__clear-filter"
            aria-label="Clear metadata filter"
            (click)="clearMetadataFilter()">

            <span
              nz-icon
              nzType="close-circle"
              nzTheme="fill">
            </span>

          </button>
        }
      </ng-template>

    </div>

    @if (metadataFilter()) {
      <span class="documents__filter-result">
        {{ filteredDocumentCount() }}
        {{ filteredDocumentCount() === 1 ? 'result' : 'results' }}
      </span>
    }

  </div>


  <div class="documents__table-container">

    <nz-table
      #documentsTable
      nzSize="middle"
      [nzData]="filteredDocuments()"
      [nzFrontPagination]="true"
      [nzPageIndex]="pageIndex()"
      [nzPageSize]="pageSize"
      [nzShowSizeChanger]="false"
      [nzShowPagination]="filteredDocuments().length > pageSize"
      [nzHideOnSinglePage]="true"
      (nzPageIndexChange)="pageIndex.set($event)">

      <thead>
        <tr>
          <th class="documents__column-filename">
            Filename
          </th>

          <th class="documents__column-type">
            Document type
          </th>

          <th class="documents__column-metadata">
            Metadata
          </th>

          <th class="documents__column-source">
            Source
          </th>

          <th class="documents__column-status">
            Status
          </th>

          <th class="documents__column-actions">
            Actions
          </th>
        </tr>
      </thead>


      <tbody>

        @for (
          document of documentsTable.data;
          track document.documentId
        ) {

          <tr>

            <td>
              <div class="document-file">

                <span
                  class="document-file__icon"
                  nz-icon
                  nzType="file-text"
                  nzTheme="outline">
                </span>

                <div class="document-file__content">

                  <button
                    type="button"
                    class="document-file__name"
                    nz-tooltip
                    [nzTooltipTitle]="document.name"
                    (click)="onDownload(document)">
                    {{ document.name }}
                  </button>

                  <span class="document-file__id">
                    {{ document.documentId }}
                  </span>

                </div>

              </div>
            </td>


            <td>
              <span class="document-type">
                {{ displayDocumentType(document.type) }}
              </span>
            </td>


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

                      <span
                        class="document-metadata__value"
                        nz-tooltip
                        [nzTooltipTitle]="metadata[1]">
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


            <td>
              <span class="document-source">
                {{ displaySource(document.source) }}
              </span>
            </td>


            <td>

              <span
                class="document-status"
                [ngClass]="statusClass(document.status)">

                <span class="document-status__dot"></span>

                {{ displayStatus(document.status) }}

              </span>

            </td>


            <td class="documents__actions-cell">

              <button
                nz-button
                nzType="text"
                nz-dropdown
                nzTrigger="click"
                nzPlacement="bottomRight"
                [nzDropdownMenu]="documentActions"
                type="button"
                class="document-actions-button">

                <span
                  nz-icon
                  nzType="more"
                  nzTheme="outline">
                </span>

              </button>


              <nz-dropdown-menu
                #documentActions="nzDropdownMenu">

                <ul
                  nz-menu
                  class="document-actions-menu">

                  <li
                    nz-menu-item
                    (click)="onDownload(document)">

                    <span
                      nz-icon
                      nzType="download"
                      nzTheme="outline">
                    </span>

                    <span>
                      Download
                    </span>

                  </li>


                  <li
                    nz-menu-item
                    (click)="openEditDocument(document)">

                    <span
                      nz-icon
                      nzType="edit"
                      nzTheme="outline">
                    </span>

                    <span>
                      Edit
                    </span>

                  </li>


                  <li nz-menu-divider></li>


                  <li
                    nz-menu-item
                    (click)="onValidate(document)">

                    <span
                      nz-icon
                      nzType="check-circle"
                      nzTheme="outline">
                    </span>

                    <span>
                      Validate
                    </span>

                  </li>


                  <li
                    nz-menu-item
                    (click)="onReject(document)">

                    <span
                      nz-icon
                      nzType="close-circle"
                      nzTheme="outline">
                    </span>

                    <span>
                      Reject
                    </span>

                  </li>


                  <li nz-menu-divider></li>


                  <li
                    nz-menu-item
                    nzDanger
                    (click)="onRemove(document)">

                    <span
                      nz-icon
                      nzType="delete"
                      nzTheme="outline">
                    </span>

                    <span>
                      Remove
                    </span>

                  </li>

                </ul>

              </nz-dropdown-menu>

            </td>

          </tr>

        }

      </tbody>

    </nz-table>


    @if (filteredDocuments().length === 0) {

      <div class="documents__empty">

        <span
          class="documents__empty-icon"
          nz-icon
          nzType="file-search"
          nzTheme="outline">
        </span>


        @if (metadataFilter()) {

          <strong>
            No matching documents
          </strong>

          <span>
            No document matches "{{ metadataFilter() }}".
          </span>

          <button
            nz-button
            nzType="link"
            type="button"
            (click)="clearMetadataFilter()">
            Clear filter
          </button>

        } @else {

          <strong>
            No documents
          </strong>

          <span>
            No document is currently associated with this case.
          </span>

        }

      </div>

    }

  </div>


  @if (uploadModalVisible()) {

    <upload-document
      [getDocumentConfigType]="getDocumentConfigType()"
      [getDocumentMetadata]="getDocumentMetadata()"
      (uploadDocument)="onUploadDocument($event)"
      (closeModal)="closeUploadModal()">
    </upload-document>

  }


  @if (editingDocument(); as document) {

    <edit-document
      [document]="document"
      [getDocumentConfigType]="getDocumentConfigType()"
      [getDocumentMetadata]="getDocumentMetadata()"
      (editDocument)="onEditDocumentSubmitted($event)"
      (closeModal)="closeEditDocument()">
    </edit-document>

  }

</section>
