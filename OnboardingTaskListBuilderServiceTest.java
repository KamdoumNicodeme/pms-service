@Service
@RequiredArgsConstructor
public class OnboardingPricingApprovalFieldsBuilderService extends OnboardingChecklistFieldsBuilderSupport implements FieldBuilderService {

    private final ReferenceDataRepositoryService referenceDataRepositoryService;

    @Override
    public void buildField(final WebForm webForm, final ScreenDescription screenDescription, final Group group, final Policy policy,
            final BusinessTransaction transaction, final Map<String,List<String>> overallCaseRisk) {

        Field.resetFieldId();

        evaluateNtaMarkupException(group);

        evaluateExplainException(group);

        evaluatePricingApprovalStage(group);

        evaluateRationaleForException(group);

        evaluateAdministrativeFee(group);

        evaluateGac(group);

        evaluatePolicyFee(group);

        evaluateIsFamilyCase(group);

        evaluateFamilyCasePolNbr(group);

        evaluateFamilyCaseTotalAmount(group);

        evaluatePricingApprovalChecked(group);

    }

    private void evaluateNtaMarkupException(Group group) {

        var ntaMarkupException = (SelectInputField) ChecklistUtils.getFieldInGroup(group, NTA_MARKUP_EXCEPTION);
        if (ntaMarkupException == null) {
            ntaMarkupException = SelectInputField.builder().fieldId(NTA_MARKUP_EXCEPTION).build();
            group.getFields().add(ntaMarkupException);
        }
        ntaMarkupException.setIsActive(true);
        ntaMarkupException.incrementOrder();
        ntaMarkupException.setLabel("NTA Markup Exception?");
        ntaMarkupException.setEnabled(false);
        ntaMarkupException.setMandatory(false);
        ntaMarkupException.setDisplayIf(null);
        ntaMarkupException.setLabelBold(false);
        ntaMarkupException.setSourceSystem(null);
    }

    private void evaluateExplainException(Group group) {

        var explainException = (TextAreaField) ChecklistUtils.getFieldInGroup(group, EXPLAIN_EXCEPTION);
        if (explainException == null) {
            explainException = TextAreaField.builder().fieldId(EXPLAIN_EXCEPTION).build();
            group.getFields().add(explainException);
        }
        explainException.setIsActive(true);
        explainException.incrementOrder();
        explainException.setLabel("Explain Exception");
        explainException.setEnabled(false);
        explainException.setMandatory(false);
        explainException.setDisplayIf(null);
        explainException.setLabelBold(false);
        explainException.setSourceSystem(null);
    }

    private void evaluatePricingApprovalStage(Group group) {

        var pricingApprovalStage = (TextInputField) ChecklistUtils.getFieldInGroup(group, PRICING_APPROVAL_STAGE);
        if (pricingApprovalStage == null) {
            pricingApprovalStage = TextInputField.builder().fieldId(PRICING_APPROVAL_STAGE).build();
            group.getFields().add(pricingApprovalStage);
        }
        pricingApprovalStage.setIsActive(true);
        pricingApprovalStage.incrementOrder();
        pricingApprovalStage.setLabel("Pricing Approval Stage");
        pricingApprovalStage.setEnabled(false);
        pricingApprovalStage.setMandatory(false);
        pricingApprovalStage.setDisplayIf(null);
        pricingApprovalStage.setLabelBold(false);
        pricingApprovalStage.setSourceSystem(null);
    }

    private void evaluateRationaleForException(Group group) {

        var rationaleForException = (TextInputField) ChecklistUtils.getFieldInGroup(group, RATIONALE_FOR_EXCEPTION);
        if (rationaleForException == null) {
            rationaleForException = TextInputField.builder().fieldId(RATIONALE_FOR_EXCEPTION).build();
            group.getFields().add(rationaleForException);
        }
        rationaleForException.setIsActive(true);
        rationaleForException.incrementOrder();
        rationaleForException.setLabel("Rationale for Exception");
        rationaleForException.setEnabled(false);
        rationaleForException.setMandatory(false);
        rationaleForException.setDisplayIf(null);
        rationaleForException.setLabelBold(false);
        rationaleForException.setSourceSystem(null);
    }

    private void evaluateAdministrativeFee(Group group) {

        var administrativeFee = (TextInputField) ChecklistUtils.getFieldInGroup(group, ADMINISTRATIVE_FEE);
        if (administrativeFee == null) {
            administrativeFee = TextInputField.builder().fieldId(ADMINISTRATIVE_FEE).build();
            group.getFields().add(administrativeFee);
        }
        administrativeFee.setIsActive(true);
        administrativeFee.incrementOrder();
        administrativeFee.setLabel("Administrative Fee");
        administrativeFee.setEnabled(false);
        administrativeFee.setMandatory(false);
        administrativeFee.setDisplayIf(null);
        administrativeFee.setLabelBold(false);
        administrativeFee.setSourceSystem(null);
    }

