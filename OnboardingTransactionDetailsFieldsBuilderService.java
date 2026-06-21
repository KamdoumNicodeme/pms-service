package com.lombardinternational.casemanagement.service.decision.domain.service.checklist.onboarding.field;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.lombardinternational.casemanagement.service.decision.domain.model.policy.AbstractPerson;
import com.lombardinternational.casemanagement.service.decision.domain.model.policy.Amount;
import com.lombardinternational.casemanagement.service.decision.domain.model.policy.EconomicBeneficiary;
import com.lombardinternational.casemanagement.service.decision.domain.model.policy.PhysicalPerson;
import com.lombardinternational.casemanagement.service.decision.domain.model.policy.Policy;
import com.lombardinternational.casemanagement.service.decision.domain.model.policy.SourceOfFunds;
import com.lombardinternational.casemanagement.service.decision.domain.model.policy.SourcesOfWealth;
import com.lombardinternational.casemanagement.service.decision.domain.model.rules.Field;
import com.lombardinternational.casemanagement.service.decision.domain.model.rules.Group;
import com.lombardinternational.casemanagement.service.decision.domain.model.rules.ScreenDescription;
import com.lombardinternational.casemanagement.service.decision.domain.model.rules.SelectInputField;
import com.lombardinternational.casemanagement.service.decision.domain.model.rules.TextAreaField;
import com.lombardinternational.casemanagement.service.decision.domain.model.rules.TextInputField;
import com.lombardinternational.casemanagement.service.decision.domain.model.transaction.BusinessTransaction;
import com.lombardinternational.casemanagement.service.decision.domain.model.webform.BooleanWebFormField;
import com.lombardinternational.casemanagement.service.decision.domain.model.webform.WebForm;
import com.lombardinternational.casemanagement.service.decision.domain.model.webform.WebFormGroup;
import com.lombardinternational.casemanagement.service.decision.domain.service.checklist.FieldBuilderService;
import com.lombardinternational.casemanagement.service.decision.domain.spi.repository.ReferenceDataRepositoryService;
import com.lombardinternational.casemanagement.service.decision.domain.utils.ChecklistUtils;
import com.lombardinternational.casemanagement.service.decision.domain.utils.PolicyUtils;
import com.lombardinternational.casemanagement.service.decision.domain.utils.RulesUtils;
import com.lombardinternational.casemanagement.service.decision.domain.utils.WebformUtils;
import com.lombardinternational.casemanagement.service.decision.domain.utils.constants.ChecklistFieldConstants;

import lombok.RequiredArgsConstructor;

import static com.lombardinternational.casemanagement.service.decision.domain.utils.RulesUtils.*;
import static com.lombardinternational.casemanagement.service.decision.domain.utils.constants.ChecklistFieldConstants.*;
import static com.lombardinternational.casemanagement.service.decision.domain.utils.constants.ReferenceConstants.*;
import static com.lombardinternational.casemanagement.service.decision.domain.utils.constants.RulesConstants.*;

