class DueDiligenceFieldsBuilderServiceTest {

    private static final MockedStatic<PolicyUtils> policyUtils = Mockito.mockStatic(PolicyUtils.class);

    @InjectMocks
    private DueDiligenceFieldsBuilderService dueDiligenceFieldsBuilderService;

    @Mock
    private ReferenceDataRepositoryService referenceDataRepositoryService;

    @BeforeEach
    void initMock() {

        Mockito.when(referenceDataRepositoryService.getReferenceDataOptionsByDomain(YES_NO_DOMAIN))
                .thenReturn(ReferenceServiceHelper.getYesNoOptions());
    }

    @AfterAll
    static void closeStaticMocks() {

        policyUtils.close();
    }

    @Test
    void buildDueDiligenceFields_NoEBO_OK() {

        Policy policy = PolicyBuilderServiceHelper.createPolicy();
        policy.setEconomicBeneficiaries(null);
        Group dueDiligence = Group.builder().groupId(ChecklistFieldConstants.DUE_DILIGENCE_GROUP).build();
        WebForm webForm = WebFormBuilderServiceHelper.createWebForm();
        BusinessTransaction transaction = TransactionBuilderServiceHelper.createTransaction();
        /* Used to automatically increment "order" by 1 for each individual field test. Makes adding/removing a field less painful */
        AtomicInteger testOrder = new AtomicInteger(1);
        int expectedNumberOfFields = 21;

        dueDiligenceFieldsBuilderService.buildField(webForm, new ScreenDescription(), dueDiligence, policy, transaction,
                Map.of(BLOCKED, new ArrayList<>()));
        assertEquals(expectedNumberOfFields, dueDiligence.getFields().size());

        FieldHelper.testMissingField(INDUSTRY + "_1", SelectInputField.class, dueDiligence);
        FieldHelper.testMissingField(POSITION + "_1", SelectInputField.class, dueDiligence);
        FieldHelper.testMissingField(INDUSTRY + "_2", SelectInputField.class, dueDiligence);
        FieldHelper.testMissingField(POSITION + "_2", SelectInputField.class, dueDiligence);
        FieldHelper.testMissingField(AT_LEAST_ONE_SOW_OF_KIND, SelectInputField.class, dueDiligence);
        FieldHelper.testMissingField(ANNUAL_INCOME + "_1", TextInputField.class, dueDiligence);
        FieldHelper.testMissingField(ANNUAL_INCOME + "_2", TextInputField.class, dueDiligence);
        FieldHelper.testMissingField(SOURCE_OF_WEALTH, TextInputField.class, dueDiligence);
        FieldHelper.testMissingField(TWENTY_PERCENT_INCOME + "_1", SelectInputField.class, dueDiligence);
        FieldHelper.testMissingField(TWENTY_PERCENT_INCOME + "_2", SelectInputField.class, dueDiligence);
        FieldHelper.testMissingField(ORIGINATOR_PEP, SelectInputField.class, dueDiligence);
        FieldHelper.testMissingField(WEALTH_ORIGINATING_COUNTRY_1_RISK + "_1", TextInputField.class, dueDiligence);
        FieldHelper.testMissingField(WEALTH_ORIGINATING_COUNTRY_1_RISK + "_2", TextInputField.class, dueDiligence);
        FieldHelper.testMissingField(WEALTH_ORIGINATING_COUNTRY_2_RISK + "_1", TextInputField.class, dueDiligence);
        FieldHelper.testMissingField(WEALTH_ORIGINATING_COUNTRY_2_RISK + "_2", TextInputField.class, dueDiligence);
        FieldHelper.testMissingField(TOTAL_WEALTH + "_1", TextInputField.class, dueDiligence);
        FieldHelper.testMissingField(TOTAL_WEALTH + "_2", TextInputField.class, dueDiligence);

        FieldHelper.testFieldValueAndIncr(BACKGROUND_DETAILS, TextAreaField.class, dueDiligence, null, testOrder, null, false, true);
        FieldHelper.testFieldValueAndIncr(RISK_ASSESSMENT, TextAreaField.class, dueDiligence, null, testOrder, null, false, true);
        FieldHelper.testFieldValueAndIncr(PEP, SelectInputField.class, dueDiligence, NO, testOrder, null, true, false);
        FieldHelper.testFieldValueAndIncr(IS_PEP_PAYER, SelectInputField.class, dueDiligence, null, testOrder, "false", true, true);
        FieldHelper.testFieldValueAndIncr(KYC_MANDATORY, SelectInputField.class, dueDiligence, null, testOrder, null, true, true);
        FieldHelper.testFieldValueAndIncr(NEW_KYC_OBTAINED, SelectInputField.class, dueDiligence, null, testOrder, "#KYC_MANDATORY# == \"YES\"",
                true, true);
        FieldHelper.testFieldValueAndIncr(INTRODUCING_PARTNER_SIGNED, SelectInputField.class, dueDiligence, null, testOrder,
                "#KYC_MANDATORY# == \"YES\" && #NEW_KYC_OBTAINED# == \"YES\"", true, true);
        FieldHelper.testFieldValueAndIncr(INFO_PROVIDED_VERIFIED, SelectInputField.class, dueDiligence, null, testOrder,
                "#KYC_MANDATORY# == \"YES\" && #NEW_KYC_OBTAINED# == \"YES\"", true, true);
        FieldHelper.testFieldValueAndIncr(NEW_KYC_IN_LINE, SelectInputField.class, dueDiligence, null, testOrder,
                "#KYC_MANDATORY# == \"YES\" && #NEW_KYC_OBTAINED# == \"YES\"", true, true);
        FieldHelper.testFieldValueAndIncr(IS_SAME_ORIGIN_PREMIUM, SelectInputField.class, dueDiligence, null, testOrder,
                "#KYC_MANDATORY# == \"NO\" || (#KYC_MANDATORY# == \"YES\" && #NEW_KYC_OBTAINED# == \"NO\")", true, true);
        FieldHelper.testFieldValueAndIncr(NEW_ORIGIN_PREM_CLASS, SelectInputField.class, dueDiligence, null, testOrder,
                "(#KYC_MANDATORY# == \"YES\" && #NEW_KYC_OBTAINED# == \"YES\") || ((#KYC_MANDATORY# == \"NO\" || (#KYC_MANDATORY# == \"YES\" && #NEW_KYC_OBTAINED# == \"NO\")) && #IS_SAME_ORIGIN_PREMIUM# == \"NO\")",
                true, true);
        FieldHelper.testFieldValueAndIncr(NEGATIVE_FINDING, SelectInputField.class, dueDiligence, NO, testOrder, null, true, false);
        FieldHelper.testFieldValueAndIncr(NEGATIVE_FINDING_THIRD_PARTY, TextInputField.class, dueDiligence, null, testOrder, "false", false,
                false);
        FieldHelper.testFieldValueAndIncr(NEGATIVE_SINCE_TRANSACTION, SelectInputField.class, dueDiligence, null, testOrder, "true", true, true);
        FieldHelper.testFieldValueAndIncr(ORIGINATOR_WORLD_CHECK, SelectInputField.class, dueDiligence, null, testOrder, "false", true, true);
        FieldHelper.testFieldValueAndIncr(IS_ON_SANCTION_LIST, SelectInputField.class, dueDiligence, null, testOrder, null, true, false);
        FieldHelper.testFieldValueAndIncr(INSIDER, SelectInputField.class, dueDiligence, NO, testOrder, null, false, false);
        FieldHelper.testFieldValueAndIncr(IS_PH_EBO_GOLDEN_VISA, SelectInputField.class, dueDiligence, null, testOrder, null, true, false);
        FieldHelper.testFieldValueAndIncr(SOURCE_OF_WEALTH_EDITABLE, TextAreaField.class, dueDiligence, null, testOrder, null, false, true);
        FieldHelper.testFieldValueAndIncr(WEALTH_ALLOCATION_OK, SelectInputField.class, dueDiligence, null, testOrder, null, false, true);
        FieldHelper.testFieldValueAndIncr(WEALTH_ALLOCATION, TextInputField.class, dueDiligence, null, testOrder, null, false, true);

        /* To make sure we accounted for all fields built in the group */
        assertEquals(expectedNumberOfFields, testOrder.get() - 1);
    }

