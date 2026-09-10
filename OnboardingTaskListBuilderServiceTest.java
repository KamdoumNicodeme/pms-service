import {
  Component,
  computed,
  effect,
  ElementRef,
  inject,
  input,
  output,
  viewChild
} from '@angular/core';

import { NzIconModule } from 'ng-zorro-antd/icon';
import { NzTooltipModule } from 'ng-zorro-antd/tooltip';

import {
  ComparisonEntryRow,
  ComparisonFieldKind,
  ResolutionPatch
} from '../../../../models/comparison.model';

import {
  buildAnnotations,
  opensRow,
  statusLabel
} from '../../comparison.utils';

import {
  ComparisonAnnotations
} from '../comparison-annotations/comparison-annotations';

import {
  ComparisonResolution
} from '../comparison-resolution/comparison-resolution';

import {
  ComparisonResult
} from '../comparison-result/comparison-result';

import {
  ComparisonRowComponent
} from '../comparison-row/comparison-row';


@Component({
  selector: 'comparison-entry-row',

  imports: [
    ComparisonResult,
    ComparisonResolution,
    ComparisonAnnotations,
    ComparisonRowComponent,
    NzIconModule,
    NzTooltipModule
  ],

  host: {
    class: 'row row--entry',
    tabindex: '0',

    '[class.is-focused]': 'focused()',
    '[class.is-expanded]': 'expanded()',
    '[attr.data-status]': 'entry().status',

    '(focus)': 'focusRequest.emit()',
    '(click)': 'onClick($event)',
    '(keydown)': 'onKeydown($event)'
  },

  template: `

    <div class="row__line">

      <div class="row__label">

        <span
          class="row__stripe"
          [nz-tooltip]="statusText()">
        </span>


        @if (chipLabel(); as chip) {

          <span
            class="row__chip"
            [attr.data-status]="entry().status">

            <nz-icon
              [nzType]="chipIcon()"
            />

            {{ chip }}

          </span>

        }


        @if (entry().label) {

          <span class="row__name">
            {{ entry().label }}
          </span>

        }


        @if (entry().isManual) {

          <button
            type="button"
            class="row__remove"
            nz-tooltip
            nzTooltipTitle="Remove this entry"
            (click)="remove.emit()">

            <nz-icon
              nzType="delete"
            />

          </button>

        }

      </div>


      <div class="row__body">

        <!-- ================================= -->
        <!-- STRUCTURED LIST ENTRY -->
        <!-- Tax Country / TIN / Reason -->
        <!-- ================================= -->

        @if (entry().children.length > 0) {

          <div class="row__children">

            @for (
              child of entry().children;
              track child.id
            ) {

              <comparison-row
                [row]="child"

                [expanded]="expandedId() === child.id"

                [focused]="focusedId() === child.id"

                (toggle)="toggleChild.emit(child.id)"

                (apply)="applyChild.emit({
                  id: child.id,
                  patch: $event
                })"

                (reset)="resetChild.emit(child.id)"
              />

            }

          </div>

        }

        <!-- ================================= -->
        <!-- SIMPLE LIST ENTRY -->
        <!-- email / phone / nationality -->
        <!-- ================================= -->

        @else {

          <comparison-result
            [resolution]="entry().resolution"

            [needsAttention]="entry().needsAttention"

            [isOverride]="entry().isOverride"

            [expanded]="expanded()"

            [emptyMeansDropped]="true"

            [kind]="kind()"

            (edit)="toggle.emit()"
          />


          <comparison-annotations
            [annotations]="annotations()"
          />


          @if (expanded()) {

            <comparison-resolution
              [fieldId]="entry().id"

              [kind]="kind()"

              [values]="entry().values"

              [status]="entry().status"

              [resolution]="entry().resolution"

              [fallback]="entry().fallback"

              [isOverride]="entry().isOverride"

              (apply)="apply.emit($event)"

              (cancel)="toggle.emit()"

              (reset)="reset.emit()"
            />

          }

        }

      </div>

    </div>
  `,

  styleUrl: './comparison-entry-row.scss'
})
export class ComparisonEntryRowComponent {

  private readonly host =
    inject<ElementRef<HTMLElement>>(
      ElementRef
    );


  private readonly panel =
    viewChild(ComparisonResolution);


  readonly entry =
    input.required<ComparisonEntryRow>();


  readonly expanded =
    input(false);


  readonly focused =
    input(false);


  readonly kind =
    input<ComparisonFieldKind>('text');


  readonly expandedId =
    input<string | null>(null);


  readonly focusedId =
    input<string | null>(null);


  readonly toggle =
    output<void>();


  readonly reset =
    output<void>();


  readonly remove =
    output<void>();


  readonly apply =
    output<ResolutionPatch>();


  readonly focusRequest =
    output<void>();


  readonly toggleChild =
    output<string>();


  readonly applyChild =
    output<{
      id: string;
      patch: ResolutionPatch;
    }>();


  readonly resetChild =
    output<string>();


  readonly statusText =
    computed(() =>
      statusLabel(
        this.entry().status
      )
    );


  readonly annotations =
    computed(() =>
      buildAnnotations(
        this.entry().values,
        this.entry().resolution.value
      )
    );


  readonly chipLabel =
    computed(() => {

      if (
        this.entry().isManual
      ) {
        return 'manual';
      }

      switch (
        this.entry().status
      ) {

        case 'added':
          return 'added';

        case 'removed':
          return 'removed';

        case 'drift':
        case 'conflict':
          return 'conflict';

        case 'updated':
          return 'changed';

        default:
          return '';
      }
    });


  readonly chipIcon =
    computed(() => {

      if (
        this.entry().isManual
      ) {
        return 'edit';
      }

      switch (
        this.entry().status
      ) {

        case 'added':
          return 'plus';

        case 'removed':
          return 'minus';

        case 'updated':
          return 'swap';

        default:
          return 'warning';
      }
    });


  constructor() {

    effect(() => {

      const element =
        this.host.nativeElement;

      if (
        this.focused()
        &&
        document.activeElement !== element
        &&
        !element.contains(
          document.activeElement
        )
      ) {

        element.focus({
          preventScroll: false
        });
      }
    });
  }


  onClick(
    event: MouseEvent
  ): void {

    /*
     * Structured entries manage their
     * own child expansion.
     *
     * We must NOT open the parent entry.
     */
    if (
      this.entry().children.length > 0
    ) {
      return;
    }


    if (
      opensRow(event)
    ) {
      this.toggle.emit();
    }
  }


  onKeydown(
    event: KeyboardEvent
  ): void {

    this.panel()
      ?.onKeydown(event);
  }
}
