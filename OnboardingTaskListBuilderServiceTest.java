<div class="list__add">

  @if (adding()) {

    <!-- =============================== -->
    <!-- STRUCTURED LIST -->
    <!-- Tax Information -->
    <!-- =============================== -->

    @if ((row().entryFields?.length ?? 0) > 0) {

      <div class="list__structured-add">

        @for (
          field of row().entryFields ?? [];
          track field.key
        ) {

          <div class="list__structured-field">

            <label class="list__structured-label">
              {{ field.label }}
            </label>


            @if (field.kind === 'select') {

              <nz-select
                class="list__structured-control"
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
                class="list__structured-control"
                [ngModel]="fieldValue(field.key)"
                (ngModelChange)="setFieldValue(field.key, $event)"
              />

            }

          </div>

        }


        <div class="list__structured-actions">

          <button
            type="button"
            class="list__add__confirm"
            (click)="submitStructured()"
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

        </div>

      </div>

    }

    <!-- =============================== -->
    <!-- SIMPLE LIST -->
    <!-- Email / phone / nationality -->
    <!-- =============================== -->

    @else {

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

    }

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

</div>
