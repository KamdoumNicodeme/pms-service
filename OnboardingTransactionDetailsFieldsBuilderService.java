@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class NbdPaymentOutsideTaxCountryRiskTest {

    private static final MockedStatic<ChecklistUtils> checklistUtils = Mockito.mockStatic(ChecklistUtils.class);

    private Map<String,List<String>> overallCaseRisk;

    @BeforeEach
    void resetBeforeTest() {

        checklistUtils.reset();
        checklistUtils.reset();
        checklistUtils.when(() -> ChecklistUtils.getFieldById(any(ScreenDescription.class), anyString())).thenCallRealMethod();
        checklistUtils.when(() -> ChecklistUtils.getFieldValue(any(Field.class))).thenCallRealMethod();

        overallCaseRisk = new HashMap<>();
        overallCaseRisk.put(HIGH, new ArrayList<>());
        overallCaseRisk.put(BLOCKED, new ArrayList<>());
    }

    @AfterAll
    static void closeStaticMocks() {

        checklistUtils.close();
    }

    @Test
    void paymentOutsideTaxCountry_High_OK() {

        ScreenDescription sd = createScreenDescription(RISK_VALUE, "FR", "2", List.of("LU", "DE"), "BE");

        WebForm webForm = createWebForm("FOE");

        checklistUtils.when(() -> WebformUtils.getWebFormFieldValueById(any(WebForm.class), eq("BRANCH"))).thenCallRealMethod();
        CaseRisk result = NbdPaymentOutsideTaxCountryRisk.paymentOutsideTaxCountry(sd, overallCaseRisk, webForm);

        assertEquals(CaseRisk.CASE_RISK_HIGH, result);
        assertEquals(1, overallCaseRisk.get(HIGH).size());
        assertEquals("Payment outside of the tax country", overallCaseRisk.get(HIGH).getFirst());
        assertEquals(0, overallCaseRisk.get(BLOCKED).size());
    }

    @Test
    void paymentOutsideTaxCountry_Standard_WhenCountryInsideTaxCountry_OK() {

        ScreenDescription sd = createScreenDescription(null, "FR|LU", "2", List.of("LU", "FR"), "BE");

        WebForm webForm = createWebForm("FOE");

        CaseRisk result = NbdPaymentOutsideTaxCountryRisk.paymentOutsideTaxCountry(sd, overallCaseRisk, webForm);

        assertEquals(CaseRisk.CASE_RISK_STANDARD, result);
        assertEquals(0, overallCaseRisk.get(HIGH).size());
        assertEquals(0, overallCaseRisk.get(BLOCKED).size());
    }

    @Test
    void paymentOutsideTaxCountry_Standard_WhenNotBanch_OK() {

        ScreenDescription sd = createScreenDescription(null, "FR", "1", List.of("LU"), "BE");

        WebForm webForm = createWebForm("FOE");

        CaseRisk result = NbdPaymentOutsideTaxCountryRisk.paymentOutsideTaxCountry(sd, overallCaseRisk, webForm);

        assertEquals(CaseRisk.CASE_RISK_STANDARD, result);
        assertEquals(0, overallCaseRisk.get(HIGH).size());
    }

    @Test
    void paymentOutsideTaxCountry_Standard_WhenBusinessOriginNotBE_OK() {

        ScreenDescription sd = createScreenDescription(null, "FR", "1", List.of("LU"), "LU");

        WebForm webForm = createWebForm("FOE");

        CaseRisk result = NbdPaymentOutsideTaxCountryRisk.paymentOutsideTaxCountry(sd, overallCaseRisk, webForm);

        assertEquals(CaseRisk.CASE_RISK_STANDARD, result);
        assertEquals(0, overallCaseRisk.get(HIGH).size());
    }

    @ParameterizedTest
    @ValueSource(strings = { HIGH, MEDIUM, STANDARD })
    void paymentOutsideTaxCountry_ForcedRiskFromRiskValue_OK(String caseValue) {

        ScreenDescription sd = createScreenDescription(caseValue, "FR", "1", List.of("LU"), "BE");

        WebForm webForm = createWebForm("FOE");

        CaseRisk result = NbdPaymentOutsideTaxCountryRisk.paymentOutsideTaxCountry(sd, overallCaseRisk, webForm);

        assertEquals(RulesUtils.resolveForcedCaseRisk(caseValue), result);
        assertEquals(0, overallCaseRisk.get(HIGH).size());
        assertEquals(0, overallCaseRisk.get(BLOCKED).size());
    }

    private ScreenDescription createScreenDescription(String riskValue, String phFiscalCountry, String numberOfOriginatingAccounts,
            List<String> bankCountries, String businessOrigin) {

        List<Field> fields = new ArrayList<>();

        fields.add(TextInputField.builder().fieldId(RISK_VALUE).selectedValue(riskValue).build());

        fields.add(TextInputField.builder().fieldId(PH_FISCAL_COUNTRY).selectedValue(phFiscalCountry).build());

        fields.add(TextInputField.builder().fieldId(NUMBER_OF_ORIGINATING_ACCOUNTS).selectedValue(numberOfOriginatingAccounts).build());

        fields.add(SelectInputField.builder().fieldId(BUSINESS_ORIGIN).selectedValue(businessOrigin).build());

        for (int i = 0; i < bankCountries.size(); i++) {
            fields.add(
                    TextInputField.builder().fieldId(COUNTRY_OF_ORIGINATING_ACCOUNT + "_" + (i + 1)).selectedValue(bankCountries.get(i)).build());
        }

        Group group = Group.builder().groupId("CASE_RISK").fields(fields).build();

        Tab tab = Tab.builder().tabId("CHECKLIST").groups(new ArrayList<>(List.of(group))).build();

        return ScreenDescription.builder().screenId("TEST").tabs(new ArrayList<>(List.of(tab))).build();
    }

    private WebForm createWebForm(String banchValue) {

        var banchField = TextWebFormField.builder().fieldId("BANCH").value(banchValue).build();

        WebFormGroup group = WebFormGroup.builder().groupId("TEST_GROUP").textFields(new ArrayList<>(List.of(banchField))).build();

        return WebForm.builder().groups(new ArrayList<>(List.of(group))).build();
    }
}
