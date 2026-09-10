<comparison-entry-row
  [entry]="entry"

  [expanded]="store.expandedId() === entry.id"
  [focused]="store.focusedId() === entry.id"

  [expandedId]="store.expandedId()"
  [focusedId]="store.focusedId()"

  (toggle)="store.toggleExpanded(entry.id)"
  (apply)="store.apply(entry.id, $event)"
  (reset)="store.reset(entry.id)"

  (toggleChild)="store.toggleExpanded($event)"

  (applyChild)="store.apply(
    $event.id,
    $event.patch
  )"

  (resetChild)="store.reset($event)"
/>
