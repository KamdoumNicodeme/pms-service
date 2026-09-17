public removeNationality(
  changeClientInformation: IChangeClientInformation,
  thirdPartyId: string,
  id: string
): IChangeClientInformation {

  const result = structuredClone(changeClientInformation);

  const client = result.policy.clients.find(
    (item: IThirdParty) => item.thirdPartyId === thirdPartyId
  );

  if (!client || client.type !== 'PHYSICAL_PERSON') {
    return result;
  }

  const physicalPerson = client as IPhysicalPerson;

  if (!physicalPerson.nationality) {
    return result;
  }

  const manualIndex = Number(
    id.replace('nationalities:manual-', '')
  );

  if (manualIndex === 1) {
    // If third exists, shift it to second
    physicalPerson.nationality.second =
      physicalPerson.nationality.third;

    physicalPerson.nationality.third = undefined;
  }

  if (manualIndex === 2) {
    physicalPerson.nationality.third = undefined;
  }

  return result;
}

protected onNationalityDeleted(
  thirdPartyId: string,
  id: string
): void {

  const current = this.pendingChangeClientInformation();

  if (!current) {
    return;
  }

  const updated =
    this.changeService.removeNationality(
      current,
      thirdPartyId,
      id
    );

  this.pendingChangeClientInformation.set(updated);

  console.log(
    'CHANGE CLIENT INFORMATION AFTER DELETE',
    updated
  );
}
