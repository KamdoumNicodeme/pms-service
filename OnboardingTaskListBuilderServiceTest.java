.row__fields {
  display: flex;
  flex-direction: column;
  width: 100%;
}

.row__field {
  display: grid;
  grid-template-columns: minmax(180px, 320px) 1fr;
  align-items: center;
  min-height: 40px;
  gap: 24px;
}

.row__field-label {
  font-weight: 400;
}

.row__field-value {
  font-weight: 500;
}
