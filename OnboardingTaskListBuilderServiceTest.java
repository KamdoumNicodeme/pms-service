import {
  Component,
  computed,
  effect,
  inject,
  input,
  signal,
  Signal,
  WritableSignal
} from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
import { switchMap } from 'rxjs';

import {
  ComparisonCounters,
  ComparisonFilter,
  ComparisonSectionDto,
  Resolution
} from '../../../../shared/models/comparison.model';

import {
  COMPARISON_DATA_SERVICE,
  ComparisonDataService
} from '../../../../services/comparison-data.service';

import { IPolicy } from '@lia/commons';

interface PolicySectionChanges {
  readonly sectionId: string;
  readonly changes: ReadonlyMap<string, Resolution>;
}

export class PolicyProfiling {

  readonly policy = input.required<IPolicy>();

  private readonly data =
    inject<ComparisonDataService>(COMPARISON_DATA_SERVICE);

  protected readonly sections = POLICY_PROFILING_SECTIONS;

  protected readonly activeId: WritableSignal<string | null> =
    signal(POLICY_PROFILING_SECTIONS[0]?.id ?? null);

  protected readonly filter: WritableSignal<ComparisonFilter> =
    signal<ComparisonFilter>('all');

  private readonly openSections: WritableSignal<ReadonlySet<string>> =
    signal(
      new Set(
        POLICY_PROFILING_SECTIONS.map(section => section.id)
      )
    );

  protected readonly section = toSignal(
    toObservable(this.activeId).pipe(
      switchMap(sectionId => {
        if (!sectionId) {
          throw new Error('Policy section id is required');
        }

        return this.data.getPolicySection(
          this.policy(),
          sectionId
        );
      })
    ),
    {
      initialValue: null
    }
  );

  protected isOpen(sectionId: string): boolean {
    return this.openSections().has(sectionId);
  }

  protected toggleSection(sectionId: string): void {
    this.openSections.update(current => {
      const next = new Set(current);

      if (next.has(sectionId)) {
        next.delete(sectionId);
      } else {
        next.add(sectionId);
      }

      return next;
    });
  }

  protected goTo(sectionId: string): void {
    this.openSections.update(
      current => new Set(current).add(sectionId)
    );

    this.activeId.set(sectionId);
  }

  protected setFilter(filter: ComparisonFilter): void {
    this.filter.set(filter);
  }

  protected onPolicyChanges(event: PolicySectionChanges): void {
    console.log(
      'POLICY CHANGES',
      event.sectionId,
      event.changes
    );
  }
}
