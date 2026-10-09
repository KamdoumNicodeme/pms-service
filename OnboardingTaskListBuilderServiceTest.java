<form class="document-form">

  <div class="document-form__file">
    <input
      #fileInput
      type="file"
      hidden
      (change)="onFileSelected($event)"
    />

    <button
      nz-button
      type="button"
      (click)="fileInput.click()"
    >
      <span nz-icon nzType="upload"></span>
      Choose a file
    </button>

    <span class="document-form__filename">
      {{ selectedFile()?.name ?? 'No file chosen' }}
    </span>
  </div>

  <div class="document-form__field">
    <label>
      <span class="required">*</span>
      Document type
    </label>

    <nz-select
      [ngModel]="selectedDocumentType()"
      (ngModelChange)="onDocumentTypeChange($event)"
      name="documentType"
      nzPlaceHolder="Select a document type"
    >
      @for (type of documentTypes(); track type.name) {
        <nz-option
          [nzValue]="type.name"
          [nzLabel]="type.name"
        />
      }
    </nz-select>
  </div>

  <div class="document-form__field">
    <label>
      <span class="required">*</span>
      Filename
    </label>

    <input
      nz-input
      [ngModel]="filename()"
      (ngModelChange)="filename.set($event)"
      name="filename"
      placeholder="Filename"
    />
  </div>

  @for (metadata of metadataFields(); track metadata.name) {
    <div class="document-form__field">
      <label>
        @if (metadata.mandatory) {
          <span class="required">*</span>
        }

        {{ metadata.name }}
      </label>

      <input
        nz-input
        [ngModel]="metadataValue(metadata.name)"
        (ngModelChange)="setMetadataValue(metadata.name, $event)"
        [name]="metadata.name"
      />
    </div>
  }

  <div class="document-form__actions">

    <button
      nz-button
      type="button"
      (click)="cancelled.emit()"
    >
      Cancel
    </button>

    <button
      nz-button
      nzType="primary"
      type="button"
      [disabled]="!canSubmit()"
      (click)="submit()"
    >
      Submit
    </button>

  </div>

</form>
