public class CommonTransactionDetailsFieldsBuilderService {

    public static void evaluatePremiumWithAssets(final WebForm webForm, final Group group,
            final ReferenceDataRepositoryService referenceDataRepositoryService) {

        var premiumWithAssets = (SelectInputField) ChecklistUtils.getFieldInGroup(group, PREMIUM_WITH_ASSETS);
        if (premiumWithAssets == null) {
            premiumWithAssets = SelectInputField.builder().fieldId(PREMIUM_WITH_ASSETS).build();
            group.getFields().add(premiumWithAssets);
        }
        premiumWithAssets.setIsActive(true);
        premiumWithAssets.incrementOrder();
        premiumWithAssets.setLabel("Premium with assets?");
        var assetTransfer = WebformUtils.getWebFormFieldValueById(webForm, IS_ASSET_TRANSFER_WF);
        premiumWithAssets.setSelectedValue("true".equals(assetTransfer) ? "YES" : "NO");
        premiumWithAssets.setOptions(referenceDataRepositoryService.getReferenceDataOptionsByDomainAndSelectedValue(YES_NO_DOMAIN,
                premiumWithAssets.getSelectedValue()));

    }

    public static void evaluatePremiumWithUnqAssets(final WebForm webForm, final Group group,
            final ReferenceDataRepositoryService referenceDataRepositoryService) {

        var premiumWithUnqAsset = (SelectInputField) ChecklistUtils.getFieldInGroup(group, PREMIUM_WITH_UNQ_ASSETS);
        if (premiumWithUnqAsset == null) {
            premiumWithUnqAsset = SelectInputField.builder().fieldId(PREMIUM_WITH_UNQ_ASSETS).build();
            group.getFields().add(premiumWithUnqAsset);
        }
        premiumWithUnqAsset.setIsActive(true);
        premiumWithUnqAsset.incrementOrder();
        premiumWithUnqAsset.setLabel("Premium with unquoted assets?");
        var unquotedAssetTransfer = WebformUtils.getWebFormFieldValueById(webForm, HAVE_UNQUOTED_PRODUCT_WF);
        premiumWithUnqAsset.setSelectedValue("true".equals(unquotedAssetTransfer) ? "YES" : "NO");
        premiumWithUnqAsset.setOptions(referenceDataRepositoryService.getReferenceDataOptionsByDomainAndSelectedValue(YES_NO_DOMAIN,
                premiumWithUnqAsset.getSelectedValue()));

    }

    public static void evaluateInvestedInIlf(final Group group, final ReferenceDataRepositoryService referenceDataRepositoryService) {

        var investedInIlf = (SelectInputField) ChecklistUtils.getFieldInGroup(group, INVESTED_IN_ILF);
        if (investedInIlf == null) {
            investedInIlf = SelectInputField.builder().fieldId(INVESTED_IN_ILF).build();
            group.getFields().add(investedInIlf);
        }
        investedInIlf.setIsActive(true);
        investedInIlf.incrementOrder();
        investedInIlf.setLabel("Will it be reinvested in an ILF?");
        investedInIlf.setEnabled(true);
        investedInIlf.setOptions(referenceDataRepositoryService.getReferenceDataOptionsByDomain(YES_NO_DOMAIN));
        investedInIlf.setMandatory(true);
    }

