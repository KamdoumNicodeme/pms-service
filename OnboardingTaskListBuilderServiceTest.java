// =====================================================
// NG-ZORRO TABS
// =====================================================

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


// =====================================================
// CCI LAYOUT
// =====================================================

.change-client-information__layout {
  display: flex;

  flex: 1 1 0;
  min-height: 0;
  width: 100%;

  gap: 1rem;

  overflow: hidden;
}


// =====================================================
// LEFT SIDE
// THIS is the scroll container
// =====================================================

.change-client-information__main {
  flex: 1 1 auto;
  min-width: 0;
  min-height: 0;

  overflow-x: hidden;
  overflow-y: auto;

  padding-right: 0.25rem;
}


// =====================================================
// RIGHT SIDE
// Does NOT scroll with the middle
// =====================================================

.change-client-information__checks {
  flex: 0 0 40rem;

  width: 40rem;
  min-width: 40rem;

  padding-right: 1.75rem;
  margin-top: 5.8rem;

  overflow-y: auto;
}


// =====================================================
// DOCUMENTS
// =====================================================

.documents-container {
  width: 100%;
  min-width: 0;
}
