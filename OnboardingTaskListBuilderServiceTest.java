@if (section.value(); as data) {

  <data-comparison
    [section]="data"
    [filter]="filter()"
    (countersChange)="countersChange.emit($event)"
    (changeChanges)="changesChange.emit($event)"
  />

} @else if (section.error()) {

  <p class="profiling-section__error">
    <nz-icon
      nzType="close-circle"
      nzTheme="fill"
    />

    We could not load this section.
  </p>

} @else {

  <p class="profiling-section__loading">
    <nz-icon nzType="loading" />
    Loading...
  </p>

}
