private String buildMrlTrusteeDisplayIf(final ScreenDescription screenDescription) {
    return ChecklistUtils.getFieldsById(screenDescription, THIRD_PARTY_SUB_TYPE_PH)
            .stream()
            .map(Field::getFieldId)
            .map(fieldId -> "#" + fieldId + "# == \"Trust\"")
            .collect(Collectors.joining(" || "));
}