    @Test
    void buildDueDiligenceFields_OK() {

        Policy policy = PolicyBuilderServiceHelper.createPolicy();
        Group dueDiligence = Group.builder().groupId(ChecklistFieldConstants.DUE_DILIGENCE_GROUP).build();
        WebForm webForm = WebFormBuilderServiceHelper.createWebForm();
        BusinessTransaction transaction = TransactionBuilderServiceHelper.createTransaction();
        /* Used to automatically increment "order" by 1 for each individual field test. Makes adding/removing a field less painful */
        AtomicInteger testOrder = new AtomicInteger(1);
        int expectedNumberOfFields = 37;

        dueDiligenceFieldsBuilderService.buildField(webForm, new ScreenDescription(), dueDiligence, policy, transaction,
                Map.of(BLOCKED, new ArrayList<>()));
        assertEquals(expectedNumberOfFields, dueDiligence.getFields().size());

        FieldHelper.testFieldValueAndIncr(INDUSTRY + "_1", SelectInputField.class, dueDiligence, null, testOrder, null, true, false);
        FieldHelper.testFieldValueAndIncr(POSITION + "_1", SelectInputField.class, dueDiligence, null, testOrder, "false", false, false);
        FieldHelper.testFieldValueAndIncr(INDUSTRY + "_2", SelectInputField.class, dueDiligence, null, testOrder, null, true, false);
        FieldHelper.testFieldValueAndIncr(POSITION + "_2", SelectInputField.class, dueDiligence, null, testOrder, "false", false, false);
        FieldHelper.testFieldValueAndIncr(BACKGROUND_DETAILS, TextAreaField.class, dueDiligence, null, testOrder, null, false, true);
        FieldHelper.testFieldValueAndIncr(RISK_ASSESSMENT, TextAreaField.class, dueDiligence, null, testOrder, null, false, true);
        FieldHelper.testFieldValueAndIncr(PEP, SelectInputField.class, dueDiligence, NO, testOrder, null, true, false);
        FieldHelper.testFieldValueAndIncr(IS_PEP_PAYER, SelectInputField.class, dueDiligence, null, testOrder, "false", true, true);
        FieldHelper.testFieldValueAndIncr(KYC_MANDATORY, SelectInputField.class, dueDiligence, null, testOrder, null, true, true);
        FieldHelper.testFieldValueAndIncr(NEW_KYC_OBTAINED, SelectInputField.class, dueDiligence, null, testOrder, "#KYC_MANDATORY# == \"YES\"",
                true, true);
        FieldHelper.testFieldValueAndIncr(INTRODUCING_PARTNER_SIGNED, SelectInputField.class, dueDiligence, null, testOrder,
                "#KYC_MANDATORY# == \"YES\" && #NEW_KYC_OBTAINED# == \"YES\"", true, true);
        FieldHelper.testFieldValueAndIncr(INFO_PROVIDED_VERIFIED, SelectInputField.class, dueDiligence, null, testOrder,
                "#KYC_MANDATORY# == \"YES\" && #NEW_KYC_OBTAINED# == \"YES\"", true, true);
        FieldHelper.testFieldValueAndIncr(NEW_KYC_IN_LINE, SelectInputField.class, dueDiligence, null, testOrder,
                "#KYC_MANDATORY# == \"YES\" && #NEW_KYC_OBTAINED# == \"YES\"", true, true);
        FieldHelper.testFieldValueAndIncr(IS_SAME_ORIGIN_PREMIUM, SelectInputField.class, dueDiligence, null, testOrder,
                "#KYC_MANDATORY# == \"NO\" || (#KYC_MANDATORY# == \"YES\" && #NEW_KYC_OBTAINED# == \"NO\")", true, true);
        FieldHelper.testFieldValueAndIncr(NEW_ORIGIN_PREM_CLASS, SelectInputField.class, dueDiligence, null, testOrder,
                "(#KYC_MANDATORY# == \"YES\" && #NEW_KYC_OBTAINED# == \"YES\") || ((#KYC_MANDATORY# == \"NO\" || (#KYC_MANDATORY# == \"YES\" && #NEW_KYC_OBTAINED# == \"NO\")) && #IS_SAME_ORIGIN_PREMIUM# == \"NO\")",
                true, true);
        FieldHelper.testFieldValueAndIncr(NEGATIVE_FINDING, SelectInputField.class, dueDiligence, NO, testOrder, null, true, false);
        FieldHelper.testFieldValueAndIncr(NEGATIVE_FINDING_THIRD_PARTY, TextInputField.class, dueDiligence, null, testOrder, "false", false,
                false);
        FieldHelper.testFieldValueAndIncr(NEGATIVE_SINCE_TRANSACTION, SelectInputField.class, dueDiligence, null, testOrder, "true", true, true);
        FieldHelper.testFieldValueAndIncr(AT_LEAST_ONE_SOW_OF_KIND, SelectInputField.class, dueDiligence, NO, testOrder, "false", true, false);
        FieldHelper.testFieldValueAndIncr(ORIGINATOR_WORLD_CHECK, SelectInputField.class, dueDiligence, null, testOrder, "false", true, true);
        FieldHelper.testFieldValueAndIncr(IS_ON_SANCTION_LIST, SelectInputField.class, dueDiligence, null, testOrder, null, true, false);
        FieldHelper.testFieldValueAndIncr(INSIDER, SelectInputField.class, dueDiligence, NO, testOrder, null, false, false);
        FieldHelper.testFieldValueAndIncr(IS_PH_EBO_GOLDEN_VISA, SelectInputField.class, dueDiligence, null, testOrder, null, true, false);
        FieldHelper.testFieldValueAndIncr(ANNUAL_INCOME + "_1", TextInputField.class, dueDiligence, "N/A", testOrder, null, false, false);
        FieldHelper.testFieldValueAndIncr(ANNUAL_INCOME + "_2", TextInputField.class, dueDiligence, "N/A", testOrder, null, false, false);
        FieldHelper.testFieldValueAndIncr(SOURCE_OF_WEALTH, TextInputField.class, dueDiligence, "2", testOrder, null, false, true);
        FieldHelper.testFieldValueAndIncr(TWENTY_PERCENT_INCOME + "_1", SelectInputField.class, dueDiligence, NO, testOrder, null, false, false);
        FieldHelper.testFieldValueAndIncr(TWENTY_PERCENT_INCOME + "_2", SelectInputField.class, dueDiligence, NO, testOrder, null, false, false);
        FieldHelper.testFieldValueAndIncr(SOURCE_OF_WEALTH_EDITABLE, TextAreaField.class, dueDiligence, null, testOrder, null, false, true);
        FieldHelper.testFieldValueAndIncr(WEALTH_ALLOCATION_OK, SelectInputField.class, dueDiligence, null, testOrder, null, false, true);
        FieldHelper.testFieldValueAndIncr(WEALTH_ORIGINATING_COUNTRY_1_RISK + "_1", TextInputField.class, dueDiligence, "", testOrder, null, true,
                false);
        FieldHelper.testFieldValueAndIncr(WEALTH_ORIGINATING_COUNTRY_2_RISK + "_1", TextInputField.class, dueDiligence, "", testOrder, null, false,
                false);
        FieldHelper.testFieldValueAndIncr(WEALTH_ORIGINATING_COUNTRY_1_RISK + "_2", TextInputField.class, dueDiligence, "", testOrder, null, true,
                false);
        FieldHelper.testFieldValueAndIncr(WEALTH_ORIGINATING_COUNTRY_2_RISK + "_2", TextInputField.class, dueDiligence, "", testOrder, null, false,
                false);
        FieldHelper.testFieldValueAndIncr(WEALTH_ALLOCATION, TextInputField.class, dueDiligence, null, testOrder, null, false, true);
        FieldHelper.testFieldValueAndIncr(TOTAL_WEALTH + "_1", TextInputField.class, dueDiligence, "N/A", testOrder, null, false, false);
        FieldHelper.testFieldValueAndIncr(TOTAL_WEALTH + "_2", TextInputField.class, dueDiligence, "N/A", testOrder, null, false, false);

        /* To make sure we accounted for all fields built in the group */
        assertEquals(expectedNumberOfFields, testOrder.get() - 1);
    }

    @Test
    void buildSourceOfFundField_False() {

        Policy policy = PolicyBuilderServiceHelper.createCapitalisedPolicy();
        Group dueDiligence = Group.builder().groupId(ChecklistFieldConstants.DUE_DILIGENCE_GROUP).build();
        WebForm webForm = WebFormBuilderServiceHelper.createWebForm();
        BusinessTransaction transaction = TransactionBuilderServiceHelper.createTransaction();

        dueDiligenceFieldsBuilderService.buildField(webForm, new ScreenDescription(), dueDiligence, policy, transaction,
                Map.of(BLOCKED, new ArrayList<>()));
        assertEquals(37, dueDiligence.getFields().size());

        FieldHelper.testFieldValue(PEP, SelectInputField.class, dueDiligence, NO, 7, null, true, false);
        FieldHelper.testFieldValue(NEGATIVE_FINDING, SelectInputField.class, dueDiligence, NO, 16, null, true, false);
    }

    @Test
    void buildPep_True() {

        Policy policy = PolicyBuilderServiceHelper.createCapitalisedPolicy();
        Group dueDiligence = Group.builder().groupId(ChecklistFieldConstants.DUE_DILIGENCE_GROUP).build();
        WebForm webForm = WebFormBuilderServiceHelper.createWebForm();
        BusinessTransaction transaction = TransactionBuilderServiceHelper.createTransaction();
        Map<String,List<String>> overallCaseRisk = Map.of(BLOCKED, new ArrayList<>());

        transaction.getRiskFactorResults().stream().filter(riskFactorResult -> riskFactorResult.getReference().equals(INT_RF_005)).findFirst()
                .get().setAnswerDescription(YES);

        dueDiligenceFieldsBuilderService.buildField(webForm, new ScreenDescription(), dueDiligence, policy, transaction, overallCaseRisk);
        assertEquals(37, dueDiligence.getFields().size());

        FieldHelper.testFieldValue(PEP, SelectInputField.class, dueDiligence, YES, 7, null, true, false);
        assertEquals(3, overallCaseRisk.get(BLOCKED).size());
        assertFalse(overallCaseRisk.get(BLOCKED).contains("Missing PEP Status"));
    }

    @Test
    void buildPep_Null() {

        Policy policy = PolicyBuilderServiceHelper.createCapitalisedPolicy();
        Group dueDiligence = Group.builder().groupId(ChecklistFieldConstants.DUE_DILIGENCE_GROUP).build();
        WebForm webForm = WebFormBuilderServiceHelper.createWebForm();
        BusinessTransaction transaction = TransactionBuilderServiceHelper.createTransaction();
        Map<String,List<String>> overallCaseRisk = Map.of(BLOCKED, new ArrayList<>());

        transaction.getRiskFactorResults().stream().filter(riskFactorResult -> riskFactorResult.getReference().equals(INT_RF_005)).findFirst()
                .get().setAnswerDescription("N/A");

        dueDiligenceFieldsBuilderService.buildField(webForm, new ScreenDescription(), dueDiligence, policy, transaction, overallCaseRisk);
        assertEquals(37, dueDiligence.getFields().size());

        FieldHelper.testFieldValue(PEP, SelectInputField.class, dueDiligence, null, 7, null, true, false);
        assertEquals(3, overallCaseRisk.get(BLOCKED).size());
        assertFalse(overallCaseRisk.get(BLOCKED).contains("Missing PEP Status"));
    }

