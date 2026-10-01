readonly coreHolders: Signal<IThirdParty[]> = computed((): IThirdParty[] => {
  const clients: IThirdParty[] = this.getBusinessData()?.clients ?? [];

  return clients.filter((client: IThirdParty) => {
    const isHolder = client.roleTypes?.includes('Holder') ?? false;
    const isControllingPerson = this.isControllingPerson(client);

    console.log('----- CLIENT -----');
    console.log('thirdPartyId:', client.thirdPartyId);
    console.log('name:', client.name);
    console.log('roleTypes:', client.roleTypes);
    console.log('isHolder:', isHolder);
    console.log('isControllingPerson:', isControllingPerson);
    console.log('KEEP:', isHolder && !isControllingPerson);

    return isHolder && !isControllingPerson;
  });
});
