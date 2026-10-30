.rejection-alert {
  width: 100%;

  &__content {
    display: flex;
    align-items: center;
    gap: 8px;
    min-width: 0;
  }

  &__title {
    font-weight: 600;
    white-space: nowrap;
  }

  &__separator {
    flex-shrink: 0;
  }

  &__reason {
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }

  ::ng-deep .ant-alert {
    display: flex;
    align-items: center;
    padding: 8px 16px;
  }

  ::ng-deep .ant-alert-icon {
    position: static;
    margin-right: 10px;
  }

  ::ng-deep .ant-alert-message {
    margin: 0;
  }
}
