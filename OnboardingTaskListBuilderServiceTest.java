<section class="client-profiling">

  @let caseId: string | undefined =
    getClientProfilingData()?.caseBusinessIdentifier;

  <header class="client-profiling__header">

    @if (summary.value(); as caseSummary: CaseSummary | undefined) {
      <h1>
        CLIENT PROFILING - {{ caseId }} - {{ caseSummary.title }}
      </h1>
    }

    <div class="client-profiling__header__actions">

      <button
        class="save-button"
        nz-button
        nzType="primary"
        nzShape="circle"
      >
        <nz-icon nzType="save" nzTheme="outline" />
      </button>

      <button nz-button nzType="primary">
        Status
      </button>

      <button nz-button nzType="primary">
        Complete
      </button>

    </div>

  </header>

  <summary
    class="client-profiling__summary"
    [facts]="summary.value()?.facts ?? []"
  ></summary>

  <nz-tabs style="margin-top: 1rem;">

    <!-- PHYSICAL / MORAL POLICY HOLDERS -->
    @for (
      holder of holders();
      track holder.thirdPartyId
    ) {

      <nz-tab>

        <ng-template nzTabLink>
          <span
            class="holder-tab"
            [attr.data-state]="stateOf(holder.thirdPartyId)"
          >

            <nz-icon
              class="holder-tab__avatar"
              nzType="user"
            />

            PH-{{ holderLabel(holder) }}

            @if (
              stateOf(holder.thirdPartyId);
              as state: ReviewState | null
            ) {
              <nz-icon
                class="holder-tab__state"
                nz-tooltip
                [nzTooltipTitle]="
                  state === 'pending'
                    ? 'Values still to review'
                    : 'Nothing left to review'
                "
                [nzType]="
                  state === 'pending'
                    ? 'exclamation-circle'
                    : 'check-circle'
                "
                nzTheme="fill"
              />
            }

          </span>
        </ng-template>

        <section class="client-profiling__content">

          <holder-profiling
            (stateChange)="
              onHolderState(
                holder.thirdPartyId,
                $event
              )
            "
            [holder]="holder"
            [policyNumber]="policyNumber()"
            [controllingPerson]="false"
          />

        </section>

      </nz-tab>
    }

    <!-- CONTROLLING PERSONS -->
    @for (
      controllingPerson of controllingPersons();
      track controllingPerson.thirdPartyId
    ) {

      <nz-tab>

        <ng-template nzTabLink>
          <span
            class="holder-tab"
            [attr.data-state]="stateOf(controllingPerson.thirdPartyId)"
          >

            <nz-icon
              class="holder-tab__avatar"
              nzType="user"
            />

            CP-{{ holderLabel(controllingPerson) }}

            @if (
              stateOf(controllingPerson.thirdPartyId);
              as state: ReviewState | null
            ) {
              <nz-icon
                class="holder-tab__state"
                nz-tooltip
                [nzTooltipTitle]="
                  state === 'pending'
                    ? 'Values still to review'
                    : 'Nothing left to review'
                "
                [nzType]="
                  state === 'pending'
                    ? 'exclamation-circle'
                    : 'check-circle'
                "
                nzTheme="fill"
              />
            }

          </span>
        </ng-template>

        <section class="client-profiling__content">

          <holder-profiling
            (stateChange)="
              onHolderState(
                controllingPerson.thirdPartyId,
                $event
              )
            "
            [holder]="controllingPerson"
            [policyNumber]="policyNumber()"
            [controllingPerson]="true"
          />

        </section>

      </nz-tab>
    }

    <!-- POLICY -->
    <nz-tab>

      <ng-template nzTabLink>

        <span class="holder-tab">

          <nz-icon
            class="holder-tab__avatar"
            nzType="file-text"
          />

          Policy

        </span>

      </ng-template>

      <section class="client-profiling__content">

        <policy-profiling />

      </section>

    </nz-tab>

  </nz-tabs>

</section>
