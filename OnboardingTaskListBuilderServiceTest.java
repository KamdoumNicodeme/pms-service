
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';

import { PolicySection } from './policy-section';
import { COMPARISON_DATA_SERVICE } from 'PATH_TO_COMPARISON_DATA_SERVICE';

type Policy = ReturnType<PolicySection['snapshotPolicy']>;

type SectionResource = {
  value: () => unknown;
  isLoading: () => boolean;
  error: () => unknown;
};

describe('PolicySection', () => {
  let fixture: ComponentFixture<PolicySection>;
  let component: PolicySection;

  let getPolicySectionMock: jest.Mock;

  const snapshotPolicy = {
    policyNumber: 'SNAPSHOT-001',
  } as unknown as Policy;

  const portalPolicy = {
    policyNumber: 'PORTAL-001',
  } as unknown as Policy;

  const corePolicy = {
    policyNumber: 'CORE-001',
  } as unknown as Policy;

  const clipPolicy = {
    policyNumber: 'CLIP-001',
  } as unknown as Policy;

  const sectionData = {
    id: 'sending-address',
    title: 'Sending address',
    fields: [],
  };

  const section = (): SectionResource =>
    (component as unknown as {
      section: SectionResource;
    }).section;

  async function render(): Promise<void> {
    fixture = TestBed.createComponent(PolicySection);
    component = fixture.componentInstance;

    fixture.componentRef.setInput(
      'snapshotPolicy',
      snapshotPolicy
    );
    fixture.componentRef.setInput(
      'portalPolicy',
      portalPolicy
    );
    fixture.componentRef.setInput(
      'corePolicy',
      corePolicy
    );
    fixture.componentRef.setInput('clipPolicy', null);
    fixture.componentRef.setInput(
      'sectionId',
      'sending-address'
    );

    fixture.detectChanges();
    await fixture.whenStable();
  }

  beforeEach(async () => {
    getPolicySectionMock = jest
      .fn()
      .mockReturnValue(of(sectionData));

    await TestBed.configureTestingModule({
      imports: [PolicySection],
    })
      .overrideComponent(PolicySection, {
        set: {
          template: '',
          imports: [],
          providers: [
            {
              provide: COMPARISON_DATA_SERVICE,
              useValue: {
                getPolicySection: getPolicySectionMock,
              },
            },
          ],
        },
      })
      .compileComponents();
  });

  describe('Initialization', () => {
    it('should create the component', async () => {
      await render();

      expect(component).toBeTruthy();
    });

    it('should initialize optional inputs with default values', async () => {
      await render();

      expect(component.clipPolicy()).toBeNull();
      expect(component.readOnly()).toBe(false);
      expect(component.entityId()).toBeNull();
      expect(component.filter()).toBe('all');
      expect(component.changeReasons()).toEqual({});
    });

    it('should initialize references as an empty object', async () => {
      await render();

      const view = component as unknown as {
        references: () => Record<string, unknown>;
      };

      expect(view.references()).toEqual({});
    });
  });

  describe('Policy section resource', () => {
    it('should load the policy section with the correct parameters', async () => {
      await render();

      expect(getPolicySectionMock).toHaveBeenCalledWith(
        snapshotPolicy,
        portalPolicy,
        corePolicy,
        null,
        'sending-address'
      );

      expect(getPolicySectionMock).toHaveBeenCalledTimes(1);
    });

    it('should expose the loaded section data', async () => {
      await render();

      expect(section().value()).toEqual(sectionData);
      expect(section().isLoading()).toBe(false);
    });

    it('should reload when sectionId changes', async () => {
      await render();

      getPolicySectionMock.mockClear();

      fixture.componentRef.setInput(
        'sectionId',
        'opt-in-out'
      );

      fixture.detectChanges();
      await fixture.whenStable();

      expect(getPolicySectionMock).toHaveBeenCalledWith(
        snapshotPolicy,
        portalPolicy,
        corePolicy,
        null,
        'opt-in-out'
      );

      expect(getPolicySectionMock).toHaveBeenCalledTimes(1);
    });

    it('should reload when snapshotPolicy changes', async () => {
      await render();

      getPolicySectionMock.mockClear();

      const updatedSnapshot = {
        policyNumber: 'SNAPSHOT-002',
      } as unknown as Policy;

      fixture.componentRef.setInput(
        'snapshotPolicy',
        updatedSnapshot
      );

      fixture.detectChanges();
      await fixture.whenStable();

      expect(getPolicySectionMock).toHaveBeenCalledWith(
        updatedSnapshot,
        portalPolicy,
        corePolicy,
        null,
        'sending-address'
      );
    });

    it('should reload when portalPolicy changes', async () => {
      await render();

      getPolicySectionMock.mockClear();

      const updatedPortal = {
        policyNumber: 'PORTAL-002',
      } as unknown as Policy;

      fixture.componentRef.setInput(
        'portalPolicy',
        updatedPortal
      );

      fixture.detectChanges();
      await fixture.whenStable();

      expect(getPolicySectionMock).toHaveBeenCalledWith(
        snapshotPolicy,
        updatedPortal,
        corePolicy,
        null,
        'sending-address'
      );
    });

    it('should reload when corePolicy changes', async () => {
      await render();

      getPolicySectionMock.mockClear();

      const updatedCore = {
        policyNumber: 'CORE-002',
      } as unknown as Policy;

      fixture.componentRef.setInput(
        'corePolicy',
        updatedCore
      );

      fixture.detectChanges();
      await fixture.whenStable();

      expect(getPolicySectionMock).toHaveBeenCalledWith(
        snapshotPolicy,
        portalPolicy,
        updatedCore,
        null,
        'sending-address'
      );
    });

    it('should reload when clipPolicy becomes available', async () => {
      await render();

      getPolicySectionMock.mockClear();

      fixture.componentRef.setInput(
        'clipPolicy',
        clipPolicy
      );

      fixture.detectChanges();
      await fixture.whenStable();

      expect(getPolicySectionMock).toHaveBeenCalledWith(
        snapshotPolicy,
        portalPolicy,
        corePolicy,
        clipPolicy,
        'sending-address'
      );
    });

    it('should not reload when readOnly changes', async () => {
      await render();

      getPolicySectionMock.mockClear();

      fixture.componentRef.setInput('readOnly', true);

      fixture.detectChanges();
      await fixture.whenStable();

      expect(getPolicySectionMock).not.toHaveBeenCalled();
      expect(component.readOnly()).toBe(true);
    });

    it('should not reload when filter changes', async () => {
      await render();

      getPolicySectionMock.mockClear();

      fixture.componentRef.setInput(
        'filter',
        'attention'
      );

      fixture.detectChanges();
      await fixture.whenStable();

      expect(getPolicySectionMock).not.toHaveBeenCalled();
      expect(component.filter()).toBe('attention');
    });

    it('should not reload when entityId changes', async () => {
      await render();

      getPolicySectionMock.mockClear();

      fixture.componentRef.setInput(
        'entityId',
        'ENTITY-002'
      );

      fixture.detectChanges();
      await fixture.whenStable();

      expect(getPolicySectionMock).not.toHaveBeenCalled();
      expect(component.entityId()).toBe('ENTITY-002');
    });
  });
});
