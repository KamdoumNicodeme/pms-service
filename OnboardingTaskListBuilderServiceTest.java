@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class OnboardingTransactionDetailsFieldsBuilderServiceTest extends OnboardingFieldsBuilderTestSupport {

    @InjectMocks
    private OnboardingTransactionDetailsFieldsBuilderService builderService;

    @Test
    void buildTransactionDetailsFieldsOK() {
        var group = group(TRANSACTION_DETAILS_GROUP);
        var transaction = transaction();
        var webForm = webFormWithTransactionDetailsValues();
        var order = new AtomicInteger(1);

        int expectedNumberOfFields = 31;

        builderService.buildField(
                webForm,
                screenDescription(),
                group,
                policy(),
                transaction,
                overallCaseRisk()
        );

        assertEquals(expectedNumberOfFields, group.getFields().size());

        FieldHelper.testSelectFieldValueAndIncr(PREMIUM_WITH_ASSETS, SelectInputField.class,
                group, "YES", order, null, true, null, 1);

        FieldHelper.testSelectFieldValueAndIncr(PREMIUM_WITH_UNQ_ASSETS, SelectInputField.class,
                group, "NO", order, null, true, null, 1);

        FieldHelper.testSelectFieldValueAndIncr(IS_ROP_CASE, SelectInputField.class,
                group, null, order, null, true, true, 2);

        FieldHelper.testFieldValueAndIncr(INITIAL_PREM, NumberInputField.class,
                group, null, order, "#IS_ROP_CASE# == \"YES\"", true, true);

        FieldHelper.testSelectFieldValueAndIncr(INVESTED_IN_ILF, SelectInputField.class,
                group, null, order, null, true, true, 2);

        FieldHelper.testSelectFieldValueAndIncr(EXISTING_ILF, SelectInputField.class,
                group, null, order, "#INVESTED_IN_ILF# == \"YES\"", true, true, 2);

        FieldHelper.testFieldValueAndIncr(ILF_MNEMONIC, TextInputField.class,
                group, null, order, "#EXISTING_ILF# == \"YES\"", false, true);

        FieldHelper.testSelectFieldValueAndIncr(IS_DEALING, SelectInputField.class,
                group, null, order, null, true, true, 2);

        FieldHelper.testSelectFieldValueAndIncr(PAYMENT_THIRD_PARTY, SelectInputField.class,
                group, "NO", order, null, true, false, 1);

        FieldHelper.testSelectFieldValueAndIncr(PAYER_IN_SANCTION_LIST, SelectInputField.class,
                group, null, order, "false", true, true, 2);

        FieldHelper.testSelectFieldValueAndIncr(NEGATIVE_FINDING_PAYERS, SelectInputField.class,
                group, null, order, "false", true, true, 2);

        FieldHelper.testSelectFieldValueAndIncr(PAYER_CORPORATE_ENTITY, SelectInputField.class,
                group, null, order, "false", true, true, 2);

        FieldHelper.testSelectFieldValueAndIncr(PAYER_NOT_LOCATED, SelectInputField.class,
                group, null, order, "false", true, true, 2);

        FieldHelper.testSelectFieldValueAndIncr(EVIDENCE_LEGAL_ENTITY, SelectInputField.class,
                group, null, order, "false", true, true, 2);

        FieldHelper.testSelectFieldValueAndIncr(CLOSE_TO_EBO, SelectInputField.class,
                group, null, order, null, true, true, 2);

        FieldHelper.testSelectFieldValueAndIncr(BANK_NOT_IN_RESIDENCE, SelectInputField.class,
                group, null, order, null, true, true, 2);

        FieldHelper.testSelectFieldValueAndIncr(ECONOMIC_JUSTIF, SelectInputField.class,
                group, null, order, "#BANK_NOT_IN_RESIDENCE# == \"YES\"", true, true, 2);

        FieldHelper.testSelectFieldValueAndIncr(EVIDENCE_TAX_DECLARED, SelectInputField.class,
                group, null, order, "#BANK_NOT_IN_RESIDENCE# == \"YES\" && #ECONOMIC_JUSTIF# == \"NO\"", true, true, 2);

        FieldHelper.testSelectFieldValueAndIncr(REFUSED_ADDITIONAL_INFO, SelectInputField.class,
                group, null, order,
                "#BANK_NOT_IN_RESIDENCE# == \"YES\" && #ECONOMIC_JUSTIF# == \"NO\" && #EVIDENCE_TAX_DECLARED# == \"NO\"",
                true, true, 2);

        FieldHelper.testSelectFieldValueAndIncr(PREMIUM_RECEIVED_DIFFERENT_EXPECTED, SelectInputField.class,
                group, null, order, null, true, true, 2);

        FieldHelper.testSelectFieldValueAndIncr(SAME_AS_DISCLOSED, SelectInputField.class,
                group, null, order, "#PREMIUM_RECEIVED_DIFFERENT_EXPECTED# == \"YES\\", true, true, 2);

        FieldHelper.testFieldValueAndIncr(RATIONALE_FOR_INVESTMENT, TextAreaField.class,
                group, null, order, null, false, true);

        FieldHelper.testSelectFieldValueAndIncr(NUMBER_OF_ORIGINATING_ACCOUNTS, SelectInputField.class,
                group, "2", order, null, true, null, 1);

        FieldHelper.testFieldValueAndIncr(NAME_OF_ORIGINATING_ACCOUNT_HOLDER + "_1", TextInputField.class,
                group, "909090", order, null, true, null);

        FieldHelper.testSelectFieldValueAndIncr(COUNTRY_OF_ORIGINATING_ACCOUNTS + "_1", SelectInputField.class,
                group, "BE", order, null, true, null, 1);

        FieldHelper.testFieldValueAndIncr(BANK_OF_ORIGINATING_ACCOUNTS + "_1", TextInputField.class,
                group, "BNP", order, null, true, null);

        FieldHelper.testFieldValueAndIncr(NAME_OF_ORIGINATING_ACCOUNT_HOLDER + "_2", TextInputField.class,
                group, "123456789", order, null, true, null);

        FieldHelper.testSelectFieldValueAndIncr(COUNTRY_OF_ORIGINATING_ACCOUNTS + "_2", SelectInputField.class,
                group, "BE", order, null, true, null, 1);

        FieldHelper.testFieldValueAndIncr(BANK_OF_ORIGINATING_ACCOUNTS + "_2", TextInputField.class,
                group, "ING", order, null, true, null);

        FieldHelper.testFieldValueAndIncr(COUNTRY_OF_ORIGINATING_ACCOUNTS_RISK, TextInputField.class,
                group, "STANDARD", order, "true", true, null);

        assertEquals(expectedNumberOfFields, order.get() - 1);
    }

    @Test
    void buildTransactionDetailsFieldsShouldBlockWhenPaymentThirdPartyCannotBeAssessed() {
        var group = group(TRANSACTION_DETAILS_GROUP);
        var transaction = transaction();
        var overallCaseRisk = overallCaseRisk();

        transaction.getRiskFactorResults().stream()
                .filter(rf -> INT_RF_016.equals(rf.getReference()))
                .findFirst()
                .orElseThrow()
                .setData("N/A");

        builderService.buildField(
                webFormWithTransactionDetailsValues(),
                screenDescription(),
                group,
                policy(),
                transaction,
                overallCaseRisk
        );

        FieldHelper.testSelectFieldValue(PAYMENT_THIRD_PARTY, SelectInputField.class,
                group, null, 9, null, true, false, 0);

        assertTrue(overallCaseRisk.get(BLOCKED).contains("3rd Party Payment risk factor cannot be assessed"));
    }

    @Test
    void buildTransactionDetailsFieldsShouldBlockWhenPayerPayeeCountryMissing() {
        var group = group(TRANSACTION_DETAILS_GROUP);
        var transaction = transaction();
        var overallCaseRisk = overallCaseRisk();

        transaction.getRiskFactorResults().stream()
                .filter(rf -> INT_RF_012.equals(rf.getReference()))
                .findFirst()
                .orElseThrow()
                .setData("N/A");

        builderService.buildField(
                webFormWithTransactionDetailsValues(),
                screenDescription(),
                group,
                policy(),
                transaction,
                overallCaseRisk
        );

        FieldHelper.testFieldValue(COUNTRY_OF_ORIGINATING_ACCOUNTS_RISK, TextInputField.class,
                group, null, 31, "true", true, null);

        assertTrue(overallCaseRisk.get(BLOCKED).contains("Missing payer/payee bank country"));
    }

    private WebForm webFormWithTransactionDetailsValues() {
        return WebForm.builder()
                .groups(List.of(
                        WebFormGroup.builder()
                                .booleanFields(List.of(
                                        BooleanWebFormField.builder()
                                                .fieldId(IS_ASSET_TRANSFER_WF)
                                                .value(true)
                                                .build(),
                                        BooleanWebFormField.builder()
                                                .fieldId(HAVE_UNQUOTED_PRODUCT_WF)
                                                .value(false)
                                                .build()
                                ))
                                .textFields(List.of(
                                        TextWebFormField.builder()
                                                .fieldId(KYC_INTRO_PURPOSE_OF_INVESTMENT_WF)
                                                .value("Investment purpose")
                                                .build()
                                ))
                                .build()
                ))
                .build();
    }
}
