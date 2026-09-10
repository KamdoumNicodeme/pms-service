<div class="list__add">

  @if ((row().entryFields?.length ?? 0) > 0) {

    <button
      type="button"
      class="list__add__trigger"
      (click)="openStructuredModal()"
    >
      <nz-icon nzType="plus" />
      Add {{ row().entryNoun }}
    </button>

  } @else {

    @if (adding()) {

      <input
        nz-input
        class="list__add__input"
        [placeholder]="'New ' + row().entryNoun"
        [ngModel]="draft()"
        (ngModelChange)="draft.set($event)"
        (keydown.enter)="submit()"
        (keydown.escape)="cancelAdd()"
      />

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
        (click)="adding.set(true)"
      >
        <nz-icon nzType="plus" />
        Add {{ row().entryNoun }}
      </button>

    }

  }

</div>


<nz-modal
  [nzVisible]="structuredModalVisible()"
  nzTitle="Add tax information"
  nzOkText="Add"
  nzCancelText="Cancel"
  [nzOkDisabled]="!canSubmitStructured()"
  (nzOnOk)="submitStructured()"
  (nzOnCancel)="closeStructuredModal()"
>

  <ng-container *nzModalContent>

    <div class="tax-form">

      @for (
        field of row().entryFields ?? [];
        track field.key
      ) {

        <div class="tax-form__field">

          <label class="tax-form__label">
            {{ field.label }}
          </label>

          @if (field.kind === 'select') {

            <nz-select
              class="tax-form__control"
              [ngModel]="fieldValue(field.key)"
              (ngModelChange)="setFieldValue(field.key, $event)"
              nzPlaceHolder="Select a value"
            >

              @for (
                option of field.options ?? [];
                track option.value
              ) {

                <nz-option
                  [nzValue]="option.value"
                  [nzLabel]="option.label"
                />

              }

            </nz-select>

          } @else {

            <input
              nz-input
              class="tax-form__control"
              [ngModel]="fieldValue(field.key)"
              (ngModelChange)="setFieldValue(field.key, $event)"
            />

          }

        </div>

      }

    </div>

  </ng-container>

</nz-modal>
