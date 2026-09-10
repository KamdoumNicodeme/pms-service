import {
  Component,
  computed,
  input,
  output,
  signal,
} from '@angular/core';

import { FormsModule } from '@angular/forms';

import { NzIconModule } from 'ng-zorro-antd/icon';
import { NzInputModule } from 'ng-zorro-antd/input';
import { NzSelectModule } from 'ng-zorro-antd/select';
import { NzModalModule } from 'ng-zorro-antd/modal';

import {
  ComparisonRow,
  ResolutionPatch,
} from '../../../../models/comparison.model';

import {
  ComparisonEntryRowComponent,
} from '../comparison-entry-row/comparison-entry-row';


/** Emitted alongside the entry the action targets. */
export interface EntryAction<T = void> {
  readonly id: string;
  readonly payload: T;
}


export interface StructuredEntryPayload {
  readonly fields: readonly {
    key: string;
    value: string | null;
  }[];
}


/**
 * A multi-valued field.
 *
 * Simple lists:
 * - email
 * - phone
 * - nationality
 *
 * Structured lists:
 * - tax information
 */
@Component({
  selector: 'comparison-list-field',

  imports: [
    ComparisonEntryRowComponent,
    FormsModule,
    NzIconModule,
    NzInputModule,
    NzSelectModule,
    NzModalModule,
  ],

  template: `
    <div class="list">

      <!-- ============================ -->
      <!-- HEADER -->
      <!-- ============================ -->

      <header class="list__header">

        <span class="list__title">
          {{ row().label }}
        </span>

        <span class="list__count">
          {{ row().entries.length }}
          {{ row().entries.length === 1 ? 'entry' : 'entries' }}
        </span>

        @if (attentionCount() > 0) {
          <span class="list__attention">
            {{ attentionCount() }} to review
          </span>
        }

      </header>


      <!-- ============================ -->
      <!-- EXISTING ENTRIES -->
      <!-- ============================ -->

      @for (entry of row().entries; track entry.id) {

        <comparison-entry-row
          [entry]="entry"

          [expanded]="expandedId() === entry.id"
          [focused]="focusedId() === entry.id"

          [expandedId]="expandedId()"
          [focusedId]="focusedId()"

          (toggle)="toggleEntry.emit(entry.id)"

          (apply)="applyEntry.emit({
            id: entry.id,
            payload: $event
          })"

          (reset)="resetEntry.emit(entry.id)"

          (remove)="removeEntry.emit(entry.entryKey)"

          (focusRequest)="focusEntry.emit(entry.id)"

          (toggleChild)="toggleEntry.emit($event)"

          (applyChild)="applyEntry.emit({
            id: $event.id,
            payload: $event.patch
          })"

          (resetChild)="resetEntry.emit($event)"
        />

      }


      <!-- ============================ -->
      <!-- ADD -->
      <!-- ============================ -->

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


      <!-- ============================ -->
      <!-- STRUCTURED ADD MODAL -->
      <!-- ============================ -->

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

          <div class="tax-form">

            @for (
              field of row().entryFields ?? [];
              track field.key
            ) {

              <div class="tax-form__field">

                <label class="tax-form__label">
                  {{ field.label }}
                </label>


                <!-- SELECT -->
                @if (field.kind === 'select') {

                  <nz-select
                    class="tax-form__control"

                    [ngModel]="fieldValue(field.key)"

                    (ngModelChange)="setFieldValue(
                      field.key,
                      $event
                    )"

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
                    class="tax-form__control"

                    [ngModel]="fieldValue(field.key)"

                    (ngModelChange)="setFieldValue(
                      field.key,
                      $event
                    )"
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

                    class="tax-form__control"

                    [type]="field.kind === 'date' ? 'date' : 'text'"

                    [ngModel]="fieldValue(field.key)"

                    (ngModelChange)="setFieldValue(
                      field.key,
                      $event
                    )"
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

  // =========================================================
  // INPUTS
  // =========================================================

  readonly row =
    input.required<ComparisonRow>();

  readonly expandedId =
    input<string | null>(null);

  readonly focusedId =
    input<string | null>(null);


  // =========================================================
  // OUTPUTS
  // =========================================================

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

  readonly addStructuredEntry =
    output<StructuredEntryPayload>();


  // =========================================================
  // SIMPLE LIST
  // =========================================================

  readonly adding =
    signal(false);

  readonly draft =
    signal('');


  submit(): void {

    const value =
      this.draft().trim();

    if (!value) {
      return;
    }

    this.addEntry.emit(value);

    this.cancelAdd();
  }


  cancelAdd(): void {

    this.adding.set(false);

    this.draft.set('');
  }


  // =========================================================
  // STRUCTURED LIST
  // =========================================================

  readonly structuredModalVisible =
    signal(false);


  readonly structuredDraft =
    signal<Record<string, string>>({});


  openStructuredModal(): void {

    this.structuredDraft.set({});

    this.structuredModalVisible.set(true);
  }


  closeStructuredModal(): void {

    this.structuredModalVisible.set(false);

    this.structuredDraft.set({});
  }


  fieldValue(
    key: string
  ): string {

    return this.structuredDraft()[key] ?? '';
  }


  setFieldValue(
    key: string,
    value: string
  ): void {

    this.structuredDraft.update(current => ({
      ...current,
      [key]: value,
    }));
  }


  readonly canSubmitStructured =
    computed(() => {

      /*
       * Tax Country is mandatory.
       *
       * We can later make the mandatory flag
       * generic if needed.
       */
      const taxCountry =
        this.fieldValue('tax-country');

      return taxCountry.trim() !== '';
    });


  submitStructured(): void {

    const definitions =
      this.row().entryFields ?? [];


    const fields =
      definitions.map(definition => {

        const rawValue =
          this.fieldValue(
            definition.key
          );

        return {
          key: definition.key,

          value:
            rawValue.trim() !== ''
              ? rawValue.trim()
              : null,
        };
      });


    this.addStructuredEntry.emit({
      fields,
    });


    this.closeStructuredModal();
  }


  // =========================================================
  // COUNTER
  // =========================================================

  readonly attentionCount =
    computed(() =>

      this.row().entries.filter(entry =>

        entry.needsAttention

        ||

        entry.children.some(
          child =>
            child.needsAttention
        )

      ).length

    );
}
