:host {
  display: block;

  --document-gold: #9d7927;
  --document-gold-dark: #80601d;
  --document-gold-light: #f3ead6;
  --document-gold-soft: #faf7f0;

  --document-border: #e8e2d7;
  --document-text: #29251f;
  --document-muted: #77716a;

  --document-success: #389e0d;
  --document-success-bg: #f0f9eb;

  --document-warning: #d48806;
  --document-warning-bg: #fff8e6;

  --document-danger: #cf3d3d;
  --document-danger-bg: #fff1f0;
}


/* =========================================================
   SECTION
   ========================================================= */

.documents {
  width: 100%;
  padding: 1.25rem 1.5rem 2rem;
}


/* =========================================================
   HEADER
   ========================================================= */

.documents__header {
  display: flex;
  align-items: center;
  justify-content: space-between;

  margin-bottom: 1rem;
}


.documents__title {
  display: flex;
  align-items: center;
  gap: 0.75rem;

  h2 {
    margin: 0;
    color: var(--document-text);
    font-size: 1rem;
    font-weight: 600;
    line-height: 1.25rem;
  }
}


.documents__title-icon {
  display: inline-flex;
  align-items: center;
  justify-content: center;

  width: 2rem;
  height: 2rem;

  border-radius: 50%;

  color: var(--document-gold);
  background: var(--document-gold-soft);

  font-size: 1rem;
}


.documents__subtitle {
  display: block;

  margin-top: 0.15rem;

  color: var(--document-muted);
  font-size: 0.75rem;
}


/* =========================================================
   ADD BUTTON
   ========================================================= */

.documents__add-button {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  gap: 0.5rem;

  height: 2.25rem;

  padding: 0 1rem;

  border: 1px solid var(--document-gold);
  border-radius: 3px;

  background: var(--document-gold);
  color: #fff;

  font-weight: 600;

  box-shadow: 0 2px 5px rgba(80, 60, 20, 0.12);

  transition:
    background 0.15s ease,
    border-color 0.15s ease,
    box-shadow 0.15s ease;

  &:hover,
  &:focus {
    background: var(--document-gold-dark);
    border-color: var(--document-gold-dark);
    color: #fff;

    box-shadow: 0 3px 8px rgba(80, 60, 20, 0.18);
  }
}


/* =========================================================
   TABLE CONTAINER
   ========================================================= */

.documents__table-wrapper {
  width: 100%;

  overflow: visible;

  border: 1px solid var(--document-border);
  border-radius: 4px;

  background: #fff;
}


/* =========================================================
   TABLE
   ========================================================= */

.documents__table {
  width: 100%;

  border-collapse: separate;
  border-spacing: 0;

  table-layout: fixed;

  color: var(--document-text);
  font-size: 0.875rem;


  th,
  td {
    vertical-align: middle;
  }


  /* HEADER */

  thead th {
    height: 2.75rem;

    padding: 0 1rem;

    text-align: left;

    background:
      linear-gradient(
        180deg,
        #a98532 0%,
        #967120 100%
      );

    color: #fff;

    font-size: 0.78rem;
    font-weight: 600;

    border-right: 1px solid rgba(255, 255, 255, 0.32);

    &:last-child {
      border-right: none;
    }
  }


  thead th:first-child {
    border-top-left-radius: 3px;
  }


  thead th:last-child {
    border-top-right-radius: 3px;
  }


  /* ROW */

  tbody td {
    min-height: 3.75rem;

    padding: 0.75rem 1rem;

    background: #fff;

    border-bottom: 1px solid var(--document-border);
    border-right: 1px solid var(--document-border);
  }


  tbody td:last-child {
    border-right: none;
  }


  tbody tr:last-child td {
    border-bottom: none;
  }


  tbody tr {
    transition: background 0.15s ease;
  }


  tbody tr:hover td {
    background: var(--document-gold-soft);
  }
}


/* =========================================================
   COLUMN WIDTHS
   ========================================================= */

.documents__filename-column {
  width: 32%;
}

.documents__type-column {
  width: 15%;
}

.documents__metadata-column {
  width: 25%;
}

.documents__source-column {
  width: 9%;
}

.documents__status-column {
  width: 11%;
}

.documents__actions-column {
  width: 8%;
  text-align: center !important;
}


/* =========================================================
   FILE
   ========================================================= */

.document-file {
  display: flex;
  align-items: center;

  min-width: 0;

  gap: 0.75rem;
}


.document-file__icon {
  flex: 0 0 auto;

  display: inline-flex;
  align-items: center;
  justify-content: center;

  width: 2rem;
  height: 2rem;

  border-radius: 3px;

  background: var(--document-gold-soft);
  color: var(--document-gold);

  font-size: 1rem;
}


