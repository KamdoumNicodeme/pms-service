
import { OutputEmitterRef } from '@angular/core';
import { CciErrorManagementSignOffForm } from './cci-error-management-sign-off.form';

describe('CciErrorManagementSignOffForm', () => {
  let validationEmitter: OutputEmitterRef<boolean>;
  let emitSpy: jest.Mock;

  const createInitialData = (
    errorResolutionDetails: string | null,
    allReturnedMessagesManaged: boolean | null
  ): NonNullable<
    ConstructorParameters<typeof CciErrorManagementSignOffForm>[1]
  > => ({
    check: {
      errorResolutionDetails,
      allReturnedMessagesManaged
    }
  } as NonNullable<
    ConstructorParameters<typeof CciErrorManagementSignOffForm>[1]
  >);

  beforeEach(() => {
    emitSpy = jest.fn();

    validationEmitter = {
      emit: emitSpy
    } as unknown as OutputEmitterRef<boolean>;
  });

  it('should create the form with a checklist control', () => {
    const form = new CciErrorManagementSignOffForm(
      validationEmitter,
      null
    );

    expect(form).toBeTruthy();
    expect(form.controls.check).toBeDefined();
  });

  it('should emit false on initialization when data is missing', () => {
    const form = new CciErrorManagementSignOffForm(
      validationEmitter,
      null
    );

    expect(form.status).toBe('INVALID');
    expect(emitSpy).toHaveBeenCalledWith(false);
    expect(emitSpy).toHaveBeenCalledTimes(1);
  });

  it('should emit true when initial data is valid', () => {
    const initialData = createInitialData(
      'All returned messages have been resolved',
      true
    );

    const form = new CciErrorManagementSignOffForm(
      validationEmitter,
      initialData
    );

    expect(form.status).toBe('VALID');
    expect(emitSpy).toHaveBeenCalledWith(true);
    expect(emitSpy).toHaveBeenCalledTimes(1);
  });

  it('should emit true when the form becomes valid', () => {
    const form = new CciErrorManagementSignOffForm(
      validationEmitter,
      null
    );

    emitSpy.mockClear();

    form.controls.check.controls.errorResolutionDetails
      .setValue('Error resolved');

    form.controls.check.controls.allReturnedMessagesManaged
      .setValue(true);

    expect(form.status).toBe('VALID');
    expect(emitSpy).toHaveBeenCalledTimes(1);
    expect(emitSpy).toHaveBeenCalledWith(true);
  });

  it('should emit false when a valid form becomes invalid', () => {
    const initialData = createInitialData(
      'Error resolved',
      true
    );

    const form = new CciErrorManagementSignOffForm(
      validationEmitter,
      initialData
    );

    emitSpy.mockClear();

    form.controls.check.controls.errorResolutionDetails
      .setValue('');

    expect(form.status).toBe('INVALID');
    expect(emitSpy).toHaveBeenCalledTimes(1);
    expect(emitSpy).toHaveBeenCalledWith(false);
  });

  it('should not emit again when validation status is unchanged', () => {
    const form = new CciErrorManagementSignOffForm(
      validationEmitter,
      null
    );

    emitSpy.mockClear();

    form.controls.check.controls.errorResolutionDetails
      .setValue('');

    form.controls.check.controls.allReturnedMessagesManaged
      .setValue(null);

    expect(form.status).toBe('INVALID');
    expect(emitSpy).not.toHaveBeenCalled();
  });

  it('should emit true again when an invalid form is corrected', () => {
    const form = new CciErrorManagementSignOffForm(
      validationEmitter,
      null
    );

    form.controls.check.controls.errorResolutionDetails
      .setValue('Error resolved');

    form.controls.check.controls.allReturnedMessagesManaged
      .setValue(true);

    form.controls.check.controls.errorResolutionDetails
      .setValue('');

    form.controls.check.controls.errorResolutionDetails
      .setValue('Updated resolution details');

    expect(emitSpy.mock.calls.map(([value]) => value))
      .toEqual([false, true, false, true]);
  });

  it('should consider false valid with Validators.required', () => {
    const initialData = createInitialData(
      'Resolution details provided',
      false
    );

    const form = new CciErrorManagementSignOffForm(
      validationEmitter,
      initialData
    );

    expect(form.status).toBe('VALID');
    expect(emitSpy).toHaveBeenCalledWith(true);
  });
});
