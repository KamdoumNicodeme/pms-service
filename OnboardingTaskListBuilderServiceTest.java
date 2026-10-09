.document-form {
  display: flex;
  flex-direction: column;
  gap: 20px;

  &__file {
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 8px;
    padding: 12px 0 20px;
  }

  &__filename {
    color: #6f6253;
    font-size: 13px;
  }

  &__field {
    display: grid;
    grid-template-columns: 180px minmax(0, 1fr);
    align-items: center;
    gap: 20px;

    input,
    nz-select {
      width: 100%;
    }
  }

  &__actions {
    display: flex;
    justify-content: space-between;
    margin-top: 12px;
    padding-top: 20px;
    border-top: 1px solid #e5dccd;
  }
}

.required {
  color: #c84a4a;
  margin-right: 4px;
}
