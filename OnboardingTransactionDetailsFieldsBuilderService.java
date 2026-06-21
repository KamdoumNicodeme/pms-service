private ScreenDescription createFakeScreenDescription(String riskValue) {

    TextInputField riskField = TextInputField.builder()
            .fieldId(RISK_VALUE)
            .selectedValue(riskValue)
            .build();

    Group group = Group.builder()
            .groupId("CASE_RISK")
            .fields(new ArrayList<>(List.of(riskField)))
            .build();

    Tab tab = Tab.builder()
            .tabId("CHECKLIST")
            .groups(new ArrayList<>(List.of(group)))
            .build();

    return ScreenDescription.builder()
            .screenId("TEST")
            .tabs(new ArrayList<>(List.of(tab)))
            .build();
}
