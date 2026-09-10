readonly structuredModalVisible = signal(false);

readonly structuredDraft =
  signal<Record<string, string>>({});
openStructuredModal(): void {
  this.structuredDraft.set({});
  this.structuredModalVisible.set(true);
}

closeStructuredModal(): void {
  this.structuredModalVisible.set(false);
  this.structuredDraft.set({});
}
