:host {
  display: block;
  width: 100%;
}

.edit-document {
  width: 100%;
  background: #fff;

  &__body {
    display: flex;
    flex-direction: column;
    gap: 18px;
    padding: 28px 32px;
  }

  &__file {
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 10px;
    margin-bottom: 12px;
  }

  &__file-button {
    display: inline-flex;
    align-items: center;
    gap: 9px;
    padding: 9px 18px;
    color: #966b22;
    background: #fffaf1;
    border: 1px solid #c99a45;
    border-radius: 4px;
    cursor: pointer;

    &:hover {
      background: #f7ebd5;
    }

    input {
      display: none;
    }
  }

  &__file-name {
    color: #8c7a64;
    font-size: 12px;
    overflow-wrap: anywhere;
  }

  &__row {
    display: grid;
    grid-template-columns: 190px minmax(0, 1fr);
    align-items: center;
    gap: 18px;

    label {
      color: #5c4426;
      font-size: 13px;
    }

    input,
    nz-select {
      width: 100%;
      min-width: 0;
    }

    input:disabled {
      background: #f6f3ef;
      color: #7e756a;
    }
  }

  &__footer {
    display: flex;
    justify-content: space-between;
    padding: 18px 32px;
    background: #fffcf8;
    border-top: 1px solid #e8dac3;

    button {
      min-width: 95px;
      border-radius: 4px;
    }

    button[nzType='primary'] {
      background: #a47a25;
      border-color: #a47a25;
      color: #fff;

      &:hover:not(:disabled) {
        background: #8b641d;
      }
    }
  }

  .required {
    margin-right: 4px;
    color: #cf4b43;
  }
}

@media (max-width: 700px) {
  .edit-document {
    &__body {
      padding: 20px 16px;
    }

    &__row {
      grid-template-columns: 1fr;
      gap: 6px;
    }

    &__footer {
      padding: 16px;
    }
  }
}
