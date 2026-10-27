// ==========================================================
// TAB LABEL
// ==========================================================

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

    &_pending {
      color: var(--cmp-drift, #d98e04);
    }
  }

  &[data-state='pending'] &__state {
    color: var(--cmp-drift, #d98e04);
  }
}


// ==========================================================
// CARD
// ==========================================================

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


// ==========================================================
// GLOBAL CCI LAYOUT
// ==========================================================

.change-client-information__layout {
  display: flex;

  flex: 1 1 auto;

  width: 100%;
  min-width: 0;
  min-height: 0;

  gap: 1rem;

  overflow: hidden;
}


// ==========================================================
// LEFT SIDE
// ==========================================================

.change-client-information__main {
  flex: 1 1 auto;

  min-width: 0;
  min-height: 0;

  display: flex;
  flex-direction: column;

  overflow: hidden;
}


// ==========================================================
// RIGHT SIDE - CHECKS AND CONTROLS
// ==========================================================

.change-client-information__checks {
  flex: 0 0 40rem;

  width: 40rem;
  min-width: 40rem;
  min-height: 0;

  padding-right: 1.75rem;

  /*
   * IMPORTANT:
   * no margin-top here.
   *
   * The old margin-top: 5.8rem was responsible
   * for part of the vertical offset.
   */
  margin-top: 0;

  overflow-x: hidden;
  overflow-y: auto;
}


// ==========================================================
// NG-ZORRO TABS
// ==========================================================
//
// We need the complete NG-ZORRO hierarchy to propagate
// the available height down to .client-profiling__content.
//
// The actual scrollbar is NOT here.
// ==========================================================

:host ::ng-deep {

  .ant-tabs {
    flex: 1 1 auto;

    min-width: 0;
    min-height: 0;

    display: flex;
    flex-direction: column;

    overflow: hidden;
  }


  // --------------------------------------------------------
  // Tabs header
  // --------------------------------------------------------

  .ant-tabs-nav {
    flex: 0 0 auto;

    margin-bottom: 0;
  }


  // --------------------------------------------------------
  // Content holder
  // --------------------------------------------------------

  .ant-tabs-content-holder {
    flex: 1 1 auto;

    min-width: 0;
    min-height: 0;

    display: flex;

    overflow: hidden;
  }


  // --------------------------------------------------------
  // Content
  // --------------------------------------------------------

  .ant-tabs-content {
    flex: 1 1 auto;

    width: 100%;

    min-width: 0;
    min-height: 0;

    display: flex;

    overflow: hidden;
  }


  // --------------------------------------------------------
  // Active pane
  // --------------------------------------------------------

  .ant-tabs-tabpane-active {
    flex: 1 1 auto;

    width: 100%;

    min-width: 0;
    min-height: 0;

    display: flex;
    flex-direction: column;

    overflow: hidden;
  }


  // --------------------------------------------------------
  // Inactive panes
  // --------------------------------------------------------

  .ant-tabs-tabpane-hidden {
    display: none;
  }
}


// ==========================================================
// CONTENT INSIDE EACH TAB
// ==========================================================
//
// THIS IS THE SCROLLING AREA.
//
// HTML:
//
// <nz-tab>
//   <section class="client-profiling__content">
//      <holder-profiling />
//   </section>
// </nz-tab>
//
// The tab itself stays fixed.
// The content inside the tab scrolls.
// ==========================================================

.client-profiling__content {
  flex: 1 1 auto;

  width: 100%;

  min-width: 0;
  min-height: 0;

  display: block;

  overflow-x: hidden;
  overflow-y: auto;

  scrollbar-gutter: stable;
}


// ==========================================================
// ANGULAR CHILD COMPONENTS
// ==========================================================
//
// Do NOT put flex: 1 here.
//
// Their natural height must be allowed to become larger
// than .client-profiling__content.
//
// That's what creates the scrollbar.
// ==========================================================

holder-profiling,
policy-profiling-tab,
documents {
  display: block;

  width: 100%;
  min-width: 0;
}


// ==========================================================
// DOCUMENTS
// ==========================================================

.documents-container {
  width: 100%;
  min-width: 0;
}


// ==========================================================
// RESPONSIVE
// ==========================================================

@media (max-width: 1400px) {

  .change-client-information__checks {
    flex: 0 0 32rem;

    width: 32rem;
    min-width: 32rem;
  }
}


@media (max-width: 1100px) {

  .change-client-information__checks {
    flex: 0 0 26rem;

    width: 26rem;
    min-width: 26rem;

    padding-right: 1rem;
  }
}


@media (max-width: 900px) {

  .change-client-information__layout {
    flex-direction: column;

    overflow-y: auto;
    overflow-x: hidden;
  }


  .change-client-information__main {
    flex: 0 0 auto;

    min-height: auto;

    overflow: visible;
  }


  .change-client-information__checks {
    flex: 0 0 auto;

    width: 100%;
    min-width: 0;

    padding-right: 0;

    overflow: visible;
  }


  .client-profiling__content {
    overflow: visible;
  }
}
