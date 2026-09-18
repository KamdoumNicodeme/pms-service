protected onHolderChanges(
    event: HolderChanges
): void {

    const data: IClientProfilingData | null =
        this.getClientProfilingData();

    if (!data) {
        return;
    }

    /*
     * Store the current state of this section and retrieve
     * all changes currently applied to this holder.
     */
    const allHolderChanges: ReadonlyMap<string, Resolution> =
        this.updateHolderSectionChanges(event);

    /*
     * Rebuild the holder from the original backend data.
     * This guarantees that removed manual entries disappear.
     */
    const base: IChangeClientInformation =
        this.changeService.buildBase(data);

    const rebuilt: IChangeClientInformation =
        this.changeService.applyHolderChanges(
            base,
            event.thirdPartyId,
            allHolderChanges
        );

    /*
     * Preserve pending modifications made to other holders.
     */
    const current: IChangeClientInformation =
        this.pendingChangeClientInformation()
            ? structuredClone(
                this.pendingChangeClientInformation()!
            )
            : this.changeService.buildBase(data);

    const rebuiltClient: IThirdParty | undefined =
        rebuilt.policy.clients.find(
            (client: IThirdParty): boolean =>
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
            (client: IThirdParty): boolean =>
                client.thirdPartyId === event.thirdPartyId
        );

    if (clientIndex === -1) {
        console.warn(
            '[ClientProfilingComponent] Client not found:',
            event.thirdPartyId
        );

        return;
    }

    /*
     * Replace only the current holder.
     */
    current.policy.clients[clientIndex] =
        structuredClone(rebuiltClient);

    this.pendingChangeClientInformation.set(current);

    console.log(
        'HOLDER:',
        event.thirdPartyId
    );

    console.log(
        'SECTION:',
        event.sectionId
    );

    console.log(
        'ALL HOLDER CHANGES:',
        allHolderChanges
    );

    console.log(
        'CHANGE CLIENT INFORMATION AFTER HOLDER CHANGE:',
        structuredClone(current)
    );
}
