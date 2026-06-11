public class PremiumNaturalPersonRisk {

    private static final String HIGH = "HIGH";
    private static final String MEDIUM = "MEDIUM";
    private static final String STANDARD = "STANDARD";
    private static final String YES = "YES";
    private static final String PHYSICAL = "PH Type=Physical";

    public static CaseRisk premiumNaturalPerson(
            final ScreenDescription screenDescription,
            final BusinessTransaction transaction,
            final Map<String, List<String>> overallCaseRisk) {

        String caseValue = ChecklistUtils.getFieldById(screenDescription, RISK_VALUE)
                .map(ChecklistUtils::getFieldValue)
                .orElse(null);

        if (HIGH.equals(caseValue)) {
            return CaseRisk.CASE_RISK_HIGH;
        }

        if (MEDIUM.equals(caseValue)) {
            return CaseRisk.CASE_RISK_MEDIUM;
        }

        if (STANDARD.equals(caseValue)) {
            return CaseRisk.CASE_RISK_STANDARD;
        }

        String phType = ChecklistUtils.getFieldById(screenDescription, PH_TYPE_ASSESSMENT)
                .map(ChecklistUtils::getFieldValue)
                .orElse(null);

        String phLegalEntity = ChecklistUtils.getFieldById(screenDescription, PH_LEGAL_ENTITY)
                .map(ChecklistUtils::getFieldValue)
                .orElse(null);

        String paidFromAppointed = ChecklistUtils.getFieldById(screenDescription, PAID_FROM_APPOINTED)
                .map(ChecklistUtils::getFieldValue)
                .orElse(null);

        if (PHYSICAL.equals(phType)
                && YES.equalsIgnoreCase(phLegalEntity)
                && YES.equalsIgnoreCase(paidFromAppointed)) {

            overallCaseRisk.get(HIGH).add("Premium is paid from a natural person");
            return CaseRisk.CASE_RISK_HIGH;
        }

        return CaseRisk.CASE_RISK_STANDARD;
    }
}
