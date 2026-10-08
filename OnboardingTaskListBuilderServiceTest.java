protected onDocumentValidated(document: ICaseDocument): void {
  this.validateDocument()(document.documentId).subscribe({
    next: (): void => {

      this.refreshDocuments()();

      this.showSaveNotification(
        'success',
        'Document validated',
        'The document has been validated successfully.'
      );
    },

    error: (error: any) => {
      console.error('Error validating document', error);

      this.showSaveNotification(
        'error',
        'Validation failed',
        'Unable to validate the document.'
      );
    }
  });
}
