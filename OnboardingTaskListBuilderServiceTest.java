@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class OnboardingIntermediationFieldsBuilderServiceTest extends OnboardingFieldsBuilderTestSupport {

    @InjectMocks
    private OnboardingIntermediationFieldsBuilderService builderService;

    @Test
    void buildIntermediationFieldsOK() {

        var group = group(INTERMEDIATION_GROUP);

        builderService.buildField(webFormWithIntermediate(), screenDescription(), group, policy(), transaction(), overallCaseRisk());

        assertEquals(16, group.getFields().size());
        FieldHelper.testSelectFieldValue(APP_FORM_SIGNER, SelectInputField.class, group, null, 1, null, true, true, 3);
        FieldHelper.testFieldValue(PARTNER_CODE, TextInputField.class, group, "KROKER1212", 2, null, true, false);
        FieldHelper.testFieldValue(PARTNER_NAME, TextInputField.class, group, "My Partner", 3, null, true, false);
        FieldHelper.testSelectFieldValue(PARTNER_TYPE, SelectInputField.class, group, null, 4, null, true, false, 4);
        FieldHelper.testSelectFieldValue(DS_CONSENT_RECEIVED, SelectInputField.class, group, null, 10,
                "#NB_INTERMEDIATED_DS# == \"YES\" && (#PARTNER_TYPE# == \"AGENT_EMP\" || #PARTNER_TYPE# == \"AGENT_IND\")", true, true, 2);
        FieldHelper.testSelectFieldValue(VULNERABLE_INDICIA_DETECTED, SelectInputField.class, group, null, 16, null, true, true, 2);
        var signer = (SelectInputField) ChecklistUtils.getFieldInGroup(group, APP_FORM_SIGNER);
        assertEquals("BROKER", signer.getOptions().getFirst().getKey());
        verify(referenceDataRepositoryService).getReferenceDataOptionsByDomainAndSelectedValue(PARTNER_TYPE_DOMAIN, null);
        verify(referenceDataRepositoryService, org.mockito.Mockito.atLeastOnce()).getReferenceDataOptionsByDomain(YES_NO_DOMAIN);
    }

    private WebForm webFormWithIntermediate() {

        var companyName = TextWebFormField.builder().fieldId("COMPANY_NAME").value("My Partner").build();

        var country = TextWebFormField.builder().fieldId("COUNTRY").value("LU").build();

        var address = WebFormGroup.builder().groupId("ADDRESS").textFields(List.of(country)).groups(new ArrayList<>()).build();

        var brokerIdentifier = TextWebFormField.builder().fieldId("BROKER_IDENTIFIER").value("KROKER1212").build();
        var intermediate =
                WebFormGroup.builder().groupId("INTERMEDIATE").textFields(List.of(companyName, brokerIdentifier)).groups(List.of(address)).build();

        var distributors = WebFormGroup.builder().groupId("DISTRIBUTORS").groups(List.of(intermediate)).build();

        var kyc = WebFormGroup.builder().groupId("KYC").groups(List.of(distributors)).build();

        return WebForm.builder().groups(List.of(kyc)).build();
    }
}

abstract class OnboardingFieldsBuilderTestSupport {

    @Mock
    protected ReferenceDataRepositoryService referenceDataRepositoryService;

    @BeforeEach
    void initReferenceDataRepositoryService() {

        lenient().when(referenceDataRepositoryService.getReferenceDataOptionsByDomain(anyString()))
                .thenAnswer(invocation -> referenceOptions(invocation.getArgument(0)));
        lenient()
                .when(referenceDataRepositoryService.getReferenceDataOptionsByDomainAndSelectedValue(anyString(),
                        org.mockito.ArgumentMatchers.any()))
                .thenAnswer(invocation -> selectedReferenceOptions(invocation.getArgument(0), invocation.getArgument(1)));
    }

    protected Policy policy() {

        return PolicyBuilderServiceHelper.createPolicy();
    }

    protected WebForm webForm() {

        return new WebForm();
    }

    protected WebForm webFormWithTextFields(TextWebFormField... fields) {

        return WebForm.builder().groups(List.of(WebFormGroup.builder().textFields(List.of(fields)).build())).build();
    }

    protected BusinessTransaction transaction() {

        return TransactionBuilderServiceHelper.createTransaction();
    }

    protected ScreenDescription screenDescription() {

        return new ScreenDescription();
    }

    protected Map<String,List<String>> overallCaseRisk() {

        return Map.of("BLOCKED", new ArrayList<>());
    }

