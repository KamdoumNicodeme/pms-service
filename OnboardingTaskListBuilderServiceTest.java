
import {
  ComponentFixture,
  TestBed,
} from '@angular/core/testing';
import { of } from 'rxjs';

import { ProfilingSection } from './profiling-section';
import {
  COMPARISON_DATA_SERVICE,
} from '../path/to/comparison-data.service';

// Reuse the existing model imports from your project.
import type {
  IThirdParty,
  ComparisonSectionDto,
} from '../path/to/models';

describe('ProfilingSection', () => {
  let component: ProfilingSection;
  let fixture: ComponentFixture<ProfilingSection>;

  let comparisonService: {
    getSection: jest.Mock;
  };

  const snapshotHolder = {
    thirdPartyId: 'HOLDER_001',
    firstName: 'John',
    lastName: 'Doe',
  } as unknown as IThirdParty;

  const coreHolder = {
    thirdPartyId: 'HOLDER_001',
    firstName: 'John',
    lastName: 'Smith',
  } as unknown as IThirdParty;

  const portalHolder = {
    thirdPartyId: 'HOLDER_001',
    firstName: 'John',
    lastName: 'Doe',
  } as unknown as IThirdParty;

  const clipHolder = {
    thirdPartyId: 'HOLDER_001',
    firstName: 'Jonathan',
    lastName: 'Doe',
  } as unknown as IThirdParty;

  const section = {
    id: 'physical-person',
  } as ComparisonSectionDto;

  async function render(): Promise<void> {
    fixture = TestBed.createComponent(ProfilingSection);
    component = fixture.componentInstance;

    fixture.componentRef.setInput(
      'policyNumber',
      'POLICY_001'
    );
    fixture.componentRef.setInput(
      'snapshotHolder',
      snapshotHolder
    );
    fixture.componentRef.setInput(
      'sectionId',
      'physical-person'
    );

    fixture.detectChanges();
    await fixture.whenStable();
    fixture.detectChanges();
  }

  async function updateInput(
    name: string,
    value: unknown
  ): Promise<void> {
    fixture.componentRef.setInput(name, value);
    fixture.detectChanges();

    await fixture.whenStable();
    fixture.detectChanges();
  }

  beforeEach(async () => {
    comparisonService = {
      getSection: jest.fn().mockReturnValue(of(section)),
    };

    await TestBed.configureTestingModule({
      imports: [ProfilingSection],
      providers: [
        {
          provide: COMPARISON_DATA_SERVICE,
          useValue: comparisonService,
        },
      ],
    })
      .overrideComponent(ProfilingSection, {
        set: {
          template: '',
          imports: [],
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

      expect(component.coreHolder()).toBeNull();
      expect(component.portalHolder()).toBeNull();
      expect(component.clipHolder()).toBeNull();
      expect(component.filter()).toBe('all');
      expect(component.readOnly()).toBe(false);
      expect(component.entityId()).toBeNull();
      expect(component.changeReasons()).toEqual({});
    });

    it('should initialize required inputs correctly', async () => {
      await render();

      expect(component.policyNumber()).toBe('POLICY_001');
      expect(component.snapshotHolder()).toEqual(
        snapshotHolder
      );
      expect(component.sectionId()).toBe(
        'physical-person'
      );
    });
  });

  describe('Section loading', () => {
    it('should call getSection with the correct context', async () => {
      await render();

      expect(comparisonService.getSection).toHaveBeenCalledWith(
        {
          policyNumber: 'POLICY_001',
          coreHolder: null,
          snapshotHolder,
          portalHolder: null,
          clipHolder: null,
        },
        'physical-person'
      );
    });

    it('should load the section returned by the service', async () => {
      await render();

      const view = component as unknown as {
        section: {
          value: () => ComparisonSectionDto | undefined;
        };
      };

      expect(view.section.value()).toEqual(section);
    });

    it('should pass all holder sources to getSection', async () => {
      await render();

      await updateInput('coreHolder', coreHolder);
      await updateInput('portalHolder', portalHolder);
      await updateInput('clipHolder', clipHolder);

      expect(comparisonService.getSection).toHaveBeenLastCalledWith(
        {
          policyNumber: 'POLICY_001',
          coreHolder,
          snapshotHolder,
          portalHolder,
          clipHolder,
        },
        'physical-person'
      );
    });
  });

  describe('Reactive inputs', () => {
    it('should reload the section when policyNumber changes', async () => {
      await render();

      comparisonService.getSection.mockClear();

      await updateInput('policyNumber', 'POLICY_002');

      expect(comparisonService.getSection).toHaveBeenCalledWith(
        expect.objectContaining({
          policyNumber: 'POLICY_002',
        }),
        'physical-person'
      );
    });

    it('should reload the section when sectionId changes', async () => {
      await render();

      comparisonService.getSection.mockClear();

      await updateInput('sectionId', 'contact-details');

      expect(comparisonService.getSection).toHaveBeenCalledWith(
        expect.any(Object),
        'contact-details'
      );
    });

    it('should reload the section when snapshotHolder changes', async () => {
      await render();

      comparisonService.getSection.mockClear();

      const updatedHolder = {
        ...snapshotHolder,
        firstName: 'Michael',
      };

      await updateInput(
        'snapshotHolder',
        updatedHolder
      );

      expect(comparisonService.getSection).toHaveBeenCalledWith(
        expect.objectContaining({
          snapshotHolder: updatedHolder,
        }),
        'physical-person'
      );
    });

    it('should reload the section when coreHolder changes', async () => {
      await render();

      comparisonService.getSection.mockClear();

      await updateInput('coreHolder', coreHolder);

      expect(comparisonService.getSection).toHaveBeenCalledWith(
        expect.objectContaining({
          coreHolder,
        }),
        'physical-person'
      );
    });

    it('should not reload when only the filter changes', async () => {
      await render();

      comparisonService.getSection.mockClear();

      await updateInput('filter', 'attention');

      expect(component.filter()).toBe('attention');
      expect(
        comparisonService.getSection
      ).not.toHaveBeenCalled();
    });

    it('should not reload when readOnly changes', async () => {
      await render();

      comparisonService.getSection.mockClear();

      await updateInput('readOnly', true);

      expect(component.readOnly()).toBe(true);
      expect(
        comparisonService.getSection
      ).not.toHaveBeenCalled();
    });

    it('should update entityId without reloading the section', async () => {
      await render();

      comparisonService.getSection.mockClear();

      await updateInput('entityId', 'HOLDER_001');

      expect(component.entityId()).toBe('HOLDER_001');
      expect(
        comparisonService.getSection
      ).not.toHaveBeenCalled();
    });
  });
});