    @Test
    void buildMissingPep_True() {

        Policy policy = PolicyBuilderServiceHelper.createCapitalisedPolicy();
        Group dueDiligence = Group.builder().groupId(ChecklistFieldConstants.DUE_DILIGENCE_GROUP).build();
        WebForm webForm = WebFormBuilderServiceHelper.createWebForm();
        BusinessTransaction transaction = TransactionBuilderServiceHelper.createTransaction();
        Map<String,List<String>> overallCaseRisk = Map.of(BLOCKED, new ArrayList<>());
        transaction.getRiskFactorResults().stream().filter(riskFactorResult -> riskFactorResult.getReference().equals(INT_RF_005)).findFirst()
                .get().setData("Missing PEP Status");

        dueDiligenceFieldsBuilderService.buildField(webForm, new ScreenDescription(), dueDiligence, policy, transaction, overallCaseRisk);
        assertEquals(37, dueDiligence.getFields().size());

        FieldHelper.testFieldValue(PEP, SelectInputField.class, dueDiligence, null, 7, null, true, false);
    }

    @Test
    void buildDueDiligenceFields_IndustryAndPosition() {

        Policy policy = PolicyBuilderServiceHelper.createCapitalisedPolicy();
        Group dueDiligence = Group.builder().groupId(ChecklistFieldConstants.DUE_DILIGENCE_GROUP).build();
        WebForm webForm = WebFormBuilderServiceHelper.createWebForm();
        BusinessTransaction transaction = TransactionBuilderServiceHelper.createTransaction();
        dueDiligenceFieldsBuilderService.buildField(webForm, new ScreenDescription(), dueDiligence, policy, transaction,
                Map.of(BLOCKED, new ArrayList<>()));
        assertEquals(37, dueDiligence.getFields().size());
        FieldHelper.testFieldValue(INDUSTRY + "_1", SelectInputField.class, dueDiligence, null, 1, null, true, false);
        FieldHelper.testFieldValue(POSITION + "_1", SelectInputField.class, dueDiligence, null, 2, "false", false, false);
    }

    @Test
    void buildDueDiligenceFields_IndustryAndPositionMissingFromGroup() {

        Policy policy = PolicyBuilderServiceHelper.createCapitalisedPolicy();
        Group dueDiligence = Group.builder().groupId(ChecklistFieldConstants.DUE_DILIGENCE_GROUP).build();
        var industry = (SelectInputField) ChecklistUtils.getFieldInGroup(dueDiligence, INDUSTRY + "_1");
        var position = (SelectInputField) ChecklistUtils.getFieldInGroup(dueDiligence, POSITION + "_1");
        dueDiligence.getFields().remove(industry);
        dueDiligence.getFields().remove(position);

        WebForm webForm = WebFormBuilderServiceHelper.createWebForm();
        BusinessTransaction transaction = TransactionBuilderServiceHelper.createTransaction();
        dueDiligenceFieldsBuilderService.buildField(webForm, new ScreenDescription(), dueDiligence, policy, transaction,
                Map.of(BLOCKED, new ArrayList<>()));
        assertEquals(37, dueDiligence.getFields().size());
        FieldHelper.testFieldValue(INDUSTRY + "_1", SelectInputField.class, dueDiligence, null, 1, null, true, false);
        FieldHelper.testFieldValue(POSITION + "_1", SelectInputField.class, dueDiligence, null, 2, "false", false, false);
    }

    @Test
    void buildDueDiligenceFields_IndustryMissingIndustrySector() {

        Policy policy = PolicyBuilderServiceHelper.createCapitalisedPolicy();
        Group dueDiligence = Group.builder().groupId(ChecklistFieldConstants.DUE_DILIGENCE_GROUP).build();
        policy.getEconomicBeneficiaries().getFirst().getThirdParties().getFirst().setProfessionIndustrySector(null);

        WebForm webForm = WebFormBuilderServiceHelper.createWebForm();
        Map<String,List<String>> overallCaseRisk = Map.of(BLOCKED, new ArrayList<>());
        BusinessTransaction transaction = TransactionBuilderServiceHelper.createTransaction();
        dueDiligenceFieldsBuilderService.buildField(webForm, new ScreenDescription(), dueDiligence, policy, transaction, overallCaseRisk);
        var industry = (SelectInputField) ChecklistUtils.getFieldInGroup(dueDiligence, INDUSTRY + "_1");
        assertEquals(37, dueDiligence.getFields().size());
        assertEquals(3, overallCaseRisk.get(BLOCKED).size());
        Optional<String> val = overallCaseRisk.get(BLOCKED).stream().findFirst();
        assertTrue(val.isPresent());
        assertNull(industry.getSelectedValue());
        assertEquals("Missing industry sector", val.get());
    }

    @Test
    void buildDueDiligenceFields_PositionNull() {

        Policy policy = PolicyBuilderServiceHelper.createCapitalisedPolicy();
        Group dueDiligence = Group.builder().groupId(ChecklistFieldConstants.DUE_DILIGENCE_GROUP).build();

        PolicyUtilsHelper.mockGetPerson(policyUtils, policy.getEconomicBeneficiaries().getFirst(), null);

        WebForm webForm = WebFormBuilderServiceHelper.createWebForm();
        Map<String,List<String>> overallCaseRisk = Map.of(BLOCKED, new ArrayList<>());
        BusinessTransaction transaction = TransactionBuilderServiceHelper.createTransaction();
        dueDiligenceFieldsBuilderService.buildField(webForm, new ScreenDescription(), dueDiligence, policy, transaction, overallCaseRisk);
        var position = (SelectInputField) ChecklistUtils.getFieldInGroup(dueDiligence, POSITION + "_1");

        assertEquals(37, dueDiligence.getFields().size());
        assertEquals(position.getDisplayIf(), "false");
        assertNull(position.getSelectedValue());

    }

    @Test
    void buildDueDiligenceFields_wealth() {

        Policy policy = PolicyBuilderServiceHelper.createPolicyWithHighResidenceCountry();
        Group dueDiligence = Group.builder().groupId(ChecklistFieldConstants.DUE_DILIGENCE_GROUP).build();
        WebForm webForm = WebFormBuilderServiceHelper.createWebForm();
        BusinessTransaction transaction = TransactionBuilderServiceHelper.createTransaction();

        dueDiligenceFieldsBuilderService.buildField(webForm, new ScreenDescription(), dueDiligence, policy, transaction,
                Map.of(BLOCKED, new ArrayList<>()));
        assertEquals(37, dueDiligence.getFields().size());
        FieldHelper.testFieldValue(IS_PH_EBO_GOLDEN_VISA, SelectInputField.class, dueDiligence, null, 23, null, true, false);
        FieldHelper.testFieldValue(WEALTH_ORIGINATING_COUNTRY_2_RISK + "_1", TextInputField.class, dueDiligence, "", 32, null, false, false);
        FieldHelper.testFieldValue(WEALTH_ALLOCATION, TextInputField.class, dueDiligence, null, 35, null, false, true);
    }

    @Test
    void buildDueDiligenceFields20Percent() {

        Policy policy = PolicyBuilderServiceHelper.createPolicy();
        policy.getEconomicBeneficiaries().stream().map(ebo -> ebo.getThirdParties().getFirst().getSourceOfFunds().getSourcesOfWealth().getFirst())
                .forEach(sourceOfWealth -> sourceOfWealth.setSplitPercentage(BigDecimal.valueOf(10)));

        Group dueDiligence = Group.builder().groupId(ChecklistFieldConstants.DUE_DILIGENCE_GROUP).build();
        WebForm webForm = WebFormBuilderServiceHelper.createWebForm();
        BusinessTransaction transaction = TransactionBuilderServiceHelper.createTransaction();

        dueDiligenceFieldsBuilderService.buildField(webForm, new ScreenDescription(), dueDiligence, policy, transaction,
                Map.of(BLOCKED, new ArrayList<>()));
        assertEquals(37, dueDiligence.getFields().size());
        FieldHelper.testFieldValue(TWENTY_PERCENT_INCOME + "_1", SelectInputField.class, dueDiligence, NO, 27, null, false, false);
        FieldHelper.testFieldValue(TWENTY_PERCENT_INCOME + "_2", SelectInputField.class, dueDiligence, NO, 28, null, false, false);

    }

    @Test
    void buildDueDiligenceFields20PercentWithDivorceAndGift() {

        Policy policy = PolicyBuilderServiceHelper.createPolicyWithTwentyPercentDivorceAndGift();

        Group dueDiligence = Group.builder().groupId(ChecklistFieldConstants.DUE_DILIGENCE_GROUP).build();
        WebForm webForm = WebFormBuilderServiceHelper.createWebForm();
        BusinessTransaction transaction = TransactionBuilderServiceHelper.createTransaction();

        dueDiligenceFieldsBuilderService.buildField(webForm, new ScreenDescription(), dueDiligence, policy, transaction,
                Map.of(BLOCKED, new ArrayList<>()));
        assertEquals(37, dueDiligence.getFields().size());
        FieldHelper.testFieldValue(TWENTY_PERCENT_INCOME + "_1", SelectInputField.class, dueDiligence, NO, 27, null, false, false);
        FieldHelper.testFieldValue(TWENTY_PERCENT_INCOME + "_2", SelectInputField.class, dueDiligence, NO, 28, null, false, false);
    }

