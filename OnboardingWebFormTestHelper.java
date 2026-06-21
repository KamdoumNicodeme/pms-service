@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NbdPremiumThresholdRisk {
    
    public static CaseRisk premiumThreshold(final ScreenDescription screenDescription, final Map<String,List<String>> overallCaseRisk) {

        CaseRisk forcedValue;

        final BigDecimal transactionAmount = getBigDecimal(screenDescription, EXPECTED_PREM_EUR);
        final BigDecimal policiesAmount = getBigDecimal(screenDescription, TOTAL_NAV_POLICY_EUR);
        final BigDecimal bePoliciesAmount = getBigDecimal(screenDescription, TOTAL_BE_NAV_POLICY_EUR);

        final String businessOrigin = getValue(screenDescription, BUSINESS_ORIGIN);
        final String contractType = getValue(screenDescription, CONTRACT_TYPE);

        if (!"BE".equals(businessOrigin)) {
            forcedValue = evaluateNonBePremiumThreshold(transactionAmount, policiesAmount, overallCaseRisk);
        } else {
            forcedValue = evaluateBePremiumThreshold(transactionAmount, bePoliciesAmount, contractType, overallCaseRisk);
        }

        return forcedValue;
    }

    private static CaseRisk evaluateNonBePremiumThreshold(final BigDecimal transactionAmount, final BigDecimal policiesAmount,
            final Map<String,List<String>> overallCaseRisk) {

        final BigDecimal firstSlice = new BigDecimal("2500000.0");
        final BigDecimal secondSlice = new BigDecimal("10000000.0");

        if (isSecondSliceThresholdReached(transactionAmount, policiesAmount, secondSlice)) {
            addRiskReason(overallCaseRisk, HIGH, "Premium amount threshold met");
            return CaseRisk.CASE_RISK_HIGH;
        }

        if (isFirstSliceThresholdReached(transactionAmount, policiesAmount, firstSlice)) {
            return CaseRisk.CASE_RISK_MEDIUM;
        }

        return CaseRisk.CASE_RISK_STANDARD;
    }

    private static CaseRisk evaluateBePremiumThreshold(final BigDecimal transactionAmount, final BigDecimal bePoliciesAmount,
            final String contractType, final Map<String,List<String>> overallCaseRisk) {

        final ThresholdConfig thresholdConfig = getBeThresholdConfig(contractType);

        if (thresholdConfig == null) {
            return CaseRisk.CASE_RISK_STANDARD;
        }

        final BigDecimal sum = transactionAmount.add(bePoliciesAmount);

        if (sum.compareTo(thresholdConfig.firstSlice()) < 0) {
            return CaseRisk.CASE_RISK_STANDARD;
        }

        if (sum.compareTo(thresholdConfig.maxSlice()) >= 0
                && isSecondSliceThresholdReached(transactionAmount, bePoliciesAmount, thresholdConfig.secondSlice())) {

            addRiskReason(overallCaseRisk, HIGH, thresholdConfig.reason());
            return CaseRisk.CASE_RISK_HIGH;
        }

        if (isFirstSliceThresholdReached(transactionAmount, bePoliciesAmount, thresholdConfig.firstSlice())) {
            return CaseRisk.CASE_RISK_MEDIUM;
        }

        return CaseRisk.CASE_RISK_STANDARD;
    }

    private static ThresholdConfig getBeThresholdConfig(final String contractType) {

        if ("Investment policy".equals(contractType)) {
            return new ThresholdConfig(new BigDecimal("1000000.0"), new BigDecimal("5000000.0"), new BigDecimal("5000000.0"),
                    "Premium amount threshold met (BE investment)");
        }

        if ("Capitalised policy".equals(contractType)) {
            return new ThresholdConfig(new BigDecimal("2500000.0"), new BigDecimal("5000000.0"), new BigDecimal("10000000.0"),
                    "Premium amount threshold met (BE capitalized)");
        }

        return null;
    }

    private static boolean isSecondSliceThresholdReached(final BigDecimal transactionAmount, final BigDecimal policiesAmount,
            final BigDecimal secondSlice) {

        return transactionAmount.add(policiesAmount.remainder(secondSlice)).divide(secondSlice).compareTo(BigDecimal.ONE) >= 0;
    }

    private static boolean isFirstSliceThresholdReached(final BigDecimal transactionAmount, final BigDecimal policiesAmount,
            final BigDecimal firstSlice) {

        return transactionAmount.compareTo(firstSlice) >= 0
                || transactionAmount.compareTo(firstSlice) < 0 && transactionAmount.add(policiesAmount).compareTo(firstSlice) >= 0;
    }

    private static BigDecimal getBigDecimal(final ScreenDescription screenDescription, final String fieldId) {

        return ChecklistUtils.getFieldById(screenDescription, fieldId).map(ChecklistUtils::getFieldValue).filter(value -> !value.isBlank())
                .map(BigDecimal::new).orElse(BigDecimal.ZERO);
    }

    private static String getValue(final ScreenDescription screenDescription, final String fieldId) {

        return ChecklistUtils.getFieldById(screenDescription, fieldId).map(ChecklistUtils::getFieldValue).orElse(null);
    }

    private static void addRiskReason(final Map<String,List<String>> overallCaseRisk, final String risk, final String reason) {

        overallCaseRisk.computeIfAbsent(risk, key -> new ArrayList<>()).add(reason);
    }

    private record ThresholdConfig(BigDecimal firstSlice, BigDecimal secondSlice, BigDecimal maxSlice, String reason) {
    }
}
