<form
  class="edit-document"
  [formGroup]="form"
  (ngSubmit)="submit()"
>
  <div class="edit-document__body">

    <div class="edit-document__file">
      <label class="edit-document__file-button">
        <span nz-icon nzType="upload"></span>
        Choose a new file

        <input
          type="file"
          hidden
          (change)="onFileSelected($event)"
        />
      </label>

      <span class="edit-document__file-name">
        @if (selectedFile(); as file) {
          {{ file.name }}
        } @else {
          No new file selected
        }
      </span>
    </div>

    <div class="edit-document__row">
      <label>
        <span class="required">*</span>
        Document type:
      </label>

      <nz-select
        formControlName="documentType"
        nzPlaceHolder="Select a document type"
        [nzShowSearch]="true"
        [nzAllowClear]="false"
      >
        @for (type of documentTypes(); track type.name) {
          <nz-option
            [nzLabel]="type.displayName"
            [nzValue]="type.name"
          />
        }
      </nz-select>
    </div>

    <div class="edit-document__row">
      <label>
        <span class="required">*</span>
        Filename:
      </label>

      <input
        nz-input
        formControlName="filename"
        placeholder="Filename"
      />
    </div>

    @for (metadata of selectedMetadata(); track metadata.name) {
      <div class="edit-document__row">
        <label>
          @if (metadata.mandatory) {
            <span class="required">*</span>
          }
          {{ metadata.name }}:
        </label>

        <input
          nz-input
          [type]="metadataInputType(metadata.type)"
          [value]="metadataValue(metadata.name)"
          [disabled]="metadata.immutable === true"
          [required]="metadata.mandatory"
          [attr.pattern]="metadata.type === 'STRING' ? metadata.pattern ?? null : null"
          (input)="updateMetadata(metadata.name, $any($event.target).value)"
        />
      </div>
    }

    <div class="edit-document__row">
      <label>Status:</label>

      <nz-select
        formControlName="status"
        nzPlaceHolder="Select a status"
        [nzAllowClear]="true"
      >
        <nz-option nzValue="Validated" nzLabel="Validated" />
        <nz-option nzValue="Rejected" nzLabel="Rejected" />
        <nz-option nzValue="Replaced" nzLabel="Replaced" />
      </nz-select>
    </div>

  </div>

  <div class="edit-document__footer">
    <button nz-button type="button" (click)="cancel()">
      Cancel
    </button>

    <button
      nz-button
      nzType="primary"
      type="submit"
      [disabled]="!canSubmit()"
    >
      Save changes
    </button>
  </div>
</form>
