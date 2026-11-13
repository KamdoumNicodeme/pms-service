import { ComponentFixture, TestBed } from '@angular/core/testing';
import { signal } from '@angular/core';
import { of } from 'rxjs';

import { ComparisonResolution } from './comparison-resolution.component';

// Adapt these two paths to the imports used by comparison-resolution.component.ts.
import { COMPARISON_DATA_SERVICE } from './comparison-data.service';
import { ReferenceService } from '../../../core/providers/services/reference.service';

describe('ComparisonResolution', () => {
  type Kind = ReturnType<ComparisonResolution['kind']>;
  type Values = ReturnType<ComparisonResolution['values']>;
  type Status = ReturnType<ComparisonResolution['status']>;
  type Resolution = ReturnType<ComparisonResolution['resolution']>;
  type Options = ReturnType<ComparisonResolution['options']>;
  type Source = Resolution['source'];
  type AvailableSources = NonNullable<ReturnType<ComparisonResolution['availableSources']>>;

  let fixture: ComponentFixture<ComparisonResolution>;
  let component: ComparisonResolution;

  let italianLabels: ReturnType<typeof signal<Array<{ value: string; label: string }>>>;
  let comparisonDataMock: {
    getReasonOptions: jest.Mock;
    getItalianBirthTownLabels: jest.Mock;
    addItalianBirthTownOptions: jest.Mock;
    setItalianBirthTownCode: jest.Mock;
  };
  let referenceServiceMock: {
    searchReferences: jest.Mock;
  };

  const baseValues = {
    portal: 'Portal value',
    core: 'Core value',
    clip: 'Clip value'
  };

  function makeResolution(
    source: Source = 'portal' as Source,
    value: string | null = 'Portal value',
    reason = '',
    comment = ''
  ): Resolution {
    return { source, value, reason, comment } as Resolution;
  }

  type Overrides = {
    fieldId?: string;
    kind?: Kind;
    options?: Options;
    values?: Values;
    status?: Status;
    resolution?: Resolution;
    fallback?: Resolution;
    readOnly?: boolean;
    isOverride?: boolean;
    structuredError?: string | null;
    availableSources?: AvailableSources | undefined;
  };

  // All input.required() fields must be provided before the first detectChanges().
  function render(overrides: Overrides = {}): void {
    const inputs: Overrides = {
      fieldId: 'firstname',
      kind: 'text' as Kind, // Only its presence matters with the template stubbed.
      options: [],
      values: baseValues as unknown as Values,
      status: 'conflict' as Status,
      resolution: makeResolution(),
      fallback: makeResolution(),
      ...overrides
    };

    Object.entries(inputs).forEach(([name, value]) => {
      if (value !== undefined) {
        fixture.componentRef.setInput(name, value);
      }
    });

    fixture.detectChanges();
  }

  function updateInput(name: keyof Overrides, value: unknown): void {
    fixture.componentRef.setInput(name, value);
    fixture.detectChanges();
  }

  // A protected method is exercised through a typed, test-only facade.
  function searchActions(): { onOptionSearch: (value: string) => void } {
    return component as unknown as { onOptionSearch: (value: string) => void };
  }

  function keyEvent(
    key: string,
    options: {
      ctrlKey?: boolean;
      metaKey?: boolean;
      altKey?: boolean;
      target?: EventTarget;
    } = {}
  ): KeyboardEvent & {
    preventDefault: jest.Mock;
    stopPropagation: jest.Mock;
  } {
    return {
      key,
      ctrlKey: false,
      metaKey: false,
      altKey: false,
      target: document.createElement('div'),
      preventDefault: jest.fn(),
      stopPropagation: jest.fn(),
      ...options
    } as unknown as KeyboardEvent & {
      preventDefault: jest.Mock;
      stopPropagation: jest.Mock;
    };
  }

  beforeEach(async () => {
    italianLabels = signal<Array<{ value: string; label: string }>>([]);

    comparisonDataMock = {
      getReasonOptions: jest.fn().mockReturnValue(signal([
        { value: 'OTHER', label: 'Other' },
        { value: 'CLIENT_REQUEST', label: 'Client request' }
      ])),
      getItalianBirthTownLabels: jest.fn().mockReturnValue(italianLabels),
      addItalianBirthTownOptions: jest.fn(),
      setItalianBirthTownCode: jest.fn()
    };

    referenceServiceMock = {
      searchReferences: jest.fn().mockReturnValue(of({ content: [] }))
    };

    await TestBed.configureTestingModule({
      imports: [ComparisonResolution],
      providers: [
        { provide: COMPARISON_DATA_SERVICE, useValue: comparisonDataMock },
        { provide: ReferenceService, useValue: referenceServiceMock }
      ]
    })
      .overrideComponent(ComparisonResolution, {
        set: {
          // These are tests of the class logic. The real template and its
          // ng-zorro / child components are deliberately not instantiated.
          template: '',
          imports: [],
          providers: [
            { provide: COMPARISON_DATA_SERVICE, useValue: comparisonDataMock },
            { provide: ReferenceService, useValue: referenceServiceMock }
          ]
        }
      })
      .compileComponents();

    fixture = TestBed.createComponent(ComparisonResolution);
    component = fixture.componentInstance;

    // JSDOM often lacks scrollIntoView(); afterNextRender() calls it.
    (fixture.nativeElement as HTMLElement).scrollIntoView = jest.fn();
  });

  it('should create the component', () => {
    render();
    expect(component).toBeTruthy();
  });

  describe('inputs and source choices', () => {
    it('should use the default optional inputs', () => {
      render();
      expect(component.readOnly()).toBe(false);
      expect(component.isOverride()).toBe(false);
      expect(component.structuredError()).toBeNull();
      expect(component.availableSources()).toBeUndefined();
    });

    it('should expose portal, core and manual as choices in this order', () => {
      render();
      expect(component.choices().map(choice => choice.source)).toEqual([
        'portal', 'core', 'manual'
      ]);
      expect(component.choices().map(choice => choice.shortcut)).toEqual([
        '1', '2', '3'
      ]);
    });

    it('should filter sources and renumber the available shortcuts', () => {
      render({ availableSources: ['core', 'manual'] as AvailableSources });
      expect(component.choices().map(choice => [choice.source, choice.shortcut]))
        .toEqual([['core', '1'], ['manual', '2']]);
    });

    it('should not filter when availableSources is empty', () => {
      render({ availableSources: [] as unknown as AvailableSources });
      expect(component.choices()).toHaveLength(3);
    });

    it('should expose the selected values in the choices', () => {
      render();
      expect(component.choices().find(choice => choice.source === 'portal')?.value)
        .toBe('Portal value');
      expect(component.choices().find(choice => choice.source === 'core')?.value)
        .toBe('Core value');
    });
  });

  describe('draft source, value and formatting', () => {
    it('should initialize draftSource from the resolution', () => {
      render({ resolution: makeResolution('core' as Source, 'Core value') });
      expect(component.draftSource()).toBe('core');
      expect(component.draftValue()).toBe('Core value');
    });

    it('should change source when editable', () => {
      render();
      component.selectSource('core' as Source);
      expect(component.draftSource()).toBe('core');
      expect(component.draftValue()).toBe('Core value');
    });

    it('should ignore source changes when readOnly', () => {
      render({ readOnly: true });
      component.selectSource('core' as Source);
      expect(component.draftSource()).toBe('portal');
    });

    it('should initialize a manual draft from an existing manual resolution', () => {
      render({ resolution: makeResolution('manual' as Source, 'Custom value') });
      expect(component.manualValue()).toBe('Custom value');
      expect(component.draftValue()).toBe('Custom value');
    });

    it('should take the distinct CLIP value as the initial manual value', () => {
      render({
        values: { portal: 'A', core: 'B', clip: 'C' } as unknown as Values
      });
      expect(component.manualValue()).toBe('C');
    });

    it('should use an empty manual value when CLIP equals portal or core', () => {
      render({
        values: { portal: 'Same', core: 'Other', clip: 'Same' } as unknown as Values
      });
      expect(component.manualValue()).toBe('');
    });

    it('should trim the manual draft and convert blanks to null', () => {
      render();
      component.selectSource('manual' as Source);
      component.manualValue.set('  custom text  ');
      expect(component.draftValue()).toBe('custom text');

      component.manualValue.set('  ');
      expect(component.draftValue()).toBeNull();
    });

    it('should format a DD/MM/YYYY manual date for the date input', () => {
      render();
      component.manualValue.set('07/11/2026');
      expect(component.manualValueFormattedDate()).toBe('2026-11-07');
    });

    it('should format a value using the options supplied by the parent', () => {
      render({ options: [{ value: 'LU', label: 'Luxembourg' }] as Options });
      expect(component.format('LU')).toBe('Luxembourg');
      expect(component.format('BE')).toBe('BE');
      expect(component.format(null)).toBeNull();
    });

    it('should format Italian birth town codes using the data service labels', () => {
      italianLabels.set([{ value: 'A001', label: 'Abano Terme' }]);
      render({ fieldId: 'italian-birth-town-code' });
      expect(component.format('A001')).toBe('Abano Terme');
      expect(component.format('X999')).toBe('X999');
    });

    it('should show the Italian code options only for that field', () => {
      italianLabels.set([{ value: 'A001', label: 'Abano Terme' }]);
      render({ fieldId: 'italian-birth-town-code' });
      expect(component.format('A001')).toBe('Abano Terme');

      updateInput('fieldId', 'firstname');
      expect(component.format('A001')).toBe('A001');
    });
  });

  describe('reasons, errors and validation', () => {
    it('should not require justification when the draft matches the fallback', () => {
      render();
      expect(component.justificationRequired()).toBe(false);
      expect(component.canApply()).toBe(true);
    });

    it('should require justification when the selected source differs from fallback', () => {
      render();
      component.selectSource('core' as Source);
      expect(component.justificationRequired()).toBe(true);
    });

    it('should require justification if the selected value differs from fallback', () => {
      render();
      component.selectSource('manual' as Source);
      component.manualValue.set('Something new');
      expect(component.justificationRequired()).toBe(true);
    });

    it('should automatically select a non-empty reason for the core source', () => {
      render();
      component.selectSource('core' as Source);
      expect(component.reason()).not.toBe('');
    });

    it('should require a reason when a justification is required', () => {
      render();
      component.selectSource('manual' as Source);
      component.manualValue.set('Custom value');
      component.reason.set('');
      expect(component.justificationRequired()).toBe(true);
      expect(component.canApply()).toBe(false);

      component.reason.set('CLIENT_REQUEST');
      expect(component.canApply()).toBe(true);
    });

    it('should require a comment when reason is OTHER', () => {
      render();
      component.selectSource('core' as Source);
      component.reason.set('OTHER');
      expect(component.commentRequired()).toBe(true);
      expect(component.canApply()).toBe(false);

      component.comment.set('A detailed explanation for this manual decision');
      expect(component.canApply()).toBe(true);
    });

    it('should not require a comment for other reasons', () => {
      render();
      component.selectSource('core' as Source);
      component.reason.set('CLIENT_REQUEST');
      expect(component.commentRequired()).toBe(false);
      expect(component.canApply()).toBe(true);
    });

    it('should reject a blank manual value even if justification is present', () => {
      render();
      component.selectSource('manual' as Source);
      component.manualValue.set('   ');
      component.reason.set('CLIENT_REQUEST');
      expect(component.canApply()).toBe(false);
    });

    it('should expose a structured error until it has been cleared', () => {
      render();
      updateInput('structuredError', 'Invalid value');
      component.clearError.set(false);
      expect(component.error()).toBe('Invalid value');
      expect(component.canApply()).toBe(false);

      component.clearError.set(true);
      expect(component.error()).toBeNull();
    });

    it('should recognize blank values', () => {
      render();
      expect(component.isBlankValue(null)).toBe(true);
      expect(component.isBlankValue('')).toBe(true);
      expect(component.isBlankValue('   ')).toBe(true);
      expect(component.isBlankValue('Some text')).toBe(false);
    });
  });

  describe('apply output', () => {
    it('should emit the fallback selection without unnecessary reason or comment', () => {
      render();
      const applySpy = jest.fn();
      component.apply.subscribe(applySpy);

      component.emitApply();

      expect(applySpy).toHaveBeenCalledWith({
        source: 'portal',
        value: 'Portal value',
        reason: '',
        comment: ''
      });
    });

    it('should emit a changed source with its reason', () => {
      render();
      const applySpy = jest.fn();
      component.apply.subscribe(applySpy);
      component.selectSource('core' as Source);
      component.reason.set('CLIENT_REQUEST');
      component.comment.set('This should not be included');

      component.emitApply();

      expect(applySpy).toHaveBeenCalledWith({
        source: 'core',
        value: 'Core value',
        reason: 'CLIENT_REQUEST',
        comment: ''
      });
    });

    it('should trim and emit the comment for OTHER', () => {
      render();
      const applySpy = jest.fn();
      component.apply.subscribe(applySpy);
      component.selectSource('manual' as Source);
      component.manualValue.set('  New value ');
      component.reason.set('OTHER');
      component.comment.set('  An explanation that is long enough  ');

      component.emitApply();

      expect(applySpy).toHaveBeenCalledWith({
        source: 'manual',
        value: 'New value',
        reason: 'OTHER',
        comment: 'An explanation that is long enough'
      });
    });

    it('should not emit when readOnly', () => {
      render({ readOnly: true });
      const applySpy = jest.fn();
      component.apply.subscribe(applySpy);
      component.emitApply();
      expect(applySpy).not.toHaveBeenCalled();
    });

    it('should not emit when the draft is invalid', () => {
      render();
      component.selectSource('manual' as Source);
      component.manualValue.set('');
      const applySpy = jest.fn();
      component.apply.subscribe(applySpy);
      component.emitApply();
      expect(applySpy).not.toHaveBeenCalled();
    });
  });

  describe('keyboard navigation', () => {
    it('should cancel when Escape is pressed', () => {
      render();
      const cancelSpy = jest.fn();
      component.cancel.subscribe(cancelSpy);
      const event = keyEvent('Escape');

      component.onKeydown(event);

      expect(cancelSpy).toHaveBeenCalledTimes(1);
      expect(event.stopPropagation).toHaveBeenCalledTimes(1);
    });

    it.each(['ctrlKey', 'metaKey'] as const)(
      'should submit with %s + Enter', modifier => {
        render();
        const applySpy = jest.fn();
        component.apply.subscribe(applySpy);
        const event = keyEvent('Enter',
          modifier === 'ctrlKey' ? { ctrlKey: true } : { metaKey: true }
        );

        component.onKeydown(event);

        expect(event.preventDefault).toHaveBeenCalledTimes(1);
        expect(event.stopPropagation).toHaveBeenCalledTimes(1);
        expect(applySpy).toHaveBeenCalledTimes(1);
      }
    );

    it('should not submit on plain Enter', () => {
      render();
      const applySpy = jest.fn();
      component.apply.subscribe(applySpy);
      component.onKeydown(keyEvent('Enter'));
      expect(applySpy).not.toHaveBeenCalled();
    });

    it('should select a source using the number shortcut outside text inputs', () => {
      render();
      const event = keyEvent('2');

      component.onKeydown(event);

      expect(component.draftSource()).toBe('core');
      expect(event.preventDefault).toHaveBeenCalled();
      expect(event.stopPropagation).toHaveBeenCalled();
    });

    it('should not intercept number keys while typing in an input', () => {
      render();
      const event = keyEvent('2', { target: document.createElement('input') });
      component.onKeydown(event);

      expect(component.draftSource()).toBe('portal');
      expect(event.preventDefault).not.toHaveBeenCalled();
    });

    it('should support Alt + number even while an input has focus', () => {
      render();
      const event = keyEvent('2', {
        altKey: true,
        target: document.createElement('input')
      });
      component.onKeydown(event);

      expect(component.draftSource()).toBe('core');
    });

    it('should respect filtered shortcut numbering', () => {
      render({ availableSources: ['core', 'manual'] as AvailableSources });
      component.onKeydown(keyEvent('1'));
      expect(component.draftSource()).toBe('core');
    });

    it('should ignore a key that does not correspond to a choice', () => {
      render();
      const event = keyEvent('9');
      component.onKeydown(event);
      expect(component.draftSource()).toBe('portal');
      expect(event.preventDefault).not.toHaveBeenCalled();
    });
  });

  describe('Italian birth town search', () => {
    it('should delegate the search with default page and size', () => {
      render();
      component.searchItalianBirthTownCodes('Roma').subscribe();
      expect(referenceServiceMock.searchReferences).toHaveBeenCalledWith(
        'IT_BIRTH_CODE', 'Roma', 0, 20
      );
    });

    it('should delegate the search with explicit pagination', () => {
      render();
      component.searchItalianBirthTownCodes('Milano', 2, 15).subscribe();
      expect(referenceServiceMock.searchReferences).toHaveBeenCalledWith(
        'IT_BIRTH_CODE', 'Milano', 2, 15
      );
    });

    it('should not search from onOptionSearch for an unrelated field', () => {
      render({ fieldId: 'firstname' });
      searchActions().onOptionSearch('Rome');
      expect(referenceServiceMock.searchReferences).not.toHaveBeenCalled();
    });

    it('should add the response options and set the Italian code', () => {
      const references = [
        { value: 'H501', label: 'Roma' },
        { value: 'F205', label: 'Milano' }
      ];
      referenceServiceMock.searchReferences.mockReturnValue(
        of({ content: references })
      );
      render({ fieldId: 'italian-birth-town-code' });

      searchActions().onOptionSearch('Rom');

      expect(referenceServiceMock.searchReferences).toHaveBeenCalledWith(
        'IT_BIRTH_CODE', 'Rom', 0, 20
      );
      expect(comparisonDataMock.addItalianBirthTownOptions)
        .toHaveBeenCalledWith(references);
      expect(comparisonDataMock.setItalianBirthTownCode)
        .toHaveBeenCalledWith(references);
    });
  });
});
