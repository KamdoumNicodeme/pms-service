<nz-modal
  [nzVisible]="uploadModalVisible()"
  nzTitle="Upload a new document"
  [nzFooter]="null"
  [nzWidth]="900"
  [nzMaskClosable]="false"
  (nzOnCancel)="closeUploadModal()"
>
  <ng-container *nzModalContent>

    <upload-document
      [getDocumentConfigType]="getDocumentConfigType()"
      [getDocumentMetadata]="getDocumentMetadata()"
      (cancelled)="closeUploadModal()"
      (submitted)="onUploadDocument($event)"
    />

  </ng-container>
</nz-modal>
