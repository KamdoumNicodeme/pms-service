
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ProfilingNav } from './profiling-nav';

type NavSection = Parameters<ProfilingNav['icon']>[0];

describe('ProfilingNav', () => {
  let component: ProfilingNav;
  let fixture: ComponentFixture<ProfilingNav>;

  const makeSection = (
    state: NavSection['state']
  ): NavSection => ({
    id: 'contact-details',
    title: 'Contact details',
    state,
    pending: 0,
  });

  const sections: NavSection[] = [
    makeSection('loading'),
    {
      ...makeSection('pending'),
      id: 'tax-information',
      title: 'Tax information',
      pending: 2,
    },
  ];

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProfilingNav],
    })
      .overrideComponent(ProfilingNav, {
        set: {
          template: '',
          imports: [],
        },
      })
      .compileComponents();

    fixture = TestBed.createComponent(ProfilingNav);
    component = fixture.componentInstance;

    fixture.componentRef.setInput('sections', sections);
    fixture.detectChanges();
  });

  describe('Initialization', () => {
    it('should create the component', () => {
      expect(component).toBeTruthy();
    });

    it('should initialize activeId to null', () => {
      expect(component.activeId()).toBeNull();
    });

    it('should receive the sections input', () => {
      expect(component.sections()).toEqual(sections);
    });

    it('should update activeId when input changes', () => {
      fixture.componentRef.setInput(
        'activeId',
        'tax-information'
      );

      fixture.detectChanges();

      expect(component.activeId()).toBe('tax-information');
    });

    it('should update sections when input changes', () => {
      const updatedSections = [
        makeSection('pending'),
      ];

      fixture.componentRef.setInput(
        'sections',
        updatedSections
      );

      fixture.detectChanges();

      expect(component.sections()).toEqual(updatedSections);
    });
  });

  describe('icon', () => {
    it('should return loading icon when section is loading', () => {
      const section = makeSection('loading');

      expect(component.icon(section)).toBe('loading');
    });

    it('should return exclamation-circle for a pending section', () => {
      const section = makeSection('pending');

      expect(component.icon(section)).toBe(
        'exclamation-circle'
      );
    });

    it('should return check-circle for other states', () => {
      const section = makeSection(
        'reviewed' as NavSection['state']
      );

      expect(component.icon(section)).toBe('check-circle');
    });
  });
});
