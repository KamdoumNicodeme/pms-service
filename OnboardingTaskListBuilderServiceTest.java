buildBase(data: IClientProfilingData): IChangeClientInformation {
  return {
    policy: structuredClone(data.initialBusinessData.policy)
  };
}
