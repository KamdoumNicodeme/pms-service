private String resolveAccountOpenedInEea(
        final ScreenDescription sd,
        final Map<String, List<String>> overallCaseRisk,
        final int position) {

    List<String> eeaCountries = overallCaseRisk != null
            ? overallCaseRisk.getOrDefault("EEA", Collections.emptyList())
            : Collections.emptyList();

    String accountOpenedCountry = ChecklistUtils
            .getFieldById(sd, ACCOUNT_OPENED_COUNTRY + "_" + position)
            .map(ChecklistUtils::getFieldValue)
            .orElse("");

    return eeaCountries.contains(accountOpenedCountry) ? "YES" : "NO";
}
