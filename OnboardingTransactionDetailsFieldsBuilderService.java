@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ThirdPartyPaymentNoEvidenceLegalRiskTest {

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
    void thirdPartyLegalNoEvidence_High_OK() {
        ScreenDescription sd = createScreenDescription(
                null,
                "true",
                YES,
                NO
        );

        CaseRisk result =
                ThirdPartyPaymentNoEvidenceLegalRisk.thirdPartyLegalNoEvidence(sd, overallCaseRisk);

        assertEquals(CaseRisk.CASE_RISK_HIGH, result);
        assertEquals(1, overallCaseRisk.get(HIGH).size());
        assertEquals(
                "3rd party payment - No evidence that the legal entity is known by tax authorities",
                overallCaseRisk.get(HIGH).getFirst()
        );
    }

    @Test
    void thirdPartyLegalNoEvidence_Standard_WhenPayerNotLocatedNotDisplayed_OK() {
        ScreenDescription sd = createScreenDescription(
                null,
                "false",
                YES,
                NO
        );

        CaseRisk result =
                ThirdPartyPaymentNoEvidenceLegalRisk.thirdPartyLegalNoEvidence(sd, overallCaseRisk);

        assertEquals(CaseRisk.CASE_RISK_STANDARD, result);
        assertEquals(0, overallCaseRisk.get(HIGH).size());
    }

    @Test
    void thirdPartyLegalNoEvidence_Standard_WhenPayerNotLocatedNo_OK() {
        ScreenDescription sd = createScreenDescription(
                null,
                "true",
                NO,
                NO
        );

        CaseRisk result =
                ThirdPartyPaymentNoEvidenceLegalRisk.thirdPartyLegalNoEvidence(sd, overallCaseRisk);

        assertEquals(CaseRisk.CASE_RISK_STANDARD, result);
        assertEquals(0, overallCaseRisk.get(HIGH).size());
    }

    @Test
    void thirdPartyLegalNoEvidence_Standard_WhenEvidenceYes_OK() {
        ScreenDescription sd = createScreenDescription(
                null,
                "true",
                YES,
                YES
        );

        CaseRisk result =
                ThirdPartyPaymentNoEvidenceLegalRisk.thirdPartyLegalNoEvidence(sd, overallCaseRisk);

        assertEquals(CaseRisk.CASE_RISK_STANDARD, result);
        assertEquals(0, overallCaseRisk.get(HIGH).size());
    }

    @ParameterizedTest
    @ValueSource(strings = { HIGH, MEDIUM, STANDARD })
    void thirdPartyLegalNoEvidence_ForcedRiskFromRiskValue_OK(String caseValue) {
        ScreenDescription sd = createScreenDescription(
                caseValue,
                "true",
                YES,
                NO
        );

        CaseRisk result =
                ThirdPartyPaymentNoEvidenceLegalRisk.thirdPartyLegalNoEvidence(sd, overallCaseRisk);

        assertEquals(RulesUtils.resolveForcedCaseRisk(caseValue), result);
        assertEquals(0, overallCaseRisk.get(HIGH).size());
        assertEquals(0, overallCaseRisk.get(BLOCKED).size());
    }

    private ScreenDescription createScreenDescription(
            String riskValue,
            String payerNotLocatedDisplayIf,
            String payerNotLocated,
            String evidence
    ) {
        List<Field> fields = new ArrayList<>();

        fields.add(TextInputField.builder()
                .fieldId(RISK_VALUE)
                .selectedValue(riskValue)
                .build());

        fields.add(SelectInputField.builder()
                .fieldId(PAYER_NOT_LOCATED)
                .displayIf(payerNotLocatedDisplayIf)
                .selectedValue(payerNotLocated)
                .build());

        fields.add(SelectInputField.builder()
                .fieldId(EVIDENCE_LEGAL_ENTITY)
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
