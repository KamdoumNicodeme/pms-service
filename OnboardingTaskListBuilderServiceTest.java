initialize(section: ComparisonSectionDto): void {
  const currentSection = this.section();

  // Exactement le même objet => rien à faire
  if (currentSection === section) {
    return;
  }

  this.section.set(section);

  this.overrides.set(new Map());
  this.manualEntries.set([]);
  this.manualStructuredEntries.set([]);
  this.deletedEntries.set([]);

  this.expandedId.set(null);
  this.focusedId.set(null);
  this.openGroups.set(new Set());
}
