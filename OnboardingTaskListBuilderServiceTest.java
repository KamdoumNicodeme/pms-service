<nz-tabs style="margin-top: 1rem;">

  <!-- POLICY TAB -->

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

      <policy-profiling

        [policy]="policy()"

      />

    </section>

  </nz-tab>
