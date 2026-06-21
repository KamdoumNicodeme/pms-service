@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class CountryOfWealthRiskTest {

    static private final MockedStatic<ChecklistUtils> checklistUtils = Mockito.mockStatic(ChecklistUtils.class);
    private Map<String,List<String>> risk;
    private BusinessTransaction transaction;

    @BeforeEach
    void resetBeforeTest() {

        transaction = TransactionBuilderServiceHelper.createTransaction();
        checklistUtils.reset();
        risk = Map.of(BLOCKED, new ArrayList<>(), HIGH, new ArrayList<>());
    }

    @AfterAll
    static void closeStaticMocks() {

        checklistUtils.close();
    }

    @ParameterizedTest
    @MethodSource("highOkParameters")
    void buildCountryOfWealthRisk_High_OK(String kycMandatory, String kycObtained, String sameOrigin, String newOrigin,
            String ESC_RF_001RiskLevel) {

        transaction.getRiskFactorResults().forEach(risk -> {
            if (ESC_RF_001.equals(risk.getReference())) {
                risk.setRiskLevel(ESC_RF_001RiskLevel);
                risk.setData("ESC_RF_001 data");
            }
        });
        ChecklistUtilsHelper.mockChecklistFieldValue(checklistUtils, KYC_MANDATORY, kycMandatory);
        ChecklistUtilsHelper.mockChecklistFieldValue(checklistUtils, NEW_KYC_OBTAINED, kycObtained);
        ChecklistUtilsHelper.mockChecklistFieldValue(checklistUtils, IS_SAME_ORIGIN_PREMIUM, sameOrigin);
        ChecklistUtilsHelper.mockChecklistFieldValue(checklistUtils, NEW_ORIGIN_PREM_CLASS, newOrigin);

        String countryOfWealthData =
                transaction.getRiskFactorResults().stream().filter(risk -> ESC_RF_001.equals(risk.getReference())).toList().getFirst().getData();
        CaseRisk result = CountryOfWealthRisk.countryOfWealth(null, transaction, risk);
        assertEquals(CaseRisk.CASE_RISK_HIGH, result);
        assertEquals(1, risk.get(HIGH).size());
        assertEquals(0, risk.get(BLOCKED).size());
        assertEquals("EBO/Originator country of wealth generation : " + countryOfWealthData, risk.get(HIGH).getFirst());
    }

    @ParameterizedTest
    @MethodSource("standardOkParameters")
    void buildCountryOfWealthRisk_Standard_OK(String kycMandatory, String kycObtained, String sameOrigin, String newOrigin,
            String ESC_RF_001RiskLevel) {

        transaction.getRiskFactorResults().forEach(risk -> {
            if (ESC_RF_001.equals(risk.getReference())) {
                risk.setRiskLevel(ESC_RF_001RiskLevel);
            }
        });
        ChecklistUtilsHelper.mockChecklistFieldValue(checklistUtils, KYC_MANDATORY, kycMandatory);
        ChecklistUtilsHelper.mockChecklistFieldValue(checklistUtils, NEW_KYC_OBTAINED, kycObtained);
        ChecklistUtilsHelper.mockChecklistFieldValue(checklistUtils, IS_SAME_ORIGIN_PREMIUM, sameOrigin);
        ChecklistUtilsHelper.mockChecklistFieldValue(checklistUtils, NEW_ORIGIN_PREM_CLASS, newOrigin);

        CaseRisk result = CountryOfWealthRisk.countryOfWealth(null, transaction, risk);
        assertEquals(CaseRisk.CASE_RISK_STANDARD, result);
        assertEquals(0, risk.get(HIGH).size());

    }

    @Test
    void buildCountryOfWealthListRisk_Medium_OK() {

        transaction.getRiskFactorResults().forEach(risk -> {
            if (ESC_RF_001.equals(risk.getReference())) {
                risk.setRiskLevel("Medium");
            }
        });
        ChecklistUtilsHelper.mockChecklistFieldValue(checklistUtils, KYC_MANDATORY, YES);
        ChecklistUtilsHelper.mockChecklistFieldValue(checklistUtils, NEW_KYC_OBTAINED, YES);
        ChecklistUtilsHelper.mockChecklistFieldValue(checklistUtils, NEW_ORIGIN_PREM_CLASS, YES);

        CaseRisk result = CountryOfWealthRisk.countryOfWealth(null, transaction, risk);
        assertEquals(CaseRisk.CASE_RISK_MEDIUM, result);
        assertEquals(0, risk.get(HIGH).size());
        assertEquals(0, risk.get(BLOCKED).size());
    }

    @Test
    void buildCountryOfWealthListRisk_MissingESCRF001_OK() {

        transaction.getRiskFactorResults().forEach(risk -> {
            if (ESC_RF_001.equals(risk.getReference())) {
                risk.setData("Missing data");
                risk.setRiskLevel("HIGH");
            }
        });
        ChecklistUtilsHelper.mockChecklistFieldValue(checklistUtils, KYC_MANDATORY, YES);
        ChecklistUtilsHelper.mockChecklistFieldValue(checklistUtils, NEW_KYC_OBTAINED, YES);
        ChecklistUtilsHelper.mockChecklistFieldValue(checklistUtils, NEW_ORIGIN_PREM_CLASS, YES);

        String countryOfWealthData =
                transaction.getRiskFactorResults().stream().filter(risk -> ESC_RF_001.equals(risk.getReference())).toList().getFirst().getData();

        CaseRisk result = CountryOfWealthRisk.countryOfWealth(null, transaction, risk);
        assertEquals(CaseRisk.CASE_RISK_STANDARD, result);
        assertEquals(0, risk.get(HIGH).size());
        assertEquals(1, risk.get(BLOCKED).size());
        assertEquals(countryOfWealthData, risk.get(BLOCKED).getFirst());
    }

    static Stream<Arguments> highOkParameters() {

        return Stream.of(Arguments.of(YES, YES, YES, YES, HIGH), Arguments.of(YES, YES, YES, YES, VERY_HIGH),
                Arguments.of(YES, YES, NO, YES, HIGH), Arguments.of(YES, YES, NO, YES, VERY_HIGH), Arguments.of(YES, NO, YES, NO, HIGH),
                Arguments.of(NO, YES, YES, NO, HIGH), Arguments.of(NO, NO, YES, NO, HIGH), Arguments.of(NO, NO, NO, YES, HIGH),
                Arguments.of(NO, NO, YES, YES, HIGH));
    }

    static Stream<Arguments> standardOkParameters() {

        return Stream.of(Arguments.of(YES, YES, YES, NO, HIGH), Arguments.of(YES, YES, YES, NO, VERY_HIGH),
                Arguments.of(YES, YES, YES, NO, MEDIUM), Arguments.of(YES, YES, YES, NO, STANDARD), Arguments.of(YES, YES, NO, YES, STANDARD),
                Arguments.of(YES, YES, NO, NO, STANDARD), Arguments.of(NO, NO, NO, NO, STANDARD));
    }
}
