private String resolveCustodianBankName(final WebFormGroup fund) {
    return Optional.ofNullable(getGroupInGroup(fund, CUSTODIAN_BANK_DESIGNATION))
            .map(custodian -> getFieldInGroup(custodian, COMPANY_NAME))
            .map(WebformUtils::getValueForWebFormField)
            .filter(StringUtils::isNotBlank)
            .orElse("N/A");
}
