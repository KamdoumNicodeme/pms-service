import { ComponentFixture, TestBed } from '@angular/core/testing';
import { signal } from '@angular/core';

import { DataComparison } from './data-comparison';
import { ComparisonStore } from './comparison.store';

/**
 * Component-level tests for DataComparison.
 *
 * The real ComparisonStore is intentionally replaced: these tests verify
 * the component's contracts (inputs -> store, store -> outputs, UI -> store).
 * Comparison algorithms and resolution logic belong in comparison.store.spec.ts.
 *
 * Uses the same Jest setup and relative imports as the existing project tests.
 */
describe('DataComparison', () => {
  type Section = ReturnType<DataComparison['section']>;
  type Reasons = ReturnType<DataComparison['changeReasons']>;
  type Filter = ReturnType<DataComparison['filter']>;
  type Counters = ReturnType<ComparisonStore['counters']>;
  type Changes = ReturnType<ComparisonStore['appliedChanges']>;
  type RemovedEntries = ReturnType<ComparisonStore['removedEntries']>;

  const SECTION_ID = 'general-information';
  const ENTITY_ID = 'CLIENT-123';
  const section = { id: SECTION_ID, title: 'General information', fields: [] } as unknown as Section;
  const reasons = {} as Reasons;

  const initialCounters = (): Counters => ({
    total: 0,
    changes: 0,
    attention: 0,
    reviewed: 0,
    overridden: 0
  } as Counters);

  let fixture: ComponentFixture<DataComparison>;
  let component: DataComparison;

  let countersSignal: ReturnType<typeof signal<Counters>>;
  let changesSignal: ReturnType<typeof signal<Changes>>;
  let removedSignal: ReturnType<typeof signal<RemovedEntries>>;
  let focusedIdSignal: ReturnType<typeof signal<string | null>>;

  let store: {
    initialize: jest.Mock;
    setFilter: jest.Mock;
    counters: typeof countersSignal;
    appliedChanges: typeof changesSignal;
    removedEntries: typeof removedSignal;
    focusedId: typeof focusedIdSignal;
    apply: jest.Mock;
    moveFocus: jest.Mock;
    activate: jest.Mock;
    collapse: jest.Mock;
  };

  beforeEach(async () => {
    countersSignal = signal(initialCounters());
    changesSignal = signal(new Map() as Changes);
    removedSignal = signal([] as unknown as RemovedEntries);
    focusedIdSignal = signal<string | null>(null);

    store = {
      initialize: jest.fn(),
      setFilter: jest.fn(),
      counters: countersSignal,
      appliedChanges: changesSignal,
      removedEntries: removedSignal,
      focusedId: focusedIdSignal,
      apply: jest.fn(),
      moveFocus: jest.fn(),
      activate: jest.fn(),
      collapse: jest.fn()
    };

    await TestBed.configureTestingModule({
      imports: [DataComparison]
    })
      .overrideComponent(DataComparison, {
        set: {
          template: '',
          imports: [],
          providers: [{ provide: ComparisonStore, useValue: store }]
        }
      })
      .compileComponents();

    fixture = TestBed.createComponent(DataComparison);
    component = fixture.componentInstance;
  });

  afterEach(() => fixture.destroy());

  function render(options: {
    section?: Section;
    entityId?: string | null;
    changeReasons?: Reasons;
    filter?: Filter;
    readOnly?: boolean;
  } = {}): void {
    fixture.componentRef.setInput('section', options.section ?? section);
    fixture.componentRef.setInput('entityId',
      options.entityId === undefined ? ENTITY_ID : options.entityId);
    fixture.componentRef.setInput('changeReasons', options.changeReasons ?? reasons);
    if (options.filter !== undefined) {
      fixture.componentRef.setInput('filter', options.filter);
    }
    if (options.readOnly !== undefined) {
      fixture.componentRef.setInput('readOnly', options.readOnly);
    }
    fixture.detectChanges();
  }

  type Actions = {
    onApplyEntry: (action: { id: string; payload: unknown }) => void;
    onApplyChild: (action: { id: string; payload: unknown }) => void;
    onKeydown: (event: KeyboardEvent) => void;
  };

  function actions(): Actions {
    // Angular's template invokes these protected methods. The cast is
    // deliberately limited to this test-only interface.
    return component as unknown as Actions;
  }

  function keyEvent(
    key: string,
    target: EventTarget = document.createElement('div')
  ): KeyboardEvent & { preventDefault: jest.Mock } {
    return {
      key,
      target,
      preventDefault: jest.fn()
    } as unknown as KeyboardEvent & { preventDefault: jest.Mock };
  }

  describe('construction and default inputs', () => {
    it('creates the component', () => {
      render();
      expect(component).toBeTruthy();
    });

    it('exposes the expected defaults', () => {
      render();
      expect(component.readOnly()).toBe(false);
      expect(component.filter()).toBe('all');
      expect(component.entityId()).toBe(ENTITY_ID);
      expect(component.removedEntries()).toEqual([]);
    });

    it('preserves read-only mode passed by the parent', () => {
      render({ readOnly: true });
      expect(component.readOnly()).toBe(true);
    });
  });

  describe('store initialization', () => {
    it('initializes from section, entityId and changeReasons', () => {
      render();
      expect(store.initialize).toHaveBeenCalledTimes(1);
      expect(store.initialize).toHaveBeenCalledWith(section, ENTITY_ID, reasons);
    });

    it.each([null, ''])(
      'does not initialize without a valid entityId (%s)', entityId => {
        render({ entityId });
        expect(store.initialize).not.toHaveBeenCalled();
      }
    );

    it('initializes when the entityId is provided after rendering', () => {
      render({ entityId: null });
      fixture.componentRef.setInput('entityId', 'CLIENT-456');
      fixture.detectChanges();
      expect(store.initialize).toHaveBeenCalledTimes(1);
      expect(store.initialize).toHaveBeenCalledWith(section, 'CLIENT-456', reasons);
    });

    it('initializes when the entityId changes', () => {
      render();
      store.initialize.mockClear();
      fixture.componentRef.setInput('entityId', 'CLIENT-456');
      fixture.detectChanges();
      expect(store.initialize).toHaveBeenCalledTimes(1);
      expect(store.initialize).toHaveBeenCalledWith(section, 'CLIENT-456', reasons);
    });

    it('initializes with the new section when section changes', () => {
      render();
      store.initialize.mockClear();
      const nextSection = { ...section, id: 'contact-details' } as Section;
      fixture.componentRef.setInput('section', nextSection);
      fixture.detectChanges();
      expect(store.initialize).toHaveBeenCalledTimes(1);
      expect(store.initialize).toHaveBeenCalledWith(nextSection, ENTITY_ID, reasons);
    });

    it('initializes with the new change reasons when reasons change', () => {
      render();
      store.initialize.mockClear();
      const nextReasons = { firstname: { reason: 'CLIENT_REQUEST' } } as unknown as Reasons;
      fixture.componentRef.setInput('changeReasons', nextReasons);
      fixture.detectChanges();
      expect(store.initialize).toHaveBeenCalledTimes(1);
      expect(store.initialize).toHaveBeenCalledWith(section, ENTITY_ID, nextReasons);
    });

    it('does not reinitialize when only the filter changes', () => {
      render();
      store.initialize.mockClear();
      fixture.componentRef.setInput('filter', 'attention' as Filter);
      fixture.detectChanges();
      expect(store.initialize).not.toHaveBeenCalled();
    });

    it('does not reinitialize when only readOnly changes', () => {
      render();
      store.initialize.mockClear();
      fixture.componentRef.setInput('readOnly', true);
      fixture.detectChanges();
      expect(store.initialize).not.toHaveBeenCalled();
    });

    it('does not reinitialize when the entityId becomes null', () => {
      render();
      store.initialize.mockClear();
      fixture.componentRef.setInput('entityId', null);
      fixture.detectChanges();
      expect(store.initialize).not.toHaveBeenCalled();
    });
  });

  describe('filter forwarding', () => {
    it('passes the default all filter to the store', () => {
      render();
      expect(store.setFilter).toHaveBeenCalledWith('all');
    });

    it.each(['changes', 'attention'] as const)(
      'passes the %s filter to the store', filter => {
        render();
        store.setFilter.mockClear();
        fixture.componentRef.setInput('filter', filter);
        fixture.detectChanges();
        expect(store.setFilter).toHaveBeenCalledTimes(1);
        expect(store.setFilter).toHaveBeenCalledWith(filter);
      }
    );

    it('does not call setFilter again for unrelated input changes', () => {
      render();
      store.setFilter.mockClear();
      fixture.componentRef.setInput('entityId', 'CLIENT-456');
      fixture.detectChanges();
      expect(store.setFilter).not.toHaveBeenCalled();
    });
  });

  describe('countersChange output', () => {
    it('emits the current counters on initial change detection', () => {
      const emit = jest.fn();
      component.countersChange.subscribe(emit);
      render();
      expect(emit).toHaveBeenLastCalledWith(initialCounters());
    });

    it('emits the updated counters', () => {
      render();
      const emit = jest.fn();
      component.countersChange.subscribe(emit);
      const next: Counters = {
        total: 7,
        changes: 4,
        attention: 2,
        reviewed: 1,
        overridden: 1
      } as Counters;
      countersSignal.set(next);
      fixture.detectChanges();
      expect(emit).toHaveBeenCalledTimes(1);
      expect(emit).toHaveBeenCalledWith(next);
    });

    it('emits new values every time counters change', () => {
      render();
      const emit = jest.fn();
      component.countersChange.subscribe(emit);
      const first = { ...initialCounters(), total: 1 } as Counters;
      const second = { ...initialCounters(), total: 2 } as Counters;
      countersSignal.set(first);
      fixture.detectChanges();
      countersSignal.set(second);
      fixture.detectChanges();
      expect(emit).toHaveBeenCalledTimes(2);
      expect(emit).toHaveBeenNthCalledWith(1, first);
      expect(emit).toHaveBeenNthCalledWith(2, second);
    });
  });

  describe('changeChanges output', () => {
    it('emits the initial empty changes and removed entries with sectionId', () => {
      const emit = jest.fn();
      component.changeChanges.subscribe(emit);
      render();
      expect(emit).toHaveBeenLastCalledWith({
        sectionId: SECTION_ID,
        changes: changesSignal(),
        removedEntries: removedSignal()
      });
    });

    it('emits changes when an applied resolution changes', () => {
      render();
      const emit = jest.fn();
      component.changeChanges.subscribe(emit);
      const next = new Map([
        ['firstname', { source: 'core', value: 'Alice', reviewed: true }]
      ]) as unknown as Changes;
      changesSignal.set(next);
      fixture.detectChanges();
      expect(emit).toHaveBeenCalledTimes(1);
      expect(emit).toHaveBeenCalledWith({
        sectionId: SECTION_ID,
        changes: next,
        removedEntries: removedSignal()
      });
    });

    it('emits changes when removedEntries changes but resolutions do not', () => {
      render();
      const emit = jest.fn();
      component.changeChanges.subscribe(emit);
      const removed = [{ id: 'emails:old@example.com', value: 'old@example.com' }] as unknown as RemovedEntries;
      removedSignal.set(removed);
      fixture.detectChanges();
      expect(emit).toHaveBeenCalledTimes(1);
      expect(emit).toHaveBeenCalledWith({
        sectionId: SECTION_ID,
        changes: changesSignal(),
        removedEntries: removed
      });
    });

    it('emits the latest changes and removedEntries together', () => {
      render();
      const emit = jest.fn();
      component.changeChanges.subscribe(emit);
      const next = new Map([['lastname', { value: 'Doe' }]]) as unknown as Changes;
      const removed = [{ id: 'phone-numbers:1', value: '+352123456' }] as unknown as RemovedEntries;
      changesSignal.set(next);
      removedSignal.set(removed);
      fixture.detectChanges();
      expect(emit).toHaveBeenLastCalledWith({
        sectionId: SECTION_ID,
        changes: next,
        removedEntries: removed
      });
    });

    it('uses the updated sectionId when the parent switches sections', () => {
      render();
      const emit = jest.fn();
      component.changeChanges.subscribe(emit);
      fixture.componentRef.setInput('section', { ...section, id: 'contact-details' });
      fixture.detectChanges();
      expect(emit).toHaveBeenLastCalledWith({
        sectionId: 'contact-details',
        changes: changesSignal(),
        removedEntries: removedSignal()
      });
    });
  });

  describe('resolution actions', () => {
    beforeEach(() => render());

    it('passes a scalar entry resolution to store.apply unchanged', () => {
      const patch = { source: 'core', value: 'Alice', reviewed: true };
      actions().onApplyEntry({ id: 'firstname', payload: patch });
      expect(store.apply).toHaveBeenCalledTimes(1);
      expect(store.apply).toHaveBeenCalledWith('firstname', patch);
    });

    it('passes a list entry resolution to store.apply unchanged', () => {
      const patch = { source: 'manual', value: 'new@example.com' };
      actions().onApplyEntry({ id: 'emails:new@example.com', payload: patch });
      expect(store.apply).toHaveBeenCalledWith('emails:new@example.com', patch);
    });

    it('passes a structured child resolution to store.apply unchanged', () => {
      const patch = { source: 'portal', value: 'BE' };
      actions().onApplyChild({ id: 'nationalities:first:country', payload: patch });
      expect(store.apply).toHaveBeenCalledTimes(1);
      expect(store.apply).toHaveBeenCalledWith('nationalities:first:country', patch);
    });

    it('keeps consecutive resolution actions in their original order', () => {
      const first = { source: 'core', value: 'Alice' };
      const second = { source: 'clip', value: 'Belgium' };
      actions().onApplyEntry({ id: 'firstname', payload: first });
      actions().onApplyChild({ id: 'nationalities:first:country', payload: second });
      expect(store.apply).toHaveBeenNthCalledWith(1, 'firstname', first);
      expect(store.apply).toHaveBeenNthCalledWith(2, 'nationalities:first:country', second);
    });
  });

  describe('keyboard navigation', () => {
    beforeEach(() => render());

    it('moves focus to the next row on ArrowDown', () => {
      const event = keyEvent('ArrowDown');
      actions().onKeydown(event);
      expect(event.preventDefault).toHaveBeenCalledTimes(1);
      expect(store.moveFocus).toHaveBeenCalledTimes(1);
      expect(store.moveFocus).toHaveBeenCalledWith(1);
    });

    it('moves focus to the previous row on ArrowUp', () => {
      const event = keyEvent('ArrowUp');
      actions().onKeydown(event);
      expect(event.preventDefault).toHaveBeenCalledTimes(1);
      expect(store.moveFocus).toHaveBeenCalledWith(-1);
    });

    it.each(['Enter', 'e'])(
      'activates a focused row on %s', key => {
        focusedIdSignal.set('firstname');
        const event = keyEvent(key);
        actions().onKeydown(event);
        expect(event.preventDefault).toHaveBeenCalledTimes(1);
        expect(store.activate).toHaveBeenCalledTimes(1);
        expect(store.activate).toHaveBeenCalledWith('firstname');
      }
    );

    it.each(['Enter', 'e'])(
      'does not activate or preventDefault without a focused row on %s', key => {
        focusedIdSignal.set(null);
        const event = keyEvent(key);
        actions().onKeydown(event);
        expect(store.activate).not.toHaveBeenCalled();
        expect(event.preventDefault).not.toHaveBeenCalled();
      }
    );

    it('collapses an open row on Escape', () => {
      const event = keyEvent('Escape');
      actions().onKeydown(event);
      expect(store.collapse).toHaveBeenCalledTimes(1);
      expect(event.preventDefault).not.toHaveBeenCalled();
    });

    it('does not interfere with typing inside a text input', () => {
      focusedIdSignal.set('firstname');
      const target = document.createElement('input');
      const event = keyEvent('Enter', target);
      actions().onKeydown(event);
      expect(store.activate).not.toHaveBeenCalled();
      expect(store.moveFocus).not.toHaveBeenCalled();
      expect(store.collapse).not.toHaveBeenCalled();
      expect(event.preventDefault).not.toHaveBeenCalled();
    });

    it('does not intercept ArrowDown from a text input', () => {
      const event = keyEvent('ArrowDown', document.createElement('input'));
      actions().onKeydown(event);
      expect(store.moveFocus).not.toHaveBeenCalled();
      expect(event.preventDefault).not.toHaveBeenCalled();
    });

    it('does not collapse the panel when Escape is pressed in an input', () => {
      const event = keyEvent('Escape', document.createElement('input'));
      actions().onKeydown(event);
      expect(store.collapse).not.toHaveBeenCalled();
    });

    it.each(['Tab', ' ', 'a', 'ArrowLeft', 'ArrowRight'])(
      'ignores unrelated key %s', key => {
        focusedIdSignal.set('firstname');
        const event = keyEvent(key);
        actions().onKeydown(event);
        expect(store.moveFocus).not.toHaveBeenCalled();
        expect(store.activate).not.toHaveBeenCalled();
        expect(store.collapse).not.toHaveBeenCalled();
        expect(event.preventDefault).not.toHaveBeenCalled();
      }
    );
  });
});
