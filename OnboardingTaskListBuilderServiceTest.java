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


/**
 * A multi-valued field: every entry is compared and arbitrated on its own line,
 * and the agent can append a value none of the three sources provided.
 */
@Component({
  selector: 'comparison-list-field',

  imports: [
    ComparisonEntryRowComponent,
    FormsModule,
    NzIconModule,
    NzInputModule,
  ],

  template: `
    <div class="list">

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


      <div class="list__add">

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

      </div>

    </div>
  `,

  styleUrl: './comparison-list-field.scss',
})
export class ComparisonListField {

  readonly row =
    input.required<ComparisonRow>();

  readonly expandedId =
    input<string | null>(null);

  readonly focusedId =
    input<string | null>(null);


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


  readonly adding =
    signal(false);

  readonly draft =
    signal('');


  readonly attentionCount =
    computed(() =>
      this.row()
        .entries
        .filter(
          entry =>
            entry.needsAttention
            ||
            entry.children.some(
              child =>
                child.needsAttention
            )
        )
        .length
    );


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
  }
}
