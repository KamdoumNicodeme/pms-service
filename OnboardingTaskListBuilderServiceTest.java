const mostRecentDate = Math.max(
  ...nationalities.map(nationality => nationality.date)
);

const hasItalianMostRecentNationality = nationalities.some(
  nationality =>
    nationality.country === 'IT' &&
    nationality.date === mostRecentDate
);

return hasItalianMostRecentNationality
  ? [...rows]
  : rows.filter(
      row => row.key !== 'italian-birth-town-code'
    );
