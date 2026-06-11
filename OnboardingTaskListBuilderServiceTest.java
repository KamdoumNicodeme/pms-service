@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class OnboardingThirdPartyFieldsBuilderServiceTest extends OnboardingFieldsBuilderTestSupport {

    @InjectMocks
    private OnboardingThirdPartyFieldsBuilderService builderService;

    @Test
    void buildThirdPartyFieldsOK() {
        Policy policy = policy();
        Group thirdParty = group(THIRD_PARTY_GROUP);
        WebForm webForm = webForm();
        BusinessTransaction transaction = transaction();

        AtomicInteger testOrder = new AtomicInteger(1);
        int expectedNumberOfFields = 10;

        builderService.buildField(
                webForm,
                screenDescription(),
                thirdParty,
                policy,
                transaction,
                overallCaseRisk()
        );

        assertEquals(expectedNumberOfFields, thirdParty.getFields().size());

        FieldHelper.testFieldValueAndIncr(THIRD_PARTY_NAME + "_1", TextInputField.class,
                thirdParty, "909090", testOrder, null, true, null);

        FieldHelper.testSelectFieldValueAndIncr(LINK_THIRD_PARTY_PH + "_1", SelectInputField.class,
                thirdParty, "originator", testOrder, null, true, null, 1);

        FieldHelper.testFieldValueAndIncr(REASON_THIRD_PARTY_PAYMENT + "_1", TextInputField.class,
                thirdParty, null, testOrder, null, null, true);

        FieldHelper.testSelectFieldValueAndIncr(THIRD_PARTY_COUNTRY + "_1", SelectInputField.class,
                thirdParty, null, testOrder, null, true, null, 0);

        FieldHelper.testFieldValueAndIncr(THIRD_PARTY_NAME + "_2", TextInputField.class,
                thirdParty, "123456789", testOrder, null, true, null);

        FieldHelper.testSelectFieldValueAndIncr(LINK_THIRD_PARTY_PH + "_2", SelectInputField.class,
                thirdParty, "originator", testOrder, null, true, null, 1);

        FieldHelper.testFieldValueAndIncr(REASON_THIRD_PARTY_PAYMENT + "_2", TextInputField.class,
                thirdParty, null, testOrder, null, null, true);

        FieldHelper.testSelectFieldValueAndIncr(THIRD_PARTY_COUNTRY + "_2", SelectInputField.class,
                thirdParty, null, testOrder, null, true, null, 0);

        FieldHelper.testFieldValueAndIncr(THIRD_PARTY_COUNTRY_RISK, TextInputField.class,
                thirdParty, "STANDARD", testOrder, null, true, null);

        FieldHelper.testFieldValueAndIncr(ADDITIONAL_INFO_LINK, TextAreaField.class,
                thirdParty, null, testOrder, "#LINK_THIRD_PARTY_PH# == \"other\"", false, true);

        assertEquals(expectedNumberOfFields, testOrder.get() - 1);

        verify(referenceDataRepositoryService, atLeastOnce())
                .getReferenceDataOptionsByDomainAndSelectedValue(eq(THIRD_PARTY_TYPE_DOMAIN), any());

        verify(referenceDataRepositoryService, atLeastOnce())
                .getReferenceDataOptionsByDomainAndSelectedValue(eq(COUNTRY_DOMAIN), any());
    }

    @Test
    void buildThirdPartyFieldsWithoutPaymentDetailsOK() {
        Policy policy = policy();
        Group thirdParty = group(THIRD_PARTY_GROUP);
        BusinessTransaction transaction = transaction();

        transaction.setPaymentDetails(Collections.emptyList());

        AtomicInteger testOrder = new AtomicInteger(1);
        int expectedNumberOfFields = 2;

        builderService.buildField(
                webForm(),
                screenDescription(),
                thirdParty,
                policy,
                transaction,
                overallCaseRisk()
        );

        assertEquals(expectedNumberOfFields, thirdParty.getFields().size());

        FieldHelper.testMissingField(THIRD_PARTY_NAME + "_1", TextInputField.class, thirdParty);
        FieldHelper.testMissingField(LINK_THIRD_PARTY_PH + "_1", SelectInputField.class, thirdParty);
        FieldHelper.testMissingField(REASON_THIRD_PARTY_PAYMENT + "_1", TextInputField.class, thirdParty);
        FieldHelper.testMissingField(THIRD_PARTY_COUNTRY + "_1", SelectInputField.class, thirdParty);

        FieldHelper.testFieldValueAndIncr(THIRD_PARTY_COUNTRY_RISK, TextInputField.class,
                thirdParty, "STANDARD", testOrder, null, true, null);

        FieldHelper.testFieldValueAndIncr(ADDITIONAL_INFO_LINK, TextAreaField.class,
                thirdParty, null, testOrder, "#LINK_THIRD_PARTY_PH# == \"other\"", false, true);

        assertEquals(expectedNumberOfFields, testOrder.get() - 1);
    }
}
