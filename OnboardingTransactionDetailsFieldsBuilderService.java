private List<WebFormGroup> getInternalDedicatedFunds(final WebForm webForm) {
    List<WebFormGroup> result = new ArrayList<>();

    List<WebFormGroup> investmentFees = new ArrayList<>();
    WebformUtils.getWebFormObjectsById(
            INVESTMENT_FEES,
            webForm,
            null,
            null,
            investmentFees,
            null
    );

    for (WebFormGroup investmentFee : investmentFees) {
        WebFormGroup premiumInvestment =
                WebformUtils.getGroupInGroup(investmentFee, PREMIUM_INVESTMENT);

        if (premiumInvestment == null) {
            continue;
        }

        WebFormGroup internalDedicatedFunds =
                WebformUtils.getGroupInGroup(premiumInvestment, INTERNAL_DEDICATED_FUNDS);

        if (internalDedicatedFunds != null && internalDedicatedFunds.getGroups() != null) {
            result.addAll(internalDedicatedFunds.getGroups());
        }
    }

    return result;
}
