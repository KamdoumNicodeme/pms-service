@else if (row.kind === 'list') {

  <comparison-list-field
    [row]="row"

    [expandedId]="store.expandedId()"

    [focusedId]="store.focusedId()"

    (toggleEntry)="store.toggleExpanded($event)"

    (resetEntry)="store.reset($event)"

    (focusEntry)="store.focus($event)"

    (applyEntry)="onApplyEntry($event)"

    (addEntry)="store.addManualEntry(
      row.key,
      $event
    )"

    (addStructuredEntry)="store.addManualStructuredEntry(
      row.key,
      $event.fields
    )"

    (removeEntry)="store.removeManualEntry(
      row.key,
      $event
    )"
  />

}
