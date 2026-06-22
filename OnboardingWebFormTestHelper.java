@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class PremiumNaturalPersonRiskOnboardingTest {

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
    void premiumNaturalPerson_High_OK() {
        ScreenDescription sd = createScreenDescription(
                null,
                "PH Type=Physical",
                YES,
                YES
        );

        CaseRisk result = PremiumNaturalPersonRiskOnboarding.premiumNaturalPerson(sd, overallCaseRisk);

        assertEquals(CaseRisk.CASE_RISK_HIGH, result);
        assertEquals(1, overallCaseRisk.get(HIGH).size());
        assertEquals("Premium is paid from a natural person", overallCaseRisk.get(HIGH).getFirst());
    }

    @Test
    void premiumNaturalPerson_Standard_WhenPhNotPhysical_OK() {
        ScreenDescription sd = createScreenDescription(
                null,
                "PH Type=Moral",
                YES,
                YES
        );

        CaseRisk result = PremiumNaturalPersonRiskOnboarding.premiumNaturalPerson(sd, overallCaseRisk);

        assertEquals(CaseRisk.CASE_RISK_STANDARD, result);
        assertEquals(0, overallCaseRisk.get(HIGH).size());
    }

    @Test
    void premiumNaturalPerson_Standard_WhenPhLegalEntityNo_OK() {
        ScreenDescription sd = createScreenDescription(
                null,
                "PH Type=Physical",
                NO,
                YES
        );

        CaseRisk result = PremiumNaturalPersonRiskOnboarding.premiumNaturalPerson(sd, overallCaseRisk);

        assertEquals(CaseRisk.CASE_RISK_STANDARD, result);
        assertEquals(0, overallCaseRisk.get(HIGH).size());
    }

    @Test
    void premiumNaturalPerson_Standard_WhenPaidFromAppointedNo_OK() {
        ScreenDescription sd = createScreenDescription(
                null,
                "PH Type=Physical",
                YES,
                NO
        );

        CaseRisk result = PremiumNaturalPersonRiskOnboarding.premiumNaturalPerson(sd, overallCaseRisk);

        assertEquals(CaseRisk.CASE_RISK_STANDARD, result);
        assertEquals(0, overallCaseRisk.get(HIGH).size());
    }

    @ParameterizedTest
    @ValueSource(strings = { HIGH, MEDIUM, STANDARD })
    void premiumNaturalPerson_ForcedRiskFromRiskValue_OK(String caseValue) {
        ScreenDescription sd = createScreenDescription(
                caseValue,
                "PH Type=Physical",
                YES,
                YES
        );

        CaseRisk result = PremiumNaturalPersonRiskOnboarding.premiumNaturalPerson(sd, overallCaseRisk);

        assertEquals(RulesUtils.resolveForcedCaseRisk(caseValue), result);
        assertEquals(0, overallCaseRisk.get(HIGH).size());
        assertEquals(0, overallCaseRisk.get(BLOCKED).size());
    }

    private ScreenDescription createScreenDescription(
            String riskValue,
            String phType,
            String phLegalEntity,
            String paidFromAppointed
    ) {
        List<Field> fields = new ArrayList<>();

        fields.add(TextInputField.builder()
                .fieldId(RISK_VALUE)
                .selectedValue(riskValue)
                .build());

        fields.add(TextInputField.builder()
                .fieldId(PH_TYPE_ASSESSMENT)
                .selectedValue(phType)
                .build());

        fields.add(SelectInputField.builder()
                .fieldId(PH_LEGAL_ENTITY)
                .selectedValue(phLegalEntity)
                .build());

        fields.add(SelectInputField.builder()
                .fieldId(PAID_FROM_APPOINTED)
                .selectedValue(paidFromAppointed)
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
