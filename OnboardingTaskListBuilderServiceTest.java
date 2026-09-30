<section class="documents">

  <header class="documents__header">
    <h3>Documents</h3>

    <span>
      {{ documents().length }} document(s)
    </span>
  </header>

  <div class="documents__table">

    <div class="documents__row documents__row--header">
      <span>Document name</span>
      <span>Type</span>
      <span>Date</span>
      <span>Status</span>
      <span>Actions</span>
    </div>

    @for (document of documents(); track document.documentId) {

      <div class="documents__row">

        <span>
          {{ document.name }}
        </span>

        <span>
          {{ document.type ?? '-' }}
        </span>

        <span>
          {{ document.date ?? '-' }}
        </span>

        <span>
          {{ document.status ?? '-' }}
        </span>

        <div class="documents__actions">

          <button
            type="button"
            (click)="download(document)">
            Download
          </button>

          <button
            type="button"
            (click)="validate(document)">
            Validate
          </button>

          <button
            type="button"
            (click)="reject(document)">
            Reject
          </button>

        </div>

      </div>

    } @empty {

      <div class="documents__empty">
        No documents available
      </div>

    }

  </div>

</section>
