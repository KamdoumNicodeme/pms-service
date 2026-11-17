
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Signal, WritableSignal } from '@angular/core';

import { HolderProfiling } from './holder-profiling.component';

type Counters = {
  total: number;
  changes: number;
  attention: number;
  reviewed: number;
  overridden: number;
};

type Filter = 'all' | 'attention' | 'changes';

type Section = {
  id: string;
  title: string;
};

type ComparisonEvent = {
  sectionId: string;
  changes: ReadonlyMap<string, unknown>;
  removedEntries: readonly {
    id: string;
    value: string | null;
  }[];
};

type HolderProfilingView = {
  sections: readonly Section[];

  totals: Signal<Counters>;
  navSections: Signal<
    readonly {
      id: string;
      title: string;
      state: string;
      pending: number;
    }[]
  >;

  entityId: Signal<string | null>;
  activeId: WritableSignal<string | null>;
  filter: WritableSignal<Filter>;

  countersFor(sectionId: string): Counters | null;
  isOpen(sectionId: string): boolean;

  onCounters(sectionId: string, counters: Counters): void;
  setFilter(filter: Filter): void;
  toggle(sectionId: string): void;
  goTo(sectionId: string): void;

  onComparisonChange(event: ComparisonEvent): void;
  onDocumentValidated(document: unknown): void;
  onDocumentRejected(document: unknown): void;
  onDocumentDownloaded(document: unknown): void;
};

