private hasAddressChanged(): boolean {
  const portalAddress =
    this.getClientProfilingData()
      ?.clientPortalBusinessData
      ?.policy.holder
      ?.sendingAddress
      ?.address;

  const changedAddress =
    this.getClientProfilingData()
      ?.changeClientInformation
      ?.policy.holder
      ?.sendingAddress
      ?.address;

  return this.normalizeAddress(portalAddress) !==
         this.normalizeAddress(changedAddress);
}

private normalizeAddress(address?: IAddressSending): string {
  if (!address) {
    return '';
  }

  return JSON.stringify(
    Object.fromEntries(
      Object.entries(address)
        .filter(([, value]) => value !== null && value !== undefined && value !== '')
        .sort(([keyA], [keyB]) => keyA.localeCompare(keyB))
    )
  );
}
