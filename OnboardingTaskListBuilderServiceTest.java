buildBase(data: IClientProfilingData): IChangeClientInformation {
  const savedChangeClientInformation: IChangeClientInformation | null =
    data.changeClientInformation ?? null;

  const policy: IPolicy =
    savedChangeClientInformation?.policy ??
    data.initialBusinessData.policy;

  return {
    policy: {
      ...structuredClone(policy),

      clients: structuredClone(
        (policy.clients ?? []).filter(
          (client: IThirdParty): boolean =>
            this.isRelevantClient(client)
        )
      ),
    },
  };
}
