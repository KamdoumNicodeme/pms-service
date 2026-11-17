import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Signal, WritableSignal } from '@angular/core';
import { of } from 'rxjs';

import { DocumentsComponent } from './documents.component';

// The document and request types are derived from the component's public API.
// This keeps the spec independent of the models' import paths.
type CaseDocument = ReturnType<DocumentsComponent['documents']>[number];
type UploadRequest = Parameters<DocumentsComponent['uploadDocument']['emit']>[0];
type EditRequest = Parameters<DocumentsComponent['editDocument']['emit']>[0]['request'];

type DocumentsComponentView = {
  editingDocument: WritableSignal<CaseDocument | null>;
  uploadModalVisible: WritableSignal<boolean>;
  pageIndex: WritableSignal<number>;
  documentCount: Signal<number>;

  metadataEntries(metadata: Record<string, string> | null): [string, string][];
  primaryMetadata(metadata: Record<string, string> | null): [string, string] | null;
  remainingMetadata(metadata: Record<string, string> | null): [string, string][];
  displayDocumentType(type?: string | null): string;
  displaySource(source?: string | null): string;
  displayStatus(status?: string | null): string;
  statusClass(status?: string | null): string;
  sortByDocumentType(a: CaseDocument, b: CaseDocument): number;
  sortBySource(a: CaseDocument, b: CaseDocument): number;
  sortByStatus(a: CaseDocument, b: CaseDocument): number;

  onUploadDocument(request: UploadRequest): void;
  onDownload(document: CaseDocument): void;
  onEditDocumentSubmitted(request: EditRequest): void;
  openEditDocument(document: CaseDocument): void;
  closeEditDocument(): void;
  onReject(document: CaseDocument): void;
  onRemove(document: CaseDocument): void;
  onValidate(document: CaseDocument): void;
  openUploadModal(): void;
  closeUploadModal(): void;
};

const makeDocument = (
  overrides: Record<string, unknown> = {}
): CaseDocument =>
  ({
    documentId: 'DOC-1',
    type: 'IDENTITY_CARD',
    source: 'CLIENT_PORTAL',
    status: 'PENDING',
    ...overrides,
  }) as unknown as CaseDocument;

