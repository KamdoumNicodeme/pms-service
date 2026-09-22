buildBase(data: IClientProfilingData): IChangeClientInformation {
  const policy = data.initialBusinessData.policy;

  return {
    policy: {
      ...structuredClone(policy),

      clients: structuredClone(
        (policy.clients ?? []).filter(
          (client: IThirdParty) => this.isRelevantClient(client)
        )
      ),
    },
  };
}

private isRelevantClient(client: IThirdParty): boolean {
  const roles = client.roleTypes ?? [];

  const isHolder = roles.includes('Holder');

  const isControllingPerson =
    roles.includes('Economic_Beneficiary_Owner') &&
    roles.includes('Trustee');

  return isHolder || isControllingPerson;
}
