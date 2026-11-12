.rejection-alert {
  display: block;
  width: 100%;
  margin: 10px 0 14px;

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
    min-width: 0;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }

  ::ng-deep .ant-alert {
    display: flex;
    align-items: center;
    min-height: 26px;
    padding: 3px 10px;
  }

  ::ng-deep .ant-alert-icon {
    position: static;
    margin-right: 8px;
    font-size: 13px;
  }

  ::ng-deep .ant-alert-message {
    margin: 0;
    font-size: 12px;
    line-height: 18px;
  }
}