describe('DocumentsComponent', () => {
  let fixture: ComponentFixture<DocumentsComponent>;
  let component: DocumentsComponent;
  let view: DocumentsComponentView;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DocumentsComponent],
    })
      .overrideComponent(DocumentsComponent, {
        // Unit-test the TypeScript behaviour; child components have their own specs.
        set: { template: '', imports: [] },
      })
      .compileComponents();

    fixture = TestBed.createComponent(DocumentsComponent);
    component = fixture.componentInstance;
    view = component as unknown as DocumentsComponentView;

    // Required signal inputs must be provided before the first detection cycle.
    fixture.componentRef.setInput('getDocumentConfigType', () => of([]));
    fixture.componentRef.setInput('getDocumentMetadata', () => of({}));
    fixture.detectChanges();
  });

  describe('initial state and document count', () => {
    it('should create with default state', () => {
      expect(component).toBeTruthy();
      expect(component.documents()).toEqual([]);
      expect(view.documentCount()).toBe(0);
      expect(view.pageIndex()).toBe(1);
      expect(view.uploadModalVisible()).toBe(false);
      expect(view.editingDocument()).toBeNull();
    });

    it('should update documentCount when documents change', () => {
      fixture.componentRef.setInput('documents', [makeDocument(), makeDocument({ documentId: 'DOC-2' })]);
      fixture.detectChanges();

      expect(view.documentCount()).toBe(2);
    });
  });

  describe('metadata helpers', () => {
    it('should return no entries when metadata is null', () => {
      expect(view.metadataEntries(null)).toEqual([]);
      expect(view.primaryMetadata(null)).toBeNull();
      expect(view.remainingMetadata(null)).toEqual([]);
    });

    it('should exclude empty metadata values', () => {
      expect(view.metadataEntries({
        other: 'Other value',
        empty: '',
        // The runtime may contain null/undefined although the type says string.
        missing: null,
        absent: undefined,
      } as unknown as Record<string, string>)).toEqual([['other', 'Other value']]);
    });

    it('should place thirdPartyId first, policyNumber second, then other metadata', () => {
      const metadata = {
        country: 'LU',
        policyNumber: 'POL-123',
        description: 'Passport',
        thirdPartyId: 'TP-123',
      };

      expect(view.metadataEntries(metadata)).toEqual([
        ['thirdPartyId', 'TP-123'],
        ['policyNumber', 'POL-123'],
        ['country', 'LU'],
        ['description', 'Passport'],
      ]);
      expect(view.primaryMetadata(metadata)).toEqual(['thirdPartyId', 'TP-123']);
      expect(view.remainingMetadata(metadata)).toEqual([
        ['policyNumber', 'POL-123'],
        ['country', 'LU'],
        ['description', 'Passport'],
      ]);
    });

    it('should return the only available entry as primary metadata', () => {
      const metadata = { policyNumber: 'POL-1' };
      expect(view.primaryMetadata(metadata)).toEqual(['policyNumber', 'POL-1']);
      expect(view.remainingMetadata(metadata)).toEqual([]);
    });
  });

  describe('display formatting', () => {
    it('should format document types', () => {
      expect(view.displayDocumentType('IDENTITY_CARD')).toBe('Identity Card');
      expect(view.displayDocumentType(null)).toBe('-');
    });

    it('should format document sources', () => {
      expect(view.displaySource('CLIENT_PORTAL')).toBe('Client Portal');
      expect(view.displaySource(undefined)).toBe('-');
    });

    it.each([
      ['VALIDATED', 'Validated'],
      ['rejected', 'Rejected'],
      ['REPLACED', 'Replaced'],
      ['pending', 'Pending'],
      ['UNKNOWN_STATUS', 'UNKNOWN_STATUS'],
      [null, 'Pending'],
    ])('should format status %s as %s', (status, expected) => {
      expect(view.displayStatus(status)).toBe(expected);
    });

    it.each([
      ['VALIDATED', 'document-status--validated'],
      ['rejected', 'document-status--rejected'],
      ['REPLACED', 'document-status--replaced'],
      ['PENDING', 'document-status--pending'],
      [null, 'document-status--pending'],
    ])('should return the CSS class for status %s', (status, expected) => {
      expect(view.statusClass(status)).toBe(expected);
    });
  });

  describe('sorting', () => {
    it('should compare documents by their displayed type', () => {
      const identity = makeDocument({ type: 'IDENTITY_CARD' });
      const passport = makeDocument({ type: 'PASSPORT' });
      expect(view.sortByDocumentType(identity, passport)).toBeLessThan(0);
      expect(view.sortByDocumentType(passport, identity)).toBeGreaterThan(0);
    });

    it('should compare documents by their displayed source', () => {
      const client = makeDocument({ source: 'CLIENT_PORTAL' });
      const core = makeDocument({ source: 'CORE_SYSTEM' });
      expect(view.sortBySource(client, core)).toBeLessThan(0);
    });

    it('should compare documents by their displayed status', () => {
      const pending = makeDocument({ status: 'PENDING' });
      const validated = makeDocument({ status: 'VALIDATED' });
      expect(view.sortByStatus(pending, validated)).toBeLessThan(0);
    });
  });

  describe('output events', () => {
    it('should emit uploadDocument with the original request', () => {
      const request = { documentType: 'IDENTITY_CARD' } as unknown as UploadRequest;
      const emitted = jest.fn();
      component.uploadDocument.subscribe(emitted);

      view.onUploadDocument(request);

      expect(emitted).toHaveBeenCalledTimes(1);
      expect(emitted).toHaveBeenCalledWith(request);
    });

    it.each([
      ['downloadDocument', 'onDownload'],
      ['rejectDocument', 'onReject'],
      ['removeDocument', 'onRemove'],
      ['validateDocument', 'onValidate'],
    ] as const)('should emit %s on %s', (outputName, methodName) => {
      const document = makeDocument();
      const emitted = jest.fn();
      component[outputName].subscribe(emitted);

      view[methodName](document);

      expect(emitted).toHaveBeenCalledTimes(1);
      expect(emitted).toHaveBeenCalledWith(document);
    });

    it('should not emit editDocument if no document is selected', () => {
      const emitted = jest.fn();
      component.editDocument.subscribe(emitted);

      view.onEditDocumentSubmitted({} as EditRequest);

      expect(emitted).not.toHaveBeenCalled();
    });

    it('should emit documentId and request when editing is submitted', () => {
      const selectedDocument = makeDocument({ documentId: 'DOC-42' });
      const request = { description: 'Updated metadata' } as unknown as EditRequest;
      const emitted = jest.fn();
      component.editDocument.subscribe(emitted);
      view.openEditDocument(selectedDocument);

      view.onEditDocumentSubmitted(request);

      expect(emitted).toHaveBeenCalledTimes(1);
      expect(emitted).toHaveBeenCalledWith({
        documentId: 'DOC-42',
        request,
      });
    });
  });

  describe('modal state', () => {
    it('should open and close the upload modal', () => {
      view.openUploadModal();
      expect(view.uploadModalVisible()).toBe(true);
      view.closeUploadModal();
      expect(view.uploadModalVisible()).toBe(false);
    });

    it('should select and clear the document being edited', () => {
      const document = makeDocument();
      view.openEditDocument(document);
      expect(view.editingDocument()).toBe(document);
      view.closeEditDocument();
      expect(view.editingDocument()).toBeNull();
    });

    it('should close upload modal and go to last page after a save notification', () => {
      view.openUploadModal();
      view.pageIndex.set(1);

      fixture.componentRef.setInput('documents',
        Array.from({ length: 12 }, (_, index) => makeDocument({ documentId: `DOC-${index}` }))
      );
      fixture.componentRef.setInput('documentsSaved', 1);
      fixture.detectChanges();

      expect(view.uploadModalVisible()).toBe(false);
      expect(view.pageIndex()).toBe(3); // 12 documents, 5 per page
    });

    it('should keep page index at 1 when the saved document list is empty', () => {
      view.pageIndex.set(4);
      fixture.componentRef.setInput('documentsSaved', 1);
      fixture.detectChanges();

      expect(view.pageIndex()).toBe(1);
    });

    it('should close the edit modal after an edit notification', () => {
      view.openEditDocument(makeDocument());
      fixture.componentRef.setInput('documentEdited', 1);
      fixture.detectChanges();

      expect(view.editingDocument()).toBeNull();
    });

    it('should not reset modals when save/edit counters are zero', () => {
      const document = makeDocument();
      view.openUploadModal();
      view.openEditDocument(document);
      fixture.detectChanges();

      expect(view.uploadModalVisible()).toBe(true);
      expect(view.editingDocument()).toBe(document);
    });
  });
});
