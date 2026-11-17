/**
 * High-value Jest tests for ChangeClientInformationSignOffComponent.
 * Reconstructed from the screenshots shared on 2026-11-17.
 *
 * IMPORTANT BEFORE RUNNING:
 *   1) Match the imports for services/tokens to your component's exact paths.
 *   2) Keep this test alongside change-client-information-sign-off.component.ts.
 *   3) The component has save() = throw new Error('Method not implemented.');
 *      These tests explicitly document that current behavior; they do not invent a save flow.
 *   4) The real application/repository isn't available here; these tests are not claimed
 *      to have been executed. Run the project's Jest suite after integrating.
 */
import { signal, type Signal } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of, throwError } from 'rxjs';
import { NzModalService } from 'ng-zorro-antd/modal';
import { TaskType, UserService } from '@lia/commons';

import { ChangeClientInformationSignOffComponent } from './change-client-information-sign-off.component';

// Adapt the following import paths to the EXACT paths in your component.
import { CASE_SUMMARY_SERVICE } from '../../../../core/providers/services/case-summary.service.service';
import { CasesService } from '../../../../core/providers/services/cases.service';
import { ApplicationUsersService } from '../../../../core/providers/services/application-users.service';
import { AssignmentService } from '../../../../core/providers/services/assignment.service';
import { RejectionModalService } from '../shared/rejection/rejection-modal.service';
import { CompletionConfirmationModalService } from '../shared/confirmation/completion-confirmation-modal.service';
import { COMPARISON_DATA_SERVICE } from '../../../../shared/components/data-comparison/comparison-data.service';

const CASE_ID = 'CASE-123';
const TASK_ID = 555;
const POLICY_NUMBER = 'POL-001';

const physical = (id: string, overrides: Record<string, unknown> = {}) => ({
  thirdPartyId: id,
  firstName: 'Anna',
  lastname: 'Smith',
  birthDate: '1980-01-01',
  roleTypes: ['Holder'],
  ...overrides,
});
const moral = (id: string, overrides: Record<string, unknown> = {}) => ({
  thirdPartyId: id,
  name: 'Acme SA',
  creationDate: '2000-01-01',
  roleTypes: ['Holder'],
  ...overrides,
});

const currentCase = () => ({
  caseBusinessIdentifier: CASE_ID,
  ownerTeam: 'TEAM-A',
  ownerDetails: { displayName: 'Jane Owner', username: 'jane.owner' },
});

const profiling = (overrides: Record<string, unknown> = {}) => ({
  initialBusinessData: {
    policy: {
      policyNumber: POLICY_NUMBER,
      clients: [physical('P-1'), moral('M-1'), physical('CP-1', { roleTypes: ['EBO'] })],
    },
  },
  clientPortalBusinessData: { policy: { clients: [physical('P-1', { firstName: 'Portal Anna' })] } },
  changeClientInformation: { policy: { clients: [] }, changeReasons: {} },
  ...overrides,
});

const business = (overrides: Record<string, unknown> = {}) => ({
  policy: {
    clients: [physical('P-1', { firstName: 'Core Anna' }), moral('M-1')],
    holder: { sendingAddress: { address: { streetName: 'Main', postCode: '1000' } } },
    digitalConsent: { useElectronicSignatureConsentStatus: 'YES' },
  },
  ...overrides,
});

// Access internals *only in the test*, without changing production visibility.
type ComponentView = {
  getClientProfilingData: Signal<any>;
  getBusinessData: Signal<any>;
  policyNumber: Signal<string>;
  changeReasons: Signal<Record<string, unknown>>;
  users: Signal<unknown[]>;
  teams: Signal<unknown[]>;
  snapshotHolders: Signal<any[]>;
  clientIds: Signal<string[]>;
  portalHolders: Signal<any[]>;
  coreHolders: Signal<any[]>;
  clipHolders: Signal<any[]>;
  controllingPersons: Signal<any[]>;
  documentSaved: Signal<number>;
  documentEdited: Signal<number>;
  documentsForHolder: (id: string) => any[];
  holderLabel: (holder: any) => string;
  portalHolderOf: (holder: any) => any;
  coreHolderOf: (holder: any) => any;
  clipHolderOf: (holder: any) => any;
  stateOf: (id: string) => string | null;
  stateOfPolicy: (policy: any) => string | null;
  onHolderState: (id: string, state: string) => void;
  onPolicyState: (policy: any, state: string) => void;
  isControllingPerson: (holder: any) => boolean;
  hasAddressChanged: () => boolean;
  normalizeAddress: (address?: any) => string;
  normalizeDigitalConsent: (consent?: any) => string;
  onDocumentValidated: (document: any) => void;
  onDocumentRejected: (document: any) => void;
  onDocumentRemoved: (document: any) => void;
  onDocumentDownloaded: (document: any) => void;
  onUploadDocument: (request: any) => void;
  onEditDocument: (event: { documentId: string; request: any }) => void;
};
const view = (component: ChangeClientInformationSignOffComponent) =>
  component as unknown as ComponentView;

