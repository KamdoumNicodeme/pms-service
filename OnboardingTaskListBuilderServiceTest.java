private isControllingPerson(client: IThirdParty): boolean {
  const roles = client.roleTypes ?? [];

  return roles.includes('TRUSTEE') && roles.includes('EBO');
}

readonly controllingPersons = computed(() =>
  (this.getClientProfilingData()
    ?.initialBusinessData
    ?.policy
    ?.clients ?? [])
    .filter(client => this.isControllingPerson(client))
);