    public static void evaluatePaymentThirdParty(final Group group, final BusinessTransaction transaction,
            final Map<String,List<String>> overallCaseRisk, final ReferenceDataRepositoryService referenceDataRepositoryService) {

        var paymentThirdParty = (SelectInputField) ChecklistUtils.getFieldInGroup(group, PAYMENT_THIRD_PARTY);
        if (paymentThirdParty == null) {
            paymentThirdParty = SelectInputField.builder().fieldId(PAYMENT_THIRD_PARTY).build();
            group.getFields().add(paymentThirdParty);
        }
        var paymentToThirdParty = RulesUtils.getRiskFactorData(transaction, INT_RF_016);
        if (paymentToThirdParty.contains("N/A")) {
            overallCaseRisk.get(RulesConstants.BLOCKED).add("3rd Party Payment risk factor cannot be assessed");
            paymentThirdParty.setSelectedValue(null);
        } else {
            paymentThirdParty.setSelectedValue(paymentToThirdParty.contains("Policy holder") ? NO : YES);
        }
        paymentThirdParty.setIsActive(true);
        paymentThirdParty.incrementOrder();
        paymentThirdParty.setLabel("Payment from a third party payer");
        paymentThirdParty.setEnabled(false);
        paymentThirdParty.setOptions(referenceDataRepositoryService.getReferenceDataOptionsByDomainAndSelectedValue(YES_NO_DOMAIN,
                paymentThirdParty.getSelectedValue()));
        paymentThirdParty.setMandatory(true);
        paymentThirdParty.setSourceSystem("From Class");

    }

    public static void evaluateRationalForInvestment(final WebForm webForm, final Group group) {

        var rationalForInvestment = (TextAreaField) ChecklistUtils.getFieldInGroup(group, RATIONALE_FOR_INVESTMENT);
        if (rationalForInvestment == null) {
            rationalForInvestment = TextAreaField.builder().fieldId(RATIONALE_FOR_INVESTMENT).build();
            group.getFields().add(rationalForInvestment);
        }
        rationalForInvestment.setIsActive(true);
        rationalForInvestment.incrementOrder();
        rationalForInvestment.setLabel("Rationale for investment");
        var kycIntroPurposeOfInvestment = WebformUtils.getWebFormFieldValueById(webForm, KYC_INTRO_PURPOSE_OF_INVESTMENT_WF);
        rationalForInvestment.setSelectedValue(kycIntroPurposeOfInvestment);
    }

    public static List<MoneyInPayment> evaluateNumberOfOriginatingAccounts(final Group group, final BusinessTransaction transaction) {

        var numberOfOriginatingAccounts = (SelectInputField) ChecklistUtils.getFieldInGroup(group, NUMBER_OF_ORIGINATING_ACCOUNTS);
        if (numberOfOriginatingAccounts == null) {
            numberOfOriginatingAccounts = SelectInputField.builder().fieldId(NUMBER_OF_ORIGINATING_ACCOUNTS).build();
            group.getFields().add(numberOfOriginatingAccounts);
        }
        numberOfOriginatingAccounts.setIsActive(true);
        numberOfOriginatingAccounts.incrementOrder();
        numberOfOriginatingAccounts.setLabel("Number of originating accounts");
        numberOfOriginatingAccounts.setMandatory(true);

        var elementsCount = 0;
        List<MoneyInPayment> payments = new ArrayList<>();
        List<BusinessTransactionProductComponent> productComponents = transaction.getProductComponentPayments();
        if (productComponents != null) {
            payments = productComponents.stream().map(BusinessTransactionProductComponent::getPayments).filter(Objects::nonNull)
                    .flatMap(Collection::stream).filter(MoneyInPayment.class::isInstance).map(MoneyInPayment.class::cast).toList();
            elementsCount = payments.size();
        }

        List<SelectInputFieldOption> selectInputFieldOptions = new ArrayList<>();
        selectInputFieldOptions.add(new SelectInputFieldOption(String.valueOf(elementsCount), String.valueOf(elementsCount)));
        numberOfOriginatingAccounts.setOptions(selectInputFieldOptions);
        numberOfOriginatingAccounts.setSelectedValue(Integer.toString(elementsCount));
        return payments;
    }

    public static void evaluateHolderOfOriginatingAccounts(final Group group, final PaymentDetails details, int position) {

        var holderOfOriginatingAccounts = (TextInputField) ChecklistUtils.getFieldInGroup(group, HOLDER_OF_ORIGINATING_ACCOUNTS + "_" + position);
        if (holderOfOriginatingAccounts == null) {
            holderOfOriginatingAccounts = TextInputField.builder().fieldId(HOLDER_OF_ORIGINATING_ACCOUNTS + "_" + position).build();
            group.getFields().add(holderOfOriginatingAccounts);
        }
        holderOfOriginatingAccounts.setIsActive(true);
        holderOfOriginatingAccounts.incrementOrder();
        holderOfOriginatingAccounts.setLabel("Account holder ID #" + position);
        holderOfOriginatingAccounts.setMandatory(true);

        holderOfOriginatingAccounts.setSelectedValue(details.getPayerID());
    }

