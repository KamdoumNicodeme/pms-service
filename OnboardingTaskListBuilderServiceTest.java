@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class OnboardingDueDiligenceFieldsBuilderServiceTest extends OnboardingFieldsBuilderTestSupport {

    @InjectMocks
    private OnboardingDueDiligenceFieldsBuilderService builderService;

    @Test
    void buildDueDiligenceFields_NoEBO_OK() {
        Policy policy = policy();
        policy.setEconomicBeneficiaries(null);

        Group group = group(DUE_DILIGENCE_GROUP);
        AtomicInteger order = new AtomicInteger(1);
        int expectedNumberOfFields = 21;

        builderService.buildField(webForm(), screenDescription(), group, policy, transaction(), overallCaseRisk());

        assertEquals(expectedNumberOfFields, group.getFields().size());

        FieldHelper.testMissingField(INDUSTRY + "_1", SelectInputField.class, group);
        FieldHelper.testMissingField(POSITION + "_1", SelectInputField.class, group);
        FieldHelper.testMissingField(ANNUAL_INCOME + "_1", TextInputField.class, group);
        FieldHelper.testMissingField(TOTAL_WEALTH + "_1", TextInputField.class, group);

        FieldHelper.testFieldValueAndIncr(BACKGROUND_DETAILS, TextAreaField.class, group, null, order, null, true, true);
        FieldHelper.testFieldValueAndIncr(RISK_ASSESSMENT, TextAreaField.class, group, null, order, null, true, true);
        FieldHelper.testFieldValueAndIncr(PEP, SelectInputField.class, group, NO, order, null, true, null);
        FieldHelper.testFieldValueAndIncr(IS_PEP_PAYER, SelectInputField.class, group, null, order, "false", true, true);
        FieldHelper.testFieldValueAndIncr(TCC_SIGNED, SelectInputField.class, group, null, order, null, true, true);
        FieldHelper.testFieldValueAndIncr(TCC_SIGNED_REFUSED, SelectInputField.class, group, null, order, "#TCC_SIGNED# == \"NO\"", true, true);
        FieldHelper.testFieldValueAndIncr(INTRODUCING_PARTNER_SIGNED, SelectInputField.class, group, null, order, null, true, true);
        FieldHelper.testFieldValueAndIncr(INFO_PROVIDED_VERIFIED, SelectInputField.class, group, null, order, null, true, true);
        FieldHelper.testFieldValueAndIncr(NEGATIVE_FINDING, SelectInputField.class, group, NO, order, null, true, false);
        FieldHelper.testFieldValueAndIncr(NEGATIVE_FINDING_THIRD_PARTY, TextInputField.class, group, null, order, "false", false, false);
        FieldHelper.testFieldValueAndIncr(AT_LEAST_ONE_SOW_OF_KIND, SelectInputField.class, group, NO, order, "false", true, false);
        FieldHelper.testFieldValueAndIncr(ORIGINATOR_WORLD_CHECK, SelectInputField.class, group, null, order, "false", true, true);
        FieldHelper.testFieldValueAndIncr(IS_ON_SANCTION_LIST, SelectInputField.class, group, null, order, null, true, false);
        FieldHelper.testFieldValueAndIncr(INSIDER, SelectInputField.class, group, NO, order, null, false, false);
        FieldHelper.testFieldValueAndIncr(IS_PH_EBO_GOLDEN_VISA, SelectInputField.class, group, null, order, null, true, false);
        FieldHelper.testFieldValueAndIncr(MINIMUM_WEALTH, SelectInputField.class, group, null, order, null, true, true);
        FieldHelper.testFieldValueAndIncr(WEALTH_ALLOCATION_OK, SelectInputField.class, group, null, order, null, true, true);
        FieldHelper.testFieldValueAndIncr(WEALTH_ALLOCATION, TextInputField.class, group, null, order, null, true, true);
        FieldHelper.testFieldValueAndIncr(KYC_SUPPORTING_DOCUMENTS, SelectInputField.class, group, null, order, null, true, true);

        assertEquals(expectedNumberOfFields, order.get() - 1);
    }

    @Test
    void buildDueDiligenceFields_OK() {
        Policy policy = PolicyBuilderServiceHelper.createPolicy();
        Group group = group(DUE_DILIGENCE_GROUP);
        AtomicInteger order = new AtomicInteger(1);
        int expectedNumberOfFields = 37;

        builderService.buildField(webForm(), screenDescription(), group, policy, transaction(), overallCaseRisk());

        assertEquals(expectedNumberOfFields, group.getFields().size());

        FieldHelper.testSelectFieldValue(INDUSTRY + "_1", SelectInputField.class, group, "adult", order.getAndIncrement(), null, true, null, 1);
        FieldHelper.testSelectFieldValue(POSITION + "_1", SelectInputField.class, group, null, order.getAndIncrement(), "false", null, null, 0);
        FieldHelper.testSelectFieldValue(INDUSTRY + "_2", SelectInputField.class, group, "agri", order.getAndIncrement(), null, true, null, 1);
        FieldHelper.testSelectFieldValue(POSITION + "_2", SelectInputField.class, group, null, order.getAndIncrement(), "false", null, null, 0);

        FieldHelper.testFieldValueAndIncr(BACKGROUND_DETAILS, TextAreaField.class, group, null, order, null, true, true);
        FieldHelper.testFieldValueAndIncr(RISK_ASSESSMENT, TextAreaField.class, group, null, order, null, true, true);
        FieldHelper.testFieldValueAndIncr(PEP, SelectInputField.class, group, NO, order, null, true, null);
        FieldHelper.testFieldValueAndIncr(IS_PEP_PAYER, SelectInputField.class, group, null, order, "false", true, true);
        FieldHelper.testFieldValueAndIncr(TCC_SIGNED, SelectInputField.class, group, null, order, null, true, true);
        FieldHelper.testFieldValueAndIncr(TCC_SIGNED_REFUSED, SelectInputField.class, group, null, order, "#TCC_SIGNED# == \"NO\"", true, true);
        FieldHelper.testFieldValueAndIncr(INTRODUCING_PARTNER_SIGNED, SelectInputField.class, group, null, order, null, true, true);
        FieldHelper.testFieldValueAndIncr(INFO_PROVIDED_VERIFIED, SelectInputField.class, group, null, order, null, true, true);
        FieldHelper.testFieldValueAndIncr(NEGATIVE_FINDING, SelectInputField.class, group, NO, order, null, true, false);
        FieldHelper.testFieldValueAndIncr(NEGATIVE_FINDING_THIRD_PARTY, TextInputField.class, group, null, order, "false", false, false);
        FieldHelper.testFieldValueAndIncr(AT_LEAST_ONE_SOW_OF_KIND, SelectInputField.class, group, NO, order, "false", true, false);
        FieldHelper.testFieldValueAndIncr(ORIGINATOR_WORLD_CHECK, SelectInputField.class, group, null, order, "false", true, true);
        FieldHelper.testFieldValueAndIncr(IS_ON_SANCTION_LIST, SelectInputField.class, group, null, order, null, true, false);
        FieldHelper.testFieldValueAndIncr(INSIDER, SelectInputField.class, group, NO, order, null, false, false);
        FieldHelper.testFieldValueAndIncr(IS_PH_EBO_GOLDEN_VISA, SelectInputField.class, group, null, order, null, true, false);

        FieldHelper.testFieldValueAndIncr(ANNUAL_INCOME + "_1", TextInputField.class, group, "N/A", order, null, null, null);
        FieldHelper.testFieldValueAndIncr(ANNUAL_INCOME + "_2", TextInputField.class, group, "N/A", order, null, null, null);
        FieldHelper.testFieldValueAndIncr(SOURCE_OF_WEALTH, TextInputField.class, group, null, order, null, false, true);
        FieldHelper.testFieldValueAndIncr(TWENTY_PERCENT_INCOME + "_1", SelectInputField.class, group, NO, order, null, null, null);
        FieldHelper.testFieldValueAndIncr(TWENTY_PERCENT_INCOME + "_2", SelectInputField.class, group, NO, order, null, null, null);

        FieldHelper.testFieldValueAndIncr(MINIMUM_WEALTH, SelectInputField.class, group, null, order, null, true, true);
        FieldHelper.testFieldValueAndIncr(WEALTH_ALLOCATION_OK, SelectInputField.class, group, null, order, null, true, true);

        FieldHelper.testFieldValueAndIncr(WEALTH_ORIGINATING_COUNTRY_1_RISK + "_1", TextInputField.class, group, "", order, null, true, null);
        FieldHelper.testFieldValueAndIncr(WEALTH_ORIGINATING_COUNTRY_2_RISK + "_1", TextInputField.class, group, "", order, null, null, null);
        FieldHelper.testFieldValueAndIncr(WEALTH_ORIGINATING_COUNTRY_1_RISK + "_2", TextInputField.class, group, "", order, null, true, null);
        FieldHelper.testFieldValueAndIncr(WEALTH_ORIGINATING_COUNTRY_2_RISK + "_2", TextInputField.class, group, "", order, null, null, null);

        FieldHelper.testFieldValueAndIncr(WEALTH_ALLOCATION, TextInputField.class, group, null, order, null, true, true);
        FieldHelper.testFieldValueAndIncr(TOTAL_WEALTH + "_1", TextInputField.class, group, "N/A", order, null, null, null);
        FieldHelper.testFieldValueAndIncr(TOTAL_WEALTH + "_2", TextInputField.class, group, "N/A", order, null, null, null);
        FieldHelper.testFieldValueAndIncr(KYC_SUPPORTING_DOCUMENTS, SelectInputField.class, group, null, order, null, true, true);

        assertEquals(expectedNumberOfFields, order.get() - 1);
    }

    @Test
    void buildDueDiligenceFields_MissingIndustrySector_ShouldBlock() {
        Policy policy = PolicyBuilderServiceHelper.createPolicy();
        policy.getEconomicBeneficiaries().getFirst()
                .getThirdParties().getFirst()
                .setProfessionIndustrySector(null);

        Group group = group(DUE_DILIGENCE_GROUP);
        Map<String, List<String>> overallCaseRisk = overallCaseRisk();

        builderService.buildField(webForm(), screenDescription(), group, policy, transaction(), overallCaseRisk);

        var industry = (SelectInputField) ChecklistUtils.getFieldInGroup(group, INDUSTRY + "_1");

        assertEquals(37, group.getFields().size());
        assertNull(industry.getSelectedValue());
        assertTrue(overallCaseRisk.get(BLOCKED).contains("Missing industry sector"));
    }

    @Test
    void buildDueDiligenceFields_PepYes_OK() {
        Policy policy = PolicyBuilderServiceHelper.createPolicy();
        BusinessTransaction transaction = transaction();
        transaction.getRiskFactorResults().stream()
                .filter(rf -> INT_RF_005.equals(rf.getReference()))
                .findFirst()
                .orElseThrow()
                .setAnswerDescription(YES);

        Group group = group(DUE_DILIGENCE_GROUP);

        builderService.buildField(webForm(), screenDescription(), group, policy, transaction, overallCaseRisk());

        FieldHelper.testFieldValue(PEP, SelectInputField.class, group, YES, 7, null, true, null);
    }

    @Test
    void buildDueDiligenceFields_MissingNegativeFinding_ShouldBlock() {
        Policy policy = PolicyBuilderServiceHelper.createPolicy();
        BusinessTransaction transaction = transaction();
        transaction.getRiskFactorResults().stream()
                .filter(rf -> INT_RF_006.equals(rf.getReference()))
                .findFirst()
                .orElseThrow()
                .setData("Missing RF_006 data");

        Group group = group(DUE_DILIGENCE_GROUP);
        Map<String, List<String>> overallCaseRisk = overallCaseRisk();

        builderService.buildField(webForm(), screenDescription(), group, policy, transaction, overallCaseRisk);

        FieldHelper.testFieldValue(NEGATIVE_FINDING, SelectInputField.class, group, null, 14, null, true, false);
        assertTrue(overallCaseRisk.get(BLOCKED).contains("Missing RF_006 data"));
    }

    @Test
    void buildDueDiligenceFields_HighNegativeFinding_ShouldDisplayThirdParty() {
        Policy policy = PolicyBuilderServiceHelper.createPolicy();
        BusinessTransaction transaction = transaction();
        transaction.getRiskFactorResults().stream()
                .filter(rf -> INT_RF_006.equals(rf.getReference()))
                .findFirst()
                .ifPresent(rf -> {
                    rf.setRiskLevel(HIGH);
                    rf.setData("INT_RF_006's data");
                });

        Group group = group(DUE_DILIGENCE_GROUP);

        builderService.buildField(webForm(), screenDescription(), group, policy, transaction, overallCaseRisk());

        FieldHelper.testFieldValue(
                NEGATIVE_FINDING_THIRD_PARTY,
                TextInputField.class,
                group,
                "INT_RF_006's data",
                15,
                "true",
                false,
                false
        );
    }
}
