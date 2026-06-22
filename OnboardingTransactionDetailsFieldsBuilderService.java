@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class PayerPayeeBankCountryRiskTest {

    private static final MockedStatic<ChecklistUtils> checklistUtils =
            Mockito.mockStatic(ChecklistUtils.class);

    private Map<String, List<String>> overallCaseRisk;
    private BusinessTransaction transaction;

    @BeforeEach
    void resetBeforeTest() {
        checklistUtils.reset();
        checklistUtils.when(() -> ChecklistUtils.getFieldById(any(ScreenDescription.class), anyString()))
                .thenCallRealMethod();
        checklistUtils.when(() -> ChecklistUtils.getFieldValue(any(Field.class)))
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
    void payerPayee_High_OK() {
        setRiskFactorLevel(INT_RF_012, HIGH);

        ScreenDescription sd = createScreenDescription(null, "SOME_VALUE");

        CaseRisk result = PayerPayeeBankCountryRisk.payerPayee(
                sd,
                transaction,
                overallCaseRisk
        );

        assertEquals(CaseRisk.CASE_RISK_HIGH, result);
        assertEquals(1, overallCaseRisk.get(HIGH).size());
        assertEquals("Payer / Payee bank country", overallCaseRisk.get(HIGH).getFirst());
    }

    @Test
    void payerPayee_Medium_OK() {
        setRiskFactorLevel(INT_RF_012, MEDIUM);

        ScreenDescription sd = createScreenDescription(null, "SOME_VALUE");

        CaseRisk result = PayerPayeeBankCountryRisk.payerPayee(
                sd,
                transaction,
                overallCaseRisk
        );

        assertEquals(CaseRisk.CASE_RISK_MEDIUM, result);
        assertEquals(0, overallCaseRisk.get(HIGH).size());
    }

    @Test
    void payerPayee_Standard_WhenRiskLevelStandard_OK() {
        setRiskFactorLevel(INT_RF_012, STANDARD);

        ScreenDescription sd = createScreenDescription(null, "SOME_VALUE");

        CaseRisk result = PayerPayeeBankCountryRisk.payerPayee(
                sd,
                transaction,
                overallCaseRisk
        );

        assertEquals(CaseRisk.CASE_RISK_STANDARD, result);
        assertEquals(0, overallCaseRisk.get(HIGH).size());
    }

    @Test
    void payerPayee_Standard_WhenOriginatingBankCountryRiskEmpty_OK() {
        setRiskFactorLevel(INT_RF_012, HIGH);

        ScreenDescription sd = createScreenDescription(null, null);

        CaseRisk result = PayerPayeeBankCountryRisk.payerPayee(
                sd,
                transaction,
                overallCaseRisk
        );

        assertEquals(CaseRisk.CASE_RISK_STANDARD, result);
        assertEquals(0, overallCaseRisk.get(HIGH).size());
    }

    @ParameterizedTest
    @ValueSource(strings = { HIGH, MEDIUM, STANDARD })
    void payerPayee_ForcedRiskFromRiskValue_OK(String caseValue) {
        setRiskFactorLevel(INT_RF_012, HIGH);

        ScreenDescription sd = createScreenDescription(caseValue, "SOME_VALUE");

        CaseRisk result = PayerPayeeBankCountryRisk.payerPayee(
                sd,
                transaction,
                overallCaseRisk
        );

        assertEquals(RulesUtils.resolveForcedCaseRisk(caseValue), result);
        assertEquals(0, overallCaseRisk.get(HIGH).size());
        assertEquals(0, overallCaseRisk.get(BLOCKED).size());
    }

    private void setRiskFactorLevel(String reference, String riskLevel) {
        transaction.getRiskFactorResults().forEach(risk -> {
            if (reference.equals(risk.getReference())) {
                risk.setRiskLevel(riskLevel);
            }
        });
    }

    private ScreenDescription createScreenDescription(
            String riskValue,
            String originatingBankCountryRiskValue
    ) {
        List<Field> fields = new ArrayList<>();

        fields.add(TextInputField.builder()
                .fieldId(RISK_VALUE)
                .selectedValue(riskValue)
                .build());

        fields.add(TextInputField.builder()
                .fieldId(ORIGINATING_BANK_COUNTRY_RISK)
                .selectedValue(originatingBankCountryRiskValue)
                .build());

        Group group = Group.builder()
                .groupId("CASE_RISK")
                .fields(fields)
                .build();

        Tab tab = Tab.builder()
                .tabId("CHECKLIST")
                .groups(new ArrayList<>(List.of(group)))
                .build();

        return ScreenDescription.builder()
                .screenId("TEST")
                .tabs(new ArrayList<>(List.of(tab)))
                .build();
    }
}
