protected onUploadDocument(request: UploadDocumentRequest): void {
  this.uploadDocument(request).subscribe({
    next: () => {
      this.uploadModalVisible.set(false);

      this.refreshDocuments();

      this.showSaveNotification(
        'success',
        'Document uploaded',
        'The document has been uploaded successfully.'
      );
    },
    error: (error: unknown) => {
      console.error('Error uploading document', error);

      this.showSaveNotification(
        'error',
        'Upload failed',
        'Unable to upload the document.'
      );
    }
  });
}
