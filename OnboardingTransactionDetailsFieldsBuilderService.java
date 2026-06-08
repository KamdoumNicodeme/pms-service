package com.lombardinternational.casemanagement.service.decision.domain.service.checklist.onboarding.field;

import com.lombardinternational.casemanagement.service.decision.domain.model.rules.Field;
import com.lombardinternational.casemanagement.service.decision.domain.model.rules.Group;
import com.lombardinternational.casemanagement.service.decision.domain.model.rules.NumberInputField;
import com.lombardinternational.casemanagement.service.decision.domain.model.rules.ScreenDescription;
import com.lombardinternational.casemanagement.service.decision.domain.model.rules.SelectInputFieldOption;
import com.lombardinternational.casemanagement.service.decision.domain.model.policy.Policy;
import com.lombardinternational.casemanagement.service.decision.domain.model.transaction.BusinessTransaction;
import com.lombardinternational.casemanagement.service.decision.domain.model.rules.SelectInputField;
import com.lombardinternational.casemanagement.service.decision.domain.model.rules.TextAreaField;
import com.lombardinternational.casemanagement.service.decision.domain.model.rules.TextInputField;
import com.lombardinternational.casemanagement.service.decision.domain.model.webform.WebForm;
import com.lombardinternational.casemanagement.service.decision.domain.service.checklist.FieldBuilderService;
import com.lombardinternational.casemanagement.service.decision.domain.spi.repository.ReferenceDataRepositoryService;
import com.lombardinternational.casemanagement.service.decision.domain.utils.ChecklistUtils;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.lombardinternational.casemanagement.service.decision.domain.utils.constants.OnboardingChecklistIds.*;
import static com.lombardinternational.casemanagement.service.decision.domain.utils.constants.ReferenceConstants.COUNTRY_DOMAIN;
import static com.lombardinternational.casemanagement.service.decision.domain.utils.constants.ReferenceConstants.YES_NO_DOMAIN;

@Service
public class OnboardingTransactionDetailsFieldsBuilderService extends OnboardingChecklistFieldsBuilderSupport implements FieldBuilderService {
    private final ReferenceDataRepositoryService referenceDataRepositoryService;

    public OnboardingTransactionDetailsFieldsBuilderService(ReferenceDataRepositoryService referenceDataRepositoryService) {
        this.referenceDataRepositoryService = referenceDataRepositoryService;
    }

    private static final String SOURCE_CLASS = "From CLASS";

    private static final String SOURCE_CONNECT = "From Connect";

    private static final String YES = "YES";

    @Override
    public void buildField(final WebForm webForm, final ScreenDescription screenDescription, final Group group, final Policy policy, final BusinessTransaction transaction, final Map<String,List<String>> overallCaseRisk) {
        Field.resetFieldId();

        evaluatePremiumWithAssets(group, webForm);

        evaluatePremiumWithUnquotedAssets(group, webForm);

        evaluateIsRopCase(group, webForm);

        evaluateInitialPremium(group, webForm);

        evaluateInvestedInIlf(group, webForm);

        evaluateExistingIlf(group, webForm);

        evaluateIlfMnemonic(group, webForm);

        evaluateIsDealing(group, webForm);

        evaluatePaymentThirdParty(group, webForm);

        evaluatePayerInSanctionList(group, webForm);

        evaluateNegativeFindingPayers(group, webForm);

        evaluatePayerCorporateEntity(group, webForm);

        evaluatePayerNotLocated(group, webForm);

        evaluateEvidenceLegalEntity(group, webForm);

        evaluateCloseToEbo(group, webForm);

        evaluateBankNotInResidence(group, webForm);

        evaluateEconomicJustification(group, webForm);

        evaluateEvidenceTaxDeclared(group, webForm);

        evaluateRefusedAdditionalInfo(group, webForm);

        evaluatePremiumReceivedDifferentThanExpected(group, webForm);

        evaluateSameAsDisclosed(group, webForm);

        evaluateRationaleForInvestment(group, webForm);

        evaluateOriginatingAccounts(group, webForm);

    }

    private void evaluatePremiumWithAssets(Group group, WebForm webForm) {
        var premiumWithAssets = (SelectInputField) ChecklistUtils.getFieldInGroup(group, PREMIUM_WITH_ASSETS);
        if (premiumWithAssets == null) {
            premiumWithAssets = SelectInputField.builder().fieldId(PREMIUM_WITH_ASSETS).build();
            group.getFields().add(premiumWithAssets);
        }
        premiumWithAssets.setIsActive(true);
        premiumWithAssets.incrementOrder();
        premiumWithAssets.setLabel("Premium with assets?");
        premiumWithAssets.setEnabled(true);
        premiumWithAssets.setMandatory(false);
        premiumWithAssets.setDisplayIf(null);
        premiumWithAssets.setLabelBold(false);
        premiumWithAssets.setSourceSystem(null);
        premiumWithAssets.setMultiple(null);
        premiumWithAssets.setMaxMultiple(null);
        premiumWithAssets.setSelectedValue(selectedValue(premiumWithAssets, webForm));

        premiumWithAssets.setOptions(yesNoOptions());

    }

