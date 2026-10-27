.client-profiling {
  display: flex;
  flex-direction: column;

  width: 100%;
  height: 100%;
  min-height: 0;

  overflow: hidden;

  // =========================================================
  // HEADER
  // =========================================================

  &__header {
    flex: 0 0 auto;

    display: flex;
    align-items: center;
    justify-content: space-between;

    height: 3.75rem;

    h1 {
      margin: 0;

      font-size: 1.1rem;
      line-height: 1;

      color: var(--ant-primary-7);
    }

    &__actions {
      display: flex;
      align-items: center;
      gap: 0.75rem;

      button {
        height: 2.25rem;

        display: inline-flex;
        align-items: center;
        justify-content: center;
      }
    }
  }

  // =========================================================
  // SUMMARY
  // =========================================================

  &__summary {
    flex: 0 0 auto;

    display: block;

    width: 100%;

    list-style-type: none;
  }

  // =========================================================
  // CONTENT
  // =========================================================

  &__content {
    flex: 1 1 auto;

    display: flex;
    flex-direction: column;

    width: 100%;
    min-height: 0;

    margin-top: 0.85rem;

    overflow: hidden;
  }

  // =========================================================
  // TASK
  // Container around @switch(...)
  // =========================================================

  &__task {
    flex: 1 1 auto;

    width: 100%;
    min-height: 0;

    overflow: hidden;
  }
}


// ===========================================================
// CHANGE CLIENT INFORMATION HOST
// ===========================================================
//
// Important:
// Angular creates:
//
// <change-client-information>
//     ...
// </change-client-information>
//
// The component must receive the available height from
// client-profiling__task.
// ===========================================================

change-client-information {
  display: block;

  width: 100%;
  height: 100%;

  min-height: 0;
}
