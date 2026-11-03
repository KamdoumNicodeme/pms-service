&__title {
  display: flex;
  align-items: center;
  gap: 0.75rem;
  min-width: 0;
  flex: 1 1 auto;
  white-space: nowrap;

  h1 {
    margin: 0;
    flex: 0 0 auto;
  }
}

&__status {
  display: flex;
  flex-direction: row;
  align-items: center;
  gap: 0.5rem;
  flex: 0 0 auto;
  white-space: nowrap;

  span {
    display: inline-flex;
    align-items: center;
    white-space: nowrap;

    em {
      display: inline-flex;
      align-items: center;
      gap: 0.25rem;
      white-space: nowrap;
    }
  }
}
