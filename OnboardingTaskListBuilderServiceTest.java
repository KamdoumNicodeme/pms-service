:host {
  display: block;
  width: 100%;
  min-width: 0;
}

.documents {
  width: 100%;
  min-width: 0;
  box-sizing: border-box;
}


/* ============================================================
   HEADER
   ============================================================ */

.documents__header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 1rem;

  margin-bottom: 1rem;
}

.documents__heading {
  min-width: 0;
}

.documents__title-row {
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

.documents__title-icon {
  color: #8a6534;
  font-size: 1.25rem;
}

.documents__title {
  margin: 0;

  color: #5c4426;

  font-size: 1.125rem;
  font-weight: 700;
  line-height: 1.4;
}

.documents__count {
  display: block;

  margin-top: 0.25rem;

  color: #8b8175;

  font-size: 0.8125rem;
  line-height: 1.4;
}

.documents__add-button {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  gap: 0.375rem;

  flex: none;

  min-height: 36px;

  border-color: #8a6534;
  background: #8a6534;

  box-shadow: none;

  font-weight: 600;
}

.documents__add-button:hover,
.documents__add-button:focus {
  border-color: #725229;
  background: #725229;
}


/* ============================================================
   FILTERS
   ============================================================ */

.documents__filters {
  display: flex;
  align-items: flex-end;
  justify-content: space-between;
  gap: 1rem;

  margin-bottom: 1rem;
}

.documents__filter {
  width: min(420px, 100%);
}

.documents__filter-label {
  display: block;

  margin-bottom: 0.375rem;

  color: #5c4426;

  font-size: 0.8125rem;
  font-weight: 600;
}

.documents__filter-result {
  flex: none;

  padding-bottom: 0.5rem;

  color: #8b8175;

  font-size: 0.8125rem;
}

.documents__clear-filter {
  display: inline-flex;
  align-items: center;
  justify-content: center;

  padding: 0;

  border: 0;

  background: transparent;

  color: #a0978d;

  cursor: pointer;
}

.documents__clear-filter:hover {
  color: #725229;
}

:host ::ng-deep .documents__filter .ant-input-affix-wrapper {
  min-height: 38px;

  border-color: #ddd4c8;
  border-radius: 4px;

  box-shadow: none;
}

:host ::ng-deep .documents__filter .ant-input-affix-wrapper:hover {
  border-color: #a98a61;
}

:host ::ng-deep .documents__filter .ant-input-affix-wrapper-focused {
  border-color: #8a6534;

  box-shadow: 0 0 0 2px rgb(138 101 52 / 10%);
}

:host ::ng-deep .documents__filter .ant-input-prefix {
  margin-right: 0.5rem;

  color: #9b9186;
}

:host ::ng-deep .documents__filter .ant-input {
  color: #554b40;
}

:host ::ng-deep .documents__filter .ant-input::placeholder {
  color: #aaa198;
}


/* ============================================================
   TABLE CONTAINER
   ============================================================ */

.documents__table-container {
  width: 100%;
  min-width: 0;

  overflow: visible;
}


/* ============================================================
   NZ TABLE
   ============================================================ */

:host ::ng-deep .ant-table-wrapper {
  width: 100%;
  min-width: 0;
}

:host ::ng-deep .ant-table {
  width: 100%;

  overflow: visible;

  border: 1px solid #e5ddd2;
  border-radius: 6px;

  background: #ffffff;

  box-shadow: none;
}

:host ::ng-deep .ant-table-container {
  overflow: visible !important;

  border-radius: 6px;
}

:host ::ng-deep .ant-table-content {
  overflow: visible !important;
}

:host ::ng-deep .ant-table-content table {
  width: 100%;

  table-layout: fixed;
}


/* ============================================================
   TABLE HEADER
   ============================================================ */

:host ::ng-deep .ant-table-thead > tr > th {
  padding: 0.75rem 1rem;

  border-bottom: 1px solid #ded4c7;

  background: #f4f0ea;

  color: #60492d;

  font-size: 0.75rem;
  font-weight: 700;
  line-height: 1.4;

  white-space: nowrap;
}

:host ::ng-deep .ant-table-thead > tr > th::before {
  display: none;
}


/* ============================================================
   TABLE BODY
   ============================================================ */

:host ::ng-deep .ant-table-tbody > tr > td {
  padding: 0.75rem 1rem;

  border-bottom: 1px solid #eee8e0;

  background: #ffffff;

  color: #5f554a;

  font-size: 0.8125rem;
  line-height: 1.4;

  vertical-align: middle;
}

:host ::ng-deep .ant-table-tbody > tr:last-child > td {
  border-bottom: 0;
}

:host ::ng-deep .ant-table-tbody > tr:hover > td {
  background: #fcfaf7;
}


/* ============================================================
   COLUMNS
   ============================================================ */

.documents__column-filename {
  width: 25%;
}

.documents__column-type {
  width: 15%;
}

.documents__column-metadata {
  width: 25%;
}

.documents__column-source {
  width: 12%;
}

.documents__column-status {
  width: 13%;
}

.documents__column-actions {
  width: 10%;

  text-align: center;
}


/* ============================================================
   FILE
   ============================================================ */

.document-file {
  display: flex;
  align-items: center;
  gap: 0.625rem;

  min-width: 0;
}

.document-file__icon {
  display: inline-flex;
  align-items: center;
  justify-content: center;

  flex: 0 0 32px;

  width: 32px;
  height: 32px;

  border-radius: 4px;

  background: #f3eee7;

  color: #8a6534;

  font-size: 1rem;
}

.document-file__content {
  display: flex;
  flex-direction: column;

  min-width: 0;
}

.document-file__name {
  display: block;

  width: 100%;
  max-width: 220px;

  padding: 0;

  overflow: hidden;

  border: 0;

  background: transparent;

  color: #60492d;

  font-family: inherit;
  font-size: 0.8125rem;
  font-weight: 600;

  text-align: left;
  text-overflow: ellipsis;

  white-space: nowrap;

  cursor: pointer;
}

.document-file__name:hover {
  color: #8a6534;

  text-decoration: underline;
}

.document-file__name:focus {
  outline: none;

  color: #8a6534;
}

.document-file__id {
  display: block;

  max-width: 220px;

  margin-top: 0.125rem;

  overflow: hidden;

  color: #a0978d;

  font-size: 0.6875rem;

  text-overflow: ellipsis;
  white-space: nowrap;
}


/* ============================================================
   DOCUMENT TYPE
   ============================================================ */

.document-type {
  display: block;

  overflow: hidden;

  color: #62574c;

  text-overflow: ellipsis;
  white-space: nowrap;
}


/* ============================================================
   METADATA
   ============================================================ */

.document-metadata {
  display: flex;
  flex-direction: column;
  gap: 0.1875rem;

  min-width: 0;
}

.document-metadata__item {
  display: flex;
  align-items: baseline;
  gap: 0.25rem;

  min-width: 0;
}

.document-metadata__key {
  flex: none;

  color: #806b52;

  font-size: 0.75rem;
  font-weight: 600;

  white-space: nowrap;
}

.document-metadata__value {
  display: block;

  min-width: 0;

  overflow: hidden;

  color: #62584e;

  font-size: 0.75rem;

  text-overflow: ellipsis;
  white-space: nowrap;
}

.documents__empty-value {
  color: #aaa198;
}


/* ============================================================
   SOURCE
   ============================================================ */

.document-source {
  display: block;

  overflow: hidden;

  color: #62584e;

  text-overflow: ellipsis;
  white-space: nowrap;
}


/* ============================================================
   STATUS
   ============================================================ */

.document-status {
  display: inline-flex;
  align-items: center;
  gap: 0.375rem;

  padding: 0.25rem 0.5rem;

  border-radius: 999px;

  background: #f5f1eb;

  color: #76634d;

  font-size: 0.75rem;
  font-weight: 600;

  white-space: nowrap;
}

.document-status__dot {
  display: block;

  flex: none;

  width: 7px;
  height: 7px;

  border-radius: 50%;

  background: #c18b2e;
}

.document-status--pending {
  background: #f8f3e8;

  color: #8a651d;
}

.document-status--pending .document-status__dot {
  background: #c18b2e;
}

.document-status--validated {
  background: #edf7f0;

  color: #34754a;
}

.document-status--validated .document-status__dot {
  background: #4b9a64;
}

.document-status--rejected {
  background: #fbefef;

  color: #a44040;
}

.document-status--rejected .document-status__dot {
  background: #c75050;
}


/* ============================================================
   ACTIONS
   ============================================================ */

.documents__actions-cell {
  text-align: center;
}

.document-actions-button {
  display: inline-flex;
  align-items: center;
  justify-content: center;

  width: 32px;
  min-width: 32px;
  height: 32px;

  padding: 0;

  border: 0;

  border-radius: 4px;

  background: transparent;

  color: #766653;
}

.document-actions-button:hover,
.document-actions-button:focus {
  background: #f3eee7;

  color: #8a6534;
}


/* ============================================================
   DROPDOWN
   ============================================================ */

:host ::ng-deep .document-actions-menu {
  min-width: 150px;
}

:host ::ng-deep .document-actions-menu .ant-dropdown-menu-item {
  display: flex;
  align-items: center;
  gap: 0.5rem;

  color: #5f554a;

  font-size: 0.8125rem;
}

:host ::ng-deep .document-actions-menu .ant-dropdown-menu-item:hover {
  background: #f7f3ed;
}

:host ::ng-deep .document-actions-menu .anticon {
  color: #806b52;
}


/* ============================================================
   PAGINATION
   ============================================================ */

:host ::ng-deep .ant-table-pagination {
  display: flex;
  align-items: center;
  justify-content: flex-end;

  margin: 1rem 0 0 !important;
}

:host ::ng-deep .ant-pagination-item {
  border-color: #ded5ca;

  border-radius: 4px;
}

:host ::ng-deep .ant-pagination-item a {
  color: #62584e;
}

:host ::ng-deep .ant-pagination-item:hover {
  border-color: #8a6534;
}

:host ::ng-deep .ant-pagination-item:hover a {
  color: #8a6534;
}

:host ::ng-deep .ant-pagination-item-active {
  border-color: #8a6534;

  background: #8a6534;
}

:host ::ng-deep .ant-pagination-item-active a {
  color: #ffffff;
}

:host ::ng-deep .ant-pagination-item-active:hover a {
  color: #ffffff;
}

:host ::ng-deep .ant-pagination-prev button,
:host ::ng-deep .ant-pagination-next button {
  border-radius: 4px;

  color: #62584e;
}


/* ============================================================
   EMPTY STATE
   ============================================================ */

.documents__empty {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;

  width: 100%;
  min-height: 180px;

  padding: 2rem;

  box-sizing: border-box;

  border: 1px solid #e5ddd2;
  border-top: 0;
  border-radius: 0 0 6px 6px;

  background: #ffffff;

  color: #8b8175;

  text-align: center;
}

.documents__empty-icon {
  margin-bottom: 0.75rem;

  color: #b39a79;

  font-size: 2rem;
}

.documents__empty strong {
  margin-bottom: 0.25rem;

  color: #60492d;

  font-size: 0.875rem;
  font-weight: 700;
}

.documents__empty > span:not(.documents__empty-icon) {
  color: #948a7e;

  font-size: 0.8125rem;
}

.documents__empty button {
  margin-top: 0.5rem;

  color: #8a6534;
}


/* ============================================================
   TOOLTIP / TEXT
   ============================================================ */

:host ::ng-deep .ant-tooltip-inner {
  font-size: 0.75rem;
}


/* ============================================================
   NO INTERNAL SCROLL
   ============================================================ */

:host ::ng-deep .ant-table-body,
:host ::ng-deep .ant-table-content,
:host ::ng-deep .ant-table-container {
  max-height: none !important;

  overflow-x: visible !important;
  overflow-y: visible !important;
}


/* ============================================================
   RESPONSIVE
   ============================================================ */

@media (max-width: 1100px) {
  .documents__column-filename {
    width: 24%;
  }

  .documents__column-type {
    width: 15%;
  }

  .documents__column-metadata {
    width: 27%;
  }

  .documents__column-source {
    width: 11%;
  }

  .documents__column-status {
    width: 14%;
  }

  .documents__column-actions {
    width: 9%;
  }

  .document-file__name,
  .document-file__id {
    max-width: 160px;
  }
}

@media (max-width: 900px) {
  .documents__header {
    align-items: flex-start;
  }

  .documents__filters {
    align-items: stretch;
    flex-direction: column;
  }

  .documents__filter {
    width: 100%;
  }

  .documents__filter-result {
    padding-bottom: 0;
  }

  .document-file__name,
  .document-file__id {
    max-width: 120px;
  }
}
