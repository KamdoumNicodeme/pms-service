@for (metadata of selectedMetadata(); track metadata.name) {
  <div class="document-form__field">

    <label>
      @if (metadata.mandatory) {
        <span class="required">*</span>
      }

      {{ metadata.name }}
    </label>

    @switch (metadata.type) {

      @case ('DATE') {
        <input
          nz-input
          type="date"
          [formControl]="metadataControl(metadata.name)"
        />
      }

      @case ('BOOLEAN') {
        <nz-select
          [formControl]="metadataControl(metadata.name)"
          nzPlaceHolder="Select a value"
        >
          <nz-option nzValue="true" nzLabel="Yes" />
          <nz-option nzValue="false" nzLabel="No" />
        </nz-select>
      }

      @case ('NUMBER') {
        <input
          nz-input
          type="number"
          [formControl]="metadataControl(metadata.name)"
        />
      }

      @default {
        <input
          nz-input
          type="text"
          [formControl]="metadataControl(metadata.name)"
          [placeholder]="metadata.name"
        />
      }

    }

  </div>
}