    private void evaluatePremiumWithUnquotedAssets(Group group, WebForm webForm) {
        var premiumWithUnqAssets = (SelectInputField) ChecklistUtils.getFieldInGroup(group, PREMIUM_WITH_UNQ_ASSETS);
        if (premiumWithUnqAssets == null) {
            premiumWithUnqAssets = SelectInputField.builder().fieldId(PREMIUM_WITH_UNQ_ASSETS).build();
            group.getFields().add(premiumWithUnqAssets);
        }
        premiumWithUnqAssets.setIsActive(true);
        premiumWithUnqAssets.incrementOrder();
        premiumWithUnqAssets.setLabel("Premium with unquoted assets?");
        premiumWithUnqAssets.setEnabled(false);
        premiumWithUnqAssets.setMandatory(false);
        premiumWithUnqAssets.setDisplayIf(null);
        premiumWithUnqAssets.setLabelBold(false);
        premiumWithUnqAssets.setSourceSystem(SOURCE_CONNECT);
        premiumWithUnqAssets.setMultiple(null);
        premiumWithUnqAssets.setMaxMultiple(null);
        premiumWithUnqAssets.setSelectedValue(selectedValue(premiumWithUnqAssets, webForm));

        premiumWithUnqAssets.setOptions(referenceOptions(YES_NO_DOMAIN, premiumWithUnqAssets.getSelectedValue()));

    }

    private void evaluateIsRopCase(Group group, WebForm webForm) {
        var isRopCase = (SelectInputField) ChecklistUtils.getFieldInGroup(group, IS_ROP_CASE);
        if (isRopCase == null) {
            isRopCase = SelectInputField.builder().fieldId(IS_ROP_CASE).build();
            group.getFields().add(isRopCase);
        }
        isRopCase.setIsActive(true);
        isRopCase.incrementOrder();
        isRopCase.setLabel("ROP Case ?");
        isRopCase.setEnabled(true);
        isRopCase.setMandatory(true);
        isRopCase.setDisplayIf(null);
        isRopCase.setLabelBold(false);
        isRopCase.setSourceSystem(null);
        isRopCase.setMultiple(null);
        isRopCase.setMaxMultiple(null);
        isRopCase.setSelectedValue(selectedValue(isRopCase, webForm));

        isRopCase.setOptions(yesNoOptions());

    }

    private void evaluateInitialPremium(Group group, WebForm webForm) {
        var initialPrem = (NumberInputField) ChecklistUtils.getFieldInGroup(group, INITIAL_PREM);
        if (initialPrem == null) {
            initialPrem = NumberInputField.builder().fieldId(INITIAL_PREM).build();
            group.getFields().add(initialPrem);
        }
        initialPrem.setIsActive(true);
        initialPrem.incrementOrder();
        initialPrem.setLabel("Initial premium (in policy currency)");
        initialPrem.setEnabled(true);
        initialPrem.setMandatory(true);
        initialPrem.setDisplayIf(selectedEquals(IS_ROP_CASE, YES));
        initialPrem.setLabelBold(false);
        initialPrem.setSourceSystem(null);
        initialPrem.setMultiple(null);
        initialPrem.setMaxMultiple(null);
        initialPrem.setSelectedValue(selectedValue(initialPrem, webForm));

    }

    private void evaluateInvestedInIlf(Group group, WebForm webForm) {
        var investedInIlf = (SelectInputField) ChecklistUtils.getFieldInGroup(group, INVESTED_IN_ILF);
        if (investedInIlf == null) {
            investedInIlf = SelectInputField.builder().fieldId(INVESTED_IN_ILF).build();
            group.getFields().add(investedInIlf);
        }
        investedInIlf.setIsActive(true);
        investedInIlf.incrementOrder();
        investedInIlf.setLabel("Will it be reinvested in an ILF?");
        investedInIlf.setEnabled(true);
        investedInIlf.setMandatory(true);
        investedInIlf.setDisplayIf(null);
        investedInIlf.setLabelBold(false);
        investedInIlf.setSourceSystem(null);
        investedInIlf.setMultiple(null);
        investedInIlf.setMaxMultiple(null);
        investedInIlf.setSelectedValue(selectedValue(investedInIlf, webForm));

        investedInIlf.setOptions(yesNoOptions());

    }

    private void evaluateExistingIlf(Group group, WebForm webForm) {
        var existingIlf = (SelectInputField) ChecklistUtils.getFieldInGroup(group, EXISTING_ILF);
        if (existingIlf == null) {
            existingIlf = SelectInputField.builder().fieldId(EXISTING_ILF).build();
            group.getFields().add(existingIlf);
        }
        existingIlf.setIsActive(true);
        existingIlf.incrementOrder();
        existingIlf.setLabel("Is it an existing ILF?");
        existingIlf.setEnabled(true);
        existingIlf.setMandatory(true);
        existingIlf.setDisplayIf(selectedEquals(INVESTED_IN_ILF, YES));
        existingIlf.setLabelBold(false);
        existingIlf.setSourceSystem(null);
        existingIlf.setMultiple(null);
        existingIlf.setMaxMultiple(null);
        existingIlf.setSelectedValue(selectedValue(existingIlf, webForm));

        existingIlf.setOptions(yesNoOptions());

    }

