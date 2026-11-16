/**
 * Jest tests reconstructed from the screenshots of ChangeClientInformationComponent.
 *
 * IMPORTANT: Align service/token import paths with the EXACT imports in your component.
 * The screenshots do not include the component's import declarations or the full
 * implementation of the collapsed document actions.
 *
 * We test real component methods with a template-free TestBed and mock their I/O.
 * We do not replace Angular modules/components with `class {}` jest mocks.
 */
import { signal, type Signal, type WritableSignal } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of, Subject, throwError } from 'rxjs';
import { deserialize, serialize } from 'node:v8';
import { TaskType, UserService } from '@lia/commons';
import { NzModalService } from 'ng-zorro-antd/modal';

import { ChangeClientInformationComponent } from './change-client-information.component';

// IMPORTANT: Copy these import paths from change-client-information.component.ts.
// Their names are grounded in the screenshots, but their source locations may differ.
import { CASE_SUMMARY_SERVICE } from '../../../../core/providers/services/case-summary.service.service';
import { CasesService } from '../../../../core/providers/services/cases.service';
import { ApplicationUsersService } from '../../../../core/providers/services/application-users.service';
import { AssignmentService } from '../../../../core/providers/services/assignment.service';
import { ClientProfilingChangeService } from '../../../../core/providers/services/client-profiling-change.service';
import { RejectionModalService } from '../shared/rejection/rejection-modal.service';
import { COMPARISON_DATA_SERVICE } from '../../../../shared/components/data-comparison/comparison-data.service';

const CASE_ID = 'CASE-123';
const TASK_ID = 555;
const POLICY_NUMBER = 'POL-001';

const person = (overrides: Record<string, unknown> = {}) => ({
  thirdPartyId: 'PERSON-1',
  type: 'PHYSICAL_PERSON',
  firstName: 'Jane',
  lastname: 'Doe',
  birthDate: '1990-01-01',
  roleTypes: ['Holder'],
  ...overrides
});

const company = (overrides: Record<string, unknown> = {}) => ({
  thirdPartyId: 'COMPANY-1',
  type: 'MORAL_PERSON',
  name: 'Example S.A.',
  creationDate: '2001-01-01',
  roleTypes: ['Holder'],
  ...overrides
});

const makeCase = () => ({
  caseBusinessIdentifier: CASE_ID,
  ownerTeam: 'TEAM_A',
  ownerDetails: { displayName: 'Case Owner', username: 'owner.user' }
});

const makeTask = () => ({ userTaskIdentifier: TASK_ID, status: 'OPENED' });

const makeData = (changes: Record<string, unknown> = {}) => ({
  initialBusinessData: {
    policy: { policyNumber: POLICY_NUMBER, clients: [person(), company()] }
  },
  clientPortalBusinessData: {
    policy: { clients: [person({ firstName: 'Portal Jane' })] }
  },
  changeClientInformation: null,
  ...changes
});

const makeBusinessData = () => ({
  policy: { clients: [person({ firstName: 'Core Jane' }), company()] }
});

// Tests access protected/private TypeScript fields via an explicit test-only view.
// Do not expose them in production merely to test them.
type UnderTest = {
  readonly saving: Signal<boolean>;
  readonly refreshClientProfiling: Signal<number>;
  readonly getClientProfilingData: Signal<any>;
  readonly getBusinessData: Signal<any>;
  readonly policyNumber: Signal<string>;
  readonly snapshotHolders: Signal<any[]>;
  readonly clientIds: Signal<string[]>;
  readonly coreHolders: Signal<any[]>;
  readonly portalHolders: Signal<any[]>;
  readonly clipHolders: Signal<any[]>;
  readonly changeReasons: Signal<Record<string, unknown>>;
  readonly pendingChangeClientInformation: WritableSignal<any>;
  readonly holderSectionChanges: WritableSignal<ReadonlyMap<string, ReadonlyMap<string, ReadonlyMap<string, unknown>>>>;
  readonly holderSectionRemovedEntries: WritableSignal<ReadonlyMap<string, ReadonlyMap<string, readonly unknown[]>>>;
  readonly policySectionChanges: WritableSignal<ReadonlyMap<string, ReadonlyMap<string, unknown>>>;
  readonly documentsSaved: Signal<number>;
  readonly documentEdited: Signal<number>;
  readonly stateOf: (id: string) => unknown;
  readonly stateOfPolicy: (policy: any) => unknown;
  readonly onHolderState: (id: string, state: any) => void;
  readonly onPolicyState: (policy: any, state: any) => void;
  readonly holderLabel: (holder: any) => string;
  readonly documentsForHolder: (id: string) => any[];
  readonly onHolderChanges: (event: any) => void;
  readonly onPolicyChanges: (event: any) => void;
  readonly onChecksChanged: (values: any) => void;
  readonly onDocumentValidated: (document: any) => void;
  readonly onDocumentRemoved: (document: any) => void;
  readonly onDocumentRejected: (document: any) => void;
  readonly onUploadDocument: (request: any) => void;
  readonly onEditDocument: (event: any) => void;
};
const view = (component: ChangeClientInformationComponent): UnderTest =>
  component as unknown as UnderTest;