    protected Group group(String groupId) {

        return Group.builder().groupId(groupId).build();
    }

    private List<SelectInputFieldOption> selectedReferenceOptions(String domain, String selectedValue) {

        var options = referenceOptions(domain);
        if (selectedValue == null) {
            return options;
        }
        return options.stream().filter(option -> option.getKey().equalsIgnoreCase(selectedValue)).toList();
    }

    protected List<SelectInputFieldOption> referenceOptions(String domain) {

        return switch (domain) {
        case YES_NO_DOMAIN -> ReferenceServiceHelper.getYesNoOptions();
        case YES_NO_NA_NEW_BUSINESS_VALUES_DOMAIN -> ReferenceServiceHelper.getYesNoNaOptions();
        case COUNTRY_DOMAIN -> ReferenceServiceHelper.getCountryOptions();
        case INDUSTRY_SECTOR_DOMAIN -> ReferenceServiceHelper.getIndustrySectorOptions();
        case PROFESSION_DOMAIN -> ReferenceServiceHelper.getProfessionOptions();
        case THIRD_PARTY_TYPE_DOMAIN -> ReferenceServiceHelper.getThirdPartyTypeOptions();
        case PARTNER_TYPE_DOMAIN -> ReferenceServiceHelper.getPartnerTypeOptions();
        case COMPLIANCE_LEVEL_DOMAIN -> ReferenceServiceHelper.getComplianceLevelOptions();
        case SIGNATURE_DOMAIN -> ReferenceServiceHelper.getSignatureDomain();
        case PROVIDER_DOMAIN -> ReferenceServiceHelper.getProviderDomain();
        case TRANSACTION_FEEDBACK_DOMAIN -> ReferenceServiceHelper.getTransactionFeedbackOptions();
        case UNDER_WRITINGS_OPTIONS -> ReferenceServiceHelper.getUnderWritingsOptions();
        case FORM_SIGNER_DOMAIN -> ReferenceServiceHelper.getPartnerDomain();
        default -> List.of();
        };
    }
}