    private void evaluateIlfMnemonic(Group group, WebForm webForm) {
        var ilfMnemonic = (TextInputField) ChecklistUtils.getFieldInGroup(group, ILF_MNEMONIC);
        if (ilfMnemonic == null) {
            ilfMnemonic = TextInputField.builder().fieldId(ILF_MNEMONIC).build();
            group.getFields().add(ilfMnemonic);
        }
        ilfMnemonic.setIsActive(true);
        ilfMnemonic.incrementOrder();
        ilfMnemonic.setLabel("ILF Mnemonic");
        ilfMnemonic.setEnabled(true);
        ilfMnemonic.setMandatory(false);
        ilfMnemonic.setDisplayIf(selectedEquals(EXISTING_ILF, YES));
        ilfMnemonic.setLabelBold(false);
        ilfMnemonic.setSourceSystem(null);
        ilfMnemonic.setMultiple(null);
        ilfMnemonic.setMaxMultiple(null);
        ilfMnemonic.setSelectedValue(selectedValue(ilfMnemonic, webForm));

    }

    private void evaluateIsDealing(Group group, WebForm webForm) {
        var isDealing = (SelectInputField) ChecklistUtils.getFieldInGroup(group, IS_DEALING);
        if (isDealing == null) {
            isDealing = SelectInputField.builder().fieldId(IS_DEALING).build();
            group.getFields().add(isDealing);
        }
        isDealing.setIsActive(true);
        isDealing.incrementOrder();
        isDealing.setLabel("Is dealing requested");
        isDealing.setEnabled(true);
        isDealing.setMandatory(true);
        isDealing.setDisplayIf(null);
        isDealing.setLabelBold(false);
        isDealing.setSourceSystem(null);
        isDealing.setMultiple(null);
        isDealing.setMaxMultiple(null);
        isDealing.setSelectedValue(selectedValue(isDealing, webForm));

        isDealing.setOptions(yesNoOptions());

    }

    private void evaluatePaymentThirdParty(Group group, WebForm webForm) {
        var paymentThirdParty = (SelectInputField) ChecklistUtils.getFieldInGroup(group, PAYMENT_THIRD_PARTY);
        if (paymentThirdParty == null) {
            paymentThirdParty = SelectInputField.builder().fieldId(PAYMENT_THIRD_PARTY).build();
            group.getFields().add(paymentThirdParty);
        }
        paymentThirdParty.setIsActive(true);
        paymentThirdParty.incrementOrder();
        paymentThirdParty.setLabel("Payment from a third party payer");
        paymentThirdParty.setEnabled(false);
        paymentThirdParty.setMandatory(true);
        paymentThirdParty.setDisplayIf(null);
        paymentThirdParty.setLabelBold(true);
        paymentThirdParty.setSourceSystem(SOURCE_CLASS);
        paymentThirdParty.setMultiple(null);
        paymentThirdParty.setMaxMultiple(null);
        paymentThirdParty.setSelectedValue(selectedValue(paymentThirdParty, webForm));

        paymentThirdParty.setOptions(referenceOptions(YES_NO_DOMAIN, paymentThirdParty.getSelectedValue()));

    }

    private void evaluatePayerInSanctionList(Group group, WebForm webForm) {
        var payerInSanctionList = (SelectInputField) ChecklistUtils.getFieldInGroup(group, PAYER_IN_SANCTION_LIST);
        if (payerInSanctionList == null) {
            payerInSanctionList = SelectInputField.builder().fieldId(PAYER_IN_SANCTION_LIST).build();
            group.getFields().add(payerInSanctionList);
        }
        payerInSanctionList.setIsActive(true);
        payerInSanctionList.incrementOrder();
        payerInSanctionList.setLabel("Is one of the payers designated on a sanctions list?");
        payerInSanctionList.setEnabled(true);
        payerInSanctionList.setMandatory(true);
        payerInSanctionList.setDisplayIf(thirdPartyPayerDisplayIf());
        payerInSanctionList.setLabelBold(false);
        payerInSanctionList.setSourceSystem(null);
        payerInSanctionList.setMultiple(null);
        payerInSanctionList.setMaxMultiple(null);
        payerInSanctionList.setSelectedValue(selectedValue(payerInSanctionList, webForm));

        payerInSanctionList.setOptions(yesNoOptions());

    }

    private void evaluateNegativeFindingPayers(Group group, WebForm webForm) {
        var negativeFindingPayers = (SelectInputField) ChecklistUtils.getFieldInGroup(group, NEGATIVE_FINDING_PAYERS);
        if (negativeFindingPayers == null) {
            negativeFindingPayers = SelectInputField.builder().fieldId(NEGATIVE_FINDING_PAYERS).build();
            group.getFields().add(negativeFindingPayers);
        }
        negativeFindingPayers.setIsActive(true);
        negativeFindingPayers.incrementOrder();
        negativeFindingPayers.setLabel("Negative press finding / Worldcheck match (on any of the payers)?");
        negativeFindingPayers.setEnabled(true);
        negativeFindingPayers.setMandatory(true);
        negativeFindingPayers.setDisplayIf(thirdPartyPayerDisplayIf());
        negativeFindingPayers.setLabelBold(false);
        negativeFindingPayers.setSourceSystem(null);
        negativeFindingPayers.setMultiple(null);
        negativeFindingPayers.setMaxMultiple(null);
        negativeFindingPayers.setSelectedValue(selectedValue(negativeFindingPayers, webForm));

        negativeFindingPayers.setOptions(yesNoOptions());

    }

