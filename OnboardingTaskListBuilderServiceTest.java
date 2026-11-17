it('adds professional fields when current professional details exist', () => {
  const current = physical({
    professionalDetails: {
      profession: 'DEVELOPER',
      status: 'EMPLOYEE',
      companyName: 'ABC',
      sector: 'IT',
    },
  });

  const result = generalPhysical(
    physical(),
    null,
    null,
    current,
  );

  expect(result.fields.map(value => value.key)).toEqual(
    expect.arrayContaining([
      'profession',
      'profession-status',
      'employer-name',
      'industry-sector',
    ]),
  );

  expect(scalar(result, 'profession').values.clip).toBe('DEVELOPER');
  expect(scalar(result, 'profession').options).toEqual(professions);
});



it('does not add profession status, employer or sector when unemployed', () => {
  const current = physical({
    professionalDetails: {
      profession: 'unemployed',
      status: 'EMPLOYEE',
      companyName: 'ABC',
    },
  });

  const result = generalPhysical(
    physical(),
    null,
    null,
    current,
  );

  expect(result.fields.some(value => value.key === 'profession')).toBe(true);

  expect(result.fields.some(value => value.key === 'employer-name')).toBe(false);

  expect(result.fields.some(value => value.key === 'profession-status')).toBe(false);

  expect(result.fields.some(value => value.key === 'industry-sector')).toBe(false);
});
