private getWorkingChangeClientInformation(
  data: IClientProfilingData
): IChangeClientInformation {

  const pending =
    this.pendingChangeClientInformation();

  // Des modifications ont déjà été faites dans CLIP
  if (pending) {
    return pending;
  }

  // Connect nous a envoyé un CCI :
  // on conserve son contenu et on travaille dessus.
  if (data.changeClientInformation) {
    return structuredClone(
      data.changeClientInformation
    );
  }

  // Connect n'a rien envoyé :
  // surtout PAS de copie du Snapshot.
  return this.changeService.createEmpty(data);
}
