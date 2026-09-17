applyHolderChanges(
  changeClientInformation: IChangeClientInformation,
  thirdPartyId: string,
  changes: ReadonlyMap<string, Resolution>
): IChangeClientInformation {

  const result: IChangeClientInformation =
    structuredClone(changeClientInformation);

  const client: IThirdParty | undefined =
    result.policy.clients.find(
      (item: IThirdParty) =>
        item.thirdPartyId === thirdPartyId
    );

  if (!client) {
    return result;
  }

  changes.forEach(
    (resolution: Resolution, fieldId: string): void => {

      switch (fieldId) {

        case 'number': {
          if (!this.isPhysicalPerson(client)) {
            break;
          }

          client.idDocument.number =
            resolution.value ?? '';

          break;
        }

        default:
          break;
      }
    }
  );

  return result;
}
