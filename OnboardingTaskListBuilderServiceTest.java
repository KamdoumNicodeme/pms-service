reload(section: ComparisonSectionDto): void {
  this.reset(section);
}

private reset(section: ComparisonSectionDto): void {
  this.section.set(section);

  this.overrides.set(new Map());
  this.manualEntries.set([]);
  this.manualStructuredEntries.set([]);

  this.expandedId.set(null);
  this.focusedId.set(null);
  this.openGroups.set(new Set());

  // Si tu as bien ce signal dans ton store
  this.deletedEntries.set([]);
}