    public static void evaluateCountryOfOriginatingAccounts(final Group group, final PaymentDetails details, int position,
            final ReferenceDataRepositoryService referenceDataRepositoryService) {

        var countryOfOriginatingAccounts =
                (SelectInputField) ChecklistUtils.getFieldInGroup(group, COUNTRY_OF_ORIGINATING_ACCOUNTS + "_" + position);
        if (countryOfOriginatingAccounts == null) {
            countryOfOriginatingAccounts = SelectInputField.builder().fieldId(COUNTRY_OF_ORIGINATING_ACCOUNTS + "_" + position).build();
            group.getFields().add(countryOfOriginatingAccounts);
        }
        countryOfOriginatingAccounts.setIsActive(true);
        countryOfOriginatingAccounts.incrementOrder();
        countryOfOriginatingAccounts.setLabel("Country of the originating bank #" + position);
        countryOfOriginatingAccounts.setMandatory(true);

        if (details.getPayerBankCountry() != null) {
            countryOfOriginatingAccounts.setSelectedValue(details.getPayerBankCountry().getIsoCountryCode());
        }
        countryOfOriginatingAccounts.setOptions(referenceDataRepositoryService.getReferenceDataOptionsByDomainAndSelectedValue(COUNTRY_DOMAIN,
                countryOfOriginatingAccounts.getSelectedValue()));

    }

    public static void evaluateBankOfOriginatingAccounts(final Group group, final PaymentDetails details, int position) {

        var bankOfOriginatingAccounts = (TextInputField) ChecklistUtils.getFieldInGroup(group, BANK_OF_ORIGINATING_ACCOUNTS + "_" + position);
        if (bankOfOriginatingAccounts == null) {
            bankOfOriginatingAccounts = TextInputField.builder().fieldId(BANK_OF_ORIGINATING_ACCOUNTS + "_" + position).build();
            group.getFields().add(bankOfOriginatingAccounts);
        }
        bankOfOriginatingAccounts.setIsActive(true);
        bankOfOriginatingAccounts.incrementOrder();
        bankOfOriginatingAccounts.setLabel("Name of the bank #" + position);
        bankOfOriginatingAccounts.setMandatory(true);

        bankOfOriginatingAccounts.setSelectedValue(details.getPayerBankName());

    }

    public static void evaluateCountryOfOriginatingAccountsRisk(final Group group, final BusinessTransaction transaction, final int elementCount,
            Map<String,List<String>> overallCaseRisk) {

        var countryOfOriginatingAccountsRisk = (TextInputField) ChecklistUtils.getFieldInGroup(group, COUNTRY_OF_ORIGINATING_ACCOUNTS_RISK);
        if (countryOfOriginatingAccountsRisk == null) {
            countryOfOriginatingAccountsRisk = TextInputField.builder().fieldId(COUNTRY_OF_ORIGINATING_ACCOUNTS_RISK).build();
            group.getFields().add(countryOfOriginatingAccountsRisk);
        }
        countryOfOriginatingAccountsRisk.setIsActive(true);
        countryOfOriginatingAccountsRisk.incrementOrder();
        countryOfOriginatingAccountsRisk.setLabel("Riskiest country of originating bank");
        countryOfOriginatingAccountsRisk.setMandatory(true);

        final var payerPayeeRisk = RulesUtils.getRiskFactorData(transaction, INT_RF_012);
        countryOfOriginatingAccountsRisk.setSelectedValue(payerPayeeRisk);
        if (payerPayeeRisk.contains("N/A")) {
            countryOfOriginatingAccountsRisk.setSelectedValue(null);
            overallCaseRisk.get(RulesConstants.BLOCKED).add("Missing payer/payee bank country");
        }

        countryOfOriginatingAccountsRisk.setDisplayIf(String.valueOf(elementCount > 0));
    }
}


