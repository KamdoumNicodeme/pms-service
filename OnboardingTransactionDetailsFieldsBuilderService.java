@ParameterizedTest
@ValueSource(strings = {HIGH, MEDIUM, STANDARD})
void countryOfWealth_ForcedRiskFromRiskValue_OK(String caseValue) {
    transaction.getRiskFactorResults().forEach(risk -> {
        if (ESC_RF_001.equals(risk.getReference())) {
            risk.setRiskLevel(HIGH);
            risk.setData("ESC_RF_001 data");
        }
    });

    ScreenDescription screenDescription =
            ScreenDescriptionBuilderServiceHelper.createChecklistScreenDescription();

    ChecklistUtils.getFieldById(screenDescription, RISK_VALUE)
            .ifPresent(field -> field.setSelectedValue(caseValue));

    CaseRisk result = MbCountryOfWealthRisk.countryOfWealth(
            screenDescription,
            transaction,
            overallCaseRisk
    );

    assertEquals(RulesUtils.resolveForcedCaseRisk(caseValue), result);
    assertEquals(0, overallCaseRisk.get(HIGH).size());
    assertEquals(0, overallCaseRisk.get(BLOCKED).size());
}
