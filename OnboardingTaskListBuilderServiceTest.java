<div class="document-form">
  <div class="document-form__header">
    Upload a new document
  </div>

  <div class="document-form__body">
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

    <div class="document-form__row">
      <label>
        <span class="required">*</span>
        Document type:
      </label>

      <nz-select
        [formControl]="form.controls.documentType"
        nzPlaceHolder="Select a document type"
        (ngModelChange)="onDocumentTypeChanged($event)"
      >
        @for (type of documentTypes(); track $index) {
          <nz-option
            [nzValue]="type"
            [nzLabel]="type"
          />
        }
      </nz-select>
    </div>

    <div class="document-form__row">
      <label>
        <span class="required">*</span>
        Filename:
      </label>

      <input
        nz-input
        [formControl]="form.controls.filename"
        placeholder="Filename"
      />
    </div>

    @for (
      metadata of metadataConfiguration()[form.controls.documentType.value] ?? [];
      track $index
    ) {
      <div class="document-form__row">
        <label>
          {{ metadata }}:
        </label>

        <input
          nz-input
          [value]="metadataValue($any(metadata))"
          (input)="updateMetadata(
            $any(metadata),
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