@Service
@RequiredArgsConstructor
public class OnboardingTransactionDetailsFieldsBuilderService extends OnboardingChecklistFieldsBuilderSupport implements FieldBuilderService {

    private final ReferenceDataRepositoryService referenceDataRepositoryService;

    @Override
    public void buildField(final WebForm webForm, final ScreenDescription screenDescription, final Group group, final Policy policy,
            final BusinessTransaction transaction, final Map<String,List<String>> overallCaseRisk) {

        Field.resetFieldId();

        evaluatePremiumWithAssets(webForm, group, referenceDataRepositoryService);

        evaluatePremiumWithUnqAssets(webForm, group, referenceDataRepositoryService);

        evaluateIsRopCase(group);

        evaluateInitialPremium(group);

        evaluateInvestedInIlf(group);

        evaluateExistingIlf(group);

        evaluateIlfMnemonic(group);

        evaluateIsDealing(group);

        evaluatePaymentThirdParty(group, transaction, overallCaseRisk, referenceDataRepositoryService);

        evaluatePayerInSanctionList(group, transaction);

        evaluateNegativeFindingPayers(group, screenDescription);

        evaluatePayerCorporateEntity(group, transaction);

        evaluatePayerNotLocated(group, transaction);

        evaluateEvidenceLegalEntity(group, transaction);

        evaluateCloseToEbo(group);

        evaluateBankNotInResidence(group);

        evaluateEconomicJustification(group);

        evaluateEvidenceTaxDeclared(group);

        evaluateRefusedAdditionalInfo(group);

        evaluatePremiumReceivedDifferentThanExpected(group);

        evaluateSameAsDisclosed(group);

        evaluateRationaleForInvestment(group);

        List<MoneyInPayment> payments = evaluateNumberOfOriginatingAccounts(group, transaction);
        for (int i = 1; i <= payments.size(); i++) {
            PaymentDetails details = payments.get(i - 1).getPaymentDetails();
            if (details == null || details.getPayerID() == null || details.getPayerBankName() == null || details.getPayerBankCountry() == null) {
                if (payments.get(i - 1).getExpectedPaymentDetails() != null) {
                    details = payments.get(i - 1).getExpectedPaymentDetails();
                } else {
                    details = new PaymentDetails();
                }
            }
            evaluateOriginatingAccountName(group, details, i);
            evaluateCountryOfOriginatingAccounts(group, details, i, referenceDataRepositoryService);
            evaluateBankOfOriginatingAccounts(group, details, i);
        }

        evaluateCountryOfOriginatingAccountsRisk(group, transaction, payments.size(), overallCaseRisk);

    }

    private void evaluateIsRopCase(Group group) {

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
        isRopCase.setOptions(referenceDataRepositoryService.getReferenceDataOptionsByDomain(YES_NO_DOMAIN));

    }

    private void evaluateInitialPremium(Group group) {

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
        initialPrem.setDisplayIf("#IS_ROP_CASE# == \"YES\"");
        initialPrem.setLabelBold(false);
        initialPrem.setSourceSystem(null);
        initialPrem.setMultiple(null);
        initialPrem.setMaxMultiple(null);

    }

    private void evaluateInvestedInIlf(Group group) {

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
        investedInIlf.setOptions(referenceDataRepositoryService.getReferenceDataOptionsByDomain(YES_NO_DOMAIN));

    }

    private void evaluateExistingIlf(Group group) {

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
        existingIlf.setDisplayIf("#INVESTED_IN_ILF# == \"YES\"");
        existingIlf.setLabelBold(false);
        existingIlf.setSourceSystem(null);
        existingIlf.setMultiple(null);
        existingIlf.setMaxMultiple(null);
        existingIlf.setOptions(referenceDataRepositoryService.getReferenceDataOptionsByDomain(YES_NO_DOMAIN));

    }

