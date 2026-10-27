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
  }

  &__state_pending {
    color: var(--cmp-drift, #d98e04);
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
// ROOT LAYOUT
// ==========================================================

.change-client-information__layout {
  display: flex;

  width: 100%;
  height: 100%;
  min-height: 0;

  gap: 1rem;

  overflow: hidden;
}


// ==========================================================
// LEFT / MAIN AREA
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
// CHECKS - RIGHT SIDE
// ==========================================================

.change-client-information__checks {
  flex: 0 0 40rem;

  width: 40rem;
  min-width: 40rem;

  min-height: 0;

  padding-right: 1.75rem;

  /*
   * IMPORTANT:
   * no margin-top: 5.8rem here.
   *
   * This was creating the large vertical offset,
   * especially on large screens.
   */
  margin-top: 0;

  overflow-y: auto;
  overflow-x: hidden;
}


// ==========================================================
// DOCUMENTS
// ==========================================================

.documents-container {
  flex: 1 1 auto;

  min-height: 0;

  display: flex;
  flex-direction: column;

  overflow: auto;

  > * {
    flex: 1 1 auto;
    min-height: 0;
  }
}


// ==========================================================
// NG-ZORRO TABS
//
// nz-tabs creates several internal containers.
// Every container in the chain must propagate the available
// height otherwise the tab content uses its natural height
// and no internal scroll can happen.
// ==========================================================

:host ::ng-deep {
  .ant-tabs {
    flex: 1 1 auto;

    min-height: 0;

    display: flex;
    flex-direction: column;

    overflow: hidden;
  }

  // --------------------------------------------------------
  // Tab navigation/header
  // --------------------------------------------------------

  .ant-tabs-nav {
    flex: 0 0 auto;
  }

  // --------------------------------------------------------
  // Container under the tab navigation
  // --------------------------------------------------------

  .ant-tabs-content-holder {
    flex: 1 1 auto;

    min-height: 0;

    display: flex;

    overflow: hidden;
  }

  // --------------------------------------------------------
  // Container containing the panes
  // --------------------------------------------------------

  .ant-tabs-content {
    flex: 1 1 auto;

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
    height: 100%;

    min-width: 0;
    min-height: 0;

    display: flex;
    flex-direction: column;

    overflow: hidden;
  }

  // --------------------------------------------------------
  // Hidden panes
  // --------------------------------------------------------

  .ant-tabs-tabpane-hidden {
    display: none;
  }
}


// ==========================================================
// CONTENT OF EACH TAB
// ==========================================================
//
// Your HTML has:
//
// <nz-tab>
//   ...
//   <section class="client-profiling__content">
//      <holder-profiling ... />
//   </section>
// </nz-tab>
//
// The section receives the remaining height.
// The component inside it becomes the scrolling area.
// ==========================================================

.client-profiling__content {
  flex: 1 1 auto;

  width: 100%;

  min-width: 0;
  min-height: 0;

  display: flex;
  flex-direction: column;

  overflow: hidden;
}


// ==========================================================
// HOLDER / POLICY / DOCUMENTS COMPONENTS
// ==========================================================
//
// Angular custom elements are inline by default.
// We explicitly make them flex containers capable of
// receiving the available height.
// ==========================================================

holder-profiling,
policy-profiling-tab,
documents {
  display: block;

  flex: 1 1 auto;

  width: 100%;

  min-width: 0;
  min-height: 0;
}


// ==========================================================
// RESPONSIVE
// ==========================================================
//
// 40rem for Checks and controls is fine on large screens,
// but we shouldn't force it on smaller screens.
// ==========================================================

@media (max-width: 1200px) {
  .change-client-information__checks {
    flex-basis: 32rem;

    width: 32rem;
    min-width: 32rem;
  }
}

@media (max-width: 900px) {
  .change-client-information__layout {
    flex-direction: column;
    overflow: auto;
  }

  .change-client-information__main {
    flex: none;
    overflow: visible;
  }

  .change-client-information__checks {
    flex: none;

    width: 100%;
    min-width: 0;

    padding-right: 0;

    overflow: visible;
  }
}
