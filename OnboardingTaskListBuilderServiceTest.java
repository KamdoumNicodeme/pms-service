:host ::ng-deep .documents__table-container .ant-pagination-prev,
:host ::ng-deep .documents__table-container .ant-pagination-next {
  border-radius: 4px;
}

:host ::ng-deep .documents__table-container .ant-pagination-prev .ant-pagination-item-link,
:host ::ng-deep .documents__table-container .ant-pagination-next .ant-pagination-item-link {
  display: flex;
  align-items: center;
  justify-content: center;

  border: 1px solid #ded5ca;
  border-radius: 4px;

  background: #ffffff;

  color: #62584e;
}

:host ::ng-deep .documents__table-container .ant-pagination-prev:not(.ant-pagination-disabled):hover .ant-pagination-item-link,
:host ::ng-deep .documents__table-container .ant-pagination-next:not(.ant-pagination-disabled):hover .ant-pagination-item-link {
  border-color: #8a6534;

  color: #8a6534;
}

:host ::ng-deep .documents__table-container .ant-pagination-disabled .ant-pagination-item-link {
  border-color: #e8e2da;

  background: #f5f2ee;

  color: #b9afa4;

  cursor: not-allowed;
}
