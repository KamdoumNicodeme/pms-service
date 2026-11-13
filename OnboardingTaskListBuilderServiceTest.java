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
