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


  <div class="documents__table-container">

    <nz-table
      #documentsTable
      nzSize="middle"
      [nzData]="documents()"
      [nzFrontPagination]="true"
      [nzPageIndex]="pageIndex()"
      [nzPageSize]="pageSize"
      [nzShowSizeChanger]="false"
      [nzHideOnSinglePage]="true"
      (nzPageIndexChange)="pageIndex.set($event)">

      <thead>

        <tr>

          <th class="documents__column-filename">
            Filename
          </th>

          <th
            class="documents__column-type"
            [nzSortFn]="sortByDocumentType">
            Document type
          </th>

          <th class="documents__column-metadata">
            Metadata
          </th>

          <th
            class="documents__column-source"
            [nzSortFn]="sortBySource">
            Source
          </th>

          <th
            class="documents__column-status"
            [nzSortFn]="sortByStatus">
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

                  <span
                    class="document-file__id"
                    nz-tooltip
                    [nzTooltipTitle]="document.documentId">

                    {{ document.documentId }}

                  </span>

                </div>

              </div>

            </td>


            <td>

              <span
                class="document-type"
                nz-tooltip
                [nzTooltipTitle]="displayDocumentType(document.type)">

                {{ displayDocumentType(document.type) }}

              </span>

            </td>


            <td>

              @if (
                primaryMetadata(document.metadata);
                as metadata
              ) {

                <div
                  class="document-metadata"
                  nz-tooltip
                  [nzTooltipTitle]="metadataTooltip">

                  <div class="document-metadata__primary">

                    <span class="document-metadata__key">
                      {{ metadata[0] }}:
                    </span>

                    <span class="document-metadata__value">
                      {{ metadata[1] }}
                    </span>

                    @if (
                      remainingMetadata(document.metadata).length > 0
                    ) {

                      <span class="document-metadata__more">
                        +{{ remainingMetadata(document.metadata).length }}
                      </span>

                    }

                  </div>

                </div>


                <ng-template #metadataTooltip>

                  <div class="document-metadata-tooltip">

                    @for (
                      item of metadataEntries(document.metadata);
                      track item[0]
                    ) {

                      <div class="document-metadata-tooltip__item">

                        <strong>
                          {{ item[0] }}:
                        </strong>

                        <span>
                          {{ item[1] }}
                        </span>

                      </div>

                    }

                  </div>

                </ng-template>

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

                    Download

                  </li>


                  <li
                    nz-menu-item
                    (click)="openEditDocument(document)">

                    <span
                      nz-icon
                      nzType="edit"
                      nzTheme="outline">
                    </span>

                    Edit

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

                    Validate

                  </li>


                  <li
                    nz-menu-item
                    (click)="onReject(document)">

                    <span
                      nz-icon
                      nzType="close-circle"
                      nzTheme="outline">
                    </span>

                    Reject

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

                    Remove

                  </li>

                </ul>

              </nz-dropdown-menu>

            </td>

          </tr>

        }

      </tbody>

    </nz-table>

  </div>


  @if (uploadModalVisible()) {

    <!-- KEEP YOUR EXISTING UPLOAD MODAL EXACTLY HERE -->

  }


  @if (editingDocument(); as document) {

    <!-- KEEP YOUR EXISTING EDIT MODAL EXACTLY HERE -->

  }

</section>
