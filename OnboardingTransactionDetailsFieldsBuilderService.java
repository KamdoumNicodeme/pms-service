@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class NoEvidenceEntityRiskTest {

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
    void noEvidence_High_OK() {
        ScreenDescription sd = createScreenDescription(
                null,
                "true",
                "PH Type=Moral",
                YES,
                NO
        );

        CaseRisk result = NoEvidenceEntityRisk.noEvidence(sd, overallCaseRisk);

        assertEquals(CaseRisk.CASE_RISK_HIGH, result);
        assertEquals(1, overallCaseRisk.get(HIGH).size());
        assertEquals(
                "No evidence that the legal entity is known by tax authorities - moral person",
                overallCaseRisk.get(HIGH).getFirst()
        );
    }

    @Test
    void noEvidence_Standard_WhenPhLegalEntityArrangementNotDisplayed_OK() {
        ScreenDescription sd = createScreenDescription(
                null,
                "false",
                "PH Type=Moral",
                YES,
                NO
        );

        CaseRisk result = NoEvidenceEntityRisk.noEvidence(sd, overallCaseRisk);

        assertEquals(CaseRisk.CASE_RISK_STANDARD, result);
        assertEquals(0, overallCaseRisk.get(HIGH).size());
    }

    @Test
    void noEvidence_Standard_WhenPhTypePhysical_OK() {
        ScreenDescription sd = createScreenDescription(
                null,
                "true",
                "PH Type=Physical",
                YES,
                NO
        );

        CaseRisk result = NoEvidenceEntityRisk.noEvidence(sd, overallCaseRisk);

        assertEquals(CaseRisk.CASE_RISK_STANDARD, result);
        assertEquals(0, overallCaseRisk.get(HIGH).size());
    }

    @Test
    void noEvidence_Standard_WhenArrangementNo_OK() {
        ScreenDescription sd = createScreenDescription(
                null,
                "true",
                "PH Type=Moral",
                NO,
                NO
        );

        CaseRisk result = NoEvidenceEntityRisk.noEvidence(sd, overallCaseRisk);

        assertEquals(CaseRisk.CASE_RISK_STANDARD, result);
        assertEquals(0, overallCaseRisk.get(HIGH).size());
    }

    @Test
    void noEvidence_Standard_WhenEvidenceYes_OK() {
        ScreenDescription sd = createScreenDescription(
                null,
                "true",
                "PH Type=Moral",
                YES,
                YES
        );

        CaseRisk result = NoEvidenceEntityRisk.noEvidence(sd, overallCaseRisk);

        assertEquals(CaseRisk.CASE_RISK_STANDARD, result);
        assertEquals(0, overallCaseRisk.get(HIGH).size());
    }

    @ParameterizedTest
    @ValueSource(strings = { HIGH, MEDIUM, STANDARD })
    void noEvidence_ForcedRiskFromRiskValue_OK(String caseValue) {
        ScreenDescription sd = createScreenDescription(
                caseValue,
                "true",
                "PH Type=Moral",
                YES,
                NO
        );

        CaseRisk result = NoEvidenceEntityRisk.noEvidence(sd, overallCaseRisk);

        assertEquals(RulesUtils.resolveForcedCaseRisk(caseValue), result);
        assertEquals(0, overallCaseRisk.get(HIGH).size());
        assertEquals(0, overallCaseRisk.get(BLOCKED).size());
    }

    private ScreenDescription createScreenDescription(
            String riskValue,
            String phLegalEntityArrangementDisplayIf,
            String phType,
            String phLegalEntityArrangement,
            String evidence
    ) {
        List<Field> fields = new ArrayList<>();

        fields.add(TextInputField.builder()
                .fieldId(RISK_VALUE)
                .selectedValue(riskValue)
                .build());

        fields.add(SelectInputField.builder()
                .fieldId(PH_LEGAL_ENTITY_ARRANGEMENT)
                .displayIf(phLegalEntityArrangementDisplayIf)
                .selectedValue(phLegalEntityArrangement)
                .build());

        fields.add(TextInputField.builder()
                .fieldId(PH_TYPE_ASSESSMENT)
                .selectedValue(phType)
                .build());

        fields.add(SelectInputField.builder()
                .fieldId(EVIDENCE_KNOWN_LEGAL_ENTITY)
                .selectedValue(evidence)
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