const resolution = (value: string, reason = 'CLIENT_REQUEST') => ({
  source: 'manual' as const,
  value,
  reason,
  comment: ''
});

const holderEvent = (
  sectionId: string,
  changes: ReadonlyMap<string, unknown>,
  removedEntries: readonly unknown[] = []
) => ({
  thirdPartyId: 'PERSON-1',
  sectionId,
  changes,
  removedEntries
});

const policyEvent = (sectionId: string, changes: ReadonlyMap<string, unknown>) => ({
  sectionId,
  changes
});

// Jest/jsdom may not expose Node's structuredClone even on a modern Node version.
// V8 serialization preserves Dates/undefined better than JSON cloning.
if (typeof globalThis.structuredClone !== 'function') {
  Object.defineProperty(globalThis, 'structuredClone', {
    configurable: true,
    writable: true,
    value: <T>(value: T): T => deserialize(serialize(value)) as T
  });
}

describe('ChangeClientInformationComponent', () => {
  let fixture: ComponentFixture<ChangeClientInformationComponent>;
  let component: ChangeClientInformationComponent;
  let user = signal<{ preferred_username: string } | null>({ preferred_username: 'john.doe' });

  let summaryMock: { getSummary: jest.Mock };
  let casesMock: {
    getCaseDataAfterClientProfiling: jest.Mock;
    getCaseBusinessDataUptoDate: jest.Mock;
    saveTask: jest.Mock;
    completeTask: jest.Mock;
  };
  let applicationUsersMock: { findALL: jest.Mock; findAllUserTeams: jest.Mock };
  let changeMock: {
    createEmpty: jest.Mock;
    cleanForSave: jest.Mock;
    applyHolderChanges: jest.Mock;
    applyPolicyChanges: jest.Mock;
  };
  let comparisonMock: { setReferences: jest.Mock };
  let assignmentMock: { openAssignmentModal: jest.Mock };
  let modalMock: { confirm: jest.Mock };

  let uploadDocumentMock: jest.Mock;
  let replacementDocumentMock: jest.Mock;
  let validateDocumentMock: jest.Mock;
  let refreshDocumentsMock: jest.Mock;

  async function render(options: {
    data?: ReturnType<typeof makeData>;
    businessData?: ReturnType<typeof makeBusinessData>;
    references?: Record<string, unknown>;
    documents?: unknown[];
    currentUser?: { preferred_username: string } | null;
  } = {}): Promise<void> {
    if (options.data) {
      casesMock.getCaseDataAfterClientProfiling.mockReturnValue(of(options.data));
    }
    if (options.businessData) {
      casesMock.getCaseBusinessDataUptoDate.mockReturnValue(of(options.businessData));
    }
    if (options.currentUser !== undefined) {
      user.set(options.currentUser);
    }

    fixture = TestBed.createComponent(ChangeClientInformationComponent);
    component = fixture.componentInstance;

    // All required signal inputs must be set before detectChanges().
    fixture.componentRef.setInput('currentCase', makeCase());
    fixture.componentRef.setInput('currentTask', makeTask());
    fixture.componentRef.setInput('clientProfilingReferences', options.references ?? {});
    fixture.componentRef.setInput('caseDocuments', options.documents ?? []);
    fixture.componentRef.setInput('positions', []);
    fixture.componentRef.setInput('policy', null);
    fixture.componentRef.setInput('getDocumentConfigType', () => of([]));
    fixture.componentRef.setInput('getDocumentMetadata', () => of({}));
    fixture.componentRef.setInput('validateDocument', validateDocumentMock);
    fixture.componentRef.setInput('rejectDocument', () => of(undefined));
    fixture.componentRef.setInput('downloadDocument', () => of(new Blob()));
    fixture.componentRef.setInput('removeDocument', () => of(undefined));
    fixture.componentRef.setInput('uploadDocument', uploadDocumentMock);
    fixture.componentRef.setInput('replacementDocument', replacementDocumentMock);
    fixture.componentRef.setInput('refreshDocuments', refreshDocumentsMock);

    fixture.detectChanges();
    await fixture.whenStable();
  }

  beforeEach(async () => {
    user = signal({ preferred_username: 'john.doe' });
    summaryMock = { getSummary: jest.fn().mockReturnValue(of({})) };
    casesMock = {
      getCaseDataAfterClientProfiling: jest.fn().mockReturnValue(of(makeData())),
      getCaseBusinessDataUptoDate: jest.fn().mockReturnValue(of(makeBusinessData())),
      saveTask: jest.fn().mockReturnValue(of({})),
      completeTask: jest.fn().mockReturnValue(of({}))
    };
    applicationUsersMock = {
      findALL: jest.fn().mockReturnValue(of([])),
      findAllUserTeams: jest.fn().mockReturnValue(of(['TEAM_A', 'TEAM_B']))
    };
    changeMock = {
      createEmpty: jest.fn((data: any) => ({
        policy: { policyNumber: data.initialBusinessData.policy.policyNumber, clients: [] }
      })),
      cleanForSave: jest.fn((data: any) => data),
      applyHolderChanges: jest.fn((data: any) => data),
      applyPolicyChanges: jest.fn((data: any) => data)
    };
    comparisonMock = { setReferences: jest.fn() };
    assignmentMock = {
      openAssignmentModal: jest.fn().mockReturnValue({ afterClose: of(null) })
    };
    modalMock = { confirm: jest.fn() };
    uploadDocumentMock = jest.fn().mockReturnValue(of({}));
    replacementDocumentMock = jest.fn().mockReturnValue(of({}));
    validateDocumentMock = jest.fn().mockReturnValue(of(undefined));
    refreshDocumentsMock = jest.fn();

    // Both root and component-level providers are overridden. The latter is
    // important if the @Component metadata provides its own service instances.
    const providers = [
      { provide: CASE_SUMMARY_SERVICE, useValue: summaryMock },
      { provide: COMPARISON_DATA_SERVICE, useValue: comparisonMock },
      { provide: ClientProfilingChangeService, useValue: changeMock },
      { provide: UserService, useValue: { user } },
      { provide: CasesService, useValue: casesMock },
      { provide: ApplicationUsersService, useValue: applicationUsersMock },
      { provide: AssignmentService, useValue: assignmentMock },
      { provide: RejectionModalService, useValue: { openRejectionModal: jest.fn() } },
      { provide: NzModalService, useValue: modalMock }
    ];

    await TestBed.configureTestingModule({
      imports: [ChangeClientInformationComponent],
      providers
    })
      .overrideComponent(ChangeClientInformationComponent, {
        set: { template: '', imports: [], providers }
      })
      .compileComponents();
  });

  afterEach(() => jest.restoreAllMocks());

  describe('initialization and computed data', () => {
    it('should create the component and inject dependencies', async () => {
      await render();
      expect(component).toBeTruthy();
    });

    it('should pass references to ComparisonDataService on initialization', async () => {
      const refs = { YESNO: [{ value: 'YES', label: 'Yes' }] };
      await render({ references: refs });
      expect(comparisonMock.setReferences).toHaveBeenCalledWith(refs);
    });

    it('should update references when the input changes', async () => {
      await render();
      comparisonMock.setReferences.mockClear();
      const updated = { COUNTRY: [{ value: 'LU', label: 'Luxembourg' }] };
      fixture.componentRef.setInput('clientProfilingReferences', updated);
      fixture.detectChanges();
      await fixture.whenStable();
      expect(comparisonMock.setReferences).toHaveBeenCalledWith(updated);
    });

    it('should load client profiling data and latest business data for the case', async () => {
      await render();
      expect(casesMock.getCaseDataAfterClientProfiling).toHaveBeenCalledWith(CASE_ID);
      expect(casesMock.getCaseBusinessDataUptoDate).toHaveBeenCalledWith(CASE_ID);
      expect(view(component).getClientProfilingData()).toBeTruthy();
    });

    it('should request the summary, users and current user teams', async () => {
      await render();
      // rxResource may load lazily on its first read.
      expect(view(component).policyNumber()).toBe(POLICY_NUMBER);
      expect(applicationUsersMock.findALL).toHaveBeenCalled();
      expect(applicationUsersMock.findAllUserTeams).toHaveBeenCalledWith('john.doe');
      expect(summaryMock.getSummary).toHaveBeenCalledWith(CASE_ID, expect.anything());
    });

    it('should compute the selected policy number, snapshots and client IDs', async () => {
      await render();
      expect(view(component).policyNumber()).toBe(POLICY_NUMBER);
      expect(view(component).snapshotHolders()).toEqual(expect.arrayContaining([
        expect.objectContaining({ thirdPartyId: 'PERSON-1' }),
        expect.objectContaining({ thirdPartyId: 'COMPANY-1' })
      ]));
      expect(view(component).clientIds()).toEqual(expect.arrayContaining(['PERSON-1', 'COMPANY-1']));
    });

    it('should return only matching holders from core and portal', async () => {
      await render();
      expect(view(component).coreHolders()).toEqual(expect.arrayContaining([
        expect.objectContaining({ thirdPartyId: 'PERSON-1', firstName: 'Core Jane' })
      ]));
      expect(view(component).portalHolders()).toEqual([
        expect.objectContaining({ thirdPartyId: 'PERSON-1', firstName: 'Portal Jane' })
      ]);
    });

    it('should read persisted CCI holders and change reasons', async () => {
      await render({ data: makeData({
        changeClientInformation: {
          policy: { policyNumber: POLICY_NUMBER, clients: [person({ firstName: 'Saved Jane' })] },
          changeReasons: { 'PERSON-1:firstname': { reason: 'CLIENT_REQUEST', comment: 'Edited' } }
        }
      }) });
      expect(view(component).clipHolders()).toEqual([
        expect.objectContaining({ thirdPartyId: 'PERSON-1', firstName: 'Saved Jane' })
      ]);
      expect(view(component).changeReasons()['PERSON-1:firstname']).toEqual({
        reason: 'CLIENT_REQUEST', comment: 'Edited'
      });
    });
  });

  describe('saving and completion', () => {
    it('should not save when no pending changes exist', async () => {
      await render();
      const onSucceed = jest.fn();
      component.save(CASE_ID, TASK_ID, onSucceed);
      expect(casesMock.saveTask).not.toHaveBeenCalled();
      expect(onSucceed).not.toHaveBeenCalled();
    });

    it('should clean, save, reset pending data and notify success', async () => {
      await render();
      const pending = { policy: { policyNumber: POLICY_NUMBER, clients: [] } };
      const cleaned = { policy: { ...pending.policy }, changeReasons: {} };
      view(component).pendingChangeClientInformation.set(pending);
      changeMock.cleanForSave.mockReturnValue(cleaned);
      const onSucceed = jest.fn();

      component.save(CASE_ID, TASK_ID, onSucceed);

      expect(changeMock.cleanForSave).toHaveBeenCalledWith(pending);
      expect(casesMock.saveTask).toHaveBeenCalledWith(
        CASE_ID, TASK_ID, TaskType.CHANGE_CLIENT_INFORMATION, cleaned
      );
      expect(view(component).pendingChangeClientInformation()).toBeNull();
      expect(view(component).saving()).toBe(false);
      expect(onSucceed).toHaveBeenCalledTimes(1);
    });

    it('should keep changes and reset saving on an API failure', async () => {
      await render();
      const pending = { policy: { policyNumber: POLICY_NUMBER, clients: [] } };
      view(component).pendingChangeClientInformation.set(pending);
      casesMock.saveTask.mockReturnValue(throwError(() => new Error('network failure')));
      const consoleSpy = jest.spyOn(console, 'error').mockImplementation(() => {});
      const onSucceed = jest.fn();

      component.save(CASE_ID, TASK_ID, onSucceed);

      expect(consoleSpy).toHaveBeenCalledWith(
        'Error saving client information', expect.any(Error)
      );
      expect(view(component).saving()).toBe(false);
      expect(view(component).pendingChangeClientInformation()).toEqual(pending);
      expect(onSucceed).not.toHaveBeenCalled();
    });

    it('should keep saving true while the HTTP request is pending', async () => {
      await render();
      const request$ = new Subject<unknown>();
      casesMock.saveTask.mockReturnValue(request$.asObservable());
      view(component).pendingChangeClientInformation.set({ policy: { clients: [] } });

      component.save(CASE_ID, TASK_ID, jest.fn());
      expect(view(component).saving()).toBe(true);

      request$.complete();
      expect(view(component).saving()).toBe(false);
    });

    it('should not open the assignment dialog without pending changes', async () => {
      await render();
      component.complete(CASE_ID, TASK_ID, jest.fn());
      expect(assignmentMock.openAssignmentModal).not.toHaveBeenCalled();
      expect(casesMock.completeTask).not.toHaveBeenCalled();
    });

    it('should not complete the task if assignment dialog is cancelled', async () => {
      await render();
      view(component).pendingChangeClientInformation.set({ policy: { clients: [] } });
      assignmentMock.openAssignmentModal.mockReturnValue({ afterClose: of(null) });
      const onSucceed = jest.fn();

      component.complete(CASE_ID, TASK_ID, onSucceed);

      expect(assignmentMock.openAssignmentModal).toHaveBeenCalled();
      expect(casesMock.completeTask).not.toHaveBeenCalled();
      expect(onSucceed).not.toHaveBeenCalled();
    });

    it('should complete with cleaned data and the selected sign-off assignee', async () => {
      await render();
      const cleaned = { policy: { policyNumber: POLICY_NUMBER, clients: [] } };
      view(component).pendingChangeClientInformation.set(cleaned);
      assignmentMock.openAssignmentModal.mockReturnValue({
        afterClose: of({ assignee: 'signoff.user', team: 'TEAM_A' })
      });
      const onSucceed = jest.fn();

      component.complete(CASE_ID, TASK_ID, onSucceed);

      expect(assignmentMock.openAssignmentModal).toHaveBeenCalledWith(
        expect.any(Array),
        'Sign Off assignment',
        expect.objectContaining({ value: 'TEAM_A' }),
        expect.objectContaining({ value: 'owner.user' })
      );
      expect(casesMock.completeTask).toHaveBeenCalledWith(
        CASE_ID,
        TASK_ID,
        TaskType.CHANGE_CLIENT_INFORMATION,
        {
          ...cleaned,
          signOffAssignment: { user: 'signoff.user', team: 'TEAM_A' }
        }
      );
      expect(onSucceed).toHaveBeenCalledTimes(1);
    });

    it('should propagate an API error without invoking the success callback', async () => {
      await render();
      view(component).pendingChangeClientInformation.set({ policy: { clients: [] } });
      assignmentMock.openAssignmentModal.mockReturnValue({
        afterClose: of({ assignee: 'signoff.user', team: 'TEAM_A' })
      });
      casesMock.completeTask.mockReturnValue(throwError(() => new Error('complete failed')));
      const consoleSpy = jest.spyOn(console, 'error').mockImplementation(() => {});
      const onSucceed = jest.fn();

      component.complete(CASE_ID, TASK_ID, onSucceed);

      expect(consoleSpy).toHaveBeenCalled();
      expect(onSucceed).not.toHaveBeenCalled();
    });

    it('should refresh client profiling by incrementing its refresh counter', async () => {
      await render();
      const before = view(component).refreshClientProfiling();
      component.refresh();
      expect(view(component).refreshClientProfiling()).toBe(before + 1);
    });

    it('should document the current reject() implementation', async () => {
      await render();
      expect(() => component.reject(CASE_ID, TASK_ID, jest.fn()))
        .toThrow('Method not implemented.');
    });
  });

  describe('holder, policy and checklist changes', () => {
    it('should do nothing when holder data has not loaded', async () => {
      await render({ data: makeData() });
      // Simulate an unavailable API result without modifying production internals.
      casesMock.getCaseDataAfterClientProfiling.mockReturnValue(of(null));
      component.refresh();
      fixture.detectChanges();
      await fixture.whenStable();
      changeMock.applyHolderChanges.mockClear();

      view(component).onHolderChanges(holderEvent('identity', new Map([
        ['firstname', resolution('Jane')]
      ])));
      expect(changeMock.applyHolderChanges).not.toHaveBeenCalled();
    });

    it('should create a pending CCI and apply changed holder fields', async () => {
      await render();
      const modified = resolution('Janet');
      view(component).onHolderChanges(holderEvent('person', new Map([
        ['firstname', modified]
      ])));

      expect(changeMock.createEmpty).toHaveBeenCalled();
      expect(changeMock.applyHolderChanges).toHaveBeenCalledTimes(1);
      const [input, source, changes, removals] = changeMock.applyHolderChanges.mock.calls[0];
      expect(input.policy).toBeDefined();
      expect(source).toEqual(expect.objectContaining({ thirdPartyId: 'PERSON-1' }));
      expect(changes.get('firstname')).toEqual(modified);
      expect(removals).toEqual([]);
      expect(view(component).pendingChangeClientInformation()).toBeTruthy();
    });

    it('should preserve changes from other sections of the same holder', async () => {
      await render();
      view(component).onHolderChanges(holderEvent('name-section', new Map([
        ['firstname', resolution('Janet')]
      ])));
      view(component).onHolderChanges(holderEvent('contact-section', new Map([
        ['lastname', resolution('Smith')]
      ])));

      const changes = changeMock.applyHolderChanges.mock.lastCall?.[2] as ReadonlyMap<string, unknown>;
      expect(changes.get('firstname')).toEqual(resolution('Janet'));
      expect(changes.get('lastname')).toEqual(resolution('Smith'));
    });

    it('should pass removed entries to the holder change service', async () => {
      await render();
      const removed = { id: 'emails:old@example.com', value: 'old@example.com' };
      view(component).onHolderChanges(holderEvent(
        'contacts', new Map(), [removed]
      ));
      expect(changeMock.applyHolderChanges.mock.lastCall?.[3])
        .toEqual([removed]);
    });

    it('should not apply holder changes when the initial snapshot holder is absent', async () => {
      await render();
      const warning = jest.spyOn(console, 'warn').mockImplementation(() => {});
      view(component).onHolderChanges({
        thirdPartyId: 'UNKNOWN', sectionId: 'personal',
        changes: new Map([['firstname', resolution('X')]]), removedEntries: []
      });
      expect(warning).toHaveBeenCalledWith(
        'Initialization snapshot holder not found:', 'UNKNOWN'
      );
      expect(changeMock.applyHolderChanges).not.toHaveBeenCalled();
    });

    it('should apply policy changes by section', async () => {
      await render();
      view(component).onPolicyChanges(policyEvent('sending-address', new Map([
        ['city', resolution('Luxembourg')]
      ])));
      expect(changeMock.applyPolicyChanges).toHaveBeenCalledTimes(1);
      const [, policyNumber, changes] = changeMock.applyPolicyChanges.mock.calls[0];
      expect(policyNumber).toBe(POLICY_NUMBER);
      expect(changes.get('city')).toEqual(resolution('Luxembourg'));
    });

    it('should merge changes from multiple policy sections', async () => {
      await render();
      view(component).onPolicyChanges(policyEvent('address', new Map([
        ['city', resolution('Esch')]
      ])));
      view(component).onPolicyChanges(policyEvent('consent', new Map([
        ['consent-to-use-digital-platform', resolution('ACCEPTED')]
      ])));
      const changes = changeMock.applyPolicyChanges.mock.lastCall?.[2] as ReadonlyMap<string, unknown>;
      expect(changes.size).toBe(2);
      expect(changes.has('city')).toBe(true);
      expect(changes.has('consent-to-use-digital-platform')).toBe(true);
    });

    it('should merge task controls with signature information', async () => {
      await render();
      const values = {
        newIndiciaDetected: true,
        statusInvestigationInitiated: false,
        proofOfAddressReceived: true,
        taxCountryChecked: true,
        dataValid: false,
        signatureInfo: {
          requestSignedByClient: true,
          clientSignatureType: 'DIGITAL',
          signatureInline: false,
          allThirdPartiesSigned: true,
          date: '2026-11-16',
          place: 'Luxembourg',
          provider: 'Provider',
          clientIdentificationValid: true,
          documentsLinkedToCase: true,
          mfa: false,
          authorizedProvider: true
        }
      };
      view(component).onChecksChanged(values);
      expect(view(component).pendingChangeClientInformation().taskControls)
        .toEqual(expect.objectContaining({
          newIndiciaDetected: true,
          proofOfAddressReceived: true,
          signatureInfo: expect.objectContaining({ clientSignatureType: 'DIGITAL' })
        }));
    });
  });

  describe('display helpers and documents', () => {
    it('should show the correct labels for physical and moral persons', async () => {
      await render();
      expect(view(component).holderLabel(person())).toBe('Doe Jane');
      expect(view(component).holderLabel(company())).toBe('Example S.A.');
      expect(view(component).holderLabel({ thirdPartyId: 'UNKNOWN' })).toBe('UNKNOWN');
    });

    it('should filter documents by the holder thirdPartyId', async () => {
      const documents = [
        { documentId: 'DOC-1', metadata: { thirdPartyId: 'PERSON-1' } },
        { documentId: 'DOC-2', metadata: { thirdPartyId: 'COMPANY-1' } }
      ];
      await render({ documents });
      expect(view(component).documentsForHolder('PERSON-1')).toEqual([documents[0]]);
    });

    it('should store review state separately for a holder and policy', async () => {
      await render();
      const policy = { policyNumber: POLICY_NUMBER };
      const holderReview = { status: 'accepted' };
      const policyReview = { status: 'pending' };
      view(component).onHolderState('PERSON-1', holderReview);
      view(component).onPolicyState(policy, policyReview);
      expect(view(component).stateOf('PERSON-1')).toBe(holderReview);
      expect(view(component).stateOfPolicy(policy)).toBe(policyReview);
      expect(view(component).stateOf('OTHER')).toBeNull();
    });

    it('should show confirmation before validating a document', async () => {
      await render();
      const document = { documentId: 'DOC-1' };
      view(component).onDocumentValidated(document);
      expect(modalMock.confirm).toHaveBeenCalledWith(expect.objectContaining({
        nzTitle: 'Confirmation',
        nzOnOk: expect.any(Function)
      }));
      expect(validateDocumentMock).not.toHaveBeenCalled();
    });

    it('should validate a document and refresh only after confirming', async () => {
      await render();
      view(component).onDocumentValidated({ documentId: 'DOC-1' });
      const config = modalMock.confirm.mock.calls[0][0];
      config.nzOnOk();
      expect(validateDocumentMock).toHaveBeenCalledWith('DOC-1');
      expect(refreshDocumentsMock).toHaveBeenCalledTimes(1);
    });

    it('should upload a document and update the refresh counter on success', async () => {
      await render();
      const request = { fileName: 'proof.pdf' };
      view(component).onUploadDocument(request);
      expect(uploadDocumentMock).toHaveBeenCalledWith(request);
      expect(view(component).documentsSaved()).toBe(1);
      expect(refreshDocumentsMock).toHaveBeenCalledTimes(1);
    });

    it('should edit a document and update the edit counter on success', async () => {
      await render();
      const edit = { documentId: 'DOC-1', request: { description: 'Updated' } };
      view(component).onEditDocument(edit);
      expect(replacementDocumentMock).toHaveBeenCalledWith('DOC-1', edit.request);
      expect(view(component).documentEdited()).toBe(1);
      expect(refreshDocumentsMock).toHaveBeenCalledTimes(1);
    });

    it('should not increment counters or refresh documents if upload fails', async () => {
      await render();
      const error = new Error('Upload failed');
      uploadDocumentMock.mockReturnValue(throwError(() => error));
      const log = jest.spyOn(console, 'error').mockImplementation(() => {});
      view(component).onUploadDocument({ fileName: 'proof.pdf' });
      expect(log).toHaveBeenCalledWith('Error uploading document', error);
      expect(view(component).documentsSaved()).toBe(0);
      expect(refreshDocumentsMock).not.toHaveBeenCalled();
    });

    it('should ask for confirmation before removing or rejecting a document', async () => {
      await render();
      const doc = { documentId: 'DOC-1' };
      view(component).onDocumentRemoved(doc);
      view(component).onDocumentRejected(doc);
      expect(modalMock.confirm).toHaveBeenCalledTimes(2);
      // The action callbacks are folded in the screenshots, so their internal
      // HTTP operations are deliberately NOT asserted here.
    });
  });
});
