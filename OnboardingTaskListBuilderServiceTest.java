import { ComponentFixture, TestBed } from '@angular/core/testing';
import { signal } from '@angular/core';

import { DataComparison } from './data-comparison.component';
import { ComparisonStore } from './comparison.store';

describe('DataComparison', () => {
  type Section = ReturnType<DataComparison['section']>;
  type Reasons = ReturnType<DataComparison['changeReasons']>;
  type Filter = ReturnType<DataComparison['filter']>;
  type Counters = ReturnType<ComparisonStore['counters']>;
  type Changes = ReturnType<ComparisonStore['appliedChanges']>;
  type RemovedEntries = ReturnType<ComparisonStore['removedEntries']>;

  let fixture: ComponentFixture<DataComparison>;
  let component: DataComparison;

  const section = { id: 'physical-person' } as Section;
  const reasons = {} as Reasons;

  let countersSignal: ReturnType<typeof signal<Counters>>;
  let appliedChangesSignal: ReturnType<typeof signal<Changes>>;
  let removedEntriesSignal: ReturnType<typeof signal<RemovedEntries>>;
  let focusedIdSignal: ReturnType<typeof signal<string | null>>;

  let storeMock: {
    initialize: jest.Mock;
    setFilter: jest.Mock;
    counters: typeof countersSignal;
    appliedChanges: typeof appliedChangesSignal;
    removedEntries: typeof removedEntriesSignal;
    focusedId: typeof focusedIdSignal;
    apply: jest.Mock;
    moveFocus: jest.Mock;
    activate: jest.Mock;
    collapse: jest.Mock;
  };

  beforeEach(async () => {
    countersSignal = signal({} as Counters);
    appliedChangesSignal = signal(new Map() as Changes);
    removedEntriesSignal = signal([] as unknown as RemovedEntries);
    focusedIdSignal = signal<string | null>(null);

    storeMock = {
      initialize: jest.fn(),
      setFilter: jest.fn(),
      counters: countersSignal,
      appliedChanges: appliedChangesSignal,
      removedEntries: removedEntriesSignal,
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
          providers: [{ provide: ComparisonStore, useValue: storeMock }]
        }
      })
      .compileComponents();

    fixture = TestBed.createComponent(DataComparison);
    component = fixture.componentInstance;
  });

  function render(entityId: string | null = 'CLIENT-123'): void {
    fixture.componentRef.setInput('section', section);
    fixture.componentRef.setInput('changeReasons', reasons);
    fixture.componentRef.setInput('entityId', entityId);
    fixture.detectChanges();
  }

  // The component exposes these methods to its template as protected methods.
  // This test-only facade lets us call them without altering production code.
  type ProtectedActions = {
    onApplyEntry: (action: { id: string; payload: unknown }) => void;
    onApplyChild: (action: { id: string; payload: unknown }) => void;
    onKeydown: (event: KeyboardEvent) => void;
  };

  function actions(): ProtectedActions {
    return component as unknown as ProtectedActions;
  }

  function keyboardEvent(
    key: string,
    target: EventTarget = document.createElement('div')
  ): KeyboardEvent & { preventDefault: jest.Mock } {