    private void evaluateIlfMnemonic(Group group) {

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
        ilfMnemonic.setDisplayIf("#EXISTING_ILF# == \"YES\"");
        ilfMnemonic.setLabelBold(false);
        ilfMnemonic.setSourceSystem(null);
        ilfMnemonic.setMultiple(null);
        ilfMnemonic.setMaxMultiple(null);

    }

    private void evaluateIsDealing(Group group) {

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

        isDealing.setOptions(referenceDataRepositoryService.getReferenceDataOptionsByDomain(YES_NO_DOMAIN));

    }

    private void evaluatePayerInSanctionList(final Group group, final BusinessTransaction transaction) {

        var payerInSanctionList = (SelectInputField) ChecklistUtils.getFieldInGroup(group, PAYER_IN_SANCTION_LIST);
        if (payerInSanctionList == null) {
            payerInSanctionList = SelectInputField.builder().fieldId(PAYER_IN_SANCTION_LIST).build();
            group.getFields().add(payerInSanctionList);
        }
        String paymentToThirdParty = getRiskFactorData(transaction, INT_RF_016);
        String displayIf = !paymentToThirdParty.contains("N/A") && !paymentToThirdParty.contains("Policy holder") ? "true" : "false";

        payerInSanctionList.setIsActive(true);
        payerInSanctionList.incrementOrder();
        payerInSanctionList.setLabel("Is one of the payers designated on a sanctions list?");
        payerInSanctionList.setEnabled(true);
        payerInSanctionList.setMandatory(true);
        payerInSanctionList.setOptions(referenceDataRepositoryService.getReferenceDataOptionsByDomain(YES_NO_DOMAIN));
        payerInSanctionList.setDisplayIf(displayIf);
    }

    private void evaluateNegativeFindingPayers(final Group group, final ScreenDescription screenDescription) {

        var negativeFindingPayers = (SelectInputField) ChecklistUtils.getFieldInGroup(group, NEGATIVE_FINDING_PAYERS);
        if (negativeFindingPayers == null) {
            negativeFindingPayers = SelectInputField.builder().fieldId(NEGATIVE_FINDING_PAYERS).build();
            group.getFields().add(negativeFindingPayers);
        }

        String paymentToThirdParty = ChecklistUtils.getFieldById(screenDescription, PAYMENT_THIRD_PARTY).map(Field::getSelectedValue).orElse("0");
        String displayForcedValue = YES.equals(paymentToThirdParty) ? "true" : "false";

        negativeFindingPayers.setIsActive(true);
        negativeFindingPayers.incrementOrder();
        negativeFindingPayers.setLabel("Negative press finding / Worldcheck match (on any of the payers)?");
        negativeFindingPayers.setEnabled(true);
        negativeFindingPayers.setOptions(referenceDataRepositoryService.getReferenceDataOptionsByDomain(YES_NO_DOMAIN));
        negativeFindingPayers.setMandatory(true);
        negativeFindingPayers.setDisplayIf(displayForcedValue);
    }

    private void evaluatePayerCorporateEntity(Group group, BusinessTransaction transaction) {

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
        payerCorporateEntity.setLabelBold(false);
        payerCorporateEntity.setSourceSystem(null);
        payerCorporateEntity.setMultiple(null);
        payerCorporateEntity.setMaxMultiple(null);

        String forcedDisplayIf = "false";
        String paymentToThirdParty = getRiskFactorData(transaction, INT_RF_016);
        if (!paymentToThirdParty.contains("N/A") && !paymentToThirdParty.contains("Policy holder")) {
            forcedDisplayIf = "true";
        }
        payerCorporateEntity.setDisplayIf(forcedDisplayIf);
        payerCorporateEntity.setOptions(referenceDataRepositoryService.getReferenceDataOptionsByDomain(YES_NO_DOMAIN));

    }

