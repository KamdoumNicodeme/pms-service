
import {
  ComponentFixture,
  TestBed,
} from '@angular/core/testing';

import { Summary } from './summary';

type CaseFact = ReturnType<Summary['facts']>[number];

describe('Summary', () => {
  let component: Summary;
  let fixture: ComponentFixture<Summary>;

  const facts = [
    {
      label: 'Policy number',
      value: 'POLICY_001',
    },
    {
      label: 'Case owner',
      value: 'John Doe',
    },
    {
      label: 'Broker',
      value: 'BROKER_001',
    },
  ] as unknown as readonly CaseFact[];

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Summary],
    })
      .overrideComponent(Summary, {
        set: {
          template: '',
          imports: [],
        },
      })
      .compileComponents();

    fixture = TestBed.createComponent(Summary);
    component = fixture.componentInstance;

    fixture.detectChanges();
  });

  describe('Initialization', () => {
    it('should create the component', () => {
      expect(component).toBeTruthy();
    });

    it('should initialize facts with an empty array', () => {
      expect(component.facts()).toEqual([]);
    });
  });

  describe('Facts input', () => {
    it('should receive the provided case facts', () => {
      fixture.componentRef.setInput('facts', facts);
      fixture.detectChanges();

      expect(component.facts()).toEqual(facts);
      expect(component.facts()).toHaveLength(3);
    });

    it('should update facts when the input changes', () => {
      fixture.componentRef.setInput('facts', facts);
      fixture.detectChanges();

      const updatedFacts = [
        {
          label: 'Policy number',
          value: 'POLICY_002',
        },
      ] as unknown as readonly CaseFact[];

      fixture.componentRef.setInput(
        'facts',
        updatedFacts
      );

      fixture.detectChanges();

      expect(component.facts()).toEqual(updatedFacts);
      expect(component.facts()).toHaveLength(1);
    });

    it('should handle an empty facts array after receiving data', () => {
      fixture.componentRef.setInput('facts', facts);
      fixture.detectChanges();

      fixture.componentRef.setInput('facts', []);
      fixture.detectChanges();

      expect(component.facts()).toEqual([]);
    });

    it('should preserve the provided facts without modifying them', () => {
      fixture.componentRef.setInput('facts', facts);
      fixture.detectChanges();

      expect(component.facts()).toBe(facts);
    });
  });
});