    private void evaluatePayerCorporateEntity(Group group, WebForm webForm) {
        var payerCorporateEntity = (SelectInputField) ChecklistUtils.getFieldInGroup(group, PAYER_CORPORATE_ENTITY);
        if (payerCorporateEntity == null) {
            payerCorporateEntity = SelectInputField.builder().fieldId(PAYER_CORPORATE_ENTITY).build();
            group.getFields().add(payerCorporateEntity);
        }
        payerCorporateEntity.setIsActive(true);
        payerCorporateEntity.incrementOrder();
        payerCorporateEntity.setLabel("Is the payer a Corporate entity where the PH/EBO is the sole shareholder?");
        payerCorporateEntity.setEnabled(true);
        payerCorporateEntity.setMandatory(true);
        payerCorporateEntity.setDisplayIf(thirdPartyPayerDisplayIf());
        payerCorporateEntity.setLabelBold(false);
        payerCorporateEntity.setSourceSystem(null);
        payerCorporateEntity.setMultiple(null);
        payerCorporateEntity.setMaxMultiple(null);
        payerCorporateEntity.setSelectedValue(selectedValue(payerCorporateEntity, webForm));

        payerCorporateEntity.setOptions(yesNoOptions());

    }

    private void evaluatePayerNotLocated(Group group, WebForm webForm) {
        var payerNotLocated = (SelectInputField) ChecklistUtils.getFieldInGroup(group, PAYER_NOT_LOCATED);
        if (payerNotLocated == null) {
            payerNotLocated = SelectInputField.builder().fieldId(PAYER_NOT_LOCATED).build();
            group.getFields().add(payerNotLocated);
        }
        payerNotLocated.setIsActive(true);
        payerNotLocated.incrementOrder();
        payerNotLocated.setLabel("Is the 3rd party payer a legal entity located in a country which is not the tax country of residence "
                        + "or place of regular economic or professional activities/interests of the PH/EBO?");
        payerNotLocated.setEnabled(true);
        payerNotLocated.setMandatory(true);
        payerNotLocated.setDisplayIf(thirdPartyPayerDisplayIf());
        payerNotLocated.setLabelBold(false);
        payerNotLocated.setSourceSystem(null);
        payerNotLocated.setMultiple(null);
        payerNotLocated.setMaxMultiple(null);
        payerNotLocated.setSelectedValue(selectedValue(payerNotLocated, webForm));

        payerNotLocated.setOptions(yesNoOptions());

    }

    private void evaluateEvidenceLegalEntity(Group group, WebForm webForm) {
        var evidenceLegalEntity = (SelectInputField) ChecklistUtils.getFieldInGroup(group, EVIDENCE_LEGAL_ENTITY);
        if (evidenceLegalEntity == null) {
            evidenceLegalEntity = SelectInputField.builder().fieldId(EVIDENCE_LEGAL_ENTITY).build();
            group.getFields().add(evidenceLegalEntity);
        }
        evidenceLegalEntity.setIsActive(true);
        evidenceLegalEntity.incrementOrder();
        evidenceLegalEntity.setLabel("Did you collect supporting evidence that the legal entity is known by the tax authorities of the country "
                        + "of residence of the PH/EBO or could the legal entity  demonstrate that its establishment complies "
                        + "with the legal provisions of the country of residence of the PH/EBO?");
        evidenceLegalEntity.setEnabled(true);
        evidenceLegalEntity.setMandatory(true);
        evidenceLegalEntity.setDisplayIf(selectedEquals(PAYER_NOT_LOCATED, YES));
        evidenceLegalEntity.setLabelBold(false);
        evidenceLegalEntity.setSourceSystem(null);
        evidenceLegalEntity.setMultiple(null);
        evidenceLegalEntity.setMaxMultiple(null);
        evidenceLegalEntity.setSelectedValue(selectedValue(evidenceLegalEntity, webForm));

        evidenceLegalEntity.setOptions(yesNoOptions());

    }

    private void evaluateCloseToEbo(Group group, WebForm webForm) {
        var closeToEbo = (SelectInputField) ChecklistUtils.getFieldInGroup(group, CLOSE_TO_EBO);
        if (closeToEbo == null) {
            closeToEbo = SelectInputField.builder().fieldId(CLOSE_TO_EBO).build();
            group.getFields().add(closeToEbo);
        }
        closeToEbo.setIsActive(true);
        closeToEbo.incrementOrder();
        closeToEbo.setLabel("In case of any additional documentation collected to corroborate the tax conformity of the funds "
                        + "(e.g. annual income tax return, the regularisation documentation or memo from the tax lawyer "
                        + "of the PH/EBO), is this  supporting documentation issued by a person who is close to the PH/EBO "
                        + "and leaving room for doubt due to the potential conflict of interest?");
        closeToEbo.setEnabled(true);
        closeToEbo.setMandatory(true);
        closeToEbo.setDisplayIf(null);
        closeToEbo.setLabelBold(false);
        closeToEbo.setSourceSystem(null);
        closeToEbo.setMultiple(null);
        closeToEbo.setMaxMultiple(null);
        closeToEbo.setSelectedValue(selectedValue(closeToEbo, webForm));

        closeToEbo.setOptions(yesNoOptions());

    }

