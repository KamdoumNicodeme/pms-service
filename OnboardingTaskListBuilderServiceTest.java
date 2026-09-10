readonly canSubmitStructured = computed(() => {
  const definitions =
    this.row().entryFields ?? [];

  if (definitions.length === 0) {
    return false;
  }

  const country =
    this.fieldValue('tax-country');

  return country.trim() !== '';
});


.tax-form {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.tax-form__field {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.tax-form__label {
  font-weight: 500;
}

.tax-form__control {
  width: 100%;
}
