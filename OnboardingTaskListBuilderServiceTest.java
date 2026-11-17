
import {
  ComponentFixture,
  TestBed,
} from '@angular/core/testing';

import { ComparisonToolbar } from './comparison-toolbar';

type Counters = ReturnType<ComparisonToolbar['counters']>;

describe('ComparisonToolbar', () => {
  let fixture: ComponentFixture<ComparisonToolbar>;
  let component: ComparisonToolbar;

  const makeCounters = (
    overrides: Partial<Counters> = {}
  ): Counters => ({
    total: 10,
    changes: 5,
    attention: 3,
    reviewed: 2,
    overridden: 1,
    ...overrides,
  });

  async function render(
    counters: Counters = makeCounters()
  ): Promise<void> {
    fixture = TestBed.createComponent(ComparisonToolbar);
    component = fixture.componentInstance;

    fixture.componentRef.setInput('counters', counters);
    fixture.componentRef.setInput('filter', 'all');

    fixture.detectChanges();
    await fixture.whenStable();
  }

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ComparisonToolbar],
    })
      .overrideComponent(ComparisonToolbar, {
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

    it('should initialize the filter input', async () => {
      await render();

      expect(component.filter()).toBe('all');
    });
  });

  describe('Filter chips', () => {
    it('should create the three filter chips', async () => {
      await render();

      expect(component.chips()).toHaveLength(3);
    });

    it('should return the expected labels and counts', async () => {
      await render();

      expect(component.chips()).toEqual([
        {
          value: 'all',
          label: 'All',
          count: 10,
        },
        {
          value: 'changes',
          label: 'Changes',
          count: 5,
        },
        {
          value: 'attention',
          label: 'Needs review',
          count: 3,
        },
      ]);
    });

    it('should handle zero counters', async () => {
      await render(
        makeCounters({
          total: 0,
          changes: 0,
          attention: 0,
        })
      );

      expect(component.chips()).toEqual([
        {
          value: 'all',
          label: 'All',
          count: 0,
        },
        {
          value: 'changes',
          label: 'Changes',
          count: 0,
        },
        {
          value: 'attention',
          label: 'Needs review',
          count: 0,
        },
      ]);
    });

    it('should update chips when counters change', async () => {
      await render();

      expect(component.chips()[0].count).toBe(10);

      fixture.componentRef.setInput(
        'counters',
        makeCounters({
          total: 20,
          changes: 8,
          attention: 4,
        })
      );

      fixture.detectChanges();

      expect(component.chips()).toEqual([
        {
          value: 'all',
          label: 'All',
          count: 20,
        },
        {
          value: 'changes',
          label: 'Changes',
          count: 8,
        },
        {
          value: 'attention',
          label: 'Needs review',
          count: 4,
        },
      ]);
    });

    it('should keep the correct chip order', async () => {
      await render();

      expect(
        component.chips().map(chip => chip.value)
      ).toEqual([
        'all',
        'changes',
        'attention',
      ]);
    });

    it('should not change chip counts when only the selected filter changes', async () => {
      await render();

      const initialChips = component.chips();

      fixture.componentRef.setInput(
        'filter',
        'attention'
      );

      fixture.detectChanges();

      expect(component.filter()).toBe('attention');
      expect(component.chips()).toEqual(initialChips);
    });
  });
});
