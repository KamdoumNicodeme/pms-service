
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Signal, WritableSignal } from '@angular/core';

import { PolicyProfiling } from './policy-profiling';

type PolicyInput = ReturnType<PolicyProfiling['snapshotPolicy']>;

type Counters = {
  total: number;
  changes: number;
  attention: number;
  reviewed: number;
  overridden: number;
};

type ComparisonFilter = 'all' | 'attention' | 'changes';

type NavSection = {
  id: string;
  title: string;
  state: string;
  pending: number;
};

type PolicyChangesEvent = {
  sectionId: string;
  changes: ReadonlyMap<string, unknown>;
  removedEntries: readonly {
    id: string;
    value: string | null;
  }[];
};

type PolicyProfilingView = {
  sections: readonly { id: string; title: string }[];

  totals: Signal<Counters>;
  navSections: Signal<readonly NavSection[]>;
  activeId: WritableSignal<string | null>;
  filter: WritableSignal<ComparisonFilter>;

  countersFor(sectionId: string): Counters | null;
  isOpen(sectionId: string): boolean;

  onCounters(sectionId: string, counters: Counters): void;
  setFilter(filter: ComparisonFilter): void;
  toggle(sectionId: string): void;
  goTo(sectionId: string): void;
  onPolicyChanges(event: PolicyChangesEvent): void;
};

