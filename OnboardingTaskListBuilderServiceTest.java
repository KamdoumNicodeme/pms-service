.document-form {
  width: min(760px, 100%);
  background: #fff;
  border: 1px solid #d8c7aa;
  border-radius: 4px;
  box-shadow: 0 3px 10px rgba(62, 43, 22, 0.12);
  overflow: hidden;

  &__header {
    padding: 12px 18px;

    color: #fff;
    background: linear-gradient(
      90deg,
      #8c6a2c,
      #aa8128
    );

    font-size: 14px;
    font-weight: 600;
  }

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
    gap: 8px;

    margin-bottom: 8px;
  }

  &__filename {
    color: #746858;
    font-size: 12px;
  }

  &__row {
    display: grid;
    grid-template-columns: 180px 1fr;
    align-items: center;
    gap: 18px;

    label {
      color: #4f4539;
      font-size: 13px;
    }

    nz-select {
      width: 100%;
    }
  }

  &__footer {
    display: flex;
    justify-content: space-between;

    padding: 18px 32px;

    border-top: 1px solid #eee4d5;
    background: #fffdf9;
  }

  &__submit {
    color: #fff;
    border-color: #9b7527;
    background: #9b7527;

    &:not(:disabled):hover {
      color: #fff;
      border-color: #7d5d1f;
      background: #7d5d1f;
    }
  }
}

.file-button {
  display: inline-flex;
  align-items: center;
  gap: 8px;

  padding: 7px 18px;

  color: #7b5c21;
  background: #fffaf0;

  border: 1px solid #b89248;
  border-radius: 3px;

  cursor: pointer;

  &:hover {
    background: #f7efdf;
  }
}

.required {
  color: #c53f3f;
}

@media (max-width: 700px) {
  .document-form__row {
    grid-template-columns: 1fr;
    gap: 6px;
  }
}
