public readonly clientIds: Signal<string[]> = computed((): string[] => [
  ...this.snapshotHolders().map(
    (client: IThirdParty) => client.thirdPartyId
  ),
  ...this.controllingPersons().map(
    (client: IThirdParty) => client.thirdPartyId
  )
]);
