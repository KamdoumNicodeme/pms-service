@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class PhIndividualNotEboRiskTest {

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
    void individualNotEbo_High_OK() {
        ScreenDescription sd = createScreenDescription(null, "PH Type=Physical", YES);

        CaseRisk result = PhIndividualNotEboRisk.individualNotEbo(sd, overallCaseRisk);

        assertEquals(CaseRisk.CASE_RISK_HIGH, result);
        assertEquals(1, overallCaseRisk.get(HIGH).size());
        assertEquals("PH is an individual and is not the EBO", overallCaseRisk.get(HIGH).getFirst());
    }

    @Test
    void individualNotEbo_Standard_WhenPhNotPhysical_OK() {
        ScreenDescription sd = createScreenDescription(null, "PH Type=Moral", YES);

        CaseRisk result = PhIndividualNotEboRisk.individualNotEbo(sd, overallCaseRisk);

        assertEquals(CaseRisk.CASE_RISK_STANDARD, result);
        assertEquals(0, overallCaseRisk.get(HIGH).size());
    }

    @Test
    void individualNotEbo_Standard_WhenPhDifferentToEboNo_OK() {
        ScreenDescription sd = createScreenDescription(null, "PH Type=Physical", NO);

        CaseRisk result = PhIndividualNotEboRisk.individualNotEbo(sd, overallCaseRisk);

        assertEquals(CaseRisk.CASE_RISK_STANDARD, result);
        assertEquals(0, overallCaseRisk.get(HIGH).size());
    }

    @ParameterizedTest
    @ValueSource(strings = { HIGH, MEDIUM, STANDARD })
    void individualNotEbo_ForcedRiskFromRiskValue_OK(String caseValue) {
        ScreenDescription sd = createScreenDescription(caseValue, "PH Type=Physical", YES);

        CaseRisk result = PhIndividualNotEboRisk.individualNotEbo(sd, overallCaseRisk);

        assertEquals(RulesUtils.resolveForcedCaseRisk(caseValue), result);
        assertEquals(0, overallCaseRisk.get(HIGH).size());
        assertEquals(0, overallCaseRisk.get(BLOCKED).size());
    }

    private ScreenDescription createScreenDescription(
            String riskValue,
            String phType,
            String phDifferentToEbo
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
                .fieldId(PH_DIFFERENT_TO_EBO)
                .selectedValue(phDifferentToEbo)
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
