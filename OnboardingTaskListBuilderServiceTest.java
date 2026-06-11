@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class OnboardingIntermediationFieldsBuilderServiceTest extends OnboardingFieldsBuilderTestSupport {

    @InjectMocks
    private OnboardingIntermediationFieldsBuilderService builderService;

    @Test
    void buildIntermediationFieldsOK() {
        var group = group(INTERMEDIATION_GROUP);

        builderService.buildField(
                webFormWithIntermediate(),
                screenDescription(),
                group,
                policy(),
                transaction(),
                overallCaseRisk()
        );

        assertEquals(16, group.getFields().size());

        FieldHelper.testSelectFieldValue(APP_FORM_SIGNER, SelectInputField.class,
                group, null, 1, null, true, true, 3);

        FieldHelper.testFieldValue(PARTNER_CODE, TextInputField.class,
                group, "KROKER1212", 2, null, true, false);

        FieldHelper.testFieldValue(PARTNER_NAME, TextInputField.class,
                group, "My Partner", 3, null, true, false);

        FieldHelper.testSelectFieldValue(PARTNER_TYPE, SelectInputField.class,
                group, null, 4, null, true, false, 4);

        FieldHelper.testSelectFieldValue(IS_PH_REPRESENTATIVE, SelectInputField.class,
                group, "NO", 5, null, true, null, 1);

        FieldHelper.testSelectFieldValue(REFERRER_AUTO, SelectInputField.class,
                group, null, 6, "#PARTNER_TYPE# == \"REFERRER\"", true, true, 2);

        FieldHelper.testSelectFieldValue(PARTNER_STATUS, SelectInputField.class,
                group, null, 7, null, false, false, 2);

        FieldHelper.testSelectFieldValue(ANALYSIS_OF_NEEDS, SelectInputField.class,
                group, null, 8,
                "#PARTNER_TYPE# == \"AGENT_EMP\" || #PARTNER_TYPE# == \"AGENT_IND\" || #PARTNER_TYPE# == \"AGENT_EXT\"",
                true, true, 2);

        FieldHelper.testSelectFieldValue(NB_INTERMEDIATED_DS, SelectInputField.class,
                group, null, 9, null, true, false, 0);

        FieldHelper.testSelectFieldValue(DS_CONSENT_RECEIVED, SelectInputField.class,
                group, null, 10,
                "#NB_INTERMEDIATED_DS# == \"YES\" && (#PARTNER_TYPE# == \"AGENT_EMP\" || #PARTNER_TYPE# == \"AGENT_IND\")",
                true, true, 2);

        FieldHelper.testSelectFieldValue(VIDEO_RECEIVED, SelectInputField.class,
                group, null, 11,
                "#NB_INTERMEDIATED_DS# == \"YES\" && (#PARTNER_TYPE# == \"AGENT_EMP\" || #PARTNER_TYPE# == \"AGENT_IND\")",
                true, true, 2);

        FieldHelper.testSelectFieldValue(CLIENT_IDENTIFIABLE, SelectInputField.class,
                group, null, 12,
                "#NB_INTERMEDIATED_DS# == \"YES\" && (#PARTNER_TYPE# == \"AGENT_EMP\" || #PARTNER_TYPE# == \"AGENT_IND\")",
                true, true, 2);

        FieldHelper.testSelectFieldValue(HASH_CHECK_PERFORMED, SelectInputField.class,
                group, null, 13,
                "#NB_INTERMEDIATED_DS# == \"YES\" && (#PARTNER_TYPE# == \"AGENT_EMP\" || #PARTNER_TYPE# == \"AGENT_IND\")",
                true, true, 2);

        FieldHelper.testSelectFieldValue(PASSPORT_CHECK_PERFORMED, SelectInputField.class,
                group, null, 14,
                "#NB_INTERMEDIATED_DS# == \"YES\" && (#PARTNER_TYPE# == \"AGENT_EMP\" || #PARTNER_TYPE# == \"AGENT_IND\")",
                true, true, 2);

        FieldHelper.testSelectFieldValue(REQUEST_DS_PROCEDURE, SelectInputField.class,
                group, "NO", 15, "false", false, false, 1);

        FieldHelper.testSelectFieldValue(VULNERABLE_INDICIA_DETECTED, SelectInputField.class,
                group, null, 16, null, true, true, 2);

        var signer = (SelectInputField) ChecklistUtils.getFieldInGroup(group, APP_FORM_SIGNER);
        assertEquals("BROKER", signer.getOptions().getFirst().getKey());

        verify(referenceDataRepositoryService).getReferenceDataOptionsByDomainAndSelectedValue(PARTNER_TYPE_DOMAIN, null);
        verify(referenceDataRepositoryService, atLeastOnce()).getReferenceDataOptionsByDomain(YES_NO_DOMAIN);
    }

    @Test
    void buildIntermediationFieldsShouldUseNAWhenIntermediateMissing() {
        var group = group(INTERMEDIATION_GROUP);

        builderService.buildField(
                webForm(),
                screenDescription(),
                group,
                policy(),
                transaction(),
                overallCaseRisk()
        );

        FieldHelper.testFieldValue(PARTNER_CODE, TextInputField.class,
                group, "N/A", 2, null, true, false);

        FieldHelper.testFieldValue(PARTNER_NAME, TextInputField.class,
                group, "N/A", 3, null, true, false);
    }

    @Test
    void buildIntermediationFieldsShouldSetNbIntermediatedDsToYesWhenDistance() {
        var group = group(INTERMEDIATION_GROUP);
        var policy = policy();
        policy.setSellingMeetingType("DISTANCE");

        builderService.buildField(
                webFormWithIntermediate(),
                screenDescription(),
                group,
                policy,
                transaction(),
                overallCaseRisk()
        );

        FieldHelper.testSelectFieldValue(NB_INTERMEDIATED_DS, SelectInputField.class,
                group, "YES", 9, null, true, false, 0);
    }

    @Test
    void buildIntermediationFieldsShouldSetNbIntermediatedDsToNoWhenFace() {
        var group = group(INTERMEDIATION_GROUP);
        var policy = policy();
        policy.setSellingMeetingType("FACE");

        builderService.buildField(
                webFormWithIntermediate(),
                screenDescription(),
                group,
                policy,
                transaction(),
                overallCaseRisk()
        );

        FieldHelper.testSelectFieldValue(NB_INTERMEDIATED_DS, SelectInputField.class,
                group, "NO", 9, null, true, false, 0);
    }

    @Test
    void buildIntermediationFieldsShouldBlockWhenSellingMeetingTypeMissing() {
        var group = group(INTERMEDIATION_GROUP);
        var policy = policy();
        policy.setSellingMeetingType(null);
        var overallCaseRisk = overallCaseRisk();

        builderService.buildField(
                webFormWithIntermediate(),
                screenDescription(),
                group,
                policy,
                transaction(),
                overallCaseRisk
        );

        FieldHelper.testSelectFieldValue(NB_INTERMEDIATED_DS, SelectInputField.class,
                group, null, 9, null, true, false, 0);

        assertFalse(overallCaseRisk.get("BLOCKED").isEmpty());
    }

    private WebForm webFormWithIntermediate() {
        var companyName = TextWebFormField.builder()
                .fieldId("COMPANY_NAME")
                .value("My Partner")
                .build();

        var country = TextWebFormField.builder()
                .fieldId("COUNTRY")
                .value("LU")
                .build();

        var brokerIdentifier = TextWebFormField.builder()
                .fieldId("BROKER_IDENTIFIER")
                .value("KROKER1212")
                .build();

        var address = WebFormGroup.builder()
                .groupId("ADDRESS")
                .textFields(List.of(country))
                .groups(new ArrayList<>())
                .build();

        var intermediate = WebFormGroup.builder()
                .groupId("INTERMEDIATE")
                .textFields(List.of(companyName, brokerIdentifier))
                .groups(List.of(address))
                .build();

        var distributors = WebFormGroup.builder()
                .groupId("DISTRIBUTORS")
                .groups(List.of(intermediate))
                .build();

        var kyc = WebFormGroup.builder()
                .groupId("KYC")
                .groups(List.of(distributors))
                .build();

        return WebForm.builder()
                .groups(List.of(kyc))
                .build();
    }
}
