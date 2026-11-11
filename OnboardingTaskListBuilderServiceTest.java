private applyItalianBirthTownCodeVisibility(
  rows: readonly ComparisonRow[]
): ComparisonRow[] {
  const manualNationalities = new Map<string, string>();

  for (const entry of this.manualEntries()) {
    if (entry.fieldKey === 'nationalities' && entry.value) {
      manualNationalities.set(entry.entryKey, entry.value);
    }
  }

  for (const entry of this.manualStructureEntries()) {
    if (entry.fieldKey !== 'nationalities') {
      continue;
    }

    const country = entry.fields.find(
      field => field.key === 'country'
    )?.value;

    if (country) {
      manualNationalities.set(entry.entryKey, country);
    }
  }

  const manualNationality = [...manualNationalities.values()].at(-1);

  if (!manualNationality || manualNationality === 'IT') {
    return [...rows];
  }

  return rows.filter(
    row => row.key !== 'italian-birth-town-code'
  );
}

console.log(
  'NATIONALITY CHANGES:',
  [...changes.entries()].filter(([key]) =>
    key.startsWith('nationalities')
  )
);

console.log(
  'NATIONALITY REMOVED:',
  removedEntries.filter(entry =>
    entry.id.startsWith('nationalities:')
  )
);

if (this.isPhysicalPerson(client)) {
  console.log(
    'NATIONALITY BEFORE APPLY:',
    structuredClone(client.nationality)
  );

  this.applyPhysicalPersonChanges(
    client,
    sourceHolder as IPhysicalPerson,
    changes
  );

  console.log(
    'NATIONALITY AFTER APPLY:',
    structuredClone(client.nationality)
  );
} else if (this.isMoralPerson(client)) {
  this.applyMoralPersonChanges(client, changes);
}

this.applyRemovedThirdPartyEntries(client, removedEntries);

if (this.isPhysicalPerson(client)) {
  console.log(
    'NATIONALITY AFTER REMOVAL:',
    structuredClone(client.nationality)
  );
}



///





protected onHolderChanges(event: HolderChanges): void {
  const data = this.getClientProfilingData();

  if (!data) {
    return;
  }

  const snapshotHolder = data.initialBusinessData.policy.clients?.find(
    (client: IThirdParty) => client.thirdPartyId === event.thirdPartyId
  );

  if (!snapshotHolder) {
    console.warn('Initialization snapshot holder not found:', event.thirdPartyId);
    return;
  }

  const allHolderChanges = this.updateHolderSectionChanges(event);
  const allRemovedEntries = this.updateHolderSectionRemovedEntries(event);

  const current: IChangeClientInformation =
    this.pendingChangeClientInformation() ??
    (data.changeClientInformation
      ? structuredClone(data.changeClientInformation)
      : this.changeService.createEmpty(data));

  const updated = structuredClone(current);

  updated.policy ??= { clients: [] } as unknown as IPolicy;

  updated.policy.clients = (updated.policy.clients ?? []).filter(
    (client: IThirdParty) => client.thirdPartyId !== event.thirdPartyId
  );

  const persistedHolder = data.changeClientInformation?.policy?.clients?.find(
    (client: IThirdParty) => client.thirdPartyId === event.thirdPartyId
  );

  if (persistedHolder) {
    updated.policy.clients.push(structuredClone(persistedHolder));
  }

  const result = this.changeService.applyHolderChanges(
    updated,
    snapshotHolder,
    allHolderChanges,
    allRemovedEntries
  );

  this.pendingChangeClientInformation.set(result);
}

private updateHolderSectionRemovedEntries(
  event: HolderChanges
): readonly RemovedEntry[] {
  const holders = new Map(this.holderSectionRemovedEntries());

  const sections = new Map<string, readonly RemovedEntry[]>(
    holders.get(event.thirdPartyId) ?? []
  );

  sections.set(event.sectionId, [...(event.removedEntries ?? [])]);
  holders.set(event.thirdPartyId, sections);

  this.holderSectionRemovedEntries.set(holders);

  return [...sections.values()].flatMap(entries => [...entries]);
}
