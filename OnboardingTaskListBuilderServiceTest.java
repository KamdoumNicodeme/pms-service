import {
  ChangeDetectionStrategy,
  Component,
  effect,
  inject,
  input,
  InputSignal,
  output,
  OutputEmitterRef
} from '@angular/core';

import {
  rxResource
} from '@angular/core/rxjs-interop';

import {
  COMPARISON_DATA_SERVICE,
  ComparisonDataService
} from '../../../../services/comparison-data.service';

import {
  ComparisonChanges,
  ComparisonCounters,
  ComparisonFilter,
  ComparisonSectionDto
} from '../../../../shared/models/comparison.model';

import { DataComparison } from '../data-comparison/data-comparison';

@Component({
  selector: 'policy-section',
  standalone: true,
  imports: [
    DataComparison
  ],
  templateUrl: './policy-section.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class PolicySection {

  // ============================================================
  // INPUTS
  // ============================================================

  readonly policyNumber: InputSignal<string> =
    input.required<string>();

  readonly sectionId: InputSignal<string> =
    input.required<string>();

  readonly filter: InputSignal<ComparisonFilter> =
    input<ComparisonFilter>('all');


  // ============================================================
  // OUTPUTS
  // ============================================================

  readonly countersChange: OutputEmitterRef<ComparisonCounters> =
    output<ComparisonCounters>();

  readonly changesChange: OutputEmitterRef<ComparisonChanges> =
    output<ComparisonChanges>();


  // ============================================================
  // DATA SERVICE
  // ============================================================

  private readonly data: ComparisonDataService =
    inject(COMPARISON_DATA_SERVICE);


  // ============================================================
  // SECTION DATA
  // ============================================================

  protected readonly section = rxResource({
    params: () => ({
      policyNumber: this.policyNumber(),
      sectionId: this.sectionId()
    }),

    stream: ({ params }) =>
      this.data.getPolicySection(
        params.policyNumber,
        params.sectionId
      )
  });


  // ============================================================
  // EVENTS
  // ============================================================

  protected onCounters(counters: ComparisonCounters): void {
    this.countersChange.emit(counters);
  }

  protected onChanges(changes: ComparisonChanges): void {
    this.changesChange.emit(changes);
  }
}