@Service
@RequiredArgsConstructor
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
            IntStream.range(0, policy.getEconomicBeneficiaries().size()).forEach(i -> {
                evaluateTwentyPercentIncome(group, policy.getEconomicBeneficiaries().get(i), i + 1);
                evaluateSourceOfWealth(group, i + 1);
            });
        }

        int countElement = WebformUtils.getKycs(webForm).size();
        IntStream.range(0, countElement).forEach(i -> evaluateMinimumWealth(group, i + 1));

        List<WebFormGroup> groups = new ArrayList<>();
        WebformUtils.getWebFormObjectsById("KYC_QUESTIONS_PRO_DETAILS", webForm, null, null, groups, null);
        int elementsCounts = groups.size();
        IntStream.range(0, elementsCounts).forEach(i -> evaluateWealthAllocationOk(group, groups.get(i), i + 1));

        if (policy.getEconomicBeneficiaries() != null) {
            IntStream.range(0, policy.getEconomicBeneficiaries().size()).forEach(i -> {
                evaluateWealthOriginatingCountry1Risk(group, policy.getEconomicBeneficiaries().get(i), i + 1);
                evaluateWealthOriginatingCountry2Risk(group, policy.getEconomicBeneficiaries().get(i), i + 1);
            });
        }

        IntStream.range(0, elementsCounts).forEach(i -> evaluateWealthAllocation(group, i + 1));

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
        position.setSourceSystem("from CLASS");

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

        if (pepStatusAnswer == null || pepStatusAnswer.contains("N/A") || pepStatusData.toUpperCase().startsWith("MISSING")) {
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
                boolean hasAtLeastOneSowOriginPrem = hasAtLeastOnSowOriginPremium(abstractPerson.get());
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

        var abstractPerson = Optional.ofNullable(policy.getEconomicBeneficiaries()).map(PolicyUtils::getPersons).orElseGet(Collections::emptyList);

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
            (overallCaseRisk.get(BLOCKED)).add("Please fill in the Golden Visa in CLASS");
        } else {
            isPhEboGoldenVisa.setSelectedValue(forcedValue);
            isPhEboGoldenVisa.setOptions(referenceDataRepositoryService.getReferenceDataOptionsByDomainAndSelectedValue(YES_NO_DOMAIN,
                    isPhEboGoldenVisa.getSelectedValue()));
        }

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
            boolean result = hasAtLeastOnSowOriginPremium(abstractPerson.get());

            twentyPercentIncome.setSelectedValue(result ? YES : NO);
        } else {
            twentyPercentIncome.setSelectedValue(NO);
        }
        twentyPercentIncome.setOptions(referenceDataRepositoryService.getReferenceDataOptionsByDomainAndSelectedValue(YES_NO_DOMAIN,
                twentyPercentIncome.getSelectedValue()));
    }

    private void evaluateSourceOfWealth(Group group, final int position) {

        var sourceOfWealth = (TextInputField) ChecklistUtils.getFieldInGroup(group, SOURCE_OF_WEALTH + "_" + position);
        if (sourceOfWealth == null) {
            sourceOfWealth = TextInputField.builder().fieldId(SOURCE_OF_WEALTH + "_" + position).build();
            group.getFields().add(sourceOfWealth);
        }
        sourceOfWealth.setIsActive(true);
        sourceOfWealth.incrementOrder();
        sourceOfWealth.setLabel("Source of wealth description #" + position);
        sourceOfWealth.setEnabled(true);
        sourceOfWealth.setMandatory(false);
        sourceOfWealth.setDisplayIf(null);
        sourceOfWealth.setLabelBold(false);
        sourceOfWealth.setSourceSystem(null);
    }

    private void evaluateMinimumWealth(Group group, int position) {

        var minimumWealth = (SelectInputField) ChecklistUtils.getFieldInGroup(group, MINIMUM_WEALTH + "_" + position);
        if (minimumWealth == null) {
            minimumWealth = SelectInputField.builder().fieldId(MINIMUM_WEALTH + "_" + position).build();
            group.getFields().add(minimumWealth);
        }
        minimumWealth.setIsActive(true);
        minimumWealth.incrementOrder();
        minimumWealth.setLabel("Minimum wealth > 250000€ in transferrable assets (not including property) #" + position);
        minimumWealth.setEnabled(true);
        minimumWealth.setMandatory(true);
        minimumWealth.setDisplayIf(null);
        minimumWealth.setLabelBold(false);
        minimumWealth.setSourceSystem(null);
        minimumWealth.setOptions(referenceDataRepositoryService.getReferenceDataOptionsByDomain(YES_NO_DOMAIN));
    }

    private void evaluateWealthAllocationOk(Group group, WebFormGroup webFormGroup, int position) {

        var wealthAllocationOk = (SelectInputField) ChecklistUtils.getFieldInGroup(group, WEALTH_ALLOCATION_OK + "_" + position);
        if (wealthAllocationOk == null) {
            wealthAllocationOk = SelectInputField.builder().fieldId(WEALTH_ALLOCATION_OK + "_" + position).build();
            group.getFields().add(wealthAllocationOk);
        }
        wealthAllocationOk.setIsActive(true);
        wealthAllocationOk.incrementOrder();
        wealthAllocationOk.setLabel("Wealth allocation in line with source #" + position);
        wealthAllocationOk.setEnabled(true);
        wealthAllocationOk.setMandatory(true);
        wealthAllocationOk.setDisplayIf(null);
        wealthAllocationOk.setLabelBold(false);
        wealthAllocationOk.setSourceSystem(null);
        var allocationInlineWithSource = WebformUtils.getWebFormGroupFieldsById(webFormGroup, "ALLOCATION_IN_LINE_WITH_SOURCE").stream()
                .filter(BooleanWebFormField.class::isInstance).map(BooleanWebFormField.class::cast).findFirst().orElse(null);
        wealthAllocationOk
                .setSelectedValue(allocationInlineWithSource != null && Boolean.TRUE.equals(allocationInlineWithSource.getValue()) ? YES : NO);
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

    private void evaluateWealthAllocation(Group group, int position) {

        var wealthAllocation = (TextInputField) ChecklistUtils.getFieldInGroup(group, WEALTH_ALLOCATION + "_" + position);
        if (wealthAllocation == null) {
            wealthAllocation = TextInputField.builder().fieldId(WEALTH_ALLOCATION + "_" + position).build();
            group.getFields().add(wealthAllocation);
        }
        wealthAllocation.setIsActive(true);
        wealthAllocation.incrementOrder();
        wealthAllocation.setLabel("Wealth allocation #" + position);
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

    private boolean hasAtLeastOnSowOriginPremium(AbstractPerson person) {

        return Optional.ofNullable(person).map(AbstractPerson::getSourceOfFunds).map(sof -> PolicyUtils.getSourceOfWealthByPerson(person))
                .orElseGet(Collections::emptyList).stream()
                .filter(sow -> sow.getDescription() != null && sow.getDescription().matches("inheri_don|divorce|gift"))
                .anyMatch(sow -> Boolean.TRUE.equals(sow.getOriginOfPremium()));

    }
}







    @Test
    void buildDueDiligenceFields_OK() {

        Policy policy = PolicyBuilderServiceHelper.createPolicy();
        Group group = group(DUE_DILIGENCE_GROUP);
        AtomicInteger order = new AtomicInteger(1);
        int expectedNumberOfFields = 32;

        builderService.buildField(webForm(), screenDescription(), group, policy, transaction(), overallCaseRisk());

        assertEquals(expectedNumberOfFields, group.getFields().size());

        FieldHelper.testSelectFieldValue(INDUSTRY + "_1", SelectInputField.class, group, "adult", order.getAndIncrement(), null, true, false, 1);
        FieldHelper.testSelectFieldValue(POSITION + "_1", SelectInputField.class, group, null, order.getAndIncrement(), "false", false, false, 3);
        FieldHelper.testSelectFieldValue(INDUSTRY + "_2", SelectInputField.class, group, "agri", order.getAndIncrement(), null, true, false, 1);
        FieldHelper.testSelectFieldValue(POSITION + "_2", SelectInputField.class, group, "Director", order.getAndIncrement(), null, false, false,
                1);

        FieldHelper.testFieldValueAndIncr(BACKGROUND_DETAILS, TextAreaField.class, group, null, order, null, true, true);
        FieldHelper.testFieldValueAndIncr(RISK_ASSESSMENT, TextAreaField.class, group, null, order, null, true, true);
        FieldHelper.testFieldValueAndIncr(PEP, SelectInputField.class, group, NO, order, null, true, false);
        FieldHelper.testFieldValueAndIncr(IS_PEP_PAYER, SelectInputField.class, group, null, order, "false", true, true);
        FieldHelper.testFieldValueAndIncr(TCC_SIGNED, SelectInputField.class, group, null, order, null, true, true);
        FieldHelper.testFieldValueAndIncr(TCC_SIGNED_REFUSED, SelectInputField.class, group, null, order, "#TCC_SIGNED# == \"NO\"", true, true);
        FieldHelper.testFieldValueAndIncr(INTRODUCING_PARTNER_SIGNED, SelectInputField.class, group, null, order, null, true, true);
        FieldHelper.testFieldValueAndIncr(INFO_PROVIDED_VERIFIED, SelectInputField.class, group, null, order, null, true, true);
        FieldHelper.testFieldValueAndIncr(NEGATIVE_FINDING, SelectInputField.class, group, NO, order, null, true, false);
        FieldHelper.testFieldValueAndIncr(NEGATIVE_FINDING_THIRD_PARTY, TextInputField.class, group, null, order, "false", false, false);
        FieldHelper.testFieldValueAndIncr(AT_LEAST_ONE_SOW_OF_KIND, SelectInputField.class, group, YES, order, "false", true, false);
        FieldHelper.testFieldValueAndIncr(ORIGINATOR_WORLD_CHECK, SelectInputField.class, group, null, order, "true", true, true);
        FieldHelper.testFieldValueAndIncr(IS_ON_SANCTION_LIST, SelectInputField.class, group, null, order, null, true, false);
        FieldHelper.testFieldValueAndIncr(INSIDER, SelectInputField.class, group, NO, order, null, false, false);
        FieldHelper.testFieldValueAndIncr(IS_PH_EBO_GOLDEN_VISA, SelectInputField.class, group, null, order, null, true, false);

         FieldHelper.testFieldValueAndIncr(ANNUAL_INCOME + "_1", TextInputField.class, group, "N/A", order, null, false, false);
         FieldHelper.testFieldValueAndIncr(ANNUAL_INCOME + "_2", TextInputField.class, group, "N/A", order, null, false, false);
         FieldHelper.testFieldValueAndIncr(SOURCE_OF_WEALTH + "_1", TextInputField.class, group, null, order, null, false, true);
         FieldHelper.testFieldValueAndIncr(SOURCE_OF_WEALTH + "_2", TextInputField.class, group, null, order, null, false, true);
         FieldHelper.testFieldValueAndIncr(TWENTY_PERCENT_INCOME + "_1", SelectInputField.class, group, NO, order, null, false, false);
         FieldHelper.testFieldValueAndIncr(TWENTY_PERCENT_INCOME + "_2", SelectInputField.class, group, NO, order, null, false, false);

         FieldHelper.testFieldValueAndIncr(MINIMUM_WEALTH + "_1", SelectInputField.class, group, null, order, null, true, true);
         FieldHelper.testFieldValueAndIncr(WEALTH_ALLOCATION_OK, SelectInputField.class, group, null, order, null, true, true);

        FieldHelper.testFieldValueAndIncr(WEALTH_ORIGINATING_COUNTRY_1_RISK + "_1", TextInputField.class, group, "", order, null, true, false);
        FieldHelper.testFieldValueAndIncr(WEALTH_ORIGINATING_COUNTRY_2_RISK + "_1", TextInputField.class, group, "", order, null, false, false);
        FieldHelper.testFieldValueAndIncr(WEALTH_ORIGINATING_COUNTRY_1_RISK + "_2", TextInputField.class, group, "", order, null, true, false);
        FieldHelper.testFieldValueAndIncr(WEALTH_ORIGINATING_COUNTRY_2_RISK + "_2", TextInputField.class, group, "", order, null, false, false);

        FieldHelper.testFieldValueAndIncr(WEALTH_ALLOCATION, TextInputField.class, group, null, order, null, true, true);
        FieldHelper.testFieldValueAndIncr(TOTAL_WEALTH + "_1", TextInputField.class, group, "N/A", order, null, false, false);
        FieldHelper.testFieldValueAndIncr(TOTAL_WEALTH + "_2", TextInputField.class, group, "N/A", order, null, false, false);
        FieldHelper.testFieldValueAndIncr(KYC_SUPPORTING_DOCUMENTS, SelectInputField.class, group, null, order, null, true, true);

        assertEquals(expectedNumberOfFields, order.get() - 1);
    }
