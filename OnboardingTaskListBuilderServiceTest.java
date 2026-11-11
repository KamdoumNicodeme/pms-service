console.log(
  'NATIONALITIES IN SAVE PAYLOAD:',
  payload.policy?.clients
    ?.filter(client => this.isPhysicalPerson(client))
    .map(client => ({
      thirdPartyId: client.thirdPartyId,
      nationality: structuredClone(client.nationality)
    }))
);