const documentExample = (overrides: Record<string, unknown> = {}) => ({
  documentId: 'D-1',
  name: 'passport.pdf',
  metadata: { thirdPartyId: 'P-1' },
  ...overrides,
});

describe('ChangeClientInformationSignOffComponent', () => {
  let fixture: ComponentFixture<ChangeClientInformationSignOffComponent>;
  let component: ChangeClientInformationSignOffComponent;
  let summaryMock: { getSummary: jest.Mock };
  let casesMock: {
    getCaseDataAfterClientProfiling: jest.Mock;
    getCaseBusinessDataUptoDate: jest.Mock;
    completeTask: jest.Mock;
    rejectTask: jest.Mock;
  };
  let usersMock: { findALL: jest.Mock; findAllUserTeams: jest.Mock };
  let assignmentMock: { openAssignmentModal: jest.Mock };
  let rejectionMock: { openRejectionModal: jest.Mock };
  let confirmationMock: { openConfirmation: jest.Mock };
  let comparisonMock: { setReferences: jest.Mock };
  let modalMock: { confirm: jest.Mock };
  let currentUser: ReturnType<typeof signal<{ preferred_username: string } | null>>;

  let validateDocument: jest.Mock;
  let rejectDocument: jest.Mock;
  let removeDocument: jest.Mock;
  let uploadDocument: jest.Mock;
  let replacementDocument: jest.Mock;
  let downloadDocument: jest.Mock;
  let refreshDocuments: jest.Mock;

  // For all tests using the real hasAddressChanged(), pass both datasets HERE,
  // *before* creating the component. Signals read these initial API values.
  async function render(options: {
    data?: ReturnType<typeof profiling>;
    businessData?: Record<string, unknown>;
    documents?: unknown[];
    references?: Record<string, unknown>;
    taskBpmnId?: string;
    currentUserValue?: { preferred_username: string } | null;
  } = {}): Promise<void> {
    casesMock.getCaseDataAfterClientProfiling.mockReturnValue(of(options.data ?? profiling()));
    casesMock.getCaseBusinessDataUptoDate.mockReturnValue(of(options.businessData ?? business()));
    if (options.currentUserValue !== undefined) currentUser.set(options.currentUserValue);

    fixture = TestBed.createComponent(ChangeClientInformationSignOffComponent);
    component = fixture.componentInstance;

    fixture.componentRef.setInput('currentTask', {
      userTaskIdentifier: TASK_ID,
      userTaskBpmnIdentifier: options.taskBpmnId ?? TaskType.CHANGE_CLIENT_INFORMATION_SIGNOFF,
    });
    fixture.componentRef.setInput('currentCase', currentCase());
    fixture.componentRef.setInput('clientProfilingReferences', options.references ?? {});
    fixture.componentRef.setInput('caseDocuments', options.documents ?? []);
    fixture.componentRef.setInput('positions', []);
    fixture.componentRef.setInput('policy', null);
    fixture.componentRef.setInput('getDocumentConfigType', () => of([]));
    fixture.componentRef.setInput('getDocumentMetadata', () => of({}));
    fixture.componentRef.setInput('validateDocument', validateDocument);
    fixture.componentRef.setInput('rejectDocument', rejectDocument);
    fixture.componentRef.setInput('removeDocument', removeDocument);
    fixture.componentRef.setInput('uploadDocument', uploadDocument);
    fixture.componentRef.setInput('replacementDocument', replacementDocument);
    fixture.componentRef.setInput('downloadDocument', downloadDocument);
    fixture.componentRef.setInput('refreshDocuments', refreshDocuments);

    fixture.detectChanges();
    await fixture.whenStable();
  }

  // Modal confirmation is not the same as a service call. Trigger nzOnOk manually.
  function confirmDocumentAction(): void {
    const config = modalMock.confirm.mock.calls[modalMock.confirm.mock.calls.length - 1][0];
    expect(typeof config.nzOnOk).toBe('function');
    config.nzOnOk();
  }

  beforeEach(async () => {
    currentUser = signal({ preferred_username: 'tester' });
    summaryMock = { getSummary: jest.fn().mockReturnValue(of({})) };
    casesMock = {
      getCaseDataAfterClientProfiling: jest.fn().mockReturnValue(of(profiling())),
      getCaseBusinessDataUptoDate: jest.fn().mockReturnValue(of(business())),
      completeTask: jest.fn().mockReturnValue(of({})),
      rejectTask: jest.fn().mockReturnValue(of({})),
    };
    usersMock = {
      findALL: jest.fn().mockReturnValue(of([{ username: 'tester' }])),
      findAllUserTeams: jest.fn().mockReturnValue(of(['TEAM-A', 'TEAM-B'])),
    };
    assignmentMock = { openAssignmentModal: jest.fn().mockReturnValue({ afterClose: of(null) }) };
    rejectionMock = { openRejectionModal: jest.fn().mockReturnValue({ afterClose: of(null) }) };
    confirmationMock = { openConfirmation: jest.fn().mockReturnValue({ afterClose: of(false) }) };
    comparisonMock = { setReferences: jest.fn() };
    modalMock = { confirm: jest.fn() };
    validateDocument = jest.fn().mockReturnValue(of(undefined));
    rejectDocument = jest.fn().mockReturnValue(of(undefined));
    removeDocument = jest.fn().mockReturnValue(of(undefined));
    uploadDocument = jest.fn().mockReturnValue(of({ documentId: 'NEW' }));
    replacementDocument = jest.fn().mockReturnValue(of({ documentId: 'D-1' }));
    downloadDocument = jest.fn().mockReturnValue(of(new Blob(['document'])));
    refreshDocuments = jest.fn();

    const providers = [
      { provide: CASE_SUMMARY_SERVICE, useValue: summaryMock },
      { provide: COMPARISON_DATA_SERVICE, useValue: comparisonMock },
      { provide: CasesService, useValue: casesMock },
      { provide: ApplicationUsersService, useValue: usersMock },
      { provide: AssignmentService, useValue: assignmentMock },
      { provide: RejectionModalService, useValue: rejectionMock },
      { provide: CompletionConfirmationModalService, useValue: confirmationMock },
      { provide: UserService, useValue: { user: currentUser } },
      { provide: NzModalService, useValue: modalMock },
    ];

    await TestBed.configureTestingModule({
      imports: [ChangeClientInformationSignOffComponent],
      providers,
    })
      .overrideComponent(ChangeClientInformationSignOffComponent, {
        set: { template: '', imports: [], providers },
      })
      .compileComponents();
  });

  const originalCreateObjectURL = Object.getOwnPropertyDescriptor(URL, 'createObjectURL');
  const originalRevokeObjectURL = Object.getOwnPropertyDescriptor(URL, 'revokeObjectURL');
  afterEach(() => {
    jest.restoreAllMocks();
    // Do not leak URL stubs into other Jest files.
    if (originalCreateObjectURL) Object.defineProperty(URL, 'createObjectURL', originalCreateObjectURL);
    else Reflect.deleteProperty(URL, 'createObjectURL');
    if (originalRevokeObjectURL) Object.defineProperty(URL, 'revokeObjectURL', originalRevokeObjectURL);
    else Reflect.deleteProperty(URL, 'revokeObjectURL');
  });

  describe('initialization, references and computed data', () => {
    it('should create and fetch the current case data', async () => {
      await render();
      expect(component).toBeTruthy();
      expect(casesMock.getCaseDataAfterClientProfiling).toHaveBeenCalledWith(CASE_ID);
      expect(casesMock.getCaseBusinessDataUptoDate).toHaveBeenCalledWith(CASE_ID);
      expect(view(component).getClientProfilingData()).toBeTruthy();
    });

    it('should forward references to ComparisonDataService', async () => {
      const refs = { COUNTRY: [{ key: 'Belgium', value: 'BE' }] };
      await render({ references: refs });
      expect(comparisonMock.setReferences).toHaveBeenCalledWith(refs);
    });

    it('should forward changed references again', async () => {
      await render();
      comparisonMock.setReferences.mockClear();
      const refs = { STATUS_MARITAL: [{ key: 'Single', value: 'S' }] };
      fixture.componentRef.setInput('clientProfilingReferences', refs);
      fixture.detectChanges();
      await fixture.whenStable();
      expect(comparisonMock.setReferences).toHaveBeenCalledWith(refs);
    });

    it('should load application users and map user teams', async () => {
      await render();
      expect(usersMock.findALL).toHaveBeenCalled();
      expect(usersMock.findAllUserTeams).toHaveBeenCalledWith('tester');
      expect(view(component).teams()).toEqual([
        { displayName: 'TEAM-A', value: 'TEAM-A' },
        { displayName: 'TEAM-B', value: 'TEAM-B' },
      ]);
    });

    it('should not request teams when the current user is null', async () => {
      await render({ currentUserValue: null });
      expect(usersMock.findAllUserTeams).not.toHaveBeenCalled();
      expect(view(component).teams()).toEqual([]);
    });

    it('should compute policy number and default change reasons', async () => {
      await render();
      expect(view(component).policyNumber()).toBe(POLICY_NUMBER);
      expect(view(component).changeReasons()).toEqual({});
    });

    it('should use persisted change reasons', async () => {
      const reasons = { 'P-1:lastname': { reason: 'CLIENT_REQUEST' } };
      await render({ data: profiling({ changeClientInformation: { policy: { clients: [] }, changeReasons: reasons } }) });
      expect(view(component).changeReasons()).toEqual(reasons);
    });

    it('should keep holders and controlling persons separate', async () => {
      await render();
      expect(view(component).snapshotHolders().map(h => h.thirdPartyId)).toEqual(['P-1', 'M-1']);
      expect(view(component).controllingPersons().map(h => h.thirdPartyId)).toEqual(['CP-1']);
      expect(view(component).clientIds()).toEqual(['P-1', 'M-1', 'CP-1']);
    });

    it('should match holders from core, portal and saved CCI by thirdPartyId', async () => {
      await render({
        data: profiling({
          changeClientInformation: {
            policy: { clients: [physical('P-1', { firstName: 'Saved' }), physical('OTHER')] },
            changeReasons: {},
          },
        }),
      });
      expect(view(component).portalHolders().map(h => h.thirdPartyId)).toEqual(['P-1']);
      expect(view(component).coreHolders().map(h => h.thirdPartyId)).toEqual(['P-1', 'M-1']);
      expect(view(component).clipHolders().map(h => h.thirdPartyId)).toEqual(['P-1']);
      expect(view(component).clipHolderOf({ thirdPartyId: 'P-1' })).toEqual(expect.objectContaining({ firstName: 'Saved' }));
      expect(view(component).clipHolderOf({ thirdPartyId: 'NOT-FOUND' })).toBeNull();
    });

    it('should find a matching portal and core holder and handle missing ones', async () => {
      await render();
      expect(view(component).portalHolderOf({ thirdPartyId: 'P-1' })).toEqual(expect.objectContaining({ firstName: 'Portal Anna' }));
      expect(view(component).coreHolderOf({ thirdPartyId: 'P-1' })).toEqual(expect.objectContaining({ firstName: 'Core Anna' }));
      expect(view(component).portalHolderOf({ thirdPartyId: 'UNKNOWN' })).toBeNull();
      expect(view(component).coreHolderOf({ thirdPartyId: 'UNKNOWN' })).toBeNull();
    });

    it('should identify controlling persons only when physical and EBO/TRUSTEE', async () => {
      await render();
      expect(view(component).isControllingPerson(physical('A', { roleTypes: ['EBO'] }))).toBe(true);
      expect(view(component).isControllingPerson(physical('B', { roleTypes: ['TRUSTEE'] }))).toBe(true);
      expect(view(component).isControllingPerson(physical('C', { roleTypes: ['Holder'] }))).toBe(false);
      expect(view(component).isControllingPerson(moral('D', { roleTypes: ['EBO'] }))).toBe(false);
    });

    it('should build physical, moral and fallback labels', async () => {
      await render();
      expect(view(component).holderLabel(physical('A', { lastname: 'Doe', firstName: 'Jane' }))).toBe('Doe Jane');
      expect(view(component).holderLabel(moral('B', { name: 'Company B' }))).toBe('Company B');
      expect(view(component).holderLabel({ thirdPartyId: 'OTHER' })).toBe('OTHER');
    });

    it('should filter documents by third party metadata', async () => {
      await render({ documents: [
        documentExample(),
        documentExample({ documentId: 'D-2', metadata: { thirdPartyId: 'M-1' } }),
        documentExample({ documentId: 'D-3', metadata: {} }),
      ] });
      expect(view(component).documentsForHolder('P-1').map(d => d.documentId)).toEqual(['D-1']);
      expect(view(component).documentsForHolder('M-1').map(d => d.documentId)).toEqual(['D-2']);
      expect(view(component).documentsForHolder('UNKNOWN')).toEqual([]);
    });

    it('should update holder and policy review states', async () => {
      await render();
      const policy = { policyNumber: POLICY_NUMBER };
      expect(view(component).stateOf('P-1')).toBeNull();
      expect(view(component).stateOfPolicy(policy)).toBeNull();
      view(component).onHolderState('P-1', 'pending');
      view(component).onPolicyState(policy, 'clean');
      expect(view(component).stateOf('P-1')).toBe('pending');
      expect(view(component).stateOfPolicy(policy)).toBe('clean');
    });
  });

  describe('save, complete and reject', () => {
    it('should explicitly document that save() is not yet implemented', async () => {
      await render();
      const success = jest.fn();
      expect(() => component.save(CASE_ID, TASK_ID, success)).toThrow('Method not implemented.');
      expect(success).not.toHaveBeenCalled();
    });

    it('should not complete when BPMN task type is different', async () => {
      await render({ taskBpmnId: 'ANOTHER_TASK' });
      component.complete(CASE_ID, TASK_ID, jest.fn());
      expect(confirmationMock.openConfirmation).not.toHaveBeenCalled();
      expect(casesMock.completeTask).not.toHaveBeenCalled();
    });

    it.each([false, null, undefined])('should cancel completion when confirmation is %s', async confirmed => {
      await render();
      confirmationMock.openConfirmation.mockReturnValue({ afterClose: of(confirmed) });
      const success = jest.fn();
      component.complete(CASE_ID, TASK_ID, success);
      expect(casesMock.completeTask).not.toHaveBeenCalled();
      expect(assignmentMock.openAssignmentModal).not.toHaveBeenCalled();
      expect(success).not.toHaveBeenCalled();
    });

    it('should complete without assignment when no manual change is required', async () => {
      await render();
      jest.spyOn(view(component), 'hasAddressChanged').mockReturnValue(false);
      confirmationMock.openConfirmation.mockReturnValue({ afterClose: of(true) });
      const success = jest.fn();

      component.complete(CASE_ID, TASK_ID, success);

      expect(assignmentMock.openAssignmentModal).not.toHaveBeenCalled();
      expect(casesMock.completeTask).toHaveBeenCalledWith(
        CASE_ID, TASK_ID, TaskType.CHANGE_CLIENT_INFORMATION_SIGNOFF,
        { manualChangeRequired: false },
      );
      expect(success).toHaveBeenCalledTimes(1);
    });

    it('should open assignment and complete with selected owner when a manual change is required', async () => {
      await render();
      jest.spyOn(view(component), 'hasAddressChanged').mockReturnValue(true);
      confirmationMock.openConfirmation.mockReturnValue({ afterClose: of(true) });
      const owner = { assignee: 'agent.1', team: 'TEAM-MANUAL' };
      assignmentMock.openAssignmentModal.mockReturnValue({ afterClose: of(owner) });
      const success = jest.fn();

      component.complete(CASE_ID, TASK_ID, success);

      expect(assignmentMock.openAssignmentModal).toHaveBeenCalledWith(
        expect.any(Array),
        'Manual change assignment',
        expect.objectContaining({ value: 'TEAM-A' }),
        expect.objectContaining({ value: 'jane.owner' }),
      );
      expect(casesMock.completeTask).toHaveBeenCalledWith(
        CASE_ID, TASK_ID, TaskType.CHANGE_CLIENT_INFORMATION_SIGNOFF,
        {
          signOffAssignment: { user: 'agent.1', team: 'TEAM-MANUAL' },
          manualChangeRequired: true,
        },
      );
      expect(success).toHaveBeenCalledTimes(1);
    });

    it.each([null, false, undefined])('should not complete if assignment is %s', async owner => {
      await render();
      jest.spyOn(view(component), 'hasAddressChanged').mockReturnValue(true);
      confirmationMock.openConfirmation.mockReturnValue({ afterClose: of(true) });
      assignmentMock.openAssignmentModal.mockReturnValue({ afterClose: of(owner) });
      const success = jest.fn();

      component.complete(CASE_ID, TASK_ID, success);

      expect(casesMock.completeTask).not.toHaveBeenCalled();
      expect(success).not.toHaveBeenCalled();
    });

    it('should reject with the chosen reason and call success', async () => {
      await render();
      rejectionMock.openRejectionModal.mockReturnValue({ afterClose: of('Missing signature') });
      const success = jest.fn();

      component.reject(CASE_ID, TASK_ID, success);

      expect(casesMock.rejectTask).toHaveBeenCalledWith(
        CASE_ID, TASK_ID, TaskType.CHANGE_CLIENT_INFORMATION_SIGNOFF,
        'Missing signature', { rejectionReason: 'Missing signature' },
      );
      expect(success).toHaveBeenCalledTimes(1);
    });

    it.each(['', null, undefined])('should not reject when reason is %s', async reason => {
      await render();
      rejectionMock.openRejectionModal.mockReturnValue({ afterClose: of(reason) });
      const success = jest.fn();
      component.reject(CASE_ID, TASK_ID, success);
      expect(casesMock.rejectTask).not.toHaveBeenCalled();
      expect(success).not.toHaveBeenCalled();
    });
  });

  describe('detecting manual address or digital-consent changes', () => {
    it('should normalize absent objects to empty strings', async () => {
      await render();
      expect(view(component).normalizeAddress()).toBe('');
      expect(view(component).normalizeDigitalConsent()).toBe('');
    });

    it('should sort properties and ignore null, undefined and empty strings', async () => {
      await render();
      expect(view(component).normalizeAddress({ b: '', x: null, y: undefined, a: 'value' }))
        .toBe(view(component).normalizeAddress({ a: 'value' }));
      expect(view(component).normalizeDigitalConsent({ b: 'YES', a: 'NO', z: null }))
        .toBe(view(component).normalizeDigitalConsent({ a: 'NO', b: 'YES' }));
    });

    it('should keep false and zero as meaningful values', async () => {
      await render();
      expect(view(component).normalizeAddress({ selected: false, number: 0 }))
        .not.toBe(view(component).normalizeAddress({}));
      expect(view(component).normalizeDigitalConsent({ allowed: false }))
        .not.toBe(view(component).normalizeDigitalConsent({}));
    });

    it('should detect no changes when normalized addresses and consents match', async () => {
      const old = business();
      const newData = profiling({
        changeClientInformation: {
          policy: {
            clients: [],
            holder: { sendingAddress: { address: { postCode: '1000', streetName: 'Main', area: '' } } },
            digitalConsent: { useElectronicSignatureConsentStatus: 'YES' },
          },
        },
      });
      await render({ businessData: old, data: newData });
      expect(view(component).hasAddressChanged()).toBe(false);
    });

    it('should detect a changed sending address', async () => {
      await render({ data: profiling({
        changeClientInformation: {
          policy: { clients: [], holder: { sendingAddress: { address: { streetName: 'Other Street', postCode: '1000' } } } },
        },
      }) });
      expect(view(component).hasAddressChanged()).toBe(true);
    });

    it('should detect changed digital consent even when the address matches', async () => {
      await render({ data: profiling({
        changeClientInformation: {
          policy: {
            clients: [],
            holder: { sendingAddress: { address: { postCode: '1000', streetName: 'Main' } } },
            digitalConsent: { useElectronicSignatureConsentStatus: 'NO' },
          },
        },
      }) });
      expect(view(component).hasAddressChanged()).toBe(true);
    });

    it('should detect newly missing address/consent compared to an existing core value', async () => {
      await render();
      expect(view(component).hasAddressChanged()).toBe(true);
    });

    it('should consider both entirely missing addresses/consents equal', async () => {
      await render({
        businessData: { policy: { clients: [], holder: {}, digitalConsent: undefined } },
        data: profiling({ changeClientInformation: { policy: { clients: [], holder: {} } } }),
      });
      expect(view(component).hasAddressChanged()).toBe(false);
    });
  });

  describe('document validation, rejection and removal', () => {
    it('should ask for confirmation before validating a document', async () => {
      await render();
      view(component).onDocumentValidated(documentExample());
      expect(modalMock.confirm).toHaveBeenCalledWith(expect.objectContaining({
        nzTitle: 'Confirmation', nzOkDanger: true,
      }));
      expect(validateDocument).not.toHaveBeenCalled();
    });

    it('should validate the selected document and refresh after confirmation', async () => {
      await render();
      view(component).onDocumentValidated(documentExample());
      confirmDocumentAction();
      expect(validateDocument).toHaveBeenCalledWith('D-1');
      expect(refreshDocuments).toHaveBeenCalledTimes(1);
    });

    it('should not refresh when document validation fails', async () => {
      await render();
      const error = new Error('validation failed');
      validateDocument.mockReturnValue(throwError(() => error));
      const log = jest.spyOn(console, 'error').mockImplementation(() => {});
      view(component).onDocumentValidated(documentExample());
      confirmDocumentAction();
      expect(log).toHaveBeenCalledWith('Error validating document', error);
      expect(refreshDocuments).not.toHaveBeenCalled();
    });

    it('should reject a document only after confirmation', async () => {
      await render();
      view(component).onDocumentRejected(documentExample());
      expect(rejectDocument).not.toHaveBeenCalled();
      confirmDocumentAction();
      expect(rejectDocument).toHaveBeenCalledWith('D-1');
      expect(refreshDocuments).toHaveBeenCalledTimes(1);
    });

    it('should not refresh when document rejection fails', async () => {
      await render();
      const error = new Error('rejection failed');
      rejectDocument.mockReturnValue(throwError(() => error));
      const log = jest.spyOn(console, 'error').mockImplementation(() => {});
      view(component).onDocumentRejected(documentExample());
      confirmDocumentAction();
      expect(log).toHaveBeenCalledWith('Error rejecting document', error);
      expect(refreshDocuments).not.toHaveBeenCalled();
    });

    it('should remove a document only after confirmation', async () => {
      await render();
      view(component).onDocumentRemoved(documentExample());
      expect(removeDocument).not.toHaveBeenCalled();
      confirmDocumentAction();
      expect(removeDocument).toHaveBeenCalledWith('D-1');
      expect(refreshDocuments).toHaveBeenCalledTimes(1);
    });

    it('should not refresh when removal fails', async () => {
      await render();
      const error = new Error('remove failed');
      removeDocument.mockReturnValue(throwError(() => error));
      const log = jest.spyOn(console, 'error').mockImplementation(() => {});
      view(component).onDocumentRemoved(documentExample());
      confirmDocumentAction();
      expect(log).toHaveBeenCalledWith('[CCI] REMOVED DOCUMENT ERROR', error);
      expect(refreshDocuments).not.toHaveBeenCalled();
    });
  });

  describe('uploading, replacing and downloading documents', () => {
    it('should update the upload counter and refresh on upload success', async () => {
      await render();
      const request = { type: 'ID', name: 'new.pdf' };
      const previous = view(component).documentSaved();
      view(component).onUploadDocument(request);
      expect(uploadDocument).toHaveBeenCalledWith(request);
      expect(view(component).documentSaved()).toBe(previous + 1);
      expect(refreshDocuments).toHaveBeenCalledTimes(1);
    });

    it('should keep the upload counter and not refresh on upload failure', async () => {
      await render();
      const error = new Error('upload failed');
      uploadDocument.mockReturnValue(throwError(() => error));
      const log = jest.spyOn(console, 'error').mockImplementation(() => {});
      const previous = view(component).documentSaved();
      view(component).onUploadDocument({ type: 'ID' });
      expect(view(component).documentSaved()).toBe(previous);
      expect(refreshDocuments).not.toHaveBeenCalled();
      expect(log).toHaveBeenCalledWith('Error uploading document', error);
    });

    it('should update the edit counter and refresh after replacing a document', async () => {
      await render();
      const request = { comment: 'Updated' };
      const previous = view(component).documentEdited();
      view(component).onEditDocument({ documentId: 'D-1', request });
      expect(replacementDocument).toHaveBeenCalledWith('D-1', request);
      expect(view(component).documentEdited()).toBe(previous + 1);
      expect(refreshDocuments).toHaveBeenCalledTimes(1);
    });

    it('should keep the edit counter when replacement fails', async () => {
      await render();
      const error = new Error('replacement failed');
      replacementDocument.mockReturnValue(throwError(() => error));
      const log = jest.spyOn(console, 'error').mockImplementation(() => {});
      const previous = view(component).documentEdited();
      view(component).onEditDocument({ documentId: 'D-1', request: {} });
      expect(view(component).documentEdited()).toBe(previous);
      expect(refreshDocuments).not.toHaveBeenCalled();
      expect(log).toHaveBeenCalledWith('[CCI] UPDATE DOCUMENT ERROR', error);
    });

    it('should create a downloadable URL, click the link and revoke it', async () => {
      await render();
      const blob = new Blob(['pdf-content'], { type: 'application/pdf' });
      downloadDocument.mockReturnValue(of(blob));
      const createUrl = jest.fn().mockReturnValue('blob:mock');
      const revokeUrl = jest.fn();
      Object.defineProperty(URL, 'createObjectURL', { configurable: true, value: createUrl });
      Object.defineProperty(URL, 'revokeObjectURL', { configurable: true, value: revokeUrl });
      const click = jest.spyOn(HTMLAnchorElement.prototype, 'click').mockImplementation(() => {});

      view(component).onDocumentDownloaded(documentExample());

      expect(downloadDocument).toHaveBeenCalledWith('D-1');
      expect(createUrl).toHaveBeenCalledWith(blob);
      expect(click).toHaveBeenCalledTimes(1);
      expect(revokeUrl).toHaveBeenCalledWith('blob:mock');
    });

    it('should download with a fallback filename when none is supplied', async () => {
      await render();
      Object.defineProperty(URL, 'createObjectURL', { configurable: true, value: jest.fn(() => 'blob:mock') });
      Object.defineProperty(URL, 'revokeObjectURL', { configurable: true, value: jest.fn() });
      const anchor = document.createElement('a');
      const click = jest.spyOn(anchor, 'click').mockImplementation(() => {});
      const originalCreateElement = document.createElement.bind(document);
      jest.spyOn(document, 'createElement').mockImplementation(((tagName: string) =>
        tagName.toLowerCase() === 'a' ? anchor : originalCreateElement(tagName)) as typeof document.createElement);

      view(component).onDocumentDownloaded(documentExample({ name: undefined }));

      expect(anchor.download).toBe('document');
      expect(click).toHaveBeenCalledTimes(1);
    });

    it('should log a failure without creating a link when download fails', async () => {
      await render();
      const error = new Error('download failed');
      downloadDocument.mockReturnValue(throwError(() => error));
      const createUrl = jest.fn();
      Object.defineProperty(URL, 'createObjectURL', { configurable: true, value: createUrl });
      const log = jest.spyOn(console, 'error').mockImplementation(() => {});
      view(component).onDocumentDownloaded(documentExample());
      expect(createUrl).not.toHaveBeenCalled();
      expect(log).toHaveBeenCalledWith('[CCI] DOWNLOAD DOCUMENT ERROR', error);
    });
  });
});
