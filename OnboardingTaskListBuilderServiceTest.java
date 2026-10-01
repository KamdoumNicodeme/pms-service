protected coreHolderOf(digitalHolder: IThirdParty): IThirdParty | null {
  console.log('🔥 coreHolderOf CALLED', digitalHolder);

  const businessData = this.getBusinessData();

  console.log('🔥 BUSINESS DATA:', businessData);
  console.log('🔥 BUSINESS CLIENTS:', businessData?.clients);

  const holders = businessData?.clients?.filter(
    (client: IThirdParty) =>
      client.roleTypes?.includes('Holder')
  ) ?? [];

  console.log('🔥 HOLDERS:', holders);

  return holders.find(
    (holder: IThirdParty) =>
      holder.thirdPartyId === digitalHolder.thirdPartyId
  ) ?? null;
}
