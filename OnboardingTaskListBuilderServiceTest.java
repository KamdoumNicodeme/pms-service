@if (row().options.length > 0) {

  <nz-select
    class="list__add__input"
    [ngModel]="draft()"
    (ngModelChange)="draft.set($event)"
    [nzPlaceHolder]="'Select ' + row().entryNoun"
  >

    @for (option of row().options; track option.value) {

      <nz-option
        [nzValue]="option.value"
        [nzLabel]="option.label"
      />

    }

  </nz-select>

} @else {

  <input
    nz-input
    class="list__add__input"

    [placeholder]="'New ' + row().entryNoun"

    [ngModel]="draft()"

    (ngModelChange)="draft.set($event)"

    (keydown.enter)="submit()"

    (keydown.escape)="cancelAdd()"
  />

}