    private void evaluateGac(Group group) {

        var gac = (TextInputField) ChecklistUtils.getFieldInGroup(group, GAC);
        if (gac == null) {
            gac = TextInputField.builder().fieldId(GAC).build();
            group.getFields().add(gac);
        }
        gac.setIsActive(true);
        gac.incrementOrder();
        gac.setLabel("GAC (#Years)");
        gac.setEnabled(false);
        gac.setMandatory(false);
        gac.setDisplayIf(null);
        gac.setLabelBold(false);
        gac.setSourceSystem(null);
    }

    private void evaluatePolicyFee(Group group) {

        var policyFee = (TextInputField) ChecklistUtils.getFieldInGroup(group, POLICY_FEE);
        if (policyFee == null) {
            policyFee = TextInputField.builder().fieldId(POLICY_FEE).build();
            group.getFields().add(policyFee);
        }
        policyFee.setIsActive(true);
        policyFee.incrementOrder();
        policyFee.setLabel("Policy Fee");
        policyFee.setEnabled(false);
        policyFee.setMandatory(false);
        policyFee.setDisplayIf(null);
        policyFee.setLabelBold(false);
        policyFee.setSourceSystem(null);
    }

    private void evaluateIsFamilyCase(Group group) {

        var isFamilyCase = (SelectInputField) ChecklistUtils.getFieldInGroup(group, IS_FAMILY_CASE);
        if (isFamilyCase == null) {
            isFamilyCase = SelectInputField.builder().fieldId(IS_FAMILY_CASE).build();
            group.getFields().add(isFamilyCase);
        }
        isFamilyCase.setIsActive(true);
        isFamilyCase.incrementOrder();
        isFamilyCase.setLabel("It is a family case ?");
        isFamilyCase.setEnabled(true);
        isFamilyCase.setMandatory(false);
        isFamilyCase.setDisplayIf(null);
        isFamilyCase.setLabelBold(false);
        isFamilyCase.setSourceSystem(null);
        isFamilyCase.setOptions(referenceDataRepositoryService.getReferenceDataOptionsByDomain(YES_NO_DOMAIN));
    }

    private void evaluateFamilyCasePolNbr(Group group) {

        var familyCasePolNbr = (NumberInputField) ChecklistUtils.getFieldInGroup(group, FAMILY_CASE_POL_NBR);
        if (familyCasePolNbr == null) {
            familyCasePolNbr = NumberInputField.builder().fieldId(FAMILY_CASE_POL_NBR).build();
            group.getFields().add(familyCasePolNbr);
        }
        familyCasePolNbr.setIsActive(true);
        familyCasePolNbr.incrementOrder();
        familyCasePolNbr.setLabel("How many policies in the family case");
        familyCasePolNbr.setEnabled(true);
        familyCasePolNbr.setMandatory(false);
        familyCasePolNbr.setDisplayIf(null);
        familyCasePolNbr.setLabelBold(false);
        familyCasePolNbr.setSourceSystem(null);
    }

    private void evaluateFamilyCaseTotalAmount(Group group) {

        var familyCaseTotalAmount = (NumberInputField) ChecklistUtils.getFieldInGroup(group, FAMILY_CASE_TOTAL_AMOUNT);
        if (familyCaseTotalAmount == null) {
            familyCaseTotalAmount = NumberInputField.builder().fieldId(FAMILY_CASE_TOTAL_AMOUNT).build();
            group.getFields().add(familyCaseTotalAmount);
        }
        familyCaseTotalAmount.setIsActive(true);
        familyCaseTotalAmount.incrementOrder();
        familyCaseTotalAmount.setLabel("Total amount invested in the family case");
        familyCaseTotalAmount.setEnabled(true);
        familyCaseTotalAmount.setMandatory(false);
        familyCaseTotalAmount.setDisplayIf(null);
        familyCaseTotalAmount.setLabelBold(false);
        familyCaseTotalAmount.setSourceSystem(null);
    }

    private void evaluatePricingApprovalChecked(Group group) {

        var pricingApprovalChecked = (SelectInputField) ChecklistUtils.getFieldInGroup(group, PRICING_APPROVAL_CHECKED);
        if (pricingApprovalChecked == null) {
            pricingApprovalChecked = SelectInputField.builder().fieldId(PRICING_APPROVAL_CHECKED).build();
            group.getFields().add(pricingApprovalChecked);
        }
        pricingApprovalChecked.setIsActive(true);
        pricingApprovalChecked.incrementOrder();
        pricingApprovalChecked.setLabel(
                "Pricing approval has been checked in CRM tab (Salesforce) and is in line with the charging structure signed by the PH in the Application form, and the premium received");
        pricingApprovalChecked.setEnabled(true);
        pricingApprovalChecked.setMandatory(true);
        pricingApprovalChecked.setDisplayIf(null);
        pricingApprovalChecked.setLabelBold(false);
        pricingApprovalChecked.setSourceSystem(null);
        pricingApprovalChecked.setOptions(referenceDataRepositoryService.getReferenceDataOptionsByDomain(YES_NO_DOMAIN));
    }
}
