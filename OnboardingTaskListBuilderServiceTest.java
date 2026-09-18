import {
  Component,
  effect,
  inject,
  input,
  InputSignal,
  output,
  OutputEmitterRef,
  ResourceRef,
} from '@angular/core';

import {
  rxResource,
} from '@angular/core/rxjs-interop';

import {
  COMPARISON_DATA_SERVICE,
  ComparisonDataService,
} from '../../../../shared/services/comparison-data.service';

import {
  ComparisonChanges,
  ComparisonCounters,
  ComparisonFilter,
  ComparisonSectionDto,
} from '../../../../shared/models/comparison.model';

@Component({
  selector: 'policy-section',
  standalone: true,
  templateUrl: './policy-section.html',
  styleUrl: './policy-section.scss',
})
export class PolicySection {

  readonly policyNumber:
    InputSignal<string> =
      input.required<string>();

  readonly sectionId:
    InputSignal<string> =
      input.required<string>();

  readonly filter:
    InputSignal<ComparisonFilter> =
      input<ComparisonFilter>('all');

  readonly countersChange:
    OutputEmitterRef<ComparisonCounters> =
      output<ComparisonCounters>();

  readonly changesChange:
    OutputEmitterRef<ComparisonChanges> =
      output<ComparisonChanges>();

  private readonly data:
    ComparisonDataService =
      inject(COMPARISON_DATA_SERVICE);

  protected readonly section:
    ResourceRef<ComparisonSectionDto | undefined> =
      rxResource({

        params: () => ({
          policyNumber: this.policyNumber(),
          sectionId: this.sectionId(),
        }),

        stream: ({ params }) =>
          this.data.getPolicySection(
            params.policyNumber,
            params.sectionId
          ),
      });
}
