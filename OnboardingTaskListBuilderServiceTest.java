import { OutputEmitterRef } from '@angular/core';
import { FormControl } from '@angular/forms';

import { CciManualChangeSignOffForm } from './cci-manual-change.form';

describe('CciManualChangeSignOffForm', () => {
  let emitSpy: jest.Mock;
  let form: CciManualChangeSignOffForm;

  beforeEach(() => {
    emitSpy = jest.fn();
    const validationEmitter = {
      emit: emitSpy,
    } as unknown as OutputEmitterRef<boolean>;

    form = new CciManualChangeSignOffForm(validationEmitter, null);
  });

  it('should create its checklist control', () => {
    expect(form.controls.checklist).toBeDefined();
  });

  it('should emit the initial validity when constructed', () => {
    expect(emitSpy).toHaveBeenCalledTimes(1);
    expect(emitSpy).toHaveBeenCalledWith(form.status === 'VALID');
  });

  // This test-only control lets us test the status notification independently
  // of the checklist's business-specific validators and initial values.
  function installValidChecklist(): FormControl<string> {
    const validControl = new FormControl('done', { nonNullable: true });
    form.setControl('checklist', validControl as unknown as typeof form.controls.checklist);
    return validControl;
  }

  it('should emit true when the form becomes valid', () => {
    installValidChecklist();

    expect(form.status).toBe('VALID');
    expect(emitSpy).toHaveBeenLastCalledWith(true);
  });

  it('should emit false when the form becomes invalid', () => {
    const checklist = installValidChecklist();
    emitSpy.mockClear();

    checklist.setErrors({ required: true });

    expect(form.status).toBe('INVALID');
    expect(emitSpy).toHaveBeenCalledTimes(1);
    expect(emitSpy).toHaveBeenCalledWith(false);
  });

  it('should emit true again after the error is cleared', () => {
    const checklist = installValidChecklist();
    checklist.setErrors({ required: true });
    emitSpy.mockClear();

    checklist.setErrors(null);

    expect(form.status).toBe('VALID');
    expect(emitSpy).toHaveBeenCalledTimes(1);
    expect(emitSpy).toHaveBeenCalledWith(true);
  });

  it('should not emit again when the valid/invalid state stays the same', () => {
    const checklist = installValidChecklist();
    emitSpy.mockClear();

    form.updateValueAndValidity();
    form.updateValueAndValidity();

    expect(emitSpy).not.toHaveBeenCalled();

    checklist.setErrors({ required: true });
    emitSpy.mockClear();

    checklist.setErrors({ anotherError: true });

    expect(form.status).toBe('INVALID');
    expect(emitSpy).not.toHaveBeenCalled();
  });
});
