private String resolveInvestmentStrategyName(final WebFormGroup fund) {
    var regularIma = Optional.ofNullable(getGroupInGroup(fund, INVESTMENT_STRATEGY))
            .map(strategy -> getGroupInGroup(strategy, REGULAR_IMA))
            .orElse(null);

    if (regularIma == null) {
        return "N/A";
    }

    String investmentStrategy = Optional.ofNullable(getFieldInGroup(regularIma, INVESTMENT_STRATEGY))
            .map(WebformUtils::getValueForWebFormField)
            .orElse("");

    if ("MANDATE".equals(investmentStrategy)) {
        return Optional.ofNullable(getGroupInGroup(regularIma, INVESTMENT_MANDATE))
                .map(mandate -> getFieldInGroup(mandate, STRATEGY_NAME))
                .map(WebformUtils::getValueForWebFormField)
                .filter(StringUtils::isNotBlank)
                .orElse("N/A");
    }

    if ("DIFFERENT".equals(investmentStrategy)) {
        return Optional.ofNullable(getFieldInGroup(regularIma, DIFFERENT_STRATEGY_NAME))
                .map(WebformUtils::getValueForWebFormField)
                .filter(StringUtils::isNotBlank)
                .orElse("N/A");
    }

    return "N/A";
}
