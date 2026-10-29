import {
  afterNextRender,
  Component,
  computed,
  effect,
  ElementRef,
  HostListener,
  inject,
  input,
  output,
  Signal,
  viewChild,
} from '@angular/core';
import {
  FormControl,
  FormGroup,
  ReactiveFormsModule,
} from '@angular/forms';
import { NzIconModule } from 'ng-zorro-antd/icon';
import { NzInputModule } from 'ng-zorro-antd/input';
import { NzSelectModule } from 'ng-zorro-antd/select';

@Component({
  selector: 'comparison-resolution',
  imports: [
    ReactiveFormsModule,
    NzIconModule,
    NzInputModule,
    NzSelectModule,
  ],
  template: `
    <section class="resolution" [formGroup]="resolutionForm">

      @if (warning(); as message: string) {
        <p class="resolution__warning">
          <nz-icon nzType="warning" nzTheme="fill" />
          {{ message }}
        </p>
      }

      <div class="resolution__body">

        <fieldset class="resolution__choices">

          @for (choice of choices(); track choice.source) {
            <label
              class="resolution__choice"
              [class.is-selected]="draftSource() === choice.source"
              [attr.data-source]="choice.source"
            >
              <input
                type="radio"
                formControlName="source"
                [value]="choice.source"
              />

              <span class="resolution__choice__head">
                <span class="resolution__choice__label">
                  {{ choice.label }}
                </span>

                <kbd>{{ choice.shortcut }}</kbd>
              </span>

              <span
                class="resolution__choice__value"
                [class.is-empty]="isBlankValue(choice.value)"
              >
                {{ isBlankValue(choice.value) ? 'no value' : choice.value }}
              </span>
            </label>
          }

        </fieldset>

        @if (draftSource() === 'manual') {
          <div class="resolution__field">

            @if (kind() === 'select' && options().length > 0) {

              <nz-select
                class="resolution__control"
                formControlName="manualValue"
                nzPlaceHolder="Pick a value"
              >
                @for (option of options(); track option.value) {
                  <nz-option
                    [nzLabel]="option.label"
                    [nzValue]="option.value"
                  />
                }
              </nz-select>

            } @else if (kind() === 'date') {

              <input
                nz-input
                type="date"
                [ngModel]="dateInputValue()"
                [ngModelOptions]="{ standalone: true }"
                [disabled]="readOnly()"
                (ngModelChange)="onDateChange($event)"
              />

            } @else {

              <input
                #manualInput
                nz-input
                class="resolution__control"
                placeholder="Type the value to keep"
                formControlName="manualValue"
              />

            }

          </div>
        }

        @if (justificationRequired()) {
          <div class="resolution__field">

            <label
              class="resolution__label"
              for="reason-{{ fieldId() }}"
            >
              Why are you changing this?
              <span class="resolution__required">
                required
              </span>
            </label>

            <nz-select
              id="reason-{{ fieldId() }}"
              class="resolution__control"
              [nzPlaceHolder]="reasonPlaceholder"
              formControlName="reason"
            >
              @for (option of reasons; track option) {
                <nz-option
                  [nzLabel]="option"
                  [nzValue]="option"
                />
              }
            </nz-select>

          </div>

          @if (commentRequired()) {
            <div class="resolution__field">

              <label
                class="resolution__label"
                for="comment-{{ fieldId() }}"
              >
                Comment
                <span class="resolution__required">
                  required
                </span>
              </label>

              <input
                id="comment-{{ fieldId() }}"
                #commentInput
                nz-input
                class="resolution__control"
                placeholder="Explain the change"
                formControlName="comment"
              />

            </div>
          }
        }

      </div>

      <footer class="resolution__footer">

        <p class="resolution__hint">
          <kbd>1</kbd> to <kbd>{{ choices().length }}</kbd> pick ·
          <kbd>Ctrl</kbd>+<kbd>Enter</kbd> apply ·
          <kbd>Esc</kbd> close
        </p>

        <div class="resolution__buttons">

          @if (isOverride()) {
            <button
              type="button"
              class="resolution__button"
              [disabled]="readOnly()"
              (click)="onReset()"
            >
              Undo my change
            </button>
          }

          <button
            type="button"
            class="resolution__button"
            (click)="cancel.emit()"
          >
            Cancel
          </button>

          <button
            type="button"
            class="resolution__button resolution__button--primary"
            [disabled]="readOnly() || !canApply()"
            (click)="emitApply()"
          >
            Apply
          </button>

        </div>

      </footer>

    </section>
  `,
  styleUrl: './comparison-resolution.scss',
})
export class ComparisonResolution {

  readonly fieldId = input.required<string>();

  readonly kind =
    input.required<ComparisonFieldKind>();

  readonly options =
    input<readonly ComparisonOption[]>([]);

  readonly values =
    input.required<
      Readonly<Record<ComparisonSourceId, string | null>>
    >();

  readonly status =
    input.required<ComparisonStatus>();

  readonly resolution =
    input.required<Resolution>();

  readonly fallback =
    input.required<Resolution>();

  readonly isOverride = input(false);

  readonly readOnly = input(false);

  readonly apply = output<ResolutionPatch>();
  readonly cancel = output<void>();
  readonly reset = output<void>();

  private readonly host =
    inject<ElementRef<HTMLElement>>(ElementRef);

  private readonly commentInput =
    viewChild<ElementRef<HTMLInputElement>>('commentInput');

  protected readonly reasons = JUSTIFICATION_REASONS;

  protected readonly reasonPlaceholder =
    REASON_PLACEHOLDER;