    @Test
    void buildDueDiligenceWitEboSize() {

        Policy policy = PolicyBuilderServiceHelper.createPolicyWithEboSize(true);
        Group dueDiligence = Group.builder().groupId(ChecklistFieldConstants.DUE_DILIGENCE_GROUP).build();
        WebForm webForm = WebFormBuilderServiceHelper.createWebForm();
        BusinessTransaction transaction = TransactionBuilderServiceHelper.createTransaction();

        dueDiligenceFieldsBuilderService.buildField(webForm, new ScreenDescription(), dueDiligence, policy, transaction,
                Map.of(BLOCKED, new ArrayList<>()));
        assertEquals(65, dueDiligence.getFields().size());
        FieldHelper.testFieldValue(SOURCE_OF_WEALTH, TextInputField.class, dueDiligence, "6", 38, null, false, true);
    }

    @Test
    void buildDueDiligenceWitEboSizeAndNoSplit() {

        Policy policy = PolicyBuilderServiceHelper.createPolicyWithEboSize(false);
        Group dueDiligence = Group.builder().groupId(ChecklistFieldConstants.DUE_DILIGENCE_GROUP).build();
        WebForm webForm = WebFormBuilderServiceHelper.createWebForm();
        BusinessTransaction transaction = TransactionBuilderServiceHelper.createTransaction();

        dueDiligenceFieldsBuilderService.buildField(webForm, new ScreenDescription(), dueDiligence, policy, transaction,
                Map.of(BLOCKED, new ArrayList<>()));
        assertEquals(65, dueDiligence.getFields().size());
        FieldHelper.testFieldValue(SOURCE_OF_WEALTH, TextInputField.class, dueDiligence, "6", 38, null, false, true);
    }

    @Test
    void buildDueDiligenceWithCountriesAndSourceOfWealth() {

        Policy policy = PolicyBuilderServiceHelper.createPolicyWithCountries(true);
        Group dueDiligence = Group.builder().groupId(ChecklistFieldConstants.DUE_DILIGENCE_GROUP).build();
        WebForm webForm = WebFormBuilderServiceHelper.createWebForm();
        BusinessTransaction transaction = TransactionBuilderServiceHelper.createTransaction();

        dueDiligenceFieldsBuilderService.buildField(webForm, new ScreenDescription(), dueDiligence, policy, transaction,
                Map.of(BLOCKED, new ArrayList<>()));
        assertEquals(37, dueDiligence.getFields().size());

        FieldHelper.testFieldValue(WEALTH_ORIGINATING_COUNTRY_1_RISK + "_1", TextInputField.class, dueDiligence, "", 31, null, true, false);
        FieldHelper.testFieldValue(WEALTH_ORIGINATING_COUNTRY_2_RISK + "_1", TextInputField.class, dueDiligence, "", 32, null, false, false);
        FieldHelper.testFieldValue(WEALTH_ORIGINATING_COUNTRY_1_RISK + "_2", TextInputField.class, dueDiligence, "", 33, null, true, false);
        FieldHelper.testFieldValue(WEALTH_ORIGINATING_COUNTRY_2_RISK + "_2", TextInputField.class, dueDiligence, "", 34, null, false, false);
    }

    @Test
    void buildDueDiligenceFieldWithAmount() {

        Policy policy = PolicyBuilderServiceHelper.createPolicyWithSourceOfFundsTotalWealth();
        Group dueDiligence = Group.builder().groupId(ChecklistFieldConstants.DUE_DILIGENCE_GROUP).build();
        WebForm webForm = WebFormBuilderServiceHelper.createWebForm();
        BusinessTransaction transaction = TransactionBuilderServiceHelper.createTransaction();
        dueDiligenceFieldsBuilderService.buildField(webForm, new ScreenDescription(), dueDiligence, policy, transaction,
                Map.of(BLOCKED, new ArrayList<>()));
        assertEquals(37, dueDiligence.getFields().size());

        FieldHelper.testFieldValue(TOTAL_WEALTH + "_1", TextInputField.class, dueDiligence, "N/A", 36, null, false, false);
        FieldHelper.testFieldValue(TOTAL_WEALTH + "_2", TextInputField.class, dueDiligence, "N/A", 37, null, false, false);

        FieldHelper.testFieldValue(ANNUAL_INCOME + "_1", TextInputField.class, dueDiligence, "N/A", 24, null, false, false);
        FieldHelper.testFieldValue(ANNUAL_INCOME + "_2", TextInputField.class, dueDiligence, "N/A", 25, null, false, false);
    }

    @Test
    void buildDueDiligenceFieldInsider_oK() {

        Policy policy = PolicyBuilderServiceHelper.createPolicyWithInsiderTrue();
        Group dueDiligence = Group.builder().groupId(ChecklistFieldConstants.DUE_DILIGENCE_GROUP).build();
        WebForm webForm = WebFormBuilderServiceHelper.createWebForm();
        BusinessTransaction transaction = TransactionBuilderServiceHelper.createTransaction();

        dueDiligenceFieldsBuilderService.buildField(webForm, new ScreenDescription(), dueDiligence, policy, transaction,
                Map.of(BLOCKED, new ArrayList<>()));
        assertEquals(37, dueDiligence.getFields().size());
        FieldHelper.testFieldValue(INSIDER, SelectInputField.class, dueDiligence, YES, 22, null, false, false);
    }

    @Test
    void buildDueDiligenceFieldIsOnSanctionList() {

        Policy policy = PolicyBuilderServiceHelper.createPolicyWithInternationalSanctionList();
        Group dueDiligence = Group.builder().groupId(ChecklistFieldConstants.DUE_DILIGENCE_GROUP).build();
        WebForm webForm = WebFormBuilderServiceHelper.createWebForm();

        BusinessTransaction transaction = TransactionBuilderServiceHelper.createTransaction();
        transaction.setProductComponentPayments(Collections.singletonList(new BusinessTransactionProductComponent()));
        transaction.setRiskFactorResults(Stream
                .concat(transaction.getRiskFactorResults().stream(), Stream
                        .of(RiskFactorResult.builder().data("data test").reference(INT_RF_007).answerDescription(NO).riskLevel(STANDARD).build()))
                .toList());

        dueDiligenceFieldsBuilderService.buildField(webForm, new ScreenDescription(), dueDiligence, policy, transaction,
                Map.of(BLOCKED, new ArrayList<>()));
        assertEquals(37, dueDiligence.getFields().size());
        FieldHelper.testFieldValue(IS_ON_SANCTION_LIST, SelectInputField.class, dueDiligence, NO, 21, null, true, false);
    }

    @Test
    void buildMissingIntRF007() {

        Policy policy = PolicyBuilderServiceHelper.createCapitalisedPolicy();
        Group transactionDetails = Group.builder().groupId(ChecklistFieldConstants.TRANSACTION_DETAILS_GROUP).build();
        WebForm webForm = WebFormBuilderServiceHelper.createWebForm();
        BusinessTransaction transaction = TransactionBuilderServiceHelper.createTransaction();
        transaction.setProductComponentPayments(Collections.singletonList(new BusinessTransactionProductComponent()));
        transaction.setRiskFactorResults(Stream.concat(transaction.getRiskFactorResults().stream(), Stream.of(RiskFactorResult.builder()
                .data("Missing Data").reference(INT_RF_007).answerDescription("data missing").riskLevel(STANDARD).build())).toList());

        Map<String,List<String>> overallCaseRisk = Map.of(BLOCKED, new ArrayList<>());
        dueDiligenceFieldsBuilderService.buildField(webForm, new ScreenDescription(), transactionDetails, policy, transaction, overallCaseRisk);
        assertEquals(37, transactionDetails.getFields().size());
        assertEquals(4, overallCaseRisk.get(BLOCKED).size());
        Optional<String> val = overallCaseRisk.get(BLOCKED).stream().filter(str -> str.equals("Missing Data")).findFirst();
        assertTrue(val.isPresent());
        assertEquals("Missing Data", val.get());
        FieldHelper.testFieldValue(IS_ON_SANCTION_LIST, SelectInputField.class, transactionDetails, null, 21, null, true, false);
    }

    @Test
    void buildMissingIntRF006() {

        Policy policy = PolicyBuilderServiceHelper.createCapitalisedPolicy();
        Group transactionDetails = Group.builder().groupId(ChecklistFieldConstants.TRANSACTION_DETAILS_GROUP).build();
        WebForm webForm = WebFormBuilderServiceHelper.createWebForm();
        BusinessTransaction transaction = TransactionBuilderServiceHelper.createTransaction();
        transaction.getRiskFactorResults().stream().filter(riskFactor -> riskFactor.getReference().equals(INT_RF_006)).findFirst()
                .ifPresent(riskFactor -> riskFactor.setData("Missing RF_006 data"));
        Map<String,List<String>> overallCaseRisk = Map.of(BLOCKED, new ArrayList<>());
        dueDiligenceFieldsBuilderService.buildField(webForm, new ScreenDescription(), transactionDetails, policy, transaction, overallCaseRisk);

        assertEquals(37, transactionDetails.getFields().size());
        assertEquals(4, overallCaseRisk.get(BLOCKED).size());
        Optional<String> val = overallCaseRisk.get(BLOCKED).stream().filter(str -> str.equals("Missing RF_006 data")).findFirst();
        assertTrue(val.isPresent());

        final var absentFieldIds = List.of(NEGATIVE_SINCE_TRANSACTION, NEGATIVE_FINDING_THIRD_PARTY);
        assertEquals("Missing RF_006 data", val.get());
        assertTrue(
                transactionDetails.getFields().stream().anyMatch(field -> absentFieldIds.stream().anyMatch(id -> id.equals(field.getFieldId()))));
        FieldHelper.testFieldValue(NEGATIVE_FINDING, SelectInputField.class, transactionDetails, null, 16, null, true, false);
    }

