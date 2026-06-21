private String resolveCustodianCountry(final WebFormGroup fund) {
    return Optional.ofNullable(
                    getGroupInGroup(fund, CUSTODIAN_BANK_DESIGNATION))
            .map(custodian -> getGroupInGroup(custodian, ADDRESS))
            .map(address -> getFieldInGroup(address, COUNTRY))
            .map(WebformUtils::getValueForWebFormField)
            .filter(StringUtils::isNotBlank)
            .orElse("N/A");
}
