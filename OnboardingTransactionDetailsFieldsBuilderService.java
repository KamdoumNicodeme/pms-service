@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class NegativeFindingThirdPartyRiskTest {

    private static final MockedStatic<ChecklistUtils> checklistUtils =
            Mockito.mockStatic(ChecklistUtils.class);

    private Map<String, List<String>> overallCaseRisk;
    private BusinessTransaction transaction;

    @BeforeEach
    void resetBeforeTest() {

        checklistUtils.reset();

        checklistUtils.when(() ->
                ChecklistUtils.getFieldById(any(ScreenDescription.class), anyString()))
                .thenCallRealMethod();

        checklistUtils.when(() ->
                ChecklistUtils.getFieldValue(any(Field.class)))
                .thenCallRealMethod();

        transaction = TransactionBuilderServiceHelper.createTransaction();

        overallCaseRisk = new HashMap<>();
        overallCaseRisk.put(HIGH, new ArrayList<>());
        overallCaseRisk.put(BLOCKED, new ArrayList<>());
    }

    @AfterAll
    static void closeStaticMocks() {
        checklistUtils.close();
    }

    @Test
    void premiumNaturalPerson_High_OK() {

        ScreenDescription sd = createScreenDescription(null, YES);

        CaseRisk result =
                NegativeFindingThirdPartyRisk.premiumNaturalPerson(
                        sd,
                        transaction,
                        overallCaseRisk);

        assertEquals(CaseRisk.CASE_RISK_HIGH, result);
        assertEquals(1, overallCaseRisk.get(HIGH).size());

        assertEquals(
                "Negative media coverage / World-Check match",
                overallCaseRisk.get(HIGH).getFirst());
    }

    @Test
    void premiumNaturalPerson_Standard_WhenNoNegativeFinding_OK() {

        ScreenDescription sd = createScreenDescription(null, NO);

        CaseRisk result =
                NegativeFindingThirdPartyRisk.premiumNaturalPerson(
                        sd,
                        transaction,
                        overallCaseRisk);

        assertEquals(CaseRisk.CASE_RISK_STANDARD, result);
        assertEquals(0, overallCaseRisk.get(HIGH).size());
    }

    @ParameterizedTest
    @CsvSource({
            "HIGH,CASE_RISK_HIGH",
            "MEDIUM,CASE_RISK_MEDIUM",
            "FORCED_MEDIUM,CASE_RISK_MEDIUM",
            "STANDARD,CASE_RISK_STANDARD"
    })
    void premiumNaturalPerson_ForcedRisk_OK(
            String riskValue,
            CaseRisk expectedRisk) {

        ScreenDescription sd =
                createScreenDescription(riskValue, YES);

        CaseRisk result =
                NegativeFindingThirdPartyRisk.premiumNaturalPerson(
                        sd,
                        transaction,
                        overallCaseRisk);

        assertEquals(expectedRisk, result);

        assertEquals(0, overallCaseRisk.get(HIGH).size());
        assertEquals(0, overallCaseRisk.get(BLOCKED).size());
    }

    private ScreenDescription createScreenDescription(
            String riskValue,
            String negativeValue) {

        List<Field> fields = new ArrayList<>();

        fields.add(TextInputField.builder()
                .fieldId(RISK_VALUE)
                .selectedValue(riskValue)
                .build());

        fields.add(BooleanInputField.builder()
                .fieldId(NEGATIVE_FINDING)
                .displayIf(Boolean.TRUE.toString())
                .selectedValue(negativeValue)
                .build());

        fields.add(BooleanInputField.builder()
                .fieldId(NEGATIVE_FINDING_PAYERS)
                .displayIf(Boolean.TRUE.toString())
                .selectedValue(NO)
                .build());

        fields.add(BooleanInputField.builder()
                .fieldId(NEGATIVE_SINCE_TRANSACTION)
                .displayIf(Boolean.TRUE.toString())
                .selectedValue(NO)
                .build());

        fields.add(BooleanInputField.builder()
                .fieldId(ORIGINATOR_WORLD_CHECK)
                .displayIf(Boolean.TRUE.toString())
                .selectedValue(NO)
                .build());

        fields.add(BooleanInputField.builder()
                .fieldId(NEGATIVE_FINDING_THIRD_PARTY)
                .displayIf(Boolean.TRUE.toString())
                .selectedValue(NO)
                .build());

        Group group = Group.builder()
                .groupId("CASE_RISK")
                .fields(fields)
                .build();

        Tab tab = Tab.builder()
                .tabId("CHECKLIST")
                .groups(List.of(group))
                .build();

        return ScreenDescription.builder()
                .screenId("TEST")
                .tabs(List.of(tab))
                .build();
    }
}