    private void evaluatePayerNotLocated(Group group, BusinessTransaction transaction) {

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
        payerNotLocated.setLabelBold(false);
        payerNotLocated.setSourceSystem(null);
        payerNotLocated.setMultiple(null);
        payerNotLocated.setMaxMultiple(null);
        payerNotLocated.setOptions(referenceDataRepositoryService.getReferenceDataOptionsByDomain(YES_NO_DOMAIN));
        payerNotLocated.setDisplayIf(String.valueOf(RulesUtils.checkPaymentOriginatorPTCORSO(transaction)));

    }

    private void evaluateEvidenceLegalEntity(Group group, BusinessTransaction transaction) {

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
        evidenceLegalEntity.setLabelBold(false);
        evidenceLegalEntity.setSourceSystem(null);
        evidenceLegalEntity.setMultiple(null);
        evidenceLegalEntity.setMaxMultiple(null);

        evidenceLegalEntity.setOptions(referenceDataRepositoryService.getReferenceDataOptionsByDomain(YES_NO_DOMAIN));
        evidenceLegalEntity.setDisplayIf(String.valueOf(RulesUtils.checkPaymentOriginatorPTCORSO(transaction)));

    }

    private void evaluateCloseToEbo(Group group) {

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

        closeToEbo.setOptions(referenceDataRepositoryService.getReferenceDataOptionsByDomain(YES_NO_DOMAIN));

    }

    private void evaluateBankNotInResidence(Group group) {

        var bankNotInResidence = (SelectInputField) ChecklistUtils.getFieldInGroup(group, BANK_NOT_IN_RESIDENCE);
        if (bankNotInResidence == null) {
            bankNotInResidence = SelectInputField.builder().fieldId(BANK_NOT_IN_RESIDENCE).build();
            group.getFields().add(bankNotInResidence);
        }
        bankNotInResidence.setIsActive(true);
        bankNotInResidence.incrementOrder();
        bankNotInResidence.setLabel(
                "Are the funds paid from a bank account located in a country which is not the tax country of residence " + "of the PH(s)/EBO(s)?");
        bankNotInResidence.setEnabled(true);
        bankNotInResidence.setMandatory(true);
        bankNotInResidence.setDisplayIf(null);
        bankNotInResidence.setLabelBold(false);
        bankNotInResidence.setSourceSystem(null);
        bankNotInResidence.setMultiple(null);
        bankNotInResidence.setMaxMultiple(null);

        bankNotInResidence.setOptions(referenceDataRepositoryService.getReferenceDataOptionsByDomain(YES_NO_DOMAIN));

    }

    private void evaluateEconomicJustification(Group group) {

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
        economicJustif.setDisplayIf("#BANK_NOT_IN_RESIDENCE# == \"YES\"");
        economicJustif.setLabelBold(false);
        economicJustif.setSourceSystem(null);
        economicJustif.setMultiple(null);
        economicJustif.setMaxMultiple(null);

        economicJustif.setOptions(referenceDataRepositoryService.getReferenceDataOptionsByDomain(YES_NO_DOMAIN));

    }

    private void evaluateEvidenceTaxDeclared(Group group) {

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
        evidenceTaxDeclared.setDisplayIf("#BANK_NOT_IN_RESIDENCE# == \"YES\" && #ECONOMIC_JUSTIF# == \"NO\"");
        evidenceTaxDeclared.setLabelBold(false);
        evidenceTaxDeclared.setSourceSystem(null);
        evidenceTaxDeclared.setMultiple(null);
        evidenceTaxDeclared.setMaxMultiple(null);

        evidenceTaxDeclared.setOptions(referenceDataRepositoryService.getReferenceDataOptionsByDomain(YES_NO_DOMAIN));

    }

    private void evaluateRefusedAdditionalInfo(Group group) {

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
        refusedAdditionalInfo
                .setDisplayIf("#BANK_NOT_IN_RESIDENCE# == \"YES\" && #ECONOMIC_JUSTIF# == \"NO\" && #EVIDENCE_TAX_DECLARED# == \"NO\"");
        refusedAdditionalInfo.setLabelBold(false);
        refusedAdditionalInfo.setSourceSystem(null);
        refusedAdditionalInfo.setMultiple(null);
        refusedAdditionalInfo.setMaxMultiple(null);

        refusedAdditionalInfo.setOptions(referenceDataRepositoryService.getReferenceDataOptionsByDomain(YES_NO_DOMAIN));

    }

