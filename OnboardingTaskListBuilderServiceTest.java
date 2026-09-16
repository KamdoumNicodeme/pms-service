private applyProfessionalFieldsVisibility(
  rows: readonly ComparisonRow[]
): ComparisonRow[] {

  const professionRow = rows.find(row => row.key === 'profession');

  if (!professionRow) {
    return [...rows];
  }

  const profession =
    professionRow.resolution.value
      ?.trim()
      .toLowerCase();

  const dependentFields = [
    'profession-status',
    'employer-name',
    'industry-sector',
  ];

  // Unemployed => on cache les champs dépendants
  if (profession === 'unemployed') {
    return rows.filter(
      row => !dependentFields.includes(row.key)
    );
  }

  // Une autre profession => on garde tout
  return [...rows];
}

readonly rows = computed<readonly ComparisonRow[]>(() => {
  const section = this.section();

  if (!section) {
    return [];
  }

  const rows = section.fields.map(field => {
    if (field.kind === 'list') {
      return this.buildListRow(field);
    }

    if (field.kind === 'group') {
      return this.buildGroupRow(field);
    }

    return this.buildScalarRow(field);
  });

  return this.applyProfessionalFieldsVisibility(rows);
});
