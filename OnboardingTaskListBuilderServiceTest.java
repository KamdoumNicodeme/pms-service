private addressGroup(
  key: string,
  label: string,
  address: IAddress | null | undefined
): ComparisonGroupFieldDto {

  return {
    key,
    label,
    kind: 'group',

    lines: [
      ['no', 'address'],
      ['postCode', 'town'],
      ['country'],
    ],

    fields: [
      this.scalarField(
        'no',
        'Number',
        address?.no
      ),

      this.scalarField(
        'address',
        'Street',
        address?.address
      ),

      this.scalarField(
        'postCode',
        'Post code',
        address?.postCode
      ),

      this.scalarField(
        'town',
        'Town',
        address?.town
      ),

      this.scalarField(
        'country',
        'Country',
        this.normalizeCountry(address?.country)
      ),
    ],
  };
}
