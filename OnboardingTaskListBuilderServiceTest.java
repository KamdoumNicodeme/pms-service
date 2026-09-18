import {
  ChangeDetectionStrategy,
  Component,
  computed,
  input,
  InputSignal,
  signal,
  Signal,
  WritableSignal
} from '@angular/core';

import {
  ComparisonCounters,
  ComparisonFilter
} from '../../../../shared/models/comparison.model';

import {
  POLICY_PROFILING_SECTIONS
} from '../../client-profiling.configuration';

@Component({
  selector: 'policy-profiling',
  standalone: true,
  templateUrl: './policy-profiling.html',
  styleUrl: './policy-profiling.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
  imports: [
    // Keep here the imports already used by your template:
    // ProfilingNav,
    // ProfilingPanel,
    // ComparisonToolbar,
    // PolicySection
  ]
})
export class PolicyProfiling {

  // ============================================================
  // INPUTS
  // ============================================================

  readonly policyNumber: InputSignal<string> =
    input.required<string>();


  // ============================================================
  // SECTIONS
  // ============================================================

  protected readonly sections = POLICY_PROFILING_SECTIONS;

  protected readonly activeId: WritableSignal<string | null> =
    signal<string | null>(
      POLICY_PROFILING_SECTIONS[0]?.id ?? null
    );

  private readonly openSections: WritableSignal<ReadonlySet<string>> =
    signal<ReadonlySet<string>>(
      new Set(
        POLICY_PROFILING_SECTIONS.map(section => section.id)
      )
    );


  // ============================================================
  // FILTER
  // ============================================================

  protected readonly filter: WritableSignal<ComparisonFilter> =
    signal<ComparisonFilter>('all');

  protected setFilter(filter: ComparisonFilter): void {
    this.filter.set(filter);
  }


  // ============================================================
  // COUNTERS
  // ============================================================

  private readonly counters: WritableSignal<
    ReadonlyMap<string, ComparisonCounters>
  > = signal<ReadonlyMap<string, ComparisonCounters>>(
    new Map()
  );

  protected readonly totals: Signal<ComparisonCounters> =
    computed((): ComparisonCounters => {

      let all = 0;
      let changes = 0;
      let needsReview = 0;

      this.counters().forEach(
        (counter: ComparisonCounters): void => {
          all += counter.all;
          changes += counter.changes;
          needsReview += counter.needsReview;
        }
      );

      return {
        all,
        changes,
        needsReview
      };
    });

  protected countersFor(
    sectionId: string
  ): ComparisonCounters {

    return this.counters().get(sectionId) ?? {
      all: 0,
      changes: 0,
      needsReview: 0
    };
  }

  protected onCounters(
    sectionId: string,
    counters: ComparisonCounters
  ): void {

    this.counters.update(
      (
        current: ReadonlyMap<string, ComparisonCounters>
      ): ReadonlyMap<string, ComparisonCounters> => {

        const next =
          new Map<string, ComparisonCounters>(current);

        next.set(sectionId, counters);

        return next;
      }
    );
  }


  // ============================================================
  // SECTION STATE
  // ============================================================

  protected isOpen(sectionId: string): boolean {
    return this.openSections().has(sectionId);
  }

  protected toggleSection(sectionId: string): void {

    this.openSections.update(
      (
        current: ReadonlySet<string>
      ): ReadonlySet<string> => {

        const next = new Set<string>(current);

        if (next.has(sectionId)) {
          next.delete(sectionId);
        } else {
          next.add(sectionId);
        }

        return next;
      }
    );
  }


  // ============================================================
  // NAVIGATION
  // ============================================================

  protected goTo(sectionId: string): void {

    this.openSections.update(
      (
        current: ReadonlySet<string>
      ): ReadonlySet<string> => {

        const next = new Set<string>(current);

        next.add(sectionId);

        return next;
      }
    );

    this.activeId.set(sectionId);

    // Scroll to the selected section after it has been opened.
    queueMicrotask((): void => {

      document
        .querySelector(`[data-section="${sectionId}"]`)
        ?.scrollIntoView({
          behavior: 'smooth',
          block: 'start'
        });
    });
  }
}
