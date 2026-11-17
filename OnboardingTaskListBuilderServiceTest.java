
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ComparisonAnnotations } from './comparison-annotations';

type Annotation = ReturnType<
  ComparisonAnnotations['annotations']
>[number];

describe('ComparisonAnnotations', () => {
  let fixture: ComponentFixture<ComparisonAnnotations>;
  let component: ComparisonAnnotations;

  const createAnnotation = (
    overrides: Partial<Annotation> = {}
  ): Annotation =>
    ({
      source: 'core',
      label: 'Core system',
      tone: 'warn',
      value: 'John Doe',
      ...overrides,
    }) as Annotation;

  function render(
    annotations: readonly Annotation[] = []
  ): void {
    fixture = TestBed.createComponent(ComparisonAnnotations);
    component = fixture.componentInstance;

    fixture.componentRef.setInput('annotations', annotations);
    fixture.detectChanges();
  }

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ComparisonAnnotations],
    }).compileComponents();
  });

  describe('Initialization', () => {
    it('should create the component', () => {
      render();

      expect(component).toBeTruthy();
    });

    it('should not display annotations when the list is empty', () => {
      render([]);

      const list = fixture.nativeElement.querySelector('ul');

      expect(list).toBeNull();
    });
  });

  describe('Annotation rendering', () => {
    it('should display a warning annotation', () => {
      render([createAnnotation()]);

      const item: HTMLElement =
        fixture.nativeElement.querySelector('li');

      expect(item).toBeTruthy();
      expect(item.getAttribute('data-tone')).toBe('warn');
      expect(item.getAttribute('data-source')).toBe('core');
      expect(item.textContent).toContain('Core system');
      expect(item.textContent).toContain('John Doe');
    });

    it('should display an update annotation', () => {
      render([
        createAnnotation({
          source: 'portal',
          label: 'Connect data',
          tone: 'update',
          value: 'Jane Doe',
        }),
      ]);

      const item: HTMLElement =
        fixture.nativeElement.querySelector('li');

      expect(item.getAttribute('data-tone')).toBe('update');
      expect(item.textContent).toContain('Jane Doe');
    });

    it('should not display muted annotations', () => {
      render([
        createAnnotation({ tone: 'muted' }),
      ]);

      expect(
        fixture.nativeElement.querySelectorAll('li')
      ).toHaveLength(0);
    });

    it('should display only non-muted annotations', () => {
      render([
        createAnnotation({
          source: 'core',
          tone: 'warn',
        }),
        createAnnotation({
          source: 'portal',
          tone: 'muted',
        }),
        createAnnotation({
          source: 'manual',
          tone: 'update',
        }),
      ]);

      const items = fixture.nativeElement.querySelectorAll('li');

      expect(items).toHaveLength(2);
      expect(items[0].getAttribute('data-source')).toBe('core');
      expect(items[1].getAttribute('data-source')).toBe('manual');
    });

    it('should display "no value" when annotation value is null', () => {
      render([
        createAnnotation({ value: null }),
      ]);

      expect(fixture.nativeElement.textContent).toContain(
        'no value'
      );
    });

    it('should display "no value" for whitespace-only values', () => {
      render([
        createAnnotation({ value: '   ' }),
      ]);

      expect(fixture.nativeElement.textContent).toContain(
        'no value'
      );
    });

    it('should update the rendered annotations when input changes', () => {
      render([createAnnotation()]);

      fixture.componentRef.setInput('annotations', [
        createAnnotation({
          source: 'portal',
          label: 'Connect data',
          value: 'Updated value',
        }),
      ]);

      fixture.detectChanges();

      expect(fixture.nativeElement.textContent).toContain(
        'Updated value'
      );
      expect(fixture.nativeElement.textContent).not.toContain(
        'John Doe'
      );
    });
  });

  describe('isBlank', () => {
    it('should return true for null', () => {
      render();

      expect(component.isBlank(null)).toBe(true);
    });

    it('should return true for an empty string', () => {
      render();

      expect(component.isBlank('')).toBe(true);
    });

    it('should return true for whitespace-only strings', () => {
      render();

      expect(component.isBlank('   ')).toBe(true);
    });

    it('should return false for a non-empty string', () => {
      render();

      expect(component.isBlank('John Doe')).toBe(false);
    });

    it('should return false for the string zero', () => {
      render();

      expect(component.isBlank('0')).toBe(false);
    });
  });
});




import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ComparisonCell } from './comparison-cell';

