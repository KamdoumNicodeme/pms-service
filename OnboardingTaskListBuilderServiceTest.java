private applyManualTaxInformations(
    client: IPhysicalPerson,
    changes: ReadonlyMap<string, Resolution>
): void {

    console.log('===== TAX CHANGES =====');

    changes.forEach((resolution: Resolution, id: string): void => {
        if (id.startsWith('tax-information')) {
            console.log(
                'TAX ID:',
                id,
                'VALUE:',
                resolution.value,
                'RESOLUTION:',
                resolution
            );
        }
    });

    console.log('=======================');
}