.document-file__info {
  min-width: 0;

  display: flex;
  flex-direction: column;

  gap: 0.15rem;
}


.document-file__name {
  max-width: 100%;

  padding: 0;

  overflow: hidden;

  border: none;
  background: transparent;

  color: var(--document-text);

  font: inherit;
  font-weight: 600;

  text-align: left;
  text-overflow: ellipsis;
  white-space: nowrap;

  cursor: pointer;

  &:hover {
    color: var(--document-gold);
    text-decoration: underline;
  }
}


.document-file__id {
  color: var(--document-muted);

  font-size: 0.7rem;
}


/* =========================================================
   TYPE
   ========================================================= */

.document-type {
  color: var(--document-text);
  font-size: 0.8rem;
}


/* =========================================================
   METADATA
   ========================================================= */

.document-metadata {
  display: flex;
  flex-wrap: wrap;

  gap: 0.25rem 0.75rem;
}


.document-metadata__item {
  display: inline-flex;
  align-items: baseline;

  min-width: 0;

  gap: 0.25rem;

  font-size: 0.76rem;
}


.document-metadata__key {
  color: var(--document-muted);
  font-weight: 500;
}


.document-metadata__value {
  overflow: hidden;

  color: var(--document-text);
  font-weight: 600;

  text-overflow: ellipsis;
  white-space: nowrap;
}


.documents__empty-value {
  color: #aaa;
}


/* =========================================================
   SOURCE
   ========================================================= */

.document-source {
  display: inline-flex;

  padding: 0.15rem 0.5rem;

  border: 1px solid var(--document-border);
  border-radius: 3px;

  background: #fafafa;

  color: #555;

  font-size: 0.72rem;
  font-weight: 600;
}


/* =========================================================
   STATUS
   ========================================================= */

.document-status {
  display: inline-flex;
  align-items: center;

  gap: 0.4rem;

  padding: 0.25rem 0.55rem;

  border-radius: 10rem;

  font-size: 0.72rem;
  font-weight: 600;
}


.document-status__dot {
  display: block;

  width: 0.4rem;
  height: 0.4rem;

  border-radius: 50%;

  background: currentColor;
}


.document-status--validated {
  color: var(--document-success);
  background: var(--document-success-bg);
}


.document-status--rejected {
  color: var(--document-danger);
  background: var(--document-danger-bg);
}


.document-status--pending {
  color: var(--document-warning);
  background: var(--document-warning-bg);
}


/* =========================================================
   ACTION
   ========================================================= */

.documents__actions-cell {
  position: relative;

  text-align: center;
}


.document-actions-button {
  display: inline-flex;
  align-items: center;
  justify-content: center;

  width: 2rem;
  height: 2rem;

  padding: 0;

  border: 1px solid #d8cbb2;
  border-radius: 3px;

  background: #fff;
  color: var(--document-gold-dark);

  box-shadow: none;

  &:hover,
  &:focus {
    border-color: var(--document-gold);

    background: var(--document-gold-soft);
    color: var(--document-gold);
  }
}


/* =========================================================
   EMPTY STATE
   ========================================================= */

.documents-empty {
  height: 10rem;

  text-align: center;
}


.documents-empty__icon {
  display: block;

  margin-bottom: 0.5rem;

  color: #b9ad98;

  font-size: 1.75rem;
}


.documents-empty strong {
  display: block;

  margin-bottom: 0.2rem;

  color: var(--document-text);
}


.documents-empty span:not(.documents-empty__icon) {
  color: var(--document-muted);
  font-size: 0.8rem;
}


/* =========================================================
   NG-ZORRO DROPDOWN

   IMPORTANT:
   le dropdown est rendu dans l'overlay global.
   On ne met surtout PAS display:none sur nz-dropdown-menu.
   ========================================================= */

:host ::ng-deep .document-actions-menu {
  min-width: 10rem;

  padding: 0.3rem;

  border-radius: 4px;

  box-shadow:
    0 4px 12px rgba(0, 0, 0, 0.12);
}


:host ::ng-deep .document-actions-menu .ant-dropdown-menu-item {
  display: flex;
  align-items: center;

  gap: 0.65rem;

  min-height: 2rem;

  padding: 0.4rem 0.65rem;

  border-radius: 3px;

  font-size: 0.8rem;
}


:host ::ng-deep .document-actions-menu .ant-dropdown-menu-item:hover {
  background: var(--document-gold-soft);
}


:host ::ng-deep .document-actions-menu .anticon {
  color: var(--document-gold);

  font-size: 0.85rem;
}


/* =========================================================
   RESPONSIVE
   ========================================================= */

@media (max-width: 1100px) {

  .documents {
    padding-inline: 1rem;
  }

  .documents__table-wrapper {
    overflow-x: auto;
  }

  .documents__table {
    min-width: 950px;
  }
}
