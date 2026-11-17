import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Signal, WritableSignal } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { BehaviorSubject } from 'rxjs';

import { EditDocumentComponent } from './edit-document.component';

// Infer the DTOs from the component's public inputs and outputs, so no model
// import paths have to be guessed in this spec.
type CaseDocument = ReturnType<EditDocumentComponent['document']>;
type EditDocumentRequest = Parameters<EditDocumentComponent['submitted']['emit']>[0];

type MetadataField = {
  name: string;
  type: string;
  mandatory: boolean;
};

// The methods/signals are protected in the component. This testing-only view
// avoids changing the component's visibility or scattering `as any` everywhere.
type EditDocumentView = {
  form: FormGroup<{
    documentType: FormControl<string | null>;
    filename: FormControl<string>;
    status: FormControl<string | null>;
  }>;
  selectedFile: WritableSignal<File>;
  metadataValues: WritableSignal<Record<string, string>>;
  documentTypes: Signal<unknown[]>;
  metadataConfiguration: Signal<Record<string, MetadataField[]>>;
  documentTypeValue: Signal<string | null>;
  selectedMetadata: Signal<MetadataField[]>;
  canSubmit: Signal<boolean>;

  onFileSelected(event: Event): void;
  onDocumentTypeChanged(documentType: string): void;
  updateMetadata(key: string, value: string): void;
  metadataValue(key: string): string;
  metadataInputType(type: string): string;
  cancel(): void;
  submit(): void;
};

const DOCUMENT_TYPES = [
  { type: 'PASSPORT', label: 'Passport' },
  { type: 'UTILITY_BILL', label: 'Utility bill' },
];

const METADATA_CONFIG: Record<string, MetadataField[]> = {
  PASSPORT: [
    { name: 'passportNumber', type: 'TEXT', mandatory: true },
    { name: 'expiryDate', type: 'DATE', mandatory: false },
    { name: 'issuingCountry', type: 'TEXT', mandatory: false },
  ],
  UTILITY_BILL: [
    { name: 'accountNumber', type: 'TEXT', mandatory: true },
  ],
};

function makeDocument(overrides: Record<string, unknown> = {}): CaseDocument {
  return {
    documentId: 'DOC-001',
    type: 'PASSPORT',
    name: 'old-passport.pdf',
    status: 'PENDING',
    metadata: { passportNumber: 'P12345', expiryDate: '2027-05-01' },
    ...overrides,
  } as unknown as CaseDocument;
}

