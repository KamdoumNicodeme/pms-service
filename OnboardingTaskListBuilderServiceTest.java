
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormControl, FormGroup } from '@angular/forms';
import { Signal, WritableSignal } from '@angular/core';
import { of } from 'rxjs';

import { UploadDocumentComponent } from './upload-document.component';

type MetadataItem = {
  name: string;
  type: string;
  mandatory: boolean;
};

type ComponentUnderTest = {
  form: FormGroup<{
    documentType: FormControl<string | null>;
    filename: FormControl<string>;
  }>;
  selectedFile: WritableSignal<File | null>;
  metadataValues: WritableSignal<Record<string, string>>;
  documentTypeValue: Signal<string | null>;
  selectedMetadata: Signal<MetadataItem[]>;
  canSubmit: Signal<boolean>;

  onFileSelected(event: Event): void;
  onDocumentTypeChanged(documentType: string): void;
  updateMetadata(key: string, value: string): void;
  metadataValue(key: string): string;
  cancel(): void;
  submit(): void;
};

describe('UploadDocumentComponent', () => {
  let fixture: ComponentFixture<UploadDocumentComponent>;
  let component: UploadDocumentComponent;
  let view: ComponentUnderTest;

  let file: File;
  let getDocumentConfigType: jest.Mock;
  let getDocumentMetadata: jest.Mock;

  const metadataConfiguration = {
    PASSPORT: [
      {
        name: 'thirdPartyId',
        type: 'TEXT',
        mandatory: true,
      },
      {
        name: 'description',
        type: 'TEXT',
        mandatory: false,
      },
    ],
    ID_CARD: [
      {
        name: 'country',
        type: 'TEXT',
        mandatory: true,
      },
    ],
  };

  const fileEvent = (files: File[]): Event =>
    ({
      target: { files },
    }) as unknown as Event;

  async function render(): Promise<void> {
    fixture = TestBed.createComponent(UploadDocumentComponent);
    component = fixture.componentInstance;
    view = component as unknown as ComponentUnderTest;

    fixture.componentRef.setInput(
      'getDocumentConfigType',
      getDocumentConfigType
    );

    fixture.componentRef.setInput(
      'getDocumentMetadata',
      getDocumentMetadata
    );

    fixture.detectChanges();
    await fixture.whenStable();
    fixture.detectChanges();
  }

  function fillValidForm(): void {
    view.form.controls.documentType.setValue('PASSPORT');
    view.onFileSelected(fileEvent([file]));
    view.updateMetadata('thirdPartyId', 'TP-123');
  }

  beforeEach(async () => {
    file = new File(['document content'], 'passport.pdf', {
      type: 'application/pdf',
    });

    getDocumentConfigType = jest.fn().mockReturnValue(of([]));

    getDocumentMetadata = jest
      .fn()
      .mockReturnValue(of(metadataConfiguration));

    await TestBed.configureTestingModule({
      imports: [UploadDocumentComponent],
    })
      .overrideComponent(UploadDocumentComponent, {
        set: {
          template: '',
        },
      })
      .compileComponents();
  });

  afterEach(() => {
    jest.restoreAllMocks();
  });

  describe('Initialization', () => {
    it('should create the component', async () => {
      await render();

      expect(component).toBeTruthy();
    });

    it('should initialize the form with empty values', async () => {
      await render();

      expect(view.form.controls.documentType.value).toBeNull();
      expect(view.form.controls.filename.value).toBe('');
      expect(view.selectedFile()).toBeNull();
      expect(view.metadataValues()).toEqual({});
      expect(view.canSubmit()).toBe(false);
    });

    it('should load document configuration and metadata', async () => {
      await render();

      expect(getDocumentConfigType).toHaveBeenCalled();
      expect(getDocumentMetadata).toHaveBeenCalled();
    });
  });

  describe('Document type and metadata', () => {
    it('should return no metadata when no document type is selected', async () => {
      await render();

      expect(view.selectedMetadata()).toEqual([]);
    });

    it('should return metadata associated with the selected document type', async () => {
      await render();

      view.form.controls.documentType.setValue('PASSPORT');

      expect(view.selectedMetadata()).toEqual(
        metadataConfiguration.PASSPORT
      );
    });

    it('should reset metadata when the document type changes', async () => {
      await render();

      view.form.controls.documentType.setValue('PASSPORT');
      view.updateMetadata('thirdPartyId', 'TP-123');

      expect(view.metadataValues()).toEqual({
        thirdPartyId: 'TP-123',
      });

      view.form.controls.documentType.setValue('ID_CARD');

      expect(view.metadataValues()).toEqual({});
      expect(view.selectedMetadata()).toEqual(
        metadataConfiguration.ID_CARD
      );
    });

    it('should update metadata without removing other values', async () => {
      await render();

      view.updateMetadata('thirdPartyId', 'TP-123');
      view.updateMetadata('description', 'Identity document');

      expect(view.metadataValues()).toEqual({
        thirdPartyId: 'TP-123',
        description: 'Identity document',
      });
    });

    it('should return an empty string for missing metadata', async () => {
      await render();

      expect(view.metadataValue('unknown')).toBe('');
    });
  });

  describe('File selection', () => {
    it('should store the selected file', async () => {
      await render();

      view.onFileSelected(fileEvent([file]));

      expect(view.selectedFile()).toBe(file);
    });

    it('should populate the filename when it is empty', async () => {
      await render();

      view.onFileSelected(fileEvent([file]));

      expect(view.form.controls.filename.value).toBe(
        'passport.pdf'
      );
    });

    it('should preserve an existing filename when selecting a file', async () => {
      await render();

      view.form.controls.filename.setValue('custom-name.pdf');

      view.onFileSelected(fileEvent([file]));

      expect(view.form.controls.filename.value).toBe(
        'custom-name.pdf'
      );
    });

    it('should clear the selected file when no file is provided', async () => {
      await render();

      view.onFileSelected(fileEvent([file]));
      view.onFileSelected(fileEvent([]));

      expect(view.selectedFile()).toBeNull();
    });
  });

  describe('Submission validation', () => {
    it('should reject submission when no file is selected', async () => {
      await render();

      view.form.controls.documentType.setValue('PASSPORT');
      view.form.controls.filename.setValue('passport.pdf');
      view.updateMetadata('thirdPartyId', 'TP-123');

      expect(view.canSubmit()).toBe(false);
    });

    it('should reject submission when document type is missing', async () => {
      await render();

      view.onFileSelected(fileEvent([file]));

      expect(view.canSubmit()).toBe(false);
    });

    it('should reject submission when mandatory metadata is missing', async () => {
      await render();

      view.form.controls.documentType.setValue('PASSPORT');
      view.onFileSelected(fileEvent([file]));

      expect(view.canSubmit()).toBe(false);
    });

    it('should reject whitespace-only mandatory metadata', async () => {
      await render();

      view.form.controls.documentType.setValue('PASSPORT');
      view.onFileSelected(fileEvent([file]));
      view.updateMetadata('thirdPartyId', '   ');

      expect(view.canSubmit()).toBe(false);
    });

    it('should allow submission when optional metadata is empty', async () => {
      await render();

      fillValidForm();

      expect(view.metadataValue('description')).toBe('');
      expect(view.canSubmit()).toBe(true);
    });

    it('should allow submission when all required values are provided', async () => {
      await render();

      fillValidForm();

      expect(view.canSubmit()).toBe(true);
    });

    it('should invalidate submission when the filename is cleared', async () => {
      await render();

      fillValidForm();
      expect(view.canSubmit()).toBe(true);

      view.form.controls.filename.setValue('   ');

      expect(view.canSubmit()).toBe(false);
    });

    it('should recompute validation when mandatory metadata is cleared', async () => {
      await render();

      fillValidForm();
      expect(view.canSubmit()).toBe(true);

      view.updateMetadata('thirdPartyId', '');

      expect(view.canSubmit()).toBe(false);
    });
  });

  describe('Submit', () => {
    it('should not emit when the form is invalid', async () => {
      await render();

      const emitSpy = jest.spyOn(component.submitted, 'emit');

      view.submit();

      expect(emitSpy).not.toHaveBeenCalled();
      expect(view.form.controls.documentType.touched).toBe(true);
      expect(view.form.controls.filename.touched).toBe(true);
    });

    it('should emit the upload request when the form is valid', async () => {
      await render();

      const emitSpy = jest.spyOn(component.submitted, 'emit');

      fillValidForm();

      view.form.controls.filename.setValue('updated-passport.pdf');
      view.updateMetadata('thirdPartyId', ' TP-123 ');
      view.updateMetadata('description', ' Reviewed ');

      view.submit();

      expect(emitSpy).toHaveBeenCalledTimes(1);

      expect(emitSpy).toHaveBeenCalledWith({
        file,
        documentType: 'PASSPORT',
        filename: 'updated-passport.pdf',
        metadata: [
          {
            key: 'thirdPartyId',
            type: 'TEXT',
            value: 'TP-123',
          },
          {
            key: 'description',
            type: 'TEXT',
            value: 'Reviewed',
          },
        ],
      });
    });

    it('should exclude empty optional metadata from the request', async () => {
      await render();

      const emitSpy = jest.spyOn(component.submitted, 'emit');

      fillValidForm();
      view.updateMetadata('description', '   ');

      view.submit();

      expect(emitSpy).toHaveBeenCalledWith(
        expect.objectContaining({
          metadata: [
            {
              key: 'thirdPartyId',
              type: 'TEXT',
              value: 'TP-123',
            },
          ],
        })
      );
    });

    it('should not emit when the selected file is removed', async () => {
      await render();

      const emitSpy = jest.spyOn(component.submitted, 'emit');

      fillValidForm();
      view.onFileSelected(fileEvent([]));

      view.submit();

      expect(emitSpy).not.toHaveBeenCalled();
    });
  });

  describe('Cancel', () => {
    it('should emit cancelled when cancel is called', async () => {
      await render();

      const emitSpy = jest.spyOn(component.cancelled, 'emit');

      view.cancel();

      expect(emitSpy).toHaveBeenCalledTimes(1);
    });
  });
});
