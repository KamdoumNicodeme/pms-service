
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { SectionDocuments } from './section-documents';

type CaseDocument = Parameters<
  SectionDocuments['validateDocument']['emit']
>[0];

type SectionDocumentsView = {
  documents: () => readonly CaseDocument[];
  settled: () => number;
  validate: (document: CaseDocument) => void;
  reject: (document: CaseDocument) => void;
  download: (document: CaseDocument) => void;
  statusOf: (document: CaseDocument) => string | null;
};

describe('SectionDocuments', () => {
  let fixture: ComponentFixture<SectionDocuments>;
  let component: SectionDocuments;

  const makeDocument = (
    id: string,
    type: string,
    status: string | null = null
  ): CaseDocument =>
    ({
      id,
      type,
      status,
    }) as CaseDocument;

  const residence = makeDocument(
    'DOC_001',
    'PROOF_OF_RESIDENCE',
    'Pending'
  );

  const identity = makeDocument(
    'DOC_002',
    'IDI',
    'Validated'
  );

  const aeoi = makeDocument(
    'DOC_003',
    'AEOI',
    'Pending'
  );

  const w9bene = makeDocument(
    'DOC_004',
    'W9BENE',
    'Rejected'
  );

  const w9ben = makeDocument(
    'DOC_005',
    'W9BEN',
    'Pending'
  );

  const documents = [
    residence,
    identity,
    aeoi,
    w9bene,
    w9ben,
  ];

  const view = (): SectionDocumentsView =>
    component as unknown as SectionDocumentsView;

  function render(
    sectionId = 'contact-details',
    caseDocuments: readonly CaseDocument[] = documents
  ): void {
    fixture = TestBed.createComponent(SectionDocuments);
    component = fixture.componentInstance;

    fixture.componentRef.setInput('sectionId', sectionId);
    fixture.componentRef.setInput('caseDocuments', caseDocuments);

    fixture.detectChanges();
  }

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SectionDocuments],
    })
      .overrideComponent(SectionDocuments, {
        set: {
          template: '',
          imports: [],
        },
      })
      .compileComponents();
  });

  describe('Initialization', () => {
    it('should create the component', () => {
      render();

      expect(component).toBeTruthy();
    });

    it('should initialize caseDocuments to an empty array', () => {
      render('contact-details', []);

      expect(component.caseDocuments()).toEqual([]);
      expect(view().documents()).toEqual([]);
    });

    it('should receive the sectionId input', () => {
      render('identity-documents');

      expect(component.sectionId()).toBe('identity-documents');
    });
  });

  describe('Document filtering', () => {
    it('should return residence documents for contact details', () => {
      render('contact-details');

      expect(view().documents()).toEqual([residence]);
    });

    it('should return identity documents for identity-documents', () => {
      render('identity-documents');

      expect(view().documents()).toEqual([identity]);
    });

    it('should return tax documents for tax-information', () => {
      render('tax-information');

      expect(view().documents()).toEqual([
        aeoi,
        w9bene,
        w9ben,
      ]);
    });

    it('should return an empty array for an unknown section', () => {
      render('unknown-section');

      expect(view().documents()).toEqual([]);
    });

    it('should filter document types case-insensitively', () => {
      const lowerCaseDocument = makeDocument(
        'DOC_006',
        'proof_of_residence'
      );

      render('contact-details', [lowerCaseDocument]);

      expect(view().documents()).toEqual([
        lowerCaseDocument,
      ]);
    });

    it('should update documents when sectionId changes', () => {
      render('contact-details');

      expect(view().documents()).toEqual([residence]);

      fixture.componentRef.setInput(
        'sectionId',
        'identity-documents'
      );

      fixture.detectChanges();

      expect(view().documents()).toEqual([identity]);
    });

    it('should update documents when caseDocuments changes', () => {
      render('identity-documents', [identity]);

      const newIdentity = makeDocument(
        'DOC_007',
        'IDI',
        'Pending'
      );

      fixture.componentRef.setInput('caseDocuments', [
        identity,
        newIdentity,
      ]);

      fixture.detectChanges();

      expect(view().documents()).toEqual([
        identity,
        newIdentity,
      ]);
    });
  });

  describe('Document status', () => {
    it('should return the initial document status', () => {
      render('identity-documents');

      expect(view().statusOf(identity)).toBe('Validated');
    });

    it('should return null when a document has no status', () => {
      const document = makeDocument('DOC_006', 'IDI');

      render('identity-documents', [document]);

      expect(view().statusOf(document)).toBeNull();
    });

    it('should return the updated status after validation', () => {
      render('contact-details');

      view().validate(residence);

      expect(view().statusOf(residence)).toBe('Validated');
    });

    it('should return the updated status after rejection', () => {
      render('contact-details');

      view().reject(residence);

      expect(view().statusOf(residence)).toBe('Rejected');
    });

    it('should use the latest locally updated status', () => {
      render('contact-details');

      view().validate(residence);
      expect(view().statusOf(residence)).toBe('Validated');

      view().reject(residence);
      expect(view().statusOf(residence)).toBe('Rejected');
    });
  });

  describe('Settled documents', () => {
    it('should return zero when no documents are settled', () => {
      render('contact-details');

      expect(view().settled()).toBe(0);
    });

    it('should count validated documents', () => {
      render('identity-documents');

      expect(view().settled()).toBe(1);
    });

    it('should count rejected documents', () => {
      render('tax-information');

      expect(view().settled()).toBe(1);
    });

    it('should count both validated and rejected documents', () => {
      const first = makeDocument('1', 'IDI', 'Validated');
      const second = makeDocument('2', 'IDI', 'Rejected');
      const third = makeDocument('3', 'IDI', 'Pending');

      render('identity-documents', [
        first,
        second,
        third,
      ]);

      expect(view().settled()).toBe(2);
    });

    it('should update settled count after validating a document', () => {
      render('contact-details');

      expect(view().settled()).toBe(0);

      view().validate(residence);

      expect(view().settled()).toBe(1);
    });

    it('should update settled count after rejecting a document', () => {
      render('contact-details');

      expect(view().settled()).toBe(0);

      view().reject(residence);

      expect(view().settled()).toBe(1);
    });

    it('should not count the same document twice', () => {
      render('contact-details');

      view().validate(residence);
      view().reject(residence);

      expect(view().settled()).toBe(1);
    });
  });

  describe('Outputs', () => {
    it('should emit validateDocument when validating a document', () => {
      render();

      const emitSpy = jest.fn();

      component.validateDocument.subscribe(emitSpy);

      view().validate(residence);

      expect(emitSpy).toHaveBeenCalledTimes(1);
      expect(emitSpy).toHaveBeenCalledWith(residence);
    });

    it('should emit rejectDocument when rejecting a document', () => {
      render();

      const emitSpy = jest.fn();

      component.rejectDocument.subscribe(emitSpy);

      view().reject(residence);

      expect(emitSpy).toHaveBeenCalledTimes(1);
      expect(emitSpy).toHaveBeenCalledWith(residence);
    });

    it('should emit downloadDocument when downloading a document', () => {
      render();

      const emitSpy = jest.fn();

      component.downloadDocument.subscribe(emitSpy);

      view().download(residence);

      expect(emitSpy).toHaveBeenCalledTimes(1);
      expect(emitSpy).toHaveBeenCalledWith(residence);
    });

    it('should not change document status when downloading', () => {
      render();

      const initialStatus = view().statusOf(residence);

      view().download(residence);

      expect(view().statusOf(residence)).toBe(initialStatus);
    });

    it('should not mutate the original document when validating', () => {
      render();

      const initialStatus = residence.status;

      view().validate(residence);

      expect(residence.status).toBe(initialStatus);
      expect(view().statusOf(residence)).toBe('Validated');
    });
  });
});
