import { ComponentFixture, TestBed } from '@angular/core/testing';
import { signal } from '@angular/core';

import { Check } from './check';
// Adapt this import to the exact path already used by check.ts.
import { COMPARISON_DATA_SERVICE } from 'PATH_TO_COMPARISON_DATA_SERVICE';

type ClientProfilingData = NonNullable<ReturnType<Check['clientProfilingData']>>;
type HolderSectionChanges = ReturnType<Check['holderSectionChanges']>;

function makeClientData(
  signatureInfo: Record<string, unknown> = {},
  otherTaskControls: Record<string, unknown> = {}
): ClientProfilingData {
  return {
    changeClientInformation: {
      taskControls: { signatureInfo, ...otherTaskControls }
    }
  } as unknown as ClientProfilingData;
}

describe('Check', () => {
  let fixture: ComponentFixture<Check>;
  let component: Check;

  const comparisonServiceMock = {
    getSignatureOptions: jest.fn(() => signal([
      { value: 'WET', label: 'Wet signature' },
      { value: 'ELECTRONIC', label: 'Electronic signature' },
      { value: 'MULTIPLE', label: 'Multiple signatures' }
    ])),
    getYesNoOptions: jest.fn(() => signal([
      { value: 'yes', label: 'Yes' },
      { value: 'no', label: 'No' }
    ]))
  };

  const flush = async (): Promise<void> => {
    fixture.detectChanges();
    await fixture.whenStable();
  };

  const render = async (
    data: ClientProfilingData = makeClientData()
  ): Promise<void> => {
    fixture.componentRef.setInput('clientProfilingData', data);
    await flush();
  };

  beforeEach(async () => {
    jest.clearAllMocks();

    await TestBed.configureTestingModule({
      imports: [Check],
      providers: [
        { provide: COMPARISON_DATA_SERVICE, useValue: comparisonServiceMock }
      ]
    })
      // We test Check's state and outputs, not NG-ZORRO's template.
      .overrideComponent(Check, { set: { template: '' } })
      .compileComponents();

    fixture = TestBed.createComponent(Check);
    component = fixture.componentInstance;
  });

  it('creates the component with all checklist controls', async () => {
    await render();

    expect(component).toBeTruthy();
    expect(component.form.contains('requestSignedByClient')).toBe(true);
    expect(component.form.contains('clientSignatureType')).toBe(true);
    expect(component.form.contains('newIndiciaDetected')).toBe(true);
    expect(component.form.contains('dataValid')).toBe(true);
    expect(comparisonServiceMock.getSignatureOptions).toHaveBeenCalled();
    expect(comparisonServiceMock.getYesNoOptions).toHaveBeenCalled();
  });

  it('initializes values saved in changeClientInformation.taskControls', async () => {
    await render(makeClientData(
      {
        requestSignedByClient: true,
        clientSignatureType: 'WET',
        signatureInLine: true,
        allThirdPartiesSigned: 'yes',
        date: '2026-11-17',
        place: 'Luxembourg'
      },
      {
        newIndiciaDetected: true,
        statusInvestigationInitiated: true,
        dataValid: true
      }
    ));

    expect(component.form.controls.requestSignedByClient.value).toBe(true);
    expect(component.form.controls.clientSignatureType.value).toBe('WET');
    expect(component.form.controls.date.value).toBe('2026-11-17');
    expect(component.form.controls.place.value).toBe('Luxembourg');
    expect(component.form.controls.newIndiciaDetected.value).toBe('yes');
    expect(component.form.controls.statusInvestigationInitiated.value).toBe(true);
    expect(component.form.controls.dataValid.value).toBe(true);
  });

  it('converts a previously saved false newIndiciaDetected into "no"', async () => {
    await render(makeClientData({}, { newIndiciaDetected: false }));
    expect(component.form.controls.newIndiciaDetected.value).toBe('no');
  });

  it('keeps fields for WET signature and clears them when changing to ELECTRONIC', async () => {
    await render(makeClientData({ clientSignatureType: 'WET' }));

    component.form.patchValue({
      signatureInLine: true,
      allThirdPartiesSigned: 'yes',
      date: '2026-11-17',
      place: 'Luxembourg'
    });
    await flush();

    expect(component.form.controls.date.value).toBe('2026-11-17');
    expect(component.form.controls.place.value).toBe('Luxembourg');

    component.form.controls.clientSignatureType.setValue('ELECTRONIC');
    await flush();

    expect(component.form.controls.signatureInLine.value).toBeNull();
    expect(component.form.controls.allThirdPartiesSigned.value).toBeNull();
    expect(component.form.controls.date.value).toBeNull();
    expect(component.form.controls.place.value).toBeNull();
  });

  it('clears partner-only controls when the provider is no longer PARTNER', async () => {
    await render(makeClientData({ clientSignatureType: 'ELECTRONIC' }));

    component.form.patchValue({
      provider: 'PARTNER',
      mfa: true,
      authorizedProvider: true
    });
    await flush();
    expect(component.form.controls.mfa.value).toBe(true);
    expect(component.form.controls.authorizedProvider.value).toBe(true);

    component.form.controls.provider.setValue('OTHER');
    await flush();

    expect(component.form.controls.mfa.value).toBeNull();
    expect(component.form.controls.authorizedProvider.value).toBeNull();
  });

  it('clears investigation status when newIndiciaDetected changes from yes to no', async () => {
    await render(makeClientData());

    component.form.patchValue({
      newIndiciaDetected: 'yes',
      statusInvestigationInitiated: true
    });
    await flush();
    expect(component.form.controls.statusInvestigationInitiated.value).toBe(true);

    component.form.controls.newIndiciaDetected.setValue('no');
    await flush();
    expect(component.form.controls.statusInvestigationInitiated.value).toBeNull();
  });

  it('shows address and country checks only when matching holder changes exist', async () => {
    await render(makeClientData());
    const before = component.total;

    const holderChanges = new Map([
      ['CLIENT-1', new Map([
        ['contact-details', new Map([
          ['legal-address.country', { source: 'manual', value: 'LU' }]
        ])]
      ])]
    ]) as unknown as HolderSectionChanges;

    fixture.componentRef.setInput('holderSectionChanges', holderChanges);
    await flush();

    expect(component.total).toBe(before + 2);

    component.form.patchValue({
      proofOfAddressReceived: true,
      taxCountryChecked: true
    });
    await flush();
    expect(component.form.controls.proofOfAddressReceived.value).toBe(true);
    expect(component.form.controls.taxCountryChecked.value).toBe(true);

    fixture.componentRef.setInput('holderSectionChanges', new Map());
    await flush();
    expect(component.total).toBe(before);
    expect(component.form.controls.proofOfAddressReceived.value).toBeNull();
    expect(component.form.controls.taxCountryChecked.value).toBeNull();
  });

  it('emits checksChanged with an ITaskControls-compatible payload', async () => {
    await render(makeClientData({ clientSignatureType: 'WET' }));
    const emitted = jest.fn();
    component.checksChanged.subscribe(emitted);

    component.form.patchValue({
      requestSignedByClient: true,
      date: '2026-11-17',
      place: 'Luxembourg',
      newIndiciaDetected: 'yes',
      dataValid: true
    });
    await flush();

    expect(emitted).toHaveBeenCalled();
    expect(emitted).toHaveBeenLastCalledWith(
      expect.objectContaining({
        signatureInfo: expect.objectContaining({
          requestSignedByClient: true,
          clientSignatureType: 'WET',
          date: '2026-11-17',
          place: 'Luxembourg'
        }),
        newIndiciaDetected: true,
        dataValid: true
      })
    );
  });

  it('emits newIndiciaDetected=false when the user selects no', async () => {
    await render();
    const emitted = jest.fn();
    component.checksChanged.subscribe(emitted);

    component.form.controls.newIndiciaDetected.setValue('no');
    await flush();

    expect(emitted).toHaveBeenLastCalledWith(
      expect.objectContaining({ newIndiciaDetected: false })
    );
  });

  it('emits checksCompleted=true when every visible WET control is filled', async () => {
    await render(makeClientData({ clientSignatureType: 'WET' }));
    const emitted = jest.fn();
    component.checksCompleted.subscribe(emitted);

    component.form.patchValue({
      requestSignedByClient: true,
      clientSignatureType: 'WET',
      signatureInLine: true,
      allThirdPartiesSigned: 'yes',
      date: '2026-11-17',
      place: 'Luxembourg',
      newIndiciaDetected: 'no',
      dataValid: true
    });
    await flush();

    expect(component.total).toBe(8);
    expect(component.completed).toBe(8);
    expect(emitted).toHaveBeenLastCalledWith(true);
  });

  it('emits checksCompleted=false when a visible answer is missing', async () => {
    await render(makeClientData({ clientSignatureType: 'WET' }));
    const emitted = jest.fn();
    component.checksCompleted.subscribe(emitted);

    component.form.patchValue({
      requestSignedByClient: true,
      clientSignatureType: 'WET',
      signatureInLine: true,
      allThirdPartiesSigned: 'yes',
      date: null,
      place: 'Luxembourg',
      newIndiciaDetected: 'no',
      dataValid: true
    });
    await flush();

    expect(component.completed).toBeLessThan(component.total);
    expect(emitted).toHaveBeenLastCalledWith(false);
  });

  it('disables and re-enables the form according to readOnly', async () => {
    await render();
    expect(component.form.enabled).toBe(true);

    fixture.componentRef.setInput('readOnly', true);
    await flush();
    expect(component.form.disabled).toBe(true);

    fixture.componentRef.setInput('readOnly', false);
    await flush();
    expect(component.form.enabled).toBe(true);
  });
});