    private void evaluateBankNotInResidence(Group group, WebForm webForm) {
        var bankNotInResidence = (SelectInputField) ChecklistUtils.getFieldInGroup(group, BANK_NOT_IN_RESIDENCE);
        if (bankNotInResidence == null) {
            bankNotInResidence = SelectInputField.builder().fieldId(BANK_NOT_IN_RESIDENCE).build();
            group.getFields().add(bankNotInResidence);
        }
        bankNotInResidence.setIsActive(true);
        bankNotInResidence.incrementOrder();
        bankNotInResidence.setLabel("Are the funds paid from a bank account located in a country which is not the tax country of residence "
                        + "of the PH(s)/EBO(s)?");
        bankNotInResidence.setEnabled(true);
        bankNotInResidence.setMandatory(true);
        bankNotInResidence.setDisplayIf(null);
        bankNotInResidence.setLabelBold(false);
        bankNotInResidence.setSourceSystem(null);
        bankNotInResidence.setMultiple(null);
        bankNotInResidence.setMaxMultiple(null);
        bankNotInResidence.setSelectedValue(selectedValue(bankNotInResidence, webForm));

        bankNotInResidence.setOptions(yesNoOptions());

    }

    private void evaluateEconomicJustification(Group group, WebForm webForm) {
        var economicJustif = (SelectInputField) ChecklistUtils.getFieldInGroup(group, ECONOMIC_JUSTIF);
        if (economicJustif == null) {
            economicJustif = SelectInputField.builder().fieldId(ECONOMIC_JUSTIF).build();
            group.getFields().add(economicJustif);
        }
        economicJustif.setIsActive(true);
        economicJustif.incrementOrder();
        economicJustif.setLabel("Is there an obvious economic justification (e.g. the PH/EBO lived in that jurisdiction, worked and/or "
                        + "works in that jurisdiction, funds were generated in that jurisdiction)?");
        economicJustif.setEnabled(true);
        economicJustif.setMandatory(true);
        economicJustif.setDisplayIf(selectedEquals(BANK_NOT_IN_RESIDENCE, YES));
        economicJustif.setLabelBold(false);
        economicJustif.setSourceSystem(null);
        economicJustif.setMultiple(null);
        economicJustif.setMaxMultiple(null);
        economicJustif.setSelectedValue(selectedValue(economicJustif, webForm));

        economicJustif.setOptions(yesNoOptions());

    }

    private void evaluateEvidenceTaxDeclared(Group group, WebForm webForm) {
        var evidenceTaxDeclared = (SelectInputField) ChecklistUtils.getFieldInGroup(group, EVIDENCE_TAX_DECLARED);
        if (evidenceTaxDeclared == null) {
            evidenceTaxDeclared = SelectInputField.builder().fieldId(EVIDENCE_TAX_DECLARED).build();
            group.getFields().add(evidenceTaxDeclared);
        }
        evidenceTaxDeclared.setIsActive(true);
        evidenceTaxDeclared.incrementOrder();
        evidenceTaxDeclared.setLabel("Did you collect supporting evidence that the bank account is  known by the tax authorities of the country "
                        + "of residence of the PH/EBO or that the funds have been tax declared (e.g. annual income tax , "
                        + "return regularisation documentation)?");
        evidenceTaxDeclared.setEnabled(true);
        evidenceTaxDeclared.setMandatory(true);
        evidenceTaxDeclared.setDisplayIf(selectedEquals(BANK_NOT_IN_RESIDENCE, YES) + " && " + selectedEquals(ECONOMIC_JUSTIF, "NO"));
        evidenceTaxDeclared.setLabelBold(false);
        evidenceTaxDeclared.setSourceSystem(null);
        evidenceTaxDeclared.setMultiple(null);
        evidenceTaxDeclared.setMaxMultiple(null);
        evidenceTaxDeclared.setSelectedValue(selectedValue(evidenceTaxDeclared, webForm));

        evidenceTaxDeclared.setOptions(yesNoOptions());

    }

    private void evaluateRefusedAdditionalInfo(Group group, WebForm webForm) {
        var refusedAdditionalInfo = (SelectInputField) ChecklistUtils.getFieldInGroup(group, REFUSED_ADDITIONAL_INFO);
        if (refusedAdditionalInfo == null) {
            refusedAdditionalInfo = SelectInputField.builder().fieldId(REFUSED_ADDITIONAL_INFO).build();
            group.getFields().add(refusedAdditionalInfo);
        }
        refusedAdditionalInfo.setIsActive(true);
        refusedAdditionalInfo.incrementOrder();
        refusedAdditionalInfo.setLabel("Did the PH/EBO refuse to provide this additional supporting documentation?");
        refusedAdditionalInfo.setEnabled(true);
        refusedAdditionalInfo.setMandatory(true);
        refusedAdditionalInfo.setDisplayIf(selectedEquals(BANK_NOT_IN_RESIDENCE, YES) + " && " + selectedEquals(ECONOMIC_JUSTIF, "NO") + " && "
                        + selectedEquals(EVIDENCE_TAX_DECLARED, "NO"));
        refusedAdditionalInfo.setLabelBold(false);
        refusedAdditionalInfo.setSourceSystem(null);
        refusedAdditionalInfo.setMultiple(null);
        refusedAdditionalInfo.setMaxMultiple(null);
        refusedAdditionalInfo.setSelectedValue(selectedValue(refusedAdditionalInfo, webForm));

        refusedAdditionalInfo.setOptions(yesNoOptions());

    }

