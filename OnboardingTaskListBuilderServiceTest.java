import { ComponentFixture, TestBed } from '@angular/core/testing';
import { signal } from '@angular/core';
import { EMPTY, of } from 'rxjs';

import { ComparisonRowComponent } from './comparison-row.component';

// Use the same import paths as comparison-row.component.ts in your project.
// These two paths may need adjusting depending on your directory structure.
import { COMPARISON_DATA_SERVICE } from './comparison-data.service';
import { ReferenceService } from '../../../core/providers/services/reference.service';

describe('ComparisonRowComponent', () => {
  type Row = ReturnType<ComparisonRowComponent['row']>;

  let fixture: ComponentFixture<ComparisonRowComponent>;
  let component: ComparisonRowComponent;

  let italianLabels: ReturnType<typeof signal<Array<{ value: string; label: string }>>>;
  let comparisonDataMock: {
    getItalianBirthTownLabels: jest.Mock;
    addItalianBirthTownOptions: jest.Mock;
  };
  let referenceServiceMock: {
    findReferencesByDomains: jest.Mock;
  };

  function makeRow(overrides: Record<string, unknown> = {}): Row {
    return {
      id: 'firstname',
      key: 'firstname',
      label: 'First name',
      status: 'updated',
      editable: true,
      values: { core: 'Alice', clip: 'Alicia' },
      resolution: { value: 'Alicia', reason: '', comment: '' },
      options: [],
      ...overrides
    } as unknown as Row;
  }

  function italianRow(
    core: string | null = 'A001',
    clip: string | null = 'B002',
    chosen: string | null = 'C003'
  ): Row {
    return makeRow({
      id: 'italian-birth-town-code',
      values: { core, clip },
      resolution: { value: chosen, reason: '', comment: '' }
    });
  }

  beforeEach(async () => {
    italianLabels = signal<Array<{ value: string; label: string }>>([]);

    comparisonDataMock = {
      getItalianBirthTownLabels: jest.fn().mockReturnValue(italianLabels),
      addItalianBirthTownOptions: jest.fn().mockImplementation(
        (references: Array<{ value: string; label: string }>) => {
          italianLabels.update(previous => [...previous, ...references]);
        }
      )
    };

    referenceServiceMock = {
      findReferencesByDomains: jest.fn().mockImplementation(
        (_domain: string, code: string) =>
          of({ value: code, label: `Town ${code}` })
      )
    };

    await TestBed.configureTestingModule({
      imports: [ComparisonRowComponent],
      providers: [
        { provide: COMPARISON_DATA_SERVICE, useValue: comparisonDataMock },
        { provide: ReferenceService, useValue: referenceServiceMock }
      ]
    })
      .overrideComponent(ComparisonRowComponent, {
        set: {
          template: '',
          imports: [],
          providers: [
            { provide: COMPARISON_DATA_SERVICE, useValue: comparisonDataMock },
            { provide: ReferenceService, useValue: referenceServiceMock }
          ]
        }
      })
      .compileComponents();

    fixture = TestBed.createComponent(ComparisonRowComponent);
    component = fixture.componentInstance;
  });

  function render(row: Row = makeRow()): void {
    fixture.componentRef.setInput('row', row);
    fixture.detectChanges();
  }

  function updateRow(row: Row): void {
    fixture.componentRef.setInput('row', row);
    fixture.detectChanges();
  }

  it('should create', () => {
    render();
    expect(component).toBeTruthy();
  });

  describe('inputs and note', () => {
    it('should use the default input values', () => {
      render();
      expect(component.expanded()).toBe(false);
      expect(component.focused()).toBe(false);
      expect(component.readOnly()).toBe(false);
      expect(component.editable()).toBe(true);
      expect(component.structuredError()).toBeNull();
      expect(component.options()).toEqual([]);
    });

    it('should return an empty note when there is no reason', () => {
      render(makeRow({
        resolution: { value: 'Alicia', reason: '', comment: 'Ignored comment' }
      }));
      expect(component.note()).toBe('');
    });

    it('should return the reason when there is no comment', () => {
      render(makeRow({
        resolution: { value: 'Alicia', reason: 'CLIENT_INPUT', comment: '' }
      }));
      expect(component.note()).toBe('CLIENT_INPUT');
    });

    it('should combine reason and comment', () => {
      render(makeRow({
        resolution: { value: 'Alicia', reason: 'CLIENT_INPUT', comment: 'Confirmed' }
      }));
      expect(component.note()).toBe('CLIENT_INPUT: Confirmed');
    });

    it('should recompute the note when the row changes', () => {
      render();
      expect(component.note()).toBe('');
      updateRow(makeRow({
        resolution: { value: 'Alice', reason: 'KYC', comment: 'Updated' }
      }));
      expect(component.note()).toBe('KYC: Updated');
    });
  });

  describe('status presentation', () => {
    it.each([
      ['added', 'added', 'plus'],
      ['removed', 'removed', 'minus'],
      ['diff', 'conflict', 'warning'],
      ['conflict', 'conflict', 'warning'],
      ['updated', 'changed', 'swap'],
      ['retained', '', 'warning']
    ])('should map %s to chip label %s and icon %s', (status, label, icon) => {
      render(makeRow({ status }));
      expect(component.chipLabel()).toBe(label);
      expect(component.chipIcon()).toBe(icon);
    });

    it('should recalculate the status text when the status changes', () => {
      render(makeRow({ status: 'added' }));
      const firstText = component.statusText();
      updateRow(makeRow({ status: 'removed' }));
      expect(typeof firstText).toBe('string');
      expect(component.statusText()).not.toBe(firstText);
    });
  });

  describe('interaction', () => {
    it('should emit toggle when onEdit is called for an editable row', () => {
      render();
      const toggleSpy = jest.fn();
      component.toggle.subscribe(toggleSpy);

      component.onEdit();

      expect(toggleSpy).toHaveBeenCalledTimes(1);
    });

    it('should not emit toggle when onEdit is called for a non-editable row', () => {
      render(makeRow({ editable: false }));
      const toggleSpy = jest.fn();
      component.toggle.subscribe(toggleSpy);

      component.onEdit();

      expect(toggleSpy).not.toHaveBeenCalled();
    });

    it('should ignore clicks on a non-editable row', () => {
      render(makeRow({ editable: false }));
      const toggleSpy = jest.fn();
      component.toggle.subscribe(toggleSpy);

      component.onClick({} as MouseEvent);

      expect(toggleSpy).not.toHaveBeenCalled();
    });

    it('should forward keyboard events to the resolution panel', () => {
      render();
      const panelMock = { onKeydown: jest.fn() };
      // The actual template is stubbed, so viewChild() has no child. Replace
      // the query signal locally for this unit test of delegation only.
      Object.defineProperty(component, 'panel', {
        configurable: true,
        value: () => panelMock
      });
      const event = new KeyboardEvent('keydown', { key: 'Escape' });

      component.onKeydown(event);

      expect(panelMock.onKeydown).toHaveBeenCalledWith(event);
    });
  });

  describe('focus effect', () => {
    it('should not focus the host while focused=false', () => {
      const focusSpy = jest.spyOn(fixture.nativeElement as HTMLElement, 'focus');
      render();
      expect(focusSpy).not.toHaveBeenCalled();
    });

    it('should focus the host when focused becomes true', () => {
      const focusSpy = jest.spyOn(fixture.nativeElement as HTMLElement, 'focus')
        .mockImplementation(() => {});
      render();

      fixture.componentRef.setInput('focused', true);
      fixture.detectChanges();

      expect(focusSpy).toHaveBeenCalledWith({ preventScroll: false });
    });
  });

  describe('Italian birth town reference loading', () => {
    it('should not request references for ordinary rows', () => {
      render(makeRow({
        values: { core: 'A001', clip: 'B002' },
        resolution: { value: 'C003', reason: '', comment: '' }
      }));

      expect(referenceServiceMock.findReferencesByDomains).not.toHaveBeenCalled();
    });

    it('should request each distinct non-empty town code', () => {
      render(italianRow('A001', 'B002', 'C003'));

      expect(referenceServiceMock.findReferencesByDomains).toHaveBeenCalledTimes(3);
      for (const code of ['A001', 'B002', 'C003']) {
        expect(referenceServiceMock.findReferencesByDomains)
          .toHaveBeenCalledWith('IT_BIRTH_CODE', code);
      }
    });

    it('should not request the same code more than once in a single row', () => {
      render(italianRow('A001', 'A001', 'A001'));

      expect(referenceServiceMock.findReferencesByDomains).toHaveBeenCalledTimes(1);
      expect(referenceServiceMock.findReferencesByDomains)
        .toHaveBeenCalledWith('IT_BIRTH_CODE', 'A001');
    });

    it('should ignore empty codes', () => {
      render(italianRow('', null, null));
      expect(referenceServiceMock.findReferencesByDomains).not.toHaveBeenCalled();
    });

    it('should skip codes which already have labels', () => {
      italianLabels.set([{ value: 'A001', label: 'Already loaded' }]);
      render(italianRow('A001', '', null));

      expect(referenceServiceMock.findReferencesByDomains).not.toHaveBeenCalled();
    });

    it('should add the references returned by the lookup', () => {
      const reference = { value: 'A001', label: 'Town A001' };
      referenceServiceMock.findReferencesByDomains.mockReturnValue(of(reference));
      render(italianRow('A001', '', null));

      expect(comparisonDataMock.addItalianBirthTownOptions)
        .toHaveBeenCalledWith([reference]);
    });

    it('should not add a reference when the lookup emits nothing', () => {
      referenceServiceMock.findReferencesByDomains.mockReturnValue(EMPTY);
      render(italianRow('A001', '', null));

      expect(comparisonDataMock.addItalianBirthTownOptions).not.toHaveBeenCalled();
    });

    it('should start loading when a normal row becomes an Italian birth-town row', () => {
      render();
      expect(referenceServiceMock.findReferencesByDomains).not.toHaveBeenCalled();

      updateRow(italianRow('A001', '', null));
      expect(referenceServiceMock.findReferencesByDomains)
        .toHaveBeenCalledWith('IT_BIRTH_CODE', 'A001');
    });

    it('should use loaded reference labels for Italian birth-town annotations', () => {
      italianLabels.set([{ value: 'A001', label: 'Town A001' }]);
      render(italianRow('A001', '', null));
      comparisonDataMock.getItalianBirthTownLabels.mockClear();

      const annotations = component.annotations();

      expect(Array.isArray(annotations)).toBe(true);
      expect(comparisonDataMock.getItalianBirthTownLabels).toHaveBeenCalled();
    });

    it('should use row options for annotations on other fields', () => {
      render(makeRow({
        options: [{ value: 'BE', label: 'Belgium' }]
      }));
      const annotations = component.annotations();
      expect(Array.isArray(annotations)).toBe(true);
      expect(comparisonDataMock.getItalianBirthTownLabels).not.toHaveBeenCalled();
    });
  });
});
