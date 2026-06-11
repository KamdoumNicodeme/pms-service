ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ThirdPartyAdditionFieldsBuilderServiceTest {

    @InjectMocks
    private ThirdPartyAdditionFieldsBuilderService thirdPartyAdditionFieldsBuilderService;

    @Mock
    private ReferenceDataRepositoryService referenceDataRepositoryService;

    @BeforeEach
    void initMock() {

        Mockito.when(referenceDataRepositoryService.getReferenceDataOptionsByDomain(THIRD_PARTY_TYPE_DOMAIN))
                .thenReturn(ReferenceServiceHelper.getThirdPartyTypeOptions());
        Mockito.when(referenceDataRepositoryService.getReferenceDataOptionsByDomainAndSelectedValue(COUNTRY_DOMAIN, "BE"))
                .thenReturn(List.of(new SelectInputFieldOption("BE", "Belgium")));
        Mockito.when(referenceDataRepositoryService.getReferenceDataOptionsByDomainAndSelectedValue(COUNTRY_DOMAIN, "N/A"))
                .thenReturn(List.of(new SelectInputFieldOption("N/A", "N/A")));
    }

    @Test
    void builderAddedInfoFields_OK() {

        Policy policy = PolicyBuilderServiceHelper.createPolicy();
        Group addedInfo = Group.builder().groupId(RulesConstants.THIRD_PARTY_GROUP).build();
        WebForm webForm = WebFormBuilderServiceHelper.createWebForm();
        BusinessTransaction transaction = TransactionBuilderServiceHelper.createTransaction();
        /* Used to automatically increment "order" by 1 for each individual field test. Makes adding/removing a field less painful */
        AtomicInteger testOrder = new AtomicInteger(1);
        int expectedNumberOfFields = 13;

        thirdPartyAdditionFieldsBuilderService.buildField(webForm, new ScreenDescription(), addedInfo, policy, transaction,
                Map.of("BLOCKED", new ArrayList<>()));
        assertEquals(expectedNumberOfFields, addedInfo.getFields().size());

        FieldHelper.testFieldValueAndIncr(THIRD_PARTY_NAME + "_1", TextInputField.class, addedInfo, "909090", testOrder, null, true, false);
        FieldHelper.testSelectFieldValueAndIncr(LINK_THIRD_PARTY_PH + "_1", SelectInputField.class, addedInfo, "originator", testOrder, null, true,
                false, 0);
        FieldHelper.testFieldValueAndIncr(REASON_THIRD_PARTY_PAYMENT + "_1", TextInputField.class, addedInfo, null, testOrder, null, false, true);
        FieldHelper.testSelectFieldValueAndIncr(THIRD_PARTY_COUNTRY + "_1", SelectInputField.class, addedInfo, null, testOrder, null, true, false,
                0);
        FieldHelper.testFieldValueAndIncr(THIRD_PARTY_NAME + "_2", TextInputField.class, addedInfo, "123456789", testOrder, null, true, false);
        FieldHelper.testSelectFieldValueAndIncr(LINK_THIRD_PARTY_PH + "_2", SelectInputField.class, addedInfo, "originator", testOrder, null, true,
                false, 0);
        FieldHelper.testFieldValueAndIncr(REASON_THIRD_PARTY_PAYMENT + "_2", TextInputField.class, addedInfo, null, testOrder, null, false, true);
        FieldHelper.testSelectFieldValueAndIncr(THIRD_PARTY_COUNTRY + "_2", SelectInputField.class, addedInfo, null, testOrder, null, true, false,
                0);
        FieldHelper.testFieldValueAndIncr(THIRD_PARTY_NAME + "_3", TextInputField.class, addedInfo, "99118822", testOrder, null, true, false);
        FieldHelper.testSelectFieldValueAndIncr(LINK_THIRD_PARTY_PH + "_3", SelectInputField.class, addedInfo, "originator", testOrder, null, true,
                false, 0);
        FieldHelper.testFieldValueAndIncr(REASON_THIRD_PARTY_PAYMENT + "_3", TextInputField.class, addedInfo, null, testOrder, null, false, true);
        FieldHelper.testSelectFieldValueAndIncr(THIRD_PARTY_COUNTRY + "_3", SelectInputField.class, addedInfo, null, testOrder, null, true, false,
                0);
        FieldHelper.testFieldValueAndIncr(THIRD_PARTY_COUNTRY_RISK, TextInputField.class, addedInfo, "STANDARD", testOrder, null, false, false);

        /* To make sure we accounted for all fields built in the group */
        assertEquals(expectedNumberOfFields, testOrder.get() - 1);
    }

    @Test
    void builderBankCountry_NA() {

        Policy policy = PolicyBuilderServiceHelper.createPolicy();
        Group generalDetails = Group.builder().groupId(RulesConstants.THIRD_PARTY_GROUP).build();
        WebForm webForm = WebForm.builder()
                .groups(Collections.singletonList(
                        WebFormGroup.builder().textFields(List.of(TextWebFormField.builder().fieldId("BANK_COUNTRY").value("").build())).build()))
                .build();
        BusinessTransaction transaction = TransactionBuilderServiceHelper.createTransaction();
        thirdPartyAdditionFieldsBuilderService.buildField(webForm, new ScreenDescription(), generalDetails, policy, transaction,
                Map.of("BLOCKED", new ArrayList<>()));
        assertEquals(13, generalDetails.getFields().size());

        FieldHelper.testSelectFieldValue(THIRD_PARTY_COUNTRY + "_1", SelectInputField.class, generalDetails, null, 4, null, true, false, 0);
        FieldHelper.testSelectFieldValue(THIRD_PARTY_COUNTRY + "_2", SelectInputField.class, generalDetails, null, 8, null, true, false, 0);
        FieldHelper.testFieldValue(THIRD_PARTY_COUNTRY_RISK, TextInputField.class, generalDetails, "STANDARD", 13, null, false, false);
    }
}
