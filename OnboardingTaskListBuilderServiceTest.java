.documents {
  width: 100%;
  padding: 1rem 1.25rem 2rem;

  &__table {
    width: 100%;
    overflow: hidden;
    border: 1px solid #ebe7df;
    border-radius: 4px;
    background: #fff;
  }

  &__row {
    display: grid;

    grid-template-columns:
      minmax(18rem, 2.7fr)
      minmax(10rem, 1.1fr)
      minmax(15rem, 1.8fr)
      minmax(7rem, 0.7fr)
      minmax(7rem, 0.7fr)
      5rem;

    min-height: 3.4rem;
    background: #fff;
    border-bottom: 1px solid #eeeae3;

    transition: background-color 0.15s ease;

    &:last-child {
      border-bottom: none;
    }

    &:not(&--header):hover {
      background: #faf8f4;
    }

    > div {
      min-width: 0;
      padding: 0.75rem 0.9rem;

      display: flex;
      align-items: center;

      border-right: 1px solid #eeeae3;

      &:last-child {
        border-right: none;
      }
    }

    &--header {
      min-height: 2.8rem;

      background: linear-gradient(
        180deg,
        var(--ant-primary-2),
        var(--ant-primary-3)
      );

      color: #5c4a27;
      font-size: 0.8rem;
      font-weight: 600;

      > div {
        border-right-color: rgba(255, 255, 255, 0.5);
      }
    }
  }

  &__filename {
    font-weight: 600;
    color: #2c2c2c;
  }

  &__metadata {
    color: #666;
    font-size: 0.82rem;
  }

  &__status {
    font-size: 0.8rem;
    font-weight: 500;

    &--validated {
      color: #389e0d;
    }

    &--rejected {
      color: #cf1322;
    }
  }

  &__actions {
    justify-content: center;
  }

  &__menu-button {
    width: 2rem;
    min-width: 2rem;
    height: 2rem;

    padding: 0;

    display: inline-flex;
    align-items: center;
    justify-content: center;

    border: 1px solid #d9d3c7;
    border-radius: 3px;

    background: #fff;

    font-size: 1rem;
    line-height: 1;

    cursor: pointer;

    &:hover {
      color: var(--ant-primary-6);
      border-color: var(--ant-primary-5);
      background: #faf8f4;
    }
  }

  &__footer {
    margin-top: 1rem;

    display: flex;
    justify-content: flex-start;
  }

  &__add-button {
    height: 2.3rem;

    display: inline-flex;
    align-items: center;
    gap: 0.4rem;

    padding: 0 1rem;

    font-weight: 600;
  }

  &__empty {
    padding: 3rem 1rem;

    text-align: center;

    color: #8c8c8c;
    background: #fff;
  }
}
