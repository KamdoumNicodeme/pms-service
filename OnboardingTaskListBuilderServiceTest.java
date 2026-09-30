protected readonly validateDocument =
  input.required<(documentId: string) => Observable<void>>();

protected readonly rejectDocument =
  input.required<(documentId: string) => Observable<void>>();

protected readonly downloadDocument =
  input.required<(documentId: string) => Observable<Blob>>();


protected onDocumentValidated(document: ICaseDocument): void {
  this.validateDocument()(document.documentId).subscribe({
    next: () => {
      console.log('[CCI] DOCUMENT VALIDATED', document);
    },
    error: (error) => {
      console.error('[CCI] VALIDATE DOCUMENT ERROR', error);
    }
  });
}

protected onDocumentRejected(document: ICaseDocument): void {
  this.rejectDocument()(document.documentId).subscribe({
    next: () => {
      console.log('[CCI] DOCUMENT REJECTED', document);
    },
    error: (error) => {
      console.error('[CCI] REJECT DOCUMENT ERROR', error);
    }
  });
}

protected onDocumentDownloaded(document: ICaseDocument): void {
  this.downloadDocument()(document.documentId).subscribe({
    next: (blob: Blob) => {
      const url = URL.createObjectURL(blob);

      const link = window.document.createElement('a');
      link.href = url;
      link.download = document.name ?? 'document';

      link.click();

      URL.revokeObjectURL(url);
    },
    error: (error) => {
      console.error('[CCI] DOWNLOAD DOCUMENT ERROR', error);
    }
  });
}
