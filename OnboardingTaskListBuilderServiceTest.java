createWorkingCopy(
  data: IClientProfilingData
): IChangeClientInformation {

  const base: IChangeClientInformation = {
    policy: structuredClone(data.initialBusinessData.policy)
  } as IChangeClientInformation;

  const existing = data.changeClientInformation;

  if (!existing?.policy) {
    return base;
  }

  return {
    ...base,
    ...structuredClone(existing),

    policy: {
      ...base.policy,
      ...structuredClone(existing.policy),

      clients: (base.policy.clients ?? []).map(
        (baseClient: IThirdParty): IThirdParty => {

          const changedClient = existing.policy.clients?.find(
            (client: IThirdParty): boolean =>
              client.thirdPartyId === baseClient.thirdPartyId
          );

          if (!changedClient) {
            return baseClient;
          }

          return {
            ...baseClient,
            ...structuredClone(changedClient)
          } as IThirdParty;
        }
      )
    }
  };
}
