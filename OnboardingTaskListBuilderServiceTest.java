:host ::ng-deep .documents__table-container {
  .ant-table-pagination {
    gap: 6px;
    margin: 1rem 0 0 !important;
  }

  .ant-pagination-item,
  .ant-pagination-prev,
  .ant-pagination-next {
    min-width: 36px;
    height: 36px;
    margin: 0;
    border: 1px solid #e5d5ba;
    border-radius: 8px;
    background: #fff;
    line-height: 34px;

    a,
    .ant-pagination-item-link {
      display: flex;
      align-items: center;
      justify-content: center;
      height: 100%;
      border: 0;
      background: transparent;
      color: #8a6534;
    }
  }

  .ant-pagination-item-active {
    border-color: #b28a4f;
    box-shadow: 0 1px 4px rgba(138, 101, 52, 0.25);

    a {
      color: #8a6534;
      font-weight: 600;
    }
  }

  .ant-pagination-item:hover,
  .ant-pagination-prev:not(.ant-pagination-disabled):hover,
  .ant-pagination-next:not(.ant-pagination-disabled):hover {
    border-color: #b28a4f;

    a,
    .ant-pagination-item-link {
      color: #8a6534;
    }
  }

  .ant-pagination-disabled {
    border-color: #cdbfa8;
    background: #cdbfa8;

    .ant-pagination-item-link {
      color: #fff;
    }
  }
}
