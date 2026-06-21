
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class NbCountryOfWealthRiskTest {

    private static final MockedStatic<ChecklistUtils> checklistUtils = Mockito.mockStatic(ChecklistUtils.class);

    private Map<String,List<String>> overallCaseRisk;
    private BusinessTransaction transaction;

    @BeforeEach
    void resetBeforeTest() {

        transaction = TransactionBuilderServiceHelper.createTransaction();
        checklistUtils.reset();

        overallCaseRisk = new HashMap<>();
        overallCaseRisk.put(BLOCKED, new ArrayList<>());
        overallCaseRisk.put(HIGH, new ArrayList<>());
    }

    @AfterAll
    static void closeStaticMocks() {

        checklistUtils.close();
    }

    @ParameterizedTest
    @ValueSource(strings = { HIGH, VERY_HIGH })
    void countryOfWealth_High_OK(String riskLevel) {

        transaction.getRiskFactorResults().forEach(risk -> {
            if (ESC_RF_001.equals(risk.getReference())) {
                risk.setRiskLevel(riskLevel);
                risk.setData("ESC_RF_001 data");
            }
        });

        ChecklistUtilsHelper.mockChecklistFieldValue(checklistUtils, RISK_VALUE, null);

        CaseRisk result = NbdCountryOfWealthRisk.countryOfWealth(null, transaction, overallCaseRisk);

        assertEquals(CaseRisk.CASE_RISK_HIGH, result);
        assertEquals(1, overallCaseRisk.get(HIGH).size());
        assertEquals(0, overallCaseRisk.get(BLOCKED).size());
        assertEquals("EBO/Originator country of wealth generation : ESC_RF_001 data", overallCaseRisk.get(HIGH).getFirst());
    }

    @Test
    void countryOfWealth_Medium_OK() {

        transaction.getRiskFactorResults().forEach(risk -> {
            if (ESC_RF_001.equals(risk.getReference())) {
                risk.setRiskLevel(MEDIUM);
                risk.setData("ESC_RF_001 data");
            }
        });

        ChecklistUtilsHelper.mockChecklistFieldValue(checklistUtils, RISK_VALUE, null);

        CaseRisk result = NbdCountryOfWealthRisk.countryOfWealth(null, transaction, overallCaseRisk);

        assertEquals(CaseRisk.CASE_RISK_MEDIUM, result);
        assertEquals(0, overallCaseRisk.get(HIGH).size());
        assertEquals(0, overallCaseRisk.get(BLOCKED).size());
    }

    @Test
    void countryOfWealth_Standard_OK() {

        transaction.getRiskFactorResults().forEach(risk -> {
            if (ESC_RF_001.equals(risk.getReference())) {
                risk.setRiskLevel(STANDARD);
                risk.setData("ESC_RF_001 data");
            }
        });

        ChecklistUtilsHelper.mockChecklistFieldValue(checklistUtils, RISK_VALUE, null);

        CaseRisk result = NbdCountryOfWealthRisk.countryOfWealth(null, transaction, overallCaseRisk);

        assertEquals(CaseRisk.CASE_RISK_STANDARD, result);
        assertEquals(0, overallCaseRisk.get(HIGH).size());
        assertEquals(0, overallCaseRisk.get(BLOCKED).size());
    }

    @Test
    void countryOfWealth_MissingRiskFactorData_Blocked_OK() {

        transaction.getRiskFactorResults().forEach(risk -> {
            if (ESC_RF_001.equals(risk.getReference())) {
                risk.setRiskLevel(HIGH);
                risk.setData("Missing ESC_RF_001 data");
            }
        });

        ChecklistUtilsHelper.mockChecklistFieldValue(checklistUtils, RISK_VALUE, null);

        CaseRisk result = NbdCountryOfWealthRisk.countryOfWealth(null, transaction, overallCaseRisk);

        assertEquals(CaseRisk.CASE_RISK_STANDARD, result);
        assertEquals(0, overallCaseRisk.get(HIGH).size());
        assertEquals(1, overallCaseRisk.get(BLOCKED).size());
        assertEquals("Missing ESC_RF_001 data", overallCaseRisk.get(BLOCKED).getFirst());
    }

    @ParameterizedTest
    @ValueSource(strings = { HIGH, MEDIUM, STANDARD })
    void countryOfWealth_ForcedRiskFromRiskValue_OK(String caseValue) {

        transaction.getRiskFactorResults().forEach(risk -> {
            if (ESC_RF_001.equals(risk.getReference())) {
                risk.setRiskLevel(HIGH);
                risk.setData("ESC_RF_001 data");
            }
        });

        ScreenDescription screenDescription = createFakeScreenDescription(caseValue);

        ChecklistUtils.getFieldById(screenDescription, RISK_VALUE).ifPresent(field -> field.setSelectedValue(caseValue));

        CaseRisk result = NbdCountryOfWealthRisk.countryOfWealth(screenDescription, transaction, overallCaseRisk);

        assertEquals(RulesUtils.resolveForcedCaseRisk(caseValue), result);
        assertEquals(0, overallCaseRisk.get(HIGH).size());
        assertEquals(0, overallCaseRisk.get(BLOCKED).size());
    }

    private ScreenDescription createFakeScreenDescription(String riskValue) {

        TextInputField riskField = TextInputField.builder().fieldId(RISK_VALUE).selectedValue(riskValue).build();

        Group group = Group.builder().groupId("CASE_RISK").fields(new ArrayList<>(List.of(riskField))).build();

        Tab tab = Tab.builder().tabId("CHECKLIST").groups(new ArrayList<>(List.of(group))).build();

        return ScreenDescription.builder().screenId("TEST").tabs(new ArrayList<>(List.of(tab))).build();
    }
}
