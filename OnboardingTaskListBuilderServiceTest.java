private ensureSendingAddress(
  result: IChangeClientInformation
): void {
  console.log('BEFORE HOLDER:', result.policy.holder);

  result.policy.holder ??= {} as IThirdParty;

  console.log('AFTER HOLDER:', result.policy.holder);

  result.policy.holder.sendingAddress ??= {} as SendingAddress;

  result.policy.holder.sendingAddress.address ??= {} as Address;
}
