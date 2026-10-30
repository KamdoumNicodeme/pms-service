private hasAddressChanged(): boolean {
  const portalAddress =
    this.getClientProfilingData()?.clientPortalBusinessData?.policy.holder.sendingAddress?.address;

  const changedAddress =
    this.getClientProfilingData()?.changeClientInformation?.policy.holder.sendingAddress?.address;

  if (!portalAddress && !changedAddress) {
    return false;
  }

  if (!portalAddress || !changedAddress) {
    return true;
  }

  return (
    portalAddress.number !== changedAddress.number ||
    portalAddress.street !== changedAddress.street ||
    portalAddress.postcode !== changedAddress.postcode ||
    portalAddress.city !== changedAddress.city ||
    portalAddress.country !== changedAddress.country
  );
}
