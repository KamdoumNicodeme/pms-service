<comparison-entry-row
  [entry]="entry"
  [kind]="row().options.length > 0 ? 'select' : 'text'"
  [options]="row().options"
  [expanded]="expandedId() === entry.id"
  [focused]="focusedId() === entry.id"
  [expandedId]="expandedId()"
  [focusedId]="focusedId()"

  (toggle)="toggleEntry.emit(entry.id)"

  (apply)="
    applyEntry.emit({
      id: entry.id,
      payload: $event
    })
  "

  (reset)="resetEntry.emit(entry.id)"

  (remove)="removeEntry.emit(entry.entryKey)"

  (focusRequest)="focusEntry.emit(entry.id)"

  (toggleChild)="toggleEntry.emit($event)"

  (applyChild)="
    applyEntry.emit({
      id: $event.id,
      payload: $event.patch
    })
  "

  (resetChild)="resetEntry.emit($event)"
/>
