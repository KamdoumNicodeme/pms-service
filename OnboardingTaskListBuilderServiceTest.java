@for (
  metadata of metadataConfiguration()[form.controls.documentType.value];
  track metadata.name
) {
  <div class="document-form__row">
    <label>
      @if (metadata.mandatory) {
        <span class="required">*</span>
      }

      {{ metadata.name }}:
    </label>

    @switch (metadata.type) {

      @case ('DATE') {
        <input
          type="date"
          nz-input
          [required]="metadata.mandatory"
          [value]="metadataValue(metadata.name.trim())"
          (input)="updateMetadata(
            metadata.name,
            $any($event.target).value
          )"
        />
      }

      @case ('NUMBER') {
        <input
          type="number"
          nz-input
          [required]="metadata.mandatory"
          [value]="metadataValue(metadata.name.trim())"
          (input)="updateMetadata(
            metadata.name,
            $any($event.target).value
          )"
        />
      }

      @case ('BOOLEAN') {
        <nz-select
          [ngModel]="metadataValue(metadata.name.trim())"
          (ngModelChange)="updateMetadata(metadata.name, $event)"
        >
          <nz-option nzValue="true" nzLabel="Yes" />
          <nz-option nzValue="false" nzLabel="No" />
        </nz-select>
      }

      @default {
        <input
          type="text"
          nz-input
          [pattern]="metadata.pattern ?? ''"
          [required]="metadata.mandatory"
          [value]="metadataValue(metadata.name.trim())"
          (input)="updateMetadata(
            metadata.name,
            $any($event.target).value
          )"
        />
      }

    }
  </div>
}
