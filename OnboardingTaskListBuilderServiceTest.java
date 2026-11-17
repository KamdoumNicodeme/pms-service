
import {
  ComponentFixture,
  TestBed,
} from '@angular/core/testing';
import { signal } from '@angular/core';

import { ComparisonResult } from './comparison-result';

// Reuse the same injection token as the component.
import { COMPARISON_DATA_SERVICE } from '../../comparison-data.service';

describe('ComparisonResult', () => {
  let fixture: ComponentFixture<ComparisonResult>;
  let component: ComparisonResult;

  const italianTownLabels = signal([
    { value: '001', label: 'Rome' },
    { value: '002', label: 'Milan' },
  ]);

  const comparisonServiceMock = {
    getItalianBirthTownLabels: jest.fn(
      () => italianTownLabels
    ),
  };

  function setResolution(
    value: string | null,
    reviewed = false
  ): void {
    fixture.componentRef.setInput('resolution', {
      value,
      reviewed,
    });

    fixture.detectChanges();
  }

  async function render(
    value: string | null = 'BE',
    reviewed = false
  ): Promise<void> {
    fixture = TestBed.createComponent(ComparisonResult);
    component = fixture.componentInstance;

    setResolution(value, reviewed);
  }

  beforeEach(async () => {
    jest.clearAllMocks();

    await TestBed.configureTestingModule({
      imports: [ComparisonResult],
    })
      .overrideComponent(ComparisonResult, {
        set: {
          template: '',
          imports: [],
          providers: [
            {
              provide: COMPARISON_DATA_SERVICE,
              useValue: comparisonServiceMock,
            },
          ],
        },
      })
      .compileComponents();
  });

  describe('Initialization', () => {
    it('should create the component', async () => {
      await render();

      expect(component).toBeTruthy();
    });

    it('should initialize optional inputs', async () => {
      await render();

      expect(component.needsAttention()).toBe(false);
      expect(component.isOverride()).toBe(false);
      expect(component.editable()).toBe(true);
      expect(component.expanded()).toBe(false);
      expect(component.readOnly()).toBe(false);
      expect(component.emptyMeansDropped()).toBe(false);
      expect(component.kind()).toBe('text');
      expect(component.options()).toEqual([]);
      expect(component.fieldId()).toBe('');
    });
  });

  describe('Display value', () => {
    it('should return the resolved value when no options exist', async () => {
      await render('Belgium');

      expect(component.displayValue()).toBe('Belgium');
    });

    it('should return the matching option label', async () => {
      await render('BE');

      fixture.componentRef.setInput('options', [
        { value: 'BE', label: 'Belgium' },
        { value: 'FR', label: 'France' },
      ]);

      fixture.detectChanges();

      expect(component.displayValue()).toBe('Belgium');
    });

    it('should return the original value when no option matches', async () => {
      await render('LU');

      fixture.componentRef.setInput('options', [
        { value: 'BE', label: 'Belgium' },
      ]);

      fixture.detectChanges();

      expect(component.displayValue()).toBe('LU');
    });

    it('should return null for an empty resolution', async () => {
      await render(null);

      expect(component.displayValue()).toBeNull();
    });

    it('should use Italian town labels for the Italian birth town field', async () => {
      await render('001');

      fixture.componentRef.setInput(
        'fieldId',
        'italian-birth-town-code'
      );

      fixture.detectChanges();

      expect(
        comparisonServiceMock.getItalianBirthTownLabels
      ).toHaveBeenCalled();

      expect(component.displayValue()).toBe('Rome');
    });

    it('should fall back to the raw value for an unknown Italian town', async () => {
      await render('999');

      fixture.componentRef.setInput(
        'fieldId',
        'italian-birth-town-code'
      );

      fixture.detectChanges();

      expect(component.displayValue()).toBe('999');
    });

    it('should recompute the displayed value when resolution changes', async () => {
      await render('BE');

      fixture.componentRef.setInput('options', [
        { value: 'BE', label: 'Belgium' },
        { value: 'FR', label: 'France' },
      ]);

      fixture.detectChanges();

      expect(component.displayValue()).toBe('Belgium');

      setResolution('FR');

      expect(component.displayValue()).toBe('France');
    });

    it('should update when the Italian town labels change', async () => {
      await render('001');

      fixture.componentRef.setInput(
        'fieldId',
        'italian-birth-town-code'
      );

      fixture.detectChanges();

      expect(component.displayValue()).toBe('Rome');

      italianTownLabels.set([
        { value: '001', label: 'Roma' },
      ]);

      expect(component.displayValue()).toBe('Roma');

      italianTownLabels.set([
        { value: '001', label: 'Rome' },
        { value: '002', label: 'Milan' },
      ]);
    });
  });

  describe('Empty and dropped states', () => {
    it('should consider null as empty', async () => {
      await render(null);

      expect(component.isEmpty()).toBe(true);
    });

    it('should consider an empty string as empty', async () => {
      await render('');

      expect(component.isEmpty()).toBe(true);
    });

    it('should consider whitespace-only values as empty', async () => {
      await render('   ');

      expect(component.isEmpty()).toBe(true);
    });

    it('should not consider a populated value as empty', async () => {
      await render('Belgium');

      expect(component.isEmpty()).toBe(false);
    });

    it('should mark an empty list value as dropped when enabled', async () => {
      await render(null);

      fixture.componentRef.setInput(
        'emptyMeansDropped',
        true
      );

      fixture.detectChanges();

      expect(component.isDropped()).toBe(true);
    });

    it('should not mark an empty value as dropped by default', async () => {
      await render(null);

      expect(component.isDropped()).toBe(false);
    });

    it('should not mark a populated value as dropped', async () => {
      await render('BE');

      fixture.componentRef.setInput(
        'emptyMeansDropped',
        true
      );

      fixture.detectChanges();

      expect(component.isDropped()).toBe(false);
    });
  });

  describe('Pending review', () => {
    it('should be pending when attention is required and not reviewed', async () => {
      await render('BE', false);

      fixture.componentRef.setInput(
        'needsAttention',
        true
      );

      fixture.detectChanges();

      expect(component.pending()).toBe(true);
    });

    it('should not be pending when the resolution is reviewed', async () => {
      await render('BE', true);

      fixture.componentRef.setInput(
        'needsAttention',
        true
      );

      fixture.detectChanges();

      expect(component.pending()).toBe(false);
    });

    it('should not be pending when attention is not required', async () => {
      await render('BE', false);

      expect(component.pending()).toBe(false);
    });

    it('should recompute pending when the review state changes', async () => {
      await render('BE', false);

      fixture.componentRef.setInput(
        'needsAttention',
        true
      );

      fixture.detectChanges();

      expect(component.pending()).toBe(true);

      setResolution('BE', true);

      expect(component.pending()).toBe(false);
    });
  });
});