    private void evaluatePremiumReceivedDifferentThanExpected(Group group, WebForm webForm) {
        var premiumReceivedDifferentExpected = (SelectInputField) ChecklistUtils.getFieldInGroup(group, PREMIUM_RECEIVED_DIFFERENT_EXPECTED);
        if (premiumReceivedDifferentExpected == null) {
            premiumReceivedDifferentExpected = SelectInputField.builder().fieldId(PREMIUM_RECEIVED_DIFFERENT_EXPECTED).build();
            group.getFields().add(premiumReceivedDifferentExpected);
        }
        premiumReceivedDifferentExpected.setIsActive(true);
        premiumReceivedDifferentExpected.incrementOrder();
        premiumReceivedDifferentExpected.setLabel("Is the premium received different than expected one (higher amount or different payment details)?");
        premiumReceivedDifferentExpected.setEnabled(true);
        premiumReceivedDifferentExpected.setMandatory(true);
        premiumReceivedDifferentExpected.setDisplayIf(null);
        premiumReceivedDifferentExpected.setLabelBold(false);
        premiumReceivedDifferentExpected.setSourceSystem(null);
        premiumReceivedDifferentExpected.setMultiple(null);
        premiumReceivedDifferentExpected.setMaxMultiple(null);
        premiumReceivedDifferentExpected.setSelectedValue(selectedValue(premiumReceivedDifferentExpected, webForm));

        premiumReceivedDifferentExpected.setOptions(yesNoOptions());

    }

    private void evaluateSameAsDisclosed(Group group, WebForm webForm) {
        var sameAsDisclosed = (SelectInputField) ChecklistUtils.getFieldInGroup(group, SAME_AS_DISCLOSED);
        if (sameAsDisclosed == null) {
            sameAsDisclosed = SelectInputField.builder().fieldId(SAME_AS_DISCLOSED).build();
            group.getFields().add(sameAsDisclosed);
        }
        sameAsDisclosed.setIsActive(true);
        sameAsDisclosed.incrementOrder();
        sameAsDisclosed.setLabel("Is the origin of the funds or additional funds and/or the payment details the same as disclosed in the KYC form?");
        sameAsDisclosed.setEnabled(true);
        sameAsDisclosed.setMandatory(true);
        sameAsDisclosed.setDisplayIf(selectedEquals(PREMIUM_RECEIVED_DIFFERENT_EXPECTED, YES));
        sameAsDisclosed.setLabelBold(false);
        sameAsDisclosed.setSourceSystem(null);
        sameAsDisclosed.setMultiple(null);
        sameAsDisclosed.setMaxMultiple(null);
        sameAsDisclosed.setSelectedValue(selectedValue(sameAsDisclosed, webForm));

        sameAsDisclosed.setOptions(yesNoOptions());

    }

    private void evaluateRationaleForInvestment(Group group, WebForm webForm) {
        var rationaleForInvestment = (TextAreaField) ChecklistUtils.getFieldInGroup(group, RATIONALE_FOR_INVESTMENT);
        if (rationaleForInvestment == null) {
            rationaleForInvestment = TextAreaField.builder().fieldId(RATIONALE_FOR_INVESTMENT).build();
            group.getFields().add(rationaleForInvestment);
        }
        rationaleForInvestment.setIsActive(true);
        rationaleForInvestment.incrementOrder();
        rationaleForInvestment.setLabel("Rationale for investment");
        rationaleForInvestment.setEnabled(true);
        rationaleForInvestment.setMandatory(false);
        rationaleForInvestment.setDisplayIf(null);
        rationaleForInvestment.setLabelBold(false);
        rationaleForInvestment.setSourceSystem(null);
        rationaleForInvestment.setMultiple(null);
        rationaleForInvestment.setMaxMultiple(null);
        rationaleForInvestment.setSelectedValue(selectedValue(rationaleForInvestment, webForm));

    }

    private void evaluateOriginatingAccounts(Group group, WebForm webForm) {
        var accountCount = originatingAccountCount(group, webForm);

        evaluateNumberOfOriginatingAccounts(group, webForm, accountCount);

        if (accountCount <= 0) {
            evaluateOriginatingAccountTemplateFields(group, webForm);

        } else {
            IntStream.rangeClosed(1, accountCount)
                    .forEach(position -> evaluateOriginatingAccountFields(group, webForm, position));

        }

        evaluateOriginatingBankCountryRisk(group, webForm);

    }

    private void evaluateNumberOfOriginatingAccounts(Group group, WebForm webForm, int accountCount) {
        var numberOfOriginatingAccounts = (SelectInputField) ChecklistUtils.getFieldInGroup(group, NUMBER_OF_ORIGINATING_ACCOUNTS);
        if (numberOfOriginatingAccounts == null) {
            numberOfOriginatingAccounts = SelectInputField.builder().fieldId(NUMBER_OF_ORIGINATING_ACCOUNTS).build();
            group.getFields().add(numberOfOriginatingAccounts);
        }
        numberOfOriginatingAccounts.setIsActive(true);
        numberOfOriginatingAccounts.incrementOrder();
        numberOfOriginatingAccounts.setLabel("Number of originating accounts");
        numberOfOriginatingAccounts.setEnabled(false);
        numberOfOriginatingAccounts.setMandatory(true);
        numberOfOriginatingAccounts.setDisplayIf(null);
        numberOfOriginatingAccounts.setLabelBold(false);
        numberOfOriginatingAccounts.setSourceSystem(null);
        numberOfOriginatingAccounts.setMultiple(null);
        numberOfOriginatingAccounts.setMaxMultiple(null);
        numberOfOriginatingAccounts.setSelectedValue(selectedValue(numberOfOriginatingAccounts, webForm));

        if ((numberOfOriginatingAccounts.getSelectedValue() == null || numberOfOriginatingAccounts.getSelectedValue().isBlank()) && accountCount > 0) {
            numberOfOriginatingAccounts.setSelectedValue(Integer.toString(accountCount));

        }

        numberOfOriginatingAccounts.setOptions(selectedOnly(numberOfOriginatingAccounts.getSelectedValue()));

    }