describe('PolicyProfiling', () => {
  let fixture: ComponentFixture<PolicyProfiling>;
  let component: PolicyProfiling;
  let view: PolicyProfilingView;

  const createPolicy = (): PolicyInput =>
    ({ policyNumber: 'POLICY-001' }) as unknown as PolicyInput;

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
    fixture = TestBed.createComponent(PolicyProfiling);
    component = fixture.componentInstance;
    view = component as unknown as PolicyProfilingView;

    fixture.componentRef.setInput('snapshotPolicy', createPolicy());
    fixture.componentRef.setInput('portalPolicy', createPolicy());
    fixture.componentRef.setInput('corePolicy', createPolicy());
    fixture.componentRef.setInput('clipPolicy', null);
    fixture.componentRef.setInput('policyNumber', 'POLICY-001');

    fixture.detectChanges();
  }

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PolicyProfiling],
    })
      .overrideComponent(PolicyProfiling, {
        set: {
          template: '',
          imports: [],
        },
      })
      .compileComponents();
  });

  afterEach(() => jest.restoreAllMocks());

  describe('Initialization', () => {
    it('should create the component', () => {
      render();

      expect(component).toBeTruthy();
    });

    it('should initialize totals to zero', () => {
      render();

      expect(view.totals()).toEqual(makeCounters());
    });

    it('should initialize the filter to all', () => {
      render();

      expect(view.filter()).toBe('all');
    });

    it('should initialize the active section to null', () => {
      render();

      expect(view.activeId()).toBeNull();
    });

    it('should initialize navigation from policy sections', () => {
      render();

      expect(view.navSections()).toHaveLength(view.sections.length);

      view.sections.forEach((section, index) => {
        expect(view.navSections()[index]).toEqual({
          id: section.id,
          title: section.title,
          state: 'loading',
          pending: 0,
        });
      });
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
        changes: 3,
        attention: 2,
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

      expect(view.totals().total).toBe(5);
      expect(view.totals().changes).toBe(1);
    });

    it('should update navigation pending count', () => {
      render();

      const sectionId = view.sections[0].id;

      view.onCounters(
        sectionId,
        makeCounters({
          total: 5,
          attention: 4,
          reviewed: 2,
        })
      );

      const section = view.navSections().find(
        item => item.id === sectionId
      );

      expect(section?.pending).toBe(2);
    });

    it('should update totals reactively when counters change', () => {
      render();

      const sectionId = view.sections[0].id;

      expect(view.totals().changes).toBe(0);

      view.onCounters(
        sectionId,
        makeCounters({ total: 3, changes: 2 })
      );

      expect(view.totals().changes).toBe(2);

      view.onCounters(
        sectionId,
        makeCounters({ total: 3, changes: 3 })
      );

      expect(view.totals().changes).toBe(3);
    });
  });

  describe('Section navigation', () => {
    it('should initially close all sections', () => {
      render();

      view.sections.forEach(section => {
        expect(view.isOpen(section.id)).toBe(false);
      });
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

    it('should allow multiple sections to remain open', () => {
      render();

      const firstSection = view.sections[0].id;
      const secondSection = view.sections[1].id;

      view.toggle(firstSection);
      view.toggle(secondSection);

      expect(view.isOpen(firstSection)).toBe(true);
      expect(view.isOpen(secondSection)).toBe(true);
      expect(view.activeId()).toBe(secondSection);
    });

    it('should navigate to a section and scroll into view', () => {
      render();

      const sectionId = view.sections[0].id;
      const panel = document.createElement('section');

      panel.dataset['section'] = sectionId;
      panel.scrollIntoView = jest.fn();

      fixture.nativeElement.appendChild(panel);

      view.goTo(sectionId);

      expect(view.isOpen(sectionId)).toBe(true);
      expect(view.activeId()).toBe(sectionId);

      expect(panel.scrollIntoView).toHaveBeenCalledWith({
        block: 'start',
        behavior: 'smooth',
      });
    });

    it('should not throw when the target section is missing in DOM', () => {
      render();

      expect(() => {
        view.goTo('missing-section');
      }).not.toThrow();

      expect(view.activeId()).toBe('missing-section');
    });
  });

  describe('Section filters', () => {
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
          changes: 2,
          attention: 2,
        })
      );

      view.onCounters(
        secondSection,
        makeCounters({
          total: 3,
          changes: 1,
          attention: 0,
        })
      );

      view.setFilter('attention');

      expect(view.isOpen(firstSection)).toBe(true);
      expect(view.isOpen(secondSection)).toBe(false);
    });

    it('should open only sections containing changes', () => {
      render();

      const firstSection = view.sections[0].id;
      const secondSection = view.sections[1].id;

      view.onCounters(
        firstSection,
        makeCounters({
          total: 3,
          changes: 2,
        })
      );

      view.onCounters(
        secondSection,
        makeCounters({
          total: 3,
          changes: 0,
        })
      );

      view.setFilter('changes');

      expect(view.isOpen(firstSection)).toBe(true);
      expect(view.isOpen(secondSection)).toBe(false);
    });

    it('should preserve opened sections when resetting filter to all', () => {
      render();

      const sectionId = view.sections[0].id;

      view.toggle(sectionId);
      view.setFilter('all');

      expect(view.filter()).toBe('all');
      expect(view.isOpen(sectionId)).toBe(true);
    });

    it('should not open sections without counters when filtering', () => {
      render();

      view.setFilter('attention');

      view.sections.forEach(section => {
        expect(view.isOpen(section.id)).toBe(false);
      });
    });
  });

  describe('Policy changes', () => {
    it('should forward policy changes to the parent', () => {
      render();

      const emitSpy = jest.spyOn(component.changesChange, 'emit');

      const changes = new Map<string, unknown>([
        [
          'sending-address',
          {
            source: 'manual',
            value: 'New address',
            reason: 'CORRECTION',
            comment: '',
          },
        ],
      ]);

      const event: PolicyChangesEvent = {
        sectionId: 'sending-address',
        changes,
        removedEntries: [],
      };

      view.onPolicyChanges(event);

      expect(emitSpy).toHaveBeenCalledTimes(1);
      expect(emitSpy).toHaveBeenCalledWith(event);
    });

    it('should forward empty changes', () => {
      render();

      const emitSpy = jest.spyOn(component.changesChange, 'emit');

      const event: PolicyChangesEvent = {
        sectionId: 'sending-address',
        changes: new Map(),
        removedEntries: [],
      };

      view.onPolicyChanges(event);

      expect(emitSpy).toHaveBeenCalledWith(event);
    });

    it('should preserve removed entries when forwarding changes', () => {
      render();

      const emitSpy = jest.spyOn(component.changesChange, 'emit');

      const removedEntries = [
        { id: 'entry-001', value: 'old-value' },
      ];

      const event: PolicyChangesEvent = {
        sectionId: 'opt-in-out',
        changes: new Map(),
        removedEntries,
      };

      view.onPolicyChanges(event);

      expect(emitSpy).toHaveBeenCalledWith(
        expect.objectContaining({
          removedEntries,
        })
      );
    });
  });

  describe('Review state', () => {
    it('should emit the review state when counters change', () => {
      render();

      const emitSpy = jest.spyOn(component.stateChange, 'emit');

      view.onCounters(
        view.sections[0].id,
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