    private void evaluatePremiumReceivedDifferentThanExpected(Group group) {

        var premiumReceivedDifferentExpected = (SelectInputField) ChecklistUtils.getFieldInGroup(group, PREMIUM_RECEIVED_DIFFERENT_EXPECTED);
        if (premiumReceivedDifferentExpected == null) {
            premiumReceivedDifferentExpected = SelectInputField.builder().fieldId(PREMIUM_RECEIVED_DIFFERENT_EXPECTED).build();
            group.getFields().add(premiumReceivedDifferentExpected);
        }
        premiumReceivedDifferentExpected.setIsActive(true);
        premiumReceivedDifferentExpected.incrementOrder();
        premiumReceivedDifferentExpected
                .setLabel("Is the premium received different than expected one (higher amount or different payment details)?");
        premiumReceivedDifferentExpected.setEnabled(true);
        premiumReceivedDifferentExpected.setMandatory(true);
        premiumReceivedDifferentExpected.setDisplayIf(null);
        premiumReceivedDifferentExpected.setLabelBold(false);
        premiumReceivedDifferentExpected.setSourceSystem(null);
        premiumReceivedDifferentExpected.setMultiple(null);
        premiumReceivedDifferentExpected.setMaxMultiple(null);

        premiumReceivedDifferentExpected.setOptions(referenceDataRepositoryService.getReferenceDataOptionsByDomain(YES_NO_DOMAIN));

    }

    private void evaluateSameAsDisclosed(Group group) {

        var sameAsDisclosed = (SelectInputField) ChecklistUtils.getFieldInGroup(group, SAME_AS_DISCLOSED);
        if (sameAsDisclosed == null) {
            sameAsDisclosed = SelectInputField.builder().fieldId(SAME_AS_DISCLOSED).build();
            group.getFields().add(sameAsDisclosed);
        }
        sameAsDisclosed.setIsActive(true);
        sameAsDisclosed.incrementOrder();
        sameAsDisclosed
                .setLabel("Is the origin of the funds or additional funds and/or the payment details the same as disclosed in the KYC form?");
        sameAsDisclosed.setEnabled(true);
        sameAsDisclosed.setMandatory(true);
        sameAsDisclosed.setDisplayIf("#PREMIUM_RECEIVED_DIFFERENT_EXPECTED# == \"YES\\");
        sameAsDisclosed.setLabelBold(false);
        sameAsDisclosed.setSourceSystem(null);
        sameAsDisclosed.setMultiple(null);
        sameAsDisclosed.setMaxMultiple(null);
        sameAsDisclosed.setOptions(referenceDataRepositoryService.getReferenceDataOptionsByDomain(YES_NO_DOMAIN));

    }

    private void evaluateRationaleForInvestment(Group group) {

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

    }

    private void evaluateOriginatingAccountName(final Group group, final PaymentDetails details, int position) {

        var holderOfOriginatingAccounts =
                (TextInputField) ChecklistUtils.getFieldInGroup(group, NAME_OF_ORIGINATING_ACCOUNT_HOLDER + "_" + position);
        if (holderOfOriginatingAccounts == null) {
            holderOfOriginatingAccounts = TextInputField.builder().fieldId(NAME_OF_ORIGINATING_ACCOUNT_HOLDER + "_" + position).build();
            group.getFields().add(holderOfOriginatingAccounts);
        }
        holderOfOriginatingAccounts.setIsActive(true);
        holderOfOriginatingAccounts.incrementOrder();
        holderOfOriginatingAccounts.setLabel("Account holder ID #" + position);
        holderOfOriginatingAccounts.setMandatory(true);

        holderOfOriginatingAccounts.setSelectedValue(details.getPayerID());
    }

}
