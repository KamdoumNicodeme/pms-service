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

        <nz-icon [nzType]="chipIcon()" />

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

        <nz-icon nzType="delete" />

      </button>
    }

  </div>


  <div class="row__body">

    <!-- ================================ -->
    <!-- STRUCTURED ENTRY : TAX INFO -->
    <!-- ================================ -->

    @if (entry().fields?.core?.length) {

      <div
        class="row__fields"
        (click)="toggle.emit()">

        @for (
          field of entry().fields?.core ?? [];
          track field.key
        ) {

          <div class="row__field">

            <span class="row__field-label">
              {{ field.label ?? field.key }}
            </span>

            <span class="row__field-value">
              {{ field.value ?? '-' }}
            </span>

          </div>

        }

        <nz-icon
          class="row__structured-edit"
          [nzType]="expanded() ? 'up' : 'edit'"
        />

      </div>

    } @else {

      <!-- ================================ -->
      <!-- SIMPLE ENTRY : EMAIL / PHONE -->
      <!-- ================================ -->

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

    }

  </div>

</div>


<!-- ======================================= -->
<!-- EDITION -->
<!-- ======================================= -->

@if (expanded()) {

  @if (entry().fields?.core?.length) {

    <!-- STRUCTURED EDITOR -->

    <section class="structured-resolution">

      @for (
        field of entry().fields?.core ?? [];
        track field.key
      ) {

        @let definition = definitionFor(field.key);

        <div class="structured-resolution__field">

          <label class="structured-resolution__label">
            {{ field.label ?? field.key }}
          </label>

          <!-- SELECT -->
          @if (
            definition?.kind === 'select'
            && (definition?.options?.length ?? 0) > 0
          ) {

            <nz-select
              class="structured-resolution__control"
              [ngModel]="draftValue(field.key)"
              (ngModelChange)="updateDraftValue(field.key, $event)"
            >

              @for (
                option of definition?.options ?? [];
                track option.value
              ) {

                <nz-option
                  [nzValue]="option.value"
                  [nzLabel]="option.label"
                />

              }

            </nz-select>

          }

          <!-- TEXT -->
          @else {

            <input
              nz-input
              class="structured-resolution__control"
              [disabled]="definition?.editable === false"
              [ngModel]="draftValue(field.key)"
              (ngModelChange)="updateDraftValue(field.key, $event)"
            />

          }

        </div>

      }


      <div class="structured-resolution__actions">

        <button
          type="button"
          class="resolution__button"
          (click)="toggle.emit()">
          Cancel
        </button>

        <button
          type="button"
          class="resolution__button resolution__button--primary"
          (click)="applyStructured()">
          Apply
        </button>

      </div>

    </section>

  } @else {

    <!-- TON COMPOSANT EXISTANT POUR LES VALEURS SIMPLES -->

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