    @Test
    void buildHighIntRF006() {

        Policy policy = PolicyBuilderServiceHelper.createCapitalisedPolicy();
        Group transactionDetails = Group.builder().groupId(ChecklistFieldConstants.TRANSACTION_DETAILS_GROUP).build();
        WebForm webForm = WebFormBuilderServiceHelper.createWebForm();
        BusinessTransaction transaction = TransactionBuilderServiceHelper.createTransaction();
        transaction.getRiskFactorResults().stream().filter(riskFactor -> riskFactor.getReference().equals(INT_RF_006)).findFirst()
                .ifPresent(riskFactor -> riskFactor.setRiskLevel("High"));
        Map<String,List<String>> overallCaseRisk = Map.of(BLOCKED, new ArrayList<>());
        dueDiligenceFieldsBuilderService.buildField(webForm, new ScreenDescription(), transactionDetails, policy, transaction, overallCaseRisk);

        assertEquals(37, transactionDetails.getFields().size());
        FieldHelper.testFieldValue(NEGATIVE_FINDING_THIRD_PARTY, TextInputField.class, transactionDetails, "INT_RF_006's data", 17, "true", false,
                false);
    }
}


public class OnboardingDueDiligenceFieldsBuilderService extends OnboardingChecklistFieldsBuilderSupport implements FieldBuilderService {

    private final ReferenceDataRepositoryService referenceDataRepositoryService;

    @Override
    public void buildField(final WebForm webForm, final ScreenDescription screenDescription, final Group group, final Policy policy,
            final BusinessTransaction transaction, final Map<String,List<String>> overallCaseRisk) {

        Field.resetFieldId();
        if (policy.getEconomicBeneficiaries() != null) {
            IntStream.range(0, policy.getEconomicBeneficiaries().size()).forEach(i -> {
                evaluateIndustry(group, policy.getEconomicBeneficiaries().get(i), i + 1, overallCaseRisk);
                evaluatePosition(group, policy.getEconomicBeneficiaries().get(i), i + 1);
            });
        }

        evaluateBackgroundDetails(group);

        evaluateRiskAssessment(group);

        evaluatePep(group, transaction);

        evaluateOriginatorPep(group, policy);

        evaluateIsPepPayer(group, transaction);

        evaluateTccSigned(group);

        evaluateTccSignedRefused(group);

        evaluateIntroducingPartnerSigned(group);

        evaluateInfoProvidedVerified(group);

        evaluateNegativeFinding(group, transaction, overallCaseRisk);

        evaluateNegativeFindingThirdParty(group, transaction);

        evaluateAtLeastOneSowOfKind(group, policy);

        evaluateOriginatorWorldCheck(group);

        evaluateIsOnSanctionList(group, transaction, overallCaseRisk);

        evaluateInsider(group, policy);

        evaluateIsPhEboGoldenVisa(group, transaction, overallCaseRisk);

        if (policy.getEconomicBeneficiaries() != null) {
            IntStream.range(0, policy.getEconomicBeneficiaries().size())
                    .forEach(i -> evaluateAnnualIncome(group, policy.getEconomicBeneficiaries().get(i), i + 1));
        }

        if (policy.getEconomicBeneficiaries() != null) {
            evaluateSourceOfWealth(group, policy.getEconomicBeneficiaries().size());
            IntStream.range(0, policy.getEconomicBeneficiaries().size())
                    .forEach(i -> evaluateTwentyPercentIncome(group, policy.getEconomicBeneficiaries().get(i), i + 1));
        }

        evaluateMinimumWealth(group);

        evaluateWealthAllocationOk(group);

        if (policy.getEconomicBeneficiaries() != null) {
            IntStream.range(0, policy.getEconomicBeneficiaries().size()).forEach(i -> {
                evaluateWealthOriginatingCountry1Risk(group, policy.getEconomicBeneficiaries().get(i), i + 1);
                evaluateWealthOriginatingCountry2Risk(group, policy.getEconomicBeneficiaries().get(i), i + 1);
            });
        }

        evaluateWealthAllocation(group);

        if (policy.getEconomicBeneficiaries() != null) {
            IntStream.range(0, policy.getEconomicBeneficiaries().size())
                    .forEach(i -> evaluateTotalWealth(group, policy.getEconomicBeneficiaries().get(i), i + 1));
        }

        evaluateKycSupportingDocuments(group);

    }

    private void evaluateIndustry(final Group group, final EconomicBeneficiary ebo, int index, final Map<String,List<String>> overallCaseRisk) {

        var industry = (SelectInputField) ChecklistUtils.getFieldInGroup(group, INDUSTRY + "_" + index);
        if (industry == null) {
            industry = SelectInputField.builder().fieldId(INDUSTRY + "_" + index).build();
            group.getFields().add(industry);
        }
        industry.setIsActive(true);
        industry.incrementOrder();
        industry.setLabel("Industry #" + index);
        industry.setMandatory(true);
        industry.setSourceSystem("from CLASS");
        var person = PolicyUtils.getPerson(ebo);
        if (person.isPresent() && person.get().getProfessionIndustrySector() != null) {
            industry.setSelectedValue(person.get().getProfessionIndustrySector().getExternalId());
        } else {
            industry.setSelectedValue(null);
            overallCaseRisk.get(BLOCKED).add("Missing industry sector");
        }

        industry.setOptions(referenceDataRepositoryService.getReferenceDataOptionsByDomainAndSelectedValue(INDUSTRY_SECTOR_DOMAIN,
                industry.getSelectedValue()));
    }

    private void evaluatePosition(final Group group, final EconomicBeneficiary ebo, int index) {

        var position = (SelectInputField) ChecklistUtils.getFieldInGroup(group, ChecklistFieldConstants.POSITION + "_" + index);
        if (position == null) {
            position = SelectInputField.builder().fieldId(ChecklistFieldConstants.POSITION + "_" + index).build();
            group.getFields().add(position);
        }
        position.setIsActive(true);
        position.incrementOrder();
        position.setLabel("Position #" + index);
        position.setSourceSystem("Position from CLASS");

        var person = PolicyUtils.getPerson(ebo);

        if (person.isPresent() && person.get() instanceof PhysicalPerson && ((PhysicalPerson) person.get()).getProfession() != null) {
            position.setSelectedValue(((PhysicalPerson) person.get()).getProfession().getExternalId());
        } else {
            position.setDisplayIf("false");
            position.setSelectedValue(null);
        }
        position.setOptions(
                referenceDataRepositoryService.getReferenceDataOptionsByDomainAndSelectedValue(PROFESSION_DOMAIN, position.getSelectedValue()));
    }

    private void evaluateBackgroundDetails(Group group) {

        var backgroundDetails = (TextAreaField) ChecklistUtils.getFieldInGroup(group, BACKGROUND_DETAILS);
        if (backgroundDetails == null) {
            backgroundDetails = TextAreaField.builder().fieldId(BACKGROUND_DETAILS).build();
            group.getFields().add(backgroundDetails);
        }
        backgroundDetails.setIsActive(true);
        backgroundDetails.incrementOrder();
        backgroundDetails.setLabel("Profession background details");
        backgroundDetails.setEnabled(true);
        backgroundDetails.setMandatory(true);
        backgroundDetails.setDisplayIf(null);
        backgroundDetails.setLabelBold(false);
        backgroundDetails.setSourceSystem(null);
    }

    private void evaluateRiskAssessment(Group group) {

        var riskAssessment = (TextAreaField) ChecklistUtils.getFieldInGroup(group, RISK_ASSESSMENT);
        if (riskAssessment == null) {
            riskAssessment = TextAreaField.builder().fieldId(RISK_ASSESSMENT).build();
            group.getFields().add(riskAssessment);
        }
        riskAssessment.setIsActive(true);
        riskAssessment.incrementOrder();
        riskAssessment.setLabel("Profession risk assessment");
        riskAssessment.setEnabled(true);
        riskAssessment.setMandatory(true);
        riskAssessment.setDisplayIf(null);
        riskAssessment.setLabelBold(false);
        riskAssessment.setSourceSystem(null);
    }

    private void evaluatePep(final Group group, final BusinessTransaction transaction) {

        var pep = (SelectInputField) ChecklistUtils.getFieldInGroup(group, ChecklistFieldConstants.PEP);
        if (pep == null) {
            pep = SelectInputField.builder().fieldId(ChecklistFieldConstants.PEP).build();
            group.getFields().add(pep);
        }
        pep.setIsActive(true);
        pep.incrementOrder();
        pep.setLabel("Is there a PEP on the policy");
        pep.setMandatory(true);

        final var pepStatusAnswer = getRiskFactorAnswerDescription(transaction, INT_RF_005);
        final var pepStatusData = getRiskFactorData(transaction, INT_RF_005);

        if (pepStatusAnswer == null || "N/A".equals(pepStatusAnswer) || pepStatusData.toUpperCase().startsWith("MISSING")) {
            pep.setSelectedValue(null);
        } else {
            pep.setSelectedValue(pepStatusAnswer.toUpperCase());
        }

        pep.setOptions(referenceDataRepositoryService.getReferenceDataOptionsByDomainAndSelectedValue(YES_NO_DOMAIN, pep.getSelectedValue()));
    }

