protected onDocumentValidated(document: ICaseDocument): void {
  this.validateDocument(document.documentId)
    .subscribe({
      next: () => {
        this.documentsRefresh.update(value => value + 1);

        this.showSaveNotification(
          'success',
          'Document validated',
          'The document has been validated successfully.'
        );
      },

      error: error => {
        console.error('Error validating document', error);

        this.showSaveNotification(
          'error',
          'Validation failed',
          'Unable to validate the document.'
        );
      }
    });
}
