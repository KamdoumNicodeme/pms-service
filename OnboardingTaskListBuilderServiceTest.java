<!-- SIMPLE LIST -->
@else {

  @if (adding()) {

    @if ((row().options?.length ?? 0) > 0) {

      <nz-select
        class="list__add__input"
        [ngModel]="draft()"
        (ngModelChange)="draft.set($event)"
        [nzPlaceHolder]="'Select ' + row().entryNoun"
      >
        @for (option of row().options ?? []; track option.value) {
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

    <button
      type="button"
      class="list__add__confirm"
      [disabled]="draft().trim() === ''"
      (click)="submit()"
    >
      Add
    </button>

    <button
      type="button"
      class="list__add__cancel"
      (click)="cancelAdd()"
    >
      Cancel
    </button>

  } @else {

    <button
      type="button"
      class="list__add__trigger"
      (click)="openSimpleAdd()"
    >
      <nz-icon nzType="plus" />

      Add {{ row().entryNoun }}
    </button>

  }

}