describe('ComparisonCell', () => {
  let fixture: ComponentFixture<ComparisonCell>;
  let component: ComparisonCell;

  function render(
    value: string | null = null,
    emphasis: 'muted' | 'strong' = 'muted',
    marker = ''
  ): void {
    fixture = TestBed.createComponent(ComparisonCell);
    component = fixture.componentInstance;

    fixture.componentRef.setInput('value', value);
    fixture.componentRef.setInput('emphasis', emphasis);
    fixture.componentRef.setInput('marker', marker);

    fixture.detectChanges();
  }

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ComparisonCell],
    }).compileComponents();
  });

  describe('Initialization', () => {
    it('should create the component', () => {
      render();

      expect(component).toBeTruthy();
    });

    it('should initialize emphasis with muted by default', () => {
      render();

      expect(component.emphasis()).toBe('muted');
    });

    it('should initialize marker with an empty string', () => {
      render();

      expect(component.marker()).toBe('');
    });
  });

  describe('Value rendering', () => {
    it('should display the provided value', () => {
      render('John Doe');

      const value: HTMLElement =
        fixture.nativeElement.querySelector('.cell__value');

      expect(value.textContent?.trim()).toBe('John Doe');
    });

    it('should display a placeholder for null', () => {
      render(null);

      expect(component.isEmpty()).toBe(true);

      expect(
        fixture.nativeElement.querySelector('.cell__placeholder')
      ).toBeTruthy();

      expect(
        fixture.nativeElement.querySelector('.cell__value')
      ).toBeNull();
    });

    it('should consider an empty string as empty', () => {
      render('');

      expect(component.isEmpty()).toBe(true);
    });

    it('should consider whitespace-only values as empty', () => {
      render('   ');

      expect(component.isEmpty()).toBe(true);
    });

    it('should not consider zero as empty', () => {
      render('0');

      expect(component.isEmpty()).toBe(false);

      expect(fixture.nativeElement.textContent).toContain('0');
    });

    it('should display the full value in the title attribute', () => {
      render('A very long comparison value');

      const value: HTMLElement =
        fixture.nativeElement.querySelector('.cell__value');

      expect(value.getAttribute('title')).toBe(
        'A very long comparison value'
      );
    });
  });

  describe('Emphasis', () => {
    it('should apply strong styling when emphasis is strong', () => {
      render('John Doe', 'strong');

      const cell: HTMLElement =
        fixture.nativeElement.querySelector('.cell');

      expect(cell.classList.contains('cell--strong')).toBe(true);
    });

    it('should not apply strong styling when emphasis is muted', () => {
      render('John Doe', 'muted');

      const cell: HTMLElement =
        fixture.nativeElement.querySelector('.cell');

      expect(cell.classList.contains('cell--strong')).toBe(false);
    });

    it('should apply empty styling for null values', () => {
      render(null);

      const cell: HTMLElement =
        fixture.nativeElement.querySelector('.cell');

      expect(cell.classList.contains('cell--empty')).toBe(true);
    });
  });

  describe('Marker', () => {
    it('should display the marker when a tooltip is provided', () => {
      render('John Doe', 'muted', 'Original value');

      const marker = fixture.nativeElement.querySelector(
        '.cell__marker'
      );

      expect(marker).toBeTruthy();
    });

    it('should not display the marker when tooltip is empty', () => {
      render('John Doe', 'muted', '');

      const marker = fixture.nativeElement.querySelector(
        '.cell__marker'
      );

      expect(marker).toBeNull();
    });
  });

  describe('Reactive inputs', () => {
    it('should update the displayed value when input changes', () => {
      render('John Doe');

      fixture.componentRef.setInput('value', 'Jane Doe');
      fixture.detectChanges();

      expect(fixture.nativeElement.textContent).toContain(
        'Jane Doe'
      );

      expect(fixture.nativeElement.textContent).not.toContain(
        'John Doe'
      );
    });

    it('should update isEmpty when value changes to null', () => {
      render('John Doe');

      expect(component.isEmpty()).toBe(false);

      fixture.componentRef.setInput('value', null);
      fixture.detectChanges();

      expect(component.isEmpty()).toBe(true);
    });

    it('should update emphasis dynamically', () => {
      render('John Doe', 'muted');

      fixture.componentRef.setInput('emphasis', 'strong');
      fixture.detectChanges();

      const cell: HTMLElement =
        fixture.nativeElement.querySelector('.cell');

      expect(cell.classList.contains('cell--strong')).toBe(true);
    });
  });
});

