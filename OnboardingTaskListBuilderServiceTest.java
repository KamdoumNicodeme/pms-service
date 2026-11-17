
import {
  ComponentFixture,
  TestBed,
} from '@angular/core/testing';

import { ComparisonGroupField } from './comparison-group-field';

type Row = ReturnType<ComparisonGroupField['row']>;

describe('ComparisonGroupField', () => {
  let fixture: ComponentFixture<ComparisonGroupField>;
  let component: ComparisonGroupField;

  const makeRow = (overrides: Partial<Row> = {}): Row =>
    ({
      id: 'group-1',
      key: 'group-1',
      label: 'Address',
      status: 'aligned',
      children: [],
      composed: {
        snapshot: [],
        portal: [],
        core: [],
        clip: [],
      },
      composedResult: [],
      ...overrides,
    }) as Row;

  async function render(
    row: Row = makeRow()
  ): Promise<void> {
    fixture = TestBed.createComponent(ComparisonGroupField);
    component = fixture.componentInstance;

    fixture.componentRef.setInput('row', row);
    fixture.detectChanges();

    await fixture.whenStable();
  }

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ComparisonGroupField],
    })
      .overrideComponent(ComparisonGroupField, {
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

    it('should initialize optional inputs with defaults', async () => {
      await render();

      expect(component.open()).toBe(false);
      expect(component.expandedId()).toBeNull();
      expect(component.focusedId()).toBeNull();
      expect(component.readOnly()).toBe(false);
    });
  });

  describe('Change count', () => {
    it('should return zero when there are no children', async () => {
      await render(makeRow({ children: [] }));

      expect(component.changeCount()).toBe(0);
    });

    it('should return zero when all children are aligned', async () => {
      await render(
        makeRow({
          children: [
            { status: 'aligned' },
            { status: 'aligned' },
          ] as Row['children'],
        })
      );

      expect(component.changeCount()).toBe(0);
    });

    it('should count children whose status is not aligned', async () => {
      await render(
        makeRow({
          children: [
            { status: 'aligned' },
            { status: 'added' },
            { status: 'removed' },
            { status: 'conflict' },
          ] as Row['children'],
        })
      );

      expect(component.changeCount()).toBe(3);
    });

    it('should count updated and drifted children', async () => {
      await render(
        makeRow({
          children: [
            { status: 'updated' },
            { status: 'drift' },
            { status: 'aligned' },
          ] as Row['children'],
        })
      );

      expect(component.changeCount()).toBe(2);
    });

    it('should recompute the count when row changes', async () => {
      await render(
        makeRow({
          children: [
            { status: 'aligned' },
          ] as Row['children'],
        })
      );

      expect(component.changeCount()).toBe(0);

      fixture.componentRef.setInput(
        'row',
        makeRow({
          children: [
            { status: 'added' },
            { status: 'removed' },
          ] as Row['children'],
        })
      );

      fixture.detectChanges();

      expect(component.changeCount()).toBe(2);
    });
  });

  describe('Status', () => {
    it('should compute status text from the row status', async () => {
      await render(
        makeRow({
          status: 'conflict',
        })
      );

      expect(component.statusText()).toBe(
        statusLabel('conflict')
      );
    });

    it('should update status text when status changes', async () => {
      await render(
        makeRow({ status: 'added' })
      );

      fixture.componentRef.setInput(
        'row',
        makeRow({ status: 'removed' })
      );

      fixture.detectChanges();

      expect(component.statusText()).toBe(
        statusLabel('removed')
      );
    });
  });

  describe('Inputs', () => {
    it('should update open state', async () => {
      await render();

      fixture.componentRef.setInput('open', true);
      fixture.detectChanges();

      expect(component.open()).toBe(true);
    });

    it('should update expanded and focused identifiers', async () => {
      await render();

      fixture.componentRef.setInput(
        'expandedId',
        'child-1'
      );

      fixture.componentRef.setInput(
        'focusedId',
        'child-2'
      );

      fixture.detectChanges();

      expect(component.expandedId()).toBe('child-1');
      expect(component.focusedId()).toBe('child-2');
    });

    it('should update readOnly state', async () => {
      await render();

      fixture.componentRef.setInput('readOnly', true);
      fixture.detectChanges();

      expect(component.readOnly()).toBe(true);
    });
  });
});
