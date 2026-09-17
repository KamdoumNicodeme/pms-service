private isPhysicalPerson(
  client: IThirdParty
): client is IPhysicalPerson {
  return client.type === 'PHYSICAL_PERSON';
}
