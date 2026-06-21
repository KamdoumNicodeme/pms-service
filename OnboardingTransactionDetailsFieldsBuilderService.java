private WebForm createWebForm(boolean banchValue) {
    BooleanWebFormField banchField = BooleanWebFormField.builder()
            .fieldId("BANCH")
            .value(banchValue)
            .build();

    WebFormGroup group = WebFormGroup.builder()
            .groupId("TEST_GROUP")
            .booleanFields(new ArrayList<>(List.of(banchField)))
            .build();

    return WebForm.builder()
            .groups(new ArrayList<>(List.of(group)))
            .build();
}
