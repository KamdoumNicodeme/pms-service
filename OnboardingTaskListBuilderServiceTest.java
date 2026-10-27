.holder-tab {
  display: inline-flex;
  align-items: center;
  gap: 0.4rem;

  &__avatar {
    font-size: 0.85rem;
    color: #b0aca3;
  }

  &__state {
    font-size: 0.8rem;
    color: #2cc12c;

    &--pending {
      color: var(--cmp-drift, #d98e04);
    }
  }

  &[data-state='pending'] &__state {
    color: var(--cmp-drift, #d98e04);
  }
}

.card {
  width: 10rem;
  display: flex;
  flex-direction: column;
  text-align: center;
  border: 1px solid var(--ant-primary-3);

  label {
    width: 100%;
    background: linear-gradient(
      180deg,
      var(--ant-primary-1),
      var(--ant-primary-2)
    );
    padding: 0.2rem 0;
    font-weight: 600;
    font-size: 0.9rem;
    color: var(--ant-primary-7);
  }
}

/*
 * IMPORTANT:
 * On ne force plus les tabs NG-ZORRO à prendre height: 100%.
 * Le scroll est géré par .change-client-information__main.
 */
:host ::ng-deep {
  .ant-tabs {
    width: 100%;
  }

  .ant-tabs-content-holder {
    width: 100%;
  }

  .ant-tabs-content {
    width: 100%;
  }

  .ant-tabs-tabpane {
    width: 100%;
  }
}

/*
 * Layout principal :
 *
 *  MAIN avec scroll     | CHECKS
 *  ↕                    | fixe
 */
.change-client-information__layout {
  display: flex;
  flex: 1 1 0;

  width: 100%;
  min-width: 0;
  min-height: 0;

  gap: 1rem;

  /*
   * Empêche le layout complet de déborder.
   * Le scroll sera porté par __main.
   */
  overflow: hidden;
}

/*
 * PARTIE CENTRALE
 *
 * C'est CET élément qui doit avoir
 * la scrollbar verticale.
 */
.change-client-information__main {
  flex: 1 1 auto;

  min-width: 0;
  min-height: 0;

  display: block;

  overflow-x: hidden;
  overflow-y: auto;

  /*
   * Évite que la scrollbar soit collée
   * directement aux checks.
   */
  padding-right: 0.25rem;

  /*
   * Important pour les navigateurs utilisant
   * des layouts flex imbriqués.
   */
  overscroll-behavior: contain;
}

/*
 * CHECKS AND CONTROLS
 *
 * Reste à droite.
 * Le scroll du milieu ne doit pas déplacer
 * cette colonne.
 */
.change-client-information__checks {
  flex: 0 0 40rem;

  width: 40rem;
  min-width: 40rem;
  min-height: 0;

  padding-right: 1.75rem;
  margin-top: 5.8rem;

  /*
   * Si les checks deviennent eux-mêmes
   * trop grands, ils peuvent avoir leur
   * propre scroll.
   */
  overflow-x: hidden;
  overflow-y: auto;
}

/*
 * DOCUMENTS
 */
.documents-container {
  width: 100%;
  min-width: 0;

  display: block;

  > * {
    width: 100%;
    min-width: 0;
  }
}