    private void evaluateOriginatingAccountTemplateFields(Group group, WebForm webForm) {
        var holder = (TextInputField) ChecklistUtils.getFieldInGroup(group, NAME_OF_ORIGINATING_ACCOUNT_HOLDER);
        if (holder == null) {
            holder = TextInputField.builder().fieldId(NAME_OF_ORIGINATING_ACCOUNT_HOLDER).build();
            group.getFields().add(holder);
        }
        holder.setIsActive(true);
        holder.incrementOrder();
        holder.setLabel("Account holder ID");
        holder.setEnabled(false);
        holder.setMandatory(true);
        holder.setDisplayIf(originatingAccountTemplateDisplayIf());
        holder.setLabelBold(false);
        holder.setSourceSystem(null);
        holder.setMultiple(null);
        holder.setMaxMultiple(null);
        holder.setSelectedValue(selectedValue(holder, webForm));

        holder.setMultiple(true);

        var country = (SelectInputField) ChecklistUtils.getFieldInGroup(group, COUNTRY_OF_ORIGINATING_ACCOUNT);
        if (country == null) {
            country = SelectInputField.builder().fieldId(COUNTRY_OF_ORIGINATING_ACCOUNT).build();
            group.getFields().add(country);
        }
        country.setIsActive(true);
        country.incrementOrder();
        country.setLabel("Country of the originating bank");
        country.setEnabled(false);
        country.setMandatory(true);
        country.setDisplayIf(originatingAccountTemplateDisplayIf());
        country.setLabelBold(false);
        country.setSourceSystem(null);
        country.setMultiple(null);
        country.setMaxMultiple(null);
        country.setSelectedValue(selectedValue(country, webForm));

        country.setMultiple(true);

        country.setOptions(referenceOptions(COUNTRY_DOMAIN, country.getSelectedValue()));

        var bankName = (TextInputField) ChecklistUtils.getFieldInGroup(group, BANK_NAME_OF_ORIGINATING_ACCOUNT);
        if (bankName == null) {
            bankName = TextInputField.builder().fieldId(BANK_NAME_OF_ORIGINATING_ACCOUNT).build();
            group.getFields().add(bankName);
        }
        bankName.setIsActive(true);
        bankName.incrementOrder();
        bankName.setLabel("Name of the bank");
        bankName.setEnabled(false);
        bankName.setMandatory(true);
        bankName.setDisplayIf(originatingAccountTemplateDisplayIf());
        bankName.setLabelBold(false);
        bankName.setSourceSystem(null);
        bankName.setMultiple(null);
        bankName.setMaxMultiple(null);
        bankName.setSelectedValue(selectedValue(bankName, webForm));

        bankName.setMultiple(true);

    }

    private void evaluateOriginatingAccountFields(Group group, WebForm webForm, int position) {
        var holder = (TextInputField) ChecklistUtils.getFieldInGroup(group, suffixed(NAME_OF_ORIGINATING_ACCOUNT_HOLDER, position));
        if (holder == null) {
            holder = TextInputField.builder().fieldId(suffixed(NAME_OF_ORIGINATING_ACCOUNT_HOLDER, position)).build();
            group.getFields().add(holder);
        }
        holder.setIsActive(true);
        holder.incrementOrder();
        holder.setLabel("Account holder ID #" + position);
        holder.setEnabled(false);
        holder.setMandatory(true);
        holder.setDisplayIf(null);
        holder.setLabelBold(false);
        holder.setSourceSystem(null);
        holder.setMultiple(null);
        holder.setMaxMultiple(null);
        holder.setSelectedValue(selectedValue(holder, webForm));

        var country = (SelectInputField) ChecklistUtils.getFieldInGroup(group, suffixed(COUNTRY_OF_ORIGINATING_ACCOUNT, position));
        if (country == null) {
            country = SelectInputField.builder().fieldId(suffixed(COUNTRY_OF_ORIGINATING_ACCOUNT, position)).build();
            group.getFields().add(country);
        }
        country.setIsActive(true);
        country.incrementOrder();
        country.setLabel("Country of the originating bank #" + position);
        country.setEnabled(false);
        country.setMandatory(true);
        country.setDisplayIf(null);
        country.setLabelBold(false);
        country.setSourceSystem(null);
        country.setMultiple(null);
        country.setMaxMultiple(null);
        country.setSelectedValue(selectedValue(country, webForm));

        country.setOptions(referenceOptions(COUNTRY_DOMAIN, country.getSelectedValue()));

        var bankName = (TextInputField) ChecklistUtils.getFieldInGroup(group, suffixed(BANK_NAME_OF_ORIGINATING_ACCOUNT, position));
        if (bankName == null) {
            bankName = TextInputField.builder().fieldId(suffixed(BANK_NAME_OF_ORIGINATING_ACCOUNT, position)).build();
            group.getFields().add(bankName);
        }
        bankName.setIsActive(true);
        bankName.incrementOrder();
        bankName.setLabel("Name of the bank #" + position);
        bankName.setEnabled(false);
        bankName.setMandatory(true);
        bankName.setDisplayIf(null);
        bankName.setLabelBold(false);
        bankName.setSourceSystem(null);
        bankName.setMultiple(null);
        bankName.setMaxMultiple(null);
        bankName.setSelectedValue(selectedValue(bankName, webForm));

    }