describe('EditDocumentComponent', () => {
  let fixture: ComponentFixture<EditDocumentComponent>;
  let component: EditDocumentComponent;
  let view: EditDocumentView;
  let types$: BehaviorSubject<typeof DOCUMENT_TYPES>;
  let metadata$: BehaviorSubject<Record<string, MetadataField[]>>;

  async function flush(): Promise<void> {
    fixture.detectChanges();
    await fixture.whenStable();
    fixture.detectChanges();
  }

  async function render(document: CaseDocument = makeDocument()): Promise<void> {
    // All three inputs are required: set them before the first change detection.
    fixture.componentRef.setInput('document', document);
    fixture.componentRef.setInput('getDocumentConfigType', () => types$.asObservable());
    fixture.componentRef.setInput('getDocumentMetadata', () => metadata$.asObservable());
    await flush();
  }

  beforeEach(async () => {
    types$ = new BehaviorSubject(DOCUMENT_TYPES);
    metadata$ = new BehaviorSubject(METADATA_CONFIG);

    await TestBed.configureTestingModule({
      imports: [EditDocumentComponent],
    })
      .overrideComponent(EditDocumentComponent, {
        // Isolate component logic. The real template and NG-ZORRO widgets are
        // tested separately; no HTML child-component dependencies here.
        set: { template: '', imports: [] },
      })
      .compileComponents();

    fixture = TestBed.createComponent(EditDocumentComponent);
    component = fixture.componentInstance;
    view = component as unknown as EditDocumentView;
  });

  describe('initialization and document input', () => {
    it('should create and populate the form from the existing document', async () => {
      await render();

      expect(component).toBeTruthy();
      expect(view.form.getRawValue()).toEqual({
        documentType: 'PASSPORT',
        filename: 'old-passport.pdf',
        status: 'PENDING',
      });
      expect(view.metadataValues()).toEqual({
        passportNumber: 'P12345',
        expiryDate: '2027-05-01',
      });
      expect(view.selectedFile()).toBeInstanceOf(File);
      expect(view.selectedFile().name).toBe('');
    });

    it('should set status to null when the document has no status', async () => {
      await render(makeDocument({ status: undefined }));
      expect(view.form.controls.status.value).toBeNull();
    });

    it('should load empty metadata when the document has none', async () => {
      await render(makeDocument({ metadata: null }));
      expect(view.metadataValues()).toEqual({});
    });

    it('should convert existing metadata values to strings', async () => {
      await render(makeDocument({
        metadata: { count: 12, confirmed: true, empty: null },
      }));

      expect(view.metadataValues()).toEqual({
        count: '12',
        confirmed: 'true',
        empty: '',
      });
    });

    it('should update the form and metadata when the document input changes', async () => {
      await render();

      fixture.componentRef.setInput('document', makeDocument({
        name: 'new-document.pdf',
        status: 'REPLACED',
        metadata: { passportNumber: 'P99999' },
      }));
      await flush();

      expect(view.form.controls.filename.value).toBe('new-document.pdf');
      expect(view.form.controls.status.value).toBe('REPLACED');
      expect(view.metadataValues()).toEqual({ passportNumber: 'P99999' });
    });
  });

  describe('configuration and selected metadata', () => {
    it('should load document type options through the callback input', async () => {
      await render();
      expect(view.documentTypes()).toEqual(DOCUMENT_TYPES);
    });

    it('should select metadata configuration matching the document type', async () => {
      await render();
      expect(view.documentTypeValue()).toBe('PASSPORT');
      expect(view.selectedMetadata()).toEqual(METADATA_CONFIG.PASSPORT);
    });

    it('should update the metadata configuration when the observable emits', async () => {
      await render();
      const updated = {
        ...METADATA_CONFIG,
        PASSPORT: [{ name: 'idNumber', type: 'TEXT', mandatory: true }],
      };

      metadata$.next(updated);
      await flush();

      expect(view.metadataConfiguration()).toEqual(updated);
      expect(view.selectedMetadata()).toEqual(updated.PASSPORT);
    });

    it('should return no metadata configuration for an unknown document type', async () => {
      await render();
      view.form.controls.documentType.setValue('UNKNOWN_TYPE');
      await flush();

      expect(view.selectedMetadata()).toEqual([]);
    });
  });

  describe('metadata helpers and document type changes', () => {
    it('should return an empty string for missing metadata keys', async () => {
      await render();
      expect(view.metadataValue('missing')).toBe('');
    });

    it('should update one metadata field without removing other values', async () => {
      await render();
      view.updateMetadata('passportNumber', 'NEW-123');

      expect(view.metadataValues()).toEqual({
        passportNumber: 'NEW-123',
        expiryDate: '2027-05-01',
      });
    });

    it.each([
      ['DATE', 'date'],
      ['DATETIME', 'datetime-local'],
      ['NUMBER', 'number'],
      ['TEXT', 'text'],
      ['UNKNOWN', 'text'],
    ])('should map metadata input type %s to %s', async (type, expected) => {
      await render();
      expect(view.metadataInputType(type)).toBe(expected);
    });

    it('should keep existing metadata when the type remains unchanged', async () => {
      await render();
      view.onDocumentTypeChanged('PASSPORT');
      expect(view.metadataValues()).toEqual({
        passportNumber: 'P12345',
        expiryDate: '2027-05-01',
      });
    });

    it('should clear metadata and load the new configuration after changing type', async () => {
      await render();

      // Use the actual form event: onDocumentTypeChanged() itself writes with
      // emitEvent:false, so direct invocation would not update documentTypeValue.
      view.form.controls.documentType.setValue('UTILITY_BILL');
      await flush();

      expect(view.form.controls.documentType.value).toBe('UTILITY_BILL');
      expect(view.documentTypeValue()).toBe('UTILITY_BILL');
      expect(view.metadataValues()).toEqual({});
      expect(view.selectedMetadata()).toEqual(METADATA_CONFIG.UTILITY_BILL);
      expect(view.canSubmit()).toBe(false);
    });
  });

  describe('file selection', () => {
    it('should store the selected file and use its name in the form', async () => {
      await render();
      const file = new File(['updated'], 'updated-passport.pdf', { type: 'application/pdf' });
      const fakeInput = { files: [file] } as unknown as HTMLInputElement;

      view.onFileSelected({ target: fakeInput } as unknown as Event);

      expect(view.selectedFile()).toBe(file);
      expect(view.form.controls.filename.value).toBe('updated-passport.pdf');
    });

    it('should replace the selected file with an empty File when no file is provided', async () => {
      await render();
      const previousName = view.form.controls.filename.value;

      view.onFileSelected({ target: { files: [] } } as unknown as Event);

      expect(view.selectedFile().name).toBe('');
      // This is the behaviour of the current component: it leaves the name.
      expect(view.form.controls.filename.value).toBe(previousName);
    });
  });

  describe('submission validation', () => {
    it('should allow submission when type, filename and mandatory metadata are filled', async () => {
      await render();
      expect(view.canSubmit()).toBe(true);
    });

    it('should reject submission when the document type is missing', async () => {
      await render(makeDocument({ type: null }));
      expect(view.canSubmit()).toBe(false);
    });

    it('should reject submission when the filename contains only whitespace', async () => {
      await render(makeDocument({ name: '   ' }));
      expect(view.canSubmit()).toBe(false);
    });

    it('should reject submission if a mandatory metadata value is empty', async () => {
      await render(makeDocument({ metadata: { passportNumber: '   ' } }));
      expect(view.canSubmit()).toBe(false);
    });

    it('should not require optional metadata', async () => {
      await render(makeDocument({ metadata: { passportNumber: 'P12345' } }));
      expect(view.canSubmit()).toBe(true);
    });

    it('should touch form controls and not emit when submission is invalid', async () => {
      await render(makeDocument({ name: '', metadata: {} }));
      const spy = jest.fn();
      component.submitted.subscribe(spy);

      view.submit();

      expect(spy).not.toHaveBeenCalled();
      expect(view.form.controls.filename.touched).toBe(true);
      expect(view.form.controls.documentType.touched).toBe(true);
    });
  });

  describe('outputs', () => {
    it('should emit cancelled when cancel is requested', async () => {
      await render();
      const spy = jest.fn();
      component.cancelled.subscribe(spy);

      view.cancel();

      expect(spy).toHaveBeenCalledTimes(1);
    });

    it('should emit a trimmed request with only populated metadata', async () => {
      await render();
      const file = new File(['new content'], 'uploaded.pdf', { type: 'application/pdf' });
      view.onFileSelected({ target: { files: [file] } } as unknown as Event);
      view.form.controls.filename.setValue('  final-passport.pdf  ');
      view.updateMetadata('passportNumber', '  NEW-PASSPORT  ');
      view.updateMetadata('expiryDate', '   ');
      view.updateMetadata('issuingCountry', '  Luxembourg  ');

      const spy = jest.fn((_request: EditDocumentRequest) => undefined);
      component.submitted.subscribe(spy);

      view.submit();

      expect(spy).toHaveBeenCalledTimes(1);
      expect(spy).toHaveBeenCalledWith({
        file,
        name: 'final-passport.pdf',
        type: 'PASSPORT',
        metadata: [
          { key: 'passportNumber', type: 'TEXT', value: 'NEW-PASSPORT' },
          { key: 'issuingCountry', type: 'TEXT', value: 'Luxembourg' },
        ],
        status: 'PENDING',
      });
    });

    it('should emit the document status as null when none is available', async () => {
      await render(makeDocument({ status: null }));
      const spy = jest.fn();
      component.submitted.subscribe(spy);

      view.submit();

      expect(spy).toHaveBeenCalledWith(expect.objectContaining({ status: null }));
    });
  });

  // Important: canSubmit is computed(), but filename is read directly from
  // form.controls.filename.value (not a signal). Once canSubmit is cached,
  // changing only filename may not invalidate it. This describes a potential
  // production fix rather than asserting the buggy behaviour as a requirement.
  it.todo('should recompute canSubmit when only the filename changes');
});
