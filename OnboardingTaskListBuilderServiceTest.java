@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class OnboardingThirdPartyFieldsBuilderServiceTest extends OnboardingFieldsBuilderTestSupport {

    @InjectMocks
    private OnboardingThirdPartyFieldsBuilderService builderService;

    @Test
    void buildThirdPartyFieldsOK() {
        var group = group(THIRD_PARTY_GROUP);
        var transaction = transaction();
        var order = new AtomicInteger(1);

        builderService.buildField(
                webForm(),
                screenDescription(),
                group,
                policy(),
                transaction,
                overallCaseRisk()
        );

        assertEquals(10, group.getFields().size());

        FieldHelper.testFieldValueAndIncr(
                THIRD_PARTY_NAME + "_1",
                TextInputField.class,
                group,
                null,
                order,
                null,
                true,
                null
        );

        FieldHelper.testSelectFieldValue(
                LINK_THIRD_PARTY_PH + "_1",
                SelectInputField.class,
                group,
                null,
                order.getAndIncrement(),
                null,
                true,
                null,
                0
        );

        FieldHelper.testFieldValueAndIncr(
                REASON_THIRD_PARTY_PAYMENT + "_1",
                TextInputField.class,
                group,
                null,
                order,
                null,
                null,
                true
        );

        FieldHelper.testSelectFieldValue(
                THIRD_PARTY_COUNTRY + "_1",
                SelectInputField.class,
                group,
                null,
                order.getAndIncrement(),
                null,
                true,
                null,
                0
        );

        FieldHelper.testFieldValueAndIncr(
                THIRD_PARTY_NAME + "_2",
                TextInputField.class,
                group,
                null,
                order,
                null,
                true,
                null
        );

        FieldHelper.testSelectFieldValue(
                LINK_THIRD_PARTY_PH + "_2",
                SelectInputField.class,
                group,
                null,
                order.getAndIncrement(),
                null,
                true,
                null,
                0
        );

        FieldHelper.testFieldValueAndIncr(
                REASON_THIRD_PARTY_PAYMENT + "_2",
                TextInputField.class,
                group,
                null,
                order,
                null,
                null,
                true
        );

        FieldHelper.testSelectFieldValue(
                THIRD_PARTY_COUNTRY + "_2",
                SelectInputField.class,
                group,
                null,
                order.getAndIncrement(),
                null,
                true,
                null,
                0
        );

        FieldHelper.testFieldValueAndIncr(
                THIRD_PARTY_COUNTRY_RISK,
                TextInputField.class,
                group,
                RulesUtils.getRiskFactorLevel(transaction, INT_RF_016),
                order,
                null,
                null,
                null
        );

        FieldHelper.testFieldValueAndIncr(
                ADDITIONAL_INFO_LINK,
                TextAreaField.class,
                group,
                null,
                order,
                "#LINK_THIRD_PARTY_PH# == \"other\"",
                false,
                true
        );

        assertEquals(10, order.get() - 1);
    }

    @Test
    void buildThirdPartyFieldsShouldPopulatePaymentDetailsValues() {
        var group = group(THIRD_PARTY_GROUP);
        var transaction = transaction();

        builderService.buildField(
                webForm(),
                screenDescription(),
                group,
                policy(),
                transaction,
                overallCaseRisk()
        );

        var firstPayment = ChecklistUtils.getMoneyInTransactionPaymentDetails(transaction).getFirst();

        FieldHelper.testFieldValue(
                THIRD_PARTY_NAME + "_1",
                TextInputField.class,
                group,
                firstPayment.getPayerID(),
                1,
                null,
                true,
                null
        );

        var link = (SelectInputField) ChecklistUtils.getFieldInGroup(group, LINK_THIRD_PARTY_PH + "_1");
        var country = (SelectInputField) ChecklistUtils.getFieldInGroup(group, THIRD_PARTY_COUNTRY + "_1");

        assertNotNull(link);
        assertNotNull(country);

        if (firstPayment.getOriginator() != null) {
            assertEquals(firstPayment.getOriginator().getExternalId(), link.getSelectedValue());
            assertEquals(1, link.getOptions().size());
        }

        if (firstPayment.getPayerLegalAddressCountry() != null) {
            assertEquals(firstPayment.getPayerLegalAddressCountry().getIsoCountryCode(), country.getSelectedValue());
            assertEquals(1, country.getOptions().size());
        }

        verify(referenceDataRepositoryService, atLeastOnce())
                .getReferenceDataOptionsByDomainAndSelectedValue(eq(THIRD_PARTY_TYPE_DOMAIN), any());

        verify(referenceDataRepositoryService, atLeastOnce())
                .getReferenceDataOptionsByDomainAndSelectedValue(eq(COUNTRY_DOMAIN), any());
    }

    @Test
    void buildThirdPartyFieldsWithoutPaymentDetailsShouldOnlyBuildRiskAndAdditionalInfo() {
        var group = group(THIRD_PARTY_GROUP);
        var transaction = transaction();
        transaction.setPaymentDetails(List.of());

        builderService.buildField(
                webForm(),
                screenDescription(),
                group,
                policy(),
                transaction,
                overallCaseRisk()
        );

        assertEquals(2, group.getFields().size());

        FieldHelper.testMissingField(THIRD_PARTY_NAME + "_1", TextInputField.class, group);
        FieldHelper.testMissingField(LINK_THIRD_PARTY_PH + "_1", SelectInputField.class, group);
        FieldHelper.testMissingField(REASON_THIRD_PARTY_PAYMENT + "_1", TextInputField.class, group);
        FieldHelper.testMissingField(THIRD_PARTY_COUNTRY + "_1", SelectInputField.class, group);

        FieldHelper.testFieldValue(
                THIRD_PARTY_COUNTRY_RISK,
                TextInputField.class,
                group,
                RulesUtils.getRiskFactorLevel(transaction, INT_RF_016),
                1,
                null,
                null,
                null
        );

        FieldHelper.testFieldValue(
                ADDITIONAL_INFO_LINK,
                TextAreaField.class,
                group,
                null,
                2,
                "#LINK_THIRD_PARTY_PH# == \"other\"",
                false,
                true
        );
    }
}
