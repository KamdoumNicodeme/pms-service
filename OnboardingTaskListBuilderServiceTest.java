@Service
@RequiredArgsConstructor
public class OnboardingThirdPartyFieldsBuilderService extends OnboardingChecklistFieldsBuilderSupport implements FieldBuilderService {

    private final ReferenceDataRepositoryService referenceDataRepositoryService;

    @Override
    public void buildField(final WebForm webForm, final ScreenDescription screenDescription, final Group group, final Policy policy,
            final BusinessTransaction transaction, final Map<String,List<String>> overallCaseRisk) {

        Field.resetFieldId();

        var payments = ChecklistUtils.getMoneyInTransactionPaymentDetails(transaction);
        IntStream.range(1, payments.size() + 1).forEach(i -> {
            var paymentDetails = payments.get(i - 1);
            evaluateThirdPartyName(group, paymentDetails, i);
            evaluateLinkThirdPartyPh(group, paymentDetails, i, referenceDataRepositoryService);
            evaluateReasonThirdPartyPayment(group, i);
            evaluateThirdPartyCountry(group, paymentDetails, i, referenceDataRepositoryService);
        });

        evaluateThirdPartyCountryRisk(group, transaction);
        evaluateAdditionalInfoLink(group);

    }

    private void evaluateAdditionalInfoLink(Group group) {

        var additionalInfoLink = (TextAreaField) ChecklistUtils.getFieldInGroup(group, ADDITIONAL_INFO_LINK);
        if (additionalInfoLink == null) {
            additionalInfoLink = TextAreaField.builder().fieldId(ADDITIONAL_INFO_LINK).build();
            group.getFields().add(additionalInfoLink);
        }
        additionalInfoLink.setIsActive(true);
        additionalInfoLink.incrementOrder();
        additionalInfoLink.setLabel("Additional info on link between 3rd party and PH");
        additionalInfoLink.setEnabled(true);
        additionalInfoLink.setMandatory(false);
        additionalInfoLink.setDisplayIf("#LINK_THIRD_PARTY_PH# == \"other\"");
        additionalInfoLink.setLabelBold(false);
        additionalInfoLink.setSourceSystem(null);
    }

}

public class CommonThirdPartyFieldsBuilderService {

    public static final String SOURCE_SYSTEM = "From Class";

    public static void evaluateThirdPartyName(final Group group, final PaymentDetails paymentDetails, int position) {

        var thirdPartyName = (TextInputField) ChecklistUtils.getFieldInGroup(group, THIRD_PARTY_NAME + "_" + position);
        if (thirdPartyName == null) {
            thirdPartyName = TextInputField.builder().fieldId(THIRD_PARTY_NAME + "_" + position).build();
            group.getFields().add(thirdPartyName);
        }
        thirdPartyName.setIsActive(true);
        thirdPartyName.incrementOrder();
        thirdPartyName.setLabel("3rd party ID #" + position);
        thirdPartyName.setSourceSystem(SOURCE_SYSTEM);
        thirdPartyName.setMandatory(true);
        thirdPartyName.setSelectedValue(paymentDetails != null ? paymentDetails.getPayerID() : null);
    }

    public static void evaluateLinkThirdPartyPh(final Group group, PaymentDetails paymentDetails, int position,
            final ReferenceDataRepositoryService referenceDataRepositoryService) {

        var linkThirdPartyPh = (SelectInputField) ChecklistUtils.getFieldInGroup(group, LINK_THIRD_PARTY_PH + "_" + position);
        if (linkThirdPartyPh == null) {
            linkThirdPartyPh = SelectInputField.builder().fieldId(LINK_THIRD_PARTY_PH + "_" + position).build();
            group.getFields().add(linkThirdPartyPh);
        }
        linkThirdPartyPh.setIsActive(true);
        linkThirdPartyPh.incrementOrder();
        linkThirdPartyPh.setLabel("Link between 3rd party and PH #" + position);
        linkThirdPartyPh.setMandatory(true);
        linkThirdPartyPh.setSourceSystem(SOURCE_SYSTEM);
        if (paymentDetails != null && paymentDetails.getOriginator() != null) {
            linkThirdPartyPh.setSelectedValue(paymentDetails.getOriginator().getExternalId());
        } else {
            linkThirdPartyPh.setSelectedValue(null);
        }
        linkThirdPartyPh.setOptions(referenceDataRepositoryService.getReferenceDataOptionsByDomainAndSelectedValue(THIRD_PARTY_TYPE_DOMAIN,
                linkThirdPartyPh.getSelectedValue()));
    }

    public static void evaluateReasonThirdPartyPayment(final Group group, int position) {

        var reasonThirdPartyPayment = (TextInputField) ChecklistUtils.getFieldInGroup(group, REASON_THIRD_PARTY_PAYMENT + "_" + position);
        if (reasonThirdPartyPayment == null) {
            reasonThirdPartyPayment = TextInputField.builder().fieldId(REASON_THIRD_PARTY_PAYMENT + "_" + position).build();
            group.getFields().add(reasonThirdPartyPayment);
        }
        reasonThirdPartyPayment.setIsActive(true);
        reasonThirdPartyPayment.incrementOrder();
        reasonThirdPartyPayment.setLabel("Reason for 3rd party payment #" + position);
        reasonThirdPartyPayment.setEnabled(true);
    }

    public static void evaluateThirdPartyCountry(final Group group, PaymentDetails paymentDetails, int position,
            final ReferenceDataRepositoryService referenceDataRepositoryService) {

        var thirdPartyCountry = (SelectInputField) ChecklistUtils.getFieldInGroup(group, THIRD_PARTY_COUNTRY + "_" + position);
        if (thirdPartyCountry == null) {
            thirdPartyCountry = SelectInputField.builder().fieldId(THIRD_PARTY_COUNTRY + "_" + position).build();
            group.getFields().add(thirdPartyCountry);
        }
        thirdPartyCountry.setIsActive(true);
        thirdPartyCountry.incrementOrder();
        thirdPartyCountry.setLabel("3rd party country #" + position);
        thirdPartyCountry.setMandatory(true);
        thirdPartyCountry.setSourceSystem(SOURCE_SYSTEM);
        if (paymentDetails != null && paymentDetails.getPayerLegalAddressCountry() != null) {
            thirdPartyCountry.setSelectedValue(paymentDetails.getPayerLegalAddressCountry().getIsoCountryCode());
        } else {
            thirdPartyCountry.setSelectedValue(null);
        }
        thirdPartyCountry.setOptions(referenceDataRepositoryService.getReferenceDataOptionsByDomainAndSelectedValue(COUNTRY_DOMAIN,
                thirdPartyCountry.getSelectedValue()));
    }

    public static void evaluateThirdPartyCountryRisk(final Group group, final BusinessTransaction transaction) {

        var thirdPartyCountryRisk = (TextInputField) ChecklistUtils.getFieldInGroup(group, THIRD_PARTY_COUNTRY_RISK);
        if (thirdPartyCountryRisk == null) {
            thirdPartyCountryRisk = TextInputField.builder().fieldId(THIRD_PARTY_COUNTRY_RISK).build();
            group.getFields().add(thirdPartyCountryRisk);
        }
        thirdPartyCountryRisk.setIsActive(true);
        thirdPartyCountryRisk.incrementOrder();
        thirdPartyCountryRisk.setLabel("Highest 3rd party risk");
        thirdPartyCountryRisk.setSourceSystem("Highest 3rd party risk from Class");
        thirdPartyCountryRisk.setSelectedValue(RulesUtils.getRiskFactorLevel(transaction, INT_RF_016));
    }