    private void evaluateOriginatingBankCountryRisk(Group group, WebForm webForm) {
        var originatingBankCountryRisk = (TextInputField) ChecklistUtils.getFieldInGroup(group, ORIGINATING_BANK_COUNTRY_RISK);
        if (originatingBankCountryRisk == null) {
            originatingBankCountryRisk = TextInputField.builder().fieldId(ORIGINATING_BANK_COUNTRY_RISK).build();
            group.getFields().add(originatingBankCountryRisk);
        }
        originatingBankCountryRisk.setIsActive(true);
        originatingBankCountryRisk.incrementOrder();
        originatingBankCountryRisk.setLabel("Riskiest country of originating bank");
        originatingBankCountryRisk.setEnabled(false);
        originatingBankCountryRisk.setMandatory(true);
        originatingBankCountryRisk.setDisplayIf("#" + NUMBER_OF_ORIGINATING_ACCOUNTS + "# >= 1");
        originatingBankCountryRisk.setLabelBold(false);
        originatingBankCountryRisk.setSourceSystem(null);
        originatingBankCountryRisk.setMultiple(null);
        originatingBankCountryRisk.setMaxMultiple(null);
        originatingBankCountryRisk.setSelectedValue(selectedValue(originatingBankCountryRisk, webForm));

    }

    private List<SelectInputFieldOption> yesNoOptions() {
        return referenceDataRepositoryService.getReferenceDataOptionsByDomain(YES_NO_DOMAIN);

    }

    private List<SelectInputFieldOption> referenceOptions(String domain, String selectedValue) {
        var options = referenceDataRepositoryService.getReferenceDataOptionsByDomainAndSelectedValue(domain, selectedValue);

        if (options.isEmpty() && selectedValue != null && !selectedValue.isBlank()) {
            return selectedOnly(selectedValue);

        }

        return options;

    }

    private List<SelectInputFieldOption> selectedOnly(String selectedValue) {
        if (selectedValue == null || selectedValue.isBlank()) {
            return List.of();

        }

        return List.of(new SelectInputFieldOption(selectedValue, selectedValue));

    }

    private String selectedValue(Field field, WebForm webForm) {
        return selectedValueFromWebForm(field.getFieldId(), webForm)
                .orElse(field.getSelectedValue());

    }

    private int originatingAccountCount(Group group, WebForm webForm) {
        var explicitCount = parsePositiveInt(selectedValueFromGroup(group, NUMBER_OF_ORIGINATING_ACCOUNTS)
                .or(() -> selectedValueFromWebForm(NUMBER_OF_ORIGINATING_ACCOUNTS, webForm))
                .orElse(null)).orElse(0);

        var countFromGroup = maxOriginatingAccountSuffix(groupFieldIds(group));

        var countFromWebForm = maxOriginatingAccountSuffix(webFormFieldIds(webForm));

        return Stream.of(explicitCount, countFromGroup, countFromWebForm).mapToInt(Integer::intValue).max().orElse(0);

    }

    private Optional<String> selectedValueFromGroup(Group group, String fieldId) {
        return Optional.ofNullable(ChecklistUtils.getFieldInGroup(group, fieldId))
                .map(Field::getSelectedValue)
                .filter(value -> value != null && !value.isBlank());

    }

    private Stream<String> groupFieldIds(Group group) {
        return Optional.ofNullable(group)
                .map(Group::getFields)
                .stream()
                .flatMap(Collection::stream)
                .filter(Objects::nonNull)
                .map(Field::getFieldId)
                .filter(Objects::nonNull);

    }

    private int maxOriginatingAccountSuffix(Stream<String> fieldIds) {
        return fieldIds.mapToInt(this::originatingAccountPosition).max().orElse(0);

    }

    private int originatingAccountPosition(String fieldId) {
        if (fieldId == null) {
            return 0;

        }

        return Stream.of(NAME_OF_ORIGINATING_ACCOUNT_HOLDER, COUNTRY_OF_ORIGINATING_ACCOUNT, BANK_NAME_OF_ORIGINATING_ACCOUNT)
                .map(prefix -> positionAfterPrefix(fieldId, prefix))
                .flatMapToInt(OptionalInt::stream)
                .max()
                .orElse(0);

    }

    private OptionalInt positionAfterPrefix(String fieldId, String prefix) {
        var marker = prefix + "_";

        if (!fieldId.startsWith(marker)) {
            return OptionalInt.empty();

        }

        return parsePositiveInt(fieldId.substring(marker.length()));

    }

    private OptionalInt parsePositiveInt(String value) {
        if (value == null || value.isBlank()) {
            return OptionalInt.empty();

        }

        try {
            var parsed = Integer.parseInt(value.trim());

            return parsed > 0 ? OptionalInt.of(parsed) : OptionalInt.empty();

        } catch (NumberFormatException ignored) {
            return OptionalInt.empty();

        }

    }

    private String selectedEquals(String fieldId, String expectedValue) {
        return "#" + fieldId + "# == \"" + expectedValue + "\"";

    }

    private String thirdPartyPayerDisplayIf() {
        return selectedEquals(PAYMENT_THIRD_PARTY, YES);

    }

    private String originatingAccountTemplateDisplayIf() {
        return "#" + NUMBER_OF_ORIGINATING_ACCOUNTS + "# >= $";

    }

    private String suffixed(String fieldId, int position) {
        return fieldId + "_" + position;

    }
}
