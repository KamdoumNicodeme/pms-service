
import {
  ComponentFixture,
  TestBed,
} from '@angular/core/testing';

import { ComparisonEntryRowComponent } from './comparison-entry-row';

type Entry = ReturnType<ComparisonEntryRowComponent['entry']>;

describe('ComparisonEntryRowComponent', () => {
  let fixture: ComponentFixture<ComparisonEntryRowComponent>;
  let component: ComparisonEntryRowComponent;

  const makeEntry = (overrides: Partial<Entry> = {}): Entry =>
    ({
      id: 'entry-1',
      key: 'entry-1',
      label: 'Belgium',
      status: 'aligned',
      isManual: false,
      isOverride: false,
      values: {
        snapshot: 'BE',
        portal: 'BE',
        core: 'BE',
        clip: 'BE',
      },
      resolution: {
        source: 'core',
        value: 'BE',
        reason: '',
        comment: '',
      },
      children: [],
      ...overrides,
    }) as Entry;

  async function render(
    entry: Entry = makeEntry()
  ): Promise<void> {
    fixture = TestBed.createComponent(
      ComparisonEntryRowComponent
    );

    component = fixture.componentInstance;
    fixture.componentRef.setInput('entry', entry);
    fixture.detectChanges();

    await fixture.whenStable();
  }

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ComparisonEntryRowComponent],
    })
      .overrideComponent(ComparisonEntryRowComponent, {
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

      expect(component.expanded()).toBe(false);
      expect(component.focused()).toBe(false);
      expect(component.kind()).toBe('text');
      expect(component.expandedId()).toBeNull();
      expect(component.focusedId()).toBeNull();
      expect(component.options()).toEqual([]);
      expect(component.readOnly()).toBe(false);
    });
  });

  describe('Chip label', () => {
    it.each([
      ['added', 'added'],
      ['removed', 'removed'],
      ['drift', 'conflict'],
      ['conflict', 'conflict'],
      ['updated', 'changed'],
      ['aligned', ''],
    ] as const)(
      'should return %s status as %s',
      async (status, expected) => {
        await render(makeEntry({ status }));

        expect(component.chipLabel()).toBe(expected);
      }
    );

    it('should return manual for a manual entry', async () => {
      await render(
        makeEntry({
          isManual: true,
          status: 'added',
        })
      );

      expect(component.chipLabel()).toBe('manual');
    });
  });

  describe('Chip icon', () => {
    it.each([
      ['added', 'plus'],
      ['removed', 'minus'],
      ['updated', 'swap'],
      ['conflict', 'warning'],
      ['drift', 'warning'],
    ] as const)(
      'should return %s icon as %s',
      async (status, expected) => {
        await render(makeEntry({ status }));

        expect(component.chipIcon()).toBe(expected);
      }
    );

    it('should return edit icon for a manual entry', async () => {
      await render(
        makeEntry({
          isManual: true,
        })
      );

      expect(component.chipIcon()).toBe('edit');
    });
  });

  describe('Entry display label', () => {
    it('should display the entry label by default', async () => {
      await render(makeEntry({ label: 'Nationality' }));

      expect(component.entryDisplayLabel()).toBe(
        'Nationality'
      );
    });

    it('should display resolution value for a manual entry', async () => {
      await render(
        makeEntry({
          isManual: true,
          resolution: {
            source: 'manual',
            value: 'Belgium',
            reason: '',
            comment: '',
          },
        })
      );

      expect(component.entryDisplayLabel()).toBe('Belgium');
    });

    it('should display the resolution value for an override', async () => {
      await render(
        makeEntry({
          isOverride: true,
          resolution: {
            source: 'manual',
            value: 'France',
            reason: '',
            comment: '',
          },
        })
      );

      expect(component.entryDisplayLabel()).toBe('France');
    });

    it('should map the resolution value to its option label', async () => {
      await render(
        makeEntry({
          isManual: true,
          resolution: {
            source: 'manual',
            value: 'BE',
            reason: '',
            comment: '',
          },
        })
      );

      fixture.componentRef.setInput('options', [
        { value: 'BE', label: 'Belgium' },
        { value: 'FR', label: 'France' },
      ]);

      fixture.detectChanges();

      expect(component.entryDisplayLabel()).toBe('Belgium');
    });

    it('should return an empty label for a manual entry without value', async () => {
      await render(
        makeEntry({
          isManual: true,
          resolution: {
            source: 'manual',
            value: null,
            reason: '',
            comment: '',
          },
        })
      );

      expect(component.entryDisplayLabel()).toBe('');
    });
  });

  describe('Display value', () => {
    it('should return an empty string for null', async () => {
      await render();

      expect(component.displayValue(null)).toBe('');
    });

    it('should return the option label when available', async () => {
      await render();

      fixture.componentRef.setInput('options', [
        { value: 'BE', label: 'Belgium' },
      ]);

      fixture.detectChanges();

      expect(component.displayValue('BE')).toBe('Belgium');
    });

    it('should return the original value when no option matches', async () => {
      await render();

      expect(component.displayValue('UNKNOWN')).toBe(
        'UNKNOWN'
      );
    });
  });

  describe('Remove action', () => {
    it('should prevent event propagation and emit remove', async () => {
      await render();

      const removeSpy = jest.fn();
      component.remove.subscribe(removeSpy);

      const event = new MouseEvent('click', {
        bubbles: true,
        cancelable: true,
      });

      const preventDefaultSpy = jest.spyOn(
        event,
        'preventDefault'
      );

      const stopPropagationSpy = jest.spyOn(
        event,
        'stopPropagation'
      );

      component.onRemove(event);

      expect(preventDefaultSpy).toHaveBeenCalledTimes(1);
      expect(stopPropagationSpy).toHaveBeenCalledTimes(1);
      expect(removeSpy).toHaveBeenCalledTimes(1);
    });
  });

  describe('Click behaviour', () => {
    it('should not toggle a row containing children', async () => {
      await render(
        makeEntry({
          children: [
            {
              key: 'child-1',
              label: 'Country',
              status: 'aligned',
            },
          ] as Entry['children'],
        })
      );

      const toggleSpy = jest.fn();
      component.toggle.subscribe(toggleSpy);

      component.onClick(new MouseEvent('click'));

      expect(toggleSpy).not.toHaveBeenCalled();
    });
  });

  describe('Reactive inputs', () => {
    it('should recompute chip label when entry changes', async () => {
      await render(makeEntry({ status: 'added' }));

      expect(component.chipLabel()).toBe('added');

      fixture.componentRef.setInput(
        'entry',
        makeEntry({ status: 'removed' })
      );

      fixture.detectChanges();

      expect(component.chipLabel()).toBe('removed');
    });
  });
});
