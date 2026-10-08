protected onDocumentRejected(document: ICaseDocument): void {
  this.rejectDocument(document.documentId)
    .subscribe({
      next: () => {
        this.documentsRefresh.update(value => value + 1);

        this.showSaveNotification(
          'success',
          'Document rejected',
          'The document has been rejected successfully.'
        );
      },

      error: error => {
        console.error('Error rejecting document', error);

        this.showSaveNotification(
          'error',
          'Rejection failed',
          'Unable to reject the document.'
        );
      }
    });
}
