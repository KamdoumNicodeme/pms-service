protected onHolderChanges(event: HolderChanges): void {

  const data: IClientProfilingData | null =
    this.getClientProfilingData();

  if (!data) {
    return;
  }

  /*
   * IMPORTANT :
   *
   * On ne repart PAS de pendingChangeClientInformation
   * pour le holder qui vient de changer.
   *
   * On repart des données initiales du backend puis on applique
   * l'état ACTUEL de son store.
   *
   * Ainsi :
   *
   * Core DE
   * + manual FR
   * + manual BE
   *
   * => DE / FR / BE
   *
   * Si FR est supprimé du store :
   *
   * Core DE
   * + manual BE
   *
   * => DE / BE
   *
   * FR disparaît automatiquement.
   */

  const holderBase: IChangeClientInformation =
    this.changeService.buildBase(data);

  const rebuiltHolderState: IChangeClientInformation =
    this.changeService.applyHolderChanges(
      holderBase,
      event.thirdPartyId,
      event.changes
    );

  /*
   * Maintenant on conserve les modifications éventuelles
   * déjà effectuées sur les AUTRES holders.
   */
  const current: IChangeClientInformation =
    this.pendingChangeClientInformation()
      ? structuredClone(this.pendingChangeClientInformation()!)
      : this.changeService.buildBase(data);

  const rebuiltClient: IThirdParty | undefined =
    rebuiltHolderState.policy.clients.find(
      (client: IThirdParty) =>
        client.thirdPartyId === event.thirdPartyId
    );

  if (!rebuiltClient) {
    console.warn(
      '[ClientProfilingComponent] Rebuilt client not found:',
      event.thirdPartyId
    );

    return;
  }

  const clientIndex: number =
    current.policy.clients.findIndex(
      (client: IThirdParty) =>
        client.thirdPartyId === event.thirdPartyId
    );

  if (clientIndex === -1) {
    console.warn(
      '[ClientProfilingComponent] Client not found in pending data:',
      event.thirdPartyId
    );

    return;
  }

  /*
   * On remplace uniquement le holder concerné.
   *
   * Les modifications des autres holders restent intactes.
   */
  current.policy.clients[clientIndex] =
    structuredClone(rebuiltClient);

  this.pendingChangeClientInformation.set(current);

  console.log(
    'CHANGE CLIENT INFORMATION AFTER HOLDER CHANGE',
    structuredClone(current)
  );
}
