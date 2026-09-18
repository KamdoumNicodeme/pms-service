if (client.type === 'MORAL_PERSON') {

    console.log(
        '[MORAL BEFORE]',
        structuredClone(client)
    );

    console.log(
        '[MORAL CHANGES]',
        [...changes.entries()]
    );

    this.applyMoralPersonChanges(
        client as IMoralPerson,
        changes
    );

    console.log(
        '[MORAL AFTER]',
        structuredClone(client)
    );

    return result;
}