  readonly resolutionForm = new FormGroup({
    source: new FormControl<ResolutionSource>('kyc', {
      nonNullable: true,
    }),

    manualValue: new FormControl<string>('', {
      nonNullable: true,
    }),

    reason: new FormControl<string>('', {
      nonNullable: true,
    }),

    comment: new FormControl<string>('', {
      nonNullable: true,
    }),
  });

  readonly draftSource = computed<ResolutionSource>(
    () => this.resolutionForm.controls.source.value
  );

  readonly manualValue = computed<string>(
    () => this.resolutionForm.controls.manualValue.value
  );

  readonly reason = computed<string>(
    () => this.resolutionForm.controls.reason.value
  );

  readonly comment = computed<string>(
    () => this.resolutionForm.controls.comment.value
  );

  readonly choices = computed<readonly SourceChoice[]>(() => {
    const values = this.values();

    return [
      {
        source: 'kyc',
        label: 'Keep client value',
        value: values.kyc,
        shortcut: '1',
      },
      {
        source: 'core',
        label: 'Take core value',
        value: values.core,
        shortcut: '2',
      },
      {
        source: 'manual',
        label: 'Type another value',
        value: this.manualValue() || null,
        shortcut: '3',
      },
    ];
  });

  readonly draftValue = computed<string | null>(() => {
    const source = this.draftSource();

    if (source === 'manual') {
      return this.manualValue().trim() || null;
    }

    return this.values()[source];
  });

  readonly justificationRequired = computed(() => {
    const fallback = this.fallback();

    return (
      this.draftSource() !== fallback.source ||
      !sameValue(this.draftValue(), fallback.value)
    );
  });

  readonly commentRequired = computed(
    () => this.reason() === REASON_REQUIRING_COMMENT
  );

  readonly canApply: Signal<boolean> = computed((): boolean => {
    if (
      this.draftSource() === 'manual' &&
      this.manualValue().trim() === ''
    ) {
      return false;
    }

    if (!this.justificationRequired()) {
      return true;
    }

    if (this.reason() === '') {
      return false;
    }

    return (
      !this.commentRequired() ||
      this.comment().trim().length >= MIN_COMMENT_LENGTH
    );
  });

  readonly warning: Signal<string> = computed((): string => {
    switch (this.status()) {
      case 'drift':
        return 'Value changed in the core system since the client sent the request.';

      case 'conflict':
        return 'The three sources hold three different values.';

      case 'removed':
        return 'The value existed when the case was created but is no longer present in the core system.';

      default:
        return '';
    }
  });

  readonly dateInputValue = computed((): string => {
    const value = this.manualValue();

    if (!value) {
      return '';
    }

    const parts = value.split('/');

    if (parts.length !== 3) {
      return value;
    }

    const [day, month, year] = parts;

    return `${year}-${month}-${day}`;
  });

  constructor() {

    effect(() => {
      const resolution = this.resolution();

      this.resolutionForm.reset(
        {
          source: resolution.source,
          manualValue:
            resolution.source === 'manual'
              ? resolution.value ?? ''
              : '',
          reason: resolution.reason ?? '',
          comment: resolution.comment ?? '',
        },
        {
          emitEvent: false,
        }
      );
    });

    effect(() => {
      if (this.readOnly()) {
        this.resolutionForm.disable({
          emitEvent: false,
        });
      } else {
        this.resolutionForm.enable({
          emitEvent: false,
        });
      }
    });

    afterNextRender((): void => {
      this.host.nativeElement.scrollIntoView({
        block: 'nearest',
        behavior: 'smooth',
      });
    });

    effect((): void => {
      if (
        this.commentRequired() &&
        !this.readOnly()
      ) {
        this.commentInput()
          ?.nativeElement
          .focus({
            preventScroll: true,
          });
      }
    });
  }

  isBlankValue(value: string | null): boolean {
    return isBlank(value);
  }

  selectSource(source: ResolutionSource): void {
    if (this.readOnly()) {
      return;
    }

    const currentReason =
      this.resolutionForm.controls.reason.value;

    this.resolutionForm.controls.source.setValue(source);

    if (
      source === 'core' &&
      currentReason === ''
    ) {
      this.resolutionForm.controls.reason.setValue(
        REASON_FOR_CORE_VALUE
      );
    }
  }

  emitApply(): void {
    if (
      this.readOnly() ||
      !this.canApply()
    ) {
      return;
    }

    this.apply.emit({
      source: this.draftSource(),
      value: this.draftValue(),
      reason: this.justificationRequired()
        ? this.reason()
        : '',
      comment: this.commentRequired()
        ? this.comment().trim()
        : '',
    });
  }

  onReset(): void {
    if (this.readOnly()) {
      return;
    }

    this.reset.emit();
  }

  onDateChange(value: string | null): void {
    if (this.readOnly()) {
      return;
    }

    if (!value) {
      this.resolutionForm.controls.manualValue.setValue('');
      return;
    }

    const [year, month, day] = value.split('-');

    this.resolutionForm.controls.manualValue.setValue(
      `${day}/${month}/${year}`
    );
  }

  @HostListener('keydown', ['$event'])
  onKeydown(event: KeyboardEvent): void {

    if (event.key === 'Escape') {
      event.stopPropagation();
      this.cancel.emit();
      return;
    }

    if (this.readOnly()) {
      return;
    }

    if (
      event.key === 'Enter' &&
      (event.ctrlKey || event.metaKey)
    ) {
      event.preventDefault();
      event.stopPropagation();

      this.emitApply();

      return;
    }

    const shortcut: SourceChoice | undefined =
      this.choices().find(
        (choice: SourceChoice) =>
          choice.shortcut === event.key
      );

    if (
      shortcut &&
      (event.altKey || !isTextInput(event.target))
    ) {
      event.preventDefault();
      event.stopPropagation();

      this.selectSource(shortcut.source);
    }
  }
}
