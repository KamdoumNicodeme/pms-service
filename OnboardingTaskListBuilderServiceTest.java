readonly coreHolder: InputSignal<IThirdParty> =
  input.required<IThirdParty>();

readonly digitalHolder: InputSignal<IThirdParty | null> =
  input<IThirdParty | null>(null);

readonly kycHolder: InputSignal<IThirdParty | null> =
  input<IThirdParty | null>(null);
