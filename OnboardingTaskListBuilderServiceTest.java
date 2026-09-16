@if (entry().label || entry().resolution.value) {
  <span class="row__name">
    {{ entryDisplayLabel() }}
  </span>
}

readonly entryDisplayLabel = computed((): string => {
  const entry = this.entry();

  // Pour une entrée manuelle, on affiche toujours
  // la valeur actuellement sélectionnée.
  if (entry.isManual) {
    return this.toDisplayValue(entry.resolution.value) ?? '';
  }

  // Si l'entrée existante a été modifiée,
  // on affiche la nouvelle valeur.
  if (entry.isOverride && entry.resolution.value) {
    return this.toDisplayValue(entry.resolution.value) ?? '';
  }

  // Sinon on conserve le label initial.
  return entry.label ?? this.toDisplayValue(entry.resolution.value) ?? '';
});

private toDisplayValue(value: string | null): string | null {
  if (!value) {
    return null;
  }

  return (
    this.options().find(option => option.value === value)?.label
    ?? value
  );
}