describe('HolderProfiling', () => {
  let fixture: ComponentFixture<HolderProfiling>;
  let component: HolderProfiling;
  let view: HolderProfilingView;

  const holder = (thirdPartyId: string) => ({
    thirdPartyId,
  });

  const makeCounters = (
    overrides: Partial<Counters> = {}
  ): Counters => ({
    total: 0,
    changes: 0,
    attention: 0,
    reviewed: 0,
    overridden: 0,
    ...overrides,
  });

  function render(): void {
    fixture = TestBed.createComponent(HolderProfiling);
    component = fixture.componentInstance;
    view = component as unknown as HolderProfilingView;

    fixture.componentRef.setInput(
      'snapshotHolder',
      holder('SNAPSHOT-001')
    );

    fixture.componentRef.setInput('coreHolder', holder('CORE-001'));
    fixture.componentRef.setInput('portalHolder', holder('PORTAL-001'));
    fixture.componentRef.setInput('clipHolder', holder('CLIP-001'));
    fixture.componentRef.setInput('policyNumber', 'POLICY-001');

    fixture.detectChanges();
  }

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HolderProfiling],
    })
      .overrideComponent(HolderProfiling, {
        set: {
          template: '',
          imports: [],
        },
      })
      .compileComponents();
  });

  afterEach(() => {
    jest.restoreAllMocks();
  });

  describe('Initialization', () => {
    it('should create the component', () => {
      render();

      expect(component).toBeTruthy();
    });

    it('should initialize totals to zero', () => {
      render();

      expect(view.totals()).toEqual({
        total: 0,
        changes: 0,
        attention: 0,
        reviewed: 0,
        overridden: 0,
      });
    });

    it('should initialize the filter to all', () => {
      render();

      expect(view.filter()).toBe('all');
    });

    it('should initialize the active section to null', () => {
      render();

      expect(view.activeId()).toBeNull();
    });

    it('should initialize navigation from configured sections', () => {
      render();

      const navigation = view.navSections();

      expect(navigation).toHaveLength(view.sections.length);

      view.sections.forEach((section, index) => {
        expect(navigation[index]).toEqual({
          id: section.id,
          title: section.title,
          state: 'loading',
          pending: 0,
        });
      });
    });
  });

  describe('Entity identification', () => {
    it('should prioritize the CLIP holder ID', () => {
      render();

      expect(view.entityId()).toBe('CLIP-001');
    });

    it('should use the core holder ID when CLIP is missing', () => {
      render();

      fixture.componentRef.setInput('clipHolder', null);
      fixture.detectChanges();

      expect(view.entityId()).toBe('CORE-001');
    });

    it('should use the portal holder ID when CLIP and core are missing', () => {
      render();

      fixture.componentRef.setInput('clipHolder', null);
      fixture.componentRef.setInput('coreHolder', null);
      fixture.detectChanges();

      expect(view.entityId()).toBe('PORTAL-001');
    });

    it('should fall back to the snapshot holder ID', () => {
      render();

      fixture.componentRef.setInput('clipHolder', null);
      fixture.componentRef.setInput('coreHolder', null);
      fixture.componentRef.setInput('portalHolder', null);
      fixture.detectChanges();

      expect(view.entityId()).toBe('SNAPSHOT-001');
    });

    it('should recompute the entity ID when an input changes', () => {
      render();

      fixture.componentRef.setInput(
        'clipHolder',
        holder('NEW-CLIP-001')
      );
      fixture.detectChanges();

      expect(view.entityId()).toBe('NEW-CLIP-001');
    });
  });

  describe('Comparison counters', () => {
    it('should return null for an unknown section', () => {
      render();

      expect(view.countersFor('unknown-section')).toBeNull();
    });

    it('should store counters for a section', () => {
      render();

      const sectionId = view.sections[0].id;
      const counters = makeCounters({
        total: 5,
        changes: 2,
        attention: 1,
        reviewed: 1,
      });

      view.onCounters(sectionId, counters);

      expect(view.countersFor(sectionId)).toEqual(counters);
    });

    it('should aggregate counters across multiple sections', () => {
      render();

      const firstSection = view.sections[0].id;
      const secondSection = view.sections[1].id;

      view.onCounters(
        firstSection,
        makeCounters({
          total: 5,
          changes: 3,
          attention: 2,
          reviewed: 1,
          overridden: 1,
        })
      );

      view.onCounters(
        secondSection,
        makeCounters({
          total: 4,
          changes: 2,
          attention: 1,
          reviewed: 2,
          overridden: 0,
        })
      );

      expect(view.totals()).toEqual({
        total: 9,
        changes: 5,
        attention: 3,
        reviewed: 3,
        overridden: 1,
      });
    });

    it('should replace previous counters for the same section', () => {
      render();

      const sectionId = view.sections[0].id;

      view.onCounters(
        sectionId,
        makeCounters({ total: 5, changes: 3 })
      );

      view.onCounters(
        sectionId,
        makeCounters({ total: 5, changes: 1 })
      );

      expect(view.totals().changes).toBe(1);
      expect(view.totals().total).toBe(5);
    });

    it('should update the navigation pending count', () => {
      render();

      const sectionId = view.sections[0].id;

      view.onCounters(
        sectionId,
        makeCounters({
          total: 5,
          changes: 3,
          attention: 3,
          reviewed: 1,
        })
      );

      const section = view.navSections().find(
        item => item.id === sectionId
      );

      expect(section?.pending).toBe(2);
    });
  });

  describe('Section navigation', () => {
    it('should initially close all sections', () => {
      render();

      for (const section of view.sections) {
        expect(view.isOpen(section.id)).toBe(false);
      }
    });

    it('should open a section when toggled', () => {
      render();

      const sectionId = view.sections[0].id;

      view.toggle(sectionId);

      expect(view.isOpen(sectionId)).toBe(true);
      expect(view.activeId()).toBe(sectionId);
    });

    it('should close an opened section when toggled again', () => {
      render();

      const sectionId = view.sections[0].id;

      view.toggle(sectionId);
      view.toggle(sectionId);

      expect(view.isOpen(sectionId)).toBe(false);
    });

    it('should open a section when navigating to it', () => {
      render();

      const sectionId = view.sections[0].id;

      view.goTo(sectionId);

      expect(view.isOpen(sectionId)).toBe(true);
      expect(view.activeId()).toBe(sectionId);
    });

    it('should scroll to the requested section', () => {
      render();

      const sectionId = view.sections[0].id;
      const panel = document.createElement('section');

      panel.scrollIntoView = jest.fn();

      const querySpy = jest
        .spyOn(fixture.nativeElement, 'querySelector')
        .mockReturnValue(panel);

      view.goTo(sectionId);

      expect(querySpy).toHaveBeenCalledWith(
        `[data-section="${sectionId}"]`
      );

      expect(panel.scrollIntoView).toHaveBeenCalledWith({
        block: 'start',
        behavior: 'smooth',
      });
    });

    it('should automatically open a pending section', () => {
      render();

      const sectionId = view.sections[0].id;

      view.onCounters(
        sectionId,
        makeCounters({
          total: 3,
          changes: 2,
          attention: 2,
          reviewed: 0,
        })
      );

      expect(view.isOpen(sectionId)).toBe(true);
    });
  });

  describe('Section filtering', () => {
    it('should update the selected filter', () => {
      render();

      view.setFilter('attention');

      expect(view.filter()).toBe('attention');
    });

    it('should open only sections requiring attention', () => {
      render();

      const firstSection = view.sections[0].id;
      const secondSection = view.sections[1].id;

      view.onCounters(
        firstSection,
        makeCounters({
          total: 3,
          attention: 2,
          changes: 2,
        })
      );

      view.onCounters(
        secondSection,
        makeCounters({
          total: 3,
          attention: 0,
          changes: 1,
        })
      );

      view.setFilter('attention');

      expect(view.isOpen(firstSection)).toBe(true);
      expect(view.isOpen(secondSection)).toBe(false);
    });

    it('should open sections containing changes', () => {
      render();

      const firstSection = view.sections[0].id;
      const secondSection = view.sections[1].id;

      view.onCounters(
        firstSection,
        makeCounters({ total: 3, changes: 2 })
      );

      view.onCounters(
        secondSection,
        makeCounters({ total: 3, changes: 0 })
      );

      view.setFilter('changes');

      expect(view.isOpen(firstSection)).toBe(true);
      expect(view.isOpen(secondSection)).toBe(false);
    });

    it('should preserve open sections when resetting the filter', () => {
      render();

      const sectionId = view.sections[0].id;

      view.toggle(sectionId);
      view.setFilter('all');

      expect(view.filter()).toBe('all');
      expect(view.isOpen(sectionId)).toBe(true);
    });
  });

  describe('Comparison changes', () => {
    it('should emit changes with the snapshot holder ID', () => {
      render();

      const emitSpy = jest.spyOn(component.holderChanges, 'emit');

      const changes = new Map([
        ['firstName', {
          source: 'manual',
          value: 'John',
          reason: '',
          comment: '',
        }],
      ]);

      const removedEntries = [
        { id: 'emails:old@example.com', value: 'old@example.com' },
      ];

      view.onComparisonChange({
        sectionId: 'physical-person',
        changes,
        removedEntries,
      });

      expect(emitSpy).toHaveBeenCalledWith({
        thirdPartyId: 'SNAPSHOT-001',
        sectionId: 'physical-person',
        changes,
        removedEntries,
      });
    });

    it('should emit empty changes without losing the section ID', () => {
      render();

      const emitSpy = jest.spyOn(component.holderChanges, 'emit');

      view.onComparisonChange({
        sectionId: 'contact-details',
        changes: new Map(),
        removedEntries: [],
      });

      expect(emitSpy).toHaveBeenCalledWith({
        thirdPartyId: 'SNAPSHOT-001',
        sectionId: 'contact-details',
        changes: new Map(),
        removedEntries: [],
      });
    });
  });

  describe('Document events', () => {
    it('should forward document validation', () => {
      render();

      const document = { documentId: 'DOC-001' };
      const emitSpy = jest.spyOn(component.validatedDocument, 'emit');

      view.onDocumentValidated(document);

      expect(emitSpy).toHaveBeenCalledWith(document);
    });

    it('should forward document rejection', () => {
      render();

      const document = { documentId: 'DOC-002' };
      const emitSpy = jest.spyOn(component.rejectedDocument, 'emit');

      view.onDocumentRejected(document);

      expect(emitSpy).toHaveBeenCalledWith(document);
    });

    it('should forward document download', () => {
      render();

      const document = { documentId: 'DOC-003' };
      const emitSpy = jest.spyOn(component.downloadedDocument, 'emit');

      view.onDocumentDownloaded(document);

      expect(emitSpy).toHaveBeenCalledWith(document);
    });
  });

  describe('Review state', () => {
    it('should emit a review state when counters change', () => {
      render();

      const emitSpy = jest.spyOn(component.stateChange, 'emit');

      const sectionId = view.sections[0].id;

      view.onCounters(
        sectionId,
        makeCounters({
          total: 4,
          changes: 2,
          attention: 2,
          reviewed: 0,
        })
      );

      fixture.detectChanges();

      expect(emitSpy).toHaveBeenCalledWith(
        expect.any(String)
      );
    });
  });
});
