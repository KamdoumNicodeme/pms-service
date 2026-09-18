protected onHolderChanges(event: HolderChanges): void {
    const data: IClientProfilingData | null = this.getClientProfilingData();

    if (!data) {
        return;
    }

    // 1. On part du pending s'il existe.
    // Sinon on initialise depuis les données backend.
    const current: IChangeClientInformation =
        this.pendingChangeClientInformation()
            ? structuredClone(this.pendingChangeClientInformation()!)
            : this.changeService.buildBase(data);

    // 2. On applique uniquement les changements reçus
    // sur l'état déjà modifié.
    const updated: IChangeClientInformation =
        this.changeService.applyHolderChanges(
            current,
            event.thirdPartyId,
            event.changes
        );

    // 3. Le résultat devient notre nouvel état de travail.
    this.pendingChangeClientInformation.set(updated);

    console.log(
        'CHANGE CLIENT INFORMATION AFTER HOLDER CHANGE',
        structuredClone(updated)
    );
}
