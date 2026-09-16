const row = this.rows().find(
  current => current.entries.some(
    entry => entry.id === id
  )
);

if (row && patch.value) {

  const duplicate = row.entries.some(entry => {

    if (entry.id === id) {
      return false;
    }

    return this.isSameListValue(
      row.key,
      this.currentEntryValue(entry),
      patch.value
    );
  });

  if (duplicate) {
    return;
  }
}
