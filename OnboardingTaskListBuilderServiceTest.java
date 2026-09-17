removeManualEntry(fieldKey: string, entryKey: string): void {

  const id = `${fieldKey}:${entryKey}`;

  // ============================================================
  // 1. ENTRY MANUELLE
  // ============================================================

  const isManual =
    entryKey.startsWith('manual-');

  if (isManual) {

    // Supprime de la liste des entrées manuelles simples
    this.manualEntries.update(current =>
      current.filter(entry =>
        !(entry.fieldKey === fieldKey && entry.entryKey === entryKey)
      )
    );

    // Supprime de la liste des entrées manuelles structurées
    this.manualStructuredEntries.update(current =>
      current.filter(entry =>
        !(entry.fieldKey === fieldKey && entry.entryKey === entryKey)
      )
    );

    // IMPORTANT :
    // Supprime également son changement.
    //
    // Exemple :
    // nationalities:manual-1
    //
    // ne doit PLUS exister dans overrides après suppression.
    this.overrides.update(current => {

      const next = new Map(current);

      next.delete(id);

      // Au cas où l'entrée structurée possède des children
      for (const key of next.keys()) {
        if (key.startsWith(`${id}:`)) {
          next.delete(key);
        }
      }

      return next;
    });

    // Une entrée créée puis supprimée n'est PAS une suppression
    // métier : on ne l'ajoute donc PAS dans deletedEntries.

    this.deletedEntries.update(current =>
      current.filter(existingId => existingId !== id)
    );

  } else {

    // ============================================================
    // 2. ENTRY PROVENANT DU BACKEND
    // ============================================================

    // Là seulement c'est une vraie suppression.
    this.deletedEntries.update(current =>
      current.includes(id)
        ? current
        : [...current, id]
    );
  }

  // ============================================================
  // 3. UI
  // ============================================================

  if (this.focusedId() === id) {
    this.focusedId.set(null);
  }

  if (this.expandedId() === id) {
    this.expandedId.set(null);
  }
}
