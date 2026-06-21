private String resolveEsgMandateStatus(
        WebFormGroup internalDedicatedFund) {

    var regularIma = Optional.ofNullable(
            getGroupInGroup(
                    getGroupInGroup(
                            internalDedicatedFund,
                            INVESTMENT_STRATEGY),
                    REGULAR_IMA))
            .orElse(null);

    if (regularIma == null) {
        return "NO";
    }

    String investmentStrategy =
            getValueForWebFormField(
                    getFieldInGroup(
                            regularIma,
                            INVESTMENT_STRATEGY));

    String promoted =
            getValueForWebFormField(
                    getFieldInGroup(
                            regularIma,
                            IS_STRATEGY_PROMOTED));

    if ("MANDATE".equals(investmentStrategy)) {
        return "YES_PRE_VALIDATED";
    }

    if ("DIFFERENT".equals(investmentStrategy)
            && Boolean.parseBoolean(promoted)) {
        return "YES_TO_BE_REVIEWED";
    }

    return "NO";
}