    private void evaluateOriginatorPep(final Group group, Policy policy) {

        boolean shouldDisplay = false;
        if (policy.getEconomicBeneficiaries() == null) {
            return;
        }
        for (EconomicBeneficiary ebo : policy.getEconomicBeneficiaries()) {
            var abstractPerson = PolicyUtils.getPerson(ebo);

            if (abstractPerson.isPresent()) {
                boolean hasAtLeastOneSowOriginPrem = hasLeastOnSowOriginPremium(abstractPerson.get());
                if (hasAtLeastOneSowOriginPrem) {
                    shouldDisplay = true;
                    break;
                }
            }
        }

        if (!shouldDisplay) {
            return;
        }

        var originatorpep = (SelectInputField) ChecklistUtils.getFieldInGroup(group, ChecklistFieldConstants.ORIGINATOR_PEP);
        if (originatorpep == null) {
            originatorpep = SelectInputField.builder().fieldId(ChecklistFieldConstants.ORIGINATOR_PEP).build();
            group.getFields().add(originatorpep);
        }

        originatorpep.setIsActive(true);
        originatorpep.setEnabled(true);
        originatorpep.incrementOrder();
        originatorpep.setLabel("Is the Originator, linked to the source of funds to be invested, a PEP?");
        originatorpep.setMandatory(true);

        originatorpep.setDisplayIf("true");
        originatorpep.setOptions(referenceDataRepositoryService.getReferenceDataOptionsByDomain(YES_NO_DOMAIN));

    }

    private void evaluateIsPepPayer(final Group group, final BusinessTransaction transaction) {

        var isPepPayer = (SelectInputField) ChecklistUtils.getFieldInGroup(group, ChecklistFieldConstants.IS_PEP_PAYER);
        if (isPepPayer == null) {
            isPepPayer = SelectInputField.builder().fieldId(ChecklistFieldConstants.IS_PEP_PAYER).build();
            group.getFields().add(isPepPayer);
        }
        isPepPayer.setIsActive(true);
        isPepPayer.incrementOrder();
        isPepPayer.setLabel("Is one of the payer a PEP?");
        isPepPayer.setEnabled(true);
        isPepPayer.setMandatory(true);

        var forcedDisplayIf = "false";
        String paymentToThirdParty = getRiskFactorData(transaction, INT_RF_016);
        if (!paymentToThirdParty.contains("N/A") && !paymentToThirdParty.contains("Policy holder")) {
            forcedDisplayIf = "true";
        }
        isPepPayer.setDisplayIf(forcedDisplayIf);
        isPepPayer.setOptions(referenceDataRepositoryService.getReferenceDataOptionsByDomain(YES_NO_DOMAIN));
    }

    private void evaluateTccSigned(Group group) {

        var tccSigned = (SelectInputField) ChecklistUtils.getFieldInGroup(group, TCC_SIGNED);
        if (tccSigned == null) {
            tccSigned = SelectInputField.builder().fieldId(TCC_SIGNED).build();
            group.getFields().add(tccSigned);
        }
        tccSigned.setIsActive(true);
        tccSigned.incrementOrder();
        tccSigned.setLabel("TCC duly signed received?");
        tccSigned.setEnabled(true);
        tccSigned.setMandatory(true);
        tccSigned.setDisplayIf(null);
        tccSigned.setLabelBold(false);
        tccSigned.setSourceSystem(null);
        tccSigned.setOptions(referenceDataRepositoryService.getReferenceDataOptionsByDomain(YES_NO_DOMAIN));
    }

    private void evaluateTccSignedRefused(Group group) {

        var tccSignedRefused = (SelectInputField) ChecklistUtils.getFieldInGroup(group, TCC_SIGNED_REFUSED);
        if (tccSignedRefused == null) {
            tccSignedRefused = SelectInputField.builder().fieldId(TCC_SIGNED_REFUSED).build();
            group.getFields().add(tccSignedRefused);
        }
        tccSignedRefused.setIsActive(true);
        tccSignedRefused.incrementOrder();
        tccSignedRefused.setLabel("Did the PH/EBO refuse to sign the TCC and this after several reminders to sign the form?");
        tccSignedRefused.setEnabled(true);
        tccSignedRefused.setMandatory(true);
        tccSignedRefused.setDisplayIf("#TCC_SIGNED# == \"NO\"");
        tccSignedRefused.setLabelBold(false);
        tccSignedRefused.setSourceSystem(null);
        tccSignedRefused.setOptions(referenceDataRepositoryService.getReferenceDataOptionsByDomain(YES_NO_DOMAIN));
    }

    private void evaluateIntroducingPartnerSigned(Group group) {

        var introducingPartnerSigned = (SelectInputField) ChecklistUtils.getFieldInGroup(group, INTRODUCING_PARTNER_SIGNED);
        if (introducingPartnerSigned == null) {
            introducingPartnerSigned = SelectInputField.builder().fieldId(INTRODUCING_PARTNER_SIGNED).build();
            group.getFields().add(introducingPartnerSigned);
        }
        introducingPartnerSigned.setIsActive(true);
        introducingPartnerSigned.incrementOrder();
        introducingPartnerSigned.setLabel("Introducing partner signs KYC questionnaire");
        introducingPartnerSigned.setEnabled(true);
        introducingPartnerSigned.setMandatory(true);
        introducingPartnerSigned.setDisplayIf(null);
        introducingPartnerSigned.setLabelBold(false);
        introducingPartnerSigned.setSourceSystem(null);
        introducingPartnerSigned.setOptions(referenceDataRepositoryService.getReferenceDataOptionsByDomain(YES_NO_DOMAIN));
    }

    private void evaluateInfoProvidedVerified(Group group) {

        var infoProvidedVerified = (SelectInputField) ChecklistUtils.getFieldInGroup(group, INFO_PROVIDED_VERIFIED);
        if (infoProvidedVerified == null) {
            infoProvidedVerified = SelectInputField.builder().fieldId(INFO_PROVIDED_VERIFIED).build();
            group.getFields().add(infoProvidedVerified);
        }
        infoProvidedVerified.setIsActive(true);
        infoProvidedVerified.incrementOrder();
        infoProvidedVerified.setLabel("Info provided on KYC questionnaire could be verified");
        infoProvidedVerified.setEnabled(true);
        infoProvidedVerified.setMandatory(true);
        infoProvidedVerified.setDisplayIf(null);
        infoProvidedVerified.setLabelBold(false);
        infoProvidedVerified.setSourceSystem(null);
        infoProvidedVerified.setOptions(referenceDataRepositoryService.getReferenceDataOptionsByDomain(YES_NO_DOMAIN));
    }

    private void evaluateNegativeFinding(Group group, BusinessTransaction transaction, Map<String,List<String>> overallCaseRisk) {

        var negativeFinding = (SelectInputField) ChecklistUtils.getFieldInGroup(group, NEGATIVE_FINDING);
        if (negativeFinding == null) {
            negativeFinding = SelectInputField.builder().fieldId(NEGATIVE_FINDING).build();
            group.getFields().add(negativeFinding);
        }
        negativeFinding.setIsActive(true);
        negativeFinding.incrementOrder();
        negativeFinding.setLabel("Negative press finding / World check match (on all roles on policy)");
        negativeFinding.setEnabled(false);
        negativeFinding.setMandatory(true);
        negativeFinding.setDisplayIf(null);
        negativeFinding.setLabelBold(false);
        negativeFinding.setSourceSystem(null);

        final var riskFactorData = getRiskFactorData(transaction, INT_RF_006);
        if (riskFactorData.toUpperCase().startsWith("MISSING")) {
            negativeFinding.setSelectedValue(null);
            overallCaseRisk.get("BLOCKED").add(riskFactorData);
        } else {
            final var riskFactorDesc = RulesUtils.getRiskFactorAnswerDescription(transaction, INT_RF_006);
            if (riskFactorDesc != null) {
                negativeFinding.setSelectedValue(riskFactorDesc.toUpperCase());
            }
            negativeFinding.setOptions(referenceDataRepositoryService.getReferenceDataOptionsByDomainAndSelectedValue(YES_NO_DOMAIN,
                    negativeFinding.getSelectedValue()));
        }
    }

    private void evaluateNegativeFindingThirdParty(Group group, BusinessTransaction transaction) {

        var negativeFindingThirdParty = (TextInputField) ChecklistUtils.getFieldInGroup(group, NEGATIVE_FINDING_THIRD_PARTY);
        if (negativeFindingThirdParty == null) {
            negativeFindingThirdParty = TextInputField.builder().fieldId(NEGATIVE_FINDING_THIRD_PARTY).build();
            group.getFields().add(negativeFindingThirdParty);
        }

        final var riskFactorData = getRiskFactorData(transaction, INT_RF_006);
        final var riskFactorLevel = getRiskFactorLevel(transaction, INT_RF_006);
        var displayIf = "false";
        if (!riskFactorData.toUpperCase().startsWith("MISSING") && HIGH.equalsIgnoreCase(riskFactorLevel)) {
            negativeFindingThirdParty.setSelectedValue(riskFactorData);
            displayIf = "true";
        }
        negativeFindingThirdParty.setIsActive(true);
        negativeFindingThirdParty.incrementOrder();
        negativeFindingThirdParty.setLabel("Negative press finding / Worldcheck match on following Third party(ies)");
        negativeFindingThirdParty.setEnabled(false);
        negativeFindingThirdParty.setMandatory(false);
        negativeFindingThirdParty.setDisplayIf(displayIf);
        negativeFindingThirdParty.setLabelBold(false);
        negativeFindingThirdParty.setSourceSystem(null);
    }

