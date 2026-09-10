import {
  Component,
  computed,
  input,
  output,
  signal
} from '@angular/core';

import { FormsModule } from '@angular/forms';

import { NzIconModule } from 'ng-zorro-antd/icon';
import { NzInputModule } from 'ng-zorro-antd/input';
import {
  NzOptionComponent,
  NzSelectComponent
} from 'ng-zorro-antd/select';
import { NzModalModule } from 'ng-zorro-antd/modal';

import {
  ComparisonRow,
  ResolutionPatch
} from '../../../../models/comparison.model';

import {
  ComparisonEntryRowComponent
} from '../comparison-entry-row/comparison-entry-row';


/** Emitted alongside the entry the action targets. */
export interface EntryAction<T = void> {
  readonly id: string;
  readonly payload: T;
}


export interface StructureEntryPayload {
  readonly fields: readonly {
    key: string;
    value: string | null;
  }[];
}


/**
 * A multi-valued field:
 * - simple list: email / phone / nationality
 * - structured list: tax information
 */
@Component({
  selector: 'comparison-list-field',

  imports: [
    ComparisonEntryRowComponent,
    FormsModule,
    NzIconModule,
    NzInputModule,
    NzSelectComponent,
    NzOptionComponent,
    NzModalModule
  ],

  template: `
    <div class="list">

      <!-- ========================= -->
      <!-- HEADER -->
      <!-- ========================= -->

      <header class="list__header">

        <span class="list__title">
          {{ row().label }}
        </span>

        <span class="list__count">
          {{ row().entries.length }}
          {{
            row().entries.length === 1
              ? 'entry'
              : 'entries'
          }}
        </span>

        @if (attentionCount() > 0) {
          <span class="list__attention">
            {{ attentionCount() }} to review
          </span>
        }

      </header>


      <!-- ========================= -->
      <!-- EXISTING ENTRIES -->
      <!-- ========================= -->

      @for (entry of row().entries; track entry.id) {

        <comparison-entry-row
          [entry]="entry"

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

      }


      <!-- ========================= -->
      <!-- ADD BUTTON -->
      <!-- ========================= -->

      <div class="list__add">

        <!-- STRUCTURED LIST -->
        @if ((row().entryFields?.length ?? 0) > 0) {

          <button
            type="button"
            class="list__add__trigger"
            (click)="openStructuredModal()"
          >

            <nz-icon nzType="plus" />

            Add {{ row().entryNoun }}

          </button>

        }

        <!-- SIMPLE LIST -->
        @else {

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


      <!-- ========================= -->
      <!-- STRUCTURED MODAL -->
      <!-- ========================= -->

      <nz-modal
        [nzVisible]="structuredModalVisible()"

        [nzTitle]="'Add ' + row().entryNoun"

        nzOkText="Add"

        nzCancelText="Cancel"

        [nzOkDisabled]="!canSubmitStructured()"

        (nzOnOk)="submitStructured()"

        (nzOnCancel)="closeStructuredModal()"
      >

        <ng-container *nzModalContent>

          <div class="structured-form">

            @for (
              field of row().entryFields ?? [];
              track field.key
            ) {

              <div class="structured-form__field">

                <label class="structured-form__label">
                  {{ field.label }}
                </label>


                <!-- SELECT -->
                @if (field.kind === 'select') {

                  <nz-select
                    class="structured-form__control"

                    [ngModel]="fieldValue(field.key)"

                    (ngModelChange)="
                      setFieldValue(
                        field.key,
                        $event
                      )
                    "

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

                }

                <!-- CHECKBOX -->
                @else if (field.kind === 'checkbox') {

                  <select
                    class="structured-form__control"

                    [ngModel]="fieldValue(field.key)"

                    (ngModelChange)="
                      setFieldValue(
                        field.key,
                        $event
                      )
                    "
                  >

                    <option value="">
                      Select a value
                    </option>

                    <option value="true">
                      Yes
                    </option>

                    <option value="false">
                      No
                    </option>

                  </select>

                }

                <!-- TEXT / DATE -->
                @else {

                  <input
                    nz-input

                    class="structured-form__control"

                    [type]="
                      field.kind === 'date'
                        ? 'date'
                        : 'text'
                    "

                    [ngModel]="fieldValue(field.key)"

                    (ngModelChange)="
                      setFieldValue(
                        field.key,
                        $event
                      )
                    "
                  />

                }

              </div>

            }

          </div>

        </ng-container>

      </nz-modal>

    </div>
  `,

  styleUrl: './comparison-list-field.scss',
})
export class ComparisonListField {

  // =========================
  // INPUTS
  // =========================

  readonly row =
    input.required<ComparisonRow>();

  readonly expandedId =
    input<string | null>(null);

  readonly focusedId =
    input<string | null>(null);


  // =========================
  // OUTPUTS
  // =========================

  readonly toggleEntry =
    output<string>();

  readonly resetEntry =
    output<string>();

  readonly removeEntry =
    output<string>();

  readonly applyEntry =
    output<EntryAction<ResolutionPatch>>();

  readonly focusEntry =
    output<string>();

  readonly addEntry =
    output<string>();

  readonly addStructureEntry =
    output<StructureEntryPayload>();


  // =========================
  // SIMPLE LIST
  // =========================

  readonly adding =
    signal(false);

  readonly draft =
    signal('');


  submit(): void {

    const value =
      this.draft().trim();

    if (value === '') {
      return;
    }

    this.addEntry.emit(value);

    this.cancelAdd();
  }


  cancelAdd(): void {

    this.adding.set(false);

    this.draft.set('');

    this.structureDraft.set({});
  }


  // =========================
  // STRUCTURED LIST
  // =========================

  readonly structureDraft =
    signal<Record<string, string>>({});


  readonly structuredModalVisible =
    signal(false);


  openStructuredModal(): void {

    this.structureDraft.set({});

    this.structuredModalVisible.set(true);
  }


  closeStructuredModal(): void {

    this.structuredModalVisible.set(false);

    this.structureDraft.set({});
  }


  fieldValue(
    key: string
  ): string {

    return (
      this.structureDraft()[key]
      ?? ''
    );
  }


  setFieldValue(
    key: string,
    value: string
  ): void {

    this.structureDraft.update(
      current => ({
        ...current,
        [key]: value
      })
    );
  }


  readonly canSubmitStructured =
    computed(() => {

      const definitions =
        this.row().entryFields ?? [];

      if (definitions.length === 0) {
        return false;
      }

      const taxCountry =
        this.fieldValue(
          'tax-country'
        );

      return (
        taxCountry.trim() !== ''
      );
    });


  submitStructured(): void {

    const definitions =
      this.row().entryFields ?? [];


    const fields =
      definitions.map(
        definition => {

          const value =
            this.fieldValue(
              definition.key
            ).trim();

          return {
            key:
              definition.key,

            value:
              value === ''
                ? null
                : value
          };
        }
      );


    this.addStructureEntry.emit({
      fields
    });


    this.closeStructuredModal();
  }


  // =========================
  // COUNTERS
  // =========================

  readonly attentionCount =
    computed(() =>

      this.row().entries.filter(
        entry =>
          entry.needsAttention
          ||
          entry.children.some(
            child =>
              child.needsAttention
          )
      ).length

    );
}
