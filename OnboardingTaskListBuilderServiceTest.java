
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { WritableSignal } from '@angular/core';

import { SegmentedChoice } from './segmented-choice';

describe('SegmentedChoice', () => {
  let component: SegmentedChoice;
  let fixture: ComponentFixture<SegmentedChoice>;

  let onChange: jest.Mock;
  let onTouched: jest.Mock;

  type UnderTest = {
    value: WritableSignal<string | null>;
    disabled: WritableSignal<boolean>;
    select: (value: string) => void;
  };

  const view = (): UnderTest =>
    component as unknown as UnderTest;

  const options = [
    { value: 'yes', label: 'Yes' },
    { value: 'no', label: 'No' },
  ];

  beforeEach(async () => {
    onChange = jest.fn();
    onTouched = jest.fn();

    await TestBed.configureTestingModule({
      imports: [SegmentedChoice],
    })
      .overrideComponent(SegmentedChoice, {
        set: {
          template: '',
          imports: [],
        },
      })
      .compileComponents();

    fixture = TestBed.createComponent(SegmentedChoice);
    component = fixture.componentInstance;

    fixture.componentRef.setInput('options', options);

    fixture.detectChanges();
  });

  describe('Initialization', () => {
    it('should create the component', () => {
      expect(component).toBeTruthy();
    });

    it('should initialize with a null value', () => {
      expect(view().value()).toBeNull();
    });

    it('should be enabled by default', () => {
      expect(view().disabled()).toBe(false);
    });
  });

  describe('writeValue', () => {
    it('should update the value', () => {
      component.writeValue('yes');

      expect(view().value()).toBe('yes');
    });

    it('should accept a null value', () => {
      component.writeValue('yes');
      component.writeValue(null);

      expect(view().value()).toBeNull();
    });

    it('should not trigger callbacks', () => {
      component.registerOnChange(onChange);
      component.registerOnTouched(onTouched);

      component.writeValue('yes');

      expect(onChange).not.toHaveBeenCalled();
      expect(onTouched).not.toHaveBeenCalled();
    });
  });

  describe('registerOnChange', () => {
    it('should call the registered callback when selecting a value', () => {
      component.registerOnChange(onChange);

      view().select('yes');

      expect(onChange).toHaveBeenCalledTimes(1);
      expect(onChange).toHaveBeenCalledWith('yes');
    });
  });

  describe('registerOnTouched', () => {
    it('should call the registered callback when selecting a value', () => {
      component.registerOnTouched(onTouched);

      view().select('yes');

      expect(onTouched).toHaveBeenCalledTimes(1);
    });
  });

  describe('setDisabledState', () => {
    it('should disable the component', () => {
      component.setDisabledState(true);

      expect(view().disabled()).toBe(true);
    });

    it('should enable the component', () => {
      component.setDisabledState(true);
      component.setDisabledState(false);

      expect(view().disabled()).toBe(false);
    });

    it('should preserve the selected value when disabled', () => {
      component.writeValue('yes');

      component.setDisabledState(true);

      expect(view().value()).toBe('yes');
    });
  });

  describe('select', () => {
    beforeEach(() => {
      component.registerOnChange(onChange);
      component.registerOnTouched(onTouched);
    });

    it('should update the selected value', () => {
      view().select('yes');

      expect(view().value()).toBe('yes');
    });

    it('should update the value when selecting another option', () => {
      view().select('yes');
      view().select('no');

      expect(view().value()).toBe('no');
    });

    it('should notify Angular forms when selecting an option', () => {
      view().select('yes');

      expect(onChange).toHaveBeenCalledWith('yes');
      expect(onTouched).toHaveBeenCalledTimes(1);
    });

    it('should not update the value when disabled', () => {
      component.writeValue('yes');
      component.setDisabledState(true);

      view().select('no');

      expect(view().value()).toBe('yes');
    });

    it('should not trigger callbacks when disabled', () => {
      component.setDisabledState(true);

      view().select('yes');

      expect(onChange).not.toHaveBeenCalled();
      expect(onTouched).not.toHaveBeenCalled();
    });

    it('should allow selection after being enabled again', () => {
      component.setDisabledState(true);
      component.setDisabledState(false);

      view().select('no');

      expect(view().value()).toBe('no');
      expect(onChange).toHaveBeenCalledWith('no');
      expect(onTouched).toHaveBeenCalledTimes(1);
    });
  });
});
