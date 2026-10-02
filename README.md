

  console.log('REMOVE MANUAL ENTRY');

  console.log('fieldKey:', fieldKey);

  console.log('entryKey:', entryKey);

  console.log('id:', id);

  console.log(

    'manualEntries BEFORE:',

    structuredClone(this.manualEntries())

  );

  const manualEntry: ManualEntry | undefined =

    this.manualEntries().find(

      (entry: ManualEntry): boolean =>

        entry.fieldKey === fieldKey &&

        entry.entryKey === entryKey

    );

  console.log(

    'manualEntry FOUND:',

    structuredClone(manualEntry)

  );
