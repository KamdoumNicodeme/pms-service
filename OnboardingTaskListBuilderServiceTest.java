protected onDocumentRejected(document: ICaseDocument): void {
  this.rejectDocument()(document.documentId).subscribe({
    next: (): void => {

      this.refreshDocuments()();

      this.showSaveNotification(
        'success',
        'Document rejected',
        'The document has been rejected successfully.'
      );
    },

    error: (error: any) => {
      console.error('Error rejecting document', error);

      this.showSaveNotification(
        'error',
        'Rejection failed',
        'Unable to reject the document.'
      );
    }
  });
}
