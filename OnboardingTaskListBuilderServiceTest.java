<div class="document-form">

  <div class="document-form__header">
    Upload a new document
  </div>

  <div class="document-form__body">

    <!-- FILE -->
    <div class="document-form__file">
      <label class="file-button">
        <span nz-icon nzType="upload"></span>
        Choose a file

        <input
          type="file"
          hidden
          (change)="onFileSelected($event)"
        />
      </label>

      <span class="document-form__filename">
        @if (selectedFile(); as file) {
          {{ file.name }}
        } @else {
          No file chosen
        }
      </span>
    </div>

    <!-- DOCUMENT TYPE -->
    <div class="document-form__row">

      <label>
        <span class="required">*</span>
        Document type:
      </label>

      <nz-select
        [formControl]="form.controls.documentType"
        nzPlaceHolder="Select a document type"
      >
        @for (type of documentTypes(); track type.value) {
          <nz-option
            [nzValue]="type.value"
            [nzLabel]="type.label"
          />
        }
      </nz-select>

    </div>

    <!-- FILENAME -->
    <div class="document-form__row">

      <label>
        <span class="required">*</span>
        Filename:
      </label>

      <input
        nz-input
        [formControl]="form.controls.filename"
      />

    </div>

    <!-- DYNAMIC METADATA -->
    @for (
      metadata of metadataDefinitions();
      track metadata.key
    ) {

      <div class="document-form__row">

        <label>
          @if (metadata.required) {
            <span class="required">*</span>
          }

          {{ metadata.label }}:
        </label>

        <input
          nz-input
          [value]="metadataValues()[metadata.key] ?? ''"
          (input)="updateMetadata(
            metadata.key,
            $any($event.target).value
          )"
        />

      </div>

    }

  </div>

  <div class="document-form__footer">

    <button
      nz-button
      type="button"
      (click)="cancel()"
    >
      Cancel
    </button>

    <button
      nz-button
      type="button"
      class="document-form__submit"
      [disabled]="!canSubmit()"
      (click)="submit()"
    >
      Submit
    </button>

  </div>

</div>
