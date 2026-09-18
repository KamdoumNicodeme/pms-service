import {
  Component,
  computed,
  effect,
  ElementRef,
  inject,
  input,
  InputSignal,
  output,
  OutputEmitterRef,
  signal,
  Signal,
  WritableSignal,
} from '@angular/core';

import {
  ComparisonCounters,
  ComparisonFilter,
  ComparisonChanges,
} from '../../../../shared/models/comparison.model';

import {
  NavSection,
  ReviewState,
} from '../../models/client-profiling.model';

import {
  POLICY_PROFILING_SECTIONS,
} from '../../client-profiling.configuration';

import { reviewState } from '../../utils/review-state.utils';

@Component({
  selector: 'policy-profiling',
  standalone: true,
  templateUrl: './policy-profiling.html',
  styleUrl: './policy-profiling.scss',
})
export class PolicyProfiling {

  private readonly host: ElementRef<HTMLElement> =
    inject<ElementRef<HTMLElement>>(ElementRef);

  readonly policyNumber: InputSignal<string> =
    input.required<string>();

  /**
   * Lets the tab bar show where this policy stands
   * without opening the tab.
   */
  readonly stateChange: OutputEmitterRef<ReviewState> =
    output<ReviewState>();

  /**
   * Sends all policy changes to the parent component.
   */
  readonly changesChange: OutputEmitterRef<ComparisonChanges> =
    output<ComparisonChanges>();

  protected readonly sections = POLICY_PROFILING_SECTIONS;

  private readonly counters:
    WritableSignal<ReadonlyMap<string, ComparisonCounters>> =
      signal<ReadonlyMap<string, ComparisonCounters>>(new Map());

  private readonly openSections:
    WritableSignal<ReadonlySet<string>> =
      signal<ReadonlySet<string>>(new Set());

  /**
   * Last state each section reported, so we react to changes
   * and not to noise.
   */
  private readonly lastState:
    Map<string, ReviewState> =
      new Map<string, ReviewState>();

  protected readonly activeId:
    WritableSignal<string | null> =
      signal<string | null>(null);

  protected readonly filter:
    WritableSignal<ComparisonFilter> =
      signal<ComparisonFilter>('all');

  /**
   * Feeds the single set of filter chips that covers
   * every policy section at once.
   */
  protected readonly totals: Signal<ComparisonCounters> =
    computed<ComparisonCounters>(() => {

      const sum: ComparisonCounters = {
        total: 0,
        changes: 0,
        attention: 0,
        reviewed: 0,
        overridden: 0,
      };

      for (const counters of this.counters().values()) {
        sum.total += counters.total;
        sum.changes += counters.changes;
        sum.attention += counters.attention;
        sum.reviewed += counters.reviewed;
        sum.overridden += counters.overridden;
      }

      return sum;
    });

  protected readonly navSections: Signal<readonly NavSection[]> =
    computed<readonly NavSection[]>(() => {

      const counters:
        ReadonlyMap<string, ComparisonCounters> =
          this.counters();

      return this.sections.map(section => {

        const summary:
          ComparisonCounters | undefined =
            counters.get(section.id);

        return {
          id: section.id,
          title: section.title,
          state: summary
            ? reviewState(summary)
            : ('loading' as const),
          pending: summary
            ? summary.attention - summary.reviewed
            : 0,
        };
      });
    });

  constructor() {

    effect((): void => {
      this.stateChange.emit(
        reviewState(this.totals())
      );
    });
  }

  protected countersFor(
    sectionId: string
  ): ComparisonCounters | null {

    return this.counters().get(sectionId) ?? null;
  }

  protected isOpen(
    sectionId: string
  ): boolean {

    return this.openSections().has(sectionId);
  }

  /**
   * A section unfolds when it has something to arbitrate
   * and folds away once it no longer does.
   *
   * Only a change of state moves it, so reopening
   * a folded section sticks.
   */
  protected onCounters(
    sectionId: string,
    counters: ComparisonCounters
  ): void {

    this.counters.update(
      (
        current: ReadonlyMap<string, ComparisonCounters>
      ): ReadonlyMap<string, ComparisonCounters> =>
        new Map(current).set(sectionId, counters)
    );

    const state: ReviewState =
      reviewState(counters);

    if (
      state === this.lastState.get(sectionId)
    ) {
      return;
    }

    this.lastState.set(sectionId, state);

    this.openSections.update(
      (
        current: ReadonlySet<string>
      ): ReadonlySet<string> => {

        const next = new Set(current);

        if (state === 'pending') {
          next.add(sectionId);
        } else {
          next.delete(sectionId);
        }

        return next;
      }
    );
  }

  /**
   * Filtering only pays off if the agent sees the matches
   * straight away, so a narrowed filter unfolds the sections
   * that still have something and folds the ones that have
   * nothing left to show.
   */
  protected setFilter(
    filter: ComparisonFilter
  ): void {

    this.filter.set(filter);

    if (filter === 'all') {
      return;
    }

    const counters:
      ReadonlyMap<string, ComparisonCounters> =
        this.counters();

    this.openSections.set(
      new Set(
        this.sections
          .filter(section => {

            const summary:
              ComparisonCounters | undefined =
                counters.get(section.id);

            if (!summary) {
              return false;
            }

            return filter === 'attention'
              ? summary.attention > 0
              : summary.changes > 0;
          })
          .map(section => section.id)
      )
    );
  }

  protected toggle(
    sectionId: string
  ): void {

    this.openSections.update(
      (
        current: ReadonlySet<string>
      ): ReadonlySet<string> => {

        const next = new Set(current);

        if (!next.delete(sectionId)) {
          next.add(sectionId);
        }

        return next;
      }
    );

    this.activeId.set(sectionId);
  }

  /**
   * Opens the section if needed, then brings it
   * to the top of the list.
   */
  protected goTo(
    sectionId: string
  ): void {

    this.openSections.update(
      (
        current: ReadonlySet<string>
      ): ReadonlySet<string> =>
        new Set(current).add(sectionId)
    );

    this.activeId.set(sectionId);

    const panel =
      this.host.nativeElement.querySelector(
        `[data-section="${sectionId}"]`
      );

    panel?.scrollIntoView({
      block: 'start',
      behavior: 'smooth',
    });
  }

  /**
   * Forwards the changes produced by a policy section
   * to the parent component.
   */
  protected onPolicyChanges(
    event: ComparisonChanges
  ): void {

    this.changesChange.emit(event);
  }
}
