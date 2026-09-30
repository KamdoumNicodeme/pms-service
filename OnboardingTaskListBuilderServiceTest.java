:host ::ng-deep .ant-dropdown-menu {
  list-style: none !important;
  margin: 0 !important;
  padding: 4px 0 !important;
}

:host ::ng-deep .ant-dropdown-menu-item {
  list-style: none !important;

  &::marker {
    display: none;
    content: '';
  }
}

:host ::ng-deep .ant-dropdown {
  min-width: 130px;
}

:host ::ng-deep .ant-dropdown-menu-item {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  padding: 6px 12px;
}
