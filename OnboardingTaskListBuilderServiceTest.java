@Injectable()
export class ClientProfilingChangeService {

  buildBase(
    data: IClientProfilingData
  ): IChangeClientInformation {
    const policy: IPolicy =
      data.changeClientInformation?.policy ??
      data.initialBusinessData.policy;

    return {
      policy: structuredClone(policy),
    };
  }

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

          case 'number':
            if (client.idDocument) {
              client.idDocument.number =
                resolution.value ?? null;
            }
            break;

          default:
            break;
        }
      }
    );

    return result;
  }
}
