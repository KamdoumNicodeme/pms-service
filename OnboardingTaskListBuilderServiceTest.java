createEmpty(
  data: IClientProfilingData
): IChangeClientInformation {
  return {
    policy: {
      policyNumber:
        data.initialBusinessData.policy.policyNumber,
      clients: []
    }
  } as IChangeClientInformation;
}