    private void evaluateAtLeastOneSowOfKind(Group group, Policy policy) {

        var atLeastOneSowOfKind = (SelectInputField) ChecklistUtils.getFieldInGroup(group, AT_LEAST_ONE_SOW_OF_KIND);
        if (atLeastOneSowOfKind == null) {
            atLeastOneSowOfKind = SelectInputField.builder().fieldId(AT_LEAST_ONE_SOW_OF_KIND).build();
            group.getFields().add(atLeastOneSowOfKind);
        }
        atLeastOneSowOfKind.setIsActive(true);
        atLeastOneSowOfKind.incrementOrder();
        atLeastOneSowOfKind.setLabel("Invisible field : used as display if condition for ORIGINATOR_WORLD_CHECK");
        atLeastOneSowOfKind.setEnabled(false);
        atLeastOneSowOfKind.setMandatory(true);
        atLeastOneSowOfKind.setDisplayIf("false");
        atLeastOneSowOfKind.setLabelBold(false);
        atLeastOneSowOfKind.setSourceSystem(null);
        var abstractPerson = PolicyUtils.getPersons(policy.getEconomicBeneficiaries());

        var hasAtLeastOneSowOfKind = abstractPerson.stream().map(AbstractPerson::getSourceOfFunds).filter(Objects::nonNull)
                .flatMap(sourceOfFunds -> sourceOfFunds.getSourcesOfWealth().stream()).filter(Objects::nonNull)
                .anyMatch(sourcesOfWealth -> sourcesOfWealth.getDescription().matches("inheri_don|divorce|gift"));

        atLeastOneSowOfKind.setSelectedValue(hasAtLeastOneSowOfKind ? YES : NO);
    }

    private void evaluateOriginatorWorldCheck(Group group) {

        var originatorWorldCheck = (SelectInputField) ChecklistUtils.getFieldInGroup(group, ORIGINATOR_WORLD_CHECK);
        if (originatorWorldCheck == null) {
            originatorWorldCheck = SelectInputField.builder().fieldId(ORIGINATOR_WORLD_CHECK).build();
            group.getFields().add(originatorWorldCheck);
        }
        originatorWorldCheck.setIsActive(true);
        originatorWorldCheck.incrementOrder();
        originatorWorldCheck.setLabel("World check match or negative press found on originator");
        originatorWorldCheck.setEnabled(true);
        originatorWorldCheck.setMandatory(true);
        originatorWorldCheck.setLabelBold(false);
        originatorWorldCheck.setSourceSystem(null);
        originatorWorldCheck.setOptions(referenceDataRepositoryService.getReferenceDataOptionsByDomain(YES_NO_DOMAIN));
        originatorWorldCheck.setDisplayIf(
                Boolean.toString(Optional.ofNullable(ChecklistUtils.getFieldInGroup(group, ChecklistFieldConstants.AT_LEAST_ONE_SOW_OF_KIND))
                        .map(Field::getSelectedValue).stream().anyMatch(YES::equals)));
    }

    private void evaluateIsOnSanctionList(Group group, BusinessTransaction transaction, Map<String,List<String>> overallCaseRisk) {

        var isOnSanctionList = (SelectInputField) ChecklistUtils.getFieldInGroup(group, IS_ON_SANCTION_LIST);
        if (isOnSanctionList == null) {
            isOnSanctionList = SelectInputField.builder().fieldId(IS_ON_SANCTION_LIST).build();
            group.getFields().add(isOnSanctionList);
        }
        isOnSanctionList.setIsActive(true);
        isOnSanctionList.incrementOrder();
        isOnSanctionList.setLabel("Is there any person designated on a sanctions list on the policy?");
        isOnSanctionList.setEnabled(false);
        isOnSanctionList.setMandatory(true);
        isOnSanctionList.setDisplayIf(null);
        isOnSanctionList.setLabelBold(false);
        isOnSanctionList.setSourceSystem(null);
        final var isOnSanctionListRiskData = getRiskFactorData(transaction, INT_RF_007);
        if (StringUtils.containsIgnoreCase(isOnSanctionListRiskData, ("MISSING"))) {
            isOnSanctionList.setSelectedValue(null);
            overallCaseRisk.get(BLOCKED).add(isOnSanctionListRiskData);
        } else {
            final var isOnSanctionListRiskAnswer = RulesUtils.getRiskFactorAnswerDescription(transaction, INT_RF_007);
            isOnSanctionList.setSelectedValue(isOnSanctionListRiskAnswer);
        }
        isOnSanctionList.setOptions(referenceDataRepositoryService.getReferenceDataOptionsByDomainAndSelectedValue(YES_NO_DOMAIN,
                isOnSanctionList.getSelectedValue()));
    }

    private void evaluateInsider(Group group, Policy policy) {

        var insider = (SelectInputField) ChecklistUtils.getFieldInGroup(group, INSIDER);
        if (insider == null) {
            insider = SelectInputField.builder().fieldId(INSIDER).build();
            group.getFields().add(insider);
        }
        insider.setIsActive(true);
        insider.incrementOrder();
        insider.setLabel("Is the person an insider to any assets invested in the policy?");
        insider.setEnabled(false);
        insider.setMandatory(false);
        insider.setDisplayIf(null);
        insider.setLabelBold(false);
        insider.setSourceSystem(null);
        var isInsider = policy.getThirdParties().stream().map(AbstractPerson::getSourceOfFunds).filter(Objects::nonNull)
                .map(SourceOfFunds::getInsiderFlag).filter(Objects::nonNull).anyMatch(Boolean::booleanValue);

        var forcedValue = isInsider ? YES : NO;
        insider.setSelectedValue(forcedValue);
        insider.setOptions(
                referenceDataRepositoryService.getReferenceDataOptionsByDomainAndSelectedValue(YES_NO_DOMAIN, insider.getSelectedValue()));
    }

    private void evaluateIsPhEboGoldenVisa(Group group, BusinessTransaction transaction, Map<String,List<String>> overallCaseRisk) {

        var isPhEboGoldenVisa = (SelectInputField) ChecklistUtils.getFieldInGroup(group, IS_PH_EBO_GOLDEN_VISA);
        if (isPhEboGoldenVisa == null) {
            isPhEboGoldenVisa = SelectInputField.builder().fieldId(IS_PH_EBO_GOLDEN_VISA).build();
            group.getFields().add(isPhEboGoldenVisa);
        }
        isPhEboGoldenVisa.setIsActive(true);
        isPhEboGoldenVisa.incrementOrder();
        isPhEboGoldenVisa.setLabel(
                "Is the PH / EBO a country national who applied for residence rights or citizenship in exchange of capital transfers purchase of property or government bonds or investment in corporate entities (who has a Golden Visa) or a Golden Passport");
        isPhEboGoldenVisa.setEnabled(false);
        isPhEboGoldenVisa.setMandatory(true);
        isPhEboGoldenVisa.setDisplayIf(null);
        isPhEboGoldenVisa.setLabelBold(false);
        isPhEboGoldenVisa.setSourceSystem(null);
        var value = getRiskFactorData(transaction, INT_RF_013);
        var forcedValue = getRiskFactorAnswerDescription(transaction, INT_RF_013);
        if (value.contains("N/A")) {
            forcedValue = null;
            (overallCaseRisk.get(BLOCKED)).add("Please fill in the Golden Visa in CLASS");
        }

        isPhEboGoldenVisa.setSelectedValue(forcedValue);
        isPhEboGoldenVisa.setOptions(referenceDataRepositoryService.getReferenceDataOptionsByDomainAndSelectedValue(YES_NO_DOMAIN,
                isPhEboGoldenVisa.getSelectedValue()));
    }

    private void evaluateAnnualIncome(final Group group, final EconomicBeneficiary ebo, int position) {

        var annualIncome = (TextInputField) ChecklistUtils.getFieldInGroup(group, ChecklistFieldConstants.ANNUAL_INCOME + "_" + position);
        if (annualIncome == null) {
            annualIncome = TextInputField.builder().fieldId(ChecklistFieldConstants.ANNUAL_INCOME + "_" + position).build();
            group.getFields().add(annualIncome);
        }
        annualIncome.setIsActive(true);
        annualIncome.incrementOrder();
        annualIncome.setLabel("Annual income #" + position);
        annualIncome.setSourceSystem("FROM CLASS");

        var abstractPerson = PolicyUtils.getPerson(ebo);

        if (abstractPerson.isPresent() && abstractPerson.get().getSourceOfFunds() != null
                && abstractPerson.get().getSourceOfFunds().getAnnualIncome() != null) {
            annualIncome.setSelectedValue(String.valueOf(abstractPerson.get().getSourceOfFunds().getAnnualIncome().getQuantity()));
        } else {
            annualIncome.setSelectedValue("N/A");
        }

    }

    private void evaluateTwentyPercentIncome(final Group group, final EconomicBeneficiary ebo, int position) {

        var twentyPercentIncome = (SelectInputField) ChecklistUtils.getFieldInGroup(group, TWENTY_PERCENT_INCOME + "_" + position);
        if (twentyPercentIncome == null) {
            twentyPercentIncome = SelectInputField.builder().fieldId(TWENTY_PERCENT_INCOME + "_" + position).build();
            group.getFields().add(twentyPercentIncome);
        }
        twentyPercentIncome.setIsActive(true);
        twentyPercentIncome.incrementOrder();
        twentyPercentIncome.setLabel("A portion of wealth is from inheritance / Gift / Donation / Divorce #" + position);
        twentyPercentIncome.setSourceSystem("From CLASS");

        var abstractPerson = PolicyUtils.getPerson(ebo);

        if (abstractPerson.isPresent()) {
            boolean result = hasLeastOnSowOriginPremium(abstractPerson.get());

            twentyPercentIncome.setSelectedValue(result ? YES : NO);
        } else {
            twentyPercentIncome.setSelectedValue(NO);
        }
        twentyPercentIncome.setOptions(referenceDataRepositoryService.getReferenceDataOptionsByDomainAndSelectedValue(YES_NO_DOMAIN,
                twentyPercentIncome.getSelectedValue()));
    }

