protected readonly pageIndex = signal(1);
protected readonly pageSize = signal(10);

protected readonly paginatedDocuments = computed(() => {
  const documents = this.documents();

  const start = (this.pageIndex() - 1) * this.pageSize();
  const end = start + this.pageSize();

  return documents.slice(start, end);
});

protected onPageSizeChange(pageSize: number): void {
  this.pageSize.set(pageSize);
  this.pageIndex.set(1);
}

.documents__pagination {
  display: flex;
  justify-content: flex-end;
  align-items: center;

  padding: 1rem 0;
  flex: none;
}
