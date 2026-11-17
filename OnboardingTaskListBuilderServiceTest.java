
import {
  ComponentFixture,
  TestBed,
} from '@angular/core/testing';

import { ComparisonListField } from './comparison-list-field';

type Row = ReturnType<ComparisonListField['row']>;
type Entry = Row['entries'][number];
type Child = Entry['children'][number];

describe('ComparisonListField', () => {
  let fixture: ComponentFixture<ComparisonListField>;
  let component: ComparisonListField;

  const makeChild = (
    key: string,
    value: string | null,
    needsAttention = false
  ): Child =>
    ({
      key,
      needsAttention,
      resolution: { value },
    }) as Child;

  const makeEntry = (
    overrides: Partial<Entry> = {}
  ): Entry =>
    ({
      id: 'entry-1',
      entryKey: 'BE',
      label: 'Belgium',
      isManual: false,
      needsAttention: false,
      values: {
        core: null,
        clip: null,
        portal: null,
        snapshot: null,
      },
      resolution: { value: null },
      fallback: { value: null },
      children: [],
      ...overrides,
    }) as Entry;

  const makeRow = (
    overrides: Partial<Row> = {}
  ): Row =>
    ({
      id: 'list-1',
      key: 'emails',
      entryNoun: 'email',
      entries: [],
      options: [],
      entryFields: [],
      ...overrides,
    }) as Row;

  const structuredRow = (
    key: string,
    fieldKeys: string[],
    entries: Entry[] = []
  ): Row =>
    makeRow({
      key,
      entries,
      entryFields: fieldKeys.map(fieldKey => ({
        key: fieldKey,
        label: fieldKey,
        type: 'text',
      })) as Row['entryFields'],
    });

  async function render(
    row: Row = makeRow()
  ): Promise<void> {
    fixture = TestBed.createComponent(ComparisonListField);
    component = fixture.componentInstance;

    fixture.componentRef.setInput('row', row);
    fixture.detectChanges();

    await fixture.whenStable();
  }

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ComparisonListField],
    })
      .overrideComponent(ComparisonListField, {
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

    it('should initialize inputs and signals', async () => {
      await render();

      expect(component.expandedId()).toBeNull();
      expect(component.focusedId()).toBeNull();
      expect(component.readOnly()).toBe(false);

      expect(component.adding()).toBe(false);
      expect(component.draft()).toBe('');
      expect(component.addError()).toBeNull();

      expect(component.structuredDraft()).toEqual({});
      expect(component.structuredError()).toBeNull();
      expect(component.structuredModalVisible()).toBe(false);
    });
  });

  describe('Simple entry validation', () => {
    it('should not validate an empty draft', async () => {
      await render();

      component.draft.set('');

      expect(component.validationError()).toBe('');
    });

    it('should accept a valid email', async () => {
      await render(makeRow({ key: 'emails' }));

      component.draft.set('john@example.com');

      expect(component.validationError()).toBe('');
    });

    it('should reject an invalid email', async () => {
      await render(makeRow({ key: 'emails' }));

      component.draft.set('invalid-email');

      expect(component.validationError()).toContain(
        'Incorrect format'
      );
    });

    it('should accept a valid phone number', async () => {
      await render(makeRow({ key: 'phone-numbers' }));

      component.draft.set('+352621123456');

      expect(component.validationError()).toBe('');
    });

    it('should reject a phone number without plus', async () => {
      await render(makeRow({ key: 'phone-numbers' }));

      component.draft.set('352621123456');

      expect(component.validationError()).toContain(
        'Incorrect format'
      );
    });

    it('should reject a phone number with too few digits', async () => {
      await render(makeRow({ key: 'phone-numbers' }));

      component.draft.set('+123');

      expect(component.validationError()).toContain(
        'Incorrect format'
      );
    });

    it('should clear addError when draft changes', async () => {
      await render();

      component.addError.set('Duplicate email');

      component.onDraftChange('new@example.com');

      expect(component.draft()).toBe('new@example.com');
      expect(component.addError()).toBeNull();
    });
  });

  describe('Nationality limits', () => {
    it('should allow fewer than three nationalities', async () => {
      await render(
        makeRow({
          key: 'nationalities',
          entries: [
            makeEntry({ entryKey: 'BE' }),
            makeEntry({ entryKey: 'FR' }),
          ],
        })
      );

      expect(component.maxEntriesReached()).toBe(false);
    });

    it('should prevent exceeding three nationalities', async () => {
      await render(
        makeRow({
          key: 'nationalities',
          entries: [
            makeEntry({ entryKey: 'BE' }),
            makeEntry({ entryKey: 'FR' }),
            makeEntry({ entryKey: 'LU' }),
          ],
        })
      );

      expect(component.maxEntriesReached()).toBe(true);
    });

    it('should not apply the limit to other lists', async () => {
      await render(
        makeRow({
          key: 'emails',
          entries: Array.from(
            { length: 4 },
            (_, index) =>
              makeEntry({
                entryKey: `email${index}@example.com`,
              })
          ),
        })
      );

      expect(component.maxEntriesReached()).toBe(false);
    });
  });

  describe('Nationality duplicates', () => {
    it('should detect an existing nationality', async () => {
      await render(
        structuredRow(
          'nationalities',
          ['country', 'date'],
          [makeEntry({ entryKey: 'BE' })]
        )
      );

      component.setFieldValue('country', 'BE');

      expect(component.nationalityDuplicate()).toBe(true);
    });

    it('should recognize country labels and codes as duplicates', async () => {
      await render(
        makeRow({
          key: 'nationalities',
          entries: [makeEntry({ entryKey: 'BE' })],
          options: [
            { value: 'BE', label: 'Belgium' },
            { value: 'FR', label: 'France' },
          ],
        })
      );

      component.setFieldValue('country', 'Belgium');

      expect(component.nationalityDuplicate()).toBe(true);
    });

    it('should allow a new nationality', async () => {
      await render(
        structuredRow(
          'nationalities',
          ['country', 'date'],
          [makeEntry({ entryKey: 'BE' })]
        )
      );

      component.setFieldValue('country', 'FR');

      expect(component.nationalityDuplicate()).toBe(false);
    });

    it('should detect a nationality from a resolved child', async () => {
      await render(
        structuredRow('nationalities', ['country', 'date'], [
          makeEntry({
            entryKey: 'manual-1',
            isManual: true,
            children: [makeChild('country', 'LU')],
          }),
        ])
      );

      component.setFieldValue('country', 'LU');

      expect(component.nationalityDuplicate()).toBe(true);
    });
  });

  describe('Simple entry submission', () => {
    it('should emit a trimmed value', async () => {
      await render();

      const emitSpy = jest.fn();
      component.addEntry.subscribe(emitSpy);

      component.draft.set('  john@example.com  ');

      component.submit();

      expect(emitSpy).toHaveBeenCalledWith(
        'john@example.com'
      );
      expect(component.draft()).toBe('');
      expect(component.adding()).toBe(false);
    });

    it('should not submit an empty value', async () => {
      await render();

      const emitSpy = jest.fn();
      component.addEntry.subscribe(emitSpy);

      component.draft.set('   ');
      component.submit();

      expect(emitSpy).not.toHaveBeenCalled();
    });

    it('should reject a duplicate email ignoring case', async () => {
      await render(
        makeRow({
          key: 'emails',
          entryNoun: 'email',
          entries: [
            makeEntry({
              entryKey: 'john@example.com',
            }),
          ],
        })
      );

      const emitSpy = jest.fn();
      component.addEntry.subscribe(emitSpy);

      component.draft.set('JOHN@example.com');
      component.submit();

      expect(emitSpy).not.toHaveBeenCalled();
      expect(component.addError()).toBe(
        'This email already exists'
      );
    });

    it('should detect duplicate phone numbers with different formatting', async () => {
      await render(
        makeRow({
          key: 'phone-numbers',
          entryNoun: 'phone number',
          entries: [
            makeEntry({
              entryKey: '+352 621 123 456',
            }),
          ],
        })
      );

      const emitSpy = jest.fn();
      component.addEntry.subscribe(emitSpy);

      component.draft.set('+352621123456');
      component.submit();

      expect(emitSpy).not.toHaveBeenCalled();
      expect(component.addError()).toContain(
        'already exists'
      );
    });

    it('should cancel adding and reset the draft', async () => {
      await render();

      component.adding.set(true);
      component.draft.set('draft@example.com');
      component.addError.set('Existing value');
      component.structuredDraft.set({ country: 'BE' });

      component.cancelAdd();

      expect(component.adding()).toBe(false);
      expect(component.draft()).toBe('');
      expect(component.addError()).toBeNull();
      expect(component.structuredDraft()).toEqual({});
    });

    it('should open the simple add editor', async () => {
      await render();

      component.draft.set('previous@example.com');
      component.openSimpleAdd();

      expect(component.adding()).toBe(true);
      expect(component.draft()).toBe('');
    });
  });

  describe('Structured entry validation', () => {
    it('should reject a structured list without field definitions', async () => {
      await render(
        structuredRow('nationalities', [])
      );

      expect(component.canSubmitStructured()).toBe(false);
    });

    it('should require country and date for nationality', async () => {
      await render(
        structuredRow('nationalities', [
          'country',
          'date',
        ])
      );

      component.setFieldValue('country', 'BE');

      expect(component.canSubmitStructured()).toBe(false);

      component.setFieldValue('date', '2026-11-17');

      expect(component.canSubmitStructured()).toBe(true);
    });

    it('should prevent adding an existing nationality', async () => {
      await render(
        structuredRow(
          'nationalities',
          ['country', 'date'],
          [makeEntry({ entryKey: 'BE' })]
        )
      );

      component.setFieldValue('country', 'BE');
      component.setFieldValue('date', '2026-11-17');

      expect(component.canSubmitStructured()).toBe(false);
    });

    it('should require a tax country', async () => {
      await render(
        structuredRow('tax-information', [
          'tax-country',
          'tin',
          'tin-unavailable-reason',
        ])
      );

      component.setFieldValue('tin', '123456789');

      expect(component.canSubmitStructured()).toBe(false);
    });

    it('should accept tax country with a TIN', async () => {
      await render(
        structuredRow('tax-information', [
          'tax-country',
          'tin',
          'tin-unavailable-reason',
        ])
      );

      component.setFieldValue('tax-country', 'LU');
      component.setFieldValue('tin', '123456789');

      expect(component.canSubmitStructured()).toBe(true);
    });

    it('should accept a reason when TIN is unavailable', async () => {
      await render(
        structuredRow('tax-information', [
          'tax-country',
          'tin',
          'tin-unavailable-reason',
        ])
      );

      component.setFieldValue('tax-country', 'LU');
      component.setFieldValue(
        'tin-unavailable-reason',
        'A'
      );

      expect(component.canSubmitStructured()).toBe(true);
    });

    it('should reject tax information without TIN or reason', async () => {
      await render(
        structuredRow('tax-information', [
          'tax-country',
          'tin',
          'tin-unavailable-reason',
        ])
      );

      component.setFieldValue('tax-country', 'LU');

      expect(component.canSubmitStructured()).toBe(false);
    });
  });

  describe('Structured form state', () => {
    it('should update a structured field', async () => {
      await render();

      component.setFieldValue('country', 'BE');

      expect(component.fieldValue('country')).toBe('BE');
    });

    it('should clear the TIN reason when a TIN is entered', async () => {
      await render();

      component.setFieldValue(
        'tin-unavailable-reason',
        'A'
      );

      component.setFieldValue('tin', '123456789');

      expect(component.fieldValue('tin')).toBe('123456789');
      expect(
        component.fieldValue('tin-unavailable-reason')
      ).toBe('');
    });

    it('should clear structured error when a field changes', async () => {
      await render();

      component.structuredError.set('Invalid value');
      component.setFieldValue('country', 'FR');

      expect(component.structuredError()).toBeNull();
    });

    it('should open the structured modal', async () => {
      await render();

      component.openStructuredModal();

      expect(component.structuredModalVisible()).toBe(true);
    });

    it('should close and clear the structured modal', async () => {
      await render();

      component.structuredModalVisible.set(true);
      component.structuredDraft.set({ country: 'BE' });

      component.closeStructuredModal();

      expect(component.structuredModalVisible()).toBe(false);
      expect(component.structuredDraft()).toEqual({});
    });
  });

  describe('Structured submission', () => {
    it('should emit a nationality and close the modal', async () => {
      await render(
        structuredRow('nationalities', [
          'country',
          'date',
        ])
      );

      const emitSpy = jest.fn();
      component.addStructureEntry.subscribe(emitSpy);

      component.structuredModalVisible.set(true);
      component.setFieldValue('country', 'FR');
      component.setFieldValue('date', '2026-11-17');

      component.submitStructured();

      expect(emitSpy).toHaveBeenCalledWith({
        fields: [
          { key: 'country', value: 'FR' },
          { key: 'date', value: '2026-11-17' },
        ],
      });

      expect(component.structuredError()).toBeNull();
      expect(component.structuredModalVisible()).toBe(false);
    });

    it('should reject a nationality without country', async () => {
      await render(
        structuredRow('nationalities', [
          'country',
          'date',
        ])
      );

      const emitSpy = jest.fn();
      component.addStructureEntry.subscribe(emitSpy);

      component.setFieldValue('date', '2026-11-17');
      component.submitStructured();

      expect(emitSpy).not.toHaveBeenCalled();
      expect(component.structuredError()).toBe(
        'Nationality is required.'
      );
    });

    it('should reject an existing nationality', async () => {
      await render(
        structuredRow(
          'nationalities',
          ['country', 'date'],
          [makeEntry({ entryKey: 'BE' })]
        )
      );

      const emitSpy = jest.fn();
      component.addStructureEntry.subscribe(emitSpy);

      component.setFieldValue('country', 'BE');
      component.setFieldValue('date', '2026-11-17');

      component.submitStructured();

      expect(emitSpy).not.toHaveBeenCalled();
      expect(component.structuredError()).toBe(
        'This nationality already exists.'
      );
    });

    it('should reject a duplicate tax country', async () => {
      await render(
        structuredRow(
          'tax-information',
          ['tax-country', 'tin'],
          [makeEntry({ entryKey: 'LU' })]
        )
      );

      const emitSpy = jest.fn();
      component.addStructureEntry.subscribe(emitSpy);

      component.setFieldValue('tax-country', 'LU');
      component.setFieldValue('tin', '123456789');

      component.submitStructured();

      expect(emitSpy).not.toHaveBeenCalled();
      expect(component.structuredError()).toBe(
        'Tax Country already exists.'
      );
    });

    it('should reject a second default TIN', async () => {
      await render(
        structuredRow(
          'tax-information',
          ['tax-country', 'tin', 'is-default'],
          [
            makeEntry({
              entryKey: 'LU',
              children: [
                makeChild('is-default', 'true'),
              ],
            }),
          ]
        )
      );

      const emitSpy = jest.fn();
      component.addStructureEntry.subscribe(emitSpy);

      component.setFieldValue('tax-country', 'FR');
      component.setFieldValue('tin', '123456789');
      component.setFieldValue('is-default', 'true');

      component.submitStructured();

      expect(emitSpy).not.toHaveBeenCalled();
      expect(component.structuredError()).toBe(
        'Default TIN already chosen.'
      );
    });

    it('should emit tax information with empty fields as null', async () => {
      await render(
        structuredRow('tax-information', [
          'tax-country',
          'tin',
          'tin-unavailable-reason',
        ])
      );

      const emitSpy = jest.fn();
      component.addStructureEntry.subscribe(emitSpy);

      component.setFieldValue('tax-country', 'LU');
      component.setFieldValue('tin', '123456789');

      component.submitStructured();

      expect(emitSpy).toHaveBeenCalledWith({
        fields: [
          { key: 'tax-country', value: 'LU' },
          { key: 'tin', value: '123456789' },
          { key: 'tin-unavailable-reason', value: null },
        ],
      });
    });
  });

  describe('Structured field errors', () => {
    it('should store an error for a specific entry and child', async () => {
      await render();

      component.updateStructuredFieldError(
        'entry-1',
        'country',
        'Nationality is required.'
      );

      expect(
        component.structuredChildrenErrors()
          .get('entry-1')
          ?.get('country')
      ).toBe('Nationality is required.');
    });

    it('should clear an existing child error', async () => {
      await render();

      component.updateStructuredFieldError(
        'entry-1',
        'country',
        'Nationality is required.'
      );

      component.updateStructuredFieldError(
        'entry-1',
        'country',
        null
      );

      expect(
        component.structuredChildrenErrors()
          .get('entry-1')
          ?.get('country')
      ).toBeNull();
    });

    it('should preserve errors for other entries', async () => {
      await render();

      component.updateStructuredFieldError(
        'entry-1',
        'country',
        'Country required'
      );

      component.updateStructuredFieldError(
        'entry-2',
        'tin',
        'TIN required'
      );

      expect(
        component.structuredChildrenErrors()
          .get('entry-1')
          ?.get('country')
      ).toBe('Country required');

      expect(
        component.structuredChildrenErrors()
          .get('entry-2')
          ?.get('tin')
      ).toBe('TIN required');
    });
  });

  describe('Attention counter', () => {
    it('should return zero when no entry requires attention', async () => {
      await render(
        makeRow({
          entries: [
            makeEntry({
              needsAttention: false,
              children: [],
            }),
          ],
        })
      );

      expect(component.attentionCount()).toBe(0);
    });

    it('should count entries requiring attention', async () => {
      await render(
        makeRow({
          entries: [
            makeEntry({
              id: 'entry-1',
              needsAttention: true,
            }),
            makeEntry({
              id: 'entry-2',
              needsAttention: false,
            }),
            makeEntry({
              id: 'entry-3',
              needsAttention: true,
            }),
          ],
        })
      );

      expect(component.attentionCount()).toBe(2);
    });

    it('should count an entry with a child requiring attention', async () => {
      await render(
        makeRow({
          entries: [
            makeEntry({
              needsAttention: false,
              children: [
                makeChild('country', 'BE', true),
              ],
            }),
          ],
        })
      );

      expect(component.attentionCount()).toBe(1);
    });

    it('should not count the same entry twice', async () => {
      await render(
        makeRow({
          entries: [
            makeEntry({
              needsAttention: true,
              children: [
                makeChild('country', 'BE', true),
                makeChild('date', null, true),
              ],
            }),
          ],
        })
      );

      expect(component.attentionCount()).toBe(1);
    });
  });

  describe('Output events', () => {
    it('should emit a resolution patch for an entry', async () => {
      await render();

      const emitSpy = jest.fn();
      component.applyEntry.subscribe(emitSpy);

      const entry = makeEntry({ id: 'entry-123' });

      const patch = {
        source: 'core',
        value: 'BE',
      } as Parameters<ComparisonListField['emitApply']>[0];

      component.emitApply(patch, entry);

      expect(emitSpy).toHaveBeenCalledWith({
        id: 'entry-123',
        payload: patch,
      });
    });

    it('should emit a string through toggleEntry', async () => {
      await render();

      const emitSpy = jest.fn();
      component.toggleEntry.subscribe(emitSpy);

      component.toggleEntry.emit('entry-1');

      expect(emitSpy).toHaveBeenCalledWith('entry-1');
    });

    it('should emit a string through resetEntry', async () => {
      await render();

      const emitSpy = jest.fn();
      component.resetEntry.subscribe(emitSpy);

      component.resetEntry.emit('entry-1');

      expect(emitSpy).toHaveBeenCalledWith('entry-1');
    });
  });
});
