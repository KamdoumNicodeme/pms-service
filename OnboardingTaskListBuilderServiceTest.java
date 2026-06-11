@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class OnboardingPricingApprovalFieldsBuilderServiceTest extends OnboardingFieldsBuilderTestSupport {

    @InjectMocks
    private OnboardingPricingApprovalFieldsBuilderService builderService;

    @Test
    void buildPricingApprovalFieldsOK() {
        var group = group(PRICING_APPROVAL_GROUP);
        var order = new AtomicInteger(1);
        int expectedNumberOfFields = 11;

        builderService.buildField(
                webForm(),
                screenDescription(),
                group,
                policy(),
                transaction(),
                overallCaseRisk()
        );

        assertEquals(expectedNumberOfFields, group.getFields().size());

        FieldHelper.testFieldValueAndIncr(
                NTA_MARKUP_EXCEPTION,
                SelectInputField.class,
                group,
                null,
                order,
                null,
                false,
                false
        );

        FieldHelper.testFieldValueAndIncr(
                EXPLAIN_EXCEPTION,
                TextAreaField.class,
                group,
                null,
                order,
                null,
                false,
                false
        );

        FieldHelper.testFieldValueAndIncr(
                PRICING_APPROVAL_STAGE,
                TextInputField.class,
                group,
                null,
                order,
                null,
                false,
                false
        );

        FieldHelper.testFieldValueAndIncr(
                RATIONALE_FOR_EXCEPTION,
                TextInputField.class,
                group,
                null,
                order,
                null,
                false,
                false
        );

        FieldHelper.testFieldValueAndIncr(
                ADMINISTRATIVE_FEE,
                TextInputField.class,
                group,
                null,
                order,
                null,
                false,
                false
        );

        FieldHelper.testFieldValueAndIncr(
                GAC,
                TextInputField.class,
                group,
                null,
                order,
                null,
                false,
                false
        );

        FieldHelper.testFieldValueAndIncr(
                POLICY_FEE,
                TextInputField.class,
                group,
                null,
                order,
                null,
                false,
                false
        );

        FieldHelper.testFieldValueAndIncr(
                IS_FAMILY_CASE,
                SelectInputField.class,
                group,
                null,
                order,
                null,
                false,
                true
        );

        FieldHelper.testFieldValueAndIncr(
                FAMILY_CASE_POL_NBR,
                NumberInputField.class,
                group,
                null,
                order,
                null,
                false,
                true
        );

        FieldHelper.testFieldValueAndIncr(
                FAMILY_CASE_TOTAL_AMOUNT,
                NumberInputField.class,
                group,
                null,
                order,
                null,
                false,
                true
        );

        FieldHelper.testFieldValueAndIncr(
                PRICING_APPROVAL_CHECKED,
                SelectInputField.class,
                group,
                null,
                order,
                null,
                true,
                true
        );

        assertEquals(expectedNumberOfFields, order.get() - 1);

        verify(referenceDataRepositoryService, times(2))
                .getReferenceDataOptionsByDomain(YES_NO_DOMAIN);
    }

    @Test
    void buildPricingApprovalFieldsShouldPopulateYesNoOptions() {
        var group = group(PRICING_APPROVAL_GROUP);

        builderService.buildField(
                webForm(),
                screenDescription(),
                group,
                policy(),
                transaction(),
                overallCaseRisk()
        );

        var isFamilyCase = (SelectInputField) ChecklistUtils.getFieldInGroup(group, IS_FAMILY_CASE);
        var pricingApprovalChecked = (SelectInputField) ChecklistUtils.getFieldInGroup(group, PRICING_APPROVAL_CHECKED);

        assertNotNull(isFamilyCase);
        assertNotNull(pricingApprovalChecked);

        assertEquals(2, isFamilyCase.getOptions().size());
        assertEquals(YES, isFamilyCase.getOptions().getFirst().getKey());

        assertEquals(2, pricingApprovalChecked.getOptions().size());
        assertEquals(YES, pricingApprovalChecked.getOptions().getFirst().getKey());
    }
}
