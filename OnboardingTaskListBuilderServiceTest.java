:host {
  display: block;
  width: 100%;
  min-width: 0;
}

/* =========================================================
   DOCUMENTS
   ========================================================= */

.documents {
  width: 100%;
  padding: 1rem 1.25rem 2rem;
  box-sizing: border-box;

  /* =======================================================
     TABLE
     ======================================================= */

  &__table {
    width: 100%;
    overflow: hidden;

    background: #fff;

    border: 1px solid #e8e3da;
    border-radius: 4px;
  }

  /* =======================================================
     ROW
     ======================================================= */

  &__row {
    display: grid;

    grid-template-columns:
      minmax(20rem, 3fr)   /* Filename */
      minmax(11rem, 1.2fr) /* Document type */
      minmax(18rem, 2fr)   /* Metadata */
      minmax(7rem, 0.8fr)  /* Source */
      minmax(7rem, 0.8fr)  /* Status */
      5rem;                /* Actions */

    width: 100%;
    min-height: 3.5rem;

    background: #fff;

    border-bottom: 1px solid #e8e3da;

    transition: background-color 0.15s ease;

    &:last-child {
      border-bottom: 0;
    }

    &:not(&--header):hover {
      background: #faf8f4;
    }

    > div {
      min-width: 0;

      display: flex;
      align-items: center;

      padding: 0.7rem 0.9rem;

      border-right: 1px solid #e8e3da;

      box-sizing: border-box;

      &:last-child {
        border-right: 0;
      }
    }

    /* =====================================================
       TABLE HEADER
       ===================================================== */

    &--header {
      min-height: 2.8rem;

      background: #9b792c;

      color: #fff;
      font-size: 0.8rem;
      font-weight: 600;

      > div {
        color: #fff;

        border-right: 1px solid rgba(255, 255, 255, 0.55);
      }
    }
  }

  /* =======================================================
     FILENAME
     ======================================================= */

  &__filename {
    color: #202020;
    font-weight: 600;

    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }

  /* =======================================================
     METADATA
     ======================================================= */

  &__metadata {
    display: flex !important;
    flex-wrap: wrap;
    align-items: center;

    gap: 0.2rem;

    color: #555;
    font-size: 0.82rem;
    line-height: 1.4;

    &-entry {
      display: inline-flex;
      gap: 0.2rem;

      white-space: nowrap;

      strong {
        color: #333;
        font-weight: 600;
      }

      &:not(:last-child)::after {
        content: ",";
      }
    }
  }

  /* =======================================================
     STATUS
     ======================================================= */

  &__status {
    font-size: 0.82rem;
    font-weight: 500;

    &--validated {
      color: #389e0d;
    }

    &--rejected {
      color: #cf1322;
    }

    &--pending {
      color: #8c8c8c;
    }
  }

  /* =======================================================
     ACTION COLUMN
     ======================================================= */

  &__actions {
    display: flex !important;
    align-items: center;
    justify-content: center;

    overflow: visible;
  }

  /* =======================================================
     THREE DOT BUTTON
     ======================================================= */

  &__menu-button {
    width: 2rem;
    min-width: 2rem;
    height: 2rem;

    padding: 0;

    display: inline-flex;
    align-items: center;
    justify-content: center;

    background: #fff;

    border: 1px solid #d7d0c3;
    border-radius: 3px;

    color: #5c4a27;

    cursor: pointer;

    box-shadow: none;

    transition:
      border-color 0.15s ease,
      color 0.15s ease,
      background-color 0.15s ease;

    &:hover,
    &:focus {
      color: #9b792c;
      border-color: #9b792c;
      background: #faf8f4;
    }
  }

  /* =======================================================
     FOOTER
     ======================================================= */

  &__footer {
    width: 100%;

    display: flex;
    align-items: center;

    margin-top: 1rem;
  }

  /* =======================================================
     ADD DOCUMENT BUTTON
     ======================================================= */

  &__add-button {
    min-height: 2.3rem;

    display: inline-flex;
    align-items: center;
    justify-content: center;

    gap: 0.5rem;

    padding: 0 1rem;

    background: #9b792c;
    border-color: #9b792c;

    color: #fff;
    font-weight: 600;

    border-radius: 2px;

    &:hover,
    &:focus {
      background: #876823;
      border-color: #876823;
      color: #fff;
    }
  }

  /* =======================================================
     EMPTY STATE
     ======================================================= */

  &__empty {
    width: 100%;

    padding: 3rem 1rem;

    box-sizing: border-box;

    text-align: center;

    color: #8c8c8c;
    background: #fff;
  }
}


/* =========================================================
   NG-ZORRO DROPDOWN

   Le dropdown est créé dans un overlay en dehors du composant.
   ::ng-deep est donc nécessaire ici si tu veux garder ce style
   dans documents.scss.
   ========================================================= */

:host ::ng-deep .ant-dropdown {
  min-width: 10rem;
}

:host ::ng-deep .ant-dropdown-menu {
  min-width: 10rem;

  margin: 0 !important;
  padding: 0.25rem 0 !important;

  list-style: none !important;

  background: #fff;

  border: 1px solid #e8e3da;
  border-radius: 3px;

  box-shadow:
    0 3px 6px -4px rgba(0, 0, 0, 0.12),
    0 6px 16px rgba(0, 0, 0, 0.08);
}


/* =========================================================
   DROPDOWN ITEMS
   ========================================================= */

:host ::ng-deep .ant-dropdown-menu-item {
  min-height: 2.25rem;

  display: flex !important;
  align-items: center;

  gap: 0.5rem;

  margin: 0 !important;
  padding: 0.45rem 0.8rem !important;

  list-style: none !important;

  color: #333;

  cursor: pointer;

  &::marker {
    content: "";
    display: none;
  }

  &:hover {
    background: #faf8f4;
    color: #9b792c;
  }

  nz-icon {
    width: 1rem;

    display: inline-flex;
    align-items: center;
    justify-content: center;
  }
}


/* =========================================================
   DROPDOWN DIVIDER
   ========================================================= */

:host ::ng-deep .ant-dropdown-menu-item-divider {
  margin: 0.25rem 0 !important;

  list-style: none !important;

  border-top-color: #eee9e0;
}


/* =========================================================
   DANGER ACTION
   ========================================================= */

:host ::ng-deep .ant-dropdown-menu-item-danger {
  color: #cf1322;

  &:hover {
    color: #cf1322;
    background: #fff1f0;
  }
}


/* =========================================================
   REMOVE NATIVE LIST BULLETS

   Sécurité supplémentaire au cas où un style global de
   l'application remet les puces sur les <li>.
   ========================================================= */

:host ::ng-deep .ant-dropdown-menu,
:host ::ng-deep .ant-dropdown-menu-submenu {
  list-style-type: none !important;
}

:host ::ng-deep .ant-dropdown-menu li {
  list-style: none !important;
}

:host ::ng-deep .ant-dropdown-menu li::marker {
  content: "" !important;
}


/* =========================================================
   RESPONSIVE
   ========================================================= */

@media (max-width: 1200px) {
  .documents {
    padding-inline: 0.75rem;

    &__table {
      overflow-x: auto;
    }

    &__row {
      min-width: 65rem;
    }
  }
}
