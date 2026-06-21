@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class NbdPremiumThresholdRiskTest {

    private static final MockedStatic<ChecklistUtils> checklistUtils =
            Mockito.mockStatic(ChecklistUtils.class);

    private Map<String, List<String>> overallCaseRisk;

    @BeforeEach
    void resetBeforeTest() {
        checklistUtils.reset();
        checklistUtils.when(() -> ChecklistUtils.getFieldById(any(ScreenDescription.class), anyString()))
                .thenCallRealMethod();
        checklistUtils.when(() -> ChecklistUtils.getFieldValue(any(Field.class)))
                .thenCallRealMethod();

        overallCaseRisk = new HashMap<>();
        overallCaseRisk.put(HIGH, new ArrayList<>());
        overallCaseRisk.put(BLOCKED, new ArrayList<>());
    }

    @AfterAll
    static void closeStaticMocks() {
        checklistUtils.close();
    }

    @Test
    void premiumThreshold_NonBe_High_OK() {
        ScreenDescription sd = createScreenDescription(
                "8000000",
                "3000000",
                "0",
                "LU",
                "Investment policy"
        );

        CaseRisk result = NbdPremiumThresholdRisk.premiumThreshold(sd, overallCaseRisk);

        assertEquals(CaseRisk.CASE_RISK_HIGH, result);
        assertEquals(1, overallCaseRisk.get(HIGH).size());
        assertEquals("Premium amount threshold met", overallCaseRisk.get(HIGH).getFirst());
    }

    @Test
    void premiumThreshold_NonBe_Medium_OK() {
        ScreenDescription sd = createScreenDescription(
                "2000000",
                "1000000",
                "0",
                "LU",
                "Investment policy"
        );

        CaseRisk result = NbdPremiumThresholdRisk.premiumThreshold(sd, overallCaseRisk);

        assertEquals(CaseRisk.CASE_RISK_MEDIUM, result);
        assertEquals(0, overallCaseRisk.get(HIGH).size());
    }

    @Test
    void premiumThreshold_NonBe_Standard_OK() {
        ScreenDescription sd = createScreenDescription(
                "1000000",
                "1000000",
                "0",
                "LU",
                "Investment policy"
        );

        CaseRisk result = NbdPremiumThresholdRisk.premiumThreshold(sd, overallCaseRisk);

        assertEquals(CaseRisk.CASE_RISK_STANDARD, result);
        assertEquals(0, overallCaseRisk.get(HIGH).size());
    }

    @Test
    void premiumThreshold_BeInvestment_High_OK() {
        ScreenDescription sd = createScreenDescription(
                "4000000",
                "0",
                "2000000",
                "BE",
                "Investment policy"
        );

        CaseRisk result = NbdPremiumThresholdRisk.premiumThreshold(sd, overallCaseRisk);

        assertEquals(CaseRisk.CASE_RISK_HIGH, result);
        assertEquals(1, overallCaseRisk.get(HIGH).size());
        assertEquals("Premium amount threshold met (BE investment)", overallCaseRisk.get(HIGH).getFirst());
    }

    @Test
    void premiumThreshold_BeInvestment_Medium_OK() {
        ScreenDescription sd = createScreenDescription(
                "800000",
                "0",
                "300000",
                "BE",
                "Investment policy"
        );

        CaseRisk result = NbdPremiumThresholdRisk.premiumThreshold(sd, overallCaseRisk);

        assertEquals(CaseRisk.CASE_RISK_MEDIUM, result);
        assertEquals(0, overallCaseRisk.get(HIGH).size());
    }

    @Test
    void premiumThreshold_BeCapitalised_High_OK() {
        ScreenDescription sd = createScreenDescription(
                "4000000",
                "0",
                "2000000",
                "BE",
                "Capitalised policy"
        );

        CaseRisk result = NbdPremiumThresholdRisk.premiumThreshold(sd, overallCaseRisk);

        assertEquals(CaseRisk.CASE_RISK_HIGH, result);
        assertEquals(1, overallCaseRisk.get(HIGH).size());
        assertEquals("Premium amount threshold met (BE capitalized)", overallCaseRisk.get(HIGH).getFirst());
    }

    @Test
    void premiumThreshold_BeCapitalised_Medium_OK() {
        ScreenDescription sd = createScreenDescription(
                "2000000",
                "0",
                "1000000",
                "BE",
                "Capitalised policy"
        );

        CaseRisk result = NbdPremiumThresholdRisk.premiumThreshold(sd, overallCaseRisk);

        assertEquals(CaseRisk.CASE_RISK_MEDIUM, result);
        assertEquals(0, overallCaseRisk.get(HIGH).size());
    }

    @Test
    void premiumThreshold_BeUnknownContractType_Standard_OK() {
        ScreenDescription sd = createScreenDescription(
                "10000000",
                "0",
                "10000000",
                "BE",
                "Unknown"
        );

        CaseRisk result = NbdPremiumThresholdRisk.premiumThreshold(sd, overallCaseRisk);

        assertEquals(CaseRisk.CASE_RISK_STANDARD, result);
        assertEquals(0, overallCaseRisk.get(HIGH).size());
    }

    private ScreenDescription createScreenDescription(
            String expectedPremEur,
            String totalNavPolicyEur,
            String totalBeNavPolicyEur,
            String businessOrigin,
            String contractType
    ) {
        List<Field> fields = new ArrayList<>();

        fields.add(TextInputField.builder()
                .fieldId(EXPECTED_PREM_EUR)
                .selectedValue(expectedPremEur)
                .build());

        fields.add(TextInputField.builder()
                .fieldId(TOTAL_NAV_POLICY_EUR)
                .selectedValue(totalNavPolicyEur)
                .build());

        fields.add(TextInputField.builder()
                .fieldId(TOTAL_BE_NAV_POLICY_EUR)
                .selectedValue(totalBeNavPolicyEur)
                .build());

        fields.add(SelectInputField.builder()
                .fieldId(BUSINESS_ORIGIN)
                .selectedValue(businessOrigin)
                .build());

        fields.add(SelectInputField.builder()
                .fieldId(CONTRACT_TYPE)
                .selectedValue(contractType)
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
