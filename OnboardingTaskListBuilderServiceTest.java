private downloadDocument(caseDocument: ICaseDocument): void {
  const caseIdentifier = this.caseIdentifier();

  if (!caseIdentifier) {
    return;
  }

  this.#caseService
    .downloadDocument(caseIdentifier, caseDocument.identifier)
    .subscribe({
      next: (blob: Blob) => {
        const url = URL.createObjectURL(blob);

        const link = window.document.createElement('a');

        link.href = url;
        link.download = caseDocument.fileName ?? 'document';

        link.click();

        URL.revokeObjectURL(url);
      },
      error: (error) => {
        console.error('Error downloading document', error);
      }
    });
}

private validateDocument(caseDocument: ICaseDocument): void {
  const caseIdentifier = this.caseIdentifier();

  if (!caseIdentifier) {
    return;
  }

  this.#caseService
    .validateDocument(caseIdentifier, caseDocument.identifier)
    .subscribe({
      next: () => {
        console.log('Document validated');
      },
      error: (error) => {
        console.error('Error validating document', error);
      }
    });
}

private rejectDocument(caseDocument: ICaseDocument): void {
  const caseIdentifier = this.caseIdentifier();

  if (!caseIdentifier) {
    return;
  }

  this.#caseService
    .rejectDocument(caseIdentifier, caseDocument.identifier)
    .subscribe({
      next: () => {
        console.log('Document rejected');
      },
      error: (error) => {
        console.error('Error rejecting document', error);
      }
    });
}