    private void evaluateSourceOfWealth(Group group, final int eboSiz) {

        var sourceOfWealth = (TextInputField) ChecklistUtils.getFieldInGroup(group, SOURCE_OF_WEALTH);
        if (sourceOfWealth == null) {
            sourceOfWealth = TextInputField.builder().fieldId(SOURCE_OF_WEALTH).build();
            group.getFields().add(sourceOfWealth);
        }
        sourceOfWealth.setIsActive(true);
        sourceOfWealth.incrementOrder();
        sourceOfWealth.setLabel("Source of wealth description");
        sourceOfWealth.setEnabled(true);
        sourceOfWealth.setMandatory(false);
        sourceOfWealth.setDisplayIf(null);
        sourceOfWealth.setLabelBold(false);
        sourceOfWealth.setSourceSystem(null);
    }

    private void evaluateMinimumWealth(Group group) {

        var minimumWealth = (SelectInputField) ChecklistUtils.getFieldInGroup(group, MINIMUM_WEALTH);
        if (minimumWealth == null) {
            minimumWealth = SelectInputField.builder().fieldId(MINIMUM_WEALTH).build();
            group.getFields().add(minimumWealth);
        }
        minimumWealth.setIsActive(true);
        minimumWealth.incrementOrder();
        minimumWealth.setLabel("Minimum wealth > 250000€ in transferrable assets (not including property)");
        minimumWealth.setEnabled(true);
        minimumWealth.setMandatory(true);
        minimumWealth.setDisplayIf(null);
        minimumWealth.setLabelBold(false);
        minimumWealth.setSourceSystem(null);
        minimumWealth.setOptions(referenceDataRepositoryService.getReferenceDataOptionsByDomain(YES_NO_DOMAIN));
    }

    private void evaluateWealthAllocationOk(Group group) {

        var wealthAllocationOk = (SelectInputField) ChecklistUtils.getFieldInGroup(group, WEALTH_ALLOCATION_OK);
        if (wealthAllocationOk == null) {
            wealthAllocationOk = SelectInputField.builder().fieldId(WEALTH_ALLOCATION_OK).build();
            group.getFields().add(wealthAllocationOk);
        }
        wealthAllocationOk.setIsActive(true);
        wealthAllocationOk.incrementOrder();
        wealthAllocationOk.setLabel("Wealth allocation in line with source");
        wealthAllocationOk.setEnabled(true);
        wealthAllocationOk.setMandatory(true);
        wealthAllocationOk.setDisplayIf(null);
        wealthAllocationOk.setLabelBold(false);
        wealthAllocationOk.setSourceSystem(null);
        wealthAllocationOk.setOptions(referenceDataRepositoryService.getReferenceDataOptionsByDomain(YES_NO_DOMAIN));
    }

    private void evaluateWealthOriginatingCountry1Risk(final Group group, final EconomicBeneficiary ebo, int position) {

        var wealthOriginationCountry1Risk =
                (TextInputField) ChecklistUtils.getFieldInGroup(group, ChecklistFieldConstants.WEALTH_ORIGINATING_COUNTRY_1_RISK + "_" + position);
        if (wealthOriginationCountry1Risk == null) {
            wealthOriginationCountry1Risk =
                    TextInputField.builder().fieldId(ChecklistFieldConstants.WEALTH_ORIGINATING_COUNTRY_1_RISK + "_" + position).build();
            group.getFields().add(wealthOriginationCountry1Risk);
        }
        wealthOriginationCountry1Risk.setIsActive(true);
        wealthOriginationCountry1Risk.incrementOrder();
        wealthOriginationCountry1Risk.setMandatory(true);
        wealthOriginationCountry1Risk.setLabel("Country 1 origin of premium #" + position);
        wealthOriginationCountry1Risk.setSourceSystem("From CLASS");

        var abstractPerson = PolicyUtils.getPerson(ebo);
        List<String> countries = new ArrayList<>();
        abstractPerson.ifPresent(person -> countries.addAll(
                PolicyUtils.getSourceOfWealthByPerson(person).stream().filter(sow -> sow.getOriginOfPremium() != null && sow.getOriginOfPremium())
                        .map(SourcesOfWealth::getWealthOriginatingcountry1).filter(Objects::nonNull).toList()));
        StringBuilder builder = new StringBuilder();
        if (!countries.isEmpty()) {
            builder.append(String.join(" ", countries));
        }
        wealthOriginationCountry1Risk.setSelectedValue(builder.toString());
    }

    private void evaluateWealthOriginatingCountry2Risk(final Group group, final EconomicBeneficiary ebo, int position) {

        var wealthOriginationCountry2Risk =
                (TextInputField) ChecklistUtils.getFieldInGroup(group, ChecklistFieldConstants.WEALTH_ORIGINATING_COUNTRY_2_RISK + "_" + position);
        if (wealthOriginationCountry2Risk == null) {
            wealthOriginationCountry2Risk =
                    TextInputField.builder().fieldId(ChecklistFieldConstants.WEALTH_ORIGINATING_COUNTRY_2_RISK + "_" + position).build();
            group.getFields().add(wealthOriginationCountry2Risk);
        }
        wealthOriginationCountry2Risk.setIsActive(true);
        wealthOriginationCountry2Risk.incrementOrder();
        wealthOriginationCountry2Risk.setLabel("Country 2 origin of premium #" + position);
        wealthOriginationCountry2Risk.setSourceSystem("FROM CLASS ");

        var abstractPerson = PolicyUtils.getPerson(ebo);
        List<String> countries = new ArrayList<>();
        abstractPerson.ifPresent(person -> countries.addAll(
                PolicyUtils.getSourceOfWealthByPerson(person).stream().filter(sow -> sow.getOriginOfPremium() != null && sow.getOriginOfPremium())
                        .map(SourcesOfWealth::getWealthOriginatingcountry2).filter(Objects::nonNull).toList()));

        StringBuilder builder = new StringBuilder();
        if (!countries.isEmpty()) {
            builder.append(String.join(" ", countries));
        }
        wealthOriginationCountry2Risk.setSelectedValue(builder.toString());
    }

    private void evaluateWealthAllocation(Group group) {

        var wealthAllocation = (TextInputField) ChecklistUtils.getFieldInGroup(group, WEALTH_ALLOCATION);
        if (wealthAllocation == null) {
            wealthAllocation = TextInputField.builder().fieldId(WEALTH_ALLOCATION).build();
            group.getFields().add(wealthAllocation);
        }
        wealthAllocation.setIsActive(true);
        wealthAllocation.incrementOrder();
        wealthAllocation.setLabel("Wealth allocation");
        wealthAllocation.setEnabled(true);
        wealthAllocation.setMandatory(true);
        wealthAllocation.setDisplayIf(null);
        wealthAllocation.setLabelBold(false);
        wealthAllocation.setSourceSystem(null);
    }

    private void evaluateTotalWealth(final Group group, EconomicBeneficiary ebo, int position) {

        var totalWealth = (TextInputField) ChecklistUtils.getFieldInGroup(group, ChecklistFieldConstants.TOTAL_WEALTH + "_" + position);
        if (totalWealth == null) {
            totalWealth = TextInputField.builder().fieldId(ChecklistFieldConstants.TOTAL_WEALTH + "_" + position).build();
            group.getFields().add(totalWealth);
        }
        totalWealth.setIsActive(true);
        totalWealth.incrementOrder();
        totalWealth.setLabel("Total wealth #" + position);

        var abstractPerson = PolicyUtils.getPerson(ebo);
        String totalWealthValue = abstractPerson.map(AbstractPerson::getSourceOfFunds).map(SourceOfFunds::getTotalWealth).map(Amount::getQuantity)
                .map(Objects::toString).orElse("N/A");
        totalWealth.setSelectedValue(totalWealthValue);
    }

    private void evaluateKycSupportingDocuments(Group group) {

        var kycSupportingDocuments = (SelectInputField) ChecklistUtils.getFieldInGroup(group, KYC_SUPPORTING_DOCUMENTS);
        if (kycSupportingDocuments == null) {
            kycSupportingDocuments = SelectInputField.builder().fieldId(KYC_SUPPORTING_DOCUMENTS).build();
            group.getFields().add(kycSupportingDocuments);
        }
        kycSupportingDocuments.setIsActive(true);
        kycSupportingDocuments.incrementOrder();
        kycSupportingDocuments.setLabel(
                "Are all KYC supporting documents (and if applicable the ones on the tax conformity of the funds) consistent and not altered (i.e. anomalies/ inconsistencies in the POR, documentation to corroborate the SOF/SOW such as no VAT number, no invoice number, no address, incorrect amount etc.)");
        kycSupportingDocuments.setEnabled(true);
        kycSupportingDocuments.setMandatory(true);
        kycSupportingDocuments.setDisplayIf(null);
        kycSupportingDocuments.setLabelBold(false);
        kycSupportingDocuments.setSourceSystem(null);
        kycSupportingDocuments.setOptions(referenceDataRepositoryService.getReferenceDataOptionsByDomain(YES_NO_DOMAIN));
    }

    private boolean hasLeastOnSowOriginPremium(AbstractPerson person) {

        return Optional.ofNullable(person).map(AbstractPerson::getSourceOfFunds).map(sof -> PolicyUtils.getSourceOfWealthByPerson(person))
                .orElseGet(Collections::emptyList).stream()
                .filter(sow -> sow.getDescription() != null && sow.getDescription().matches("inheri_don|divorce|gift"))
                .anyMatch(sow -> Boolean.TRUE.equals(sow.getOriginOfPremium()));

    }
}