ublic class OnboardingIntermediationFieldsBuilderService extends OnboardingChecklistFieldsBuilderSupport implements FieldBuilderService {

    private final ReferenceDataRepositoryService referenceDataRepositoryService;

    @Override
    public void buildField(final WebForm webForm, final ScreenDescription screenDescription, final Group group, final Policy policy,
            final BusinessTransaction transaction, final Map<String,List<String>> overallCaseRisk) {

        Field.resetFieldId();

        evaluateAppFormSigner(group);

        evaluatePartnerCode(group, webForm);

        evaluatePartnerName(group, webForm);

        evaluatePartnerType(group, policy);

        evaluatePhRepresentative(group, policy, overallCaseRisk);

        evaluateReferrerAuto(group);

        evaluatePartnerStatus(group);

        evaluateAnalysisOfNeeds(group);

        evaluateNbIntermediatedDs(group, policy, overallCaseRisk);

        evaluateDsConsentReceived(group);

        evaluateVideoReceived(group);

        evaluateClientIdentifiable(group);

        evaluateHashCheckPerformed(group);

        evaluatePassportCheckPerformed(group);

        evaluateRequestDsProcedure(group, policy, screenDescription);

        evaluateVulnerableIndiciaDetected(group);

    }

    private void evaluateAppFormSigner(Group group) {

        var appFormSigner = (SelectInputField) ChecklistUtils.getFieldInGroup(group, APP_FORM_SIGNER);
        if (appFormSigner == null) {
            appFormSigner = SelectInputField.builder().fieldId(APP_FORM_SIGNER).build();
            group.getFields().add(appFormSigner);
        }
        appFormSigner.setIsActive(true);
        appFormSigner.incrementOrder();
        appFormSigner.setLabel("Application form signer");
        appFormSigner.setEnabled(true);
        appFormSigner.setMandatory(true);
        appFormSigner.setDisplayIf(null);
        appFormSigner.setLabelBold(false);
        appFormSigner.setSourceSystem(null);
        appFormSigner.setOptions(referenceDataRepositoryService.getReferenceDataOptionsByDomain(FORM_SIGNER_DOMAIN));
    }

    private void evaluatePartnerCode(Group group, WebForm webForm) {

        var partnerCode = (TextInputField) ChecklistUtils.getFieldInGroup(group, PARTNER_CODE);
        if (partnerCode == null) {
            partnerCode = TextInputField.builder().fieldId(PARTNER_CODE).build();
            group.getFields().add(partnerCode);
        }
        partnerCode.setIsActive(true);
        partnerCode.incrementOrder();
        partnerCode.setLabel("Introducing partner code");
        partnerCode.setEnabled(false);
        partnerCode.setMandatory(true);
        partnerCode.setDisplayIf(null);
        partnerCode.setLabelBold(false);
        partnerCode.setSourceSystem("From Connect");

        WebFormGroup intermediate = getIntermediate(webForm);
        String forcedValue = (intermediate != null) ? getValueForWebFormField(getFieldInGroup(intermediate, "BROKER_IDENTIFIER")) : "N/A";
        partnerCode.setSelectedValue(forcedValue);
    }

    private void evaluatePartnerName(Group group, WebForm webForm) {

        var partnerName = (TextInputField) ChecklistUtils.getFieldInGroup(group, PARTNER_NAME);
        if (partnerName == null) {
            partnerName = TextInputField.builder().fieldId(PARTNER_NAME).build();
            group.getFields().add(partnerName);
        }
        partnerName.setIsActive(true);
        partnerName.incrementOrder();
        partnerName.setLabel("Introducing partner name");
        partnerName.setEnabled(false);
        partnerName.setMandatory(true);
        partnerName.setDisplayIf(null);
        partnerName.setLabelBold(false);
        partnerName.setSourceSystem("From Connect");
        WebFormGroup intermediate = getIntermediate(webForm);
        String forcedValue = (intermediate != null) ? getValueForWebFormField(getFieldInGroup(intermediate, "COMPANY_NAME")) : "N/A";
        partnerName.setSelectedValue(forcedValue);
    }

    private void evaluatePartnerType(Group group, Policy policy) {

        var partnerType = (SelectInputField) ChecklistUtils.getFieldInGroup(group, PARTNER_TYPE);
        if (partnerType == null) {
            partnerType = SelectInputField.builder().fieldId(PARTNER_TYPE).build();
            group.getFields().add(partnerType);
        }
        partnerType.setIsActive(true);
        partnerType.incrementOrder();
        partnerType.setLabel("Partner type");
        partnerType.setEnabled(false);
        partnerType.setMandatory(true);
        partnerType.setDisplayIf(null);
        partnerType.setLabelBold(false);
        partnerType.setSourceSystem("From CLASS");
        String forcedValue = null;
        if (policy.getBroker() != null && policy.getBroker().getPartnerType() != null) {
            forcedValue = policy.getBroker().getPartnerType();
        }
        partnerType.setSelectedValue(forcedValue);
        partnerType.setOptions(referenceDataRepositoryService.getReferenceDataOptionsByDomainAndSelectedValue(PARTNER_TYPE_DOMAIN,
                partnerType.getSelectedValue()));
    }

    private void evaluatePhRepresentative(final Group group, final Policy policy, final Map<String,List<String>> overallCaseRisk) {

        var phRepresentative = (SelectInputField) ChecklistUtils.getFieldInGroup(group, IS_PH_REPRESENTATIVE);
        if (phRepresentative == null) {
            phRepresentative = SelectInputField.builder().fieldId(IS_PH_REPRESENTATIVE).build();
            group.getFields().add(phRepresentative);
        }
        phRepresentative.setIsActive(true);
        phRepresentative.incrementOrder();
        phRepresentative.setLabel(
                "Is the PH/EBO a representative (Director/ Shareholder/ Sales Person) of the intermediary who intermediated the policy ?");
        phRepresentative.setMandatory(true);
        phRepresentative.setSourceSystem("Calculated based on Overall case risk");
        final var thirdParties = PolicyUtils.getThirdPartiesByRole(policy, Arrays.asList("HLR", "EBO"));
        final var isPhEboRepresentative = thirdParties.stream().map(AbstractPerson::getSourceOfFunds).filter(Objects::nonNull)
                .map(SourceOfFunds::getIsPHEBORepresentativeIntermediary).toList();
        var forcedValue = isPhEboRepresentative.stream().filter(Objects::nonNull).reduce(false, (a, b) -> a || b) ? YES : NO;
        final var hasEmptyRepresentative = isPhEboRepresentative.stream().anyMatch(Objects::isNull);
        if (hasEmptyRepresentative) {
            forcedValue = null;
            overallCaseRisk.get("BLOCKED").add("Please fill in the question Is the PH / EBO a representative … of the intermediary in CLASS");
        }
        phRepresentative.setSelectedValue(forcedValue);
        phRepresentative.setOptions(referenceDataRepositoryService.getReferenceDataOptionsByDomainAndSelectedValue(YES_NO_DOMAIN,
                phRepresentative.getSelectedValue()));
    }

    private void evaluateReferrerAuto(Group group) {

        var referrerAuto = (SelectInputField) ChecklistUtils.getFieldInGroup(group, REFERRER_AUTO);
        if (referrerAuto == null) {
            referrerAuto = SelectInputField.builder().fieldId(REFERRER_AUTO).build();
            group.getFields().add(referrerAuto);
        }
        referrerAuto.setIsActive(true);
        referrerAuto.incrementOrder();
        referrerAuto.setLabel("Referrer fully automated");
        referrerAuto.setEnabled(true);
        referrerAuto.setMandatory(true);
        referrerAuto.setDisplayIf("#PARTNER_TYPE# == \"REFERRER\"");
        referrerAuto.setLabelBold(false);
        referrerAuto.setSourceSystem(null);
        referrerAuto.setOptions(referenceDataRepositoryService.getReferenceDataOptionsByDomain(YES_NO_DOMAIN));
    }

    private void evaluatePartnerStatus(Group group) {

        var partnerStatus = (SelectInputField) ChecklistUtils.getFieldInGroup(group, PARTNER_STATUS);
        if (partnerStatus == null) {
            partnerStatus = SelectInputField.builder().fieldId(PARTNER_STATUS).build();
            group.getFields().add(partnerStatus);
        }
        partnerStatus.setIsActive(true);
        partnerStatus.incrementOrder();
        partnerStatus.setLabel("Is the partner status \"Active\"?");
        partnerStatus.setEnabled(false);
        partnerStatus.setMandatory(false);
        partnerStatus.setDisplayIf(null);
        partnerStatus.setLabelBold(false);
        partnerStatus.setSourceSystem("From Connect");
        partnerStatus.setOptions(referenceDataRepositoryService.getReferenceDataOptionsByDomain(YES_NO_DOMAIN));
    }

    private void evaluateAnalysisOfNeeds(Group group) {

        var analysisOfNeeds = (SelectInputField) ChecklistUtils.getFieldInGroup(group, ANALYSIS_OF_NEEDS);
        if (analysisOfNeeds == null) {
            analysisOfNeeds = SelectInputField.builder().fieldId(ANALYSIS_OF_NEEDS).build();
            group.getFields().add(analysisOfNeeds);
        }
        analysisOfNeeds.setIsActive(true);
        analysisOfNeeds.incrementOrder();
        analysisOfNeeds.setLabel("Analysis of the Demands and needs/ Fact Find received?");
        analysisOfNeeds.setEnabled(true);
        analysisOfNeeds.setMandatory(true);
        analysisOfNeeds.setDisplayIf("#PARTNER_TYPE# == \"AGENT_EMP\" || #PARTNER_TYPE# == \"AGENT_IND\" || #PARTNER_TYPE# == \"AGENT_EXT\"");
        analysisOfNeeds.setLabelBold(false);
        analysisOfNeeds.setSourceSystem(null);
        analysisOfNeeds.setOptions(referenceDataRepositoryService.getReferenceDataOptionsByDomain(YES_NO_DOMAIN));
    }

    private void evaluateNbIntermediatedDs(Group group, Policy policy, Map<String,List<String>> overallCaseRisk) {

        var nbIntermediatedDs = (SelectInputField) ChecklistUtils.getFieldInGroup(group, NB_INTERMEDIATED_DS);
        if (nbIntermediatedDs == null) {
            nbIntermediatedDs = SelectInputField.builder().fieldId(NB_INTERMEDIATED_DS).build();
            group.getFields().add(nbIntermediatedDs);
        }
        nbIntermediatedDs.setIsActive(true);
        nbIntermediatedDs.incrementOrder();
        nbIntermediatedDs.setLabel("Has the New Business been intermediated using the Distance Selling process?");
        nbIntermediatedDs.setEnabled(false);
        nbIntermediatedDs.setMandatory(true);
        nbIntermediatedDs.setDisplayIf(null);
        nbIntermediatedDs.setLabelBold(false);
        nbIntermediatedDs.setSourceSystem("From CLASS");

        String forcedValue = null;

        if ("DISTANCE".equals(policy.getSellingMeetingType())) {
            forcedValue = "YES";
        } else if ("FACE".equals(policy.getSellingMeetingType())) {
            forcedValue = "NO";
        } else {
            overallCaseRisk.get("BLOCKED")
                    .add("Distance Selling incomplete: Missing Has the New Business been intermediated using the Distance Selling process?");
        }
        nbIntermediatedDs.setSelectedValue(forcedValue);

    }

    private void evaluateDsConsentReceived(Group group) {

        var dsConsentReceived = (SelectInputField) ChecklistUtils.getFieldInGroup(group, DS_CONSENT_RECEIVED);
        if (dsConsentReceived == null) {
            dsConsentReceived = SelectInputField.builder().fieldId(DS_CONSENT_RECEIVED).build();
            group.getFields().add(dsConsentReceived);
        }
        dsConsentReceived.setIsActive(true);
        dsConsentReceived.incrementOrder();
        dsConsentReceived.setLabel("Has the client consent for Distance Selling been received?");
        dsConsentReceived.setEnabled(true);
        dsConsentReceived.setMandatory(true);
        dsConsentReceived.setDisplayIf("#NB_INTERMEDIATED_DS# == \"YES\" && (#PARTNER_TYPE# == \"AGENT_EMP\" || #PARTNER_TYPE# == \"AGENT_IND\")");
        dsConsentReceived.setLabelBold(false);
        dsConsentReceived.setSourceSystem(null);
        dsConsentReceived.setOptions(referenceDataRepositoryService.getReferenceDataOptionsByDomain(YES_NO_DOMAIN));
    }

    private void evaluateVideoReceived(Group group) {

        var videoReceived = (SelectInputField) ChecklistUtils.getFieldInGroup(group, VIDEO_RECEIVED);
        if (videoReceived == null) {
            videoReceived = SelectInputField.builder().fieldId(VIDEO_RECEIVED).build();
            group.getFields().add(videoReceived);
        }
        videoReceived.setIsActive(true);
        videoReceived.incrementOrder();
        videoReceived.setLabel("Has the video been received?");
        videoReceived.setEnabled(true);
        videoReceived.setMandatory(true);
        videoReceived.setDisplayIf("#NB_INTERMEDIATED_DS# == \"YES\" && (#PARTNER_TYPE# == \"AGENT_EMP\" || #PARTNER_TYPE# == \"AGENT_IND\")");
        videoReceived.setLabelBold(false);
        videoReceived.setSourceSystem(null);
        videoReceived.setOptions(referenceDataRepositoryService.getReferenceDataOptionsByDomain(YES_NO_DOMAIN));
    }

    private void evaluateClientIdentifiable(Group group) {

        var clientIdentifiable = (SelectInputField) ChecklistUtils.getFieldInGroup(group, CLIENT_IDENTIFIABLE);
        if (clientIdentifiable == null) {
            clientIdentifiable = SelectInputField.builder().fieldId(CLIENT_IDENTIFIABLE).build();
            group.getFields().add(clientIdentifiable);
        }
        clientIdentifiable.setIsActive(true);
        clientIdentifiable.incrementOrder();
        clientIdentifiable.setLabel("Client could be identified on the video?");
        clientIdentifiable.setEnabled(true);
        clientIdentifiable.setMandatory(true);
        clientIdentifiable
                .setDisplayIf("#NB_INTERMEDIATED_DS# == \"YES\" && (#PARTNER_TYPE# == \"AGENT_EMP\" || #PARTNER_TYPE# == \"AGENT_IND\")");
        clientIdentifiable.setLabelBold(false);
        clientIdentifiable.setSourceSystem(null);
        clientIdentifiable.setOptions(referenceDataRepositoryService.getReferenceDataOptionsByDomain(YES_NO_DOMAIN));
    }

    private void evaluateHashCheckPerformed(Group group) {

        var hashCheckPerformed = (SelectInputField) ChecklistUtils.getFieldInGroup(group, HASH_CHECK_PERFORMED);
        if (hashCheckPerformed == null) {
            hashCheckPerformed = SelectInputField.builder().fieldId(HASH_CHECK_PERFORMED).build();
            group.getFields().add(hashCheckPerformed);
        }
        hashCheckPerformed.setIsActive(true);
        hashCheckPerformed.incrementOrder();
        hashCheckPerformed.setLabel("Hash tool checks performed");
        hashCheckPerformed.setEnabled(true);
        hashCheckPerformed.setMandatory(true);
        hashCheckPerformed
                .setDisplayIf("#NB_INTERMEDIATED_DS# == \"YES\" && (#PARTNER_TYPE# == \"AGENT_EMP\" || #PARTNER_TYPE# == \"AGENT_IND\")");
        hashCheckPerformed.setLabelBold(false);
        hashCheckPerformed.setSourceSystem(null);
        hashCheckPerformed.setOptions(referenceDataRepositoryService.getReferenceDataOptionsByDomain(YES_NO_DOMAIN));
    }

    private void evaluatePassportCheckPerformed(Group group) {

        var passportCheckPerformed = (SelectInputField) ChecklistUtils.getFieldInGroup(group, PASSPORT_CHECK_PERFORMED);
        if (passportCheckPerformed == null) {
            passportCheckPerformed = SelectInputField.builder().fieldId(PASSPORT_CHECK_PERFORMED).build();
            group.getFields().add(passportCheckPerformed);
        }
        passportCheckPerformed.setIsActive(true);
        passportCheckPerformed.incrementOrder();
        passportCheckPerformed.setLabel("Have the additional checks on passport been performed?");
        passportCheckPerformed.setEnabled(true);
        passportCheckPerformed.setMandatory(true);
        passportCheckPerformed
                .setDisplayIf("#NB_INTERMEDIATED_DS# == \"YES\" && (#PARTNER_TYPE# == \"AGENT_EMP\" || #PARTNER_TYPE# == \"AGENT_IND\")");
        passportCheckPerformed.setLabelBold(false);
        passportCheckPerformed.setSourceSystem(null);
        passportCheckPerformed.setOptions(referenceDataRepositoryService.getReferenceDataOptionsByDomain(YES_NO_DOMAIN));
    }

    private void evaluateRequestDsProcedure(Group group, Policy policy, ScreenDescription sd) {

        var requestDsProcedure = (SelectInputField) ChecklistUtils.getFieldInGroup(group, REQUEST_DS_PROCEDURE);
        if (requestDsProcedure == null) {
            requestDsProcedure = SelectInputField.builder().fieldId(REQUEST_DS_PROCEDURE).build();
            group.getFields().add(requestDsProcedure);
        }
        requestDsProcedure.setIsActive(true);
        requestDsProcedure.incrementOrder();
        requestDsProcedure.setLabel("PCS to request the distance selling procedure from the broker?");
        requestDsProcedure.setEnabled(false);
        requestDsProcedure.setMandatory(false);
        requestDsProcedure.setLabelBold(false);
        requestDsProcedure.setSourceSystem("From CLASS");
        String forcedDisplayIf = "false";
        String forcedValue = "NO";
        String partnerType = getFieldById(sd, "PARTNER_TYPE").map(ChecklistUtils::getFieldValue).orElse("");
        if (policy.getBroker() != null && !"APPROVED".equals(policy.getBroker().getDistanceSellingApprovalStatus())
                && "YES".equals(getFieldById(sd, "NB_INTERMEDIATED_DS").map(ChecklistUtils::getFieldValue).orElse(""))
                && !"AGENT_IND".equals(partnerType) && !"AGENT_EMP".equals(partnerType)) {
            forcedDisplayIf = "true";
            forcedValue = "YES";
        }
        requestDsProcedure.setDisplayIf(forcedDisplayIf);
        requestDsProcedure.setSelectedValue(forcedValue);
        requestDsProcedure.setOptions(referenceDataRepositoryService.getReferenceDataOptionsByDomainAndSelectedValue(YES_NO_DOMAIN,
                requestDsProcedure.getSelectedValue()));
    }

    private void evaluateVulnerableIndiciaDetected(Group group) {

        var vulnerableIndiciaDetected = (SelectInputField) ChecklistUtils.getFieldInGroup(group, VULNERABLE_INDICIA_DETECTED);
        if (vulnerableIndiciaDetected == null) {
            vulnerableIndiciaDetected = SelectInputField.builder().fieldId(VULNERABLE_INDICIA_DETECTED).build();
            group.getFields().add(vulnerableIndiciaDetected);
        }
        vulnerableIndiciaDetected.setIsActive(true);
        vulnerableIndiciaDetected.incrementOrder();
        vulnerableIndiciaDetected.setLabel("Vulnerable client indicia detected");
        vulnerableIndiciaDetected.setEnabled(true);
        vulnerableIndiciaDetected.setMandatory(true);
        vulnerableIndiciaDetected.setDisplayIf(null);
        vulnerableIndiciaDetected.setLabelBold(false);
        vulnerableIndiciaDetected.setSourceSystem(null);
        vulnerableIndiciaDetected.setOptions(referenceDataRepositoryService.getReferenceDataOptionsByDomain(YES_NO_DOMAIN));
    }
}
