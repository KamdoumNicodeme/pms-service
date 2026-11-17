
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ProfilingPanel } from './profiling-panel';

type Counters = NonNullable<
  ReturnType<ProfilingPanel['counters']>
>;

describe('ProfilingPanel', () => {
  let component: ProfilingPanel;
  let fixture: ComponentFixture<ProfilingPanel>;
  let host: HTMLElement;

  const counters: Counters = {
    total: 5,
    changes: 3,
    attention: 2,
    reviewed: 0,
    overridden: 0,
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProfilingPanel],
    })
      .overrideComponent(ProfilingPanel, {
        set: {
          template: '',
          imports: [],
        },
      })
      .compileComponents();

    fixture = TestBed.createComponent(ProfilingPanel);
    component = fixture.componentInstance;
    host = fixture.nativeElement as HTMLElement;

    fixture.componentRef.setInput(
      'title',
      'Contact details'
    );

    fixture.detectChanges();
  });

  describe('Initialization', () => {
    it('should create the component', () => {
      expect(component).toBeTruthy();
    });

    it('should initialize title correctly', () => {
      expect(component.title()).toBe('Contact details');
    });

    it('should initialize open to false', () => {
      expect(component.open()).toBe(false);
    });

    it('should initialize counters to null', () => {
      expect(component.counters()).toBeNull();
    });

    it('should return clean state when counters are null', () => {
      expect(component.state()).toBe('clean');
    });

    it('should return an empty label when counters are null', () => {
      expect(component.label()).toBe('');
    });
  });

  describe('Inputs', () => {
    it('should update title when input changes', () => {
      fixture.componentRef.setInput(
        'title',
        'Tax information'
      );

      fixture.detectChanges();

      expect(component.title()).toBe('Tax information');
    });

    it('should update open when input changes', () => {
      fixture.componentRef.setInput('open', true);

      fixture.detectChanges();

      expect(component.open()).toBe(true);
    });

    it('should update counters when input changes', () => {
      fixture.componentRef.setInput('counters', counters);

      fixture.detectChanges();

      expect(component.counters()).toEqual(counters);
    });
  });

  describe('Computed properties', () => {
    it('should return pending state when attention is required', () => {
      fixture.componentRef.setInput('counters', counters);

      fixture.detectChanges();

      expect(component.state()).toBe('pending');
    });

    it('should compute a label when counters are provided', () => {
      fixture.componentRef.setInput('counters', counters);

      fixture.detectChanges();

      expect(component.label()).not.toBe('');
    });

    it('should reset state and label when counters become null', () => {
      fixture.componentRef.setInput('counters', counters);
      fixture.detectChanges();

      fixture.componentRef.setInput('counters', null);
      fixture.detectChanges();

      expect(component.state()).toBe('clean');
      expect(component.label()).toBe('');
    });
  });

  describe('Host bindings', () => {
    it('should always apply the panel class', () => {
      expect(host.classList.contains('panel')).toBe(true);
    });

    it('should not apply is-open class when closed', () => {
      expect(host.classList.contains('is-open')).toBe(false);
    });

    it('should apply is-open class when open is true', () => {
      fixture.componentRef.setInput('open', true);

      fixture.detectChanges();

      expect(host.classList.contains('is-open')).toBe(true);
    });

    it('should remove is-open class when panel closes', () => {
      fixture.componentRef.setInput('open', true);
      fixture.detectChanges();

      fixture.componentRef.setInput('open', false);
      fixture.detectChanges();

      expect(host.classList.contains('is-open')).toBe(false);
    });

    it('should set data-state to clean when counters are null', () => {
      expect(host.getAttribute('data-state')).toBe('clean');
    });

    it('should update data-state when counters change', () => {
      fixture.componentRef.setInput('counters', counters);

      fixture.detectChanges();

      expect(host.getAttribute('data-state')).toBe('pending');
    });

    it('should reset data-state when counters are removed', () => {
      fixture.componentRef.setInput('counters', counters);
      fixture.detectChanges();

      fixture.componentRef.setInput('counters', null);
      fixture.detectChanges();

      expect(host.getAttribute('data-state')).toBe('clean');
    });
  });
});
